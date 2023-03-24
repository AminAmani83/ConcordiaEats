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
		int updatedRow = productDaoImpl.createProduct(product);
		assertTrue( updatedRow == 1, "the impacted row is equal to 1");
	}

}
