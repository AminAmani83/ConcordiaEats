package ca.concordia.eats.dao;

import ca.concordia.eats.dto.User;
import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.UserCredentials;
import ca.concordia.eats.utils.ConnectionUtil;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;

import javax.servlet.http.HttpSession;

@Repository
public class UserDaoImpl implements UserDao {

    private ProductDao productDao;
    private Connection con;

    public UserDaoImpl() throws IOException {
        this.con = ConnectionUtil.getConnection();
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

        } catch (Exception ex) {
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
            PreparedStatement pst = con.prepareStatement("insert into user (username, password, role, email) values(?,?,?,?);");
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getRole());
            pst.setString(4, user.getEmail());
            pst.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return user;
    }


    /**
     * Need to retrieve this information for all Customers:
     * - userId (int), username (string), email (string), address (string), phone (string).
     * <p>
     * Important: make sure to only select users whose role is 'customer'
     */
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> allCustomers = new LinkedList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select id, username, role, address, email, phone from user where role='CUSTOMER';");
            while (rs.next()) {
                allCustomers.add(new Customer(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)));
            }
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }

        return allCustomers;
    }


    @Override
    public Customer getCustomerById(int userId) {

        Customer customer = null;

        try {
            PreparedStatement pst = con.prepareStatement("select id, username, role, email, address, phone from user where id = (?);");
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                customer = new Customer(rs.getInt(1),
                                        rs.getString(2),
                                        rs.getString(3),
                                        rs.getString(4),
                                        rs.getString(5),
                                        rs.getString(6));
            }

        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }

        return customer;
    }

    @Override
    public Customer getCustomerByCredential(UserCredentials userCredentials) {
        Customer customer = null;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            stmt.setString(1, userCredentials.getUsername());
            stmt.setString(2, userCredentials.getPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setUsername(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return customer;
    }

    /**
     * Only allow a Customer to update its email, address or phone.
     * 'username' and 'password' cannot be updated
     * 'role' cannot be updated.
     */
    @Override
    public Customer updateCustomer(Customer customer) {
        try {
            PreparedStatement pst = con.prepareStatement("update user set email = ?, address = ?, phone = ?  where id = ?;");
            pst.setString(1, customer.getEmail());
            pst.setString(2, customer.getAddress());
            pst.setString(3, customer.getPhone());
            pst.setInt(4, customer.getUserId());
            pst.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return customer;
    }


    @Override
    public Customer createCustomer(Customer customer) {
        try {
            PreparedStatement pst = con.prepareStatement("insert into user (username, password, role, email, address, phone) values(?,?,'CUSTOMER',?,?,?);");
            pst.setString(1, customer.getUsername());
            pst.setString(1, customer.getPassword());
            pst.setString(2, customer.getEmail());
            pst.setString(3, customer.getAddress());
            pst.setString(4, customer.getPhone());
            pst.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return customer;
    }

    /**
     * Helper method to removeCustomer
     */
    @Override
    public boolean checkUserIsCustomer(UserCredentials userCredentials) {

        boolean isCustomer = true;
        User user = fetchCustomerData(userCredentials);

        if (user.getRole().equalsIgnoreCase("ADMIN")) {
            isCustomer = false;
        }
        return isCustomer;
    }

    @Override
    public UserCredentials fetchUserCredentialsById(int userId) {
        try {
            PreparedStatement pst = con.prepareStatement("select username, password from user where id = ?;");
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new UserCredentials(rs.getString(1), rs.getString(2));
            }
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public void updateUserProfile(Customer customer, UserCredentials userCredentials) {
        try {
            PreparedStatement pst = con.prepareStatement("update user set username = ?, password = ?, email = ?, phone = ?, address = ? where id = ?;");
            pst.setString(1, userCredentials.getUsername());
            pst.setString(2, userCredentials.getPassword());
            pst.setString(3, customer.getEmail());
            pst.setString(4, customer.getPhone());
            pst.setString(5, customer.getAddress());
            pst.setInt(6, customer.getUserId());
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
    }


    @Override
    public boolean removeCustomerById(int customerId) {

        boolean customerRemoved = false;
        UserCredentials userCredentials = fetchUserCredentialsById(customerId);
        boolean isCustomer = checkUserIsCustomer(userCredentials);

        if (isCustomer) {
            try {

                PreparedStatement pst = con.prepareStatement("delete from user where username = ? and password = ?;");
                pst.setString(1, userCredentials.getUsername());
                pst.setString(2, userCredentials.getPassword());
                pst.executeUpdate();

                customerRemoved = true;

            } catch (Exception ex) {
                System.out.println("Exception Occurred: " + ex.getMessage());
            }
        }
        return customerRemoved;
    }


    public boolean checkUserByCredentials(UserCredentials userCredentials) {
        boolean userExists = false;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
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
    }

    @Override
    public Customer fetchCustomerData(UserCredentials userCredentials) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            stmt.setString(1, userCredentials.getUsername());
            stmt.setString(2, userCredentials.getPassword());
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return null;

            Customer customer = new Customer();
            customer.setUserId(rs.getInt("id"));
            customer.setUsername(rs.getString("username"));
            customer.setRole(rs.getString("role"));
            customer.setEmail(rs.getString("email"));
            customer.setAddress(rs.getString("address"));
            customer.setPhone(rs.getString("phone"));
            return customer;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

		
	@Override
	public List<Product> fetchCustomerSearchedProduct(User user)     {
		List<Product> products = productDao.fetchAllProducts();
		List<Product>  searchedProducts = new ArrayList<>();
        try {
            // Create a statement
            String query = "SELECT * FROM search_history WHERE userId = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, user.getUserId());
            
            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery(query);
            
            // Create a HashMap to hold the search phrases and their counts
            
            // Loop through the result set and add each search phrase to the HashMap
            while (resultSet.next()) {
        		String  searchedphrase = resultSet.getString("phrase");
                int userId = resultSet.getInt("userId");
                	  for (Product product : products) {
                          if (product.getName().toLowerCase().contains(searchedphrase.toLowerCase())) {
                        	  searchedProducts.add(product);

                          }
                      
            }

            }}
        catch (SQLException ex) {
            ex.printStackTrace();
        }
  		return searchedProducts;
    }


}
