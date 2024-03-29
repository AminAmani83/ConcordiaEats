package ca.concordia.eats.service;

import ca.concordia.eats.dao.ProductDao;
import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    public ProductServiceImpl() {
    }

    // used for Integration Testing
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> fetchAllProducts() {
        return productDao.fetchAllProducts();
    }

    @Override
    public Product fetchProductById(int productId) {
        return productDao.fetchProductById(productId);
    }

    @Override
    public Product createProduct(Product product) {
        return productDao.createProduct(product);
    }

    @Override
    public Product updateProduct(Product product) {
    	return productDao.updateProduct(product);
    }

    @Override
    public boolean removeProductById(int productId) {
        return productDao.removeProductById(productId);
    }

    @Override
    public List<Category> fetchAllCategories() {
        return productDao.fetchAllCategories();
    }

    @Override
    public Category fetchCategoryById(int categoryId) {
        return productDao.fetchCategoryById(categoryId);
    }

    @Override
    public Category createCategory(Category category) {
        return productDao.createCategory(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return productDao.updateCategory(category);
    }

    @Override
    public boolean removeCategoryById(int categoryId) {
        return productDao.removeCategoryById(categoryId);
    }


    // FAVORITES

    @Override
    public void makeFavorite(int customerId, int productId) {
        productDao.makeFavorite(customerId, productId);
    }

    @Override
    public boolean removeFavorite(int customerId, int productId) {
        return productDao.removeFavorite(customerId, productId);
    }

    @Override
    public List<Product> fetchCustomerFavoriteProducts(int customerId) {
        List<Integer> customerFavoriteProductIds = productDao.fetchCustomerFavoriteProductIds(customerId);
        return productDao.fetchAllProducts().stream().filter(p -> customerFavoriteProductIds.contains(p.getId()) && !p.isDisable()).collect(Collectors.toList());
    }


    // RATING

    @Override
    public void rateProduct(int customerId, int productId, int rating) {
        productDao.rateProduct(customerId, productId, rating);
    }

    @Override
    public Map<Integer, Integer> fetchAllCustomerRatings(int customerId) {
        return productDao.fetchAllCustomerRatings(customerId);
    }

    @Override
    public List<Product> fetchPastPurchasedProducts(int customerId) {
        return productDao.fetchPastPurchasedProducts(customerId);
    }

    @Override
    public List<Product> search(String query, int userId) {
        return productDao.search(query, userId);
    }



    @Override
    public Double calculateAvgProductRating(int productId) {
        return productDao.calculateAvgProductRating(productId);
    }

    @Override
    public Map<Integer, Double> fetchAllProductAvgRatings(){
    	Map<Integer, Double> productAvgRatings = new HashMap<Integer, Double>();
        try {
        	List<Product> products = fetchAllProducts().stream().filter(p -> !p.isDisable()).collect(Collectors.toList());
        	for (Product p : products) {
            	productAvgRatings.put(p.getId(), calculateAvgProductRating(p.getId()));
            }
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return productAvgRatings;
    }

}
