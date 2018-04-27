package dynamic_comp;

import java.util.ArrayList;
import java.util.Random;

import static_comp.Point;

public class Population {

	/* Fields */
	private int initial_pop;
	private int max_pop;
	private int comfort_param;
	private Point initial_pos, final_pos;
	private ArrayList<Individual> individuals;
	
	/* Constructor */
	public Population(int initial_pop, int max_pop, int comfort_param, Point initial_pos, Point final_pos) {
		this.initial_pop = initial_pop;
		this.initial_pos = initial_pos;
		this.final_pos = final_pos;
		this.max_pop = max_pop;
		this.comfort_param = comfort_param;
		individuals = new ArrayList<Individual>();
		startPopulating();
	}
	
	/* Methods */
	private void startPopulating(){
		for(int elements = 0; elements < initial_pop; elements++)
			addIndividual(new Individual(this.initial_pos, this.final_pos));
	}
	
	void addIndividual(Individual individual) {
		if(checkPopulationLimit())
			while(checkPopulationLimit())
				epidemic();
		individuals.add(individual);
	}
	
	//Returns true if the population is higher then its limit, false otherwise
	public boolean checkPopulationLimit(){
		if(individuals.size() >= max_pop)
			return true;
		return false;
	}
	
	void epidemic(){
		Random random = new Random();
		float nextFloat;
		
		ArrayList<Individual> aux = new ArrayList<Individual>();
		for(int survivors = 0; survivors < 5; survivors++){
			aux.add(getMax(this.individuals));
		}
		
		for(Individual i : this.individuals){
			nextFloat = random.nextFloat();
			if(nextFloat <= i.getComfort()){
				aux.add(i);
			}
			else{
				i = null;
			}
		}
		
		this.individuals = aux;
	}
	
	//POR UML
	private Individual getMax(ArrayList<Individual> is){
		float max_comfort = is.get(0).getComfort();
		Individual strongest = is.get(0);
		for(Individual i: is){
			if(i.getComfort() > max_comfort){
				max_comfort = i.getComfort();
				strongest = i;
			}
		}
		is.remove(strongest);
		return strongest;
	}

	public int getComfort_param() {
		return comfort_param;
	}
	
	public ArrayList<Individual> getIndividuals() {
		return individuals;
	}
	
}
