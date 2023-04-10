package ca.concordia.eats.dto;

import java.util.List;

public class Customer extends User {
  
    private String address;
    private String phone;

    private Favorite favorite;
    private Rating rating;
    private List<Product> recommendation;
    private Product highestRatingProduct;
    private Product bestSellerProduct;
    
    public Customer() {
    }

    public Customer(Integer userId, String username, String role, String address, String email, String phone) {
        super(userId, username, role, email);
        this.address = address;
        this.phone = phone;
    }

    public Customer(Integer userId, String username, String role, String address, String email, String phone, boolean loginStatus) {
        super(userId, username, role, email, loginStatus);
        this.address = address;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Favorite getFavorite() {
        return favorite;
    }

    public void setFavorite(Favorite favorite) {
        this.favorite = favorite;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public List<Product> getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(List<Product> recommendation) {
        this.recommendation = recommendation;
    }
    public Product getHighestRatingProduct() {
        return highestRatingProduct;
    }

    public void setHighestRatingProduct(Product highestRatingProduct) {
        this.highestRatingProduct = highestRatingProduct;
    }
    public Product getBestSellerProduct() {
        return bestSellerProduct;
    }

    public void setBestSellerProduct(Product bestSellerProduct) {
        this.bestSellerProduct = bestSellerProduct;
    }
}
