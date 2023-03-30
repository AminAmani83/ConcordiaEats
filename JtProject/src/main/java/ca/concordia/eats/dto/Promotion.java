package ca.concordia.eats.dto;

import java.time.LocalDateTime;

public class Promotion {
  
    private int promotionId;
    private int promotionTypeId;
    private String promotionType;
    private LocalDateTime promotionStartDate;
    private LocalDateTime promotionEndDate;

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }
    
    public int getPromotionTypeId() {
        return promotionTypeId;
    }

    public void setPromotionTypeId(int promotionTypeId) {
        this.promotionTypeId = promotionTypeId;
    }

    public LocalDateTime getPromotionStartDate() {
        return promotionStartDate;
    }
    public void setPromotionStartDate(LocalDateTime promotionStartDate) {
        this.promotionStartDate = promotionStartDate;
    }
    public LocalDateTime getPromotionEndDate() {
        return promotionEndDate;
    }
    public void setPromotionEndDate(LocalDateTime promotionEndDate) {
        this.promotionEndDate = promotionEndDate;
    }

    public String getPromotionType(int promotionTypeId){
        return promotionType;
    }
}
