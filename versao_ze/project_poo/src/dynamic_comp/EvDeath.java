package dynamic_comp;

public class EvDeath extends Event{

	EvDeath(double time, Individual individual){
		super(time, individual);
	}

	@Override
	char action() {
		if(this.individual == null) return 'E';
		return 'D';
	}
	
}
