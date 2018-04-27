package static_comp;

import java.util.ArrayList;

public class Path {

	/* Fields */
	private int cost;
	private ArrayList<Edge> edges;
	
	/* Constructor */
	public Path() {
		edges = new ArrayList<Edge>();
	}
	
	/* Methods */
	public void addEdge(Edge edge){
		int index = 0;
		
		for(Edge edge_aux : getEdges()) {
			if(edge_aux.equals(edge)) {
				removeEdges(index);
				break;
			}
			index++;
		}
		
		cost += edge.getCost();
		getEdges().add(edge);
	}
	
	public void removeEdges(int index) {
		for(int i = index; i < getEdges().size(); i++) { //da para fazer isto num for each??
			cost -= getEdges().get(i).getCost();
			getEdges().remove(i);
		}
	}

	public int getCost() {
		return cost;
	}
	
	public int getPathLength() {
		return edges.size();
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
}
