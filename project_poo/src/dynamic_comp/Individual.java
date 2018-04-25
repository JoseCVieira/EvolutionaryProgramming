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
	Path path;
	
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
		
		path.removeEdges((int)Math.floor(path.getEdges().size() * length_prefix));
		ArrayList<Edge> edges = path.getEdges();
		Point[] points = edges.get(edges.size() - 1).getPoints();
		
		length = edges.size();
		position = points[1];
		calculateDist();
	}
	
	/* Methods */
	private void calculateDist(){
		dist = Math.abs(position.getX() - final_pos.getX()) + Math.abs(position.getY() - final_pos.getY());
	}

	public void Move(Point position) {
		// TODO depois de fazer move length = path.getLength();
		
		this.position = position;
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
	
}
