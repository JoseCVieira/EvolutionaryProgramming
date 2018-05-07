package dynamic_comp;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * PEC - Pending Event Container saves all the events associated to the simulation.
 * In this way, the PEC contains an Event TreeSet that introduces events based on their time.
 * 
 *
 */

public class PEC{
	
	/*fields*/
	SortedSet<Event> events = new TreeSet<Event>(new Comparator<Event>(){

		@Override
		public int compare(Event o1, Event o2) {
			if(o1.getTime() > o2.getTime()) return 1;
			else if(o1.getTime() < o2.getTime()) return -1;
			else return 0;
		}});
	
	/**
	 * Adds an event to the Event SortetSet.
	 * 
	 * @param event
	 */
	void addEvent(Event event){
		events.add(event);
	}
	
	/**
	 * 
	 * Returns the first Event in the SortetSet and eliminates it from the Set.
	 * 
	 * @return Event
	 */
	Event nextEvent(){
		Event first_event = events.first();
		events.remove(first_event);
		return first_event;
	}
	
}