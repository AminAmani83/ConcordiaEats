package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class ProductDaoImplTest {

    private ProductDaoImpl productDaoImpl;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    Connection mockConnection;

    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        productDaoImpl = new ProductDaoImpl(jdbcTemplate, mockConnection);
    }


    // Categories

    @Test
    public void testFetchAllCategories() throws Exception {
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt(1)).thenReturn(1, 2);
        when(mockResultSet.getString(2)).thenReturn("Category 1", "Category 2");

        List<Category> categories = productDaoImpl.fetchAllCategories();

        assertNotNull(categories);
        assertEquals(2, categories.size());
        assertEquals(1, categories.get(0).getId());
        assertEquals("Category 1", categories.get(0).getName());
        assertEquals(2, categories.get(1).getId());
        assertEquals("Category 2", categories.get(1).getName());
    }

    @Test
    public void testCreateCategory() throws Exception {
        Category category = new Category(0, "New Category");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        Category createdCategory = productDaoImpl.createCategory(category);

        assertNotNull(createdCategory);
        assertEquals("New Category", createdCategory.getName());

        verify(mockConnection).prepareStatement("insert into category(name) values(?);");
        verify(mockStatement).setString(1, category.getName());
        verify(mockStatement).executeUpdate();
    }

    @Test
    public void testUpdateCategory() throws Exception {
        Category category = new Category(1, "Updated Category");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        Category updatedCategory = productDaoImpl.updateCategory(category);

        assertNotNull(updatedCategory);
        assertEquals(1, updatedCategory.getId());
        assertEquals("Updated Category", updatedCategory.getName());

        verify(mockConnection).prepareStatement("update category set name = ? where id = ?");
        verify(mockStatement).setString(1, category.getName());
        verify(mockStatement).setInt(2, category.getId());
        verify(mockStatement).executeUpdate();
    }

    @Test
    public void testRemoveCategory() throws Exception {
        Category category = new Category(1, "Category to be removed");
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        boolean rowRemoved = productDaoImpl.removeCategoryById(category.getId());

        assertTrue(rowRemoved);

        verify(mockConnection).prepareStatement("delete from category where id = ? ;");
        verify(mockStatement).setInt(1, 1);
        verify(mockStatement).executeUpdate();
    }


    // Favorites

    @Test
    public void testMakeFavorite() throws Exception {
        int customerId = 1;
        int productId = 2;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        productDaoImpl.makeFavorite(customerId, productId);

        verify(mockConnection).prepareStatement("insert into favorite values (?, ?);");
        verify(mockStatement).setInt(1, customerId);
        verify(mockStatement).setInt(2, productId);
        verify(mockStatement).executeUpdate();
    }

    @Test
    void testRemoveFavorite() throws SQLException {
        int customerId = 1;
        int productId = 2;
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeUpdate()).thenReturn(1);

        boolean rowRemoved = productDaoImpl.removeFavorite(customerId, productId);

        assertTrue(rowRemoved);

        verify(mockConnection).prepareStatement("delete from favorite where userId=? and productId=?;");
        verify(mockStatement).setInt(1, customerId);
        verify(mockStatement).setInt(2, productId);
        verify(mockStatement).executeUpdate();
    }

    @Test
    void testFetchCustomerFavoriteProductIds() throws Exception {
        int customerId = 1;
        List<Integer> expectedProductIds = Arrays.asList(2, 3, 4);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, true, false);
        when(mockResultSet.getInt(1)).thenReturn(2, 3, 4);

        List<Integer> actualProductIds = productDaoImpl.fetchCustomerFavoriteProductIds(customerId);

        assertEquals(expectedProductIds, actualProductIds);
        verify(mockConnection).createStatement();
        verify(mockStatement).executeQuery("select productId from favorite where userId=" + customerId + ";");
        verify(mockResultSet, times(4)).next();
    }

}