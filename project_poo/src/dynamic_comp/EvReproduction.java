package dynamic_comp;

public class EvReproduction extends Event {
	
	EvReproduction(double time, Individual individual){
		super(time, individual);
	}

	@Override
	void action(Simulation context) {
		double new_time;
		double length_prefix;
			//System.out.println(time);
		if(this.individual == null){
			
		}else {
			new_time = time + Simulation.expRandom(context.getReprod_param()*(1-Math.log(this.individual.getComfort())));
			//System.out.println("[REPRO]\t time: "+time+" new_time: "+new_time);
			length_prefix = this.individual.getLength()*0.9 + this.individual.getComfort()*0.1;
					
			context.getPec().addEvent(new EvReproduction(new_time, this.individual));
			//new child and 3 new events
			
			
			//ceil() method rounds a number UPWARDS to the nearest integer
			//System.out.println("PARENT PATH: "+ this.individual.getPath());
			Individual i = new Individual(this.individual.getGrid(), this.individual.getComfort_param(),
			  this.individual.getPath(), (int)Math.ceil(length_prefix));
			//System.out.println("NEW PATH: "+ i.getPath());
			context.getPopulation().addIndividual(i);
			context.createNewBornEvents(i);

		}
	
	}

}
