package ca.concordia.eats.service;

import ca.concordia.eats.dao.DAOException;
import ca.concordia.eats.dao.ProductDao;
import ca.concordia.eats.dao.ProductDaoImpl;
import ca.concordia.eats.dao.PromotionDao;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.Promotion;
import ca.concordia.eats.dto.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public boolean deletePromotionById(int promotionId) throws ServiceException {
        try {
            return promotionDao.removePromotion(promotionId);
        } catch (DAOException e) {
            throw new ServiceException("Error deleting promotion", e);
        }
    }

    @Override
    public boolean applyPromotion(int promotionId) throws ServiceException {
        try {
            Promotion promotion = promotionDao.fetchPromotionById(promotionId);
            String promotionType = promotion.getType();
            Date currentDate = new Date();

            if (!currentDate.after(promotion.getStartDate()) || !currentDate.before(promotion.getEndDate())){
                return false;
            }

            switch (promotionType) {
                case "10% Site-wide Discount":
                    applySiteWideDiscount(10);
                    break;
                case "10% purchase Discount":
                    applyPurchaseDiscount(10);
                    break;
                case "Free Shipping":
                    System.out.println("Free Shipping");
                    break;
                case "Buy One Get One Free":
                    System.out.println("Buy One Get One Free");
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

    @Override
    public void applySiteWideDiscount(float discountPercentage) {
        List<Product> allProducts;
        allProducts = productDao.fetchAllProducts();
        for (Product product : allProducts) {
            product.setDiscountPercent(discountPercentage);
            product.setOnSale(true);
            productDao.updateProduct(product);
        }
    }

    @Override
    public void applyPurchaseDiscount(float discountPercentage) {
        List<Purchase> allPurchases;
        allPurchases = promotionDao.fetchAllPurchases();
        for (Purchase purchase : allPurchases) {
            purchase.setDiscountPercent(discountPercentage);
            purchase.setOnSale(true);
            promotionDao.updatePurchase(purchase);
        }

    }


}

