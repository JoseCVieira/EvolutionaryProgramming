package dynamic_comp;

import java.util.ArrayList;

import simulation_comp.Point;

public class Population {

	/* Fields */
	private int initial_pop;
	private int max_pop;
	private int k;
	private Point initial_pos, final_pos;
	private ArrayList<Individual> individuals;
	
	/* Constructor */
	public Population(int initial_pop, int max_pop, int k, Point initial_pos, Point final_pos) {
		this.initial_pop = initial_pop;
		this.max_pop = max_pop;
		this.k = k;
		this.initial_pos = initial_pos;
		this.final_pos = final_pos;
		individuals = new ArrayList<Individual>();
		startPopulating();
	}
	
	/* Methods */
	void startPopulating(){
		
		for(int elements = 0; elements < initial_pop; elements++){
			addIndividual(new Individual(this.initial_pos));
		}
	}
	
	void addIndividual(Individual individual) {
		
		if(individuals.size() >= max_pop){
			//epidemic();
		}
		else{
			this.individuals.add(individual);
		}
	}
	
	//Returns true if the population is higher then its limit, false otherwise
	public boolean checkPopulationLimit(){
		if(individuals.size() >= max_pop){
			return true;
		}
		return false;
	}

	public ArrayList<Individual> getIndividuals() {
		return individuals;
	}
	
}
