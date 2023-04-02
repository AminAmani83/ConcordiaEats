package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.Promotion;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PromotionDaoImpl implements PromotionDao {
    private Connection con;

    @Override
    public List<Promotion> fetchAllPromotions() {
        List<Promotion> allPromotions = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM promotion");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Promotion promotion = mapResultSetToPromotion(rs);
                allPromotions.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPromotions;
    }

    @Override
    public Promotion fetchPromotionById(int PromotionId) {
        Promotion promotion = null;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM promotion" +
                    "WHERE promotion.id = ?");
            stmt.setInt(1, PromotionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                promotion = mapResultSetToPromotion(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotion;
    }

    @Override
    public Promotion createPromotion(Promotion promotion) throws SQLException {
        String sql = "INSERT INTO promotions (promotion_type_id, promotion_start_date, promotion_end_date) VALUES (?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, promotion.getPromotionTypeId());
        stmt.setObject(2, promotion.getPromotionStartDate());
        stmt.setObject(3, promotion.getPromotionEndDate());
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Creating promotion failed, no rows affected.");
        }
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                promotion.setPromotionId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating promotion failed, no ID obtained.");
            }
        }
        return promotion;
    }

    @Override
    public Promotion updatePromotion(Promotion promotion) throws SQLException {
        String sql = "UPDATE promotions SET promotion_type_id = ?, promotion_start_date = ?, promotion_end_date = ? WHERE promotion_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, promotion.getPromotionTypeId());
        stmt.setObject(2, promotion.getPromotionStartDate());
        stmt.setObject(3, promotion.getPromotionEndDate());
        stmt.setInt(4, promotion.getPromotionId());
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Updating promotion failed, no rows affected.");
        }
        return promotion;
    }

    @Override
    public boolean removePromotionById(int promotionId) throws SQLException {
        String sql = "DELETE FROM promotions WHERE promotion_id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, promotionId);
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Deleting promotion failed, no rows affected.");
        }
        return true;
    }

    @Override
    public String getPromotionTypeByTypeId(int PromotionTypeId) {
        String promotionType = null;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT ? FROM promotion_type " +
                    "WHERE promotion_type.id = ?");
            stmt.setString(1, "type");
            stmt.setInt(1, PromotionTypeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                promotionType = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotionType;
    }

    @Override
    public Promotion mapResultSetToPromotion(ResultSet rs) {
        Promotion promotion = null;
        try {
            promotion = new Promotion();
            promotion.setPromotionId(rs.getInt(1));
            Date startDate = rs.getDate(2);
            LocalDateTime localStartDateTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            promotion.setPromotionStartDate(localStartDateTime);
            Date endDate = rs.getDate(3);
            LocalDateTime localEndDateTime = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            promotion.setPromotionEndDate(localEndDateTime);
            promotion.setPromotionTypeId(rs.getInt(4));
            int promotionTypeId = promotion.getPromotionTypeId();
            promotion.setPromotionType(getPromotionTypeByTypeId(promotionTypeId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotion;
    }

}
