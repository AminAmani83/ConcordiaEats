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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;
import ca.concordia.eats.dto.UserCredentials;
import ca.concordia.eats.dao.UserDao;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)

public class ProductDaoImplTest {
    private ProductDaoImpl productDao;
    private JdbcTemplate jdbcTemplate;
    private Connection con;

    @BeforeEach
    public void setUp() throws IOException {
        jdbcTemplate = mock(JdbcTemplate.class);
        con = mock(Connection.class);
        productDao = new ProductDaoImpl(jdbcTemplate);
        productDao.con = con;
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate = null;
        con = null;
        productDao = null;
    }

    @Test
    public void testFetchAllProducts() {
        when(jdbcTemplate.query(anyString(), any(ProductDaoImpl.ProductRowMapper.class))).thenReturn(getTestProducts());
        List<Product> products = productDao.fetchAllProducts();
        assertEquals(2, products.size());
        assertEquals("Product 1", products.get(0).getName());
        assertEquals("Product 2", products.get(1).getName());
    }

    private List<Product> getTestProducts() {
        Product product1 = new Product(1, "Product 1", "Description 1", "path/to/image1.jpg", 10.0f, 5, false, 0.0f, 4.5, new Category(1, "Category 1"));
        Product product2 = new Product(2, "Product 2", "Description 2", "path/to/image2.jpg", 15.0f, 10, true, 10.0f, 3.5, new Category(2, "Category 2"));
        return List.of(product1, product2);
    }
    
    @Override
    public Product createProduct(Product product) {
        String sql = "insert into product( name, description, imagePath, categoryid, price, salesCount, isOnSale, discountPercent)value(?,?,?,?,?,?,?,?)";
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int update = jdbcTemplate.update(
                conn -> {
                    // Pre-compiling SQL
                    PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    // Set parameters
                    preparedStatement.setString(1, product.getName());
                    preparedStatement.setString(2, product.getDescription());
                    preparedStatement.setString(3, product.getImagePath() == null ? "" : product.getImagePath());
                    Category category = product.getCategory();
                    if (category != null) {
                        preparedStatement.setInt(4, category.getId());
                    } else {
                        preparedStatement.setInt(4, 6);
                    }
                    preparedStatement.setFloat(5, product.getPrice());
                    preparedStatement.setInt(6, product.getSalesCount());
                    preparedStatement.setBoolean(7, product.isOnSale());
                    preparedStatement.setFloat(8, product.getDiscountPercent());
                    return preparedStatement;

                }, generatedKeyHolder);
        if (update == 1) {
            Integer id = generatedKeyHolder.getKey().intValue();
            product.setId(id);
            return product;
        } else {
            return null;
        }

    }
    
    @Test
    public void testUpdateProduct() {
        Category category = new Category(1, "Category 1");
        Product product = new Product(1, "Updated Product", "Updated Description", "path/to/updated_image.jpg", 15.0f, 10, true, 10.0f, 4.5, category);
        
        when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);

        Product updatedProduct = productDao.updateProduct(product);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals("Updated Description", updatedProduct.getDescription());
        assertEquals("path/to/updated_image.jpg", updatedProduct.getImagePath());
        assertEquals(15.0f, updatedProduct.getPrice());
        assertEquals(10, updatedProduct.getSalesCount());
        assertEquals(true, updatedProduct.isOnSale());
        assertEquals(10.0f, updatedProduct.getDiscountPercent());
        assertEquals(category.getId(), updatedProduct.getCategory().getId());
        assertEquals(category.getName(), updatedProduct.getCategory().getName());
    }

    @Test
    public void testRemoveProductById() {
        int productId = 1;
        
        when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);

        boolean isRemoved = productDao.removeProductById(productId);

        assertTrue(isRemoved);
    }
    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productDao = new ProductDaoImpl(mockJdbcTemplate, mockConnection);
    }

    @Test
    public void testFetchAllCategories() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt(1)).thenReturn(1, 2);
        when(mockResultSet.getString(2)).thenReturn("Category 1", "Category 2");

        List<Category> categories = productDao.fetchAllCategories();

        assertNotNull(categories);
        assertEquals(2, categories.size());
        assertEquals(1, categories.get(0).getId());
        assertEquals("Category 1", categories.get(0).getName());
        assertEquals(2, categories.get(1).getId());
        assertEquals("Category 2", categories.get(1).getName());
    }
    
    
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productDao = new ProductDaoImpl(mockJdbcTemplate, mockConnection);
    }

    @Test
    public void testCreateCategory() throws Exception {
        Category category = new Category(null, "New Category");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Category createdCategory = productDao.createCategory(category);

        assertNotNull(createdCategory);
        assertEquals("New Category", createdCategory.getName());

        verify(mockPreparedStatement).setString(1, category.getName());
        verify(mockPreparedStatement).executeUpdate();
    }
    
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productDao = new ProductDaoImpl(mockJdbcTemplate, mockConnection);
    }

    @Test
    public void testUpdateCategory() throws Exception {
        Category category = new Category(1, "Updated Category");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        Category updatedCategory = productDao.updateCategory(category);

        assertNotNull(updatedCategory);
        assertEquals(1, updatedCategory.getId());
        assertEquals("Updated Category", updatedCategory.getName());

        verify(mockPreparedStatement).setString(1, category.getName());
        verify(mockPreparedStatement).setInt(2, category.getId());
        verify(mockPreparedStatement).executeUpdate();
    }
    
    @Override
    public boolean removeCategoryById(int categoryId) {
        int rowsAffected = 0;
        try {
            PreparedStatement pst = con.prepareStatement("delete from category where id = ? ;");
            pst.setInt(1, categoryId);
            rowsAffected = pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return rowsAffected == 1;
    }
    
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productDao = new ProductDaoImpl(mockJdbcTemplate, mockConnection);
    }

    @Test
    public void testMakeFavorite() throws Exception {
        int customerId = 1;
        int productId = 2;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDao.makeFavorite(customerId, productId);

        verify(mockPreparedStatement).setInt(1, customerId);
        verify(mockPreparedStatement).setInt(2, productId);
        verify(mockPreparedStatement).executeUpdate();
    }
    @Override
    public void removeFavorite(int customerId, int productId) {
        try {
            PreparedStatement pst = con.prepareStatement("delete from favorite where userId=? and productId=?;");
            pst.setInt(1, customerId);
            pst.setInt(2, productId);
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
    }
    
    @Mock
    private Connection mockConnection;
    @Mock
    private Statement mockStatement;
    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productDao = new ProductDaoImpl(mockJdbcTemplate, mockConnection);
    }

    @Test
    public void testFetchCustomerFavoriteProductIds() throws Exception {
        int customerId = 1;
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt(1)).thenReturn(2, 3);

        List<Integer> favoriteProductIds = productDao.fetchCustomerFavoriteProductIds(customerId);

        assertEquals(2, favoriteProductIds.size());
        assertEquals(2, favoriteProductIds.get(0));
        assertEquals(3, favoriteProductIds.get(1));
        verify(mockStatement).executeQuery("select productId from favorite where userId=" + customerId + ";");
    }

	}

	@Mock
	private Connection mockConnection;
	@Mock
	private PreparedStatement mockPreparedStatement;
	
	@BeforeEach
	public void setUp() {
	    MockitoAnnotations.openMocks(this);
	    productDao = new ProductDaoImpl(mockJdbcTemplate, mockConnection);
	}
	
	@Test
	public void testRemoveAllFavoritesByCustomerId() throws Exception {
	    int customerId = 1;
	    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
	    when(mockPreparedStatement.executeUpdate()).thenReturn(1);
	
	    productDao.removeAllFavoritesByCustomerId(customerId);
	
	    verify(mockPreparedStatement).setInt(1, customerId);
	    verify(mockPreparedStatement).executeUpdate();
	}
	
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productDao = new ProductDaoImpl(mockJdbcTemplate, mockConnection);
    }

    @Test
    public void testFetchRatingByProductIdAndCustomerId() throws Exception {
        int customerId = 1;
        int productId = 2;
        int rating = 4;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(rating);

        int fetchedRating = productDao.fetchRatingByProductIdAndCustomerId(customerId, productId);

        assertEquals(rating, fetchedRating);
        verify(mockPreparedStatement).setInt(1, customerId);
        verify(mockPreparedStatement).setInt(2, productId);
        verify(mockPreparedStatement).executeQuery();
    }
    
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productDao = new ProductDaoImpl(mockJdbcTemplate, mockConnection);
    }

    @Test
    public void testInsertNewRating() throws Exception {
        int customerId = 1;
        int productId = 2;
        int rating = 4;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDao.insertNewRating(customerId, productId, rating);

        verify(mockPreparedStatement).setInt(1, customerId);
        verify(mockPreparedStatement).setInt(2, productId);
        verify(mockPreparedStatement).setInt(3, rating);
        verify(mockPreparedStatement).executeUpdate();
    }
    
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productDao = new ProductDaoImpl(mockJdbcTemplate, mockConnection);
    }

    @Test
    public void testUpdateCurrentRating() throws Exception {
        int customerId = 1;
        int productId = 2;
        int rating = 4;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        productDao.updateCurrentRating(customerId, productId, rating);

        verify(mockPreparedStatement).setInt(1, rating);
        verify(mockPreparedStatement).setInt(2, customerId);
        verify(mockPreparedStatement).setInt(3, productId);
        verify(mockPreparedStatement).executeUpdate();
    }
	
    @Override
    public void rateProduct(int customerId, int productId, int rating) {
        try {
            int currentRating = fetchRatingByProductIdAndCustomerId(customerId, productId);

            if (currentRating == -1) {
                insertNewRating(customerId, productId, rating);
            } else if (currentRating >= 0 & currentRating <= 5) {
                updateCurrentRating(customerId, productId, rating);
            } else {
                System.out.println("currentRating value is outside valid bounds.");
            }

        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
    }
    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        productDao = new ProductDaoImpl();
        productDao.con = mockConnection;
    }

    @Test
    public void testSearch() throws SQLException {
        // Set up the test data
        String query = "apple";
        int userId = 1;
        List<Product> expectedProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Apple iPhone");
        product1.setPrice(999.99f);
        product1.setDescription("The latest and greatest iPhone from Apple");
        product1.setImagePath("/path/to/image.jpg");
        product1.setRating(4.5);
        expectedProducts.add(product1);
        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Apple MacBook");
        product2.setPrice(1999.99f);
        product2.setDescription("The ultimate laptop for professionals");
        product2.setImagePath("/path/to/image2.jpg");
        product2.setRating(4.8);
        expectedProducts.add(product2);

        // Set up the mock objects
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("name")).thenReturn("Apple iPhone", "Apple MacBook");
        when(mockResultSet.getFloat("price")).thenReturn(999.99f, 1999.99f);
        when(mockResultSet.getString("description")).thenReturn("The latest and greatest iPhone from Apple", "The ultimate laptop for professionals");
        when(mockResultSet.getString("imagePath")).thenReturn("/path/to/image.jpg", "/path/to/image2.jpg");
        when(productDao.calculateAvgProductRating(1)).thenReturn(4.5);
        when(productDao.calculateAvgProductRating(2)).thenReturn(4.8);

        // Call the method under test
        List<Product> actualProducts = productDao.search(query, userId);

        // Verify the results
        verify(mockPreparedStatement).setString(1, "%apple%");
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(2)).getInt("id");
        verify(mockResultSet, times(2)).getString("name");
        verify(mockResultSet, times(2)).getFloat("price");
        verify(mockResultSet, times(2)).getString("description");
        verify(mockResultSet, times(2)).getString("imagePath");
        verify(productDao, times(2)).calculateAvgProductRating(anyInt());
        assertEquals(expectedProducts, actualProducts);
    }
}

public SearchHistory saveSearchHistoryToDatabase(String SearchQuery, int userId) throws SQLException {
    User user = new User();
    SearchHistory searchHistory = new SearchHistory();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    String sql = "INSERT INTO search_history(userId, phrase, timeStamp) VALUES (?, ?,?);";
    PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    stmt.setInt(1, userId);
    stmt.setString(2, SearchQuery);
    stmt.setTimestamp(3, timestamp);
    int rowsAffected = stmt.executeUpdate();
    if (rowsAffected == 0) {
        throw new SQLException("Creating promotion failed, no rows affected.");
    }
    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
            // searchHistory.setSearchHistoryId(generatedKeys.getInt(1));
        } else {
            throw new SQLException("Creating promotion failed, no ID obtained.");
        }
    }
    return searchHistory;
}

@Test
public void testCalculateAvgProductRating() throws SQLException {
    // Mock the database connection
    Connection mockConnection = Mockito.mock(Connection.class);
    Statement mockStatement = Mockito.mock(Statement.class);
    ResultSet mockResultSet = Mockito.mock(ResultSet.class);
    Mockito.when(mockConnection.createStatement()).thenReturn(mockStatement);
    Mockito.when(mockStatement.executeQuery(Mockito.anyString())).thenReturn(mockResultSet);
    Mockito.when(mockResultSet.next()).thenReturn(true);
    Mockito.when(mockResultSet.getDouble(1)).thenReturn(3.5);

    // Create an instance of the class to test
    ProductDAOImpl productDAO = new ProductDAOImpl(mockConnection);

    // Call the method and assert the result
    Double avgRating = productDAO.calculateAvgProductRating(1);
    assertEquals(3.5, avgRating, 0.001);
}

			private ProductDAOImpl productDAO;
			
			@Mock
			private Connection mockConnection;
			
			@Mock
			private PreparedStatement mockPreparedStatement;
			
			@Before
			public void setUp() throws Exception {
			    MockitoAnnotations.initMocks(this);
			    productDAO = new ProductDAOImpl(mockConnection);
			}
			
			@Test
			public void testRemoveAllRatingsByCustomerId() throws Exception {
			    int customerId = 1;
			
			    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
			
			    productDAO.removeAllRatingsByCustomerId(customerId);
			
			    verify(mockPreparedStatement).setInt(1, customerId);
			    verify(mockPreparedStatement).executeUpdate();
			}
			
		    @Mock
		    private Connection con;

		    @Mock
		    private Statement stmt;

		    @Mock
		    private ResultSet rs;

		    @InjectMocks
		    private YourClass yourClass;

		    @Before
		    public void setUp() throws Exception {
		        MockitoAnnotations.initMocks(this);
		    }

		    @Test
		    public void testFetchAllProductSumSalesQuantity() throws Exception {
		        // mock the result set
		        when(rs.next()).thenReturn(true).thenReturn(false);
		        when(rs.getInt(1)).thenReturn(1);
		        when(rs.getInt(2)).thenReturn(10);

		        // mock the statement and connection
		        when(stmt.executeQuery(anyString())).thenReturn(rs);
		        when(con.createStatement()).thenReturn(stmt);

		        // test the method
		        Map<Integer, Integer> result = yourClass.fetchAllProductSumSalesQuantity();
		        Map<Integer, Integer> expected = new HashMap<>();
		        expected.put(1, 10);
		        assertEquals(expected, result);
		    }
		    
		    @Override
		    public Map<Integer, Double> fetchAllProductAvgRatings(){
		    	Map<Integer, Double> productAvgRatings = new HashMap<Integer, Double>();
		        try {
		        	List<Product> products = fetchAllProducts();
		        	for (Product p : products) {
		            	productAvgRatings.put(p.getId(), calculateAvgProductRating(p.getId()));
		            }
		        } catch (Exception ex) {
		            System.out.println("Exception Occurred: " + ex.getMessage());
		        }
		        return productAvgRatings;
		    }
}
