package ca.concordia.eats.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductDaoImplTest {
	   private ProductDaoImpl productDaoImplTest;
	    private Connection con;

	   
		@BeforeEach
	    void setUp() throws IOException {
	        con = mock(Connection.class);
	        productDaoImplTest = new ProductDaoImpl(con);
	    }
	@Autowired
    private ProductDao productDaoImpl;
	
	@Test
	void testFetchAllProducts() {
		List<Product> allProducts = productDaoImpl.fetchAllProducts();
		assertTrue( allProducts.size()>1, "the product size larger than 1");
	}
	
	@Test
	void testFetchProductById() {
		Product product = productDaoImpl.fetchProductById(14);
		assertTrue( product != null, "the product size is equal to 1");
	}
	
	@Test
	void testCreateProduct() {
		Category category = new Category(6, "category6");
		Product product = new Product(17, "banana", "description", "1.jpg", 11.0f, 20, true, 4.2f, 0.8, category );
		Product createdProduct = productDaoImpl.createProduct(product);
		assertTrue( createdProduct != null, "the product is created successfully");
	}
	
	@Test
	void testUpdateProduct() {
		Product product = productDaoImpl.fetchProductById(14);
		product.setDescription("update-description");
		Product updateProduct = productDaoImpl.updateProduct(product);
		assertTrue( updateProduct != null, "the product is created successfully");
		assertTrue( "update-description".equals(updateProduct.getDescription()), "the product's descrption is updated successfully");
	}
	/*
	@Test
	void testDeleteProduct() {
		Category category = new Category(6, "category6");
		Product product = new Product(17, "banana", "description", "1.jpg", 11.0f, 20, true, 4.2f, 0.8, category );
		Product newProduct = productDaoImpl.createProduct(product);
		boolean ifDeleted = productDaoImpl.removeProductById(newProduct.getId());
		assertTrue( ifDeleted, "the product is deleted successfully");
	}
	@Test
	void testDeleteProduct() {
		Category category = new Category(6, "category6");
		Product product = new Product(17, "banana", "description", "1.jpg", 11.0f, 20, true, 4.2f, 0.8, category );
		Product newProduct = productDaoImpl.createProduct(product);
		boolean ifDeleted = productDaoImpl.removeProductById(newProduct.getId());
		assertTrue( ifDeleted, "the product is deleted successfully");
	}
	*/
	
	//Best Deals Module
	@Test
	 void testFetchAllProductAvgRatings() throws Exception {
        // Arrange
        Statement stmt = mock(Statement.class);
        ResultSet rs = mock(ResultSet.class);

        when(con.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery("select productId, Avg(rating) as AvgRating from rating where Group by productId;")).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt(1)).thenReturn(1, 2);
        when(rs.getFloat(2)).thenReturn(4.5f, 3.0f);

        // Act
        Map<Integer, Float> productAvgRatings = productDaoImplTest.fetchAllProductAvgRatings();

        // Assert
        assertEquals(2, productAvgRatings.size());
        assertEquals(4.5f, productAvgRatings.get(1));
        assertEquals(3.0f, productAvgRatings.get(2));

        verify(con, times(1)).createStatement();
        verify(stmt, times(1)).executeQuery("select productId, Avg(rating) as AvgRating from rating where Group by productId;");
        verify(rs, times(3)).next();
        verify(rs, times(2)).getInt(1);
        verify(rs, times(2)).getFloat(2);
    }
	
}
