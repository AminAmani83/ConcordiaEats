package ca.concordia.eats.dto;

import java.util.*;

public class Recommendation {
    private Product recommendendedProduct;
    private Product highestRatingProduct;
    private Product bestSellerProduct;

    
    public Product getHighestRatingProduct() {
        return highestRatingProduct;
    }

    public void setHighestRatingProduct(Product highestRatingProduct) {
        this.highestRatingProduct = highestRatingProduct;
    }
  
    public Product getRecommendendedProduct() {
        return recommendendedProduct;
    }

    public void setRecommendendedProduct(Product recommendendedProduct) {
        this.recommendendedProduct = recommendendedProduct;
    }
    public Product getBestSellerProduct() {
        return bestSellerProduct;
    }

    public void setBestSellerProduct(Product bestSellerProduct) {
        this.bestSellerProduct = bestSellerProduct;
    }
}
