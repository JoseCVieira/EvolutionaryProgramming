package simulation_comp;

public class Individual {

	/* Fields */
	private int length;  // TODO length = path.getLength();
	private int dist;	 // TODO dist = calculateDist();
	private int length_prefix;
	private Point position;
	Path path;
	
	/* Constructors */
	public Individual(Point position) {
		this.setPosition(position);
		path = new Path();
		dist = calculateDist();
		length = 0; // default ja e 0.. fica na mesma?
	}
	
	public Individual(Point position, Path path, int length_prefix) {
		this.setPosition(position);
		this.path = path;
		this.length_prefix = length_prefix;
		this.position = position;
		
		//TODO path = .... remover parte do path (length_prefix)
		//TODO length = ....
		dist = calculateDist();
	}
	
	/* Methods */
	int calculateDist(){
		//TODO
		return 0;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
}
