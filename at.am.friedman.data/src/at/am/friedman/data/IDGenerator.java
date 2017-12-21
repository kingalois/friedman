package at.am.friedman.data;

/**
 * Generates new Ids(increment with ++id)
 * @author Alois
 *
 */
public class IDGenerator {

	private int id = 0;

	public void setId(int id) {
		if (this.id < id) {
			this.id = id;
		}
	}

	/** 
	 * 
	 * @return increment and return the new value
	 */
	public int getNewId() {
		return ++id; // increment and return the new value
	}

}
