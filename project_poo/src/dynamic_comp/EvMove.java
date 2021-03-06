package dynamic_comp;

/**
 * An EvMove is a subclass of Event that is responsible to make the individual move. 
 * The time and individual are immutable once they are defined.
 */

public class EvMove extends Event {

	/**
	 * Constructs and initializes a EvMove with a specific time and individual
	 * 
	 * @param time
	 * time at which the event occurs
	 * @param individual
	 * the individual that it is assigned to
	 */
	EvMove(double time, Individual individual) {
		super(time, individual);
	}
	
	/**
	 * Creates a new Event Move for the specific individual and makes the individual move.
	 * Checks if the final point was already reached. If yes, and if the individual's path has less cost
	 * than the best one so far, the best path is updated. If not, and if the individual has
	 * reached the final point, his path is saved in the best path. Otherwise, if the individual has better
	 * comfort than the comfort of the best path, the best individual is also updated.
	 * 
	 * @param context
	 * Simulation where all the individuals and the events are
	 */
	public void action(Simulation context) {
		double new_time;
		
		new_time = this.getTime() + Simulation.expRandom(context.getMove_param()*(1-Math.log(this.getIndividual().getComfort())));			
		
		context.getPec().addEvent(new EvMove(new_time, this.getIndividual()));
		this.getIndividual().move(context.getNewIndividualPosition(this.getIndividual()));
		
		if(!context.isFinal_hit()) {
			if(this.getIndividual().getPosition().equals(context.getGrid().getFinal_pos())) {
				context.setFinal_hit(true);
				
				Individual i = new Individual(this.getIndividual().getPath(), this.getIndividual().getPath().getPathLength());
				context.setBest_individual(i);
				
			}else if(this.getIndividual().getComfort() > context.getBest_individual().getComfort()) {
				
				Individual i = new Individual(this.getIndividual().getPath(), this.getIndividual().getPath().getPathLength());
				context.setBest_individual(i);
			}
		} else {
			if(this.getIndividual().getPosition().equals(context.getGrid().getFinal_pos())) {				
				if(this.getIndividual().getPath().getCost() < context.getBest_individual().getPath().getCost()) {
					
					Individual i = new Individual(this.getIndividual().getPath(),	this.getIndividual().getPath().getPathLength());
					context.setBest_individual(i);
				}
			}
		}
			
	}

}
