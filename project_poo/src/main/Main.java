package main;

import java.util.ArrayList;

import static_comp.Edge;
import static_comp.Grid;
import static_comp.Point;

public class Main {

	public static void main(String[] args) {
		
		int col = 2;
		int row = 3;
		
		Point initialPoint = new Point(1, 1);
		Point finalPoint = new Point(5, 4);
		
		Point aux_1 = new Point(2, 2);
		Point aux_2 = new Point(3, 3);
		Edge sZone = new Edge(aux_1, aux_2, 4);
		
		ArrayList<Edge> sZones = new ArrayList<Edge>();
		sZones.add(sZone);
		
		int n_obst = 4;
		
		ArrayList<Point> obsts = new ArrayList<Point>();
		aux_1 = new Point(2, 1);
		obsts.add(aux_1);
		aux_1 = new Point(2, 3);
		obsts.add(aux_1);
		aux_1 = new Point(2, 4);
		obsts.add(aux_1);
		aux_1 = new Point(4, 2);
		obsts.add(aux_1);
		
		int final_instant = 100;
		int initial_pop = 10;
		int max_pop = 100;
		int comfort_sensitivity = 3;
		int death_param = 10;
		int move_param = 1;
		int reprod_param = 1;
		
		Grid grid = new Grid(n_obst, col, row, obsts, sZones);
	}
	
}
