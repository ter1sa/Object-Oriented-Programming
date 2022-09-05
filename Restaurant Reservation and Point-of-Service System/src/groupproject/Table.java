package groupproject;

/**
 * Table entity
 * @author Tan Yu Ling
 *
 */
public class Table {
	private int tableID;
	private int capacity;
	private boolean occupied;

	/**
	 * Class Constructor
	 * @param id table ID
	 * @param c Number of people that may be seated at this table
	 */
	public Table(int id, int c) {
		tableID = id;
		capacity = c;
		occupied = false;
	}

	/**
	 * Prints this table
	 */
	public void showTable() {
		System.out.println("Table ID: " + tableID + " " + "Capacity: " + capacity + " " + "Status: "
				+ (occupied ? "occupied" : "vacant"));
	}

	/**
	 * Sets occupied to false
	 */
	public void vacate() {
		occupied = false;
	}

	/**
	 * Sets occupied to true
	 */
	public void occupy() {
		occupied = true;
	}

	/**
	 * Gets this table ID
	 * @return table ID
	 */
	public int getTableID() {
		return tableID;
	}

	/**
	 * Gets this table capacity
	 * @return table capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Gets this table's occupancy status
	 * @return true if occupied, false if unoccupied
	 */
	public boolean isOccupied() {
		return occupied;
	}
}