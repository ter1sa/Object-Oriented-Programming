package groupproject;

/**
 * Interface to auto-generate ID numbers
 * @author Tan Yu Ling
 *
 */
public interface Indexable {
	/**
	 * Looks for an unused ID number in the range of 1 to infinity. 
	 * If there are missing IDs in the range of numbers used, it will return the missing ID.
	 * Otherwise, return one more than the largest ID number.
	 * @return unused ID number
	 */
	public int generateID();
	
	/**
	 * Checks if the ID passed in has already been used as the ID of an object
	 * @param id  ID to be checked
	 * @return -1 if ID is not already used. If used, returns ArrayList index of the object with that ID
	 */
	public int getIndexByID(int id);
}
