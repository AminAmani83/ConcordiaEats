package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Basket;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;

public interface OrderDao {

	public void makeOrder(User user, Basket basket);
	public void removeAllPurchasesByCustomerId(int customerId);

}
