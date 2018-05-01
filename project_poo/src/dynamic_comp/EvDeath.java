package dynamic_comp;

import java.util.Iterator;

public class EvDeath extends Event{

	EvDeath(double time, Individual individual){
		super(time, individual);
	}

	@Override
	void action(Simulation context) {
		System.out.println("DEATH");
		//context.getPec().addEvent(event);
		if(this.individual == null) {
			
		}else {
			Event e;
			//eliminates all the events that belong to the individual that died
			for(Iterator<Event> i = context.getPec().events.iterator();  i.hasNext(); ){
				e = i.next();
				if(e.individual == this.individual){
					//pec.events.remove(e);
					i.remove();
				}
			}
			System.out.println("DEAD");
			//puts the individual pointing to null to be collected by gc
			this.individual = null;
			
		}
	}
	
}
