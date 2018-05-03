package dynamic_comp;

public class EvObservation extends Event{
	public static final int N_OBSERVATIONS = 20;
	private int number;
	EvObservation(double time){
		super(time, null);
	}

	@Override
	void action(Simulation context) {
		number = (int)(time*N_OBSERVATIONS/context.getFinal_time());
		
		System.out.println("Observation "+number+":");
		System.out.println("\t\t Present Instant:\t\t\t"+time);
		System.out.println("\t\t Number Of Realized Events:\t\t"+context.getEvent_counter());
		System.out.println("\t\t Population Size:\t\t\t"+context.getPopulation().getIndividuals().size());
		System.out.print("\t\t Final point has been hit:\t\t");
		if(context.isFinal_hit()) {
			System.out.println("yes");
		}else {
			System.out.println("no");
		}
	
		System.out.println("\t\t Path of the best fit individual:\t"+context.getBest_individual().getPath());
		System.out.println("\t\t Cost/Comfort:\t\t\t"+context.getBest_individual().getPath().getCost()+"/"+context.getBest_individual().getComfort());
	}
	
}
