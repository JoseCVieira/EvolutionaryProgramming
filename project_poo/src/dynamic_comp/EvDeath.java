package dynamic_comp;

import java.util.Iterator;

public class EvDeath extends Event{

	EvDeath(double time, Individual individual){
		super(time, individual);
	}

	@Override
	protected void action(Simulation context) {
		Event e;
		//eliminates all the events that belong to the individual that died
		for(Iterator<Event> i = context.getPec().events.iterator();  i.hasNext(); ){
			e = i.next();
			if(e.individual == individual)
				i.remove();
		}
		//puts the individual pointing to null to be collected by gc
		individual = null;
	}
	
}
