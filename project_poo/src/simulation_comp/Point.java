package simulation_comp;

public class Point {
	
	/* Fields */
	private int x;
	private int y;
	private boolean obst;
	
	/* Constructor */
	public Point(int x, int y) {
		this.setX(x);
		this.setY(y);
		obst = false; //default
	}

	/* Methods */
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isObst() {
		return obst;
	}

	public void setObst(boolean obst) {
		this.obst = obst;
	}

	@Override
	public String toString() {
		return "Point = ("+x+","+y+")\n";
	}
	
}
