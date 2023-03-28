package ca.concordia.eats.dao;

import ca.concordia.eats.dto.User;
import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.UserCredentials;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private Connection con;
    public UserDaoImpl() {
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "");
        } catch(Exception e) {
            System.out.println("Error connecting to the DB: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new LinkedList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select id, username, role, email from user");
            while (rs.next()) {
                allUsers.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }

        return allUsers;
    }


    @Override
    public User getUserById(int userId) {
        User user = new User();

        try {
            PreparedStatement pst = con.prepareStatement("select id, username, role, email from user where id = (?);");
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));

        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }

        return user;
    }


    /**
     * - Cannot change role from 'Customer' to 'Admin'
     * - Cannot change password.
     */
    @Override
    public User updateUser(User user) {
        try {
            PreparedStatement pst = con.prepareStatement("update user set username = ?, email = ? where id = ?");
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getEmail());
            pst.setInt(3, user.getUserId());
            pst.executeUpdate();

        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return user;
    }


    /**
     * Address and Phone are not set here.
     */
    @Override
    public User createUser(User user) {
        try {
            PreparedStatement pst = con.prepareStatement("insert into user (username, password, email) values(?,?,?);");
            pst.setString(1, user.getUsername());
            pst.setString(2, "some_password");
            pst.setString(3, user.getEmail());
            pst.executeUpdate();

        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return user;
    }


    @Override
    public boolean removeUser(int userId) {
        try {
            PreparedStatement pst = con.prepareStatement("delete from user where id = ? ;");
            pst.setInt(1, userId);
            pst.executeUpdate();
        
        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return true;
    }
<<<<<<< HEAD
 
    
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
            ResultSet rs = stmt.executeQuery("select * from user where role='CUSTOMER';");
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
            PreparedStatement pst = con.prepareStatement("select * from user where id = (?);");
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
            PreparedStatement pst = con.prepareStatement("update user set username = ?, set email = ?, set address = ?, set phone = ?  where id = ?;");
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
            PreparedStatement pst = con.prepareStatement("insert into users (username, role, email, address, phone) values(?,'CUSTOMER',?,?,?);");
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
            PreparedStatement pst = con.prepareStatement("delete from user where id = ? ;");
            pst.setInt(1, userId);
            pst.executeUpdate();
        
        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return true;
=======

    public boolean checkUserByCredentials(UserCredentials userCredentials) {
        boolean userExists = false;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, userCredentials.getUsername());
            stmt.setString(2, userCredentials.getPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userExists;
>>>>>>> main
    }

}
