package static_comp;

/**
 * Point is composed of an x and y and it is used to represent a position of the grid
 */

public class Point {
	
	/* Fields */
	private int x;
	private int y;
	
	/**
	 * Constructs class and initializes its attributes
	 * @param x
	 * x coordinate of this point
	 * @param y
	 * y coordinate of this point
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/* Methods */
	/**
	 * returns the field X
	 * @return int
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * returns the field Y
	 * @return int
	 */
	public int getY() {
		return y;
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

	/**
	 * textual description of this class
	 */
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
	
}
