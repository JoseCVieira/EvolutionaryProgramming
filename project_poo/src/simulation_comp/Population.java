package simulation_comp;

import java.util.ArrayList;

public class Population {

	/* Fields */
	private int initial_pop;
	private int comfort_sensitivity;
	private ArrayList<Individual> individuals;
	
	/* Constructor */
	public Population(int initial_pop, int comfort_sensitivity) {
		this.initial_pop = initial_pop;
		this.comfort_sensitivity = comfort_sensitivity;
		
		individuals = new ArrayList<Individual>();
		
		// TODO addIndividual()... (populacao inicial)
	}
	
	/* Methods */
	void addIndividual(Individual individual) {
		// TODO
	}
	
}
