package ca.concordia.eats.controller;

import ca.concordia.eats.dto.Product;
import ca.concordia.eats.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    ProductService productService;

    @GetMapping("/product/make-favorite")
    public String makeFavorite(@RequestParam("customerid") int customerId, @RequestParam("productid") int productId) {
        productService.makeFavorite(customerId, productId);
        return "redirect:/product/";
    }

    @GetMapping("/product/remove-favorite")
    public String removeFavorite(@RequestParam("customerid") int customerId, @RequestParam("productid") int productId) {
        productService.removeFavorite(customerId, productId);
        return "redirect:/product/";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model ) {
        List<Product> products = productService.search(query);
        model.addAttribute("products", products);
        return "search-results";
    }

    @GetMapping("/product/rate-product")
    public String rateProduct(@RequestParam("customerid") int customerId, 
                                @RequestParam("productid") int productId, 
                                @RequestParam("rating") int rating) {

        productService.rateProduct(customerId, productId, rating);
        return "redirect:/product/";
    }

}
