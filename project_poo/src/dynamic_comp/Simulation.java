package dynamic_comp;

import java.util.ArrayList;
import java.util.Random;

import static_comp.Edge;
import static_comp.Grid;
import static_comp.Parser;
import static_comp.Point;

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
		
		for(int i = 0; i <= N_OBSERVATIONS; i++)
			pec.addEvent(new EvObservation(i*getFinal_time()/N_OBSERVATIONS));
		
		createPopulation(init_pop, max_pop, comfort_param);
		
		setBest_individual(new Individual(grid, comfort_param));
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
	}
	/**
	 * Creates the population by giving the initial population, max population and a given comfort parameter
	 * 
	 * @param init_pop
	 * @param max_pop
	 * @param comfort_param
	 */
	private void createPopulation(int init_pop, int max_pop, int comfort_param) {
		population = new Population(init_pop, max_pop);
		population.startPopulating(getGrid(), comfort_param, this);
		
		for(Individual i : population.getIndividuals())
			createNewBornEvents(i);
	}
	/**
	 * 
	 * @param i
	 * @return
	 */
	Point getNewIndividualPosition(Individual i){
		int npoints;
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
		npoints = possible_positions.size();
		for(int index = 0; index < npoints; index++)
			if(rand_double <= (double)(index + 1)/npoints)
				return possible_positions.get(index);
		
		return null;
	}
	/**
	 * Creates 3 different types of events for a given individual
	 * @param i
	 */
	void createNewBornEvents(Individual i){
		
		double time = current_time + expRandom(death_param*(1-Math.log(1-i.getComfort())));
		//System.out.println("Morte" + time);
		pec.addEvent(new EvDeath(time, i));
		
		time = current_time + expRandom(reprod_param*(1-Math.log(i.getComfort())));
		//System.out.println("Reprodução" + time);

		pec.addEvent(new EvReproduction(time,i));
		
		time = current_time + expRandom(reprod_param*(1-Math.log(i.getComfort())));
		//System.out.println("Mover" + time);
		pec.addEvent(new EvMove(time, i));
	}
	/**
	 * Used to calculate an exponential Random value given a mean value m
	 * @param m
	 * @return double
	 */
	static double expRandom(double m) {
		Random random = new Random();
		return -m*Math.log(1.0-random.nextDouble());
	}
	
	int getReprod_param() {
		return reprod_param;
	}

	int getMove_param() {
		return move_param;
	}
	
	Population getPopulation() {
		return population;
	}	
	
	PEC getPec() {
		return pec;
	}

	Grid getGrid() {
		return grid;
	}
	
	boolean isFinal_hit() {
		return final_hit;
	}

	void setFinal_hit(boolean final_hit) {
		this.final_hit = final_hit;
	}

	Individual getBest_individual() {
		return best_individual;
	}

	void setBest_individual(Individual best_individual) {
		this.best_individual = best_individual;
	}
	
	int getEvent_counter() {
		return event_counter;
	}
	
	int getFinal_time() {
		return final_time;
	}
	
}
