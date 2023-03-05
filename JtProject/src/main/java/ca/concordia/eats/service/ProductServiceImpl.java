package ca.concordia.eats.service;

import ca.concordia.eats.dao.ProductDao;
import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return productDao.createProduct(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public boolean removeProductById(int productId) {
        return false;
    }

    @Override
    public List<Category> fetchAllCategories() {
        return null;
    }

    @Override
    public Category fetchCategoryById(int categoryId) {
        return null;
    }

    @Override
    public Category createCategory(Category category) {
        return null;
    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }

    @Override
    public boolean removeCategoryById(int categoryId) {
        return false;
    }
}
