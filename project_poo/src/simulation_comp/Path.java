package simulation_comp;

import java.util.ArrayList;

public class Path {

	/* Fields */
	private int cost;
	private ArrayList<Edge> edges;
	
	/* Constructor */
	public Path() {
		edges = new ArrayList<Edge>();
		cost = 0; // default ja e 0.. fica na mesma?
	}
	
	/* Methods */
	void addEdge(Edge edge){
		//TODO cost += ...
		//TODO edges.add(e);
		//TODO verificar se o path nao e repetido (se for voltar a tras...)
	}
}
