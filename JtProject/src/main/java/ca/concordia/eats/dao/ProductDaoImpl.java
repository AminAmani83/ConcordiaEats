package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Category;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Properties;
import java.io.FileReader;
import java.io.IOException;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Connection con;

    /**
     * Uses the db.properties file in resources to retrieve db connection parameters
     * username=<my-username>
     * password=<my-secret-password>
     * url=<jdbc-url>
     * @throws IOException
     */
    public ProductDaoImpl() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String dbConfigPath = rootPath + "db.properties";

		FileReader reader = new FileReader(dbConfigPath);
		Properties dbProperties = new Properties();
		dbProperties.load(reader);

		try {
			this.con = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties.getProperty("username"), dbProperties.getProperty("password"));
       } catch (Exception e) {
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
                new Object[]{productId}
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
                    preparedStatement.setString(3, product.getImagePath() == null ? "" : product.getImagePath());
                    Category category = product.getCategory();
                    if (category != null) {
                        preparedStatement.setInt(4, category.getId());
                    } else {
                        preparedStatement.setInt(4, 6);
                    }
                    preparedStatement.setFloat(5, product.getPrice());
                    preparedStatement.setInt(6, product.getSalesCount());
                    preparedStatement.setBoolean(7, product.isOnSale());
                    preparedStatement.setFloat(8, product.getDiscountPercent());
                    return preparedStatement;

                }, generatedKeyHolder);
        if (update == 1) {
            Integer id = generatedKeyHolder.getKey().intValue();
            product.setId(id);
            return product;
        } else {
            return null;
        }

    }

    @Override
    public Product updateProduct(Product product) {
        String sql = "update product set name =?, description = ?, imagePath=?, categoryid =?, price=?, salesCount=?, isOnSale=?, discountPercent=? where id = ?";
        Category category = product.getCategory();
        int update = jdbcTemplate.update(sql,
                new Object[]{
                        product.getName(),
                        product.getDescription(),
                        product.getImagePath(),
                        category != null ? category.getId() : 6,
                        product.getPrice(),
                        product.getSalesCount(),
                        product.isOnSale(),
                        product.getDiscountPercent(),
                        product.getId()});
        if (update == 1) {
            return product;
        } else {
            return null;
        }

    }

    @Override
    public boolean removeProductById(int productId) {
        String sql = "delete from product where id = ?";
        int deleted = jdbcTemplate.update(sql, new Object[]{productId});
        if (deleted == 1) {
            return true;
        } else {
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
            pst.executeUpdate();
        } catch (Exception ex) {
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
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return category;
    }

    @Override
    public boolean removeCategoryById(int categoryId) {
        try {
            PreparedStatement pst = con.prepareStatement("delete from category where categoryid = ? ;");
            pst.setInt(1, categoryId);
            pst.executeUpdate();
        } catch (Exception ex) {
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
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
    }

    @Override
    public void removeFavorite(int customerId, int productId) {
        try {
            PreparedStatement pst = con.prepareStatement("delete from favorite where userId=? and productId=?;");
            pst.setInt(1, customerId);
            pst.setInt(2, productId);
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
    }

    @Override
    public List<Product> fetchCustomerFavoriteProducts(int customerId) {
        List<Product> customerFavoriteProducts = new LinkedList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from favorite join product on favorite.productId = product.id join category on product.categoryId = category.id where userId=" + customerId + ";");
            while (rs.next()) {
                customerFavoriteProducts.add(new Product(rs.getInt(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getFloat(7),
                        rs.getInt(8), rs.getBoolean(9), rs.getFloat(10), rs.getDouble(11),
                        new Category(rs.getInt(12), rs.getString(13))));
            }
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return customerFavoriteProducts;
    }

    /**
     * Helper method used in rateProduct.
     */
    @Override 
    public int fetchRatingByProductIdAndCustomerId(int customerId, int productId) {

        int currentRating = -1;     // default value

        try {
            PreparedStatement pst = con.prepareStatement("select rating from rating where userId = ? and productId = ?;");
            pst.setInt(1, customerId);
            pst.setInt(2, productId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                currentRating = rs.getInt(1);
            }
            
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }

        return currentRating;
    }


    /**
     * Helper method used in rateProduct - used to insert a new rating if 
     * this is the first time this customer rates this product.
     */
    @Override
    public void insertNewRating(int customerId, int productId, int rating) {
        try {
            PreparedStatement pst = con.prepareStatement("insert into rating (userId, productId, rating) values (?, ?, ?);");
            pst.setInt(1, customerId);
            pst.setInt(2, productId);
            pst.setInt(3, rating);
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
    } 

    
    /**
     * Helper method used in rateProduct - used to update a rating if one
     * exists already for this product and this customer.
     */
    @Override
    public void updateCurrentRating(int customerId, int productId, int rating) {
        try {
            PreparedStatement pst = con.prepareStatement("update rating set rating = ? where userId = ? and productId = ?;");
            pst.setInt(1, rating);
            pst.setInt(2, customerId);
            pst.setInt(3, productId);
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
    }

    /**
     * rateProduct either updates a current rating or inserts a new rating.
     */
    @Override
    public void rateProduct(int customerId, int productId, int rating) {
        try {
            int currentRating = fetchRatingByProductIdAndCustomerId(customerId, productId);

            if (currentRating==-1) {
                insertNewRating(customerId, productId, currentRating);
            } else if (currentRating>=0 & currentRating<=5) {
                updateCurrentRating(customerId, productId, currentRating);
            } else {
                System.out.println("currentRating value is outside valid bounds.");
            }

        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
    }


    /**
     * customerRatings is defined as <productId, rating> for a specified customer.
     */
    @Override
    public Map<Integer, Integer> fetchAllCustomerRatings(int customerId) {

        Map<Integer, Integer> customerRatings = new HashMap<Integer, Integer>();    
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select productId, rating from rating where userId=" + customerId + ";");
            while (rs.next()) {
                customerRatings.put(rs.getInt(1), rs.getInt(2));
            }
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return customerRatings;
    }

    /**
     * Retrieves all products that were already purchased by a given customer so that
     * this customer is allowed to rate them.
     */
    @Override
    public List<Product> fetchPastPurchasedProducts(int customerId) {

        String sqlQuery = "SELECT p.id, p.name, p.description, p.imagePath, p.price, p.salesCount, p.isOnSale, p.discountPercent, c.id, c.name FROM product p  JOIN category c on p.categoryid = c.id WHERE p.id IN (SELECT DISTINCT(productId) FROM purchase_details WHERE purchaseId IN (SELECT pur.id FROM purchase pur WHERE userId = ?));";
        List<Product> pastPurchaseProducts = new LinkedList<>();

        try {
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setInt(1, customerId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                pastPurchaseProducts.add(new Product(rs.getInt(1),          // id
                                                    rs.getString(2),        // name
                                                    rs.getString(3),        // description    
                                                    rs.getString(4),        // image path
                                                    rs.getFloat(5),         // price
                                                    rs.getInt(6),           // sales count  
                                                    rs.getBoolean(7),       // is on sale
                                                    rs.getFloat(8),         // discountPercent
                                                    new Category(rs.getInt(9), rs.getString(10))       /// category
                                                    )
                                        );
            }
         } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return pastPurchaseProducts;
    }


    /**
     * Helper method that facilitates handling of potential new rating.
     */
    @Override
    public boolean hasPurchased(int customerId, int productId) {
        String sqlQuery = "SELECT DISTINCT(productId) FROM purchase_details WHERE purchaseId in (SELECT id FROM purchase WHERE userId = ?) AND productId = ? )";
        boolean hasPurchased = false;

        try {
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setInt(1, customerId);
            pst.setInt(2, productId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                hasPurchased = true;
            }
         } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }

        return hasPurchased;
    }



    @Override
    public List<Product> search(String query) {
        List<Product> products = new ArrayList<>();
        try {
            String sql = "SELECT * FROM product WHERE name LIKE ?";
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

