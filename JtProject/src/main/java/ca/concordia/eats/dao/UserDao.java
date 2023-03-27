package ca.concordia.eats.dao;

import ca.concordia.eats.dto.User;
import ca.concordia.eats.dto.Customer;

import java.util.List;

/**
 * User Data Access Object.
 * Interface implemented in UserDaoImpl.java
 */
public interface UserDao {
    // CRUD USER
    public List<User> getAllUsers();
    public User getUserById(int userId);
    public User updateUser(User user);
    public User createUser(User user);
    public boolean removeUser(int userId);

    // CRUD CUSTOMER
    public List<Customer> getAllCustomers();
    public Customer getCustomerById(int userId);
    public Customer updateCustomer(Customer customer);
    public Customer createCustomer(Customer customer);
    public boolean removeCustomer(int userId);

}
