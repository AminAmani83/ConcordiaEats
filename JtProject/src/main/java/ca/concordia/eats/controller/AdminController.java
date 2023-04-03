package ca.concordia.eats.controller;

import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;
import ca.concordia.eats.dto.UserCredentials;
import ca.concordia.eats.service.ProductService;
import ca.concordia.eats.service.UserService;
import ca.concordia.eats.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.io.FileReader;
import java.io.IOException;

@Controller

public class AdminController {

	@Autowired
	ProductService productService;

	@Autowired
	private UserService userService;

	Connection con;
	public AdminController() throws IOException {
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String dbConfigPath = rootPath + "db.properties";

		FileReader reader = new FileReader(dbConfigPath);
		Properties dbProperties = new Properties();
		dbProperties.load(reader);

		try {
			this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", dbProperties.getProperty("username"), dbProperties.getProperty("password"));
		} catch(Exception e) {
			System.out.println("Error connecting to the DB: " + e.getMessage());
		}
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
	public String index(Model model) {
		List<Product> allProducts = productService.fetchAllProducts();
		if(usernameForClass.equalsIgnoreCase("")) {
			return "userLogin";
		}
		else {
			// Temp Products TODO: use actual results from DB
			Product bestSellerProduct = new Product(1, "Hamburger", "Delicious", "https://tmbidigitalassetsazure.blob.core.windows.net/secure/RMS/attachments/37/1200x1200/Sausage-Sliders-with-Cran-Apple-Slaw_exps48783_SD2235819D06_24_2bC_RMS.jpg", 0f, 0, false, 0f, null, null);
			Product highestRatedProduct = new Product(2, "Chicken Soup", "Delicious", "https://www.allrecipes.com/thmb/NgpgUebR7ixeEuToPd1c1TgaQmU=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/8814_HomemadeChickenSoup_SoupLovingNicole_LSH-2000-4ae7ff733c554fdab0796d15c8d1151f.jpg", 0f, 0, false, 0f, null, null);
			Product recommendedProduct = new Product(3, "Hamburger", "Delicious", "https://tastesbetterfromscratch.com/wp-content/uploads/2017/04/Tiramisu-14.jpg", 0f, 0, false, 0f, null, null);
			model.addAttribute("username", usernameForClass);
			model.addAttribute("allProducts", allProducts);
			model.addAttribute("bestSellerProduct", bestSellerProduct);
			model.addAttribute("highestRatedProduct", highestRatedProduct);
			model.addAttribute("recommendedProduct", recommendedProduct);
			return "index";
		}
	}

	@GetMapping("/userloginvalidate")
	public String userLogin(Model model) {
		
		return "userLogin";
	}
	@RequestMapping(value = "userloginvalidate", method = RequestMethod.POST)
	public String userLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {

		try {
			UserCredentials userCredentials = new UserCredentials();
			User user = new User();
			userCredentials.setUsername(username);
			userCredentials.setPassword(password);
			boolean isValid = userService.validateUserLogin(userCredentials);
			if (isValid) {
				usernameForClass = username;
				user = userService.fetchUserData(userCredentials);
				session.setAttribute("user", user);
				//User u = (User) session.getAttribute("user");
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
		productService.removeCategoryById(categoryId);
		return "redirect:/admin/categories";
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
	public String updateproduct(@ModelAttribute("product") Product product, @RequestParam("productImage") MultipartFile multipartFile, @RequestParam("categoryid") int categoryId ) 
	
	{
		Category category = productService.fetchCategoryById(categoryId);
		product.setCategory(category);
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		product.setImagePath(fileName);
		productService.updateProduct(product);
		String uploadDir = "/resources/Product Images/";
		 
        try {
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("upload file failed", e);
		}
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

	@GetMapping("/admin/customers")
	public String getCustomerDetail() {
		return "displayCustomers";
	}

	@GetMapping("profileDisplay")
	public String profileDisplay(Model model) {
		String displayusername,displaypassword,displayemail,displayaddress;
		try
		{
			Statement stmt = con.createStatement();
			ResultSet rst = stmt.executeQuery("select * from users where username = '"+ usernameForClass +"';");
			
			if(rst.next())
			{
			int userid = rst.getInt(1);
			displayusername = rst.getString(2);
			displayemail = rst.getString(3);
			displaypassword = rst.getString(4);
			displayaddress = rst.getString(5);
			model.addAttribute("userid",userid);
			model.addAttribute("username",displayusername);
			model.addAttribute("email",displayemail);
			model.addAttribute("password",displaypassword);
			model.addAttribute("address",displayaddress);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		System.out.println("Hello");
		return "updateProfile";
	}
	
	@RequestMapping(value = "updateuser",method=RequestMethod.POST)
	public String updateUserProfile(@RequestParam("userid") int userid,@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("address") String address) 
	
	{
		try
		{
			PreparedStatement pst = con.prepareStatement("update users set username= ?,email = ?,password= ?, address= ? where uid = ?;");
			pst.setString(1, username);
			pst.setString(2, email);
			pst.setString(3, password);
			pst.setString(4, address);
			pst.setInt(5, userid);
			int i = pst.executeUpdate();	
			usernameForClass = username;
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		return "redirect:/index";
	}

}