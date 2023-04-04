package ca.concordia.eats.service;

import ca.concordia.eats.dao.ProductDao;
import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

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

    @Override
    public void makeFavorite(int customerId, int productId) {
        productDao.makeFavorite(customerId, productId);
    }

    @Override
    public void removeFavorite(int customerId, int productId) {
        productDao.removeFavorite(customerId, productId);
    }

    @Override
    public List<Product> fetchCustomerFavoriteProducts(int customerId) {
        return productDao.fetchCustomerFavoriteProducts(customerId);
    }


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
    public boolean hasPurchased(int customerId, int productId) {
        return productDao.hasPurchased(customerId, productId);
    }

    public List<Product> search(String query) {
        return productDao.search(query);
    }

}
