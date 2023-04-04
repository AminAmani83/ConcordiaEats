package ca.concordia.eats.service;

import ca.concordia.eats.dto.Promotion;
import ca.concordia.eats.dto.PromotionType;

import java.util.List;

public interface PromotionService {
    public List<Promotion> getAllPromotions() throws ServiceException;
    public Promotion getPromotionById(int promotionId) throws ServiceException;
    public Promotion createPromotion(Promotion promotion) throws ServiceException;
    public Promotion updatePromotion(Promotion promotion) throws ServiceException;
    public boolean deletePromotionById(int promotionId) throws ServiceException;

}

