package ca.concordia.eats.dao;

import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.Product;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao {
	private ProductDao productDao;
	
	@Override
	public List<Product> fetchCustomerSearchedProduct(Customer customer)     {
    	List<Product> products = productDao.fetchAllProducts();
		List<Product>  searchedProducts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "")) {
            // Create a statement
            String query = "SELECT * FROM search_history";
            Statement statement = conn.createStatement();
            
            // Execute the query and get the result set
            ResultSet resultSet = statement.executeQuery(query);
            
            // Create a HashMap to hold the search phrases and their counts
            
            // Loop through the result set and add each search phrase to the HashMap
            while (resultSet.next()) {
        		String  searchedphrase = resultSet.getString("phrase");
                int userId = resultSet.getInt("userId");
                if(userId==customer.getUserId())
                	  for (Product product : products) {
                          if (product.getName().toLowerCase().contains(searchedphrase.toLowerCase())) {
                        	  searchedProducts.add(product);

                          }
                      }
            }

            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
  		return searchedProducts;
    }


    
}
