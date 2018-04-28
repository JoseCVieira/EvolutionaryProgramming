package static_comp;

import java.util.ArrayList;

public class Path {

	/* Fields */
	private int cost;
	private ArrayList<Edge> edges;
	
	/* Constructor */
	public Path() {
		setEdges(new ArrayList<Edge>());
	}
	
	/* Methods */
	public void addEdge(Point p1, Point p2, int cost){
		Edge e1 = new Edge(p1, p2, cost);
		ArrayList<Edge> new_path = new ArrayList<Edge>();
		boolean repeat = false;
		
		this.cost = 0;
		for(Edge edge : getEdges()) {
			if(p2.equals(edge.getPoints()[0])) {
				repeat = true;
				break;
			}else if(p2.equals(edge.getPoints()[1])) {
				repeat = true;
				this.cost += edge.getCost();
				new_path.add(edge);
				break;
			}else {
				this.cost += edge.getCost();
				new_path.add(edge);
			}				
		}
		setEdges(new_path);
		
		if(!repeat) {
			this.cost += cost;
			getEdges().add(e1);
		}
		
	}

	public int getCost() {
		return cost;
	}
	
	public int getPathLength() {
		return getEdges().size();
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}

	@Override
	public String toString() {
		String print = "";
		
		for(Edge edge : getEdges()) {
			print += edge;
		}
		
		return print;
	}
	
}
