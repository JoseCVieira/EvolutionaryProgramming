package dynamic_comp;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Population represents a set of the class Individuals that has an initial and a maximum number of individuals.
 * These variables are immutable.
 */

public class Population {
	
	/*Class Fields*/
	private static final int NR_SURVIVORS = 5;

	/* Fields */
	private int initial_pop;
	private int max_pop;
	private LinkedList<Individual> individuals;
	
	/**
	 * Constructs and initializes a new Population with an initial and maximum population
	 * 
	 * @param initial_pop
	 * @param max_pop
	 */
	public Population(int initial_pop, int max_pop) {
		this.initial_pop = initial_pop;
		this.max_pop = max_pop;
		individuals = new LinkedList<Individual>();
	}
	
	/**
	 * Adds n Individuals to the Population Array, where n is the initial population passed in the Population constructor
	 * 
	 * @param grid
	 * The grid related to the map.
	 * 
	 * @param comfort_param
	 * The parameter of the Individual's comfort.
	 * 
	 * @param context
	 * A Simulation Object
	 */
	void startPopulating(Simulation context){
		for(int elements = 0; elements < initial_pop; elements++)
			addIndividual(new Individual(), context);
	}
	
	
	/**
	 * Adds an individual to the Population array if the population max hasn't been reached.
	 * Otherwise it calls the epidemic method.
	 * 
	 * @param individual
	 * 
	 * @param context
	 * A Simulation Object
	 */
	
	void addIndividual(Individual individual, Simulation context) {
		if(individuals.size() >= max_pop)
			while(individuals.size() >= max_pop)
				epidemic(context);
		individuals.add(individual);
	}
	
	/**
	 * When we reach the maximum population, the NR_SURVIVORS(that is defined as 5) individuals with more comfort
	 * instantly survive. The rest of the individuals live if the comfort is lower than a random number.
	 * Otherwise they die (individual is put to null).
	 * 
	 * @param context
	 * A Simulation Object
	 */
	private void epidemic(Simulation context){
		LinkedList<Individual> aux = new LinkedList<Individual>();
		Random random = new Random();
		Event e;
		Individual i = null;
		for(int survivors = 0; survivors < NR_SURVIVORS; survivors++)
			aux.add(getIndMaxComfort());
		
		for(Iterator<Individual> iterator = context.getPopulation().individuals.iterator(); iterator.hasNext(); ){	
			i = iterator.next();
			if(random.nextFloat() <= i.getComfort())
				aux.add(i);
			else{
				for(Iterator<Event> it = context.getPec().events.iterator();  it.hasNext(); ){	
					e = it.next();
					if(i == e.individual)
						it.remove();
				}
				iterator.remove();
				i = null;
			}
			
		}	
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
		Individual strongest = new Individual();
		
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
	 * Gets all the elements of the population
	 * 
	 * @return LinkedList<Individual>
	 */
	LinkedList<Individual> getIndividuals() {
		return individuals;
	}
	
}
