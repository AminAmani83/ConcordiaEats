package ca.concordia.eats.dto;

/**
 * UserCredential is the main Class for Users, Admin, Customer.
 */
public class UserCredentials {
    private String username;
    private String password;

    public UserCredentials() {
    }

    public UserCredentials(String username) {
        this.username = username;
    }

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
