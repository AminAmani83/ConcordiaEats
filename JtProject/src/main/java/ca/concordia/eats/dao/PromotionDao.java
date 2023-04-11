package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Promotion;
import ca.concordia.eats.dto.Purchase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface PromotionDao {
    public List<Promotion> fetchAllPromotions() throws DAOException;
    public Promotion fetchPromotionById(int PromotionId) throws DAOException;
    public Promotion createPromotion(Promotion promotion) throws DAOException;
    public Promotion updatePromotion(Promotion promotion) throws DAOException;
    public boolean removePromotion(int PromotionId) throws DAOException;
    public Promotion mapResultSetToPromotion(ResultSet rs) throws SQLException;
    public boolean removePromotionFromPurchases (int promotionId);
    public List<Purchase> fetchAllPurchases();

}
