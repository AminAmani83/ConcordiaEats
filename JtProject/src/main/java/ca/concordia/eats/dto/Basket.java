package ca.concordia.eats.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.concordia.eats.dao.DAOException;
import ca.concordia.eats.dao.PromotionDao;
import ca.concordia.eats.dao.PromotionDaoImpl;
import ca.concordia.eats.service.PromotionService;

public class Basket {
    
    private int basketId;
    private float totalPrice;
    private List<Product> lineItems;
    private Map<Product, Integer> products = new HashMap<>();
    
    public Basket() {
    }

    public Basket(int basketId, float totalPrice, List<Product> lineItems) {
        this.basketId = basketId;
        this.totalPrice = totalPrice;
        this.lineItems = lineItems;
    }
  
    public int getBasketId() {
        return basketId;
    }

    public void setBasketId(int basketId) {
        this.basketId = basketId;
    }
  
    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public List<Product> getLineItems() {
        return lineItems;
    }
    
    public void setLineItems(List<Product> lineItems) {
        this.lineItems = lineItems;
    }
    
        
    // This method adds a product to the cart, or increases its quantity if it is already in the cart.    
    public void addProduct(Product product) {
    	
    	if (products.containsKey(product)) {
    		products.replace(product, products.get(product) + 1);
    		
        } else {
        	
            products.put(product, 1);
        }   
    }

    // This method removes a product from the cart.
    public void removeProduct(Product product) {
        products.remove(product);
    }
     
    // This method returns a list of all products in the cart, along with their quantities.
    public List<Product> getProductsInCart(){
    	
    	List<Product> productList = new ArrayList<Product>();
		
    	for (Map.Entry<Product, Integer> entry : products.entrySet()) {
			
    		Product product = entry.getKey();
    		product.setSalesCount(entry.getValue());
			
    		productList.add(product);
    	}

    	return productList;
    }
      
    // This method calculates and returns the total cost of all products in the cart.
   public float getTotal(List<Promotion> activePromotions) {
        float total = 0;
        
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            
            if (product.isOnSale()) {
                total += product.getPrice() * (1 - product.getDiscountPercent() / 100.0f) * quantity;
            } else {
                total += product.getPrice() * quantity;
            }
        }

        // Apply promotions if present
        if (!activePromotions.isEmpty()) {
            Promotion promotion = activePromotions.get(0); // Assuming only one active promotion at a time
            if (promotion.getType().equals("SITEWIDE_DISCOUNT_10")) {
                total *= 0.9;
            } else if (promotion.getType().equals("SITEWIDE_DISCOUNT_20")) {
                total *= 0.8;
            }
        }
        
        return total;
    }
	
   // This method calculates and returns the sub-total cost of all products in the cart.
   public float getSubTotal() {
   	
   	float subTotal = 0;
   	
       for (Map.Entry<Product, Integer> entry : products.entrySet()) {
       	
       	Product product = entry.getKey();
       	
           int quantity = entry.getValue();
           
           if (product.isOnSale()) {
           	
           	subTotal += product.getPrice() * (1 - product.getDiscountPercent() / 100.0f) * quantity;
           	
           } else {
           	
           	subTotal += product.getPrice() * quantity;
           }
       }
       return subTotal;
   }
    
    // This method calculates and returns the taxes for the entire order.
    public double getTaxes() {
    	
        double tax = 0;
        double taxRate = 0.15;
        	
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            	
            Product product = entry.getKey();
            	
            int quantity = entry.getValue();
                
            if (product.isOnSale()) {
                	
                tax += (product.getPrice() * (1 - product.getDiscountPercent() / 100.0f) * quantity) * taxRate;
                	
            } else {
                	
                tax += (product.getPrice() * quantity) * taxRate;
            }
        }
        
        // Format the tax value as a string with one decimal place
        String formattedTax = String.format("%.1f", tax);
        
        // Convert the formatted string back to a double and return it
        return Double.parseDouble(formattedTax);
    }
    
    // This method calculates and returns the delivery fee.
    public double getDelivery(List<Promotion> activePromotions) {
    	
    	double delivery = 5.0;
   
    	if (!activePromotions.isEmpty()) {
            Promotion promotion = activePromotions.get(0); // Assuming only one active promotion at a time
            if (promotion.getType().equals("Free Shipping")) {
                delivery = 0.0;
            }
    	}
    	
        return delivery;
    }
	
    // This method updates the quantity of a product in the cart.
    public void updateProduct(Product product, int quantity) {
		
        if (products.containsKey(product)) {
        	
        	products.replace(product, quantity);
            	
            	if (quantity < 1) {
            		
            		products.remove(product);
           	}
        }
    }	
}
