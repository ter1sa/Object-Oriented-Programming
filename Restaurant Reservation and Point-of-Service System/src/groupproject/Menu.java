package groupproject;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Menu entity
 * Implements methods to view menu, create, update and remove both product and promotional product
 * Reads stored menu from text file upon instantiation,
 * writes to text file every time menu are created/ deleted or updated.
 * @author Teresa Zhang
 *
 */
public class Menu implements Indexable{
	
	private ArrayList <Item> product;
	BufferedWriter out;
	
	//extra methods that are used within the code
	//function to auto generate menuID
	@Override
	public int generateID() {
		int lastID;
		for(int i=0;i<product.size();i++) //check if there are any items with missing IDs (as compared to index)
			if(getIndexByID(i) == -1)	//if yes, return this missing ID
				return i;
		if (product.size()==0) {
			lastID=0;
		}
		
		else {
			lastID = product.get(product.size()-1).getProductID(); //otherwise, get the last used ID
			while(getIndexByID(lastID) != -1) //increment it till you get a new, unused ID
				lastID++;
		}
		
		return lastID; //return this newID
	}
	
	@Override
	public int getIndexByID(int id){
		for(int i=0;i<product.size();i++)
			if(product.get(i).getProductID() == id)
				return i;
		return -1; //if no such item with given ID exists, return -1
	}
	
	
	/**
	 * for other classes to use
	 * @param id item ID
	 * @return Item if have, NULL if not
	 */
	public Item getProductByID(int id){
		for(int i=0;i<product.size();i++)
			if(product.get(i).getProductID() == id)
				return product.get(i);
		return null;
	}

	/**
	 * Menu class constructor
	 * Instantiates ArrayList of Item
	 * Read stored menu from txt file
	 * Able to auto remove promotional product when the validity period ends
	 */
	public Menu(){
		product = new ArrayList <Item>();
		
		//continuously compare end date and current date
		Runnable expiredReservationsRunnable = new Runnable() {
			public void run() {
				removeExpiredPromo();
			}
		};
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		exec.scheduleAtFixedRate(expiredReservationsRunnable, 0, 1, TimeUnit.DAYS); 
		
		//read and write from txt file
		try {
			   File file = new File("menu.txt");
			   BufferedReader br = new BufferedReader(new FileReader(file));
			   List<String> lines = new ArrayList<String>();
			   String line = br.readLine();
			   Calendar Sdate = Calendar.getInstance();
			   Calendar Edate = Calendar.getInstance();
			   String tempName;
			   String tempDesc;
			   double tempPrice;
			   int tempProdID;
			   int tempCategory;
			   boolean tempisPromo;
			   while (line != null) {
			    lines.add(line.replace(">", ""));
			    line = br.readLine();
			   }
			   int i = 0;

			   while (i < lines.size()) {
			    tempName = lines.get(i);
			    tempDesc = lines.get(i + 1);
			    tempPrice = Double.parseDouble(lines.get(i + 2));
			    tempProdID = Integer.parseInt(lines.get(i + 3));
			    tempCategory = Integer.parseInt(lines.get(i + 4));
			    tempisPromo = Boolean.parseBoolean(lines.get(i + 5));
			    
			    if(tempisPromo == true) {
			     String[] dateS = lines.get(i+6).split("\\|");
			     String[] dateE = lines.get(i+7).split("\\|");
			     Sdate.set(Integer.parseInt(dateS[0]), Integer.parseInt(dateS[1]),Integer.parseInt(dateS[2]));
			     Edate.set(Integer.parseInt(dateE[0]), Integer.parseInt(dateE[1]),Integer.parseInt(dateE[2]));
			     PromoProduct tempPromoProd = new PromoProduct(tempName, tempDesc, tempPrice, tempProdID, tempCategory, Sdate.getTime(), Edate.getTime());
			     product.add(tempPromoProd);
			    }
			    else {
			     Product tempProduct = new Product(tempName, tempDesc, tempPrice, tempProdID, tempCategory);
			     product.add(tempProduct);
			    }
			    
			    i = i + 9;
			   }

			   br.close();
			  }

			  catch (IOException e) {
			   System.out.println("There was a problem:" + e);
			  } catch (NumberFormatException e) {
			   // System.out.println("This is not a number");
			  }
	}
	
	/**
	 * Print out the menu
	 * Sort menu according to categories
	 */
	public void printMenu() {
		//first sort it according to category
		Item[] productCopy = new Item[product.size()];
		System.arraycopy(product.toArray(), 0, productCopy, 0, product.size());
		int n = productCopy.length;
		Item temp;
		
		for(int i=0; i < n; i++) {
			for(int j=1; j < (n-i); j++) {
				if(productCopy[j-1].getCategory() > productCopy[j].getCategory()) {
                temp = productCopy[j-1];
                productCopy[j-1] = productCopy[j];
                productCopy[j] = temp;
				}
			}
		}
	  
	    //then print it
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	    String pcategory = null;
	    System.out.println("--------MENU---------");
		System.out.format("%n%8s %9s %30s %9s %s%n", "Category", "ProductID", "Name", "Price", "Description");
		
		for (int i=0; i<n; i++) {
			switch(productCopy[i].getCategory()) {
				case 1: pcategory = "Starter";
					break;
				case 2: pcategory = "Main";
					break;
				case 3: pcategory = "Dessert";
					break;
				case 4: pcategory = "Drink";
					break;
				case 5: pcategory = "Promo";
				break;
			}
			if(productCopy[i].getCategory() == 5)
				System.out.format("%8s %9d %30s %4s%5.2f %s%n %71s%s%s%s%n", pcategory,productCopy[i].getProductID(), productCopy[i].getName(), "SGD ", productCopy[i].getPrice(), productCopy[i].getDescription(), "Valid From: ", formatter.format(((PromoProduct) productCopy[i]).getStartDate()), " to ", formatter.format(((PromoProduct) productCopy[i]).getEndDate()));
			else
				System.out.format("%8s %9d %30s %4s%5.2f %s%n", pcategory,productCopy[i].getProductID(), productCopy[i].getName(), "SGD ", productCopy[i].getPrice(), productCopy[i].getDescription());
		}
	}
	
	/**
	 * Writes all Menu in Menu ArrayList to text file
	 */
	public void writeMenu() {
		  try { 
		   Calendar Sdate = Calendar.getInstance();
		   Calendar Edate = Calendar.getInstance();
		   out = new BufferedWriter(new FileWriter("menu.txt", false));
		   for (int counter = 0; counter < product.size(); counter++) {
		    if (product.get(counter).getIsPromo() == true) {
		     Sdate.setTime(((PromoProduct) product.get(counter)).getStartDate());
		     Edate.setTime(((PromoProduct) product.get(counter)).getEndDate());
		    }
		    out.write(product.get(counter).getName() + "\n" + 
		       (product.get(counter).getDescription()) + "\n" + 
		        String.valueOf(product.get(counter).getPrice()) + "\n" +
		        String.valueOf(product.get(counter).getProductID()) + "\n" + 
		        String.valueOf(product.get(counter).getCategory())+ "\n"+
		        String.valueOf(product.get(counter).getIsPromo())+"\n");
		    if (product.get(counter).getIsPromo() == true) {
		     out.write(Sdate.get(Calendar.YEAR)+"|"+Sdate.get(Calendar.MONTH)+"|"+Sdate.get(Calendar.DAY_OF_MONTH)+"|");
		     out.newLine();
		     out.write(Edate.get(Calendar.YEAR)+"|"+Edate.get(Calendar.MONTH)+"|"+Edate.get(Calendar.DAY_OF_MONTH)+"|");
		    }
		    else {
		     out.newLine();
		    }
		    out.newLine();
		    out.newLine();
		   }
		   out.close();
		  } catch (IOException e) {
		   System.out.println("There was a problem:" + e);
		  }

		 }
	
	
	//main methods
	
	/**
	 * Creates new product
	 * @param cname Name of Product
	 * @param cdescription Description of Product
	 * @param cprice Price of Product
	 * @param ccategory Category of Product
	 */
	public void createProduct(String cname, String cdescription, double cprice, int ccategory){
		Product newProduct = new Product(cname, cdescription, cprice, generateID(), ccategory);
		product.add(newProduct);
		writeMenu();
	}
	
	/**
	 * Creates new promoproduct
	 * Override the previous method
	 * @param cname Name of PromoProduct
	 * @param cdescription Description of PromoProduct
	 * @param cprice Price of PromoProduct
	 * @param ccategory Category of PromoProduct
	 * @param cstartDate start date of the validity period of PromoProduct
	 * @param cendDate end date of the validity period of PromoProduct
	 */
	public void createProduct(String cname, String cdescription, double cprice, int ccategory, Date cstartDate, Date cendDate){
		if(ccategory != 5) {
			System.out.println("Invalid");
			return;
		}
		PromoProduct newProduct = new PromoProduct(cname, cdescription, cprice, generateID(), ccategory, cstartDate, cendDate);
		product.add(newProduct);
		writeMenu();
	}
	
	//Update product
	
	/**
	 * Update product price
	 * @param itemID item ID of item
	 * @param price price of item
	 */
	public void updateProductPrice(int itemID, double price){
	int index = getIndexByID(itemID);
		if(index!=-1){
			product.get(index).setPrice(price);
			writeMenu();
		}
		else
			System.out.println("Update failed");
			
	}
	
	/**
	 * Update product name
	 * @param itemID item ID of item
	 * @param name name of item
	 */
	public void updateProductName(int itemID, String name){
		int index = getIndexByID(itemID);
		if(index!=-1){
			product.get(index).setName(name);
			writeMenu();
		}
		else
			System.out.println("Update failed");
	}
	
	/**
	 * Update product description
	 * @param itemID item ID of item
	 * @param description description of item
	 */
	public void updateProductDescription(int itemID, String description){
		int index = getIndexByID(itemID);
		if(index!=-1){
			product.get(index).setDescription(description);
			writeMenu();
			}
		else
			System.out.println("Update failed");
	}
	/**
	 * Update product category
	 * @param itemID item ID of item
	 * @param category category of item
	 */
	public void updateProductCategory(int itemID, int category){
		int index = getIndexByID(itemID);
		if(index!=-1){
			product.get(index).setCategory(category);
			writeMenu();
			}
		else
			System.out.println("Update failed");

	}
	
	/**
	 * Update product availability
	 * @param itemID item ID of item
	 * @param available availability of item
	 */
	public void updateAvailability(int itemID, boolean available) {
		int index = getIndexByID(itemID);
		if(index!=-1) {
			product.get(index).setAvailability(available);
			writeMenu();
		}
		else
			System.out.println("Update failed");
	}

	/**
	 * Remove product
	 * Removes item from arraylist
	 * @param itemID item ID of item
	 */
	public void removeProduct(int itemID){
		int index = getIndexByID(itemID);
		if(index!=-1){
			product.remove(index);
			writeMenu();
			}
		else
			System.out.println("Remove failed");
	}
	
	
	/**
	 * Update start date for promoproduct
	 * @param itemID item ID of item
	 * @param startDate start date of the validity period of PromoProduct
	 */
	public void updateStartDate(int itemID, Date startDate) {
		int index = getIndexByID(itemID);
		if(index!=-1) {
			((PromoProduct) product.get(index)).setStartDate(startDate);
			writeMenu();
		}
		else
			System.out.println("Update failed");
	}
	
	/**
	 * Update end date for promoproduct
	 * @param itemID item ID of item
	 * @param endDate end date of the validity period of PromoProduct
	 */
	public void updateEndDate(int itemID, Date endDate) {
		int index = getIndexByID(itemID);
		if(index!=-1) {
			((PromoProduct) product.get(index)).setEndDate(endDate);
			writeMenu();
		}
		else
			System.out.println("Update failed");
	}
	
	/**
	 * Remove expired promoproduct, used in constructor
	 */
	public void removeExpiredPromo() {
		Calendar limit = Calendar.getInstance();
		Date cutoffTime = limit.getTime();
		
		for(int i=0;i<product.size();i++) {
			int index = getIndexByID(i);
			if(index!=-1) {
				if(product.get(i).getCategory() != 5)
					return;
				if(((PromoProduct) product.get(i)).getEndDate().before(cutoffTime)) {
					System.out.println("Removed" + product.get(i).getName());
					product.remove(i);
					writeMenu();
				}
			}
		}
	}
}
