package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.Promotion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface PromotionDao {
    public List<Promotion> fetchAllPromotions();
    public Promotion fetchPromotionById(int PromotionId);
    public Promotion createPromotion(Promotion promotion) throws SQLException;
    public Promotion updatePromotion(Promotion promotion) throws SQLException;
    public boolean removePromotionById(int PromotionId) throws SQLException;
    public String getPromotionTypeByTypeId(int PromotionTypeId);
    public Promotion mapResultSetToPromotion(ResultSet rs);

}
