package ca.concordia.eats.service;

import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;

import java.util.List;
import java.util.Map;

public interface BestDealsService {
    // CRUD USER

    public List<Product> fetchPersonalizedRecommendatedProductsBasedSearchPatternByUser(Customer customer);
    public List<Product> fetchHighestRatingProductsByUser (Customer customer);
    public  Product fetchHighestRatingProductByUser (Customer customer);
    public List<Product> fetchBestSellerProducts ();
    public  Product fetchBestSellerProduct ();
    public  Map<Integer, Integer> fetchMostSearchedProductsByUser (Customer customer);


}
