package ca.concordia.eats.service;

import ca.concordia.eats.dto.User;
import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.UserCredentials;

import java.util.List;

public interface UserService {
    // CRUD USER
    public List<User> getAllUsers();
    public User getUserById(int userId);
    public User updateUser(User user);
    public User createUser(User user);
    public boolean removeUser(int userId);
    public boolean validateUserLogin(UserCredentials userCredentials);
    public User fetchUserData(UserCredentials userCredentials);

    // CRUD CUSTOMER
    public List<Customer> getAllCustomers();
    public Customer getCustomerById(int userId);
    public Customer updateCustomer(Customer customer);
    public Customer createCustomer(Customer customer);
    public boolean removeCustomer(int userId);
   
}
