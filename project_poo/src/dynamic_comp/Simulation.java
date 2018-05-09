package dynamic_comp;

import java.util.ArrayList;
import java.util.Random;

import static_comp.Edge;
import static_comp.Grid;
import static_comp.Parser;
import static_comp.Point;

/**
 * Simulation is the class that make the interconnection between the static structure and the dynamic components
 */

public class Simulation {
	
	static final int N_OBSERVATIONS = 20;
	
	private int final_time;
	private int death_param;
	private int reprod_param;
	private int move_param;
	private double current_time;
	private boolean final_hit;
	private int event_counter;
	private Population population;
	private Grid grid;
	private PEC pec;
	private Event current_event;
	private Individual best_individual;
	
	/**
	 * Constructs simulation by using the data parsed by the given object Parser
	 * @param p
	 * Parser object with parsed data
	 */
	public Simulation(Parser p) {
		int m = p.getInteger("grid0", 1); //rows
		int n = p.getInteger("grid0", 0); //cols
		
		Point initialPoint = p.getPoint("initialpoint0");//updatePoint()
		Point finalPoint = p.getPoint("finalpoint0");
		int nzones = p.getInteger("specialcostzones0", 0);
		ArrayList<Edge> sZones = new ArrayList<Edge>();
		
		for(int i= 0; i < nzones; i++)
			sZones.add(p.getEdge("zone"+i));
		
		int nobsts = p.getInteger("obstacles0", 0);
		ArrayList<Point> obsts = new ArrayList<Point>();
		for(int i= 0; i < nobsts; i++)
			obsts.add( p.getPoint("obstacle"+i));
		
		this.final_time = p.getInteger("simulation0", 0);
		int init_pop = p.getInteger("simulation0", 1);		
		int max_pop = p.getInteger("simulation0", 2);
		int comfort_param = p.getInteger("simulation0", 3);
		this.death_param = p.getInteger("death0", 0);
		this.move_param =p.getInteger("reproduction0", 0);
		this.reprod_param = p.getInteger("move0", 0);
		
		grid = new Grid(n, m, obsts, sZones, initialPoint, finalPoint);
		pec = new PEC();
		
		Individual.grid = grid;
		Individual.comfort_param = comfort_param;
		
		for(int i = 0; i <= N_OBSERVATIONS; i++)
			pec.addEvent(new EvObservation((double)i*getFinal_time()/N_OBSERVATIONS));
		
		createPopulation(init_pop, max_pop);
		
		setBest_individual(new Individual());
		final_hit = false;
	}
	
	/**
	 * Makes simulation to read each event one by one and making it actuate
	 */
	public void startSimulation() {
		while(current_time <= getFinal_time()) {
			if(!pec.events.isEmpty()){
				current_event = pec.nextEvent();
				
				if(current_event.getTime() <= getFinal_time())
					current_time = current_event.getTime();
				else
					break;
				/*Event Action*/
				current_event.action(this);
				event_counter++;
			}else
				current_time = getFinal_time();
		}
		
		System.out.println("\n\n\tPath of the best fit individual:\t"+best_individual.getPath());
	}
	
	/**
	 * Creates the population by giving the initial population, max population and a given comfort parameter
	 * 
	 * @param init_pop
	 * int with the size of the initial population 
	 * @param max_pop
	 * int with the maximum possible size of the population 
	 */
	private void createPopulation(int init_pop, int max_pop) {
		population = new Population(init_pop, max_pop);
		population.startPopulating(this);
		
		for(Individual i : population.getIndividuals())
			createNewBornEvents(i);
	}
	
	/**
	 * Finds next position that will be attributed to the individual. First it will generate all possible points without
	 * take into account if they are an obstacle or not. Next step is verify is some of them are obstacles. If it is an obstacle,
	 * then it is removed from the possible positions. At the end, dividing by the number of possibilities its used a random value to
	 * decide what's the next position.
	 * 
	 * @param i
	 * Individual from which the method gets its new position
	 * @return Point
	 */
	Point getNewIndividualPosition(Individual i){
		double rand_double = new Random().nextDouble();
		ArrayList<Point> obst = getGrid().getObts();
		ArrayList<Point> possible_positions = new ArrayList<Point>();
		ArrayList<Point> aux = new ArrayList<Point>();
		
		//get the neighbor points
		for(int m = -1; m <= 1; m = m + 2){
			if(m + i.getPosition().getY() < 1 || m + i.getPosition().getY() > getGrid().getM()) continue;
			possible_positions.add(new Point(i.getPosition().getX(), i.getPosition().getY() + m));
		}
		
		for(int n = -1; n <= 1; n = n + 2){
			if(n + i.getPosition().getX() < 1 || n + i.getPosition().getX() > getGrid().getN()) continue;
			possible_positions.add(new Point(i.getPosition().getX() + n, i.getPosition().getY()));
		}
		
		//eliminate the ones with obstacles
		for(Point o : obst)
			for(Point p : possible_positions)
				if(o.equals(p))
					aux.add(p);

		for(Point a : aux){
			if(possible_positions.remove(a)) continue;
			break;
		}
		
		//choose a random point to move to
		int npoints = possible_positions.size();
		int index = 0;
		for(Point p : possible_positions) {
			if(rand_double <= (double)(index + 1)/npoints)
				return p;
			index++;
		}		
		return null;
	}
	
	/**
	 * Creates 3 different types of events for a given individual
	 * @param i
	 * Individual to which the events are related 
	 */
	void createNewBornEvents(Individual i){
		double time = current_time + expRandom(death_param*(1-Math.log(1-i.getComfort())));
		pec.addEvent(new EvDeath(time, i));
		
		time = current_time + expRandom(reprod_param*(1-Math.log(i.getComfort())));
		pec.addEvent(new EvReproduction(time,i));
		
		time = current_time + expRandom(reprod_param*(1-Math.log(i.getComfort())));
		pec.addEvent(new EvMove(time, i));
	}
	
	/**
	 * Used to calculate an exponential Random value given a mean value m
	 * @param m
	 * double type mean value for exponential random observation
	 * @return double
	 */
	static double expRandom(double m) {
		Random random = new Random();
		return -m*Math.log(1.0-random.nextDouble());
	}
	
	/**
	 * @return returns reproduction parameter
	 */
	int getReprod_param() {
		return reprod_param;
	}

	/**
	 * @return returns move parameter
	 */
	int getMove_param() {
		return move_param;
	}
	
	/**
	 * @return returns population
	 */
	Population getPopulation() {
		return population;
	}	
	
	/**
	 * @return returns pec
	 */
	PEC getPec() {
		return pec;
	}

	/**
	 * @return returns grid
	 */
	Grid getGrid() {
		return grid;
	}
	
	/**
	 * @return final hit
	 */
	boolean isFinal_hit() {
		return final_hit;
	}

	/**
	 * set final hit. True when some individual reach the final position
	 * @param final_hit
	 * boolean type 
	 */
	void setFinal_hit(boolean final_hit) {
		this.final_hit = final_hit;
	}

	/**
	 * @return returns the best individual
	 */
	Individual getBest_individual() {
		return best_individual;
	}

	/**
	 * set the best individual that is the individual with the best comfort in case of none of the individuals
	 * reach the final position or the individual that has the lower cost path in case of at least one of them reach the final
	 * position
	 * @param best_individual
	 * The related individual object
	 */
	void setBest_individual(Individual best_individual) {
		this.best_individual = best_individual;
	}
	
	/**
	 * @return returns event counter
	 */
	int getEvent_counter() {
		return event_counter;
	}
	
	/**
	 * @return returns final instant
	 */
	int getFinal_time() {
		return final_time;
	}
	
}
