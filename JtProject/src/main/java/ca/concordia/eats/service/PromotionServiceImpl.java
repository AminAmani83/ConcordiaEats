package ca.concordia.eats.service;

import ca.concordia.eats.dao.PromotionDao;
import ca.concordia.eats.dto.Promotion;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    private PromotionDao promotionDao;

    public PromotionServiceImpl(PromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionDao.fetchAllPromotions();
    }

    @Override
    public Promotion getPromotionById(int promotionId) {
        return promotionDao.fetchPromotionById(promotionId);
    }

    @Override
    public Promotion createPromotion(Promotion promotion) throws ServiceException {
        try {
            return promotionDao.createPromotion(promotion);
        } catch (SQLException e) {
            throw new ServiceException("Error creating promotion", e);
        }
    }

    @Override
    public Promotion updatePromotion(Promotion promotion) throws ServiceException {
        try {
            return promotionDao.updatePromotion(promotion);
        } catch (SQLException e) {
            throw new ServiceException("Error updating promotion", e);
        }
    }

    @Override
    public boolean deletePromotionById(int promotionId) throws ServiceException {
        try {
            return promotionDao.removePromotionById(promotionId);
        } catch (SQLException e) {
            throw new ServiceException("Error deleting promotion", e);
        }
    }

    @Override
    public String getPromotionTypeNameById(int promotionTypeId) {
        return promotionDao.getPromotionTypeByTypeId(promotionTypeId);
    }
}

