package ca.concordia.eats.dto;

import java.util.List;

public class Basket {
  
    private int basketId;
    private float totalPrice;
    private List<Product> lineItems;

    public Basket() {
    }

    public Basket(int basketId, float totalPrice, List<Product> lineItems) {
        this.basketId = basketId;
        this.totalPrice = totalPrice;
        this.lineItems = lineItems;
    }
  
    public int getBasketId() {
        return basketId;
    }

    public void setBasketId(int basketId) {
        this.basketId = basketId;
    }
  
    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Product> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<Product> lineItems) {
        this.lineItems = lineItems;
    }
}
