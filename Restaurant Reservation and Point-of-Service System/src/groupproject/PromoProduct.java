package groupproject;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * PromoProduct entity
 * @author Teresa Zhang
 *
 */
public class PromoProduct implements Item{
	private String name;
	private String description;
	private double price;
	private boolean available;
	private int productID;
	private int category;
	private boolean isPromo;
	private Date startDate;
	private Date endDate;
	
	/**
	 * PromoProduct class constructor
	 * @param name Name of promoProduct
	 * @param description Description of promoProduct
	 * @param price Price of promoProduct
	 * @param productID ID of promoProduct
	 * @param category Category of promoProduct
	 * @param startDate start date of the validity period of PromoProduct
	 * @param endDate end date of the validity period of PromoProduct
	 */
	public PromoProduct(String name, String description, double price, int productID, int category, Date startDate, Date endDate) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.available = true;
		this.productID = productID;
		this.category = category;
		this.isPromo = true;
		this.startDate = startDate;
		this.endDate = endDate;
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
	 * @param name Name of promoProduct
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
	 * @param description Description of promoProduct
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
	 * @param price Price of promoProduct
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
	 * @return true if promoproduct is available, false if product is not available
	 */
	public boolean getAvailability() {
		return available;
	}
	
	/**
	 * set this available
	 * @param available Availability of promoProduct
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
	 * @param category Category of promoProduct
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
	 * get this startDate
	 * @return startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * set this startDate
	 * @param startDate start date of the validity period of PromoProduct
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * get this endDate
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * set this endDate
	 * @param endDate end date of the validity period of PromoProduct
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * print promoproduct
	 */
	public void showPromoProduct() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String date1 = formatter.format(startDate);
		String date2 = formatter.format(endDate);
		System.out.println(date1+" to "+date2);
		System.out.println("Name: "+getName());
		System.out.println("ItemID: "+ getProductID());
		System.out.println("Category: "+5);
		System.out.println("Description: "+getDescription());
		System.out.println("Price: "+ getPrice());
	}
}
