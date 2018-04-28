package dynamic_comp;

import java.util.ArrayList;

import static_comp.Edge;
import static_comp.Grid;
import static_comp.Path;
import static_comp.Point;

public class Individual {

	/* Fields */
	private int length;
	private int dist;
	private int comfort_param;
	private float comfort;
	private Point position;
	private Path path;
	private Grid grid;

	/* Constructors */
	public Individual(Grid grid, int comfort_param) {
		this.position = grid.getInitial_pos();
		this.comfort_param = comfort_param;
		this.grid = grid;
		
		comfort = (float) 0.1;
		
		calculateDist();
		path = new Path();
	}

	public Individual(Grid grid, int comfort_param, Path path, int length_prefix) {
		int index = 0;
		int index_rem;
		
		this.comfort_param = comfort_param;
		this.path = path;
		this.grid = grid;
		
		//ceil() method rounds a number UPWARDS to the nearest integer
		index_rem = (int) Math.ceil(path.getPathLength() * length_prefix);
		
		ArrayList<Edge> edges = path.getEdges();
		
		for(Edge edge : edges) {
			if(index++ >= index_rem)
				edges.remove(edge);
		}
		
		path.setEdges(edges);
		length = path.getPathLength();
		
		if(path.getPathLength() != 0) {
			position = path.getEdges().get(path.getPathLength() - 1).getPoints()[1];
		}else {
			position = grid.getInitial_pos();
		}
		
		calculateDist();
		calculateComfort();
	}
	
	/* Methods */
	private void calculateDist(){
		dist = Math.abs(position.getX() - getGrid().getFinal_pos().getX()) + Math.abs(position.getY() - getGrid().getFinal_pos().getY());
	}
	
	private void calculateComfort(){
		double aux_1, aux_2;
		
		aux_1 = path.getCost() - length + 2;
		aux_1 /= ((getGrid().getCmax() - 1)*length) + 3;
		aux_1 = 1 - aux_1;
		aux_1 = Math.pow(aux_1, comfort_param);
		
		aux_2 = dist;
		aux_2 /= (getGrid().getN() + getGrid().getM() + 1);
		aux_2 = 1 - aux_2;
		aux_2 = Math.pow(aux_2, comfort_param);
		
		comfort = (float)(aux_1 * aux_2);
	}
	
	public void move(Point new_position) {
		Edge edge_1 = new Edge(position, new_position);
		Edge edge_2 = new Edge(new_position, position);
		
		for(Edge edge : grid.getEdges())
			if(edge_1.equals(edge) || edge_2.equals(edge)) {
				path.addEdge(position, new_position, edge.getCost());
				break;
			}
		
		position = new_position;
		length = path.getPathLength();
		calculateDist();
		calculateComfort();
	}

	public Point getPosition() {
		return position;
	}
	
	public int getDist() {
		return dist;
	}
	
	public int getLength() {
		return length;
	}
	
	public Path getPath() {
		return path;
	}

	public float getComfort() {
		return comfort;
	}

	public Grid getGrid() {
		return grid;
	}

	public int getComfort_param() {
		return comfort_param;
	}
	
}
