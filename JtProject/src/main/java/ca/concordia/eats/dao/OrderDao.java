package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.Purchase;
import ca.concordia.eats.dto.LineItem;
import javax.servlet.http.HttpSession;

public interface OrderDao {

    public float calculateBasketItemPrice(int productId, HttpSession session);

    public float sumBasketItemPrices(HttpSession session);

    public float calculateTotalPrice(HttpSession session, float deliveryFee);

    public Purchase checkout(HttpSession session);

    public boolean addToBasket(Product product, int quantity, HttpSession session);

    public boolean updateBasket(Product product, int quantity, HttpSession session);
}

