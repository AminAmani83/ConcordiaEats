package ca.concordia.eats.controller;

import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.Favorite;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;
import ca.concordia.eats.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    ProductService productService;

    @GetMapping("/product/make-favorite")
    public String makeFavorite(@RequestParam("productid") int productId, @RequestParam("src") String sourcePage, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("user");
        productService.makeFavorite(customer.getUserId(), productId);
        customer.setFavorite(new Favorite(productService.fetchCustomerFavoriteProducts(customer.getUserId())));
        return "redirect:/" + sourcePage;
    }

    @GetMapping("/product/remove-favorite")
    public String removeFavorite(@RequestParam("productid") int productId, @RequestParam("src") String sourcePage, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("user");
        productService.removeFavorite(customer.getUserId(), productId);
        customer.setFavorite(new Favorite(productService.fetchCustomerFavoriteProducts(customer.getUserId())));
        return "redirect:/" + sourcePage;
    }

    @GetMapping("/favorites")
    public String fetchCustomerFavoriteProducts(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("user");
        model.addAttribute("favoriteProducts", customer.getFavorite().getCustomerFavoritedProducts());
        return "/favorites";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model, HttpSession session ) {
        User user = (User) session.getAttribute("user");
        List<Product> products = productService.search(query, user.getUserId());
        model.addAttribute("products", products);
        return "search-results";
    }
}
