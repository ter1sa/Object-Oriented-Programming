package groupproject;
import java.util.Date;

/**
 * Revenue Report entity 
 * 
 * @author Venkataraman Sidhaarth
 *
 */


public class RevenueReport
{
  private OrderList orders;
/**
 * Class Constructor 
 * 
 * @param orders : the order        the OrderList object containing all the orders
 */
  public RevenueReport(OrderList orders)
  {
    this.orders = orders;

  }
/**
 * Print the revenue report and all the completed orders within the period
 * @param start        the starting date of the revenue period
 * @param end          the ending date of the revenue period
 */
  public void generateReport (Date start, Date end) 
  {
	System.out.println("\nREVENUE REPORT");
	System.out.println("Start Date : "+start);
	System.out.println("End Date : "+end);
    System.out.println("\n");
	double price;
    double memberPrice;
    double totalPrice;
    
    int orderSize, memberSize;

      orderSize = 0;
      memberSize = 0; 
      price = 0.0;
      memberPrice = 0.0;
      totalPrice = 0.0;

      for (int i=0; i<orders.getSize(); i++){
        if (orders.getOrders().get(i).getDate().before(end) && orders.getOrders().get(i).getDate().after(start)){
          if (orders.getOrders().get(i).isMember()){
            memberSize++;
            orderSize++;
            memberPrice+= orders.getOrders().get(i).getTotalPrice()*0.9;
            System.out.println("\nOrder " + i + "\n");
            orders.getOrders().get(i).printOrder();
            System.out.format("%n%-28s %7s %8s %9s", "Date","OrderID", "isMember", "Price SGD");
            System.out.format("%n%28s %7d %8s %9.2f ",
								orders.getOrders().get(i).getDate().toString(),
								orders.getOrders().get(i).getOrderID(), 
								String.valueOf(orders.getOrders().get(i).isMember()),
								orders.getOrders().get(i).getTotalPrice()*0.9);
            System.out.println("\n");

          }
          else {
        	  orderSize++;
            price += orders.getOrders().get(i).getTotalPrice();
            System.out.println("\nOrder " + i + "\n");
            orders.getOrders().get(i).printOrder();
            System.out.format("%n%-28s %7s %8s %9s", "Date","OrderID", "isMember", "Price SGD");
            System.out.format("%n%28s %7d %8s %9.2f",
								orders.getOrders().get(i).getDate().toString(),
								orders.getOrders().get(i).getOrderID(), 
								Boolean.toString(orders.getOrders().get(i).isMember()),
								orders.getOrders().get(i).getTotalPrice());
            System.out.println("\n");
          }
        }
      }
      totalPrice = memberPrice + price;
      if (orderSize == 0) {
    	  System.out.println("There were no orders made during this period.\n");
    	  return;
      }
      System.out.println("\n");
      System.out.format("%n%35s %-9.2f","Total revenue(SGD):" , totalPrice);
      System.out.format("%n%35s %-9d","Total no.of orders:" , orderSize);		
      System.out.format("%n%35s %-9d","No.of orders by members:" , memberSize);
      System.out.format("%n%35s %-9.2f","Revenue from non-members(SGD):" , price);
      System.out.format("%n%35s %-9.2f","Revenue from members(SGD):", memberPrice);
      System.out.println("\n");
    }
  
}
