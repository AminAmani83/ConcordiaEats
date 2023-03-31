package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.Purchase;
import ca.concordia.eats.dto.LineItem;

public interface OrderDao {

    float calculateBasketItemPrice();

    float sumBasketItemPrices();

    float calculateTotalPrice();

    Purchase checkout();

    boolean addToBasket(Product product, int quantity);

    boolean updateBasket(Product product, int quantity);
}
