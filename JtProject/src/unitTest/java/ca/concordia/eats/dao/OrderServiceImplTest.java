package ca.concordia.eats.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;

import ca.concordia.eats.dto.*;
import ca.concordia.eats.dao.*;

class OrderServiceImplTest {
    
	@Mock
    private Basket mockBasket;
    
    @Mock
    private Product mockProduct;

    private OrderServiceImpl orderService;
    
    @BeforeEach
    void setUp() throws Exception {
    	MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl();
    }
    
    @Test
    public void testAddProduct() {
        // create a mock Product and Basket
        Product mockProduct = mock(Product.class);
        Basket mockBasket = mock(Basket.class);
        
        // create an instance of OrderServiceImpl and call the addProduct method
        OrderServiceImpl orderService = new OrderServiceImpl();
        orderService.addProduct(mockProduct, mockBasket);
        
        // verify that the addProduct method was called on the mock Basket with the mock Product as an argument
        verify(mockBasket).addProduct(mockProduct);
    }
    
    @Test
    public void testRemoveProduct() {
        // create a mock product and basket
        Product mockProduct = mock(Product.class);
        Basket mockBasket = mock(Basket.class);
        
        // create an instance of the service to test
        OrderServiceImpl orderService = new OrderServiceImpl();
        
        // call the method to be tested
        orderService.removeProduct(mockProduct, mockBasket);
        
        // verify that the removeProduct method was called on the basket with the correct product
        verify(mockBasket, times(1)).removeProduct(mockProduct);
    }
    
    @Test
    public void testGetProductsInCart() {
        // Create a mock Basket object
        Basket mockBasket = mock(Basket.class);

        // Create a mock list of products
        List<Product> mockProductList = new ArrayList<Product>();
        mockProductList.add(new Product(1, "Product 1", "Description 1", "/test/product1.png", 10.0f, 10, false, 0.0f, null));
        mockProductList.add(new Product(2, "Product 2", "Description 2", "/test/product2.png", 20.0f, 20, false, 0.0f, null));

        // Stub the Basket.getProductsInCart() method to return the mock product list
        when(mockBasket.getProductsInCart()).thenReturn(mockProductList);

        // Create an instance of the OrderServiceImpl and call the getProductsInCart() method
        OrderServiceImpl orderService = new OrderServiceImpl();
        List<Product> productsInCart = orderService.getProductsInCart(mockBasket);

        // Assert that the mock product list is equal to the returned product list 
        assertEquals(productsInCart, mockProductList);
    }
    
    @Test
    public void testGetTotal() {
        // Create a mock Basket object
        Basket mockBasket = mock(Basket.class);

        // Stub the Basket.getTotal() method to return a fixed value
        when(mockBasket.getTotal()).thenReturn(50.0f);

        // Create an instance of the OrderServiceImpl and call the getTotal() method
        OrderServiceImpl orderService = new OrderServiceImpl();
        float total = orderService.getTotal(mockBasket);

        // Assert that the returned total is equal to the expected value
        assertEquals(50.0f, total, 0.0f);
    }
    
    @Test
    public void testGetTaxes() {
        // Create a mock Basket object
        Basket mockBasket = mock(Basket.class);

        // Set up stub for the Basket.getTaxes() method to return 5.0
        when(mockBasket.getTaxes()).thenReturn(5.0);

        // Create an instance of the OrderServiceImpl and call the getTaxes() method
        OrderServiceImpl orderService = new OrderServiceImpl();
        double taxes = orderService.getTaxes(mockBasket);

        // Verify that the Basket.getTaxes() method was called once
        verify(mockBasket, times(1)).getTaxes();

        // Assert that the returned taxes value is equal to the stubbed value of 5.0
        assertEquals(5.0, taxes, 0.0);
    }
    
    @Test
    public void testGetDelivery() {
        // Create a mock Basket object
        Basket mockBasket = mock(Basket.class);

        // Stub the Basket.getDelivery() method to return a delivery cost
        when(mockBasket.getDelivery()).thenReturn(5.0);

        // Create an instance of the OrderServiceImpl and call the getDelivery() method
        OrderServiceImpl orderService = new OrderServiceImpl();
        double deliveryCost = orderService.getDelivery(mockBasket);

        // Assert that the returned delivery cost is equal to the expected value
        assertEquals(5.0, deliveryCost, 0.001);
    }
    
    @Test
    public void testUpdateProduct() {
        // create a mock product and basket
        Product mockProduct = mock(Product.class);
        Basket mockBasket = mock(Basket.class);

        // create an instance of the service to test
        OrderServiceImpl orderService = new OrderServiceImpl();

        // call the method to be tested
        orderService.updateProduct(mockProduct, 2, mockBasket);

        // verify that the updateProduct method was called on the basket with the correct product and quantity
        verify(mockBasket, times(1)).updateProduct(mockProduct, 2);
    }
}
