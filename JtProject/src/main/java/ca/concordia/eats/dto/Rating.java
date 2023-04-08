package ca.concordia.eats.dto;

import java.util.*;

public class Rating {
  
    Map<Integer, Integer> customerRatings = new HashMap<Integer, Integer>();        // <productId, rating>
  
    public Rating () {
    }

    public Rating(Map<Integer,Integer> customerRatings) {
        this.customerRatings = customerRatings;
    }

    public Map<Integer,Integer> getCustomerRatings() {
        return customerRatings;
    }

    public void setCustomerRatings(Map<Integer,Integer> customerRatings) {
        this.customerRatings = (customerRatings == null) ? new HashMap<Integer,Integer>() : new HashMap<Integer,Integer>(customerRatings);
    }

}
