package ca.concordia.eats.dao;

<<<<<<< HEAD
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.Promotion;
import ca.concordia.eats.dto.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PromotionDaoImplTest {

    @InjectMocks
    private PromotionDaoImpl promotionDao;

    @Mock
    private Connection con;

    @Mock
    private PreparedStatement stmt;

    @Mock
    private ResultSet rs;

    @BeforeEach
    public void setUp() throws IOException, SQLException {
        when(con.prepareStatement(any(String.class))).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
    }

    @Test
    public void testFetchAllPromotions() throws SQLException, DAOException {
        // Mocking ResultSet data
        when(rs.next()).thenReturn(true, false); // Mocks one promotion in the ResultSet
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getDate("startDate")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(rs.getDate("endDate")).thenReturn(new java.sql.Date(new Date().getTime()));
        when(rs.getString("name")).thenReturn("Test Promotion");
        when(rs.getString("type")).thenReturn("Discount");

        // Test fetchAllPromotions
        List<Promotion> promotions = promotionDao.fetchAllPromotions();

        // Assert the results
        assertEquals(1, promotions.size());
        Promotion promotion = promotions.get(0);
        assertEquals(1, promotion.getId());
        assertEquals("Test Promotion", promotion.getName());
        assertEquals("Discount", promotion.getType());
    }
    @Override
    public boolean removePromotion(int promotionId) throws DAOException {
        String sql = "DELETE FROM promotion WHERE promotion.id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, promotionId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new DAOException("Deleting promotion failed, no rows affected.");
            }
        } catch (SQLException | DAOException e) {
            throw new DAOException("Failed to remove promotion", e);
        }
        return true;
    }
    
    @Override
    public Promotion mapResultSetToPromotion(ResultSet rs) throws SQLException {
        Promotion promotion = null;
        promotion = new Promotion();
        promotion.setId(rs.getInt(1));
        Date startDate = rs.getDate(2);
        promotion.setStartDate(startDate);
        Date endDate = rs.getDate(3);
        promotion.setEndDate(endDate);
        promotion.setName(rs.getString(4));
        promotion.setType(rs.getString(5));
        return promotion;
    }
    
    
=======
import ca.concordia.eats.dto.Promotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
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
        when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockStatement);
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

    @Test
    public void testFetchPromotionById() throws SQLException, DAOException {
        // Given
        int promotionId = 1;
        when(mockResultSet.next()).thenReturn(true);
        Promotion expectedPromotion = new Promotion();
        expectedPromotion.setId(1);
        expectedPromotion.setStartDate(new Date());
        expectedPromotion.setEndDate(new Date());
        expectedPromotion.setName("Promotion 1");
        expectedPromotion.setType("Discount");

        when(mockResultSet.getInt(1)).thenReturn(expectedPromotion.getId());
        when(mockResultSet.getDate(2)).thenReturn(new java.sql.Date(new Date().getTime()));
        when(mockResultSet.getDate(3)).thenReturn(new java.sql.Date(new Date().getTime()));
        when(mockResultSet.getString(4)).thenReturn(expectedPromotion.getName());
        when(mockResultSet.getString(5)).thenReturn(expectedPromotion.getType());

        // When
        Promotion actualPromotion = promotionDaoImpl.fetchPromotionById(promotionId);

        // Then
        assertEquals(expectedPromotion.getId(), actualPromotion.getId());
        assertEquals(expectedPromotion.getName(), actualPromotion.getName());
        assertEquals(expectedPromotion.getType(), actualPromotion.getType());
    }

    @Mock
    private ResultSet mockGeneratedKeys;

    @Test
    public void testCreatePromotion() throws SQLException, DAOException {
        // Given
        when(mockStatement.executeUpdate()).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockGeneratedKeys);
        when(mockGeneratedKeys.next()).thenReturn(true);
        when(mockGeneratedKeys.getInt(1)).thenReturn(1);

        Promotion promotion = new Promotion();
        promotion.setStartDate(new Date());
        promotion.setEndDate(new Date());
        promotion.setName("Promotion 1");
        promotion.setType("Discount");

        // When
        Promotion actualPromotion = promotionDaoImpl.createPromotion(promotion);

        // Then
        assertEquals(1, actualPromotion.getId());
    }

    @Test
    public void testUpdatePromotion() throws DAOException, SQLException {
        // Given
        when(mockStatement.executeUpdate()).thenReturn(1);

        Promotion promotion = new Promotion();
        promotion.setId(1);
        promotion.setStartDate(new Date());
        promotion.setEndDate(new Date());
        promotion.setName("Promotion 1");
        promotion.setType("Discount");

        // When
        Promotion actualPromotion = promotionDaoImpl.updatePromotion(promotion);

        // Then
        assertEquals(promotion, actualPromotion);
    }

    @Test
    public void testRemovePromotion() throws SQLException, DAOException {
        // Given
        when(mockStatement.executeUpdate()).thenReturn(1);
        int promotionId = 1;

        // When
        boolean result = promotionDaoImpl.removePromotion(promotionId);

        // Then
        assertTrue(result);
    }
>>>>>>> refs/heads/main
}