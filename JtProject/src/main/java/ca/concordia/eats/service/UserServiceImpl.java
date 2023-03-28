package ca.concordia.eats.service;

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
    UserDao userDao;

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

    @Override
    public boolean removeUser(int userId) {
        return userDao.removeUser(userId);
    }


    @Override
    public List<Customer> getAllCustomers() {
        return userDao.getAllCustomers();
    }

    @Override
    public Customer getCustomerById(int userId) {
        return userDao.getCustomerById(userId);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return userDao.updateCustomer(customer);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return userDao.createCustomer(customer);
    }

    @Override
    public boolean removeCustomer(int userId) {
        return userDao.removeCustomer(userId);
    }
}
