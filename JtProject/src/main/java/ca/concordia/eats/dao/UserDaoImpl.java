package ca.concordia.eats.dao;

import ca.concordia.eats.dto.User;
import ca.concordia.eats.dto.UserCredentials;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private Connection con;
    public UserDaoImpl() {
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "");
        } catch(Exception e) {
            System.out.println("Error connecting to the DB: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new LinkedList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            while (rs.next()) {
                allUsers.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
            }
        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }

        return allUsers;
    }


    @Override
    public User getUserById(int userId) {
        User user = new User();

        try {
            PreparedStatement pst = con.prepareStatement("select * from users where user_id = (?);");
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4));

        } catch (Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }

        return user;
    }


    @Override
    public User updateUser(User user) {
        try {
            PreparedStatement pst = con.prepareStatement("update users set username = ? where user_id = ?");
            pst.setString(1, user.getUsername());
            pst.setInt(2, user.getUserId());
            pst.executeUpdate();

        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return user;
    }


    @Override
    public User createUser(User user) {
        try {
            PreparedStatement pst = con.prepareStatement("insert into users (username) values(?);");
            pst.setString(1, user.getUsername());
            pst.executeUpdate();

        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return user;
    }


    @Override
    public boolean removeUser(int userId) {
        try {
            PreparedStatement pst = con.prepareStatement("delete from users where user_id = ? ;");
            pst.setInt(1, userId);
            pst.executeUpdate();
        
        } catch(Exception ex) {
            System.out.println("Exception Occurred: " + ex.getMessage());
        }
        return true;
    }

    public boolean checkUserByCredentials(UserCredentials userCredentials) {
        boolean userExists = false;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, userCredentials.getUsername());
            stmt.setString(2, userCredentials.getPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return userExists;
    }

    @Override
    public User fetchUserByCredentials(UserCredentials userCredentials) {
        User user = null;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, userCredentials.getUsername());
            stmt.setString(2, userCredentials.getPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // TODO: get all user data for user sesseion management
                user = new User();
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return user;
    }

}
