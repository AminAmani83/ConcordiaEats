package ca.concordia.eats.dto;

import java.util.Date;

public class Promotion {
  
    private int id;
    private String name;
    private Date StartDate;
    private Date EndDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
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


}
