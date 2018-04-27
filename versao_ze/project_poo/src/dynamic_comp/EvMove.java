package dynamic_comp;

public class EvMove extends Event {

	EvMove(double time, Individual individual) {
		super(time, individual);
	}

	@Override
	char action() {
		if(this.individual == null) return 'E';
		return 'M';
	}

}
