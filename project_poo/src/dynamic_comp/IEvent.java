package dynamic_comp;

public interface IEvent {

	/**
	 * The action of this method with be defined in the subclasses
	 * 
	 * @param context
	 * Simulation where all the individuals and the events are
	 */
	abstract void action(Simulation context);
}
