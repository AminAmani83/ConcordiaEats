package ca.concordia.eats.service;

import ca.concordia.eats.dto.UserCredentials;
import org.springframework.stereotype.Service;

import ca.concordia.eats.dao.UserDao;
import ca.concordia.eats.dto.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Implements the UserService.java interface.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAlUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public User createUser(User user) {
        return userDao.createUser(user);
    }

    @Override
    public boolean removeUser(int userId) {
        return userDao.removeUser(userId);
    }

    public boolean validateUserLogin(UserCredentials userCredentials) {
        return userDao.getUserByCredentials(userCredentials);
    }






}
