package static_comp;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parser extends DefaultHandler, which is a class from SAX that provides functions to be implemented
 * in order to correctly parse the input file. This functions provide the Elements information
 *  
 */
public class Parser extends DefaultHandler{

	private int counter = 0;
	private String tag;
	private Map<String, Integer[]> inputs = new HashMap<String, Integer[]>();
	
	/**
	 * This function is called upon an element starting event, which provides the tag of the element 
	 * its respective attributes in a String format. This elements are then inserted in a Map object
	 * which assigns a tag string to an array of integers, providing a way of addressing each attribute using
	 * the element's tag plus a counter which is used to make sure the tag isn't repeated.
	 * @param uri
	 * @param name
	 * @param tag
	 * @param atts
	 */
	public void startElement(String uri, String name, String tag, Attributes atts){
		String key = "";
		if(this.tag == tag)
			counter++;
		else
			counter= 0;
		this.tag = tag;
		key = ""+tag+counter;
		Integer []array = new Integer[Integer.valueOf(atts.getLength())];
		for(int i = 0; i < atts.getLength();i++) {
			array[i] = Integer.valueOf(atts.getValue(i));
		}
		inputs.put(key, array);
	}
	
	/**
	 * This function is called whenever there is any character inside an element.
	 * The only element that requires this message is the zone element, on which the cost is defined
	 * in the character data space. 
	 * @param ch
	 * @param start
	 * @param length
	 */
	public void characters(char[]ch,int start,int length){
		Integer []cost = new Integer[1];
		/*verifies if the tag is the right one*/
		if(tag.equals("zone")) {
			
			String val =new String(ch, start, length);
			/*verifies if data corresponds to an integer*/
			if(val.matches(".*\\d.*")){
				//inserts cost into input map
				cost[0] = Integer.valueOf(val);
				inputs.put(""+tag+counter+"cost", cost);
			}
		} 
	}
	
	/**
	 * Gets a Point object parsed from the xml document according to its tag
	 * @param tag
	 * @return Point
	 */
	public Point getPoint(String tag) {
		return new Point(inputs.get(tag)[0], inputs.get(tag)[1]);
	}
	
	/**
	 * Gets an Edge object parsed from the xml document according to its tag.
	 * Has a try/catch block to prevent the situations where there is no 
	 * cost associated on the required edge and if that happens, returns the edge without the cost.
	 * @param tag
	 * @return Edge
	 */
	public Edge getEdge(String tag) {
		try {
			int cost =inputs.get(tag+"cost")[0];
			return new Edge(new Point(inputs.get(tag)[0], 
					inputs.get(tag)[1]), new Point(inputs.get(tag)[2], inputs.get(tag)[3]), cost);
			}catch(NullPointerException e) {
				return new Edge(new Point(inputs.get(tag)[0], 
						inputs.get(tag)[1]), new Point(inputs.get(tag)[2], inputs.get(tag)[3]));
			}
	}
	
	/**
	 * Gets an Integer object parsed from the xml document 
	 * according to its tag and index in the xml attributes
	 * 
	 * @param tag
	 * @param i
	 * @return Integer
	 */
	public Integer getInteger(String tag, int i) {
		return inputs.get(tag)[i];
	}
	
}