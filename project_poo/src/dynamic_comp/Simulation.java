package dynamic_comp;

import java.util.Iterator;
import java.util.Random;

import static_comp.Point;

public class Simulation {
	
	static Random random = new Random();
	private float death_param;
	private float reprod_param;
	private float move_param;
	private int comfort_param;
	private int init_pop;
	private int max_pop;
	private float current_time;
	private Point initial_pos;
	private Point final_pos;
	private PEC pec;
	private Population population = new Population(init_pop, max_pop, comfort_param, initial_pos, final_pos);
	
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
			Individual i = new Individual(current_event.individual.getPath(), (int) (current_event.individual.getLength()*0.9 + current_event.individual.getComfort()*0.1),
					current_event.individual.getFinal_pos());
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
	
	void createInitalPopulationEvents(){
		for(Individual i : population.getIndividuals()){
			createNewBornEvents(i);
		}
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
