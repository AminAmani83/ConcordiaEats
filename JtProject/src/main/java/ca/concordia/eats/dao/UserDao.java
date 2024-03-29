package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;
import ca.concordia.eats.dto.UserCredentials;

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
    public boolean checkUserByCredentials(UserCredentials userCredentials);
    public UserCredentials fetchUserCredentialsById(int userId);
    public void updateUserProfile(Customer customer, UserCredentials userCredentials);

    // CRUD CUSTOMER
    public Customer fetchCustomerData(UserCredentials userCredentials);
    public boolean checkUserIsCustomer(UserCredentials userCredentials);    // helper method for checking that the User is indeed a customer and not an admin.
    public List<Customer> getAllCustomers();
    public Customer getCustomerById(int userId);
    public Customer getCustomerByCredential(UserCredentials userCredentials);
    public Customer updateCustomer(Customer customer);      // equivalent to updateProfile() in our class diagram
    public Customer createCustomer(Customer customer);      // equivalent to register() in our class diagram
    public boolean removeCustomerById(int customerId);
	public List<Product> fetchCustomerSearchedProduct(Customer customer) ;



}
