package ca.concordia.eats.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.concordia.eats.dao.OrderDao;
import ca.concordia.eats.dto.Basket;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;

import javax.servlet.http.HttpSession;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;
	
	@Override
	public void addProduct(Product product, HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        
        basket.addProduct(product, session);

	}

	@Override
	public void removeProduct(Product product, HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        
        basket.removeProduct(product);
	}

	@Override
	public List<Product> getProductsInCart(HttpSession session) {
		
        Basket basket = (Basket) session.getAttribute("basket");
		return basket.getProductsInCart();
	}
	
	@Override
	public void checkout() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getTotal(HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
		return basket.getTotal();
	}
	
	public void makeOrder(HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        User user = (User) session.getAttribute("user");
        
        if (basket != null && user != null)
        	orderDao.makeOrder(user, basket);
        
        basket = new Basket();
        session.setAttribute("basket", basket);
        
	}

	@Override
	public void updateProduct(Product product, int quantity, HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        
        basket.updateProduct(product, quantity);
		
	}	
}
