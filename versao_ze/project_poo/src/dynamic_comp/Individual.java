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
		
		calculateDist();
		path = new Path();
	}

	public Individual(Grid grid, int comfort_param, Path path, int length_prefix) {
		this.comfort_param = comfort_param;
		this.path = path;
		this.grid = grid;
		
		path.removeEdges((int)Math.ceil(path.getPathLength() * length_prefix)); //ceil() method rounds a number UPWARDS to the nearest integer
		Point[] points = path.getEdges().get(path.getPathLength() - 1).getPoints();
		
		length = path.getPathLength();
		position = points[1];
		calculateDist();
		calculateComfort();
	}
	
	/* Methods */
	private void calculateDist(){
		dist = Math.abs(position.getX() - getGrid().getFinal_pos().getX()) + Math.abs(position.getY() - getGrid().getFinal_pos().getY());
	}
	
	private void calculateComfort(){
		double aux_1, aux_2;
		
		aux_1 = 1 - ((path.getCost() - length + 2)/((getGrid().getCmax() - 1)*length + 3));
		aux_1 = Math.pow(aux_1, getComfort_param());
		
		aux_2 = 1 - (dist/(getGrid().getM() + getGrid().getN() + 1));
		aux_2 = Math.pow(aux_2, getComfort_param());
		
		comfort = (float)(aux_1 * aux_2);
	}
	
	public void move(Point new_position) {
		int cost = 0;
		
		ArrayList<Edge> edges = path.getEdges();
		Edge new_edge = new Edge(position, new_position, 1);
		
		for(Edge edge : edges)
			if(edge.equals(new_edge))
				cost = edge.getCost();
		
		new_edge.setCost(cost);
		path.addEdge(new_edge);
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
