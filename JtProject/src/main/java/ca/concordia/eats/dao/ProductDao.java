package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.SearchHistory;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductDao {
    // CRUD PRODUCT
    public List<Product> fetchAllProducts();
    public Product fetchProductById(int productId);
    public Product createProduct(Product product);
    public Product updateProduct(Product product);
    public boolean removeProductById(int productId);

    // CRUD CATEGORY
    public List<Category> fetchAllCategories();
    public Category fetchCategoryById(int categoryId);
    public Category createCategory(Category category);
    public Category updateCategory(Category category);
    public boolean removeCategoryById(int categoryId);

    // OTHER : FAVORITE
    public void makeFavorite(int customerId, int productId);
    public void removeFavorite(int customerId, int productId);
    public List<Product> fetchCustomerFavoriteProducts(int customerId);
    public List<Product> search(String query, HttpSession session);

    public SearchHistory saveSearchHistoryToDatabase(String SearchQuery, HttpSession session) throws SQLException;

    // OTHER : RATING
    public void rateProduct(int customerId, int productId, int rating);
    public Map<Integer, Integer> fetchAllCustomerRatings(int customerId);
    public int fetchRatingByProductIdAndCustomerId(int customerId, int productId);
    public List<Product> fetchPastPurchasedProducts(int customerId);
    public void updateCurrentRating(int customerId, int productId, int rating);         // helper for rateProduct
    public void insertNewRating(int customerId, int productId, int rating);             // helper for rateProduct

}

