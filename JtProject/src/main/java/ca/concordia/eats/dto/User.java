package ca.concordia.eats.dto;

public class User {
  
    private int userId;
    private String username;
    private String nameOfUser;      // ie.: user type: 'Customer' or 'Admin'
    private String email;           // this is a NOT NULL column in DB.
    private boolean loginStatus;

    public User() {
    }

    public User(Integer userId, String username, String nameOfUser, String email) {
        this.userId = userId;
        this.username = username;
        this.nameOfUser = nameOfUser;
        this.email = email;
    }

    public User(Integer userId, String username, String nameOfUser, String email, boolean loginStatus) {
        this.userId = userId;
        this.username = username;
        this.nameOfUser = nameOfUser;
        this.email = email;
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

    public void setId(int userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
