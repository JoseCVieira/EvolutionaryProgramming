package static_comp;

import java.util.ArrayList;

public class Grid {
	
	/* Fields */
	private int n_obst; //para que vai servir isto???
	private int row;
	private int col;
	private int cmax;
	private ArrayList<Edge> edges;
	private ArrayList<Point> obts;
	private ArrayList<Edge> sZones;
	
	/* Constructor */
	public Grid(int n_obst, int row, int col, ArrayList<Point> obts, ArrayList<Edge> sZones){
		this.n_obst = n_obst;
		this.row = row;
		this.col = col;
		this.obts = obts;
		this.sZones = sZones;
		edges = new ArrayList<Edge>();
		
		generateEdges();
		insertSpecialEdges();
		calculateMaxCost();
		insertObst();
	}
	
	/* Methods */
	private void generateEdges(){
		Point point_1, point_2;
		Edge edge;
		
		for(int i = 1; i <= row; i++) {
			for(int j = 1; j <= col; j++) {
				point_1 = new Point(i, j);
				
				if(j != col) {
					point_2 = new Point(i, j + 1);
					edge = new Edge(point_1, point_2);
					edges.add(edge);
				}
				
				if(i != row) {
					point_2 = new Point(i + 1, j);
					edge = new Edge(point_1, point_2);
					edges.add(edge);
				}
			}
		}
	}
	
	private void insertSpecialEdges(){
		for(Edge edge : edges)
			for(Edge sZone : sZones)
				if(is_specialEdge(edge, sZone))
					if(edge.getCost() < sZone.getCost())
						edge.setCost(sZone.getCost());
		
	}
	
	private void calculateMaxCost(){
		cmax = -1;
		for(Edge sZone : sZones)
			if(sZone.getCost() > cmax)
				cmax = sZone.getCost();
	}
	
	private void insertObst(){
		Point[] edge_p = new Point[2];
		
		for(Point point : obts) {
			for(Edge edge : edges) {
				edge_p = edge.getPoints();
				
				if(edge_p[0].equals(point))
					edge_p[0].setObst(true);
				else if(edge_p[1].equals(point))
					edge_p[1].setObst(true);
				
			}
		}
	}
	
	public int getCmax() {
		return cmax;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getN_obst() {
		return n_obst;
	}
	
	/* auxiliary methods */
	private boolean is_specialEdge(Edge edge, Edge sZone) {
		Point[] edge_p = new Point[2];
		Point[] szone_p = new Point[2];
		
		edge_p = edge.getPoints();
		szone_p = sZone.getPoints();
		
		if(edge_p[0].getX() >= szone_p[0].getX() && edge_p[0].getX() <= szone_p[1].getX() &&
		   edge_p[1].getX() >= szone_p[0].getX() && edge_p[1].getX() <= szone_p[1].getX() &&
		   edge_p[0].getY() >= szone_p[0].getY() && edge_p[0].getY() <= szone_p[1].getY() &&
		   edge_p[1].getY() >= szone_p[0].getY() && edge_p[1].getY() <= szone_p[1].getY())
			return true;
		return false;
	}
}