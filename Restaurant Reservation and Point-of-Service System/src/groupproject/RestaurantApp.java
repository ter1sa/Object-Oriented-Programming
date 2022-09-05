package groupproject;

import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Contains the main method
 * @author Shenal Devinda
 * @author Tan Yu Ling
 * @author Venkataraman Sidhaarth
 * @author Samantha Tan
 * @author Teresa Zhang
 */
public class RestaurantApp {
	
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * Main method which contains a switch statement to carry out various actions requested by the user
	 * Initialisation of object classes takes place, creating the arraylists within them
	 * @param args Command Line Argument
	 * @throws ParseException Exception Handling
	 */
	public static void main(String[] args) throws ParseException {
		
		int choice1;
		boolean end = false;
		// Set Opening hours, booking will be available until 1hr before close
		int OpeningHour = 18;
		int ClosingHour = 22;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		//initializes munu, reservation manager, table manager and orderlist
		Menu menu = new Menu();
		ReservationManager reservationMgr = new ReservationManager();
		TableManager tableMgr = new TableManager();
		OrderList orderlist = new OrderList(menu, tableMgr);
		RevenueReport rr = new RevenueReport(orderlist);
		
		do {
			System.out.println("What do you want to do:");
			System.out.println("1. View Menu");
			System.out.println("2. Create/ Update/ Remove Menu Product");
			System.out.println("3. Create/ Update/ Remove Menu Promotional Product");
			System.out.println("4. Create Order"); 
			System.out.println("5. View Active Orders");
			System.out.println("6. Update/Remove Order");
			System.out.println("7. Create Reservation Booking"); 
			System.out.println("8. Check/Remove Reservation Booking"); 
			System.out.println("9. Check Table Availability");
			System.out.println("10. Print Order Invoice");
			System.out.println("11. Print All Orders");
			System.out.println("12. Print Sales Revenue Report by Period");
			System.out.println("13. Quit"); 
			
			choice1 = sc.nextInt();
			
			switch(choice1) {
			//View Menu
			case 1: menu.printMenu();
					break;
			//Create/ Update/ Remove Menu Product
			case 2:
				int choice2;
				do{
					System.out.println("What do you want to do:");
					System.out.println("1. Create Menu Product");
					System.out.println("2. Update Menu Product");
					System.out.println("3. Remove Menu Product");
					System.out.println("4. Back");

					choice2 = sc.nextInt();

					switch(choice2){
					
					//Create Menu Product
					case 1:{
						System.out.println("--Create Menu Product--");
						System.out.println("Please enter the name of the product: ");
						String setname = sc.nextLine();
						setname = sc.nextLine();
						System.out.println("Please enter the category of the product: ");
						System.out.println("1. Starter");
						System.out.println("2. Main");
						System.out.println("3. Dessert");
						System.out.println("4. Drinks");
						int setcategory = sc.nextInt();
						System.out.println("Please enter the description of the product: ");
						String setdescription = sc.nextLine();
						setdescription = sc.nextLine();
						System.out.println("Please enter the price of the product: ");
						double setprice = sc.nextDouble();
						menu.createProduct(setname, setdescription, setprice, setcategory);
						System.out.println(setname + " added to menu");
						break;
						}
					//Update Menu Product
					case 2:{
						int id;
						System.out.println("Which product do you wish to update?");
						do{
							System.out.println("Please enter the product ID : ");
							id = sc.nextInt();
							
							if(menu.getIndexByID(id) == -1)
								System.out.println("Error : Invalid ID");
						}while(menu.getIndexByID(id) == -1);
						
						int updatechoice;
						System.out.println("Updating " + menu.getProductByID(id).getName());
						System.out.println("1. Update product name");
						System.out.println("2. Update product category");
						System.out.println("3. Update product description");
						System.out.println("4. Update product price");
						System.out.println("5. Update product availability");
						System.out.println("6. Back");
						updatechoice = sc.nextInt();

						switch(updatechoice){
						case 1:{
							System.out.println("Enter the new name: ");
							String updatename1 = sc.nextLine();
							updatename1 = sc.nextLine();
							menu.updateProductName(id, updatename1);
							System.out.println(menu.getProductByID(id).getName() + " had its name updated to " + updatename1);
							break;
							}
						case 2:{
							System.out.println("Please enter the new category: ");
							System.out.println("1. Starter");
							System.out.println("2. Main");
							System.out.println("3. Dessert");
							System.out.println("4. Drinks");
							int updatecategory1 = sc.nextInt();
							menu.updateProductCategory(id, updatecategory1);
							System.out.print(menu.getProductByID(id).getName() + " had its category updated from " + menu.getProductByID(id).getCategory() + " to ");
							System.out.println(updatecategory1);
							break;
							}
						case 3:{
							System.out.println("Enter the new description: ");
							String updatedescription1 = sc.nextLine();
							updatedescription1 = sc.nextLine();
							menu.updateProductDescription(id, updatedescription1);
							System.out.print(menu.getProductByID(id).getName() + " had its desciption updated from " + menu.getProductByID(id).getDescription() + " to ");
							System.out.println(updatedescription1);
							break;
							}
						case 4:{
							System.out.println("Enter the new price(SGD): ");
							double updateprice1 = sc.nextDouble();
							menu.updateProductPrice(id, updateprice1);
							System.out.print(menu.getProductByID(id).getName() + " had its price updated from " + menu.getProductByID(id).getPrice() + " to ");
							System.out.println(updateprice1);
							break;
							}
						case 5:{
							System.out.println("Enter the new availability: ");
							boolean updateavail1 = sc.nextBoolean();
							menu.updateAvailability(id, updateavail1);
							System.out.print(menu.getProductByID(id).getName() + " had its availability updated from " + menu.getProductByID(id).getAvailability() + " to ");
							System.out.println(updateavail1);
							break;
							}
						default: {
							break;
							}
						}
						break;
					}
					//Remove Menu Product
					case 3:{
						int id;
						System.out.println("--Remove Menu Product--");
						do{
							System.out.println("Please enter the ID of the product you wish to remove: ");
							id = sc.nextInt();
							if(menu.getIndexByID(id) == -1)
								System.out.println("Error : Enter valid menu ID");
							}while(menu.getIndexByID(id) == -1);
						
						System.out.println(menu.getProductByID(id).getName() + " removed succesfully");
						menu.removeProduct(id);

						break;
						}

					default:
						break;
					}


				} while (choice2 != 4 && choice2 > -1);
				break;
			
			//Create/ Update/ Remove Menu Promotional Product
			case 3:
				int choice3;
				do{
					System.out.println("What do you want to do:");
					System.out.println("1. Create Menu Promotional Product");
					System.out.println("2. Update Menu Promotional Product");
					System.out.println("3. Remove Menu Promotional Product");
					System.out.println("4. Back");

					choice3 = sc.nextInt();

					switch(choice3){
					
					//Create Menu Promotional Product
					case 1:{
						System.out.println("--Create Menu Promotional Product--");
						System.out.println("Please enter the name of the product: ");
						String setname = sc.nextLine();
						setname = sc.nextLine();
						System.out.println("Please enter the description of the product: ");
						String setdescription = sc.nextLine();
						System.out.println("Please enter the price of the product: ");
						double setprice = sc.nextDouble();
						
						//Get startDate
						int syear, smonth, sdate;
						System.out.println("Please enter the promotional product start date: ");
						syear = getYear();
						smonth = getMonth(syear);
						sdate = getDate(smonth);
						
						//Get endDate
						int eyear, emonth, edate;
						System.out.println("Please enter the promotional product end date: ");
						eyear = getYear();
						emonth = getMonth(eyear);
						edate = getDate(emonth);
						
						menu.createProduct(setname, setdescription, setprice, 5, new GregorianCalendar(syear, smonth-1, sdate).getTime(), new GregorianCalendar(eyear, emonth-1, edate).getTime());
						System.out.println(setname + " added to menu");
						break;
						}
					//Update Menu Promotional Product
					case 2:{
						int id;
						System.out.println("Which product do you wish to update?");
						do{
							System.out.println("Please enter the product ID: ");
							id = sc.nextInt();
							
							if(menu.getProductByID(id).getCategory() !=5 && menu.getIndexByID(id) == -1)
								System.out.println("Error : Invalid ID");
						}while(menu.getIndexByID(id) == -1);
						
						int updatechoice;
						System.out.println("Updating " + menu.getProductByID(id).getName());
						System.out.println("1. Update product name");
						System.out.println("2. Update product description");
						System.out.println("3. Update product price");
						System.out.println("4. Update product availability");
						System.out.println("5. Update product start date");
						System.out.println("6. Update product end date");
						System.out.println("7. Back");
						updatechoice = sc.nextInt();

						switch(updatechoice){
						case 1:{
							System.out.println("Enter the new name: ");
							String updatename1 = sc.nextLine();
							updatename1 = sc.nextLine();
							menu.updateProductName(id, updatename1);
							System.out.println(menu.getProductByID(id).getName() + " had its name updated to " + updatename1);
							break;
							}
						case 2:{
							System.out.println("Enter the new description: ");
							String updatedescription1 = sc.nextLine();
							updatedescription1 = sc.nextLine();
							menu.updateProductDescription(id, updatedescription1);
							System.out.print(menu.getProductByID(id).getName() + " had its desciption updated from " + menu.getProductByID(id).getDescription() + " to ");
							System.out.println(updatedescription1);
							break;
							}
						case 3:{
							System.out.println("Enter the new price(SGD): ");
							double updateprice1 = sc.nextDouble();
							menu.updateProductPrice(id, updateprice1);
							System.out.print(menu.getProductByID(id).getName() + " had its price updated from " + menu.getProductByID(id).getPrice() + " to ");
							System.out.println(updateprice1);
							break;
							}
						case 4:{
							System.out.println("Enter the new availability: ");
							boolean updateavail1 = sc.nextBoolean();
							menu.updateAvailability(id, updateavail1);
							System.out.print(menu.getProductByID(id).getName() + " had its availability updated from " + menu.getProductByID(id).getAvailability() + " to ");
							System.out.println(updateavail1);
							break;
							}
						case 5:{
							System.out.println("Enter the new start date: ");
							int year, month, date;
							year = getYear();
							month = getMonth(year);
							date = getDate(month);
							menu.updateStartDate(id, new GregorianCalendar(year, month-1, date).getTime());
							System.out.print(menu.getProductByID(id).getName() + " had its start date updated from " + formatter.format(((PromoProduct) menu.getProductByID(id)).getStartDate()) + " to ");
							System.out.println(formatter.format(((PromoProduct) menu.getProductByID(id)).getStartDate()));
							break;
						}
						case 6:{
							System.out.println("Enter the new end date: ");
							int year, month, date;
							year = getYear();
							month = getMonth(year);
							date = getDate(month);
							System.out.print(menu.getProductByID(id).getName() + " had its end date updated from " + formatter.format(((PromoProduct) menu.getProductByID(id)).getEndDate()) + " to ");
							menu.updateEndDate(id, new GregorianCalendar(year, month-1, date).getTime());
							System.out.println(formatter.format(((PromoProduct) menu.getProductByID(id)).getEndDate()));
							break;
						}
						default: {
							break;
							}
						}
						break;
					}

					//Remove Menu Promotional Product
					case 3:{
						int id;
						System.out.println("--Remove Menu Product--");
						do{
							System.out.println("Please enter the ID of the item you wish to remove: ");
							id = sc.nextInt();
							if(menu.getIndexByID(id) == -1)
								System.out.println("Error : Enter valid menu itemID");
							}while(menu.getIndexByID(id) == -1);
						
						menu.removeProduct(id);
						System.out.println(menu.getProductByID(id).getName() + " removed succesfully");

						break;
						}

					default:
						break;
					}


				} while (choice3 != 4 && choice3 > -1);
			
			// Create Order
			case 4:
				int tableID, staffID, pax;
			    System.out.print("Input your staffID : ");
			    staffID = sc.nextInt();

			  do
			    {
			   System.out.print("Input no of people : ");
			   pax = sc.nextInt();
			   if(pax>10)
			      {
			        System.out.println("Sorry. We can seat a maximum of 10 people");
			      }
			   else if(pax<=0)
			      {
			    System.out.println("Please input a valid number");
			      }
			  }while(pax>10 || pax<=0);
			  System.out.print("Reservation? (y/n) : ");
			  String answer = sc.next();
			    if(answer.equals("y")|| answer.equals("Y") || answer.equals("YES")||answer.equals("yes"))
			      {
			    	reservationMgr.showReservations();
			    	System.out.print("Input Table Number: ");
			    	tableID = sc.nextInt();
			      }
			    else {
			    	tableID = tableMgr.getEmptyTable(pax);
			    }
				if(tableID==-1)
				{
				    System.out.println("Sorry. We are completely occupied at the moment.");
				}
				else
			    {
				  System.out.print("Table " + tableID + " assigned");
				  
				  int choice;
				  int menuSelection;
				  int orderID = orderlist.generateID();
				  int itemSize = 0;
				  int quantity = 0; 
				  Order order = new Order(orderID,staffID,tableID);
				  
					do{	
					System.out.println("\n1. Add Menu Item\n2. Finish");
					choice = sc.nextInt();
					switch(choice){
						case 1: menu.printMenu();
								System.out.print("Input Menu Item ID to add : ");
								menuSelection = sc.nextInt();
								System.out.print("Input Quantity : ");
								quantity = sc.nextInt();
								if(menu.getIndexByID(menuSelection)!=-1){ 
									order.addOrderLine(menu.getProductByID(menuSelection),quantity);
									itemSize++;
								System.out.println(menu.getProductByID(menuSelection).getName() +" added");
								}
								else 
									System.out.println("Error : Input valid item ID");
								break;
	
						case 2: if(itemSize==0){
									System.out.println("Error : Order is empty. Please add items before finishing");
									choice = 1; 
								}
								else{ 
									orderlist.createOrder(order, tableID);
									System.out.println("Order creation successful");
								}
								break;
						default:System.out.println("Check input value");
								break;
						}
					}while(choice!=2);
					}
			        break;
		        
		    // View Order
			case 5:
				int orderID;
				boolean empty = orderlist.checkActiveOrders();
		    	if (empty == true) {
		    		System.out.print("No Active Orders\n");
		    		break;
		    	}
		    	System.out.print("Tables with Active Orders :\n");
		    	orderlist.printActiveTableId();
		    	System.out.print("Input the TableID to View Order : ");
				tableID = sc.nextInt();
				orderID = orderlist.getActiveOrderByTableID(tableID);
				if(orderID == -1) {
					System.out.println("Inputted tableID does not have an Active Order");
					break;
				}
				int ind = orderlist.getIndexByID(orderID);
				Order ord = orderlist.getOrderByID(orderID);
				if(ind==-1)
					System.out.println("Wrong Selection");
				else if(!ord.isActive())
					System.out.println("Wrong Selection");
				
				else
					orderlist.viewOrder(orderlist.getOrderByID(orderID));
		    	break;
		    	
		    // Update or Cancel Order
			case 6:
				empty = orderlist.checkActiveOrders();
		    	if (empty == true) {
		    		System.out.print("No Active Orders\n");
		    		break;
		    	}
				System.out.println("\n1. Update order\n2. Cancel order\n");
				int choose = sc.nextInt();
				switch(choose){
						case 1:
							System.out.print("Tables with Active Orders :\n");
					    	orderlist.printActiveTableId();
					    	System.out.print("Input the TableID to Update Order : ");
							tableID = sc.nextInt();
							orderID = orderlist.getActiveOrderByTableID(tableID);
							if(orderID == -1) {
								System.out.println("Inputted tableID does not have an Active Order");
								break;
							}
					    	int index = orderlist.getIndexByID(orderID);
							Order order = orderlist.getOrderByID(orderID);
							int menuSelection;
							int choice;
							int quantity;
							if(index==-1)
								System.out.println("Error : Invalid order ID");
							else if(!order.isActive())
								System.out.println("Error : Order has already completed");
							
							else{ //if order is active and valid
								orderlist.viewOrder(order);
								do{
								System.out.println("\n1. Add Menu Item\n2. Remove Menu Item\n3. Finish");
								choice = sc.nextInt();
								switch(choice){
									case 1: 
											menu.printMenu();
											System.out.print("Input item ID to add : ");
											menuSelection  = sc.nextInt();
											System.out.print("Input quantity : ");
											quantity  = sc.nextInt();
											Item product = menu.getProductByID(menuSelection);
											int exists = order.checkProduct(product);
											if(menu.getIndexByID(menuSelection)!=-1){//if item exists
												if (exists == 1) {//If product alr in orderline
													order.amendAddOrderLine(menu.getProductByID(menuSelection),quantity);
													System.out.println(menu.getProductByID(menuSelection).getName() +" added");
												}
												else {
												order.addOrderLine(menu.getProductByID(menuSelection),quantity);
												System.out.println(menu.getProductByID(menuSelection).getName() +" added");
												}
											}
											else //if item doesnt exist
												System.out.println("Error : Input valid item ID");
											sc.nextLine();
											System.out.println("\nPress enter to continue");
											sc.nextLine();
											break;
											
									case 2: 
											menu.printMenu();
											System.out.print("Input item ID to remove : ");
											menuSelection  = sc.nextInt();
											order.removeOrderLine(menu.getProductByID(menuSelection));
											System.out.println("\nPress enter to continue");
											sc.nextLine();
											break;
									case 3: if(order.getOrderSize()==0){ //no items in order
												System.out.println("\nError : Order is now empty. You have to add atleast 1 set or 1 item");
												choice = 1;
												sc.nextLine();
												System.out.println("\nPress enter to continue");
												sc.nextLine();
									}
									else{
											System.out.println("Order update successful");
									    	orderlist.update(order, index);
									}
											break;
									default:System.out.println("Check input value");
										System.out.println("\nPress enter to continue");
										sc.nextLine();
											break;
								}
							}while(choice!=3);
							}

					    	break;
						case 2:
							System.out.print("Tables with Active Orders :\n");
					    	orderlist.printActiveTableId();
					    	System.out.print("Input the TableID to Remove Order : ");
							tableID = sc.nextInt();
							orderID = orderlist.getActiveOrderByTableID(tableID);
							if(orderID == -1) {
								System.out.println("Inputted tableID does not have an Active Order");
								break;
							}
							orderlist.removeOrder(orderID);
							break;
						default : System.out.println("Error : Check input");
				}
				sc.nextLine();
				break; 
				
			// Create reservation booking	
			case 7:
			    String name;
			    int phoneNumber;
			    int noOfPeople;
			    int year, month, date, hour, minute;
			    Calendar bookingTime = Calendar.getInstance();
			    sc.nextLine();
			    
			    // Get Name
			    System.out.print("Input the following details\nName : ");
			    name = sc.nextLine();
			    
			    // Get Phone Number
			    System.out.print("Phone Number : ");
			    phoneNumber = sc.nextInt();
			    
			    // Get Pax
			    do{
				    System.out.print("Reservation for how many people : ");
				    noOfPeople = sc.nextInt();
				    if(noOfPeople>10)
				      System.out.println("Sorry. We can seat a maximum of 10 people");
            else if(noOfPeople < 1) {
						System.out.println("Invalid number");
					}
				} while (noOfPeople > 10 || noOfPeople < 1);
			    
			    // Get Year
			    year = getYear();
			    
			    // Get Month
				month = getMonth(year);
				
				// Get Date
				date = getDate(month);
				
				// Get Hour
				hour = getHour(date,OpeningHour,ClosingHour);
				
				// Get Minute
				minute = getMinute(hour,ClosingHour);
				
				// Set the date
				bookingTime.set(year, month-1, date, hour, minute);
				Date bookingDate = bookingTime.getTime();
				reservationMgr.addReservation(name, phoneNumber, noOfPeople, bookingDate);
				break;		
				
		// Check/Remove Reservation Booking
		case 8:
			if (reservationMgr.reservations.size() == 0) {
				System.out.println("No reservations currently");
				break;
			}
			reservationMgr.showReservations();
			System.out.println("Select your option below:\n"+
					   "1.  Remove reservation booking\n"+
					   "2.  Quit to main menu");
			int choice = sc.nextInt();
			if(choice == 1) {
				reservationMgr.showReservations();
				System.out.println("Enter index of reservation to be deleted:");
				int index = sc.nextInt();
				Reservation temp = reservationMgr.searchReservationbyIndex(index);
				if(temp == null) {
					System.out.println("Reservation does not exist");
					break;
				}
				reservationMgr.removeReservation(temp);
				System.out.println("Reservation deleted");
			}
			break;
			
		//Check Table Availability
	    case 9: 
	        tableMgr.showTables();
			break;
		
		// Print Order Invoice
	    case 10:
			empty = orderlist.checkActiveOrders();
	    	if (empty == true) {
	    		System.out.print("No Active Orders\n");
	    		break;
	    	}
	    	System.out.print("Tables with Active Orders :\n");
	    	orderlist.printActiveTableId();
	    	System.out.print("Input the TableID to Close Order and Print Invoice : ");
			tableID = sc.nextInt();
			orderID = orderlist.getActiveOrderByTableID(tableID);
			if(orderID == -1) {
				System.out.println("Inputted tableID does not have an Active Order");
				break;
			}
		  	int index = orderlist.getIndexByID(orderID);
		    if(index==-1){
		     System.out.println("\nNo such order exists\n");
		     return;
		    }
		    Order order = orderlist.getOrderByID(orderID);
		    if(!order.isActive()){
		     System.out.println("\nOrder is already complete\n");
		     return;
		    }
		    orderlist.viewOrder(order);
		    double price = orderlist.calculateSum(order);
		    System.out.print("\nAre you a member ? (y/n) : ");
		    String ans = sc.next();
		    if(ans.equals("y")|| ans.equals("Y") || ans.equals("YES")||ans.equals("yes"))
		      {
		     order.orderIsByMember();
		     System.out.println("Members enjoy 10% discount");
		     System.out.println("Total Price : " + price);
		     System.out.println("Price after discount & GST : " + price*0.9*1.07);
		    }
		    else {
		    System.out.println("Total Price : " + price);
		    System.out.println("Price after GST : " + price*1.07);
		    }
		  	orderlist.createInvoice(orderID, order.getTableID(), order, index);
		  	System.out.println("\n");
		  	break;
		  	  
		  // Print all Orders
	      case 11:
	    	  orderlist.printOrderList();
	    	  break;
		  	  
		  // Print Sales Revenue Report by Period
		  case 12: 
				int startYear, startMonth, startDay, endYear, endMonth, endDay;
				Calendar calStart = Calendar.getInstance(), calEnd = Calendar.getInstance();
				System.out.println("Enter start date:");
				startYear = getStartYearRevenueReport();
				startMonth = getStartMonthRevenueReport(startYear);
				startDay = getStartDateRevenueReport(startMonth, startYear);
				calStart.set(startYear, startMonth, startDay,00,00);
				Date startDate = calStart.getTime();
				System.out.println("Enter end date:");
				endYear = getEndYearRevenueReport(startYear);
				endMonth = getEndMonthRevenueReport(startYear, endYear, startMonth);
				endDay = getEndDateRevenueReport(startYear, endYear, startMonth, endMonth, startDay);
				calEnd.set(endYear, endMonth, endDay,23,59);
				Date endDate = calEnd.getTime();
				rr.generateReport(startDate, endDate);
				break;  
		//Quit
	    case 13:
	    	end = true;
	    	break;   	
	    	
		//If choice not in options
		default:
			System.out.println("Invalid Selection");
			continue;
			}// End of switch
		} while(end == false); //End of do-while for main menu
		System.out.print("Exited~");
		System.exit(0);
	} //End of main function
	
	/**
	 * Method to get a day from the user that is within the days of a month
	 * Prevents choosing a day in the future
	 * @param month The month of start date
	 * @param year The year of start date
	 * @return user inputted day
	 */
  private static int getStartDateRevenueReport(int month, int year) {
		int cont;
		int date;
		Calendar currentTime = Calendar.getInstance();
		int maxdate;
		if (month == 2) {
			maxdate = 28;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			maxdate = 30;
		} else {
			maxdate = 31;
		}

		do {
			cont = 1;
			System.out.print("Day: ");
			date = sc.nextInt();
			if (date > maxdate 
					|| (year == currentTime.get(Calendar.YEAR)
						&& month == currentTime.get(Calendar.MONTH) + 1)
						&& date > currentTime.get(Calendar.DAY_OF_MONTH)) {
				System.out.print("Date Invalid");
				cont = 0;
			}
		} while (cont == 0);
		return date;
	}
  
	/**
	 * Method to get a day from the user that is within the days of a month
	 * Prevents choosing a day prior to the starting day
	 * @param Syear The year of start date
	 * @param Eyear The year of end date
	 * @param Smonth The month of start date
	 * @param Emonth The month of end date
	 * @param Sdate The day of start date
	 * @return user inputted day
	 */
  private static int getEndDateRevenueReport(int Syear, int Eyear, int Smonth, int Emonth, int Sdate) {
		int cont;
		int date;
		int maxdate;
		if (Emonth == 2) {
			maxdate = 28;
		} else if (Emonth == 4 || Emonth == 6 || Emonth == 9 || Emonth == 11) {
			maxdate = 30;
		} else {
			maxdate = 31;
		}

		do {
			cont = 1;
			System.out.print("Day: ");
			date = sc.nextInt();
			if (date > maxdate) {
				System.out.print("Date Invalid");
				cont = 0;
			}
			if (Syear == Eyear && Smonth == Emonth && date < Sdate ) {
				System.out.print("Date Invalid - End Date cannot be prior to Start Date");
				cont = 0;
			}
		} while (cont == 0);
		return date;
	}
	
	/**
	 * Method to get a month from the user that is within the months of the year
	 * Prevents choosing a month in the future
	 * @param year The year of start date
	 * @return user inputted month
	 */
	private static int getStartMonthRevenueReport(int year) {
		int cont;
		int month;
		Calendar currentTime = Calendar.getInstance();
		do {
			cont = 1;
			System.out.print("Month: ");
			month = sc.nextInt();
			if ((year == currentTime.get(Calendar.YEAR) && month > currentTime.get(Calendar.MONTH) + 1) 
					|| month > 12 ) {
				System.out.print("Month Invalid\n");
				cont = 0;
			}
		} while (cont == 0);
		return month;
	}
	
	/**
	 * Method to get a month from the user that is within the months of the year
	 * Prevents choosing a month prior to the starting month
	 * @param Syear The year of start date
	 * @param Eyear The year of end date
	 * @param Smonth The month of start date
	 * @return user inputted
	 */
	private static int getEndMonthRevenueReport(int Syear, int Eyear, int Smonth) {
		int cont;
		int month;
		do {
			cont = 1;
			System.out.print("Month: ");
			month = sc.nextInt();
			if (month > 12 ) {
				System.out.print("Month Invalid\n");
				cont = 0;
			}
			if ((Syear == Eyear && month < Smonth)){
				System.out.print("Month Invalid - End Date cannot be prior to Start Date\n");
				cont = 0;
			}
		} while (cont == 0);
		return month;
	}
	
	/**
	 * Method to get a year from the user that is not in the future
	 * @return user inputted year
	 */
	private static int getStartYearRevenueReport() {
		int year, cont;
		Calendar currentTime = Calendar.getInstance();
		do {
			cont = 1;
			System.out.print("Year: ");
			year = sc.nextInt();
			if (year > currentTime.get(Calendar.YEAR)) {
				System.out.print("Year Invalid\n");
				cont = 0;
			}
		} while (cont == 0);
		return year;
	}
	
	/**
	 * Method to get a year from the user that is not prior to start year
	 * @param Syear The year of the start date
	 * @return user inputted year
	 */
	private static int getEndYearRevenueReport(int Syear) {
		int year, cont;
		do {
			cont = 1;
			System.out.print("Year: ");
			year = sc.nextInt();
			if (year<Syear) {
				System.out.print("Year Invalid - End Date cannot be prior to Start Date\n");
				cont = 0;
			}
		} while (cont == 0);
		return year;
	}
	

	/**
	 * Method to get a year for Reservation
	 * Ensures year inputted is in the future
	 * @return user inputted year
	 */
	private static int getYear() {
		int cont;
		int year;
		Calendar bookingTime = Calendar.getInstance();
		do {
		    cont = 1;
		    System.out.print("Year: ");
			year = sc.nextInt();
			if (year > bookingTime.get(Calendar.YEAR)+1||year < bookingTime.get(Calendar.YEAR)) {
				System.out.print("Year Invalid\n");
				cont = 0;
				}
	    }
	    while(cont == 0);
		return year;
	}
	
	/**
	 * Method to get a month for Reservation
	 * Ensure its within months of a year and is in the future
	 * @param year User inputted year of reservation
	 * @return user inputted month
	 */
	private static int getMonth(int year) {
		int cont;
		int month;
		Calendar bookingTime = Calendar.getInstance();
		do {
			cont = 1;
			System.out.print("Month: ");
			month  = sc.nextInt();
			if ((month < bookingTime.get(Calendar.MONTH)+1 && year == bookingTime.get(Calendar.YEAR)) 
					|| month >12 ) {
				System.out.print("Month Invalid\n");
				cont = 0;
			}
		}
		while(cont == 0);
		return month;
	}
	
	/**
	 * Method to get a day for Reservation
	 * @param month User inputted month of reservation
	 * @return user inputted day
	 */
	private static int getDate(int month) {
		int cont;
		int date;
		Calendar bookingTime = Calendar.getInstance();
		int maxdate;
		if(month == 2) {
	    	maxdate = 28;
	    }else if (month == 4 ||month == 6 || month == 9 || month == 11) {
	    	maxdate = 30;
	    }else{
	    	maxdate = 31;
	    }
		
		do {
			cont = 1;
			System.out.print("Day: ");
			date  = sc.nextInt();
			if (date > maxdate || 
			   (date < bookingTime.get(Calendar.DAY_OF_MONTH) && month == bookingTime.get(Calendar.MONTH)+1)) {
				System.out.print("Date Invalid");
				cont = 0;
			}
		}
		while(cont == 0);
		return date;
	}
	
	/**
	 * Method to get an hour for time of Reservation
	 * Ensures it is between opening hour and 1hr before closing
	 * @param date User inputted day of reservation
	 * @param OpeningHour opening hour of the restaurant
	 * @param ClosingHour closing hour of the restaurant
	 * @return user inputted hour
	 */
	private static int getHour(int date, int OpeningHour, int ClosingHour) {
		int cont;
		int hour;
		Calendar bookingTime = Calendar.getInstance();
		do {
			cont = 1;
			System.out.print("Hour of reservation (in 24 hrs) : ");
			hour  = sc.nextInt();
			if (hour >= ClosingHour || hour < OpeningHour ||
			   (hour < bookingTime.get(Calendar.HOUR) && date == bookingTime.get(Calendar.DAY_OF_MONTH))) {
						System.out.print("Hour Invalid, booking must be between "+OpeningHour+"00hrs-"+(ClosingHour-1)+"00hrs");
						cont = 0;
			}
		}
		while(cont == 0);
		return hour;
	}
	
	/**
	 * Method to get minutes for time of Reservation
	 * Only allows intervals of 15
	 * Ensures last booking is 1hr before closing
	 * @param hour User inputted hour of reservation
	 * @param ClosingHour closing hour of the restaurant
	 * @return user inputted minutes
	 */
	private static int getMinute(int hour, int ClosingHour) {
		int cont;
		int minute;
		do {
			cont = 1;
			System.out.print("Minute of reservation : ");
			minute  = sc.nextInt();
			if ((minute != 0 && minute != 15 && minute != 30 && minute != 45)|| 
			   (hour == ClosingHour - 1 && minute != 0)) {
						System.out.print("Minute Invalid, booking must be between 1600hrs-2100hrs");
						cont = 0;
					}
		}
		while(cont == 0);
		return minute;
	}
}

