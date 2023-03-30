package ca.concordia.eats.dto;

import java.util.*;

public class Rating {
  
    Map<Integer, Integer> customerRatings = new HashMap<Integer, Integer>();        // <productId, rating>
    private List<Product> rateableProducts;
  
    public Rating() {
        this.rateableProducts = new ArrayList<Product>();
    }
      
    public List<Product> getRateableProducts() {
        return new ArrayList(rateableProducts);
    }

    public void setRateableProducts(List<Product> rateableProducts) {
        this.rateableProducts = (rateableProducts == null) ? new ArrayList<Product>() : new ArrayList<Product>(rateableProducts);
    }
}
