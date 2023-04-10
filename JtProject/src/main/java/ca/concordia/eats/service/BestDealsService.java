package ca.concordia.eats.service;

import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;

import java.util.List;
import java.util.Map;

public interface BestDealsService {
    // CRUD USER

    public List<Product> fetchPersonalizedRecommendatedProductsByUser(Customer customer);
    public Product fetchPersonalizedRecommendatedProductByUser(Customer customer);
    public List<Product> fetchHighestRatingProducts ();
    public  Product fetchHighestRatingProduct ();
    public List<Product> fetchBestSellerProducts ();
    public  Product fetchBestSellerProduct ();
    public  Map<Integer, Integer> fetchMostSearchedProductsByUser (Customer customer);


}
