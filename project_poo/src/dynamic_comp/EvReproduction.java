package dynamic_comp;

/**
 * An EvReproduction is a subclass of Event that is responsible for creating a new Individual
 * The time and individual are immutable once they are defined.
 */

public class EvReproduction extends Event {
	
	/**
	 * Constructs and initializes an EvReproduction with a specific time and individual
	 * 
	 * @param time
	 * time at which the event occurs
	 * @param individual
	 * the individual that it is assigned to
	 */
	EvReproduction(double time, Individual individual){
		super(time, individual);
	}

	
	/**
	 * Creates a new reproduction event for the specific individual and creates a new child,
	 * based on the parent's path and comfort, with 3 new events(Move, Death, Reproduction)
	 * 
	 * @param context
	 * Simulation where all the individuals and the events are
	 */
	@Override
	protected void action(Simulation context) {
		double new_time;
		double length_prefix;
				
		new_time = time + Simulation.expRandom(context.getReprod_param()*(1-Math.log(individual.getComfort())));
			
		context.getPec().addEvent(new EvReproduction(new_time, individual));
		
		length_prefix = individual.getLength()*0.9 + individual.getComfort();
		Individual i = new Individual(individual.getPath(), (int)Math.ceil(length_prefix));
		
		context.getPopulation().addIndividual(i, context);
		context.createNewBornEvents(i);
		
		if(!context.isFinal_hit())
			if(individual.getComfort() > context.getBest_individual().getComfort())
				context.setBest_individual(i);
		
	}

}
