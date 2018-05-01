package dynamic_comp;

public class EvMove extends Event {

	EvMove(double time, Individual individual) {
		super(time, individual);
	}

	@Override
	void action(Simulation context) {
		double new_time;
		System.out.println("MOVE");

		if(this.individual == null) {
			
		}else {
			new_time = this.time+ context.expRandom(context.getMove_param()*(1-Math.log(this.individual.getComfort())));	
			context.getPec().addEvent(new EvMove(new_time, this.individual));
			this.individual.move(context.getNewIndividualPosition(this.individual));
		}
	
	}
	

}
