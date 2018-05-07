package dynamic_comp;

import java.util.ArrayList;
import java.util.Random;

import static_comp.Grid;

/**
 * Population represents a set of the class Individuals that has an initial and a maximum number of individuals.
 * These variables are immutable.
 *
 */

public class Population {
	
	/*Class Fields*/
	private static final int NR_SURVIVORS = 5;

	/* Fields */
	private int initial_pop;
	private int max_pop;
	private ArrayList<Individual> individuals;
	
	/**
	 * Constructs and initializes a new Population with an initial and maximum population
	 * 
	 * @param initial_pop
	 * @param max_pop
	 */
	public Population(int initial_pop, int max_pop) {
		this.initial_pop = initial_pop;
		this.max_pop = max_pop;
		individuals = new ArrayList<Individual>();
	}
	
	/**
	 * Adds n Individuals to the Population Array, where n is the initial population passed in the Population constructor
	 * 
	 * @param grid
	 * The grid related to the map.
	 * 
	 * @param comfort_param
	 * The parameter of the Individual's comfort.
	 */
	void startPopulating(Grid grid, int comfort_param){
		for(int elements = 0; elements < initial_pop; elements++)
			addIndividual(new Individual(grid, comfort_param));
	}
	
	
	/**
	 * Adds an individual to the Population array if the population max hasn't been reached.
	 * Otherwise it calls the epidemic method.
	 * 
	 * @param individual
	 */
	
	void addIndividual(Individual individual) {
		if(individuals.size() >= max_pop)
			while(individuals.size() >= max_pop)
				epidemic();
		individuals.add(individual);
	}
	
	/**
	 * When we reach the maximum population, the NR_SURVIVORS(that is defined as 5) individuals with more comfort
	 * instantly survive. The rest of the individuals live if the comfort is lower than a random number.
	 * Otherwise they die (individual is put to null).
	 */
	private void epidemic(){
		ArrayList<Individual> aux = new ArrayList<Individual>();
		Random random = new Random();
		
		for(int survivors = 0; survivors < NR_SURVIVORS; survivors++)
			aux.add(getIndMaxComfort());
		
		for(Individual i : individuals)			
			if(random.nextFloat() <= i.getComfort())
				aux.add(i);
			else
				i = null;
		
		individuals = aux;
	}
	
	/**
	 * Gets the individual with the most comfort from the Population Array and
	 * eliminates from the array.
	 * 
	 * @return individual
	 */
	private Individual getIndMaxComfort(){
		float max_comfort = -1;
		Individual strongest = null; //vai existir sempre individous logo nunva vai returnar null
		
		for(Individual individual: individuals){
			if(individual.getComfort() > max_comfort){
				max_comfort = individual.getComfort();
				strongest = individual;
			}
		}
		
		individuals.remove(strongest);
		return strongest;
	}
	
	/**
	 * 
	 * Gets all the elements of the population
	 * 
	 * @return ArrayList<Individual>
	 */
	ArrayList<Individual> getIndividuals() {
		return individuals;
	}
	
}
