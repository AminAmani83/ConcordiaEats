package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Promotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PromotionDaoImplTest {

    private PromotionDaoImpl promotionDaoImpl;

    @Mock
    private Connection mockConnection;

    @Mock
    ProductDaoImpl productDaoImpl;

    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        promotionDaoImpl = new PromotionDaoImpl(mockConnection, productDaoImpl);
    }

    @Test
    public void testFetchAllPromotions() throws SQLException, DAOException {
        // Mocking ResultSet data
        when(mockResultSet.next()).thenReturn(true, false); // Mocks one promotion in the ResultSet
        when(mockResultSet.getInt(1)).thenReturn(1);
        when(mockResultSet.getDate(2)).thenReturn(new java.sql.Date(new Date().getTime()));
        when(mockResultSet.getDate(3)).thenReturn(new java.sql.Date(new Date().getTime()));
        when(mockResultSet.getString(4)).thenReturn("Test Promotion");
        when(mockResultSet.getString(5)).thenReturn("Discount");

        // Test fetchAllPromotions
        List<Promotion> promotions = promotionDaoImpl.fetchAllPromotions();

        // Assert the results
        assertEquals(1, promotions.size());
        Promotion promotion = promotions.get(0);
        assertEquals(1, promotion.getId());
        assertEquals("Test Promotion", promotion.getName());
        assertEquals("Discount", promotion.getType());
    }


}