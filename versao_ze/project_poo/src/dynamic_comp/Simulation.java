package dynamic_comp;

import java.util.Iterator;
import java.util.Random;

import static_comp.Grid;

public class Simulation {
	
	static Random random = new Random();
	private float final_instant;
	private float death_param;
	private float reprod_param;
	private float move_param;
	private float current_time;
	private Population population;
	private Grid grid;
	private PEC pec;
	
	public Simulation(Grid grid, float final_instant, float death_param, float move_param, float reprod_param) {
		this.final_instant = final_instant;
		this.death_param = death_param;
		this.reprod_param = reprod_param;
		this.move_param = move_param;
		this.grid = grid;
	}
	
	public void createPopulation(int init_pop, int max_pop, int comfort_param) {
		population = new Population(init_pop, max_pop);
		population.startPopulating(grid, comfort_param);
	}
	
	public void createInitalPopulationEvents(){ // mudar para startSimulation
		for(Individual i : population.getIndividuals()){
			createNewBornEvents(i);
		}
	}
	
	void simulateEvent(Event current_event){
		
		if(current_event.action() == 'M'){
			//new move time for the individual
			pec.addEvent(new EvMove(current_time + expRandom(move_param*(1-Math.log(current_event.individual.getComfort()))), current_event.individual));
			//make the actual move in the simulation!!!!!!!!
		}
		else if(current_event.action() == 'R'){
			//new reproduction time for the parent
			pec.addEvent(new EvReproduction(current_time + expRandom(reprod_param*(1-Math.log(current_event.individual.getComfort()))),current_event.individual));
			//new child and 3 new events
			Individual i = new Individual(current_event.individual.getGrid(), current_event.individual.getComfort_param(),
					current_event.individual.getPath(), (int) (current_event.individual.getLength()*0.9 + current_event.individual.getComfort()*0.1));
			population.addIndividual(i);
			createNewBornEvents(i);
		}
		else if(current_event.action() == 'D'){
			//eliminates all the events that belong to the individual that died
			Event e;
			for(Iterator<Event> i = pec.events.iterator();  i.hasNext(); ){
				e = i.next();
				if(e.individual == current_event.individual){
					pec.events.remove(e);
				}
			}
			//puts the individual pointing to null to be collected by gc
			current_event.individual = null;
		}
		else{
			System.out.println("Erro. Evento desconhecido");
		}
		return;
	}
	
	void createNewBornEvents(Individual i){
		pec.addEvent(new EvDeath(current_time + expRandom(death_param*(1-Math.log(1-i.getComfort()))), i));
		pec.addEvent(new EvReproduction(current_time + expRandom(reprod_param*(1-Math.log(i.getComfort()))),i));
		pec.addEvent(new EvMove(current_time + expRandom(move_param*(1-Math.log(i.getComfort()))), i));
	}
	
	public static double expRandom(double m) {
		double next = random.nextDouble();
		return -m*Math.log(1.0-next);
	}

}
