package ca.concordia.eats.controller;

import ca.concordia.eats.dto.*;
import ca.concordia.eats.service.PromotionService;
import ca.concordia.eats.service.ServiceException;
import ca.concordia.eats.service.UserService;
import ca.concordia.eats.service.ProductService;
import ca.concordia.eats.service.RecommendationService;
import ca.concordia.eats.utils.ConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

@Controller

public class AdminController {

	@Autowired
	ProductService productService;
	@Autowired
	PromotionService promotionService;

	@Autowired
	private UserService userService;
	
	 @Autowired
	 RecommendationService recommendationService;

	Connection con;

	/**
     * Uses the db.properties file in resources to retrieve db connection parameters
     * username=<my-username>
     * password=<my-secret-password>
	 * url=<jdbc-url>
     * @throws IOException
     */
	public AdminController() throws IOException {
        this.con = ConnectionUtil.getConnection();
	}

	int adminLogInCheck = 0;
	String usernameForClass = "";

	@RequestMapping(value = {"/","/logout"})
	public String returnIndex() {
		adminLogInCheck =0;
		usernameForClass = "";
		return "userLogin";
	}

	@GetMapping("/index")
	public String index(Model model, HttpSession session, @RequestParam("category-filter[]") Optional<String[]> categoryFilterNames) {
		if (usernameForClass.equalsIgnoreCase("")) {
			return "userLogin";
		} else {
			List<Product> allProducts = productService.fetchAllProducts().stream().filter(p -> !p.isDisable()).collect(Collectors.toList());
			List<Category> nonEmptyCategories = allProducts.stream().map(Product::getCategory).distinct()
					.sorted(Comparator.comparing(Category::getName)).collect(Collectors.toList());
			Customer customer = (Customer) session.getAttribute("user");
			refreshRecommendedProducts(customer, allProducts);

			model.addAttribute("recommendedProduct", customer.getRecommendation().getRecommendedProduct());
	        model.addAttribute("bestSellerProduct", customer.getRecommendation().getBestSellerProduct());
	        model.addAttribute("highestRatedProduct", customer.getRecommendation().getHighestRatingProduct());

			if (categoryFilterNames.isPresent()) {
				allProducts = allProducts.stream()
						.filter(p -> Arrays.asList(categoryFilterNames.get()).contains(p.getCategory().getName()))
						.collect(Collectors.toList());
			}

			model.addAttribute("username", usernameForClass);
			model.addAttribute("allProducts", allProducts);
			model.addAttribute("allCategories", nonEmptyCategories);
			model.addAttribute("favoriteProducts", customer.getFavorite().getCustomerFavoritedProducts());
			model.addAttribute("purchasedProducts", productService.fetchPastPurchasedProducts(customer.getUserId()));
			model.addAttribute("productCardFavSrc", "index");

			return "index";
		}
	}

	private static void refreshRecommendedProducts(Customer customer, List<Product> allProducts) {
		Map<Integer, Product> allProductsMap = allProducts.stream().collect(Collectors.toMap(Product::getId, item -> item));
		Product recommendedProduct = customer.getRecommendation().getRecommendedProduct();
		if (recommendedProduct != null) {
			customer.getRecommendation().setRecommendedProduct(allProductsMap.get(recommendedProduct.getId()));
		}
		Product bestSellerProduct = customer.getRecommendation().getBestSellerProduct();
		if (bestSellerProduct != null) {
			customer.getRecommendation().setBestSellerProduct(allProductsMap.get(bestSellerProduct.getId()));
		}
		Product highestRatingProduct = customer.getRecommendation().getHighestRatingProduct();
		customer.getRecommendation().setHighestRatingProduct(allProductsMap.get(highestRatingProduct.getId()));
	}

	@GetMapping("/userloginvalidate")
	public String userLogin(Model model) {
		return "userLogin";
	}

	@RequestMapping(value = "userloginvalidate", method = RequestMethod.POST)
	public String userLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {

		try {
			UserCredentials userCredentials = new UserCredentials();
			userCredentials.setUsername(username);
			userCredentials.setPassword(password);
			boolean isValid = userService.validateUserLogin(userCredentials);
			if (isValid) {
				Basket basket = new Basket();
				usernameForClass = username;
				Customer customer = userService.fetchCustomerData(userCredentials);
				customer.setFavorite(new Favorite(productService.fetchCustomerFavoriteProducts(customer.getUserId())));

				Recommendation recommendation = new Recommendation();
				recommendation.setBestSellerProduct(recommendationService.fetchBestSellerProduct());
				recommendation.setRecommendedProduct(recommendationService.fetchPersonalizedRecommendedProductByCustomer(customer));
				recommendation.setHighestRatingProduct(recommendationService.fetchHighestRatingProduct());
				customer.setRecommendation(recommendation);

				session.setAttribute("user", customer);
				session.setAttribute("basket", basket);

				return "redirect:/index";
			} else {
				model.addAttribute("message", "Invalid Username or Password");
				return "userLogin";
			}
		}
		catch(Exception e) {
			System.out.println("Exception:"+e);
		}
		return "userLogin";
	}

	@GetMapping("/admin")
	public String adminLogin(Model model) {
		return "adminlogin";
	}

	@GetMapping("/adminhome")
	public String adminHome(Model model) {
		if(adminLogInCheck !=0)
			return "adminHome";
		else
			return "redirect:/admin";
	}

	@GetMapping("/loginvalidate")
	public String adminLog(Model model) {
		return "adminlogin";
	}

	@RequestMapping(value = "loginvalidate", method = RequestMethod.POST)
	public String adminLogin(@RequestParam("username") String username, @RequestParam("password") String pass, Model model) {
		if(username.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("123")) {
			adminLogInCheck =1;
			return "redirect:/adminhome";
			}
		else {
			model.addAttribute("message", "Invalid Username or Password");
			return "adminlogin";
		}
	}

	@GetMapping("/admin/categories")
	public String getAllCategories(Model model) {
		List<Category> allCategories = productService.fetchAllCategories();
		model.addAttribute("allCategories", allCategories);
		return "categories";
	}

	@RequestMapping(value = "admin/sendcategory",method = RequestMethod.GET)
	public String createCategory(@RequestParam("categoryname") String categoryName) {
		Category category = new Category();
		category.setName(categoryName);
		productService.createCategory(category);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/delete")
	public String removeCategory(@RequestParam("id") int categoryId) {
		boolean categoryRemoved = productService.removeCategoryById(categoryId);
		if (categoryRemoved) {
			return "redirect:/admin/categories";
		} else {
			return "redirect:/admin/categories?msg=removalError";
		}
	}

	@GetMapping("/admin/categories/update")
	public String updateCategory(@RequestParam("categoryid") int categoryId, @RequestParam("categoryname") String categoryName) {
		productService.updateCategory(new Category(categoryId, categoryName));
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/products")
	public String getAllProduct(Model model) {
		List<Product> allProducts = productService.fetchAllProducts();
		model.addAttribute("allProducts", allProducts);
		return "products";
	}
	
	@GetMapping("/admin/products/add")
	public String addProduct(Model model) {
		List<Category> allCategories = productService.fetchAllCategories();
		model.addAttribute("allCategories", allCategories);
		model.addAttribute("product", new Product());
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String addProduct(@ModelAttribute("product") Product product, @RequestParam("categoryid") int categoryId) {
		Category categoryById = productService.fetchCategoryById(categoryId);
		product.setCategory(categoryById);
		productService.createProduct(product);
		return "redirect:/admin/products";
	}

	@GetMapping("/admin/products/update")
	public String updateProduct(@RequestParam("pid") int id, Model model) {
		Product product = productService.fetchProductById(id);
		model.addAttribute("product", product);
		return "productsUpdate";
	}

	@RequestMapping(value = "/admin/products/updateData",method=RequestMethod.POST)
	public String updateproduct(@ModelAttribute("product") Product product, @RequestParam("categoryid") int categoryId ) 
	{
		Category category = productService.fetchCategoryById(categoryId);
		product.setCategory(category);
//		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String fileName = "";
		product.setImagePath(fileName);
		productService.updateProduct(product);
//		String uploadDir = "/resources/Product Images/";
//		 
//        try {
//			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new RuntimeException("upload file failed", e);
//		}
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/products/delete")
	public String removeProduct(@RequestParam("id") int id)
	{
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}
	
	@PostMapping("/admin/products")
	public String postProduct() {
		return "redirect:/admin/categories";
	}

	/**
	 * To display all customers in the admin customer panel.
	 * @param model
	 * @return
	 */
	@GetMapping("/admin/customers")
	public String getAllCustomers(Model model) {
		List<Customer> allCustomers = userService.getAllCustomers();
		model.addAttribute("allCustomers", allCustomers);
		return "customers";
	}

	/**
	 * To allow the admin to remove customers from the customer panel.
	 * @param customerId
	 * @return
	 */
	@GetMapping("/admin/customers/delete")
	public String removeCustomer(@RequestParam("id") int customerId) {
		userService.removeCustomerById(customerId);
		return "redirect:/admin/customers";
	}


	/**
	 * The admin can update only certain information from the customer.
	 * The admin can only do so by 'id'.
	 * @param customerId
	 * @param email
	 * @param address
	 * @param phone
	 * @return
	 */
	@GetMapping("/admin/customers/update")
	public String updateCustomer(@RequestParam("customerId") int customerId, 
								@RequestParam("customerName") String username,
								@RequestParam("email") String email, 
								@RequestParam("address") String address,
								@RequestParam("phone") String phone) {
		Customer customer = userService.getCustomerById(customerId);

		// update the customer
		customer.setEmail(email);
		customer.setPhone(phone);
		customer.setAddress(address);

		userService.updateCustomer(customer);

		return "redirect:/admin/customers";
	}


	@GetMapping("/admin/promotions")
	public String getAllPromotions(Model model) {
		try {
			List<Promotion> allPromotions = promotionService.getAllPromotions();
			model.addAttribute("allPromotions", allPromotions);
			return "promotions";
		} catch (ServiceException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "An error occurred while retrieving the promotions");
			return "error";
		}

	}

	@RequestMapping(value = "admin/create", method = RequestMethod.POST)
	public String createPromotion(@RequestParam("name") String name,
								  @RequestParam("promotionStartDate") Date startDate,
								  @RequestParam("promotionEndDate") Date endDate,
								  @RequestParam("promotionType") String promotionType,
								  Model model) {
		try {
			Promotion promotion = new Promotion();
			promotion.setStartDate(startDate);
			promotion.setEndDate(endDate);
			promotion.setName(name);
			promotion.setType(promotionType);
			promotionService.createPromotion(promotion);
			return "redirect:/admin/promotions";
		} catch (ServiceException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "An error occurred while creating the promotion");
			return "error";
		}
	}

	@GetMapping("/admin/promotions/delete")
	public String removePromotion(@RequestParam("id") int promotionId, Model model) {
		try {
			boolean promotionRemoved = promotionService.removePromotionById(promotionId);
			if (promotionRemoved) {
				return "redirect:/admin/promotions";
			} else {
				return "redirect:/admin/categories?msg=removalError";
			}
		} catch (ServiceException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "An error occurred while deleting the promotion");
			return "redirect:/admin/promotions?msg=removalError";
		}
	}

	@GetMapping("/admin/promotions/update")
	public String updatePromotion(@RequestParam("promotionId") int id,
								  @RequestParam("promotionName") String name,
								  @RequestParam("promotionStartDate") Date startDate,
								  @RequestParam("promotionEndDate") Date endDate,
								  @RequestParam("promoType") String promoType,
								  Model model) {
		try {
			Promotion promotion = promotionService.getPromotionById(id);
			promotion.setName(name);
			promotion.setStartDate(startDate);
			promotion.setEndDate(endDate);
			promotion.setType(promoType);
			promotionService.updatePromotion(promotion);
			return "redirect:/admin/promotions";
		} catch (ServiceException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "An error occurred while deleting the promotion");
			return "error";
		}
	}


}