package ca.concordia.eats.dao;

import ca.concordia.eats.dto.User;

import java.util.List;

/**
 * User Data Access Object.
 * Interface implemented in UserDaoImpl.java
 */
public interface UserDao {
    // CRUD USER
    public List<User> getAllUsers();
    public User getUserById(int userId);
    public User updateUser(User user);
    public User createUser(User user);
    public boolean removeUser(int userId);

}
