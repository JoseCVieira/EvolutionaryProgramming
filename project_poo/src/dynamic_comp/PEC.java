package dynamic_comp;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class PEC{
	
	SortedSet<Event> events = new TreeSet<Event>(new Comparator<Event>(){

		@Override
		public int compare(Event o1, Event o2) {
			if(o1.getTime() > o2.getTime()) return 1;
			else if(o1.getTime() < o2.getTime()) return -1;
			else return 0;
		}});
	
	public void addEvent(Event event){
		events.add(event);
	}
	
	public Event nextEvent(){
		Event first_event = events.first();
		events.remove(first_event);
		return first_event;
	}
}
