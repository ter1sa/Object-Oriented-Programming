package groupproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * Implements methods to keep track of orders, tables, products,reservations and staff
 * Implements methods to allow creation, search, update and deletion of orders.
 * Reads stored orders from text file upon instantiation,
 * writes to text file every time orders are created or deleted.
 * @author Samantha Tan 
 *
 */

public class OrderList implements Indexable
{
 private TableManager tables;
 private ArrayList <Order> orders;
 private int activeOrders;
 
 BufferedWriter out;
 /**
  * Class contructor, instantiates ArrayList of Orders, reads stored orders from text file
  * @param menu         menu object containing all the items available to order 
  * @param tables       TableManager object that keep tracks of the available tables
  */
  public OrderList(Menu menu, TableManager tables)
  {
    this.tables = tables;
    orders = new ArrayList <Order>();
    activeOrders = 0;
  
    try {
  File file = new File("orderlist.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList<String>();
        String line = br.readLine();
        int tempOrderID;
  int tempStaffID;
  int tempTableID;
  int year, month, date, hour, minute;
  boolean tempIsMember;
  boolean tempIsActive;
  double tempTotalPrice;
  double tempTotalPriceGST;
  int tempOrderSize;
  Calendar orderTime = Calendar.getInstance();
        while(line != null) {
            lines.add(line.replace(">", ""));
            line = br.readLine();
        }
    int i=0;
    
 while (i<lines.size()){
 tempOrderID = Integer.parseInt(lines.get(i));
 tempStaffID = Integer.parseInt(lines.get(i+1));
 tempTableID= Integer.parseInt(lines.get(i+2));
 
 year = Integer.parseInt(lines.get(i+3));
 month = Integer.parseInt(lines.get(i+4));
 date = Integer.parseInt(lines.get(i+5));
 hour = Integer.parseInt(lines.get(i+6));
 minute = Integer.parseInt(lines.get(i+7));
 
 tempIsMember = Boolean.parseBoolean(lines.get(i+8));
 tempIsActive = Boolean.parseBoolean(lines.get(i+9));
 tempTotalPrice = Double.parseDouble(lines.get(i+10));
 tempTotalPriceGST = Double.parseDouble(lines.get(i+11));
 String[] prodlist = lines.get(i+12).split("\\|");
 tempOrderSize = Integer.parseInt(lines.get(i+13));
 String[] qtylist = lines.get(i+14).split("\\|");
 
 orderTime.set(Calendar.YEAR, year);
 orderTime.set(Calendar.MONTH, month-1);
 orderTime.set(Calendar.DAY_OF_MONTH, date);
 orderTime.set(Calendar.HOUR_OF_DAY, hour);
 orderTime.set(Calendar.MINUTE, minute);
 
 
 Order order = new Order(tempOrderID, tempStaffID, tempTableID, orderTime.getTime(), tempIsMember, tempTotalPrice, tempTotalPriceGST, tempIsActive);
 if (tempIsActive == true) {
	 tables.occupy(tempTableID);
 }
 
 for(int j=0; j<tempOrderSize; j++) {
  order.addOrderLine(menu.getProductByID(Integer.parseInt(prodlist[j])),Integer.parseInt(qtylist[j]));
 }
 orders.add(order);
 i=i+16;
 }

    br.close();
    }
    
         catch(IOException e){
         System.out.println("There was a problem:" + e);
     }
   catch (NumberFormatException e) {
   }
  }

  /**
   * writeOrder method to write the order details to the text file
   */
  public void writeOrder(){
     try{
     Calendar date = Calendar.getInstance();
     int year, month, day, hour, minute;
      out = new BufferedWriter(new FileWriter("orderlist.txt",false)); 
      for(int counter=0;counter<orders.size();counter++){
        date.setTime(orders.get(counter).getDate());
        year = date.get(Calendar.YEAR);
        month = date.get(Calendar.MONTH);
        day = date.get(Calendar.DAY_OF_MONTH);
        hour = date.get(Calendar.HOUR_OF_DAY);
        minute = date.get(Calendar.MINUTE);
        out.write(String.valueOf(orders.get(counter).getOrderID())+"\n"+
          String.valueOf(orders.get(counter).getStaffID())+"\n"+ 
          String.valueOf(orders.get(counter).getTableID())+"\n"+
          String.valueOf(year)+"\n"+String.valueOf(month+1)+"\n"+String.valueOf(day)+"\n"+
          String.valueOf(hour)+"\n"+String.valueOf(minute)+"\n"+
          String.valueOf(orders.get(counter).isMember())+"\n"+
          String.valueOf(orders.get(counter).isActive())+"\n"+
          String.valueOf(orders.get(counter).getTotalPrice())+"\n"+
          String.valueOf(orders.get(counter).getGstPrice())+"\n");
        for(int counter1=0;counter1<orders.get(counter).getOrderSize();counter1++){
         out.write(String.valueOf(orders.get(counter).getProdIDByIndex(counter1))+"|");
        }
     out.write("\n"+String.valueOf(orders.get(counter).getOrderSize())+"\n");
        for(int counter1=0;counter1<orders.get(counter).getOrderSize();counter1++){
         out.write(String.valueOf(orders.get(counter).getQtyByIndex(counter1))+"|");
        }
      out.newLine();
      out.newLine();
      }
   System.out.println("File Written");
      out.close();
      }
      catch(IOException e){
      System.out.println("There was a problem:" + e);
      }
 }
  /**
   * Create order method for creating a new order, displaying it and occupying the respective table when called. The order will be added to active orders
   * @param order           the order object to be created
   * @param tableID         the tableID that the order is taking place at
   */
  public void createOrder(Order order, int tableID) {
		orders.add(order);
		tables.occupy(tableID);
		System.out.println("Order creation successful");
		this.viewOrder(order);
		writeOrder();
		activeOrders++;
}
  /**
   * update order method for when order details need to be modified for a particular order
   * @param order             the updated order that will be used to replace the old order
   * @param index             the index of the order that needs to be modified
   */
	public void update(Order order, int index){
		System.out.println("Order update successful");
		this.viewOrder(order);
		orders.remove(index); //remove old order
		orders.add(order); //re-add updated order
		writeOrder();
	}
	/**
	 * remove Order method to remove an order when needed
	 * @param orderID     the orderID of the order that needs to be removed
	 */
public void removeOrder(int orderID){
		//remove order
		int index;
		Order order;
		if(!this.checkOrderById(orderID))
			System.out.println("Error : Invalid order ID");
		else{ //if order exists
			index = this.getIndexByID(orderID);
			order = orders.get(index);
			if(!order.isActive()){
				System.out.println("\nOrder has already been completed. Cannot cancel this order");
				return;
			}
			this.viewOrder(order);
			tables.vacate(order.getTableID());
			orders.remove(index);
			writeOrder();
			activeOrders--;
			System.out.println("\nOrder cancelled. Table " +order.getTableID() + " is now vacant");
		}
		
	}

/**
 * method to print the order
 * @param order to be printed 
 */
 public void viewOrder(Order order)
 {
	 order.printOrder();
 }
/**
 * method to check if the order is exists by searching by OrderID
 * @param id       orderID to be searched
 * @return        true if order exists if not retun false 
 */
 public boolean checkOrderById(int id)
  {
    for(int i=0;i<orders.size();i++)
      {
     if(orders.get(i).getOrderID() == id)
      return true;
      }
    return false;
 }
/**
 * method to print all the orders in the order list
 */
 public void printOrderList()
 {
	 System.out.print("No of Orders: "+orders.size()+"\n");
	 System.out.print("\n");
	  for(int i=0;i<orders.size();i++)
	  {
		  System.out.print("Order ID: "+orders.get(i).getOrderID()+"\n");
		  System.out.print("Active: "+orders.get(i).isActive()+"\n");
		  System.out.print("Table ID: "+orders.get(i).getTableID()+"\n");
		  System.out.print("Price: "+orders.get(i).getTotalPrice()+"\n");
		  System.out.print("Price(Incl GST): "+orders.get(i).getGstPrice()+"\n");
		  System.out.print("Product - Quantity:\n");
		  for(int j=0;j<orders.get(i).getOrderSize();j++) {
			  System.out.print(orders.get(i).getProdIDByIndex(j)+" - ");
			  System.out.print(orders.get(i).getQtyByIndex(j)+"\n");
		  }
		  Date date = orders.get(i).getDate();
		  System.out.print("Date: "+ date.toString()+"\n");
		  System.out.print("\n");
	  }
 }
 /**
  * method to print all the orderIDs of the orders that are active
  */
 public void printOrderId()
 {
	  for(int i=0;i<orders.size();i++)
	  {
		  if(orders.get(i).isActive())
		  System.out.print(orders.get(i).getOrderID()+"\n");
	  }
 }
 /**
  * method to print the tableIDs of all the tables that have active orders at the moment
  */
 public void printActiveTableId()
 {
	  for(int i=0;i<orders.size();i++)
	  {
		  if(orders.get(i).isActive())
		  System.out.print(orders.get(i).getTableID()+"\n");
	  }
 }
 /**
  * method to get the active orderID of a particular order based on the tableID 
  * @param TableID      ID of table that order is associated with
  * @return             OrderID of the order happening at that table 
  */
 public int getActiveOrderByTableID(int TableID){
	 for(int i=0;i<orders.size();i++)
	  {
		  if(orders.get(i).isActive()) {
			  if(orders.get(i).getTableID() == TableID) {
				  return orders.get(i).getOrderID();
			  }
		  }
	  }
	 return -1;
 }
 /**
  * method to check if there are any active orders at the moment
  * @return       true if there are no active orders, and false if there is at least one active order 
  */
 public boolean checkActiveOrders()
 {
	 boolean flag = true;
	  for(int i=0;i<orders.size();i++)
	  {
		  if(orders.get(i).isActive())
		  flag = false;
	  }
	  return flag;
 }
 /**
  * method to create an ID for an order
  * @return the orderID created 
  */
 public int generateID()
 {
	 int lastID;
	 for(int i=0;i<orders.size();i++)
	 {
		 if(!checkOrderById(i))
		 {
			 return i;
		 }
	 }
	 if (orders.size()==0)
	 {
		 lastID=0;
	 }
	 else
	 {
		 lastID = orders.get(orders.size()-1).getOrderID();
	 }
	 while(checkOrderById(lastID))
	 {
		 lastID++;
	 }
	 return lastID;
}
  /**
   * method to get the index of an order in the OrderList based on the orderID provided 
   * @return      the 
   */
  public int getIndexByID(int id)
  {
  for(int i=0;i<orders.size();i++)
    {
      if(orders.get(i).getOrderID() == id)
      {
        return i;
      }
}
  return -1;
 }
  /**
   * method to get the order Object 
   * @param id      id of the order object to be returned
   * @return the Order Object possessing the requested ID 
   */
 public Order getOrderByID(int id)
 {
	 for(int i=0;i<orders.size();i++)
	 {
		 if(orders.get(i).getOrderID() == id)
		 {
			 return orders.get(i);
		 }
	 }
	return null;
 }
 
/**
 * method to calculate the sum of the items in a particular order
 * @param order        the order for which the sum is to be calculated 
 * @return             the calculated sum
 */
 public double calculateSum(Order order)
  {
	double price = order.getTotalPrice();
	return price;
 }
  /**
   * method to get the number of orders currently active 
   * @return the number of active orders
   */
  public int getActiveOrders()
  {
  return activeOrders;
 }
 /**
  * method to get the total number of orders stored in the OrderList
  * @return the number of orders
  */
 public int getSize()
  {
  return orders.size();
 }
  /**
   * method to close an order, vacate table and create an invoice when order is completed and payment is being made
   * @param orderID           orderID of order to be closed
   * @param tableID           tableID of table where order to be closed took place 
   * @param order             the order object
   * @param index             the index of the order to be removed
   */
 public void createInvoice(int orderID, int tableID, Order order, int index)
  {
  tables.vacate(tableID);
  order.closeOrder();
  orders.remove(index);
  orders.add(order);
  writeOrder();
  activeOrders--;
 }
 /**
  * method to return the array list storing all the orders
  * @return array list of orders
  */
 public ArrayList<Order> getOrders () {
		// TODO Auto-generated method stub
		return orders;
	}
}