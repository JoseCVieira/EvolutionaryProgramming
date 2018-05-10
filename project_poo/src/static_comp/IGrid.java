package static_comp;

public interface IGrid {

	/**
	 * Generates edges according to a given m and n attributes
	 */
	void generateEdges();
	
	/**
	 * Creates edges belonging to a special zone
	 */
	void create_specialEdges();
	
	/**
	 * Finds max cost in the grid by searching in sZones List
	 */
	void calculateMaxCost();
}