package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    Connection con;
    public ProductDaoImpl() {
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "");
        } catch(Exception e) {
            System.out.println("Error connecting to the DB: " + e.getMessage());
        }
    }
	
    @Override
    public List<Product> fetchAllProducts() {
    	return jdbcTemplate.query(
                "select p.id, p.name, p.description, p.imagePath, p.price, p.salesCount, p.isOnSale, p.discountPercent, r.rating, c.id as categoryId, c.name as categoryName from product p join category c on p.categoryid = c.id left join rating r on r.productId = p.id order by p.id desc",
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
    	String sql = "select p.id, p.name, p.description, p.imagePath, p.price, p.salesCount, p.isOnSale, p.discountPercent, r.rating, c.id as categoryId, c.name as categoryName from product p left join category c on p.categoryid = c.id left join rating r on r.productId = p.id where p.id = ?";
    	
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
    public Product createProduct(Product product) {
    	String sql = "insert into product( name, description, imagePath, categoryid, price, salesCount, isOnSale, discountPercent)value(?,?,?,?,?,?,?,?)";
    	GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		int update = jdbcTemplate.update(
				conn -> {
		            // Pre-compiling SQL
		            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		            // Set parameters
		            preparedStatement.setString(1, product.getName());
		            preparedStatement.setString(2, product.getDescription());
		            preparedStatement.setString(3, product.getImagePath()==null?"":product.getImagePath());
		            Category category = product.getCategory();
		            if(category!=null) {
		            	preparedStatement.setInt(4, category.getId());
		            }else {
		            	preparedStatement.setInt(4, 6);
		            }
		            preparedStatement.setFloat(5, product.getPrice());
		            preparedStatement.setInt(6, product.getSalesCount());
		            preparedStatement.setBoolean(7, product.isOnSale());
		            preparedStatement.setFloat(8, product.getDiscountPercent());
		            return preparedStatement;
		            
		        }, generatedKeyHolder);
		if(update == 1) {
			Integer id = generatedKeyHolder.getKey().intValue();
			product.setId(id);
			return product;
		}else {
			return null;
		}
		
    }

    @Override
    public Product updateProduct(Product product) {
    	String sql = "update product set name =?, description = ?, imagePath=?, categoryid =?, price=?, salesCount=?, isOnSale=?, discountPercent=? where id = ?";
		Category category = product.getCategory();
		int update = jdbcTemplate.update(sql,
				new Object[] {
						product.getName(),
						product.getDescription(),
						product.getImagePath(),
						category!=null?category.getId():6,
						product.getPrice(),
						product.getSalesCount(),
						product.isOnSale(),
						product.getDiscountPercent(),
						product.getId()});
		if(update == 1) {
			return product;
		}else {
			return null;
		}
		
    }

    @Override
    public boolean removeProductById(int productId) {
    	String sql = "delete from product where id = ?";
		int deleted =  jdbcTemplate.update(sql,new Object[] {productId});
		if(deleted==1) {
			return true;
		}else {
			return false;
		}
		
    }

    @Override
    public List<Category> fetchAllCategories() {
        List<Category> allCategories = new LinkedList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from category");
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
            PreparedStatement pst = con.prepareStatement("insert into category(name) values(?);");
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
            PreparedStatement pst = con.prepareStatement("update category set name = ? where categoryid = ?");
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
            PreparedStatement pst = con.prepareStatement("delete from category where categoryid = ? ;");
            pst.setInt(1, categoryId);
            int i = pst.executeUpdate();
        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return true;
    }

    @Override
    public void makeFavorite(int customerId, int productId) {
        try {
            PreparedStatement pst = con.prepareStatement("insert into favorite values (?, ?);");
            pst.setInt(1, customerId);
            pst.setInt(2, productId);
            int i = pst.executeUpdate();
        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
    }

    @Override
    public void removeFavorite(int customerId, int productId) {
        try {
            PreparedStatement pst = con.prepareStatement("delete from favorite where customerId=? and productId=?;");
            pst.setInt(1, customerId);
            pst.setInt(2, productId);
            int i = pst.executeUpdate();
        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
    }

    @Override
    public List<Product> fetchCustomerFavoriteProducts(int customerId) {
        return null; // todo
    }

    @Override
    public List<Product> search(String query) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE name LIKE ?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
