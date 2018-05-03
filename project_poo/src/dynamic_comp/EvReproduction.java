package dynamic_comp;

public class EvReproduction extends Event {
	
	EvReproduction(double time, Individual individual){
		super(time, individual);
	}

	@Override
	void action(Simulation context) {
		double new_time;
		double length_prefix;
		
		new_time = time + Simulation.expRandom(context.getReprod_param()*(1-Math.log(individual.getComfort())));
		context.getPec().addEvent(new EvReproduction(new_time, individual));
		
		//new child and 3 new events
		length_prefix = individual.getLength()*0.9 + individual.getComfort()*0.1;
		//ceil() method rounds a number UPWARDS to the nearest integer
		Individual i = new Individual(individual.getGrid(), individual.getPath(),
				(int)Math.ceil(length_prefix), individual.getComfort_param());
		
		context.getPopulation().addIndividual(i);
		context.createNewBornEvents(i);
	}

}
