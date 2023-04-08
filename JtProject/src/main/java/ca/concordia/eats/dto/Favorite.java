package ca.concordia.eats.dto;

import java.util.*;

public class Favorite {
  
    private List<Product> customerFavoritedProducts = new ArrayList<>();
  
    public Favorite() {
    }

    public Favorite(List<Product> customerFavoritedProducts) {
        this.customerFavoritedProducts = customerFavoritedProducts;
    }

    public List<Product> getCustomerFavoritedProducts() {
        return new ArrayList(customerFavoritedProducts);
    }

    public void setCustomerFavoritedProducts(List<Product> customerFavoritedProducts) {
        this.customerFavoritedProducts = (customerFavoritedProducts == null) ? new ArrayList<Product>() : new ArrayList<Product>(customerFavoritedProducts);
    }
}
