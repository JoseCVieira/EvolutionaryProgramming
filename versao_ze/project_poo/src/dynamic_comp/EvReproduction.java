package dynamic_comp;

public class EvReproduction extends Event {
	
	EvReproduction(double time, Individual individual){
		super(time, individual);
	}

	@Override
	char action() {
		if(this.individual == null){
			return 'E';
		}
		return 'R';
	}

}
