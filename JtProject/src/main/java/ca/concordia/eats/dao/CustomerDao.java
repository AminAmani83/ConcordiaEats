package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Customer;

import java.util.List;

/**
 * User Data Access Object.
 * Interface implemented in CustomerDaoImpl.java
 */
public interface CustomerDao extends UserDao {
    // CRUD CUSTOMER
    public List<Customer> getAllCustomers();
    public Customer getCustomerById(int userId);
    public Customer updateCustomer(Customer customer);
    public Customer createCustomer(Customer customer);
    public boolean removeCustomer(int userId);

}
