package ca.concordia.eats;

import ca.concordia.eats.dao.*;
import ca.concordia.eats.dto.Category;
import ca.concordia.eats.service.ProductService;
import ca.concordia.eats.service.ProductServiceImpl;
import ca.concordia.eats.service.UserService;
import ca.concordia.eats.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


public class IntegrationTest {
    ProductService productService;
    UserService userService;

    @BeforeEach
    public void setUp() throws IOException {
        ProductDao productDao = new ProductDaoImpl(new JdbcTemplate());
        UserDao userDao = new UserDaoImpl();
        OrderDao orderDao = new OrderDaoImpl();
        productService = new ProductServiceImpl(productDao);
        userService = new UserServiceImpl(userDao, orderDao, productDao, productService);
    }

    @Test
    public void testStrategy1() {

        // Create Category
        String categoryName = "SpaceFood";
        Category createdCategory = productService.createCategory(new Category(0, categoryName));
        assertNotNull(createdCategory);
        assertEquals(categoryName, createdCategory.getName());

        // Fetch Category
        List<Category> allCategories = productService.fetchAllCategories();
        createdCategory = allCategories.stream().filter(cat -> cat.getName().equals(categoryName)).collect(Collectors.toList()).get(0);
        assertTrue(createdCategory.getId() != 0);

        // Update Category
        String categoryNewName = "Space Food";
        Category updatedCategory = productService.updateCategory(new Category(createdCategory.getId(), categoryNewName));
        assertNotNull(updatedCategory);
        assertEquals(categoryNewName, updatedCategory.getName());

        // Delete Category
        boolean removed = productService.removeCategoryById(updatedCategory.getId());
        assertTrue(removed);

        // Fetch Category to make sure it was actually deleted
        allCategories = productService.fetchAllCategories();
        List<Category> removedCategory = allCategories.stream().filter(cat -> cat.getName().equals(categoryName)).collect(Collectors.toList());
        assertTrue(removedCategory.isEmpty());

    }

}
