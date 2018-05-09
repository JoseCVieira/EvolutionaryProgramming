package static_comp;

import java.util.Arrays;

/**
 * Edge is a class used to represent a connection between two points, where it has a cost that is 1 by default.
 * This class is used, for instance, to represent every connection in the grid and every move made by an individual (path)
 * It is also used to represent a special zone once it was the needed fields.
 */

public class Edge {

	/* Fields */
	private int cost;
	private Point[] points;
	
	/**
	 * Constructs Edge object and initializes its points
	 * and assigning a default cost of 1
	 * @param p1 
	 * first point of the edge
	 * @param p2 
	 * second point of the edge
	 */
	public Edge(Point p1, Point p2) {
		points = new Point[2];
		
		getPoints()[0] = p1;
		getPoints()[1] = p2;
		cost = 1; //default
	}
	
	/**
	 * Constructs Edge object and initializes its points
	 * and assigning a given cost
	 * @param p1 
	 * first point of the edge
	 * @param p2 
	 * second point of the edge
	 * @param cost 
	 * non default cost
	 */
	public Edge(Point p1, Point p2, int cost) {
		points = new Point[2];
		
		getPoints()[0] = p1;
		getPoints()[1] = p2;
		this.cost = cost;
	}

	/* Methods */
	
	/**
	 * cost the cost to set
	 * @param cost
	 * integer type value representing the cost to be setted
	 */
	void setCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * returns the cost associated with the edge 
	 * @return cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * returns the two points associated with the edge 
	 * @return Point[]
	 */
	public Point[] getPoints() {
		return points;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(points);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (!Arrays.equals(points, other.points))
			return false;
		return true;
	}

	/**
	 * textual description of this class
	 */
	@Override
	public String toString() {
		return ""+getPoints()[0];
	}

}
