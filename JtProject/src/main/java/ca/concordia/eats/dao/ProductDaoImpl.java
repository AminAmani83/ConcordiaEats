package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
    @Override
    public List<Product> fetchAllProducts() {
    	return jdbcTemplate.query(
                "select p.id, p.name, p.description, p.imagePath, p.price, p.salesCount, p.isOnSale, p.discountPercent, r.rating, c.id as categoryId, c.name as categoryName from product p join category c on p.categoryid = c.id join rating r on r.productId = p.id",
                (rs, rowNum) ->
                        new Product(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getString("imagePath"),
                                rs.getFloat("price"),
                                rs.getInt("salesCount"),
                                rs.getBoolean("isOnSale"),
                                rs.getFloat("discountPercent"),
                                rs.getDouble("rating"),
                                new Category(rs.getInt("categoryId"), rs.getString("categoryName"))
                        )
        );
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
        List<Category> allCategories = new LinkedList<>();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from categories");
            while (rs.next()) {
                allCategories.add(new Category(rs.getInt(1), rs.getString(2)));
            }
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }

        return allCategories;
    }

    @Override
    public Category fetchCategoryById(int categoryId) {
        return null;
    }

    @Override
    public Category createCategory(Category category) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject","root","");
            PreparedStatement pst = con.prepareStatement("insert into categories(name) values(?);");
            pst.setString(1, category.getName());
            int i = pst.executeUpdate();
        }
        catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return category;
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
