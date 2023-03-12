package ca.concordia.eats.dto;

import java.util.*;

public class Favorite {
  
    private List<Product> customerFavoritedProducts;
  
    public Initialize() {
        this.customerFavoritedProducts = new ArrayList<Product>();
    }
      
    public List<Product> getCustomerFavoritedProducts() {
        return new ArrayList(customerFavoritedProducts);
    }

    public void setCustomerFavoritedProducts(List<Product> customerFavoritedProducts) {
        this.customerFavoritedProducts = (customerFavoritedProducts == null) ? new ArrayList<Product>() : new ArrayList<Product>(customerFavoritedProducts);
    }
}
