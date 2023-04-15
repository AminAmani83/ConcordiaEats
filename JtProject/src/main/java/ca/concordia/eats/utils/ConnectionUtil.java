package ca.concordia.eats.utils;

import ca.concordia.eats.dao.DAOException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionUtil {

    private static Connection con;

    public static Connection getConnection() throws IOException {

        if (con == null) {
            String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                    .replaceAll("%20", " ")
                    .replaceAll("test-classes", "classes");
            String dbConfigPath = rootPath + "db.properties";

            FileReader reader = new FileReader(dbConfigPath);
            Properties dbProperties = new Properties();
            dbProperties.load(reader);

            try {
                con = DriverManager.getConnection(dbProperties.getProperty("url"), dbProperties.getProperty("username"), dbProperties.getProperty("password"));
            } catch (Exception e) {
                System.out.println("Error connecting to the DB: " + e.getMessage());
            }
        }

        return con;
    }
}
