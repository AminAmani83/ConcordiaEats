package ca.concordia.eats.service;

import ca.concordia.eats.dao.DAOException;
import ca.concordia.eats.dao.ProductDao;
import ca.concordia.eats.dao.PromotionDao;
import ca.concordia.eats.dto.Basket;
import ca.concordia.eats.dto.Product;
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
    public boolean removePromotionById(int promotionId) throws ServiceException {
        try {
            return promotionDao.removePromotion(promotionId);
        } catch (DAOException e) {
            throw new ServiceException("Error deleting promotion", e);
        }
    }

    @Override
    public boolean applyAllCurrentPromotionsToBasket(Basket basket) throws ServiceException {
        Date currentDate = new Date();
        List<Product> allProducts = basket.getProductsInCart();

        try {
            List<Promotion> allPromotions = promotionDao.fetchAllPromotions();
            for (Promotion promotion: allPromotions) {
                if (!currentDate.after(promotion.getStartDate()) || !currentDate.before(promotion.getEndDate())){
                    continue;
                }
                switch (promotion.getType()) {
                    case "SITEWIDE_DISCOUNT_10":
                        for (Product product : allProducts) {
                            if (product.isOnSale()) continue;
                            product.setPrice((float) (product.getPrice() * 0.9));
                            product.setOnSale(true);
                            System.out.println("SITEWIDE_DISCOUNT_10 applied");
                        }

                        break;
                    case "SITEWIDE_DISCOUNT_20":
                        for (Product product : allProducts) {
                            if (product.isOnSale()) continue;
                            product.setPrice((float) (product.getPrice() * 0.8));
                            product.setOnSale(true);
                            System.out.println("SITEWIDE_DISCOUNT_20 applied");
                        }
                        break;

                    default:
                        System.out.println("Invalid Promotion");
                        break;
                }
            }

        } catch (DAOException e) {
            throw new ServiceException("Error applying promotion", e);
        }

        return true;
    }

}

