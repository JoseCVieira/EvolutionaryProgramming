package static_comp;

public class Point {
	
	/* Fields */
	private int x;
	private int y;
	private boolean obst;
	
	/* Constructor */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		obst = false; //default
	}

	/* Methods */
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isObst() {
		return obst;
	}

	public void setObst(boolean obst) {
		this.obst = obst;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
	
}
