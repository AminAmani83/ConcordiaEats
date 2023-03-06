package ca.concordia.eats.dto;

public class BasketItem {
  
    private int basketItemId;
    private int basketItemQuantity;
  
    public int getBasketItemId() {
        return basketItemId;
    }

    public void setBasketItemId(int basketItemId) {
        this.basketItemId = basketItemId;
    }
    
    public int getBasketItemQuantity() {
        return basketItemQuantity;
    }

    public void setBasketItemQuantity(int basketItemQuantity) {
        this.basketItemQuantity = basketItemQuantity;
    }
}
