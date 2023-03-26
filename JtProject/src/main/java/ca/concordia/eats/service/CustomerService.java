package ca.concordia.eats.service;

import ca.concordia.eats.dto.Customer;

import java.util.List;

public interface CustomerService extends UserService {
    // CRUD CUSTOMER
    public List<Customer> getAllCustomers();
    public Customer getCustomerById(int userId);
    public Customer updateCustomer(Customer customer);
    public Customer createCustomer(Customer customer);
    public boolean removeCustomer(int userId);

}
