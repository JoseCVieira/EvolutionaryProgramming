package dynamic_comp;

import java.util.ArrayList;

import static_comp.Edge;
import static_comp.Path;
import static_comp.Point;

public class Individual {

	/* Fields */
	private int length;
	private int dist;
	private Point position, final_pos;
	private Path path;
	private float comfort;

	/* Constructors */
	
	public Individual(Point position, Point final_pos) {
		this.position = position;
		this.final_pos = final_pos;
		calculateDist();
		path = new Path();
	}

	public Individual(Path path, int length_prefix, Point final_pos) {
		this.path = path;
		this.final_pos = final_pos;
		
		path.removeEdges((int)Math.ceil(path.getPathLength() * length_prefix)); //ceil() method rounds a number UPWARDS to the nearest integer
		Point[] points = path.getEdges().get(path.getPathLength() - 1).getPoints();
		
		length = path.getPathLength();
		position = points[1];
		calculateDist();
	}
	
	/* Methods */
	private void calculateDist(){
		dist = Math.abs(position.getX() - final_pos.getX()) + Math.abs(position.getY() - final_pos.getY());
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

	//UML
	public Point getFinal_pos() {
		return final_pos;
	}
	
	//UML
	public Path getPath() {
		return path;
	}

	//UML
	public float getComfort() {
		return comfort;
	}

	//UML
	public void setComfort(float comfort) {
		this.comfort = comfort;
	}
	
	
}
