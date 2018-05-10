package static_comp;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
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
	 * 
	 * @param uri
	 * The Namespace URI, or the empty string if the element has no Namespace URI 
	 * or if Namespace processing is not being performed.
	 * @param name
	 * The local name (without prefix), or the empty string if Namespace processing is not being performed.
	 * @param tag
	 * The qualified name (with prefix), or the empty string if qualified names are not available.
	 * @param atts
	 * The attributes attached to the element. If there are no attributes, it shall be an empty Attributes object.
	 */
	@Override
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
			if(atts.getValue(i).matches(".*\\d.*")){
				array[i] = Integer.valueOf(atts.getValue(i));
			}
			
		}
		inputs.put(key, array);
	}
	
	/**
	 * This function is called whenever there is any character inside an element.
	 * The only element that requires this message is the zone element, on which the cost is defined
	 * in the character data space. 
	 * @param ch
	 * Array with the data characters
	 * @param start
	 * starting point of the data
	 * @param length
	 * length of the data
	 */
	@Override
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
	 *	Called when there is a fatal Error when parsing the file
	 *	builds the exception message and throws a SAXException with that message
	 */
	@Override
	public void fatalError(SAXParseException e) throws SAXException{
		String error = "fatalError at "+e.getLineNumber()+"\t"+e.getMessage();
		throw new SAXException(error);
	}
	
	/**
	 *	Called when there is a Error when parsing the file
	 *builds the exception message and throws a SAXException with that message
	 */
	@Override
	public void error(SAXParseException e) throws SAXException{
		String error = "Error at "+e.getLineNumber()+"\t"+e.getMessage();
		throw new SAXException(error);
	}
	
	/**
	 *	Called when there is a warning when parsing the file
	 *	builds the exception message and throws a SAXException with that message
	 */
	@Override
	public void warning(SAXParseException e) throws SAXException{
		String error = "Warning at "+e.getLineNumber()+"\t"+e.getMessage();
		throw new SAXException(error);
	}
	
	/**
	 * Gets a Point object parsed from the xml document according to its tag
	 * @param tag
	 * tag associated with desired point
	 * @return Point
	 * wanted point
	 * @throws SAXException
	 * when trying to get a point with tag undefined or when number of obstacles 
	 * is wrongly defined in xml file
	 */
	public Point getPoint(String tag) throws SAXException {
		try {
		return new Point(inputs.get(tag)[0], inputs.get(tag)[1]);
		}catch(NullPointerException e) {
			String error = "Error getting Point with tag: "+tag+"\twrong number of obstacles specified or tag does not exist";
			throw new SAXException(error);
		}
	}
	
	/**
	 * Gets an Edge object parsed from the xml document according to its tag.
	 * Has a try/catch block to prevent the situations where there is no 
	 * cost associated on the required edge and if that happens, returns the edge without the cost.
	 * 
	 * @param tag
	 * tag associated with desired edge
	 * @return Edge
	 * desired edge
	 * @throws SAXException
	 * Throws SAXException when the wrong number of zones is specified or the edge doesn't have a cost
	 * in the respective xml file 
	 */
	public Edge getEdge(String tag) throws SAXException{
		try {
			int cost =inputs.get(tag+"cost")[0];
		return new Edge(new Point(inputs.get(tag)[0], 
				inputs.get(tag)[1]), new Point(inputs.get(tag)[2], inputs.get(tag)[3]), cost);
		}catch(NullPointerException e){
			String error = "Error getting Edge with tag: "+tag+"\twrong number of zones specified or cost undefined or tag does not exist";
			throw new SAXException(error);
		}
	}
	
	/**
	 * Gets an Integer object parsed from the xml document 
	 * according to its tag and index in the xml attributes
	 * 
	 * @param tag
	 * tag associated with int
	 * @param i
	 * index of the desired value
	 * @return Integer
	 * desired integer
	 * @throws SAXException
	 * when trying to get some integer with undefined value in xml file or specified tag is inexistent
	 */
	public Integer getInteger(String tag, int i) throws SAXException{
		try {
		Integer val = inputs.get(tag)[i];
		if(val != null) {
			return val;
		}else{
			String error = "Error getting Point with tag: "+tag+"\tvalue is not an int";
			throw new SAXException(error);
		}
		}catch(NullPointerException e) {
			String error = "Error getting Point with tag: "+tag+"\tdoes not exist";
			throw new SAXException(error);
		}
		
	}
	
}