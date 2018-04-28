package static_comp;

import java.util.ArrayList;

public class Grid {
	
	/* Fields */
	private int n;
	private int m;
	private int cmax;
	private Point initial_pos;
	private Point final_pos;
	private ArrayList<Edge> edges;
	private ArrayList<Point> obts;
	private ArrayList<Edge> sZones;
	
	/* Constructor */
	public Grid(int n, int m, ArrayList<Point> obts, ArrayList<Edge> sZones, Point initial_pos, Point final_pos){
		this.n = n;
		this.m = m;
		this.obts = obts;
		this.sZones = sZones;
		this.initial_pos = initial_pos;
		this.final_pos = final_pos;
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
		
		for(int i = 1; i <= m; i++) {
			for(int j = 1; j <= n; j++) {
				point_1 = new Point(j, i);
				
				if(j != n) {
					point_2 = new Point(j + 1, i);
					edge = new Edge(point_1, point_2);
					edges.add(edge);
				}
				
				if(i != m) {
					point_2 = new Point(j, i + 1);
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
	
	public int getN() {
		return n;
	}
	
	public int getM() {
		return m;
	}
	
	public Point getInitial_pos() {
		return initial_pos;
	}

	public Point getFinal_pos() {
		return final_pos;
	}
	
	public ArrayList<Point> getObts() {
		return obts;
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
	/* auxiliary methods */
	private boolean is_specialEdge(Edge edge, Edge sZone) { //ver se da para mudar isto
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