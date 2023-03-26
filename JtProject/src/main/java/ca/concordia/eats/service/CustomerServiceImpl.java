package ca.concordia.eats.service;

import org.springframework.stereotype.Service;

import ca.concordia.eats.dao.CustomerDao;
import ca.concordia.eats.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Implements the UserService.java interface.
 */
@Service
public class CustomerServiceImpl extends UserServiceImpl implements CustomerService {

    @Autowired
    CustomerDao customerDao;

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    @Override
    public Customer getCustomerById(int userId) {
        return customerDao.getCustomerById(userId);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerDao.updateCustomer(customer);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerDao.createCustomer(customer);
    }

    @Override
    public boolean removeCustomer(int userId) {
        return customerDao.removeCustomer(userId);
    }
}
