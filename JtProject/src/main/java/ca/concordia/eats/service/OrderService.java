package ca.concordia.eats.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import ca.concordia.eats.dto.Product;

public interface OrderService {
	
    void addProduct(Product product, HttpSession session);

    void removeProduct(Product product, HttpSession session);
    
    List<Product> getProductsInCart(HttpSession session);

    void checkout();

    float getTotal(HttpSession session);

	void makeOrder(HttpSession session);

	void updateProduct(Product product, int quantity, HttpSession session);
}
