package groupproject;

/**
 * Item interface
 * @author Teresa Zhang
 *
 */
public interface Item {
	
	/**
	 * get this name
	 * @return name
	 */
	public abstract String getName();
	

	/**
	 * set this name
	 * @param name Name of Item
	 */
	public abstract void setName(String name);
	

	/**
	 * get this description
	 * @return description
	 */
	public abstract String getDescription();
	

	/**
	 * set this description
	 * @param description Description of Item
	 */
	public abstract void setDescription(String description);
	

	/**
	 * get this price
	 * @return price
	 */
	public abstract double getPrice();
	
	/**
	 * set this price
	 * @param price Price of Item
	 */
	public abstract void setPrice(double price);
	
	/**
	 * get this productID
	 * @return productID
	 */
	public abstract int getProductID();
	
	/**
	 * get this available
	 * @return true if Item is available, false if Item is not available
	 */
	public abstract boolean getAvailability();
	
	/**
	 * set this available
	 * @param available Availability of Item
	 */
	public abstract void setAvailability(boolean available);
	
	/**
	 * get this category
	 * @return category
	 */
	public abstract int getCategory();
	
	/**
	 * set this category
	 * @param category Category of Item
	 */
	public abstract void setCategory(int category);
	
	/**
	 * get this isPromo
	 * @return true if item is PromoProduct, false if item is not PromoProduct
	 */
	public abstract boolean getIsPromo();
}
