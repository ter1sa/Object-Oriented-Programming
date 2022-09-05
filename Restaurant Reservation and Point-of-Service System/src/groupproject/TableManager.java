package groupproject;

import java.util.ArrayList;

/**
 * Implements methods to keep track of tables in the restaurant
 * 
 * @author Tan Yu Ling
 *
 */
public class TableManager {
	private ArrayList<Table> arrTables;

	/**
	 * Class Constructor Instantiates all tables in the restaurant
	 */
	public TableManager() {
		arrTables = new ArrayList<Table>();

		arrTables.add(0, new Table(1, 2));
		arrTables.add(1, new Table(2, 2));
		arrTables.add(2, new Table(3, 4));
		arrTables.add(3, new Table(4, 4));
		arrTables.add(4, new Table(5, 4));
		arrTables.add(5, new Table(6, 6));
		arrTables.add(6, new Table(7, 6));
		arrTables.add(7, new Table(8, 8));
		arrTables.add(8, new Table(9, 8));
		arrTables.add(9, new Table(10, 10));
	}

	/**
	 * Gets table ID of empty table
	 * 
	 * @param pax Number of customers to be seated
	 * @return table ID if empty table is present, or -1 if no suitable table is
	 *         found
	 */
	public int getEmptyTable(int pax) {
		int startIndex;
		if (pax > 8) {
			startIndex = 9;
		} else if (pax > 6) {
			startIndex = 7;
		} else if (pax > 4) {
			startIndex = 5;
		} else if (pax > 2) {
			startIndex = 2;
		} else {
			startIndex = 0;
		}

		for (int i = startIndex; i < arrTables.size(); i++) {
			if (arrTables.get(i).isOccupied() == false) {
				return i + 1;
			}
		}
		return -1;
	}

	/**
	 * Prints all tables
	 */
	public void showTables() {
		if (arrTables.size() == 0) {
			return;
		}
		System.out.println("Tables:\n" + "------------------------------");
		for (int i = 0; i < arrTables.size(); i++) {
			arrTables.get(i).showTable();
		}
	}

	/**
	 * Mark a table as occupied
	 * @param id table ID of table
	 */
	public void occupy(int id) {
		arrTables.get(id - 1).occupy();
	}

	/**
	 * Mark a table as vacant
	 * @param id table ID of table
	 */
	public void vacate(int id) {
		arrTables.get(id - 1).vacate();
	}
}