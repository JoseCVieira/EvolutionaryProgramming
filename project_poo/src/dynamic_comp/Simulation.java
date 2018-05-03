package dynamic_comp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import main.Parser;
import static_comp.Edge;
import static_comp.Grid;
import static_comp.Point;

public class Simulation {
	
	static Random random = new Random();
	private float final_time;
	private float death_param;
	private float reprod_param;
	private float move_param;
	private double current_time;
	private Population population;
	private Grid grid;
	private PEC pec;
	private Event current_event;
	private Individual best_individual;
	private boolean final_hit;
	private int event_counter;
	private static Individual ind; //remover isto depois
	private static int cont = 0;//remover isto depois
	private Point defaultPoint  = new Point(0,0);
	private Edge defaultEdge = new Edge(defaultPoint,defaultPoint);
	public Simulation(Parser p) {
		int m = p.getInteger("grid0", 1); //rows
		int n = p.getInteger("grid0", 0); //cols
		
		
		Point initialPoint = p.getPoint("initialpoint0");//updatePoint()
		Point finalPoint = p.getPoint("finalpoint0");
		int nzones = p.getInteger("specialcostzones0", 0);
		ArrayList<Edge> sZones = new ArrayList<Edge>();
		
		for(int i= 0; i < nzones; i++) {
			sZones.add(p.getEdge("zone"+i));
		}
		
		
		int nobsts = p.getInteger("obstacles0", 0);
		ArrayList<Point> obsts = new ArrayList<Point>();
		for(int i= 0; i < nobsts; i++) {
			obsts.add( p.getPoint("obstacle"+i));
		}
		
		
		this.final_time = (Integer)p.getInteger("simulation0", 0);
		int init_pop = p.getInteger("simulation0", 1);		
		int max_pop = p.getInteger("simulation0", 2);
		int comfort_param = p.getInteger("simulation0", 3);
		this.death_param = p.getInteger("death0", 0);
		this.move_param =p.getInteger("reproduction0", 0);
		this.reprod_param = p.getInteger("move0", 0);
		
		
		Grid grid = new Grid(n, m, obsts, sZones, initialPoint, finalPoint);
		this.grid = grid;
		pec = new PEC();
		for(int i = 0; i <= EvObservation.N_OBSERVATIONS; i++) {
			pec.addEvent(new EvObservation(i*final_time/EvObservation.N_OBSERVATIONS));
			System.out.println(i*final_time/EvObservation.N_OBSERVATIONS);
		}
		createPopulation(init_pop, max_pop, comfort_param);
	}
	
	public void startSimulation() {
		while(current_time <= final_time) {
			
			if(!pec.events.isEmpty()){
				current_event = pec.nextEvent();
				if(current_event.getTime() <= final_time){
					current_time = current_event.getTime();
				}
				else break;
			
				/*for(int i = 0; i< 30; i++)
					System.out.println();*/
				
				ind = current_event.individual;
				//System.out.println(this);
				
				//if(cont++ == 100) {				
					/*try{
					    Thread.sleep(100);
					}catch(InterruptedException ex){
					    Thread.currentThread().interrupt();
					}*/
				///}
				//System.out.print(pec.events.size());
				current_event.action(this);
				this.event_counter++;
				//System.out.print(pec.events.size());
			
			}else{
				System.out.println("No more events");
				current_time = final_time;
			}
		}
	}
	
	private void createPopulation(int init_pop, int max_pop, int comfort_param) {
		population = new Population(init_pop, max_pop);
		population.startPopulating(grid, comfort_param);
		
		for(Individual i : population.getIndividuals()){
			createNewBornEvents(i);
		}
	}
	
	public Point getNewIndividualPosition(Individual i){
		int npoints;
		double rand_double = new Random().nextDouble();
		ArrayList<Point> obst = grid.getObts();
		ArrayList<Point> possible_positions = new ArrayList<Point>();
		ArrayList<Point> aux = new ArrayList<Point>();
		
		//get the neighbor points
		for(int m = -1; m <= 1; m = m + 2){
			if(m + i.getPosition().getY() < 1 || m + i.getPosition().getY() > grid.getM()) continue;
			possible_positions.add(new Point(i.getPosition().getX(), i.getPosition().getY() + m));
		}
		
		for(int n = -1; n <= 1; n = n + 2){
			if(n + i.getPosition().getX() < 1 || n + i.getPosition().getX() > grid.getN()) continue;
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
	
	 void  createNewBornEvents(Individual i){
		pec.addEvent(new EvDeath(current_time + expRandom(death_param*(1-Math.log(1-i.getComfort()))), i));
		pec.addEvent(new EvReproduction(current_time + expRandom(reprod_param*(1-Math.log(i.getComfort()))),i));
		pec.addEvent(new EvMove(current_time + expRandom(move_param*(1-Math.log(i.getComfort()))), i));
		
	}
	
	public Individual getBestIndividual() {
		return best_individual;
	}
	public boolean getFinalHit() {
		return final_hit;
	}
	public int getEventCounter() {
		return event_counter;
	}
	public static double expRandom(double m) {
		double next = random.nextDouble();
		return -m*Math.log(1.0-next);
	}
	
	public float getFinalTime() {
		return this.final_time;
	}
	public float getDeath_param() {
		return death_param;
	}

	public float getReprod_param() {
		return reprod_param;
	}
	public Population getPopulation() {
		return population;
	}
	
	public float getMove_param() {
		return move_param;
	}
	
	public PEC getPec() {
		return pec;
	}
	public void setPec(Object object) {
		this.pec =(PEC) object;
	}


	@Override
	public String toString() {
		String print = "";
		boolean obst = false;
		
		ArrayList<Point> obsts = grid.getObts();
		//Individual individual = population.getIndividuals().get(0);
		Individual individual = ind;
		
		int ind_id = 0;
		
		for(Individual i : population.getIndividuals()) {
			ind_id++;
			if(ind.equals(i))
				break;
		}
		
		print +="*** Individual "+ ind_id +" ***\n\n";
		print +="current time = " +current_time+"\n";
		print +="position = " +individual.getPosition()+ "\n";
		print +="cost = " +individual.getPath().getCost()+"\n";
		print +="comfort = " +individual.getComfort()+ "\n";
		print +="dist = " +individual.getDist()+ "\n";
		print +="length = " +individual.getLength() +"\n\n";
		
		print +="path =\n" + individual.getPath()+"\n\n";
		for(int i = 1; i <= grid.getM(); i++) { //col
			for(int j = 1; j <= grid.getN(); j++) { //row
				
				String id = String.format("%3d", ind_id);

				if(individual.getPosition().equals(new Point(j, i)))
					print +="["+id+"]";
				else
					print +="     ";

				obst = false;
				for(Point p : obsts)
					if(p.equals(new Point(j, i)))
						obst = true;
				
				if(obst)
					print +="[O]";
				else {
					if(grid.getInitial_pos().equals(new Point(j, i)))
						print +="[I]";
					else if(grid.getFinal_pos().equals(new Point(j, i)))
						print +="[F]";
					else
						print +="   ";
				}
				print +=new Point(j, i);
				print +="   ";
			}
			print +="\n\n";
		}
		
		return print;
	}


	
}
