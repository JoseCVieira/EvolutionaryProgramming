package dynamic_comp;

public abstract class Event {
	
	private double time;
	protected Individual individual;
	
	Event(double time, Individual individual){
		this.time = time;
		this.individual = individual;
	}

	abstract char action();

	public double getTime() {
		return time;
	}
	
}
