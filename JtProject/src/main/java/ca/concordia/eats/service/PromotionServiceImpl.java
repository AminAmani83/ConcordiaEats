package ca.concordia.eats.service;

import ca.concordia.eats.dao.DAOException;
import ca.concordia.eats.dao.ProductDao;
import ca.concordia.eats.dao.PromotionDao;
import ca.concordia.eats.dto.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionDao promotionDao;
    @Autowired
    private ProductDao productDao;

    public PromotionServiceImpl(PromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }

    @Override
    public List<Promotion> getAllPromotions() throws ServiceException {
        try {
            return promotionDao.fetchAllPromotions();
        } catch (DAOException e) {
            throw new ServiceException("Error fetching all promotions", e);
        }
    }

    @Override
    public Promotion getPromotionById(int promotionId) throws ServiceException {
        try {
            return promotionDao.fetchPromotionById(promotionId);
        } catch (DAOException e) {
            throw new ServiceException("Error getting promotion by ID", e);
        }
    }


    @Override
    public Promotion createPromotion(Promotion promotion) throws ServiceException {
        try {
            return promotionDao.createPromotion(promotion);
        } catch (DAOException e) {
            throw new ServiceException("Error creating promotion", e);
        }
    }

    @Override
    public Promotion updatePromotion(Promotion promotion) throws ServiceException {
        try {
            return promotionDao.updatePromotion(promotion);
        } catch (DAOException e) {
            throw new ServiceException("Error updating promotion", e);
        }
    }

    @Override
    public boolean removePromotion(int promotionId) throws ServiceException {
        try {
            return promotionDao.removePromotion(promotionId);
        } catch (DAOException e) {
            throw new ServiceException("Error deleting promotion", e);
        }
    }

    @Override
    public boolean applyPromotionToAllProducts(int promotionId) throws ServiceException {
        try {
            Promotion promotion = promotionDao.fetchPromotionById(promotionId);
            String promotionType = promotion.getType();
            Date currentDate = new Date();

            if (!currentDate.after(promotion.getStartDate()) || !currentDate.before(promotion.getEndDate())){
                return false;
            }

            switch (promotionType) {
                case "SITEWIDE_DISCOUNT_10":
                    promotionDao.applyDiscountPromotionToAllProducts(promotionId, 10);
                    break;
                case "SITEWIDE_DISCOUNT_20":
                    promotionDao.applyDiscountPromotionToAllProducts(promotionId, 20);
                    break;
                default:
                    System.out.println("Invalid Promotion");
                    break;
            }
        } catch (DAOException e) {
            throw new ServiceException("Error applying promotion", e);
        }

        return true;
    }


}

