package ca.concordia.eats.dto;

public class LineItem {
  
    private int lineItemId;
    private int lineItemQuantity;
  
    public int getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(int lineItemId) {
        this.lineItemId = lineItemId;
    }
    
    public int getLineItemQuantity() {
        return lineItemQuantity;
    }

    public void setLineItemQuantity(int lineItemQuantity) {
        this.lineItemQuantity = lineItemQuantity;
    }
}
