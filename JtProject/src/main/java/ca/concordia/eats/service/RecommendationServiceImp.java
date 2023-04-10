package ca.concordia.eats.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ca.concordia.eats.dao.ProductDao;
import ca.concordia.eats.dao.UserDao;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;



/**
 * Implements the UserService.java interface.
 */
@Service
public class RecommendationServiceImp implements RecommendationService {

    @Autowired
    private UserDao userDao;
    private ProductDao productDao;

	@Override
	public List<Product> fetchPersonalisedRecommendation(User user) {
		return mostSearchedProducts(user);
	}

	@Override
	public  List<Product> mostSearchedProducts(User user){
	   List<Product> searchedProducts = userDao.fetchCustomerSearchedProduct(user);
       Map<Integer, Integer> searchCountMap = new HashMap<>();
	   List<Product> topSearchedProducts = null;


       for (Product p : searchedProducts) {
           if (searchCountMap.containsKey(p.getId())) {
               searchCountMap.put(p.getId(), searchCountMap.get(p.getId()) + 1);
           } else {
               searchCountMap.put(p.getId(), 1);
           }
       }
       
       List<Map.Entry<Integer, Integer>> sortedResults = new ArrayList<>(searchCountMap.entrySet());
       sortedResults.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));
       for (int i = 0; i < 3 && i < sortedResults.size(); i++) {
    	   topSearchedProducts.add(productDao.fetchProductById(sortedResults.get(i).getKey()));
       }
       return topSearchedProducts;
	}




}
