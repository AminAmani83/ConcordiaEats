package ca.concordia.eats.service;

import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;

import java.util.List;

public interface RecommendationService {
    // CRUD USER

    public List<Product> fetchPersonalisedRecommendation(User user);
	public  List<Product> mostSearchedProducts(User user);

}
