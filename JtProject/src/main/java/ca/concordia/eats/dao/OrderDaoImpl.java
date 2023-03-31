package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Purchase;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.LineItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDao {

    Connection con;

    public OrderDaoImpl() {

        try {
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "");
        } catch (Exception var2) {
            System.out.println("Error connecting to the DB: " + var2.getMessage());
        }
    }

    @Override
    public float calculateBasketItemPrice() {

        float totalPrice = 0.0f;

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "");
             PreparedStatement stmt = con.prepareStatement("SELECT p.price, pd.quantity FROM product p JOIN purchase_details pd ON p.id = pd.productId")) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                float price = rs.getFloat("price");
                int quantity = rs.getInt("quantity");
                totalPrice += price * quantity;
            }
        } catch (SQLException ex) {
            System.out.println("Error calculating basket item price: " + ex.getMessage());
        }

        return totalPrice;
    }

    @Override
    public float sumBasketItemPrices() {

        return 0;
    }

    @Override
    public float calculateTotalPrice() {

        return 0;
    }

    @Override
    public Purchase checkout() {

        return null;
    }

    private int generateNewPurchaseDetailsId() {
        int newId = 0;
        try (PreparedStatement stmt = con.prepareStatement("SELECT MAX(id) AS max_id FROM purchase_details")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                newId = rs.getInt("max_id") + 1;
            } else {
                newId = 1;
            }
        } catch (SQLException e) {
            System.out.println("Error generating new purchase details id: " + e.getMessage());
        }
        return newId;
    }

    @Override
    public boolean addToBasket(Product product, int quantity) {

        int newId = generateNewPurchaseDetailsId();

        try (PreparedStatement stmt = con.prepareStatement("INSERT INTO purchase_details (id, productId, quantity) VALUES (?, ?, ?)")) {
            stmt.setInt(1, newId);
            stmt.setInt(2, product.getId());
            stmt.setInt(3, quantity);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.out.println("Error adding product to basket: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateBasket(Product product, int quantity) {

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "");
             PreparedStatement stmt = con.prepareStatement("UPDATE purchase_details SET quantity = ? WHERE productId = ?")) {

            stmt.setInt(1, quantity);
            stmt.setInt(2, product.getId());

            int rowsUpdated = stmt.executeUpdate();

            if (quantity == 0) {
                // If quantity is 0, delete the row from purchase_details
                try (PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM purchase_details WHERE productId = ?")) {
                    deleteStmt.setInt(1, product.getId());
                    deleteStmt.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Error deleting row from purchase_details: " + ex.getMessage());
                    return false;
                }
            }

            return rowsUpdated > 0; // returns true if at least one row was updated
        } catch (SQLException ex) {
            System.out.println("Error updating basket: " + ex.getMessage());
            return false;
        }
    }
}
