package ca.concordia.eats.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ca.concordia.eats.dto.*;
import ca.concordia.eats.service.PromotionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import ca.concordia.eats.utils.ConnectionUtil;

@Repository
public class OrderDaoImpl implements OrderDao {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
	 
    @Autowired
    private PromotionDao promotionDao;
	    
    Connection con;

    public OrderDaoImpl() throws IOException {
        this.con = ConnectionUtil.getConnection();
    }


     // This method fills purchase_details, purchase and updates product database tables with information concerning the order as soon as the order gets checked out.
     public void makeOrder(User user, Basket basket) {
	        try {
	            String purchaseSql = "insert into purchase(userId, timeStamp, total_price, promotionId) values(?,?,?,?)";
	            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
	            int purchaseUpdate = jdbcTemplate.update(
	                conn -> {
	                    PreparedStatement preparedStatement = conn.prepareStatement(purchaseSql, Statement.RETURN_GENERATED_KEYS);
	                    preparedStatement.setInt(1, user.getUserId());
	                    preparedStatement.setString(2, String.valueOf(new Timestamp(System.currentTimeMillis())));
	                    preparedStatement.setFloat(3, basket.getTotal(promotionDao.fetchAllPromotions()));
	                    preparedStatement.setInt(4, 0);
	                    return preparedStatement;
	                }, generatedKeyHolder);

	            if (purchaseUpdate == 1) {
	                Integer purchaseId = generatedKeyHolder.getKey().intValue();
	                String purchaseDetailSql = "insert into purchase_details(purchaseId, productId, quantity, price, isOnSale, discountPercent) values(?,?,?,?,?,?)";
	                for (Product product : basket.getProductsInCart()) {
	                    int purchaseDetailUpdate = jdbcTemplate.update(
	                        conn -> {
	                            PreparedStatement preparedStatement = conn.prepareStatement(purchaseDetailSql);
	                            preparedStatement.setInt(1, purchaseId);
	                            preparedStatement.setInt(2, product.getId());
	                            preparedStatement.setInt(3, product.getSalesCount());
	                            preparedStatement.setFloat(4, product.getPrice());
	                            preparedStatement.setBoolean(5, product.isOnSale());
	                            preparedStatement.setFloat(6, product.getDiscountPercent());
	                            return preparedStatement;
	                        }
	                    );
	                    if (purchaseDetailUpdate != 1) {
	                        return;
	                    }
	                    // Update the salesCount variable in the product table
	                    String updateSalesCountSql = "UPDATE product SET salesCount = salesCount + ? WHERE id = ?";
	                    int updateSalesCount = jdbcTemplate.update(
	                        conn -> {
	                            PreparedStatement preparedStatement = conn.prepareStatement(updateSalesCountSql);
	                            preparedStatement.setInt(1, product.getSalesCount());
	                            preparedStatement.setInt(2, product.getId());
	                            return preparedStatement;
	                        }
	                    );
	                    if (updateSalesCount != 1) {
	                        return;
	                    }
	                }
	            } else {
	                return;
	            }
	        } catch (Exception ex) {
	            System.out.println("Exception Occurred: " + ex.getMessage());
	        }
	    }

    /**
     * Helper method for removeAllPurchasesByCustomerId
     * @param customerId
     * @return
     */
    private List<Integer> fetchPurchaseIdsByCustomerId(int customerId) {

        List<Integer> purchaseIds = new ArrayList<Integer>();

        try {
            PreparedStatement pst = con.prepareStatement("select id from purchase where userId=?;");
            pst.setInt(1, customerId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                purchaseIds.add(rs.getInt(1));
            }
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }

        return purchaseIds;

        //preparedStatement.setInt(1, purchaseId);
        //preparedStatement.setInt(2, product.getId());
        //preparedStatement.setInt(3, product.getSalesCount());

	}


    /**
     * Helper method for removeAllPurchasesByCustomerId
     * @param customerId
     * @return
     */


    /**
     * Helper method for removeAllPurchasesByCustomerId
     * @param purchaseIds
     */
    private void removePurchaseDetailsByPurchaseId(List<Integer> purchaseIds) {

        for (int id : purchaseIds) {
            try {
                PreparedStatement pst = con.prepareStatement("delete from purchase_details where purchaseId=?;");
                pst.setInt(1, id);
                pst.executeUpdate();

            } catch (Exception ex) {
                System.out.println("Exception Occurred: " + ex.getMessage());
            }    
        }
    }

    /**
     * Helper method for removeAllPurchasesByCustomerId.
     * @param customerId
     */
    private void removePurchasesByCustomerId(int customerId) {
        try {
            PreparedStatement pst = con.prepareStatement("delete from purchase where userId=?;");
            pst.setInt(1, customerId);
            pst.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }    
    }

    /**
     * To remove all purchases, one first needs to remove all associated lines in 
     * purchase_details table and then remove all purchases associated with this
     * customer in 'purchase' table.
     */
    @Override
    public void removeAllPurchasesByCustomerId(int customerId) {

        List<Integer> purchaseIds = fetchPurchaseIdsByCustomerId(customerId);
        removePurchaseDetailsByPurchaseId(purchaseIds);
        removePurchasesByCustomerId(customerId);
    }
}
