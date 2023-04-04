package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;
import java.util.List;

/**
 * User Data Access Object.
 * Interface implemented in UserDaoImpl.java
 */
public interface CustomerDao  {

	public List<Product> fetchCustomerSearchedProduct(Customer customer);

}