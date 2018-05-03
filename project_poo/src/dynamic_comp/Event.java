package dynamic_comp;

public abstract class Event {
	
	protected double time;
	protected Individual individual;
	
	Event(double time, Individual individual){
		this.time = time;
		this.individual = individual;
	}

	protected abstract void action(Simulation context);

	protected final double getTime() {
		return time;
	}
	
}
