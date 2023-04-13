package ca.concordia.eats.dto;

public class Recommendation {
    private Product recommendedProduct;
    private Product highestRatingProduct;
    private Product bestSellerProduct;

    
    public Product getHighestRatingProduct() {
        return highestRatingProduct;
    }

    public void setHighestRatingProduct(Product highestRatingProduct) {
        this.highestRatingProduct = highestRatingProduct;
    }
  
    public Product getRecommendedProduct() {
        return recommendedProduct;
    }

    public void setRecommendedProduct(Product recommendendedProduct) {
        this.recommendedProduct = recommendendedProduct;
    }
    public Product getBestSellerProduct() {
        return bestSellerProduct;
    }

    public void setBestSellerProduct(Product bestSellerProduct) {
        this.bestSellerProduct = bestSellerProduct;
    }
}
