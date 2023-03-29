package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;

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

    // OTHER : RATING
    public void rateProduct(int customerId, int productId);
    public Map<Integer, Integer> fetchAllCustomerRatings(int customerId);
    public List<Product> fetchPastPurchasedProducts(int customerId);

}

