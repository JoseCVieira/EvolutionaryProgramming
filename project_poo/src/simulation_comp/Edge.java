package simulation_comp;

public class Edge {

	/* Fields */
	private int cost;
	private Point[] points;
	
	/* Constructor */
	public Edge(Point p1, Point p2) {
		points = new Point[2];
		
		points[0] = p1;
		points[1] = p2;
	}

	/* Methods */
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
