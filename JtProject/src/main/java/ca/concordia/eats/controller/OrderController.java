package ca.concordia.eats.controller;

import ca.concordia.eats.dto.*;
import ca.concordia.eats.service.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PromotionService promotionService;

    // This method is used to show the items in the shopping cart.
    @GetMapping("/order")
    public String shoppingCart(HttpSession session, Model model) {
        Basket sessionBasket = (Basket) session.getAttribute("basket");
        Customer customer = (Customer) session.getAttribute("user");

        List<Product> customerFavoritedProducts = customer.getFavorite().getCustomerFavoritedProducts();
        boolean customerFavProductIsOnSale = customerFavoritedProducts.stream().anyMatch(Product::isOnSale);
        model.addAttribute("customerFavProductIsOnSale", customerFavProductIsOnSale);

        model.addAttribute("allProducts", orderService.getProductsInCart(sessionBasket));
        model.addAttribute("total", String.valueOf(orderService.getTotal(sessionBasket)));
        model.addAttribute("subTotal", String.valueOf(orderService.getSubTotal(sessionBasket)));
        model.addAttribute("tax", String.valueOf(orderService.getTaxes(sessionBasket)));
        model.addAttribute("delivery", String.valueOf(orderService.getDelivery(sessionBasket)));
        model.addAttribute("noCategoryFilter", true);
        model.addAttribute("favoriteProducts", customer.getFavorite().getCustomerFavoritedProducts());
        model.addAttribute("productCardFavSrc", "order");
        return "order";
    }

    // This method is used to add a product to the cart.
    @GetMapping("/order/add/{productId}")
    public String addProductToCart(@PathVariable("productId") Integer productId, HttpSession session, Model model) {
        Product product = productService.fetchProductById(productId);
        Basket sessionBasket = (Basket) session.getAttribute("basket");
        Customer customer = (Customer) session.getAttribute("user");
        if (product != null) {
            orderService.addProduct(product, sessionBasket);
        }

        List<Product> customerFavoritedProducts = customer.getFavorite().getCustomerFavoritedProducts();
        boolean customerFavProductIsOnSale = customerFavoritedProducts.stream().anyMatch(Product::isOnSale);
        model.addAttribute("customerFavProductIsOnSale", customerFavProductIsOnSale);

        model.addAttribute("allProducts", orderService.getProductsInCart(sessionBasket));
        model.addAttribute("total", String.valueOf(orderService.getTotal(sessionBasket)));
        model.addAttribute("subTotal", String.valueOf(orderService.getSubTotal(sessionBasket)));
        model.addAttribute("tax", String.valueOf(orderService.getTaxes(sessionBasket)));
        model.addAttribute("delivery", String.valueOf(orderService.getDelivery(sessionBasket)));
        model.addAttribute("noCategoryFilter", true);
        model.addAttribute("favoriteProducts", customer.getFavorite().getCustomerFavoritedProducts());
        model.addAttribute("productCardFavSrc", "order");

        return "order";
    }

    // This method is used to update the quantity of a product in the cart.
    @GetMapping("/order/update/{productId}")
    public String updateProductToCart(@PathVariable("productId") Integer productId, @RequestParam("quantity") int quantity,
                                      HttpSession session, Model model) {
        Product product = productService.fetchProductById(productId);
        Basket sessionBasket = (Basket) session.getAttribute("basket");
        Customer customer = (Customer) session.getAttribute("user");
        if (product != null) {
            orderService.updateProduct(product, quantity, sessionBasket);
        }

        List<Product> customerFavoritedProducts = customer.getFavorite().getCustomerFavoritedProducts();
        boolean customerFavProductIsOnSale = customerFavoritedProducts.stream().anyMatch(Product::isOnSale);
        model.addAttribute("customerFavProductIsOnSale", customerFavProductIsOnSale);

        model.addAttribute("allProducts", orderService.getProductsInCart(sessionBasket));
        model.addAttribute("total", String.valueOf(orderService.getTotal(sessionBasket)));
        model.addAttribute("subTotal", String.valueOf(orderService.getSubTotal(sessionBasket)));
        model.addAttribute("tax", String.valueOf(orderService.getTaxes(sessionBasket)));
        model.addAttribute("delivery", String.valueOf(orderService.getDelivery(sessionBasket)));
        model.addAttribute("noCategoryFilter", true);
        model.addAttribute("favoriteProducts", customer.getFavorite().getCustomerFavoritedProducts());
        model.addAttribute("productCardFavSrc", "order");
        return "order";
    }

    // This method is used to remove a product from the cart
    @GetMapping("/order/delete/{productId}")
    public String removeProductFromCart(@PathVariable("productId") Integer productId, HttpSession session, Model model) {
        Product product = productService.fetchProductById(productId);
        Basket sessionBasket = (Basket) session.getAttribute("basket");
        Customer customer = (Customer) session.getAttribute("user");
        if (product != null) {
            orderService.removeProduct(product, sessionBasket);
        }

        List<Product> customerFavoritedProducts = customer.getFavorite().getCustomerFavoritedProducts();
        boolean customerFavProductIsOnSale = customerFavoritedProducts.stream().anyMatch(Product::isOnSale);
        model.addAttribute("customerFavProductIsOnSale", customerFavProductIsOnSale);

        model.addAttribute("allProducts", orderService.getProductsInCart(sessionBasket));
        model.addAttribute("total", String.valueOf(orderService.getTotal(sessionBasket)));
        model.addAttribute("subTotal", String.valueOf(orderService.getSubTotal(sessionBasket)));
        model.addAttribute("tax", String.valueOf(orderService.getTaxes(sessionBasket)));
        model.addAttribute("delivery", String.valueOf(orderService.getDelivery(sessionBasket)));
        model.addAttribute("noCategoryFilter", true);
        model.addAttribute("favoriteProducts", customer.getFavorite().getCustomerFavoritedProducts());
        model.addAttribute("productCardFavSrc", "order");
        return "order";
    }

    @GetMapping("/checkout")
    public String CheckOut(HttpSession session) {
        return "buy";
    }

    // This method is mapped to the URL "/makeorder" with the GET request method.
    // It retrieves the Basket and User objects from the HttpSession.
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
