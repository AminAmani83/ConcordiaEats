package ca.concordia.eats.dao;

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
    
    
}