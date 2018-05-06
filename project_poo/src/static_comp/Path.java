package static_comp;

import java.util.ArrayList;

public class Path {

	/* Fields */
	private int cost;
	private ArrayList<Edge> edges;
	
	/**
	 * 
	 */
	public Path() {
		setEdges(new ArrayList<Edge>());
	}
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 * @param cost
	 */
	public void addEdge(Point p1, Point p2, int cost){
		Edge e1 = new Edge(p1, p2, cost); //repeating edge
		Edge e2 = new Edge(p2, p1, cost); //coming back
		
		boolean repeating = false;
		
		this.cost = 0;
		ArrayList<Edge> new_path = new ArrayList<Edge>();
		
		for(Edge edge : edges) {
			if(e1.equals(edge))
				break;
			else if(e2.equals(edge)) {
				repeating = true;
				break;
			}
			
			new_path.add(edge);
			this.cost += edge.getCost();
		}
		
		if(!repeating) {
			new_path.add(e1);
			this.cost += cost;
		}
		
		setEdges(new_path);
	}
	/**
	 * 
	 * @return
	 */
	public int getCost() {
		return cost;
	}
	/**
	 * 
	 * @return
	 */
	public int getPathLength() {
		return edges.size();
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	/**
	 * 
	 * @param edges
	 */
	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
		cost = 0;
		
		for(Edge e : edges) {
			cost += e.getCost();
		}
	}

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
