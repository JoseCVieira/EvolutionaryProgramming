package dynamic_comp;

public interface IPendEvHandler {
	/**
	 * Adds an event to the Event SortetSet.
	 * 
	 * @param event
	 * object Event to be added
	 */
	 void addEvent(Event event);
	
	/**
	 * Returns the first Event in the SortetSet and eliminates it from the Set.
	 * 
	 * @return Event
	 */
	 Event nextEvent();
}
