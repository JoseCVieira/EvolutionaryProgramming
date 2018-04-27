package main;

import java.util.ArrayList;

import dynamic_comp.Simulation;
import static_comp.Edge;
import static_comp.Grid;
import static_comp.Point;

public class Main {

	public static void main(String[] args) {
		
		int n = 5; //row
		int m = 4; //col
		
		Point initialPoint = new Point(1, 1);
		Point finalPoint = new Point(5, 4);
		
		Point aux_1 = new Point(2, 2);
		Point aux_2 = new Point(3, 3);
		Edge sZone = new Edge(aux_1, aux_2, 4);
		
		ArrayList<Edge> sZones = new ArrayList<Edge>();
		sZones.add(sZone);
		
		ArrayList<Point> obsts = new ArrayList<Point>();
		aux_1 = new Point(2, 1);
		obsts.add(aux_1);
		aux_1 = new Point(2, 3);
		obsts.add(aux_1);
		aux_1 = new Point(2, 4);
		obsts.add(aux_1);
		aux_1 = new Point(4, 2);
		obsts.add(aux_1);
		
		float final_instant = 100;
		int init_pop = 10;
		int max_pop = 100;
		int comfort_param = 3;
		float death_param = 10;
		float move_param = 1;
		float reprod_param = 1;
		
		Grid grid = new Grid(n, m, obsts, sZones, initialPoint, finalPoint);
		Simulation simulation = new Simulation(grid, final_instant, death_param, move_param, reprod_param);
		simulation.createPopulation(init_pop, max_pop, comfort_param);
		simulation.createInitalPopulationEvents();
	}
	
}
