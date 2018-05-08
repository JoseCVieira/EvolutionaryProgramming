package dynamic_comp;

/**
 * 
 * An EvObservation is a subclass of Event that has no individual associated 
 * and is responsible for printing an observation. The time is immutable.
 * 
 *
 */

public class EvObservation extends Event{
	
	/**
	 * Constructs and initializes an EvObservation with a specific time and individual
	 * 
	 * @param time
	 * time at which the event occurs
	 * @param individual
	 * the individual that it is assigned to
	 * 
	 */
	EvObservation(double time){
		super(time, null);
	}

	/**
	 * Prints the value of the observation number, present instant, the number of events realized,
	 * the population size and if the final point has been hit or not. All of these properties are
	 * related to the Simulation object, context.
	 * 
	 * @param context
	 * 
	 */
	@Override
	protected void action(Simulation context) {
		int number = (int)(time*Simulation.N_OBSERVATIONS/context.getFinal_time());
		
		System.out.println("Observation "+number+":");
		System.out.println("\t\t Present Instant:\t\t\t"+time);
		System.out.println("\t\t Number Of Realized Events:\t\t"+context.getEvent_counter());
		System.out.println("\t\t Population Size:\t\t\t"+context.getPopulation().getIndividuals().size());
		System.out.print("\t\t Final point has been hit:\t\t");
		
		if(context.isFinal_hit())
			System.out.println("yes");
		else
			System.out.println("no");
	
		System.out.println("\t\t Path of the best fit individual:\t"+context.getBest_individual().getPath());
		System.out.println("\t\t Cost/Comfort:\t\t\t\t"+ context.getBest_individual().getPath().getCost()+"/"+ String.format("%.4f",context.getBest_individual().getComfort()));
	}
	
}
