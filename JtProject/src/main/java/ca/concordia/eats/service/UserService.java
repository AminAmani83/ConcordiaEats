package ca.concordia.eats.service;

import ca.concordia.eats.dto.User;

import java.util.List;

public interface UserService {
    // CRUD USER
    public List<User> getAlUsers();
    public User getUserById(int userId);
    public User updateUser(User user);
    public User createUser(User user);
    public boolean removeUser(User user);

}
