package ca.concordia.eats.controller;

import ca.concordia.eats.dto.*;
import ca.concordia.eats.service.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
    private ProductService productService;

    @GetMapping("/order")
    public String shoppingCart(HttpSession session, Model model) {
    	Basket sessionBasket = (Basket) session.getAttribute("basket");
        model.addAttribute("allProducts", orderService.getProductsInCart(sessionBasket));
        model.addAttribute("total", String.valueOf(orderService.getTotal(sessionBasket)));
        return "order";
    }

    @GetMapping("/order/add/{productId}")
    public String addProductToCart(@PathVariable("productId") Integer productId, HttpSession session, Model model) {
        Product product = productService.fetchProductById(productId);
        Basket sessionBasket = (Basket) session.getAttribute("basket");
        if (product != null)
        {
        	orderService.addProduct(product, sessionBasket);
        }
        
        model.addAttribute("allProducts", orderService.getProductsInCart(sessionBasket));
        model.addAttribute("total", String.valueOf(orderService.getTotal(sessionBasket)));
        
        return "order";
    }
    
    @GetMapping("/order/update/{productId}")
    public String updateProductToCart(@PathVariable("productId") Integer productId, @RequestParam("quantity") int quantity,
    		HttpSession session, Model model) {
        Product product = productService.fetchProductById(productId);
        Basket sessionBasket = (Basket) session.getAttribute("basket");
        if (product != null)
        {
        	orderService.updateProduct(product, quantity, sessionBasket);
        }
        
        model.addAttribute("allProducts", orderService.getProductsInCart(sessionBasket));
        model.addAttribute("total", String.valueOf(orderService.getTotal(sessionBasket)));
        
        return "order";
    }
    
    @GetMapping("/order/delete/{productId}")
    public String removeProductFromCart(@PathVariable("productId") Integer productId, HttpSession session, Model model) {
        Product product = productService.fetchProductById(productId);
        Basket sessionBasket = (Basket) session.getAttribute("basket");
        if (product != null)
        {
        	orderService.removeProduct(product, sessionBasket);
        }
        
        model.addAttribute("allProducts", orderService.getProductsInCart(sessionBasket));
        model.addAttribute("total", String.valueOf(orderService.getTotal(sessionBasket)));
        
        return "order";
    }
    
    @GetMapping("/checkout")
    public String CheckOut(HttpSession session) {

        return "buy";
    }
    
    @GetMapping("/makeorder")
    public String MakeOrder(HttpSession session) {

    	Basket sessionBasket = (Basket) session.getAttribute("basket");
    	User sessionUser = (User) session.getAttribute("user");
    	orderService.makeOrder(sessionBasket, sessionUser);
    	sessionBasket = new Basket();
    	session.setAttribute("basket", sessionBasket);
    	
        return "redirect:/index";
    }
}
