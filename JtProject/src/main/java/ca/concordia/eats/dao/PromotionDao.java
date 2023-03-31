package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Promotion;

import java.util.List;

public interface PromotionDao {
    public List<Promotion> fetchAllPromotions();
    public Promotion fetchPromotionById(int PromotionId);
    public Promotion createPromotion(Promotion promotion);
    public Promotion updatePromotion(Promotion promotion);
    public boolean removePromotionById(int PromotionId);
    public String getPromotionTypeByTypeId(int PromotionTypeId);
}
