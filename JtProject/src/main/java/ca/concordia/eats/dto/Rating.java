package ca.concordia.eats.dto;

public class Rating {
  
    Map<Integer, Integer> customerRatings = new HashMap<Integer, Integer>();
    private List<Product> rateableProducts;
  
    public Initializing() {
        this.rateableProducts = new ArrayList<Product>();
    }
      
    public List<Product> getRateableProducts() {
        return new ArrayList(rateableProducts);
    }

    public void setRateableProducts(List<Product> rateableProducts) {
        this.rateableProducts = (rateableProducts == null) ? new ArrayList<Product>() : new ArrayList<Product>(rateableProducts);
    }
}
