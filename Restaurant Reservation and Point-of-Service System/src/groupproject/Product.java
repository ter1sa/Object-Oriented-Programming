package groupproject;

/**
 * Product entity
 * @author Teresa Zhang
 *
 */
public class Product implements Item{
	private String name;
	private String description;
	private double price;
	private boolean available;
	private int productID;
	private int category;
	private boolean isPromo;
	

	/**
	 * Product class constructor
	 * available will always be true when create a new product
	 * @param name Name of Product
	 * @param description Description of Product
	 * @param price Price of Product
	 * @param productID ID of Product
	 * @param category Category of Product
	 */
	public Product(String name, String description, double price, int productID, int category){ 
		this.name = name;
		this.description = description;
		this.price = price;
		this.available = true;
		this.productID = productID;
		this.category = category;
		this.isPromo = false;
		
	}

	/**
	 * get this name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	

	/**
	 * set this name
	 * @param name Name of Product
	 */
	public void setName(String name) {
		this.name = name;
	}
	

	/**
	 * get this description
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	

	/**
	 * set this description
	 * @param description Description of Product
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	

	/**
	 * get this price
	 * @return price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * set this price
	 * @param price Price of Product
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * get this productID
	 * @return productID
	 */
	public int getProductID() {
		return productID;
	}
	
	/**
	 * get this available
	 * @return true if product is available, false if product is not available
	 */
	public boolean getAvailability() {
		return available;
	}
	
	/**
	 * set this available
	 * @param available Availability of Product
	 */
	public void setAvailability(boolean available) {
		this.available = available;
	}
	
	/**
	 * get this category
	 * @return category
	 */
	public int getCategory(){
		return category;
	}
	
	/**
	 * set this category
	 * @param category Category of Product
	 */
	public void setCategory(int category) {
		this.category = category;
	}
	
	/**
	 * get this isPromo
	 * @return true if item is PromoProduct, false if item is not PromoProduct
	 */
	public boolean getIsPromo() {
		return isPromo;
	}
	
	/**
	 * print product
	 */
	public void printProduct() {
		String pcategory;
		
		if (category == 1)
			pcategory = "Appetiser";
		else if (category == 2)
			pcategory = "Main";
		else if (category == 3)
			pcategory = "Dessert";
		else if (category == 4)
			pcategory = "Drink";
		else if (category == 5)
			pcategory = "Promo";
		else
			pcategory = "Not specified";
		
		System.out.println("Name: "+getName());
		System.out.println("ItemID: "+ getProductID());
		System.out.println("Category: "+pcategory);
		System.out.println("Description: "+getDescription());
		System.out.println("Price: "+ getPrice());
		}
}
