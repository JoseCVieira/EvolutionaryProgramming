package dynamic_comp;

import static_comp.Point;

public class Test {

	public static void main(String[] args) {
		Simulation s = new Simulation();
		Population p = new Population(10, 100, 4, new Point(2,2), new Point(4,4));
		Point new_point = s.getNewIndividualPosition(p.getIndividuals().get(0));
		System.out.println(new_point);
	}

}
