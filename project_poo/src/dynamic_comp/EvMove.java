package dynamic_comp;

public class EvMove extends Event {

	EvMove(double time, Individual individual) {
		super(time, individual);
	}

	@Override
	void action(Simulation context) {
		double new_time;
		
		new_time = time + Simulation.expRandom(context.getMove_param()*(1-Math.log(individual.getComfort())));			
		
		context.getPec().addEvent(new EvMove(new_time, individual));
		individual.move(context.getNewIndividualPosition(individual));
		
		if(!context.isFinal_hit()) {
			if(individual.getPosition().equals(context.getGrid().getFinal_pos())) {
				context.setFinal_hit(true);
				
				Individual i = new Individual(individual.getGrid(), individual.getPath(),
						individual.getPath().getPathLength(), individual.getComfort_param());
				context.setBest_individual(i);
				
			}else if(individual.getComfort() > context.getBest_individual().getComfort()) {
				
				Individual i = new Individual(individual.getGrid(), individual.getPath(),
						individual.getPath().getPathLength(), individual.getComfort_param());
				context.setBest_individual(i);
			}
		}
		
		else if(context.isFinal_hit()) {
			if(individual.getPosition().equals(context.getGrid().getFinal_pos())) {				
				if(individual.getPath().getCost() < context.getBest_individual().getPath().getCost()) {
					
					Individual i = new Individual(individual.getGrid(), individual.getPath(),
							individual.getPath().getPathLength(), individual.getComfort_param());
					context.setBest_individual(i);
				}
			}
		}
			
	}

}
