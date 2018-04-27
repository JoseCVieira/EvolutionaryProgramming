package dynamic_comp;

public abstract class Event {
	
	protected double time;
	protected Individual individual;
	
	Event(double time, Individual individual){
		this.time = time;
		this.individual = individual;
	}

	abstract char action();
}
