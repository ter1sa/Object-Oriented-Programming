package groupproject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Implements methods to allow creation, search and deletion of reservations.
 * Reads stored reservations from text file upon instantiation,
 * writes to text file every time reservations are created or deleted.
 * @author Tan Yu Ling
 * @author Shenal Devinda
 *
 */
public class ReservationManager implements Indexable {
	/**
	 * Create an ArrayList of Reservations
	 */
	public ArrayList<Reservation> reservations;
	BufferedWriter out;
	
	/**
	 * Class Constructor
	 * Instantiates ArrayList of Reservation, reads stored reservations from text file,
	 * and starts checking for expired reservations every minute
	 */
	public ReservationManager() {
		reservations = new ArrayList<Reservation>();
		// read from file
		Runnable expiredReservationsRunnable = new Runnable() {
			public void run() {
				removeExpiredReservations();
			}
		};
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		exec.scheduleAtFixedRate(expiredReservationsRunnable, 0, 1, TimeUnit.MINUTES);
	
		try {
			File file = new File("reservationlist.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			List<String> lines = new ArrayList<String>();
			String line = br.readLine();
			String tempName;
			int tempPhoneNumber;
			int tempNoOfPeople;
			int tempTableID;
			int year, month, date, hour, minute;
			int resID;
			Calendar bookingTime = Calendar.getInstance();
			while (line != null) {
				lines.add(line.replace(">", ""));
				line = br.readLine();
			}
			int i = 0;

			while (i < lines.size()) {
				tempName = lines.get(i);
				tempPhoneNumber = Integer.parseInt(lines.get(i + 1));
				tempNoOfPeople = Integer.parseInt(lines.get(i + 2));
				tempTableID = Integer.parseInt(lines.get(i + 3));

				year = Integer.parseInt(lines.get(i + 4));
				month = Integer.parseInt(lines.get(i + 5));
				date = Integer.parseInt(lines.get(i + 6));
				hour = Integer.parseInt(lines.get(i + 7));
				minute = Integer.parseInt(lines.get(i + 8));
				resID = Integer.parseInt(lines.get(i + 9));

				bookingTime.set(Calendar.YEAR, year);
				bookingTime.set(Calendar.MONTH, month - 1);
				bookingTime.set(Calendar.DAY_OF_MONTH, date);
				bookingTime.set(Calendar.HOUR_OF_DAY, hour);
				bookingTime.set(Calendar.MINUTE, minute);
				Reservation tempReservation = new Reservation(tempName, tempNoOfPeople, tempPhoneNumber, tempTableID,
						bookingTime.getTime(), resID);
				reservations.add(tempReservation);
				i = i + 11;
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
	 * Creates new Reservation
	 * Reservation added to ArrayList and written to text file
	 * @param name Name of reservation holder
	 * @param phoneNumber Contact number of reservation holder
	 * @param noOfPeople Number of customers 
	 * @param bookingDate Date and time of reservation
	 * @return 1 if successful, 0 if unsuccessful
	 */
	public int addReservation(String name, int phoneNumber, int noOfPeople, Date bookingDate) {
		int tableID = getAvailableTable(bookingDate, noOfPeople);
		if (tableID == -1) {
			return 0;
		}
		Reservation newReservation = new Reservation(name, noOfPeople, phoneNumber, tableID, bookingDate, generateID());
		reservations.add(newReservation);
		writeReservation();
		return 1;
	}

	/**
	 * Returns available table ID for a new Reservation
	 * @param d Date and time of new Reservation
	 * @param pax Number of customers
	 * @return Table ID if successful, -1 if unsuccessful
	 */
	public int getAvailableTable(Date d, int pax) {
		Calendar earliest = Calendar.getInstance();
		earliest.setTime(d);
		earliest.add(Calendar.HOUR, -2);
		Calendar latest = Calendar.getInstance();
		latest.setTime(d);
		latest.add(Calendar.HOUR, 2);
		Date earlierLimit = earliest.getTime();
		Date laterLimit = latest.getTime();

		TableManager reservedTables = new TableManager();
		for (Reservation e : reservations) {
			if (e.getReservationTime().after(earlierLimit) && e.getReservationTime().before(laterLimit)) {
				reservedTables.occupy(e.getTableID());
			}
		}
		return reservedTables.getEmptyTable(pax);
	}
	
	@Override
	public int generateID() {
		int lastID;
		for (int i = 1; i <= reservations.size(); i++)
			if (getIndexByID(i) == -1)
				return i;
		if (reservations.size() == 0) {
			lastID = 1;
		}

		else {
			lastID = reservations.get(reservations.size() - 1).getReservationID();
			while (getIndexByID(lastID) != -1)
				lastID++;
		}

		return lastID;
	}

	@Override
	public int getIndexByID(int id) {
		for (int i = 0; i < reservations.size(); i++)
			if (reservations.get(i).getReservationID() == id)
				return i;
		return -1;
	}

	/**
	 * Search for Reservation by ArrayList Index
	 * @param index ArrayList index
	 * @return Reservation
	 */
	public Reservation searchReservationbyIndex(int index) {
		if (index > reservations.size() - 1)
			return null;
		return reservations.get(index);
	}

	/**
	 * Print all reservations
	 */
	public void showReservations() {
		if (reservations.size() == 0) {
			System.out.println("No reservations");
			return;
		}
		for (Reservation r : reservations) {

			r.showReservation();
		}
	}

	/**
	 * Search for Reservation by name of reservation holder
	 * @param n name of reservation holder
	 * @return Reservation
	 */
	public Reservation searchReservationbyName(String n) {
		for (Reservation r : reservations) {
			if (r.getName() == n) {
				return r;
			}
		}
		return null;
	}
	
	/**
	 * Search for Reservation by reservation id
	 * @param id reservation id
	 * @return Reservation
	 */
	public Reservation searchReservationbyID(int id) {
		for (Reservation r : reservations) {
			if (r.getReservationID() == id) {
				return r;
			}
		}
		return null;
	}

	/**
	 * Search for Reservation by Contact Number
	 * @param contact contact number of reservation holder
	 * @return Reservation
	 */
	public Reservation searchReservationbyContact(int contact) {
		for (Reservation r : reservations) {
			if (r.getContact() == contact) {
				return r;
			}
		}
		return null;
	}

	/**
	 * Remove Reservation from reservations ArrayList
	 * @param r Reservation to be removed
	 * @return 1 if successful, 0 if Reservation is not found
	 */
	public int removeReservation(Reservation r) {
		for (Reservation reservation : reservations) {
			if (reservation == r) {
				reservations.remove(reservation);
				writeReservation();
				return 1;
			}
		}
		return 0;
	}

	/**
	 * Writes all Reservations in reservations ArrayList to text file
	 */
	public void writeReservation() {
		try {
			Calendar date = Calendar.getInstance();
			int year, month, day, hour, minute;
			out = new BufferedWriter(new FileWriter("reservationlist.txt", false));
			for (int counter = 0; counter < reservations.size(); counter++) {
				date.setTime(reservations.get(counter).getReservationTime());
				year = date.get(Calendar.YEAR);
				month = date.get(Calendar.MONTH);
				day = date.get(Calendar.DAY_OF_MONTH);
				hour = date.get(Calendar.HOUR_OF_DAY);
				minute = date.get(Calendar.MINUTE);
				out.write(reservations.get(counter).getName() + "\n" + (reservations.get(counter).getContact()) + "\n"
						+ String.valueOf(reservations.get(counter).getNoOfPax()) + "\n"
						+ String.valueOf(reservations.get(counter).getTableID()) + "\n" + String.valueOf(year) + "\n"
						+ String.valueOf(month + 1) + "\n" + String.valueOf(day) + "\n" + String.valueOf(hour) + "\n"
						+ String.valueOf(minute) + "\n" + String.valueOf(reservations.get(counter).getReservationID()));
				out.newLine();
				out.newLine();
			}
			System.out.println("File Written");
			out.close();
		} catch (IOException e) {
			System.out.println("There was a problem:" + e);
		}

	}

	/**
	 * Removes expired Reservations when current time is more than 15 minutes past reservation time
	 */
	public void removeExpiredReservations() {
		Calendar limit = Calendar.getInstance();
		limit.add(Calendar.MINUTE, -15);
		Date cutoffTime = limit.getTime();

		boolean first = true;
		for (Reservation r : reservations) {
			if (r.getReservationTime().before(cutoffTime)) {
				if (first) {
					System.out.println("Removed the following reservations:");
				}
				r.showReservation();
				reservations.remove(r);
				writeReservation();
			}
		}
	}
}