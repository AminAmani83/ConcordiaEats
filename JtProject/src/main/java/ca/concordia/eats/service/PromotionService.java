package ca.concordia.eats.service;

import ca.concordia.eats.dto.Basket;
import ca.concordia.eats.dto.Promotion;

import java.util.List;

public interface PromotionService {
    public List<Promotion> getAllPromotions() throws ServiceException;
    public Promotion getPromotionById(int promotionId) throws ServiceException;
    public Promotion createPromotion(Promotion promotion) throws ServiceException;
    public Promotion updatePromotion(Promotion promotion) throws ServiceException;
    public boolean removePromotionById(int promotionId) throws ServiceException;
    List<Promotion> fetchAllActivePromotions() throws ServiceException;
}

