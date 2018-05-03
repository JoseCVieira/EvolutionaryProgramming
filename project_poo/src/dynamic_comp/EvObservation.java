package dynamic_comp;

public class EvObservation extends Event{
	public static final int N_OBSERVATIONS = 20;
	private int number;
	EvObservation(double time){
		super(time, null);
	}

	@Override
	void action(Simulation context) {
		number = (int)(time*N_OBSERVATIONS/context.getFinalTime());
		
		System.out.println("Observation "+number);
		System.out.println("\t\t\t Present Instant:\t\t\t"+time);
		System.out.println("\t\t\t Number Of Realized Events:\t\t\t"+context.getEventCounter());
		System.out.println("\t\t\t Population Size:\t\t\t"+context.getPopulation().getIndividuals().size());
		
		if(context.getFinalHit()) {
			System.out.println("\t\t\t Final point has been hit:\t\t\tyes");
		}else {
			System.out.println("\t\t\t Final point has been hit:\t\t\tno");
		}
		
		System.out.println("\t\t\t Path of the best fit individual:\t\t\t"+context.getBestIndividual().getPath());
		System.out.println("\t\t\t Cost/Comfort:\t\t\t"+context.getBestIndividual().getPath().getCost()+"/"+context.getBestIndividual().getComfort());
	}
	
}
