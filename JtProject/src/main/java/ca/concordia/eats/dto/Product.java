package ca.concordia.eats.dto;

public class Product {
    private Integer id;
    private String name;
    private String description;
    private String imagePath;
    private float price;
    private int salesCount;
    private boolean isOnSale;
    private float discountPercent;
    private Double rating;
    private Category category;

    public Product() {
    }

    public Product(Integer id, String name, String description, String imagePath, float price, int salesCount, boolean isOnSale, float discountPercent, Double rating, Category category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
        this.salesCount = salesCount;
        this.isOnSale = isOnSale;
        this.discountPercent = discountPercent;
        this.rating = rating;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    public boolean isOnSale() {
        return isOnSale;
    }

    public void setOnSale(boolean onSale) {
        isOnSale = onSale;
    }

    public float getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
 
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Product))
            return false;
        
        Product other = (Product)o;
        
        return this.id == other.id;
    }
    
    @Override
    public final int hashCode() {
    	
    	return this.id;
    }

}
