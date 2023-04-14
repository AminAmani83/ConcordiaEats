package ca.concordia.eats.controller;

import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.Favorite;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.Rating;
import ca.concordia.eats.service.ProductService;
import ca.concordia.eats.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    ProductService productService;
    @Autowired
    RecommendationService recommendationService;

    @GetMapping("/product/make-favorite")
    public String makeFavorite(@RequestParam("productid") int productId, @RequestParam("src") String sourcePage, HttpSession session) {
        if (session.getAttribute("user") == null) return "userLogin";
        Customer customer = (Customer) session.getAttribute("user");
        productService.makeFavorite(customer.getUserId(), productId);
        customer.setFavorite(new Favorite(productService.fetchCustomerFavoriteProducts(customer.getUserId())));
        return "redirect:/" + sourcePage;
    }

    @GetMapping("/product/remove-favorite")
    public String removeFavorite(@RequestParam("productid") int productId, @RequestParam("src") String sourcePage, HttpSession session) {
        if (session.getAttribute("user") == null) return "userLogin";
        Customer customer = (Customer) session.getAttribute("user");
        productService.removeFavorite(customer.getUserId(), productId);
        customer.setFavorite(new Favorite(productService.fetchCustomerFavoriteProducts(customer.getUserId())));
        return "redirect:/" + sourcePage;
    }

    /**
     * purchasedProducts is used to display the correct rating button in the front-end.
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/favorites")
    public String fetchCustomerFavoriteProducts(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "userLogin";
        Customer customer = (Customer) session.getAttribute("user");

        model.addAttribute("favoriteProducts", productService.fetchCustomerFavoriteProducts(customer.getUserId()));
        model.addAttribute("purchasedProducts", productService.fetchPastPurchasedProducts(customer.getUserId()));
        model.addAttribute("productCardFavSrc", "favorites");
        return "/favorites";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) return "userLogin";
        Customer customer = (Customer) session.getAttribute("user");
        List<Product> products = productService.search(query, customer.getUserId());
        model.addAttribute("products", products);
        model.addAttribute("purchasedProducts", productService.fetchPastPurchasedProducts(customer.getUserId()));
        model.addAttribute("favoriteProducts", customer.getFavorite().getCustomerFavoritedProducts());
        model.addAttribute("productCardFavSrc", "search?query=" + query);
        model.addAttribute("query", query);

        return "search-results";
    }

    /**
     * Allows the Customer to rate a product he/she has already purchased.
     * 
     * @param productId - the product to be rated
     * @param rating - the chosen rating
     * @param sourcePage - the page on which the customer was when rating the product - there are multiple page possibilities here.
     * @param session - the User session
     * @return
     */
    @GetMapping("/product/rate-product")
    public String rateProduct(@RequestParam("productId") int productId, 
            @RequestParam("rating") int rating,
            @RequestParam("src") String sourcePage,
            HttpSession session) {
        
			if (session.getAttribute("user") == null) return "userLogin";
			
			Customer customer = (Customer) session.getAttribute("user");
			Product product = productService.fetchProductById(productId);
			Map<Integer,Integer> customerRatings = productService.fetchAllCustomerRatings(customer.getUserId());
			List<Product> purchasedProducts = productService.fetchPastPurchasedProducts(customer.getUserId()); 
			
			if (purchasedProducts.contains(product)) {      // allow rating (also checked in front-end when creating the button)
			productService.rateProduct(customer.getUserId(), productId, rating);
			customer.setRating(new Rating(customerRatings, purchasedProducts));
			product.setRating(productService.calculateAvgProductRating(productId));     // Needs to be recalculated after this rating.  
			} 
			return "redirect:/" + sourcePage;
			}


}
