package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Promotion;
import ca.concordia.eats.dto.PromotionType;

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

    public List<PromotionType> getAllPromotionTypes() throws DAOException;
    public PromotionType getPromotionTypeById(int id) throws DAOException;
    public PromotionType createPromotionType(PromotionType promotionType) throws DAOException;
    public void updatePromotionType(PromotionType promotionType) throws DAOException;
    public void deletePromotionType(int id) throws DAOException;

}
