package simulation_comp;

import java.util.ArrayList;

public class Aggregate extends SpecialZone{

	/* Fields */
	ArrayList<SpecialZone> specialZones;
	
	/* Constructor */
	public Aggregate(Point p1, Point p2, int cost) {
		super(p1, p2, cost);
		specialZones = new ArrayList<SpecialZone>();
	}
	
	/* Methods */
	void addZone(SpecialZone specialZone){
		//TODO
	}
	
}
