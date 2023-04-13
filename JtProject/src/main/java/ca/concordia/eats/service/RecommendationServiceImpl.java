package ca.concordia.eats.service;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ca.concordia.eats.dao.ProductDao;
import ca.concordia.eats.dao.UserDao;
import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;



/**
 * Implements the UserService.java interface.
 */
@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;
    

	@Override
	public List<Product> fetchPersonalizedRecommendedProductsByCustomer(Customer customer) {
		   List<Product> topSearchedProducts = new ArrayList<>();
	       Map<Integer, Integer> searchCountMap =  fetchMostSearchedProductsByCustomer(customer);
		   List<Map.Entry<Integer, Integer>> sortedResults = new ArrayList<>(searchCountMap.entrySet());
	       sortedResults.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));
	       for (int i = 0; i < 3 && i < sortedResults.size(); i++) {
	    	   topSearchedProducts.add(productDao.fetchProductById(sortedResults.get(i).getKey()));
	       }
		return topSearchedProducts;
	}

	@Override
	public  Map<Integer, Integer> fetchMostSearchedProductsByCustomer(Customer customer){
	   List<Product> searchedProducts = userDao.fetchCustomerSearchedProduct(customer);
       Map<Integer, Integer> searchCountMap = new HashMap<>();
       for (Product p : searchedProducts) {
           if (searchCountMap.containsKey(p.getId())) {
               searchCountMap.put(p.getId(), searchCountMap.get(p.getId()) + 1);
           } else {
               searchCountMap.put(p.getId(), 1);
           }
       }
       return searchCountMap;
	}

	@Override
	public List<Product> fetchHighestRatingProducts() {
		List<Product> highestRatingProducts=new ArrayList<>();
		Map<Integer, Double> allProductAvgRatings= productDao.fetchAllProductAvgRatings();
		List<Map.Entry<Integer, Double>> sortedResults = new ArrayList<>(allProductAvgRatings.entrySet());
		sortedResults.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));
		for (Map.Entry<Integer, Double> sortedResult : sortedResults) {
			highestRatingProducts.add(productDao.fetchProductById(sortedResult.getKey()));
		}
		return highestRatingProducts;
	}

	@Override
	public List<Product> fetchBestSellerProducts() {
		List<Product> bestSellerProducts=new ArrayList<>();
		Map<Integer, Integer> allProductSumSalesQuantity= productDao.fetchAllProductSumSalesQuantity();
		List<Map.Entry<Integer, Integer>> sortedResults = new ArrayList<>(allProductSumSalesQuantity.entrySet());
		sortedResults.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));
		for (Map.Entry<Integer, Integer> sortedResult : sortedResults) {
			bestSellerProducts.add(productDao.fetchProductById(sortedResult.getKey()));
		}
		return bestSellerProducts;
	    
	}

	@Override
	public Product fetchHighestRatingProduct() {
		List<Product> highestRatingProducts = fetchHighestRatingProducts();
		return highestRatingProducts.isEmpty() ? null : highestRatingProducts.get(0);
	}

	@Override
	public Product fetchBestSellerProduct() {
		List<Product> bestSellerProducts = fetchBestSellerProducts();
		return bestSellerProducts.isEmpty() ? null : bestSellerProducts.get(0);
	}

	@Override
	public Product fetchPersonalizedRecommendedProductByCustomer(Customer customer) {
		List<Product> customerRecommendedProducts = fetchPersonalizedRecommendedProductsByCustomer(customer);
		return customerRecommendedProducts.isEmpty() ? null : customerRecommendedProducts.get(0);
	}
}
