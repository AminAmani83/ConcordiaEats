package ca.concordia.eats.dto;

public class User extends UserCredentials {
  
    private int userId;
    private String role;            // ie.: user type: 'Customer' or 'Admin'
    private String email;           // this is a NOT NULL column in DB.
    private boolean loginStatus;    // not stored in DB.

    public User() {
    }

    public User(Integer userId, String username, String role, String email) {
        super(username);

        this.userId = userId;
        this.role = role;
        this.email = email;
    }

    public User(Integer userId, String username, String role, String email, boolean loginStatus) {
        super(username);

        this.userId = userId;
        this.role = role;
        this.email = email;
        this.loginStatus = loginStatus;
    }

    public User(Integer userId, String username, String password, String role, String email) {
        super(username, password);

        this.userId = userId;
        this.role = role;
        this.email = email;
    }

    public User(Integer userId, String username, String password, String role, String email, boolean loginStatus) {
        super(username, password);

        this.userId = userId;
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
