package ca.concordia.eats.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.concordia.eats.dao.OrderDao;
import ca.concordia.eats.dao.PromotionDao;
import ca.concordia.eats.dto.Basket;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.Promotion;
import ca.concordia.eats.dto.User;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;    
    @Autowired
    private PromotionDao promotionDao;
	
	// This method adds a product to the session basket.
    @Override
	public void addProduct(Product product, Basket sessionBasket) {
        Basket basket = sessionBasket;
        
        basket.addProduct(product);

	}

	
	// This method removes a product from the session basket.
    	public void removeProduct(Product product, Basket sessionBasket) {
        Basket basket = sessionBasket;
        
        basket.removeProduct(product);
	}

	// This method returns a list of all products in the session basket.
        @Override
	public List<Product> getProductsInCart(Basket sessionBasket) {
		
		return sessionBasket.getProductsInCart();
	}

	// This method returns the total cost of the products in the session basket.
	public float getTotal(Basket sessionBasket) {	
		return sessionBasket.getTotal(promotionDao.fetchAllPromotions());
	}
	
	// This method returns the taxes for the products in the session basket.
	public double getTaxes(Basket sessionBasket) {
	       
			return sessionBasket.getTaxes();
	}
	
	// This method returns the delivery fee for the order.
	public double getDelivery(Basket sessionBasket) {
	       
			return sessionBasket.getDelivery();
	}
	
	// This method is responsible for creating a new order for the session user.
	public void makeOrder(Basket sessionBasket, User sessionUser) {
       
        if (sessionBasket != null && sessionUser != null)
        	orderDao.makeOrder(sessionUser, sessionBasket);
	}

	
	// This method updates the quantity of a product in the session basket.
	public void updateProduct(Product product, int quantity, Basket sessionBasket) {
        Basket basket = sessionBasket;
        
        basket.updateProduct(product, quantity);	
	}	
}
