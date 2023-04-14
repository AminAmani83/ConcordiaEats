package ca.concordia.eats;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.service.ProductService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IntegrationTest {

    @InjectMocks
    private ProductService productService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategoryAndAddProduct() {
        Category category = new Category(1, "Electronics");
        when(productService.createCategory(category)).thenReturn(category);

        Category createdCategory = productService.createCategory(category);
        assertNotNull(createdCategory);

        Product product = new Product(1, "Laptop", "Dell Laptop", null, 1000, 1, false, 0, category);
        when(productService.createProduct(product)).thenReturn(product);

        Product createdProduct = productService.createProduct(product);
        assertNotNull(createdProduct);

        product.setCategory(createdCategory);
        productService.updateProduct(product);

        Product updatedProduct = productService.fetchProductById(product.getId());
        assertEquals(createdCategory.getId(), updatedProduct.getCategory().getId());
    }

}