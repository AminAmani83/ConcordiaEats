package ca.concordia.eats.dto;

import java.util.Date;

public class Promotion {
  
    private int id;
    private PromotionType promotionType;
    private Date StartDate;
    private Date EndDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Date getStartDate() {
        return StartDate;
    }
    public void setStartDate(Date startDate) {
        this.StartDate = startDate;
    }
    public Date getEndDate() {
        return EndDate;
    }
    public void setEndDate(Date endDate) {
        this.EndDate = endDate;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
    }

}
