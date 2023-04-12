package ca.concordia.eats.dto;

import java.sql.Timestamp;

public class Purchase {
  
    private int purchaseId;
    private int purchaseDetailsId;
    private Timestamp timeStamp;
    private float totalPrice;
    private int quantity;
    private float price;
    private boolean isOnSale;
    private float discountPercent;
  
    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }
  
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
  
    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
    public boolean isOnSale() {
        return isOnSale;
    }

    public void setOnSale(boolean isOnSale) {
        this.isOnSale = isOnSale;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getPurchaseDetailsId() {
        return purchaseDetailsId;
    }

    public void setPurchaseDetailsId(int purchaseDetailsId) {
        this.purchaseDetailsId = purchaseDetailsId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
