package ca.concordia.eats.dao;

import ca.concordia.eats.dto.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new LinkedList<>();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "");
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
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "");
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
        return null;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public boolean removeUser(User user) {
        return true;
    }
    
}
