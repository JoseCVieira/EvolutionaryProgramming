package dynamic_comp;

/**
 * Event is an abstract class that has a time and an individual associated. These variables are 
 * immutable once they are defined. The class has an abstract method action that is defined
 * based on the function that the Event's subclass should have when its time comes.
 */

public abstract class Event implements IEvent{
	
	/*fields*/
	private final double time;
	private final Individual individual;
	
	/**
	 * Constructs an Event with a specific time and individual. To be used needs
	 * to be defined in a subclass
	 * 
	 * @param time
	 * time at which the event occurs
	 * @param individual
	 * the individual that it is assigned to
	 */
	Event(double time, Individual individual){
		this.time = time;
		this.individual = individual;
	}

	
	/**
	 * The action of this method with be defined in the subclasses
	 * 
	 * @param context
	 * Simulation where all the individuals and the events are
	 */
	@Override
	public abstract void action(Simulation context);

	/**
	 * Returns the time of the Event
	 * 
	 * @return time
	 */
	protected final double getTime() {
		return time;
	}
	
	/**
	 * Returns the individual of the Event
	 * 
	 * @return individual
	 */
	
	protected final Individual getIndividual(){
		return individual;
	}
	
}
