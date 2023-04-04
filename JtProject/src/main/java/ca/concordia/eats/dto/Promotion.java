package ca.concordia.eats.dto;

import java.time.LocalDateTime;

public class Promotion {
  
    private int PromotionId;
    private int promotionTypeId;
    private PromotionType promotionType;
    private LocalDateTime promotionStartDate;
    private LocalDateTime promotionEndDate;

    public int getPromotionId() {
        return PromotionId;
    }

    public void setPromotionId(int promotionId) {
        this.PromotionId = promotionId;
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

    public int getPromotionTypeId() {
        return promotionTypeId;
    }
    public void setPromotionTypeId(int promotionTypeId) {
        this.promotionTypeId = promotionTypeId;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
    }

}
