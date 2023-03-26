package ca.concordia.eats.dto;

public class User {
  
    private int userId;
    private String username;
    private String nameOfUser;      // ie.: user type: 'Customer' or 'Admin'
    private boolean loginStatus;

    public User() {
    }

    public User(Integer userId, String username, String nameOfUser, boolean loginStatus) {
        this.userId = userId;
        this.username = username;
        this.nameOfUser = nameOfUser;
        this.loginStatus = loginStatus;
    }
  
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(boolean loginStatus) {
        this.loginStatus = loginStatus;
    } 
}
