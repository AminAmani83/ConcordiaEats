package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Promotion;
import ca.concordia.eats.dto.PromotionType;
import ca.concordia.eats.utils.ConnectionUtil;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PromotionDaoImpl implements PromotionDao {
    private Connection con;

    public PromotionDaoImpl() throws IOException {
        this.con = ConnectionUtil.getConnection();
    }

    @Override
    public List<Promotion> fetchAllPromotions() throws DAOException {
        List<Promotion> allPromotions = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT promotion.id, promotion.startDate, promotion.endDate, promotionType.type" +
                    " FROM promotion, promotion_type"+
                    "WHERE promotionTypeId = promotion_type.id");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Promotion promotion = mapResultSetToPromotion(rs);
                allPromotions.add(promotion);
            }
        } catch (SQLException e) {
            throw new DAOException("Error fetching all promotions", e);
        }
        return allPromotions;
    }

    @Override
    public Promotion fetchPromotionById(int PromotionId) throws DAOException {
        Promotion promotion = null;
        String sql = "SELECT promotion.id, promotion.startDate, promotion.endDate, promotionType.type" +
                " FROM promotion, promotion_type"+
                "WHERE promotion.id = ? AND promotionTypeId = promotion_type.id";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, PromotionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                promotion = mapResultSetToPromotion(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("Error fetching promotion by id: " + PromotionId, e);
        }
        return promotion;
    }


    @Override
    public Promotion createPromotion(Promotion promotion) throws DAOException {
        String sql = "INSERT INTO promotions (id, startDate, endDate, promotionTypeId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, promotion.getPromotionId());
            stmt.setObject(2, promotion.getPromotionStartDate());
            stmt.setObject(3, promotion.getPromotionEndDate());
            stmt.setInt(4, promotion.getPromotionType().getId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new DAOException("Creating promotion failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    promotion.setPromotionId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating promotion failed, no ID obtained.");
                }
            }
        } catch (SQLException | DAOException e) {
            throw new DAOException("Error creating promotion", e);
        }
        return promotion;
    }


    @Override
    public Promotion updatePromotion(Promotion promotion) throws DAOException {
        String sql = "UPDATE promotions" +
                " SET startDate = ?, endDate = ?, promotionTypeId = ? " +
                "WHERE promotion.id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setObject(1, promotion.getPromotionStartDate());
            stmt.setObject(2, promotion.getPromotionEndDate());
            stmt.setObject(3, promotion.getPromotionType().getId());
            stmt.setInt(4, promotion.getPromotionId());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new DAOException("Updating promotion failed, no rows affected.");
            }
        } catch (SQLException | DAOException e) {
            throw new DAOException("Error updating promotion.", e);
        }
        return promotion;
    }


    @Override
    public boolean removePromotion(int promotionId) throws DAOException {
        String sql = "DELETE FROM promotions WHERE promotion_id = ?";
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
        promotion.setPromotionId(rs.getInt(1));
        Date startDate = rs.getDate(2);
        LocalDateTime localStartDateTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        promotion.setPromotionStartDate(localStartDateTime);
        Date endDate = rs.getDate(3);
        LocalDateTime localEndDateTime = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        promotion.setPromotionEndDate(localEndDateTime);
        promotion.getPromotionType().setType(rs.getString(4));
        return promotion;
    }

    @Override
    public List<PromotionType> getAllPromotionTypes() throws DAOException {
        List<PromotionType> promotionTypes = new ArrayList<>();
        String query = "SELECT id, type FROM promotion_type";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                PromotionType promotionType = new PromotionType();
                promotionType.setId(rs.getInt("id"));
                promotionType.setType(rs.getString("type"));
                promotionTypes.add(promotionType);
            }
        } catch (SQLException e) {
            throw new DAOException("Error retrieving promotion types", e);
        }
        return promotionTypes;
    }

    @Override
    public PromotionType getPromotionTypeById(int id) throws DAOException {
        String query = "SELECT type FROM promotion_type WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PromotionType promotionType = new PromotionType();
                    promotionType.setId(id);
                    promotionType.setType(rs.getString("type"));
                    return promotionType;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error retrieving promotion type with id " + id, e);
        }
    }

    @Override
    public PromotionType createPromotionType(PromotionType promotionType) throws DAOException {
        String query = "INSERT INTO promotion_type (id, type) VALUES (?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, promotionType.getId());
            stmt.setString(2, promotionType.getType());
            int numRowsAffected = stmt.executeUpdate();
            if (numRowsAffected == 1) {
                return promotionType;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DAOException("Error creating promotion type " + promotionType, e);
        }
    }

    @Override
    public void updatePromotionType(PromotionType promotionType) throws DAOException {
        String query = "UPDATE promotion_type SET type = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, promotionType.getType());
            stmt.setInt(2, promotionType.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error updating promotion type " + promotionType, e);
        }
    }

    @Override
    public void deletePromotionType(int id) throws DAOException {
        String query = "DELETE FROM promotion_type WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error deleting promotion type with id " + id, e);
        }
    }

}
