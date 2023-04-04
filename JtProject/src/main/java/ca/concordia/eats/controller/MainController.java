package ca.concordia.eats.controller;

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

@Controller
public class MainController {

    @Autowired
    ProductService productService;

    @GetMapping("/product/make-favorite")
    public String makeFavorite(@RequestParam("productid") int productId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        productService.makeFavorite(user.getUserId(), productId);
        return "redirect:/product/";
    }

    @GetMapping("/product/remove-favorite")
    public String removeFavorite(@RequestParam("productid") int productId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        productService.removeFavorite(user.getUserId(), productId);
        return "redirect:/product/";
    }

    @GetMapping("/product/get-favorites")
    public String fetchCustomerFavoriteProducts(@RequestParam("productid") int productId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        productService.fetchCustomerFavoriteProducts(user.getUserId());
        return "redirect:/product/favorites";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<Product> products = productService.search(query);
        model.addAttribute("products", products);
        return "search-results";
    }
}
