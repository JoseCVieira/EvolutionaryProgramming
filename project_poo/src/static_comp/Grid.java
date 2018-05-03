package static_comp;

import java.util.ArrayList;

public class Grid {
	
	/* Fields */
	private int n;
	private int m;
	private int cmax;
	private Point initial_pos;
	private Point final_pos;
	private ArrayList<Point> obts;
	private ArrayList<Edge> edges;
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
		create_specialEdges();
		calculateMaxCost();
		
		this.sZones = null;
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
	
	private void create_specialEdges() {
		int ini_x, ini_y, final_x, final_y;
		Edge e;
		Point p1, p2;
		
		for(Edge sZone : sZones) {
			ini_x   = sZone.getPoints()[0].getX();
			ini_y   = sZone.getPoints()[0].getY();
			final_x = sZone.getPoints()[1].getX();
			final_y = sZone.getPoints()[1].getY();
		
			for(int i = ini_x; i < final_x; i++) {
				p1 = new Point(i, ini_y);
				p2 = new Point(i+1, ini_y);
				e = new Edge(p1, p2);
				
				for(Edge edge : edges)
					if(edge.equals(e))
						if(edge.getCost() < sZone.getCost())
							edge.setCost(sZone.getCost());
				
				p1 = new Point(i, final_y);
				p2 = new Point(i+1, final_y);
				e = new Edge(p1, p2);
				
				for(Edge edge : edges)
					if(edge.equals(e))
						if(edge.getCost() < sZone.getCost())
						edge.setCost(sZone.getCost());
			}
			
			for(int i = ini_y; i < final_y; i++) {
				p1 = new Point(ini_x, i);
				p2 = new Point(ini_x, i+1);
				e = new Edge(p1, p2);
				
				for(Edge edge : edges)
					if(edge.equals(e))
						if(edge.getCost() < sZone.getCost())
							edge.setCost(sZone.getCost());
				
				p1 = new Point(final_x, i);
				p2 = new Point(final_x, i+1);
				e = new Edge(p1, p2);
				
				for(Edge edge : edges)
					if(edge.equals(e))
						if(edge.getCost() < sZone.getCost())
							edge.setCost(sZone.getCost());
			}
		}
	}
	
	private void calculateMaxCost(){
		cmax = -1;
		for(Edge sZone : sZones)
			if(sZone.getCost() > cmax)
				cmax = sZone.getCost();
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
	
}