package groupproject;
/**
 * OrderLine entity
 * @author Venkataraman Sidhaarth
 *
 */

public class OrderLine {
	private Item product;
	private int quantity;
	/**
	 * Class constructor
	 * @param item         The item object that is being ordered 
	 * @param quantity     The quantity of item objects being ordered 
	 */

	public OrderLine(Item item, int quantity){
		  this.product = item;
		  this.quantity = quantity;
		  }
	/**
	 * Alternative class constructor initiating the OrderLine with default values for the parameters 
	 */
	public OrderLine(){
		  this.product = null;
		  this.quantity = 0;
		  }
	/**
	 * Get the quantity being ordered
	 * @return quantity
	 */
	  
	public int getQuantity() {
		  return this.quantity;
	  }
	/**
	 * Set a new quanity, amending the OrderLine
	 * @param newQuantity    the new quantity to be amended to
	 * @return true if the set method was successful in setting a new quantity
	 */
	public boolean setQuantity(int newQuantity) {
		if (newQuantity >0) {
			this.quantity = newQuantity;
			return true;
		}
		
		else {
			System.out.println("Invalid quantity entered!");
			return false;
		}
	}
	
	/**
	 * Get the OrderLine price
	 * @return the total price for the OrderLine
	 */
	  
	public double getOrderLinePrice() {
		  return this.quantity * this.product.getPrice();
	  }
	/**
	 * get the item in the OrderLine
	 * @return product
	 */
	public Item getProduct() {
		return this.product;
	}
	/**
	 * get the productID of the Item 
	 * @return ProductID
	 */
	public int getProductID() {
		return this.product.getProductID();
	}

}
