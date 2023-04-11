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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import ca.concordia.eats.dto.Basket;
import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;
import ca.concordia.eats.utils.ConnectionUtil;

@Repository
public class OrderDaoImpl implements OrderDao {
	
	 @Autowired
    private JdbcTemplate jdbcTemplate;

    Connection con;

    public OrderDaoImpl() throws IOException {
        this.con = ConnectionUtil.getConnection();
    }


	public void makeOrder(User user, Basket basket) {
		
        try {
        	String sql = "insert into purchase(userId, timeStamp, total_price, promotionId)value(?,?,?,?)";
        	
            GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
            int update = jdbcTemplate.update(
                    conn -> {
                        // Pre-compiling SQL
                        PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        // Set parameters
                        preparedStatement.setInt(1, user.getUserId());
                        preparedStatement.setString(2, String.valueOf( new Timestamp(System.currentTimeMillis())));
                        preparedStatement.setFloat(3, basket.getTotal());
                        preparedStatement.setInt(4, 0);
                        return preparedStatement;

                    }, generatedKeyHolder);
                        
                        
                        if (update == 1) {
                            Integer id = generatedKeyHolder.getKey().intValue();
                            addPurchaseDetails(id, basket.getProductsInCart());
                        } else {
                            return;
                        }
            
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
	}
	
	private void addPurchaseDetails(Integer purchaseId, List<Product> list) {
		for (Product product : list) 
		{ 
			addPurchaseDetail(purchaseId, product);
		} 	
	}
	
	private void addPurchaseDetail(Integer purchaseId, Product product) {
    	String sql = "insert into purchase(userId, timeStamp, total_price, promotionId)value(?,?,?,?)";

        //preparedStatement.setInt(1, purchaseId);
        //preparedStatement.setInt(2, product.getId());
        //preparedStatement.setInt(3, product.getSalesCount());
    	
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
    }

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
