package ca.concordia.eats.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ca.concordia.eats.dto.Product;


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
    
        
    public void addProduct(Product product) {
    	
    	if (products.containsKey(product)) {
    		
    		products.replace(product, products.get(product) + 1);
    		
        } else {
        	
            products.put(product, 1);
        }   
    }

    public void removeProduct(Product product) {
    	
        products.remove(product);
    }
        
    public List<Product> getProductsInCart(){
    	
    	List<Product> productList = new ArrayList<Product>();
		
    	for (Map.Entry<Product, Integer> entry : products.entrySet()) {
			
    		Product product = entry.getKey();
    		product.setSalesCount(entry.getValue());
			
    		productList.add(product);
    	}

    	return productList;
    }
        
    public float getTotal() {
    	
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
        return total;
    }

	public void updateProduct(Product product, int quantity) {
		
        if (products.containsKey(product)) {
        	
        	products.replace(product, quantity);
            	
            	if (quantity < 1) {
            		
            		products.remove(product);
            }
        }
	}	
}
