package dynamic_comp;

import java.util.ArrayList;
import java.util.Random;

import static_comp.Grid;

public class Population {
	
	private static final int NR_SURVIVORS = 5;

	/* Fields */
	private int initial_pop;
	private int max_pop;
	private ArrayList<Individual> individuals;
	
	/* Constructor */
	public Population(int initial_pop, int max_pop) {
		this.initial_pop = initial_pop;
		this.max_pop = max_pop;
		
		individuals = new ArrayList<Individual>();
	}
	
	/* Methods */
	public void startPopulating(Grid grid, int comfort_param){
		for(int elements = 0; elements < initial_pop; elements++)
			addIndividual(new Individual(grid, comfort_param));
	}
	
	void addIndividual(Individual individual) {
		if(checkPopulationLimit())
			while(checkPopulationLimit())
				epidemic();
		individuals.add(individual);
	}
	
	public ArrayList<Individual> getIndividuals() {
		return individuals;
	}
	
	//Returns true if the population is higher then its limit, false otherwise
	public boolean checkPopulationLimit(){
		if(individuals.size() >= max_pop)
			return true;
		return false;
	}
	
	void epidemic(){
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
	
	//POR UML
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
	
}
