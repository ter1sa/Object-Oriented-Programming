package groupproject;
import java.util.Date;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
/**
 * Order Entity
 * Stores all the OrderLines
 * @author Venkataraman Sidhaarth
 *
 */

public class Order {
 private int orderID;
 private ArrayList <OrderLine> orderlines;   
 private int staffID;
 private int tableID;
 private Date date;
 private boolean isMember;
 private boolean active; 
 private double totalPrice;
 private double totalPriceGST;
 
 /**
  * Class Constructor
  * @param orderID        OrderID
  * @param staffID        StaffID of the Staff taking the order 
  * @param tableID        TableID of the table the customers making the order are sitting at
  */
 
 public Order(int orderID, int staffID, int tableID){
  this.orderID = orderID;
  this.staffID = staffID;
  this.tableID = tableID;
  this.setActive(true);
  this.date = new Date();
  this.isMember = false;
  this.orderlines = new ArrayList <OrderLine>();
  this.totalPrice = 0; 
  this.totalPriceGST =0;
 }
 /**
  * Alternative class constructor with more parameters
  * @param orderID       OrderID
  * @param staffID       StaffID of staff taking the order
  * @param tableID       TableID of the table the customers making the orders are sitting at
  * @param date          date of the order
  * @param isMember      Whether the person making the order is a member of the restaurant
  * @param totalPrice    total price of the order
  * @param totalPriceGST total price of the order including GST
  * @param IsActive      Boolean tracking if the order is active
  */
 public Order(int orderID, int staffID, int tableID, Date date, boolean isMember,
     double totalPrice, double totalPriceGST, boolean IsActive){
  this.orderID = orderID;
  this.staffID = staffID;
  this.tableID = tableID;
  this.date = date;
  this.isMember = isMember;
  this.active = IsActive;
  this.orderlines = new ArrayList <OrderLine>();
  this.totalPrice = totalPrice; 
  this.totalPriceGST = totalPriceGST;
 }
 
 /**
  * addOrderLine to the order for the specified item and quantity
  * @param item             item being added
  * @param quantity         quantity of item being added
  */
 public void addOrderLine(Item item, int quantity) {
  int index;
  OrderLine o = new OrderLine(item,quantity);
  for (index = 0; index < orderlines.size(); index++) {
   if (orderlines.get(index).getProduct().getProductID() == item.getProductID()) {
    System.out.println("Item already in order!");
    return;
   }
  }
  orderlines.add(o);
 }
 
/** 
 * check if a particular product is already in the order 
 * @param product to be checked
 * @return 1 if the product is in and 0 if it is not in the OrderLine
 */
 public int checkProduct(Item product) {
	  int index;
	  for (index = 0; index < orderlines.size(); index++) {
	   if (orderlines.get(index).getProduct().getProductID() == product.getProductID()) {
		   return 1;
	   }
	  }
	  return 0;
 }
 /**
  * get the order size 
  * @return the size of the order 
  */
 public int getOrderSize(){
  return orderlines.size();
 }
 /**
  * get product based on the index of the product in the order
  * @param i the desired index 
  * @return the product
  */
 public Item getProdByIndex(int i){
  return orderlines.get(i).getProduct();
 }
 /**
  * get the product ID based on the index of the product in the order 
  * @param i the desired index 
  * @return the product ID 
  */
 public int getProdIDByIndex(int i){
	  return orderlines.get(i).getProductID();
 }
 /**
  * get the product quantity based on the index of the product in the order
  * @param i the desired index 
  * @return  the product quantity
  */
 public int getQtyByIndex(int i){
  return orderlines.get(i).getQuantity();
 }
 /**
  * remove an item from the order by removing its OrderLine
  * @param item  item to be removed from the order
  */
 public void removeOrderLine(Item item) {
  int index;
  for (index = 0; index < orderlines.size(); index++) {
   if (orderlines.get(index).getProduct().getProductID() == item.getProductID()) {
    orderlines.remove(index);
    System.out.println("Item removed!");
    return;
   }
  }
  System.out.println("Item does not exist in the Order!");
  
 }
 /**
  * Amending the OrderLine  
  * @param product       the product being amended 
  * @param quantity      the quantity the product is being amended to
  */
 public void amendAddOrderLine(Item product, int quantity) {
	  int index;
	  for (index = 0; index < orderlines.size(); index++) {
	   if (orderlines.get(index).getProduct().getProductID() == product.getProductID()) {
		   int q = orderlines.get(index).getQuantity();
		   quantity += q;
	    if (orderlines.get(index).setQuantity(quantity)) {
	     System.out.println("Quantity Amended!");
	     return;
	    }
	   }
	  }
	  System.out.println("Amend operation failed!");
 }
 /**
  * Amending the OrderLine
  * @param product     the product being amended 
  * @param quantity    the quantity the product is being amended to
  */
 public void amendFixedOrderLine(Item product, int quantity) {
  int index;
  for (index = 0; index < orderlines.size(); index++) {
   if (orderlines.get(index).getProduct().getProductID() == product.getProductID()) {
    if (orderlines.get(index).setQuantity(quantity)) {
     System.out.println("Quantity Amended!");
     return;
    }
   }
  }
  System.out.println("Amend operation failed!");
  
 }

 /**
  * closing the other once it's completed
  */
 public void closeOrder() {
  LocalDateTime now = LocalDateTime.now();  
  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); 
  System.out.println(dtf.format(now));  
  setActive(false);
 }
 /**
  * printing the order details: item, quantity and price in sgd 
  */
 public void printOrder() {
  int index;
  totalPrice =0;
  System.out.format("%n%-28s %7s %8s", "Item","Quantity", "Price SGD");
  for (index = 0; index < orderlines.size(); index++) {
	  System.out.format("%n%-28s %7d %8s", orderlines.get(index).getProduct().getName() ,orderlines.get(index).getQuantity(), orderlines.get(index).getOrderLinePrice());
	  totalPrice += orderlines.get(index).getOrderLinePrice();
  }
  System.out.printf("\nTotal Price: %.2f\n", totalPrice);
  if (this.isMember()) totalPrice *= 0.9;
  totalPriceGST = totalPrice * 1.07;
  System.out.printf("Total Price (with 7%%GST): %.2f\n",totalPriceGST);
 }
 
 /**
  * checking for if the order is made by a member
  * @return true if yes, order is made by a member
  */
 
 public boolean isMember(){
  return isMember;
 }
 /**
  * setting the isMember attribute to true if the order is indeed made by a member
  */
 public void orderIsByMember(){
  isMember = true;
 }
 /**
  * getting the total price of the order
  * @return totalPrice 
  */
 public double getTotalPrice(){
  int index;
  totalPrice =0;
  for (index = 0; index < orderlines.size(); index++) {
   totalPrice += orderlines.get(index).getOrderLinePrice();
  }
  return totalPrice;
 }
 /**
  * getting the total price of the order including GST
  * @return        totalPrice with GST
  */
 public double getGstPrice() {
  int index;
  totalPrice = 0;
  totalPriceGST = 0;
  for (index = 0; index < orderlines.size(); index++) {
   totalPrice += orderlines.get(index).getOrderLinePrice();
  }
  totalPriceGST = Math.round(totalPrice * 1.07);
  return totalPriceGST;
 }
 /**
  * getting the staffID of the staff taking the order 
  * @return staffID
  */
 public int getStaffID(){
  return staffID;
 }
 /**
  * setting the staffID of the staff taking the order
  * @param staffID       StaffID to be set
  */
 public void setStaffID(int staffID){
  this.staffID = staffID;
 }
 /**
  * getting the tableID of the table the customers are sitting at
  * @return tableID
  */
 public int getTableID(){
  return tableID;
 }
 /**
  * setting the tableID 
  * @param tableID   TableID to be set
  */
 public void setTableID(int tableID){
  this.tableID = tableID;
 }
 /**
  * get the date object of the date the order is being carried out on
  * @return    Return the date object of the order
  */
 public Date getDate(){
	  return date;
	 }
/**
 * get the orderID of the order	 
 * @return       return the orderID of the object
 */
 public int getOrderID(){
	  return orderID;
	 }

/**
 * check if order is active
 * @return        the boolean, true for active orders
 */
public boolean isActive() {
	  return active;
	 }
/**
 * method for setting the order as active when the order is created
 * @param active    tracking if an order is active, true for active orders
 */
public void setActive(boolean active) {
	  this.active = active;
	 }

	}