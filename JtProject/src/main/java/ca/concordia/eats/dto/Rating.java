package ca.concordia.eats.dto;

import java.util.*;

/**
 * Instance variables:
 *  customerRatings maps a the rating from this customer to a productId: <productId, rating>
 *  rateableProducts contains Products that were purchased by this customer. 
 */
public class Rating {
  
    Map<Integer, Integer> customerRatings = new HashMap<Integer, Integer>();      // <productId, rating>
    List<Product> rateableProducts = new ArrayList<Product>();                    // this variable contains only past purchased Products
  
    // this variable contains only past purchased Products
   
    public Rating () {
    }

    public Rating(Map<Integer,Integer> customerRatings, List<Product> rateableProducts) {
        this.customerRatings = customerRatings;
        this.rateableProducts = rateableProducts;
    }

    public Map<Integer,Integer> getCustomerRatings() {
        return customerRatings;
    }

    public void setCustomerRatings(Map<Integer,Integer> customerRatings) {
        this.customerRatings = (customerRatings == null) ? new HashMap<Integer,Integer>() : new HashMap<Integer,Integer>(customerRatings);
    }

    public List<Product> getRateableProducts() {
        return rateableProducts;
    }

    public void setRateableProducts(List<Product> rateableProducts) {
        this.rateableProducts = (rateableProducts == null) ? new ArrayList<Product>() : new ArrayList<Product>(rateableProducts);
    }

}
