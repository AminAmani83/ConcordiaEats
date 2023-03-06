package ca.concordia.eats.dto;

public class Recommendation {
  
    private List<Product> customerSearchedProducts;
  
    public Initialized() {
        this.customerSearchedProducts = new ArrayList<Product>();
    }
      
    public List<Product> getCustomerSearchedProducts() {
        return new ArrayList(customerSearchedProducts);
    }

    public void setCustomerSearchedProducts(List<Product> customerSearchedProducts) {
        this.customerSearchedProducts = (customerSearchedProducts == null) ? new ArrayList<Product>() : new ArrayList<Product>(customerSearchedProducts);
    }
}
