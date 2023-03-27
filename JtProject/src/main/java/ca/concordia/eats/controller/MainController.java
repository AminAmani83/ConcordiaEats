package ca.concordia.eats.controller;

import ca.concordia.eats.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
