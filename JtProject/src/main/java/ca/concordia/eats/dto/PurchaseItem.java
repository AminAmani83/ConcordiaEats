package ca.concordia.eats.dto;

public class PurchaseItem {
  
    private int purchaseItemId;
    private int purchaseItemQuantity;
  
    public int getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(int purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }
    
    public int getPurchaseItemQuantity() {
        return purchaseItemQuantity;
    }

    public void setPurchaseItemQuantity(int purchaseItemQuantity) {
        this.purchaseItemQuantity = purchaseItemQuantity;
    }
}
