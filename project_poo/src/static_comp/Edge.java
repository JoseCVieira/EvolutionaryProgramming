package static_comp;

import java.util.Arrays;

public class Edge {

	/* Fields */
	private int cost;
	private Point[] points;
	
	/**
	 * Constructs Edge object and initializes its points
	 * and assigning a default cost of 1
	 * @param p1
	 * @param p2
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
	 * @param p2
	 * @param cost
	 */
	public Edge(Point p1, Point p2, int cost) {
		points = new Point[2];
		
		getPoints()[0] = p1;
		getPoints()[1] = p2;
		this.cost = cost;
	}

	/* Methods */
	void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getCost() {
		return cost;
	}

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

	@Override
	public String toString() {
		return ""+getPoints()[0];
	}

}
