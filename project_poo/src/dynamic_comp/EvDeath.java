package dynamic_comp;

import java.util.Iterator;

/**
 * An EvDeath is a subclass of Event that is responsible to make the individual die. 
 * The time and individual are immutable once they are defined.
 */

public class EvDeath extends Event{

	/**
	 * Constructs and initializes a EvDeath with a specific time and individual
	 * 
	 * @param time
	 * time at which the event occurs
	 * @param individual
	 * the individual that it is assigned to
	 */
	EvDeath(double time, Individual individual){
		super(time, individual);
	}

	/**
	 * Eliminates all the events that belong to the individual and eliminates him from his population LinkedList.
	 * 
	 * @param context
	 * Simulation where all the individuals and the events are
	 */
	public void action(Simulation context) {
		Event e;
		for(Iterator<Event> i = context.getPec().getEvents().iterator();  i.hasNext(); ){
			e = i.next();
			if(this.getIndividual() == e.getIndividual())
				i.remove();
		}
		context.getPopulation().getIndividuals().remove(this.getIndividual());
	}
		
}
