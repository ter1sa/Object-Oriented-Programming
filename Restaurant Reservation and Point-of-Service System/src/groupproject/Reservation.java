package groupproject;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Reservation entity
 * 
 * @author Tan Yu Ling
 *
 */
public class Reservation {
	private Date reservationTime;
	private String name;
	private int noOfPax;
	private int contact;
	private int tableID;
	private int reservationID;

	/**
	 * Class Constructor
	 * 
	 * @param n         Name of reservation holder
	 * @param pax       Number of customers
	 * @param contactNo Phone number of reservation holder
	 * @param table     Table ID assigned
	 * @param date      Date and time of reservation
	 * @param rID       Reservation ID
	 */
	public Reservation(String n, int pax, int contactNo, int table, Date date, int rID) {
		reservationTime = date;
		name = n;
		noOfPax = pax;
		contact = contactNo;
		tableID = table;
		reservationID = rID;
	}

	/**
	 * Get this reservationID
	 * 
	 * @return reservationID
	 */
	public int getReservationID() {
		return reservationID;
	}

	/**
	 * Get this reservationTime
	 * @return reservation time
	 */
	public Date getReservationTime() {
		return reservationTime;
	}

	/**
	 * Get this table ID
	 * @return table ID
	 */
	public int getTableID() {
		return tableID;
	}

	/**
	 * Print this reservation
	 */
	public void showReservation() {
		SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM  HH:mm");
		String date = formatter.format(reservationTime);
		System.out.println(reservationID + ".  "+date + "\n" + "Reservation for " + noOfPax + " under " + name + " (contact: " + contact
				+ "): Table " + tableID + "\n");
	}

	/**
	 * Get this name
	 * @return reservation holder's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get this contact number
	 * @return contact number
	 */
	public int getContact() {
		return contact;
	}

	/**
	 * Get this number of customers
	 * @return number of customers
	 */
	public int getNoOfPax() {
		return noOfPax;
	}
}