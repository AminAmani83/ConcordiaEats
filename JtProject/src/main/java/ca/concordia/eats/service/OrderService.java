package ca.concordia.eats.service;

import java.util.List;
import java.util.Map;
import ca.concordia.eats.dto.Basket;

import javax.servlet.http.HttpSession;

import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;

public interface OrderService {
	
    void addProduct(Product product, Basket sessionBasket);

    void removeProduct(Product product, Basket sessionBasket);
    
    List<Product> getProductsInCart(Basket sessionBasket);

    void checkout();

    float getTotal(Basket sessionBasket);

	void makeOrder(Basket sessionBasket, User sessionUser);

	void updateProduct(Product product, int quantity, Basket sessionBasket);
}
