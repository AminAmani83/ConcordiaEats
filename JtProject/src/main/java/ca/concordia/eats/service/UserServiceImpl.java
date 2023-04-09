package ca.concordia.eats.service;

import ca.concordia.eats.dto.Favorite;
import ca.concordia.eats.dto.Rating;
import ca.concordia.eats.dto.UserCredentials;
import org.springframework.stereotype.Service;

import ca.concordia.eats.dao.UserDao;
import ca.concordia.eats.dto.User;
import ca.concordia.eats.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Implements the UserService.java interface.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    ProductService productService;

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public User createUser(User user) {
        return userDao.createUser(user);
    }

    public boolean validateUserLogin(UserCredentials userCredentials) {
        return userDao.checkUserByCredentials(userCredentials);
    }


    // Below are Customer related service classes.
    @Override
    public List<Customer> getAllCustomers() {
        return userDao.getAllCustomers();
    }

    @Override
    public Customer getCustomerById(int userId) {
        return userDao.getCustomerById(userId);
    }

    @Override
    public Customer getCustomerByCredential(UserCredentials userCredentials) {
        return userDao.getCustomerByCredential(userCredentials);
    }   

    @Override
    public Customer updateCustomer(Customer customer) {
        return userDao.updateCustomer(customer);
    }

    public Customer fetchCustomerData(UserCredentials userCredentials) {
        Customer customer = userDao.fetchCustomerData(userCredentials);
        customer.setFavorite(new Favorite(productService.fetchCustomerFavoriteProducts(customer.getUserId())));
        customer.setPurchasedProducts(productService.fetchPastPurchasedProducts(customer.getUserId()));
        return customer;
    }

    @Override
    public UserCredentials fetchUserCredentialsById(int userId) {
        return userDao.fetchUserCredentialsById(userId);
    }

    @Override
    public void updateUserProfile(Customer customer, UserCredentials userCredentials) {
        userDao.updateUserProfile(customer, userCredentials);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return userDao.createCustomer(customer);
    }

    @Override
    public boolean removeCustomerById(int customerId) {
        return userDao.removeCustomerById(customerId);
    }
}
