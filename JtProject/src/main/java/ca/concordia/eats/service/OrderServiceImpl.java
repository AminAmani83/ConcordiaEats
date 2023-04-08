package ca.concordia.eats.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.concordia.eats.dao.OrderDao;
import ca.concordia.eats.dto.Basket;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;
	
	@Override
	public void addProduct(Product product, Basket sessionBasket) {
        Basket basket = sessionBasket;
        
        basket.addProduct(product);

	}

	
	public void removeProduct(Product product, Basket sessionBasket) {
        Basket basket = sessionBasket;
        
        basket.removeProduct(product);
	}

	@Override
	public List<Product> getProductsInCart(Basket sessionBasket) {
		
		return sessionBasket.getProductsInCart();
	}
	
	@Override
	public void checkout() {
		// TODO Auto-generated method stub
		
	}

	public float getTotal(Basket sessionBasket) {
       
		return sessionBasket.getTotal();
	}
	
	public void makeOrder(Basket sessionBasket, User sessionUser) {
       
        if (sessionBasket != null && sessionUser != null)
        	orderDao.makeOrder(sessionUser, sessionBasket);
	}

	
	public void updateProduct(Product product, int quantity, Basket sessionBasket) {
        Basket basket = sessionBasket;
        
        basket.updateProduct(product, quantity);
		
	}	
}
