package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {
    @Override
    public List<Product> fetchAllProducts() {
        return null;
    }

    @Override
    public Product fetchProductById(int productId) {
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
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
