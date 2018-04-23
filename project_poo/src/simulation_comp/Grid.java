package simulation_comp;

import java.util.ArrayList;

public class Grid {
	
	/* Fields */
	private int n_obst;
	private int m;
	private int n;
	private int cmax;
	
	private ArrayList<Population> populations;
	private ArrayList<Edge> edges;
	
	private Point initial_point;
	private Point final_point;
	
	/* Constructor */
	public Grid(int m, int n, int n_obst, Point initial_point, Point final_point){
		this.m = m;
		this.n = n;
		this.n_obst = n_obst;
		
		this.initial_point = initial_point;
		this.final_point = final_point;
		
		populations = new ArrayList<Population>();
		edges = new ArrayList<Edge>();
	}
	
	/* Methods */
	private void calculateMaxCost(){
		//TODO cmax = ...
	}
	
	public int getCmax() {
		return cmax;
	}
	
	void insertObst(Point[] obst){
		//TODO
	}
	
	void addPopulation(Population population){
		//TODO
	}
	
}