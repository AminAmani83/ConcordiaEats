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
            PreparedStatement stmt2 = con.prepareStatement("SELECT * FROM promotion_type " +
                    "WHERE promotion_type.id = promotion.promotionTypeId");
            ResultSet rs = stmt.executeQuery();
            ResultSet rs2 = stmt2.executeQuery();

            while (rs.next()) {
                Promotion promotion = new Promotion();
                promotion.setPromotionId(rs.getInt(1));
                Date startDate = rs.getDate(2);
                LocalDateTime localStartDateTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                promotion.setPromotionStartDate(localStartDateTime);
                Date endDate = rs.getDate(2);
                LocalDateTime localEndDateTime = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                promotion.setPromotionEndDate(localEndDateTime);
                promotion.setPromotionTypeId(rs2.getInt(1));
                allPromotions.add(promotion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPromotions;
    }

    @Override
    public Promotion fetchPromotionById(int PromotionId) {
        return null;
    }

    @Override
    public Promotion createPromotion(Promotion promotion) {
        return null;
    }

    @Override
    public Promotion updatePromotion(Promotion promotion) {
        return null;
    }

    @Override
    public boolean removePromotionById(int PromotionId) {
        return false;
    }

    @Override
    public String getPromotionTypeByTypeId(int PromotionTypeId) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT ? FROM promotion_type " +
                    "WHERE promotion_type.id = ?");
            stmt.setString(1, "type");
            stmt.setInt(1, PromotionTypeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String promotionType = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
