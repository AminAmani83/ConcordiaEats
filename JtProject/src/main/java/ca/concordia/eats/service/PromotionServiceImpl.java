package ca.concordia.eats.service;

import ca.concordia.eats.dao.DAOException;
import ca.concordia.eats.dao.PromotionDao;
import ca.concordia.eats.dto.Promotion;
import ca.concordia.eats.dto.PromotionType;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    private PromotionDao promotionDao;

    public PromotionServiceImpl(PromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }

    @Override
    public List<Promotion> getAllPromotions() throws ServiceException {
        List<Promotion> promotions;
        try {
            promotions = promotionDao.fetchAllPromotions();
            for (Promotion promotionItem : promotions) {
                PromotionType promotionType = promotionDao.getPromotionTypeById(promotionItem.getPromotionTypeId());
                promotionItem.setPromotionType(promotionType);
            }

        } catch (DAOException e) {
            throw new ServiceException("Error fetching all promotions", e);
        }
        return promotions;
    }

    @Override
    public Promotion getPromotionById(int promotionId) throws ServiceException {
        try {
            Promotion promotion = promotionDao.fetchPromotionById(promotionId);
            PromotionType promotionType = promotionDao.getPromotionTypeById(promotion.getPromotionTypeId());
            promotion.setPromotionType(promotionType);
            return promotion;
        } catch (DAOException e) {
            throw new ServiceException("Error getting promotion by ID", e);
        }
    }


    @Override
    public Promotion createPromotion(Promotion promotion) throws ServiceException {
        try {
            Promotion NewPromotion = promotionDao.createPromotion(promotion);
            PromotionType promotionType = promotionDao.getPromotionTypeById(NewPromotion.getPromotionTypeId());
            promotion.setPromotionType(promotionType);
            return NewPromotion;
        } catch (DAOException e) {
            throw new ServiceException("Error creating promotion", e);
        }
    }

    @Override
    public Promotion updatePromotion(Promotion promotion) throws ServiceException {
        try {
            Promotion NewPromotion = promotionDao.createPromotion(promotion);
            PromotionType promotionType = promotionDao.getPromotionTypeById(NewPromotion.getPromotionTypeId());
            promotion.setPromotionType(promotionType);
            return NewPromotion;
        } catch (DAOException e) {
            throw new ServiceException("Error updating promotion", e);
        }
    }

    @Override
    public boolean deletePromotionById(int promotionId) throws ServiceException {
        try {
            return promotionDao.removePromotion(promotionId);
        } catch (DAOException e) {
            throw new ServiceException("Error deleting promotion", e);
        }
    }

}

