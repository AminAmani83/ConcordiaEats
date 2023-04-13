package ca.concordia.eats.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.quality.Strictness;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;
import ca.concordia.eats.dto.UserCredentials;
import ca.concordia.eats.dao.UserDao;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class UserDaoImplTest {

    @Mock
    private Connection con;
    private Connection mockConnection;

    @InjectMocks
    private UserDaoImpl userDao;

    @BeforeEach
    public void setup() throws SQLException {
    	MockitoAnnotations.openMocks(this);
        Statement stmt = con.createStatement();
        stmt.executeUpdate("create table user (id int primary key, username varchar(255), role varchar(255), email varchar(255))");
        stmt.executeUpdate("insert into user (id, username, role, email) values (1, 'user1', 'role1', 'user1@test.com')");
        stmt.executeUpdate("insert into user (id, username, role, email) values (2, 'user2', 'role2', 'user2@test.com')");
    }

    @AfterEach
    public void cleanup() throws SQLException {
        Statement stmt = con.createStatement();
        stmt.executeUpdate("drop table user");
    }
    
    @Test
    public void testGetAllUsers() throws SQLException {
        List<User> allUsers = userDao.getAllUsers();
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
        // mock the PreparedStatement and ResultSet objects
        PreparedStatement mockStatement = Mockito.mock(PreparedStatement.class);
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);
        Connection mockConnection = mock(Connection.class);


        // set up the mock objects to return dummy data
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockStatement);
        Mockito.when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(1);
        Mockito.when(mockResultSet.getString(2)).thenReturn("testuser");
        Mockito.when(mockResultSet.getString(3)).thenReturn("user");
        Mockito.when(mockResultSet.getString(4)).thenReturn("testuser@example.com");

        // call the getUserById method
        User user = userDao.getUserById(1);

        // verify that the mock objects were called with the correct parameters
        Mockito.verify(mockConnection).prepareStatement("select id, username, role, email from user where id = (?);");
        Mockito.verify(mockStatement).setInt(1, 1);
        Mockito.verify(mockStatement).executeQuery();
        Mockito.verify(mockResultSet).next();
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
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        User user = new User(1, "John", "john@example.com", "admin");
        UserDao userDao = new UserDaoImpl(mockConnection);

        User updatedUser = userDao.updateUser(user);

        verify(mockConnection).prepareStatement("update user set username = ?, email = ? where id = ?");
        verify(mockStatement).setString(1, "John");
        verify(mockStatement).setString(2, "john@example.com");
        verify(mockStatement).setInt(3, 1);
        verify(mockStatement).executeUpdate();

        assertEquals(user, updatedUser);
    }
    
    @Test
    public void testCreateUser() throws SQLException {
        // Create a mock PreparedStatement
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        
        // Create a mock User object
        User user = new User(1, "password123", "user", "john.doe@example.com");
        
        // Call the method under test
        User result = userDao.createUser(user);
        
        // Verify that the PreparedStatement was called with the correct parameters
        verify(mockStatement).setString(1, user.getUsername());
        verify(mockStatement).setString(2, user.getPassword());
        verify(mockStatement).setString(3, user.getRole());
        verify(mockStatement).setString(4, user.getEmail());
        verify(mockStatement).executeUpdate();
        
        // Verify that the method returns the expected result
        assertEquals(user, result);
    }
    
    @Test
    void testGetAllCustomers() throws SQLException {
        // Create mock objects
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Set up mock objects to return test data
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 3);
        when(mockResultSet.getString("username")).thenReturn("user1", "user3");
        when(mockResultSet.getString("role")).thenReturn("CUSTOMER");
        when(mockResultSet.getString("address")).thenReturn("123 Main St", "789 Elm St");
        when(mockResultSet.getString("email")).thenReturn("user1@test.com", "user3@test.com");
        when(mockResultSet.getString("phone")).thenReturn("123-456-7890", "123-456-7892");

        // Create instance of UserDaoImpl with mockConnection
        UserDaoImpl userDao = new UserDaoImpl(mockConnection);

        // Call the method under test
        List<Customer> customers = userDao.getAllCustomers();

        // Verify the results
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

        // Create a mock Connection and PreparedStatement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);

        // Mock the prepareStatement() method to return the PreparedStatement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        // Mock the ResultSet returned by the executeQuery() method
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(userId);
        when(mockResultSet.getString("username")).thenReturn(expectedUsername);
        when(mockResultSet.getString("role")).thenReturn(expectedRole);
        when(mockResultSet.getString("email")).thenReturn(expectedEmail);
        when(mockResultSet.getString("address")).thenReturn(expectedAddress);
        when(mockResultSet.getString("phone")).thenReturn(expectedPhone);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        // Create a UserDaoImpl object using the mock Connection
        UserDao userDao = new UserDaoImpl(mockConnection);

        // Act
        Customer result = userDao.getCustomerById(userId);

        // Assert
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
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("username")).thenReturn(expectedUsername);
        UserDao userDao = new UserDaoImpl(mockConnection);

        // Act
        UserCredentials userCredentials = new UserCredentials(username, password);
        Customer result = userDao.getCustomerByCredential(userCredentials);

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

        // Create a mock Connection and PreparedStatement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);

        // Mock the prepareStatement() method to return the PreparedStatement
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);

        // Mock the executeUpdate() method to return 1 (success)
        when(mockStatement.executeUpdate()).thenReturn(1);

        // Create a UserDaoImpl object using the mock Connection
        UserDao userDao = new UserDaoImpl(mockConnection);

        // Act
        Customer result = userDao.createCustomer(expectedCustomer);

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
    public void testCheckUserIsCustomer() throws SQLException {
        // Arrange
        UserCredentials credentials = new UserCredentials("testuser", "password123");
        String expectedRole = "CUSTOMER";

        // Mock the fetchCustomerData() method to return a user with the expected role
        Mockito.when(userDao.fetchCustomerData(credentials)).thenReturn((Customer) new User(1, "password123", expectedRole, "testuser@example.com", "123 Main St"));

        // Act
        boolean result = userDao.checkUserIsCustomer(credentials);

        // Assert
        assertTrue(result);
    }
    
    @Test
    public void testFetchUserCredentialsById() throws SQLException {
        // Arrange
        Connection mockConnection = Mockito.mock(Connection.class);
        UserDao userDao = new UserDaoImpl(mockConnection);

        // Set up the mock ResultSet
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);
        Mockito.when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockResultSet.getString("username")).thenReturn("testuser");

        // Set up the mock PreparedStatement
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);

        // Act
        UserCredentials userCredentials = new UserCredentials("testuser", "password123");
        Customer result = userDao.getCustomerByCredential(userCredentials);

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
        Customer customer = new Customer(userId, expectedPassword, expectedUsername, expectedEmail, expectedAddress, expectedPhone);
        UserCredentials userCredentials = new UserCredentials(expectedUsername, expectedPassword);

        // Mock Connection and PreparedStatement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockStatement);

        // Act
        userDao.updateUserProfile(customer, userCredentials);

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
    public void testRemoveCustomerById() throws SQLException {
        // Arrange
        int customerId = 1;

        // Create mock objects
        Connection mockConnection = Mockito.mock(Connection.class);
        PreparedStatement mockStmt1 = Mockito.mock(PreparedStatement.class);
        PreparedStatement mockStmt2 = Mockito.mock(PreparedStatement.class);
        ResultSet mockRs = Mockito.mock(ResultSet.class);

        // Set up the mock database to return a user credential for the specified customer ID
        Mockito.when(mockConnection.prepareStatement("select username, password from user where id = ?;")).thenReturn(mockStmt1);
        Mockito.when(mockStmt1.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true);
        Mockito.when(mockRs.getString("username")).thenReturn("testuser");
        Mockito.when(mockRs.getString("password")).thenReturn("password123");

        // Set up the mock database to indicate that the user is a customer
        Mockito.when(mockConnection.prepareStatement("select role from user where username = ? and password = ?;")).thenReturn(mockStmt2);
        Mockito.when(mockStmt2.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true);
        Mockito.when(mockRs.getString("role")).thenReturn("CUSTOMER");

        // Act
        boolean result = userDao.removeCustomerById(customerId);

        // Assert
        assertTrue(result);
        Mockito.verify(mockConnection).prepareStatement("select username, password from user where id = ?;");
        Mockito.verify(mockStmt1).setInt(1, customerId);
        Mockito.verify(mockStmt1).executeQuery();
        Mockito.verify(mockRs).next();
        Mockito.verify(mockRs).getString("username");
        Mockito.verify(mockRs).getString("password");
        Mockito.verify(mockConnection).prepareStatement("select role from user where username = ? and password = ?;");
        Mockito.verify(mockStmt2).setString(1, "testuser");
        Mockito.verify(mockStmt2).setString(2, "password123");
        Mockito.verify(mockStmt2).executeQuery();
        Mockito.verify(mockRs, Mockito.times(2)).next();
        Mockito.verify(mockRs).getString("role");
        Mockito.verify(mockConnection).prepareStatement("delete from user where username = ? and password = ?;");
        Mockito.verify(mockStmt2).setString(1, "testuser");
        Mockito.verify(mockStmt2).setString(2, "password123");
        Mockito.verify(mockStmt2).executeUpdate();
    }
    
    @Test
    public void testCheckUserByCredentials() throws SQLException {
        // Arrange
        UserCredentials userCredentials = new UserCredentials("testuser", "password123");

        // Create mock objects
        Connection mockConnection = Mockito.mock(Connection.class);
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);
        ResultSet mockRs = Mockito.mock(ResultSet.class);

        // Set up the mock database to return a result for the specified user credentials
        Mockito.when(mockConnection.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?")).thenReturn(mockStmt);
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true);

        // Create the UserDaoImpl instance with the mock connection
        UserDaoImpl userDao = new UserDaoImpl(mockConnection);

        // Act
        boolean result = userDao.checkUserByCredentials(userCredentials);

        // Assert
        assertTrue(result);
        Mockito.verify(mockConnection).prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
        Mockito.verify(mockStmt).setString(1, "testuser");
        Mockito.verify(mockStmt).setString(2, "password123");
        Mockito.verify(mockStmt).executeQuery();
        Mockito.verify(mockRs).next();
    }
    
    @Test
    public void testFetchCustomerData() throws SQLException {
        // Arrange
        UserCredentials userCredentials = new UserCredentials("testuser", "password123");
        Connection mockConnection = Mockito.mock(Connection.class);
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);
        ResultSet mockRs = Mockito.mock(ResultSet.class);
        Customer expectedCustomer = new Customer();
        expectedCustomer.setUserId(1);
        expectedCustomer.setUsername("testuser");
        expectedCustomer.setRole("CUSTOMER");
        expectedCustomer.setEmail("testuser@example.com");
        expectedCustomer.setAddress("123 Main St");
        expectedCustomer.setPhone("555-1234");

        // Set up the mock database to return a customer for the specified user credentials
        Mockito.when(mockConnection.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?")).thenReturn(mockStmt);
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true);
        Mockito.when(mockRs.getInt("id")).thenReturn(expectedCustomer.getUserId());
        Mockito.when(mockRs.getString("username")).thenReturn(expectedCustomer.getUsername());
        Mockito.when(mockRs.getString("role")).thenReturn(expectedCustomer.getRole());
        Mockito.when(mockRs.getString("email")).thenReturn(expectedCustomer.getEmail());
        Mockito.when(mockRs.getString("address")).thenReturn(expectedCustomer.getAddress());
        Mockito.when(mockRs.getString("phone")).thenReturn(expectedCustomer.getPhone());

        // Create a new UserDaoImpl instance with the mock connection
        UserDao userDao = new UserDaoImpl(mockConnection);

        // Act
        Customer actualCustomer = userDao.fetchCustomerData(userCredentials);

        // Assert
        assertNotNull(actualCustomer);
        assertEquals(expectedCustomer.getUserId(), actualCustomer.getUserId());
        assertEquals(expectedCustomer.getUsername(), actualCustomer.getUsername());
        assertEquals(expectedCustomer.getRole(), actualCustomer.getRole());
        assertEquals(expectedCustomer.getEmail(), actualCustomer.getEmail());
        assertEquals(expectedCustomer.getAddress(), actualCustomer.getAddress());
        assertEquals(expectedCustomer.getPhone(), actualCustomer.getPhone());
        Mockito.verify(mockConnection).prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
        Mockito.verify(mockStmt).setString(1, "testuser");
        Mockito.verify(mockStmt).setString(2, "password123");
        Mockito.verify(mockStmt).executeQuery();
        Mockito.verify(mockRs).next();
    }
    
    @Test
    public void testFetchCustomerSearchedProduct() throws SQLException {
        // Arrange
        UserCredentials userCredentials = new UserCredentials("testuser", "password123");
        Customer customer = new Customer();
        customer.setUserId(1);
        
        // Create mock objects
        Connection mockConnection = Mockito.mock(Connection.class);
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);
        ResultSet mockRs = Mockito.mock(ResultSet.class);
        ProductDao mockProductDao = Mockito.mock(ProductDao.class);

        // Set up the mock database to return a result for the specified user credentials
        Mockito.when(mockConnection.prepareStatement("SELECT * FROM search_history WHERE userId = ?;")).thenReturn(mockStmt);
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);
        Mockito.when(mockRs.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockRs.getInt("userId")).thenReturn(1);
        Mockito.when(mockRs.getString("phrase")).thenReturn("test");

        // Set up the mock productDao to return a list of products
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setName("test product 1");
        products.add(product1);
        Product product2 = new Product();
        product2.setName("test product 2");
        products.add(product2);
        Mockito.when(mockProductDao.fetchAllProducts()).thenReturn(products);

        // Create the UserDaoImpl instance with the mock connection and productDao
        UserDao userDao = new UserDaoImpl(mockConnection, mockProductDao);

        // Act
        List<Product> result = userDao.fetchCustomerSearchedProduct(customer);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        Mockito.verify(mockConnection).prepareStatement("SELECT * FROM search_history WHERE userId = ?;");
        Mockito.verify(mockStmt).setInt(1, 1);
        Mockito.verify(mockStmt).executeQuery();
        Mockito.verify(mockRs, Mockito.times(2)).next();
        Mockito.verify(mockRs).getInt("userId");
        Mockito.verify(mockRs).getString("phrase");
        Mockito.verify(mockProductDao).fetchAllProducts();
    }
}
