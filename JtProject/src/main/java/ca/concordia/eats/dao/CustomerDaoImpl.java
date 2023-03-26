package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Customer;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class CustomerDaoImpl extends UserDaoImpl implements CustomerDao {

    Connection con;
    public CustomerDaoImpl() {
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://localhsot:3306/springproject", "root", "");
        } catch(Exception e) {
            System.out.println("Error connecting to the database " + e.getMessage());
        }
    }

    /**
     * Need to retrieve this information for all Customers:
     * - userId (int), username (string), email (string), address (string), phone (string).
     * 
     * Important: make sure to only select users whose role is 'customer'
     */
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> allCustomers = new LinkedList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users where role='customer';");
            while (rs.next()) {
                allCustomers.add(new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7)));
            }
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }

        return allCustomers;
    }


    @Override
    public Customer getCustomerById(int userId) {
        Customer customer = new Customer();

        try {
            PreparedStatement pst = con.prepareStatement("select * from users where user_id = (?);");
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            customer = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));

        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }

        return customer;
    }


    @Override
    public Customer updateCustomer(Customer customer) {
        try {
            PreparedStatement pst = con.prepareStatement("update users set username = ?, set email = ?, set address = ?, set phone = ?  where user_id = ?;");
            pst.setString(1, customer.getUsername());
            pst.setString(2, customer.getEmail());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getPhone());
            pst.setInt(5, customer.getUserId());
            pst.executeUpdate();

        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return customer;
    }


    @Override
    public Customer createCustomer(Customer customer) {
        try {
            PreparedStatement pst = con.prepareStatement("insert into users (username, role, email, address, phone) values(?,'customer',?,?,?);");
            pst.setString(1, customer.getUsername());
            pst.setString(2, customer.getEmail());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getPhone());
            pst.executeUpdate();

        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return customer;
    }


    @Override
    public boolean removeCustomer(int userId) {
        try {
            PreparedStatement pst = con.prepareStatement("delete from users where user_id = ? ;");
            pst.setInt(1, userId);
            pst.executeUpdate();
        
        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return true;
    }
    
}
