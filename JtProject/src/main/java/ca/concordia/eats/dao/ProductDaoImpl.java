package ca.concordia.eats.dao;

import ca.concordia.eats.dto.*;
import ca.concordia.eats.utils.ConnectionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
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
     *
     * @throws IOException
     */
    public ProductDaoImpl() throws IOException {
        this.con = ConnectionUtil.getConnection();
    }

    @Override
    public List<Product> fetchAllProducts() {
        return jdbcTemplate.query(
                "select p.id, p.name, p.description, p.imagePath, p.price, p.salesCount, p.isOnSale, p.discountPercent, r.avg_rating as rating, c.id as categoryId, c.name as categoryName from product p join category c on p.categoryid = c.id left join (select avg(rating) as avg_rating, productId from rating group by productId) r on r.productId = p.id order by p.id desc",
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
        String sql = "select p.id, p.name, p.description, p.imagePath, p.price, p.salesCount, p.isOnSale, p.discountPercent, r.rating, c.id as categoryId, c.name as categoryName from product p left join category c on p.categoryid = c.id left join (select avg(rating) as rating, productId from rating where productId = ?) r on r.productId = p.id where p.id = ?";

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
                new Object[]{productId, productId}
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
            PreparedStatement pst = con.prepareStatement("update category set name = ? where id = ?");
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
        int rowsAffected = 0;
        try {
            PreparedStatement pst = con.prepareStatement("delete from category where id = ? ;");
            pst.setInt(1, categoryId);
            rowsAffected = pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return rowsAffected == 1;
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
    public List<Integer> fetchCustomerFavoriteProductIds(int customerId) {
        List<Integer> customerFavoriteProductIds = new LinkedList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select productId from favorite where userId=" + customerId + ";");
            while (rs.next()) {
                customerFavoriteProductIds.add(rs.getInt(1));
            }
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return customerFavoriteProductIds;
    }

    /**
     * Helper method when one wants to remove a customer; its favorite history needs to be cleared first.
     */
    @Override
    public void removeAllFavoritesByCustomerId(int customerId) {
        try {
            PreparedStatement pst = con.prepareStatement("delete from favorite where userId=?;");
            pst.setInt(1, customerId);
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
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

            if (currentRating == -1) {
                insertNewRating(customerId, productId, rating);
            } else if (currentRating >= 0 & currentRating <= 5) {
                updateCurrentRating(customerId, productId, rating);
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
        List<Product> pastPurchasedProducts = new ArrayList<>();

        try {
            PreparedStatement pst = con.prepareStatement(sqlQuery);
            pst.setInt(1, customerId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                pastPurchasedProducts.add(new Product(rs.getInt(1),          // id
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
        return pastPurchasedProducts;
    }


    @Override
    public List<Product> search(String query, int userId) {
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
                product.setPrice(rs.getFloat("price"));
                product.setDescription(rs.getString("description"));
                product.setImagePath(rs.getString("imagePath"));
                product.setRating(calculateAvgProductRating(rs.getInt("id")));
                products.add(product);
                SearchHistory searchHistory = saveSearchHistoryToDatabase(query, userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;

    }


    @Override
    public SearchHistory saveSearchHistoryToDatabase(String SearchQuery, int userId) throws SQLException {
        User user = new User();
        SearchHistory searchHistory = new SearchHistory();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String sql = "INSERT INTO search_history(userId, phrase, timeStamp) VALUES (?, ?,?);";
        PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, userId);
        stmt.setString(2, SearchQuery);
        stmt.setTimestamp(3, timestamp);
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Creating promotion failed, no rows affected.");
        }
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                // searchHistory.setSearchHistoryId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating promotion failed, no ID obtained.");
            }
        }
        return searchHistory;
    }

    @Override
    public void removeAllSearchHistoryByCustomerId(int customerId) {
        try {
            PreparedStatement pst = con.prepareStatement("delete from search_history where userId=?;");
            pst.setInt(1, customerId);
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
    }


    /**
     * This method is used in the background to calculate the average product rating for one product.
     * This average rating is displayed on the Front-End as filled stars.
     */
    @Override
    public Double calculateAvgProductRating(int productId) {

        Double avgRating = 0.0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select AVG(rating) from rating where productId=" + productId + ";");
            if (rs.next()) {
                avgRating = rs.getDouble(1);
            }
            
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return avgRating;
    }

    @Override
    public void removeAllRatingsByCustomerId(int customerId) {
        try {
            PreparedStatement pst = con.prepareStatement("delete from rating where userId=?;");
            pst.setInt(1, customerId);
            pst.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
    }



    
    
    @Override
    public Map<Integer, Integer> fetchAllProductSumSalesQuantity(){
    	Map<Integer, Integer> productSumSalesQuantity = new HashMap<Integer, Integer>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select productId, Sum(quantity) as SalesQuantity from purchase_details Group by productId;");
            while (rs.next()) {
            	productSumSalesQuantity.put(rs.getInt(1), rs.getInt(2));
            }
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return productSumSalesQuantity;	
    	
    }
    @Override
    public Map<Integer, Double> fetchAllProductAvgRatings(){
    	Map<Integer, Double> productAvgRatings = new HashMap<Integer, Double>();
        try {
        	List<Product> products = fetchAllProducts();
        	for (Product p : products) {
            	productAvgRatings.put(p.getId(), calculateAvgProductRating(p.getId()));
            }
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return productAvgRatings;
    }

}

