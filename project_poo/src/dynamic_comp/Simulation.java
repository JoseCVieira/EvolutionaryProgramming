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
	
	
	private static Individual ind; //remover isto depois
	private static int cont = 0;//remover isto depois
	private Point defaultPoint  = new Point(0,0);
	private Edge defaultEdge = new Edge(defaultPoint,defaultPoint);
	public Simulation(Parser p) {
		int n = 5; //row
		int m = 4; //col
		
		
		Point initialPoint = (Point) p.getValue("initialpoint0", defaultPoint);
		Point finalPoint = (Point) p.getValue("finalpoint0", defaultPoint);
		int nzones = (Integer) p.getValue("specialcostzones0", 0);
		ArrayList<Edge> sZones = new ArrayList<Edge>();
		
		for(int i= 0; i < nzones; i++) {
			sZones.add((Edge) p.getValue("zone"+i, defaultEdge));
		}
		
		
		int nobsts = (Integer)p.getValue("obstacles0", 0);
		ArrayList<Point> obsts = new ArrayList<Point>();
		for(int i= 0; i < nobsts; i++) {
			obsts.add((Point) p.getValue("obstacle"+i, defaultPoint));
		}
		
		
		this.final_time = (Integer)p.getValue("simulation0", 0);
		int init_pop = (Integer)p.getValue("simulation0", 1);		
		int max_pop = (Integer)p.getValue("simulation0", 2);
		int comfort_param = (Integer)p.getValue("simulation0", 3);
		this.death_param = (Integer)p.getValue("death0", 0);
		this.move_param =(Integer)p.getValue("reproduction0", 0);
		this.reprod_param = (Integer)p.getValue("move0", 0);
		
		
		Grid grid = new Grid(n, m, obsts, sZones, initialPoint, finalPoint);
		this.grid = grid;
		pec = new PEC();
		
		createPopulation(init_pop, max_pop, comfort_param);
	}
	
	public void startSimulation() {
		while(current_time < final_time) {
			
			if(!pec.events.isEmpty()){
				current_event = pec.nextEvent();
				if(current_event.getTime() < final_time){
					current_time = current_event.getTime();
				}
				else break;
			
				for(int i = 0; i< 30; i++)
					System.out.println();
				
				ind = current_event.individual;
				System.out.println(this);
				
				//if(cont++ == 100) {				
					try{
					    Thread.sleep(100);
					}catch(InterruptedException ex){
					    Thread.currentThread().interrupt();
					}
				///}
				
				simulateEvent(current_event);
			
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
	
	private void simulateEvent(Event current_event){
		double time, length_prefix;
		
		if(current_event.action() == 'M'){ //new move time for the individual
			time = current_time + expRandom(move_param*(1-Math.log(current_event.individual.getComfort())));
			
			pec.addEvent(new EvMove(time, current_event.individual));
			current_event.individual.move(getNewIndividualPosition(current_event.individual));
		}
		else if(current_event.action() == 'R'){ //new reproduction time for the parent
			time = current_time + expRandom(reprod_param*(1-Math.log(current_event.individual.getComfort())));
			length_prefix = current_event.individual.getLength()*0.9 + current_event.individual.getComfort()*0.1;
					
			pec.addEvent(new EvReproduction(time, current_event.individual));
			//new child and 3 new events
			
			
			//ceil() method rounds a number UPWARDS to the nearest integer
			Individual i = new Individual(current_event.individual.getGrid(), current_event.individual.getComfort_param(),
			  current_event.individual.getPath(), (int)Math.ceil(length_prefix));
			
			population.addIndividual(i);
			createNewBornEvents(i);
		}
		else if(current_event.action() == 'D'){
			//eliminates all the events that belong to the individual that died
			Event e;
			for(Iterator<Event> i = pec.events.iterator();  i.hasNext(); ){
				e = i.next();
				if(e.individual == current_event.individual){
					//pec.events.remove(e);
					i.remove();
				}
			}
			System.out.println("DEAD");
			//puts the individual pointing to null to be collected by gc
			current_event.individual = null;
		}
		else{
			System.out.println("Erro. Evento desconhecido"); // nao podemos ter prints além dos pedidos
		}
		return;
	}
	
	private Point getNewIndividualPosition(Individual i){
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
	
	void createNewBornEvents(Individual i){
		pec.addEvent(new EvDeath(current_time + expRandom(death_param*(1-Math.log(1-i.getComfort()))), i));
		pec.addEvent(new EvReproduction(current_time + expRandom(reprod_param*(1-Math.log(i.getComfort()))),i));
		pec.addEvent(new EvMove(current_time + expRandom(move_param*(1-Math.log(i.getComfort()))), i));
	}
	
	public static double expRandom(double m) {
		double next = random.nextDouble();
		return -m*Math.log(1.0-next);
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
