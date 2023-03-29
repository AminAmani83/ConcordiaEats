package ca.concordia.eats.dto;

public class User {
  
    private int userId;
    private String username;
    private String role;      // ie.: user type: 'Customer' or 'Admin'
    private String email;           // this is a NOT NULL column in DB.
    private boolean loginStatus;

    public User() {
    }

    public User(Integer userId, String username, String role, String email) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.email = email;
    }

    public User(Integer userId, String username, String role, String email, boolean loginStatus) {
        this.userId = userId;
        this.username = username;
        this.role = role;
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
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
