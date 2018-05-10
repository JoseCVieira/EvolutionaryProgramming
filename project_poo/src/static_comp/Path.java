package static_comp;

import java.util.ArrayList;

/**
 * Path is a Class that holds a array list of type Edge that represents the path that the individual
 * went through. This Path also has a cost associated (sum of the costs of all edges in the path)
 */

public class Path {

	/* Fields */
	private int cost;
	private ArrayList<Edge> edges;
	
	/**
	 * always create a new array list empty of type Edge
	 */
	public Path() {
		setEdges(new ArrayList<Edge>());
	}
	
	/**
	 * this method allows adding an edge to the individual's path. When it is called, it will create the possible two ways to move,
	 * represented by e1 and e2. Then, each edge already in the array list, will be compared with the 2 edges just created.
	 * If it is equals to e1, it means that the individual is repeating the edge, so it is not added.
	 * If it is equals to e2, it means that the individual is repeating the edge, but in the reverse way, so the edge is
	 * removed and so the cost.
	 * 
	 * @param p1
	 * Initial Point object of the Edge
	 * @param p2
	 * Ending Point object of the Edge
	 * @param cost
	 * Related cost of the Edge
	 */
	public void addEdge(Point p1, Point p2, int cost){
		Edge e1 = new Edge(p1, p2, cost); //repeating edge
		
		boolean repeating = false;
		
		this.cost = 0;
		ArrayList<Edge> new_path = new ArrayList<Edge>();
		
		for(Edge edge : edges) {
			if(p2.equals(edge.getPoints()[0])) {
				repeating = true;
				break;
			}else if(p2.equals(edge.getPoints()[1]))
				break;
			else {
				new_path.add(edge);
				this.cost += edge.getCost();
			}
		}
		
		if(!repeating) {
			new_path.add(e1);
			this.cost += cost;
		}
		
		setEdges(new_path);
	}
	
	/**
	 * Returns path's cost
	 * @return int
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Returns path's length
	 * @return int
	 */
	public int getPathLength() {
		return edges.size();
	}
	
	/**
	 * Returns path's edges
	 * @return ArrayList
	 * ArrayList of edges
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	/**
	 * sets all edges of the path
	 * @param edges
	 * ArrayList of edges
	 */
	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
		cost = 0;
		
		for(Edge e : edges) {
			cost += e.getCost();
		}
	}

	/**
	 * textual description of this class
	 */
	@Override
	public String toString() {
		String print = "{";
		for(Edge edge : getEdges()) {
			print += edge + ",";
			if(edge.equals(getEdges().get(edges.size() - 1)))
				print += edge.getPoints()[1];
		}
		print += "}";
		
		return print;
	}

}
