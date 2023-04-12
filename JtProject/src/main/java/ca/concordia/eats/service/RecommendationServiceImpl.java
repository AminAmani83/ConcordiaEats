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
    private ProductDao productDao;
    

	@Override
	public List<Product> fetchPersonalizedRecommendedProductsByCustomer(Customer customer) {
		   List<Product> topSearchedProducts = null;
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
		List<Product> HighestRatingProducts=new ArrayList<>();
		Map<Integer, Double> AllProductAvgRatings= productDao.fetchAllProductAvgRatings();
		List<Map.Entry<Integer, Double>> sortedResults = new ArrayList<>(AllProductAvgRatings.entrySet());
		sortedResults.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));
	       for (int i = 0;  i < sortedResults.size(); i++) {
	    	   HighestRatingProducts.add(productDao.fetchProductById(sortedResults.get(i).getKey()));
	       }
		return HighestRatingProducts;
	}

	@Override
	public List<Product> fetchBestSellerProducts() {
		List<Product> BestSellerProducts=new ArrayList<>();
		Map<Integer, Integer> AllProductSumSalesQuantity= productDao.fetchAllProductSumSalesQuantity();
		List<Map.Entry<Integer, Integer>> sortedResults = new ArrayList<>(AllProductSumSalesQuantity.entrySet());
		sortedResults.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));
	       for (int i = 0;  i < sortedResults.size(); i++) {
	    	   BestSellerProducts.add(productDao.fetchProductById(sortedResults.get(i).getKey()));
	       }
	       return BestSellerProducts;
	    
	}

	@Override
	public Product fetchHighestRatingProduct() {
		Product HighestRatingProduct;
		List<Product> HighestRatingProducts=fetchHighestRatingProducts();
		HighestRatingProduct = HighestRatingProducts.get(0);
		return HighestRatingProduct;
	}

	@Override
	public Product fetchBestSellerProduct() {
		Product BestSellerProduct;
		List<Product> BestSellerProducts=fetchBestSellerProducts();
		BestSellerProduct = BestSellerProducts.get(0);
		return BestSellerProduct;
	}

	@Override
	public Product fetchPersonalizedRecommendedProductByCustomer(Customer customer) {
		Product PersonalizedRecommendatedProduct;
		List<Product> PersonalizedRecommendatedProducts=fetchPersonalizedRecommendedProductsByCustomer(customer);
		PersonalizedRecommendatedProduct = PersonalizedRecommendatedProducts.get(0);
		return PersonalizedRecommendatedProduct;		
	}





}
