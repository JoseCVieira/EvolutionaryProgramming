package dynamic_comp;

import java.util.ArrayList;

/**
 * Individuals are the objects that create the dynamism in the simulation.
 */

import static_comp.Edge;
import static_comp.Grid;
import static_comp.Path;
import static_comp.Point;

public class Individual {

	/* Fields */
	private int length;
	private int dist;
	private float comfort;
	private Point position;
	private Path path;
	
	static Grid grid;
	static int comfort_param;
	
	/* Constructors */
	/**
	 * Constructs and initializes a new Individual. This constructor is called at the beginning 
	 * of the simulation to create the initial individuals
	 */
	Individual() {
		this.position = grid.getInitial_pos();
		
		path = new Path();
		calculateDist();
		calculateComfort();
	}

	/**
	 * This constructor is called when it occurs a reproduction. It receives the path of the individual that have generate it 
	 * as well as the length prefix that is used to assign part of the received path
	 * 
	 * @param path
	 * @param length_prefix
	 */
	Individual(Path path, int length_prefix) {
		this.path = new Path();

		
		ArrayList<Edge> edges = new ArrayList<Edge>();
		
		if(length_prefix > path.getPathLength())
			edges = path.getEdges();
		else
			edges = new ArrayList<Edge> (path.getEdges().subList(0, length_prefix));
		
		this.path.setEdges(edges);
		length = path.getPathLength();
		
		if(length != 0)
			position = path.getEdges().get(length - 1).getPoints()[1];
		else
			position = grid.getInitial_pos();
		
		calculateDist();
		calculateComfort();
	}
	
	/* Methods */
	
	/**
	 * Calculate dist. Distance(dist) is the minimum moves that it needs to make to reach the end position, assuming that 
	 * all points are non obstacles and ignoring the cost of the moves.
	 */
	private void calculateDist(){
		dist = Math.abs(position.getX() - grid.getFinal_pos().getX()) + Math.abs(position.getY() - grid.getFinal_pos().getY());
	}
	
	/**
	 * Calculate comfort. This field is used to determine what are the best path of the simulation and so to calculate 
	 * the events and the associated time
	 */
	private void calculateComfort(){
		double aux_1, aux_2;
		
		aux_1 = path.getCost() - length + 2;
		aux_1 /= ((grid.getCmax() - 1)*length) + 3;
		aux_1 = 1 - aux_1;
		aux_1 = Math.pow(aux_1, comfort_param);
		
		aux_2 = dist;
		aux_2 /= (grid.getN() + grid.getM() + 1);
		aux_2 = 1 - aux_2;
		aux_2 = Math.pow(aux_2, comfort_param);
		
		comfort = (float)(aux_1 * aux_2);
	}
	
	/**
	 * This method is called when a EvMove occur. It creates two edges edge_1 and edge_2 that are used to determine the cost
	 * of the movement that the individual just made. Once it founds the corresponding cost by searching all edges belonging
	 * to the grid, it calls the method addEdge where it decide if the individual is repeating the received edge or not.
	 * At the end where the path is already updated, is attributed its current position.
	 * @param new_position
	 */
	void move(Point new_position) {
		Edge edge_1 = new Edge(position, new_position);
		Edge edge_2 = new Edge(new_position, position);
		
		for(Edge edge : grid.getEdges()) {
			if(edge_1.equals(edge) || edge_2.equals(edge)) {
				path.addEdge(position, new_position, edge.getCost());
				break;
			}
		}
		
		//position = new_position;
		if(path.getPathLength() != 0)
			position = path.getEdges().get(path.getPathLength() - 1).getPoints()[1];
		else
			position = grid.getInitial_pos();
			
		length = path.getPathLength();
		calculateDist();
		calculateComfort();
	}

	/**
	 * @return returns its current position
	 */
	Point getPosition() {
		return position;
	}
	
	/**
	 * @return returns the distance between the current position and the final point
	 */
	int getDist() {
		return dist;
	}
	
	/**
	 * @return returns the length of its path
	 */
	int getLength() {
		return length;
	}
	
	/**
	 * @return returns its path
	 */
	Path getPath() {
		return path;
	}
	
	/**
	 * @return returns its comfort
	 */
	float getComfort() {
		return comfort;
	}	
}
