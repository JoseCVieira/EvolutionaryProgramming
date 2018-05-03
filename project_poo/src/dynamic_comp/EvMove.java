package dynamic_comp;

public class EvMove extends Event {

	EvMove(double time, Individual individual) {
		super(time, individual);
	}

	@Override
	void action(Simulation context) {
		double new_time;
		//System.out.println(time);
		if(this.individual == null) {
			
		}else {
			
			new_time = this.time+ Simulation.expRandom(context.getMove_param()*(1-Math.log(this.individual.getComfort())));	
			//System.out.println("[MOVE]\t time: "+time+" new_time: "+new_time);
			context.getPec().addEvent(new EvMove(new_time, this.individual));
			this.individual.move(context.getNewIndividualPosition(this.individual));
		}
	
	}
	

}
