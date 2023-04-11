package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Promotion;
import ca.concordia.eats.dto.Purchase;
import org.springframework.stereotype.Repository;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Repository
public class PromotionDaoImpl implements PromotionDao {

    Connection con;

    public PromotionDaoImpl() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                .replaceAll("%20", " ");
        String dbConfigPath = rootPath + "db.properties";

        FileReader reader = new FileReader(dbConfigPath);
        Properties dbProperties = new Properties();
        dbProperties.load(reader);

        try {
            this.con = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties.getProperty("username"), dbProperties.getProperty("password"));
        } catch (Exception e) {
            System.out.println("Error connecting to the DB: " + e.getMessage());
        }
    }
    

    @Override
    public List<Promotion> fetchAllPromotions() throws DAOException {
        List<Promotion> allPromotions = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT promotion.id, promotion.startDate, promotion.endDate, promotion.name, promotion.type " +
                    " FROM promotion");
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
        String sql = "SELECT promotion.id, promotion.startDate, promotion.endDate, promotion.name," +
                " promotion.type" +
                " FROM promotion"+
                " WHERE promotion.id = ?";
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
        String sql = "INSERT INTO promotion (id, startDate, endDate, name, type) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, promotion.getId());
            stmt.setObject(2, promotion.getStartDate());
            stmt.setObject(3, promotion.getEndDate());
            stmt.setString(4, promotion.getName());
            stmt.setString(5, promotion.getType());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new DAOException("Creating promotion failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    promotion.setId(generatedKeys.getInt(1));
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
        String sql = "UPDATE promotion" +
                " SET startDate = ?, endDate = ?, name = ? , type = ?" +
                " WHERE promotion.id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setObject(1, promotion.getStartDate());
            stmt.setObject(2, promotion.getEndDate());
            stmt.setString(3, promotion.getName());
            stmt.setString(4, promotion.getType());
            stmt.setInt(5, promotion.getId());
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
        removePromotionFromPurchases(promotionId);
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

    @Override
    public boolean removePromotionFromPurchases(int promotionId) {
        String sql = "UPDATE purchase" +
                " SET promotionId = NULL" +
                " WHERE purchase.id = ? AND promotionId = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            List<Purchase> allPurchases = fetchAllPurchases();
            for (Purchase purchase: allPurchases) {
                int purchaseId = purchase.getPurchaseId();
                stmt.setInt(1, purchaseId);
                stmt.setInt(2, promotionId);
                stmt.executeUpdate();
            }
        } catch (SQLException | DAOException e) {
            throw new DAOException("Error updating purchase.", e);
        }
        return true;
    }

    public List<Purchase> fetchAllPurchases() {
        List<Purchase> allPurchases = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT id, timeStamp, total_price FROM purchase");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Purchase purchase = new Purchase();
                purchase.setPurchaseId(rs.getInt(1));
                // purchase.setTimeStamp(rs.getLong(2));
                purchase.setTotalPrice(rs.getFloat(3));
                allPurchases.add(purchase);
            }
        } catch (SQLException e) {
            throw new DAOException("Error fetching all purchases", e);
        }
        return allPurchases;
    }

}
