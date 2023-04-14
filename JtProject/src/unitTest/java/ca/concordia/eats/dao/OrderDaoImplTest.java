package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Basket;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class OrderDaoImplTest {

    private OrderDaoImpl orderDaoImpl;

    @Mock
    private JdbcTemplate mockJdbcTemplate;

    @Mock
    private Connection mockConnection;

    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    private User testUser;
    private Basket testBasket;
    private List<Product> testProducts;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        orderDaoImpl = new OrderDaoImpl(mockJdbcTemplate, mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        testUser = new User();
        testUser.setUserId(1);

        testProducts = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setPrice(10);
        product1.setSalesCount(2);
        product1.setOnSale(false);
        product1.setDiscountPercent(0);
        testProducts.add(product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setPrice(5);
        product2.setSalesCount(1);
        product2.setOnSale(true);
        product2.setDiscountPercent(20);
        testProducts.add(product2);

        testBasket = new Basket();
        testBasket.setTotalPrice(17);
        testBasket.setLineItems(testProducts);
    }

    @Test
    public void testRemovePurchaseDetailsByPurchaseId() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException {
        // Given
        List<Integer> testPurchaseIds = Arrays.asList(1, 2, 3);

        // Access private method using reflection
        Method removePurchaseDetailsByPurchaseIdMethod = OrderDaoImpl.class.getDeclaredMethod("removePurchaseDetailsByPurchaseId", List.class);
        removePurchaseDetailsByPurchaseIdMethod.setAccessible(true);

        // When
        removePurchaseDetailsByPurchaseIdMethod.invoke(orderDaoImpl, testPurchaseIds);

        // Then
        verify(mockStatement, times(testPurchaseIds.size())).setInt(eq(1), anyInt());
        verify(mockStatement, times(testPurchaseIds.size())).executeUpdate();
    }

    @Test
    public void testRemovePurchasesByCustomerId() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException {
        // Given
        int testCustomerId = 1;

        // Access private method using reflection
        Method removePurchasesByCustomerIdMethod = OrderDaoImpl.class.getDeclaredMethod("removePurchasesByCustomerId", int.class);
        removePurchasesByCustomerIdMethod.setAccessible(true);

        // When
        removePurchasesByCustomerIdMethod.invoke(orderDaoImpl, testCustomerId);

        // Then
        verify(mockStatement, times(1)).setInt(1, testCustomerId);
        verify(mockStatement, times(1)).executeUpdate();
    }

}