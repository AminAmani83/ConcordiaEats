package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Basket;
import ca.concordia.eats.dto.Purchase;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.LineItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpSession;
import java.util.List;
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
    public float calculateBasketItemPrice(int productId, HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        if (basket == null) {
            return 0; // basket not found
        }

        List<Product> lineItems = basket.getLineItems();
        for (Product p : lineItems) {
            if (p.getId() == productId) {
                return p.getPrice() * p.getQuantity();
            }
        }

        return 0; // product not found in basket
    }


    @Override
    public float sumBasketItemPrices(HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        if (basket == null) {
            return 0; // basket not found
        }

        List<Product> lineItems = basket.getLineItems();
        float totalPrice = 0;
        for (Product p : lineItems) {
            totalPrice += p.getPrice() * p.getQuantity();
        }

        return totalPrice;
    }

    @Override
    public float calculateTotalPrice(HttpSession session, float deliveryFee) {
        float subtotal = sumBasketItemPrices(session);
        float tax = 0.15f * subtotal; // calculate 15% tax on subtotal
        float total = subtotal + tax + deliveryFee; // add tax and delivery fee to subtotal
        return total;
    }

    @Override
    public Purchase checkout(HttpSession session) {

        return null;
    }

    public int generateUniqueBasketId() {
        // Get the current time in milliseconds
        long currentTime = System.currentTimeMillis();

        // Concatenate the current time with a random number to get a unique ID
        int uniqueId = (int) (currentTime % 1000000) * 100 + (int) (Math.random() * 100);

        return uniqueId;
    }

    @Override
    public boolean addToBasket(Product product, int quantity, HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        if (basket == null) {
            basket = new Basket();
            basket.setBasketId(generateUniqueBasketId()); // set a unique basketId
            session.setAttribute("basket", basket);
        }

        List<Product> lineItems = basket.getLineItems();
        for (Product p : lineItems) {
            if (p.getId() == product.getId()) {
                p.setQuantity(p.getQuantity() + quantity);
                basket.setTotalPrice(basket.getTotalPrice() + (p.getPrice() * quantity)); // update totalPrice
                return true;
            }
        }
        lineItems.add(product);
        product.setQuantity(quantity);
        basket.setTotalPrice(basket.getTotalPrice() + (product.getPrice() * quantity)); // update totalPrice
        return true;
    }

    @Override
    public boolean updateBasket(Product product, int quantity, HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        if (basket == null) {
            return false; // basket not found
        }

        List<Product> lineItems = basket.getLineItems();
        float productTotalPrice = product.getPrice() * quantity;
        for (Product p : lineItems) {
            if (p.getId() == product.getId()) {
                p.setQuantity(quantity);
                basket.setTotalPrice(basket.getTotalPrice() + (p.getPrice() * (quantity - p.getQuantity()))); // update totalPrice
                p.setPrice(productTotalPrice); // set the total price for the product to the newly calculated value
                return true;
            }
        }

        return false; // product not found in basket
    }
}
