package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
@Qualifier("productDaoImpl")
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
    	String sql = "select p.id, p.name, p.description, p.imagePath, p.price, p.salesCount, p.isOnSale, p.discountPercent, r.rating, c.id as categoryId, c.name as categoryName from product p join category c on p.categoryid = c.id join rating r on r.productId = p.id where p.id = ?";
    	
		return jdbcTemplate.queryForObject(
                sql,
                new RowMapper<Product>() {
                	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                		Product product = new Product(
                				rs.getInt("id"), 
                				rs.getString("name"), 
                				rs.getString("description"),
                				rs.getString("imagePath"), 
                				rs.getFloat("price"), 
                				rs.getInt("salesCount"), 
                				rs.getBoolean("isOnSale"),
                				rs.getFloat("discountPercent"), 
                				rs.getDouble("rating"),
                				new Category(rs.getInt("categoryId"), rs.getString("categoryName")));
                		return product;
                	}
                },
                new Object[] { productId }
        );
    }

    @Override
    public int createProduct(Product product) {
    	String sql = "insert into product(id, name, description, imagePath, categoryid, price, salesCount, isOnSale, discountPercent)value(?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql,
				new Object[] {product.getId(),
						product.getName(),
						product.getDescription(),
						product.getImagePath(),
						product.getCategory().getId(),
						product.getPrice(),
						product.getSalesCount(),
						product.isOnSale(),
						product.getDiscountPercent()});
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
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject","root","");
            PreparedStatement pst = con.prepareStatement("update categories set name = ? where categoryid = ?");
            pst.setString(1, category.getName());
            pst.setInt(2, category.getId());
            int i = pst.executeUpdate();
        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return category;
    }

    @Override
    public boolean removeCategoryById(int categoryId) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject","root","");
            PreparedStatement pst = con.prepareStatement("delete from categories where categoryid = ? ;");
            pst.setInt(1, categoryId);
            int i = pst.executeUpdate();
        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return true;
    }
}
