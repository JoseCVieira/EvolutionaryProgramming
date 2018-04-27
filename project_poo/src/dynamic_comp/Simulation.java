package dynamic_comp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import static_comp.Point;
import static_comp.Grid;

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
	private Grid grid;
	private Population population = new Population(init_pop, max_pop, comfort_param, initial_pos, final_pos);
	
	
	void simulateEvent(Event current_event){
		
		if(current_event.action() == 'M'){
			//new move time for the individual
			pec.addEvent(new EvMove(current_time + expRandom(move_param*(1-Math.log(current_event.individual.getComfort()))), current_event.individual));
			Point new_position = getNewIndividualPosition(current_event.individual);
			current_event.individual.move(new_position);
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
	
	//UML
	public Point getNewIndividualPosition(Individual i){
		
		int npoints;
		double rand_double = new Random().nextDouble();
		ArrayList<Point> obst = grid.getObts();
		ArrayList<Point> possible_positions = new ArrayList<Point>();
		ArrayList<Point> aux = new ArrayList<Point>();
		
		//get the neighbor points
		for(int m = -1; m <= 1; m = m + 2){
			if(m + i.getPosition().getX() < 0 || m + i.getPosition().getX() > grid.getCol() - 1) continue;
			possible_positions.add(new Point(i.getPosition().getX() + m, i.getPosition().getY()));
		}
		
		for(int n = -1; n <= 1; n = n + 2){
			if(n + i.getPosition().getY() < 0 || n + i.getPosition().getY() > grid.getRow() - 1) continue;
			possible_positions.add(new Point(i.getPosition().getX(), i.getPosition().getY() + n));
		}
		
		System.out.println(possible_positions.toString());
		//eliminate the ones with obstacles
		for(Point o : obst){
			for(Point p : possible_positions){
				if(!o.equals(p)){
					aux.add(p);
				}
			}
		}
		System.out.println(aux.toString());
		possible_positions = aux;
		
		//choose a random point to move to
		npoints = possible_positions.size();
		for(int index = 0; index < npoints; index++){
			if(rand_double <= (index + 1)/npoints){
				return possible_positions.get(index);
			}
		}
		
		return null;
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
	
	
	//UML
	public Point getNewIndividualPosition(Individual i){
		
		int npoints;
		double rand_double = new Random().nextDouble();
		ArrayList<Point> obst = grid.getObts();
		ArrayList<Point> possible_positions = new ArrayList<Point>();
		ArrayList<Point> aux = new ArrayList<Point>();
		
		//get the neighbor points
		for(int m = -1; m <= 1; m = m + 2){
			if(m + i.getPosition().getX() < 0 || m + i.getPosition().getX() > grid.getCol() - 1) continue;
			possible_positions.add(new Point(i.getPosition().getX() + m, i.getPosition().getY()));
		}
		
		for(int n = -1; n <= 1; n = n + 2){
			if(n + i.getPosition().getY() < 0 || n + i.getPosition().getY() > grid.getRow() - 1) continue;
			possible_positions.add(new Point(i.getPosition().getX(), i.getPosition().getY() + n));
		}
		
		System.out.println(possible_positions.toString());
		//eliminate the ones with obstacles
		for(Point o : obst){
			for(Point p : possible_positions){
				if(!o.equals(p)){
					aux.add(p);
				}
			}
		}
		System.out.println(aux.toString());
		possible_positions = aux;
		
		//choose a random point to move to
		npoints = possible_positions.size();
		for(int index = 0; index < npoints; index++){
			if(rand_double <= (index + 1)/npoints){
				return possible_positions.get(index);
			}
		}
		
		return null;
	}
	
	public static double expRandom(double m) {
		double next = random.nextDouble();
		return -m*Math.log(1.0-next);
	}

}
