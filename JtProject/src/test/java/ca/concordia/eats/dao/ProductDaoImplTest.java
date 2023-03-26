package ca.concordia.eats.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;

@SpringBootTest
class ProductDaoImplTest {

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
	
	@Test
	void testDeleteProduct() {
		Category category = new Category(6, "category6");
		Product product = new Product(17, "banana", "description", "1.jpg", 11.0f, 20, true, 4.2f, 0.8, category );
		Product newProduct = productDaoImpl.createProduct(product);
		boolean ifDeleted = productDaoImpl.removeProductById(newProduct.getId());
		assertTrue( ifDeleted, "the product is deleted successfully");
	}
	
}
