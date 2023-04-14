package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Basket;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private OrderDaoImpl orderDao;

    private User testUser;
    private Basket testBasket;
    private List<Product> testProducts;

    @BeforeEach
    public void setUp() {
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
        testBasket.setTotal(17);
        testBasket.setProductsInCart(testProducts);
    }

    @Test
    public void testMakeOrder() throws IOException {
        // Given
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        keyHolder.getKeyList().add(1);
        when(jdbcTemplate.update(any(), any(GeneratedKeyHolder.class))).thenReturn(1);

        // When
        orderDao.makeOrder(testUser, testBasket);

        // Then
        verify(jdbcTemplate, times(1)).update(anyString(), any(GeneratedKeyHolder.class));
        verify(jdbcTemplate, times(testProducts.size())).update(anyString());
    }
    

    @Test
    public void testFetchPurchaseIdsByCustomerId() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // Given
        int testCustomerId = 1;
        List<Integer> expectedPurchaseIds = Arrays.asList(1, 2, 3);

        PreparedStatement pst = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);
        when(rs.next()).thenReturn(true, true, true, false);
        when(rs.getInt(1)).thenReturn(1, 2, 3);
        when(pst.executeQuery()).thenReturn(rs);
        when(orderDao.con.prepareStatement(anyString())).thenReturn(pst);

        // Access private method using reflection
        Method fetchPurchaseIdsByCustomerIdMethod = OrderDaoImpl.class.getDeclaredMethod("fetchPurchaseIdsByCustomerId", int.class);
        fetchPurchaseIdsByCustomerIdMethod.setAccessible(true);

        // When
        List<Integer> result = (List<Integer>) fetchPurchaseIdsByCustomerIdMethod.invoke(orderDao, testCustomerId);

        // Then
        assertEquals(expectedPurchaseIds, result);
    }

    @Test
    public void testRemovePurchaseDetailsByPurchaseId() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // Given
        List<Integer> testPurchaseIds = Arrays.asList(1, 2, 3);
        PreparedStatement pst = mock(PreparedStatement.class);
        when(orderDao.con.prepareStatement(anyString())).thenReturn(pst);

        // Access private method using reflection
        Method removePurchaseDetailsByPurchaseIdMethod = OrderDaoImpl.class.getDeclaredMethod("removePurchaseDetailsByPurchaseId", List.class);
        removePurchaseDetailsByPurchaseIdMethod.setAccessible(true);

        // When
        removePurchaseDetailsByPurchaseIdMethod.invoke(orderDao, testPurchaseIds);

        // Then
        verify(pst, times(testPurchaseIds.size())).setInt(eq(1), anyInt());
        verify(pst, times(testPurchaseIds.size())).executeUpdate();
    }
    @Test
    public void testRemovePurchasesByCustomerId() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // Given
        int testCustomerId = 1;
        PreparedStatement pst = mock(PreparedStatement.class);
        when(orderDao.con.prepareStatement(anyString())).thenReturn(pst);

        // Access private method using reflection
        Method removePurchasesByCustomerIdMethod = OrderDaoImpl.class.getDeclaredMethod("removePurchasesByCustomerId", int.class);
        removePurchasesByCustomerIdMethod.setAccessible(true);

        // When
        removePurchasesByCustomerIdMethod.invoke(orderDao, testCustomerId);

        // Then
        verify(pst, times(1)).setInt(1, testCustomerId);
        verify(pst, times(1)).executeUpdate();
    }
    
    public void removeAllPurchasesByCustomerId(int customerId) {

        List<Integer> purchaseIds = fetchPurchaseIdsByCustomerId(customerId);
        removePurchaseDetailsByPurchaseId(purchaseIds);
        removePurchasesByCustomerId(customerId);
    }
}