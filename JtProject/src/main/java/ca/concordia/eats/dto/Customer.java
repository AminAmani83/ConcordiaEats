package ca.concordia.eats.dto;

public class Customer extends User {
  
    private String address;
    private String phone;
    
    public Customer() {
    }

    public Customer(Integer userId, String username, String role, String address, String email, String phone) {
        super(userId, username, role, email);
        this.address = address;
        this.phone = phone;
    }

    public Customer(Integer userId, String username, String role, String address, String email, String phone, boolean loginStatus) {
        super(userId, username, role, email, loginStatus);
        this.address = address;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
