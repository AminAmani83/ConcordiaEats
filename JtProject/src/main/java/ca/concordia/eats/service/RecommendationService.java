package ca.concordia.eats.service;


import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.Product;
import ca.concordia.eats.dto.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public interface RecommendationService {
    // CRUD USER

    public List<Product> FetchPresonanllizedRecomendation(Customer customer);
	public  List<Product> MostSerachedProducts(Customer customer);




}
