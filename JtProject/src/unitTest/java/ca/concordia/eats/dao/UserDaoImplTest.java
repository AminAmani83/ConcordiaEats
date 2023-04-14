package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;
import ca.concordia.eats.dto.UserCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class UserDaoImplTest {

    private UserDaoImpl userDaoImpl;

    @Mock
    private Connection mockConnection;

    @Mock
    private ProductDao productDao;

    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;


    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.openMocks(this);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        userDaoImpl = new UserDaoImpl(productDao, mockConnection);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockConnection.createStatement()).thenReturn(mockStatement);

        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
    }


    // TODO: try again
    @Test
    public void testGetAllUsers() {
        List<User> allUsers = userDaoImpl.getAllUsers();
        assertEquals(2, allUsers.size());
        assertEquals("user1", allUsers.get(0).getUsername());
        assertEquals("role1", allUsers.get(0).getRole());
        assertEquals("user1@test.com", allUsers.get(0).getEmail());
        assertEquals("user2", allUsers.get(1).getUsername());
        assertEquals("role2", allUsers.get(1).getRole());
        assertEquals("user2@test.com", allUsers.get(1).getEmail());
    }
    
    @Test
    public void testGetUserById() throws Exception {
        // set up the mock objects to return dummy data
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(1);
        Mockito.when(mockResultSet.getString(2)).thenReturn("testuser");
        Mockito.when(mockResultSet.getString(3)).thenReturn("user");
        Mockito.when(mockResultSet.getString(4)).thenReturn("testuser@example.com");

        // call the getUserById method
        User user = userDaoImpl.getUserById(1);

        // verify that the mock objects were called with the correct parameters
        Mockito.verify(mockConnection).prepareStatement("select id, username, role, email from user where id = (?);");
        Mockito.verify(mockStatement).setInt(1, 1);
        Mockito.verify(mockStatement).executeQuery();
        Mockito.verify(mockResultSet).getInt(1);
        Mockito.verify(mockResultSet).getString(2);
        Mockito.verify(mockResultSet).getString(3);
        Mockito.verify(mockResultSet).getString(4);

        // check that the returned User object has the correct values
        assertEquals(1, user.getUserId());
        assertEquals("testuser", user.getUsername());
        assertEquals("user", user.getRole());
        assertEquals("testuser@example.com", user.getEmail());
    }
    
    @Test
    public void testUpdateUser() throws SQLException {
        User user = new User(1, "John", "admin", "john@example.com");

        User updatedUser = userDaoImpl.updateUser(user);

        verify(mockConnection).prepareStatement("update user set username = ?, email = ? where id = ?");
        verify(mockStatement).setString(1, "John");
        verify(mockStatement).setString(2, "john@example.com");
        verify(mockStatement).setInt(3, 1);
        verify(mockStatement).executeUpdate();

        assertEquals(user, updatedUser);
    }
    
    @Test
    public void testCreateUser() throws SQLException {
        // Create a mock User object
        User user = new User(1, "password123", "user", "john.doe@example.com");
        
        // Call the method under test
        User result = userDaoImpl.createUser(user);
        
        // Verify that the PreparedStatement was called with the correct parameters
        verify(mockStatement).setString(1, user.getUsername());
        verify(mockStatement).setString(2, user.getPassword());
        verify(mockStatement).setString(3, user.getRole());
        verify(mockStatement).setString(4, user.getEmail());
        verify(mockStatement).executeUpdate();
        
        // Verify that the method returns the expected result
        assertEquals(user, result);
    }

    // TODO: try again
    @Test
    void testGetAllCustomers() throws SQLException {
        // Set up mock objects to return test data
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 3);
        when(mockResultSet.getString("username")).thenReturn("user1", "user3");
        when(mockResultSet.getString("role")).thenReturn("CUSTOMER", "CUSTOMER");
        when(mockResultSet.getString("address")).thenReturn("123 Main St", "789 Elm St");
        when(mockResultSet.getString("email")).thenReturn("user1@test.com", "user3@test.com");
        when(mockResultSet.getString("phone")).thenReturn("123-456-7890", "123-456-7892");

        // Call the method under test
        List<Customer> customers = userDaoImpl.getAllCustomers();

        // Verify the results
        verify(mockStatement).executeQuery("select id, username, role, address, email, phone from user where role='CUSTOMER';");

        assertEquals(2, customers.size());
        assertEquals(1, customers.get(0).getUserId());
        assertEquals("user1", customers.get(0).getUsername());
        assertEquals("CUSTOMER", customers.get(0).getRole());
        assertEquals("123 Main St", customers.get(0).getAddress());
        assertEquals("user1@test.com", customers.get(0).getEmail());
        assertEquals("123-456-7890", customers.get(0).getPhone());
        assertEquals(3, customers.get(1).getUserId());
        assertEquals("user3", customers.get(1).getUsername());
        assertEquals("CUSTOMER", customers.get(1).getRole());
        assertEquals("789 Elm St", customers.get(1).getAddress());
        assertEquals("user3@test.com", customers.get(1).getEmail());
        assertEquals("123-456-7892", customers.get(1).getPhone());
    }

    @Test
    public void testGetCustomerById() throws SQLException {
        // Arrange
        int userId = 1;
        String expectedUsername = "testuser";
        String expectedRole = "CUSTOMER";
        String expectedEmail = "testuser@example.com";
        String expectedAddress = "123 Main St";
        String expectedPhone = "555-555-5555";

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(userId);
        when(mockResultSet.getString("username")).thenReturn(expectedUsername);
        when(mockResultSet.getString("role")).thenReturn(expectedRole);
        when(mockResultSet.getString("email")).thenReturn(expectedEmail);
        when(mockResultSet.getString("address")).thenReturn(expectedAddress);
        when(mockResultSet.getString("phone")).thenReturn(expectedPhone);

        // Act
        Customer result = userDaoImpl.getCustomerById(userId);

        // Assert
        verify(mockConnection).prepareStatement("select id, username, role, email, address, phone from user where id = (?);");
        verify(mockStatement).setInt(1, userId);
        verify(mockStatement).executeQuery();

        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(expectedUsername, result.getUsername());
        assertEquals(expectedRole, result.getRole());
        assertEquals(expectedEmail, result.getEmail());
        assertEquals(expectedAddress, result.getAddress());
        assertEquals(expectedPhone, result.getPhone());
    }
 
    @Test
    public void testGetCustomerByCredential() throws SQLException {
        // Arrange
        String username = "testuser";
        String password = "testpassword";
        String expectedUsername = "testuser";
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("username")).thenReturn(expectedUsername);

        // Act
        UserCredentials userCredentials = new UserCredentials(username, password);
        Customer result = userDaoImpl.getCustomerByCredential(userCredentials);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUsername, result.getUsername());
    }

    @Test
    public void testCreateCustomer() throws SQLException {
        // Arrange
        Customer expectedCustomer = new Customer();
        expectedCustomer.setUsername("testuser");
        expectedCustomer.setPassword("password123");
        expectedCustomer.setRole("CUSTOMER");
        expectedCustomer.setEmail("testuser@example.com");
        expectedCustomer.setAddress("123 Main St");
        expectedCustomer.setPhone("555-555-5555");

        // Mock the executeUpdate() method to return 1 (success)
        when(mockStatement.executeUpdate()).thenReturn(1);

        // Act
        Customer result = userDaoImpl.createCustomer(expectedCustomer);

        // Assert
        assertNotNull(result);
        assertEquals(expectedCustomer.getUsername(), result.getUsername());
        assertEquals(expectedCustomer.getPassword(), result.getPassword());
        assertEquals(expectedCustomer.getRole(), result.getRole());
        assertEquals(expectedCustomer.getEmail(), result.getEmail());
        assertEquals(expectedCustomer.getAddress(), result.getAddress());
        assertEquals(expectedCustomer.getPhone(), result.getPhone());
    }


    @Test
    public void testFetchUserCredentialsById() throws SQLException {
        // Set up the mock ResultSet
        Mockito.when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockResultSet.getString("username")).thenReturn("testuser");

        // Act
        UserCredentials userCredentials = new UserCredentials("testuser", "password123");
        Customer result = userDaoImpl.getCustomerByCredential(userCredentials);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }
    
    @Test
    public void testUpdateUserProfile() throws SQLException {
        // Arrange
        int userId = 1;
        String expectedUsername = "testuser";
        String expectedPassword = "password123";
        String expectedEmail = "testuser@example.com";
        String expectedAddress = "123 Main St";
        String expectedPhone = "555-555-5555";
        Customer customer = new Customer(userId, expectedUsername, "CUSTOMER", expectedAddress, expectedEmail, expectedPhone);

        UserCredentials userCredentials = new UserCredentials(expectedUsername, expectedPassword);

        // Act
        userDaoImpl.updateUserProfile(customer, userCredentials);

        // Assert
        verify(mockStatement).setString(1, expectedUsername);
        verify(mockStatement).setString(2, expectedPassword);
        verify(mockStatement).setString(3, expectedEmail);
        verify(mockStatement).setString(4, expectedPhone);
        verify(mockStatement).setString(5, expectedAddress);
        verify(mockStatement).setInt(6, userId);
        verify(mockStatement).executeUpdate();
    }

    @Test
    public void testCheckUserByCredentials() throws SQLException {
        // Arrange
        UserCredentials userCredentials = new UserCredentials("testuser", "password123");

        // Mock
        Mockito.when(mockResultSet.next()).thenReturn(true);

        // Act
        boolean result = userDaoImpl.checkUserByCredentials(userCredentials);

        // Assert
        assertTrue(result);
        Mockito.verify(mockConnection).prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
        Mockito.verify(mockStatement).setString(1, "testuser");
        Mockito.verify(mockStatement).setString(2, "password123");
        Mockito.verify(mockStatement).executeQuery();
        Mockito.verify(mockResultSet).next();
    }
    
    @Test
    public void testFetchCustomerData() throws SQLException {
        // Arrange
        UserCredentials userCredentials = new UserCredentials("testuser", "password123");
        Customer expectedCustomer = new Customer();
        expectedCustomer.setUserId(1);
        expectedCustomer.setUsername("testuser");
        expectedCustomer.setRole("CUSTOMER");
        expectedCustomer.setEmail("testuser@example.com");
        expectedCustomer.setAddress("123 Main St");
        expectedCustomer.setPhone("555-1234");

        // Set up the mock database to return a customer for the specified user credentials
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getInt("id")).thenReturn(expectedCustomer.getUserId());
        Mockito.when(mockResultSet.getString("username")).thenReturn(expectedCustomer.getUsername());
        Mockito.when(mockResultSet.getString("role")).thenReturn(expectedCustomer.getRole());
        Mockito.when(mockResultSet.getString("email")).thenReturn(expectedCustomer.getEmail());
        Mockito.when(mockResultSet.getString("address")).thenReturn(expectedCustomer.getAddress());
        Mockito.when(mockResultSet.getString("phone")).thenReturn(expectedCustomer.getPhone());

        // Act
        Customer actualCustomer = userDaoImpl.fetchCustomerData(userCredentials);

        // Assert
        assertNotNull(actualCustomer);
        assertEquals(expectedCustomer.getUserId(), actualCustomer.getUserId());
        assertEquals(expectedCustomer.getUsername(), actualCustomer.getUsername());
        assertEquals(expectedCustomer.getRole(), actualCustomer.getRole());
        assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail());
        assertEquals(expectedCustomer.getAddress(), actualCustomer.getAddress());
        assertEquals(expectedCustomer.getPhone(), actualCustomer.getPhone());
        Mockito.verify(mockConnection).prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
        Mockito.verify(mockStatement).setString(1, "testuser");
        Mockito.verify(mockStatement).setString(2, "password123");
        Mockito.verify(mockStatement).executeQuery();
        Mockito.verify(mockResultSet).next();
    }
    
    @Test
    public void testFetchCustomerSearchedProduct() throws SQLException {
        // Arrange
        UserCredentials userCredentials = new UserCredentials("testuser", "password123");
        Customer customer = new Customer();
        customer.setUserId(1);
        
        // Create mock objects
        ProductDao mockProductDao = Mockito.mock(ProductDao.class);

        // Set up the mock database to return a result for the specified user credentials
        Mockito.when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockResultSet.getInt("userId")).thenReturn(1);
        Mockito.when(mockResultSet.getString("phrase")).thenReturn("test");

        // Set up the mock productDao to return a list of products
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setName("test product 1");
        products.add(product1);
        Product product2 = new Product();
        product2.setName("test product 2");
        products.add(product2);
        doReturn(products).when(mockProductDao).fetchAllProducts();

        // Act
        List<Product> result = userDaoImpl.fetchCustomerSearchedProduct(customer);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        Mockito.verify(mockConnection).prepareStatement("SELECT * FROM search_history WHERE userId = ?;");
        Mockito.verify(mockStatement).setInt(1, 1);
        Mockito.verify(mockStatement).executeQuery();
        Mockito.verify(mockResultSet, Mockito.times(2)).next();
        Mockito.verify(mockResultSet).getInt("userId");
        Mockito.verify(mockResultSet).getString("phrase");
        Mockito.verify(mockProductDao).fetchAllProducts();
    }
}
