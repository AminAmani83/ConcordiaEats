package ca.concordia.eats.controller;

import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.service.ProductService;
import ca.concordia.eats.service.ProductServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller

public class AdminController {

	@Autowired
	ProductService productService;
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
		//List<Product> allProducts = productService.fetchAllProducts();
		if(usernameForClass.equalsIgnoreCase(""))
			return "userLogin";
		else {
			model.addAttribute("username", usernameForClass);
			model.addAttribute("allProducts", new ArrayList<Product>()); // Todo
			return "index";
		}
	}

	@GetMapping("/userloginvalidate")
	public String userLogin(Model model) {
		
		return "userLogin";
	}
	@RequestMapping(value = "userloginvalidate", method = RequestMethod.POST)
	public String userLogin(@RequestParam("username") String username, @RequestParam("password") String pass, Model model) {
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject","root","");
			Statement stmt = con.createStatement();
			ResultSet rst = stmt.executeQuery("select * from users where username = '"+username+"' and password = '"+ pass+"' ;");
			if(rst.next()) {
				usernameForClass = rst.getString(2);
				return "redirect:/index";
				}
			else {
				model.addAttribute("message", "Invalid Username or Password");
				return "userLogin";
			}
			
		}
		catch(Exception e)
		{
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
	public String getProduct(Model model) {
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
		final RedirectView redirectView = new RedirectView("/book/addBook", true);
		Category fetchCategoryById = productService.fetchCategoryById(categoryId);
		product.setCategory(fetchCategoryById);
		productService.createProduct(product);
		return "redirect:/admin/products";
	}

	@GetMapping("/admin/products/update")
	public String updateProduct(@RequestParam("pid") int id, Model model) {
		String pname,pdescription,pimage;
		int pid,pweight,pquantity,pcategoryId;
		float pprice;
		String pcategoryName;
		try
		{
			Product product = productService.fetchProductById(id);
			pid = product.getId();
			pname = product.getName();
			pimage = product.getImagePath();
			pcategoryId = product.getCategory().getId();
			pcategoryName = product.getCategory().getName();
			pquantity = product.getSalesCount();
			pprice =  product.getPrice();
			pdescription = product.getDescription();
			model.addAttribute("pid",pid);
			model.addAttribute("pname",pname);
			model.addAttribute("pimage",pimage);
			model.addAttribute("pquantity",pquantity);
			model.addAttribute("pprice",pprice);
			model.addAttribute("pdescription",pdescription);
			model.addAttribute("pcategoryId", pcategoryId);
			model.addAttribute("pcategoryName", pcategoryName);
			
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		return "productsUpdate";
	}
	@RequestMapping(value = "/admin/products/updateData",method=RequestMethod.POST)
	public String updateproducttodb(@RequestParam("id") int id,@RequestParam("name") String name, @RequestParam("price") float price, @RequestParam("quantity") int quantity, @RequestParam("description") String description, @RequestParam("productImage") String picture, @RequestParam("categoryid") int categoryId ) 
	
	{
		try
		{
			Product product = new Product(); //(id, name, description, picture, price, quantity);
			product.setId(id);
			product.setName(name);
			product.setDescription(description);
			product.setImagePath(picture);
			product.setPrice(price);
			product.setSalesCount(quantity);
			Category fetchCategoryById = productService.fetchCategoryById(categoryId);
			product.setCategory(fetchCategoryById);
			productService.updateProduct(product);
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/products/delete")
	public String removeProductDb(@RequestParam("id") int id)
	{
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}
	
	@PostMapping("/admin/products")
	public String postProduct() {
		return "redirect:/admin/categories";
	}
	@RequestMapping(value = "admin/products/sendData",method=RequestMethod.POST)
	public String addproducttodb(@RequestParam("name") String name, @RequestParam("categoryid") String catid, @RequestParam("price") int price, @RequestParam("weight") int weight, @RequestParam("quantity") int quantity, @RequestParam("description") String description, @RequestParam("productImage") String picture ) {
		
		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject","root","");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from categories where name = '"+catid+"';");
			if(rs.next())
			{
			int categoryid = rs.getInt(1);
			
			PreparedStatement pst = con.prepareStatement("insert into products(name,image,categoryid,quantity,price,weight,description) values(?,?,?,?,?,?,?);");
			pst.setString(1,name);
			pst.setString(2, picture);
			pst.setInt(3, categoryid);
			pst.setInt(4, quantity);
			pst.setInt(5, price);
			pst.setInt(6, weight);
			pst.setString(7, description);
			int i = pst.executeUpdate();
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		return "redirect:/admin/products";
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
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject","root","");
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
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject","root","");
			
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
