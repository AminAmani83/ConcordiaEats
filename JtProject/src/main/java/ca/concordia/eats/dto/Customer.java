package ca.concordia.eats.dto;

public class Customer extends User {
  
    private String address;
    private String email;
    private String phone;
    
    public Customer(Integer userId, String username, String nameOfUser, String address, String email, String phone, boolean loginStatus) {
        super(userId, username, nameOfUser, loginStatus);
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
