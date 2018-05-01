package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import static_comp.*;
import dynamic_comp.*;
public class Parser extends DefaultHandler{

	
	private int counter = 0;
	private static String tag;
	
	private static String fileName ="";
	private Map<String, Integer[]> inputs = new HashMap<String, Integer[]>();
	public Parser(String filename) {
		this.fileName = filename;
	}
	
	public void startDocument(){
		 System.out.println("Beginning the parsing of"+ fileName);
	}
	public void endDocument(){
		System.out.println("Ending the parsing of"+ fileName);
	}
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
	public void characters(char[]ch,int start,int length){
		Integer []cost = new Integer[1];
		if(tag.equals("zone")) {
			String val =new String(ch, start, length);
			if(val.matches(".*\\d.*")){
				cost[0] = Integer.valueOf(val);
				inputs.put(""+tag+counter+"cost", cost);
			}
				
			
			
		} 
	}
	public Object getValue(String tag, Object o) {
		if(o instanceof Point) {
			return new Point(inputs.get(tag)[0], inputs.get(tag)[1]);
		}else if(o instanceof Integer) {
			return inputs.get(tag)[((Integer) o)];
		}else if(o instanceof Edge) {
			try {
			int cost =inputs.get(tag+"cost")[0];
			return new Edge(new Point(inputs.get(tag)[0], 
					inputs.get(tag)[1]), new Point(inputs.get(tag)[2], inputs.get(tag)[3]), cost);
			}catch(NullPointerException e) {
				return new Edge(new Point(inputs.get(tag)[0], 
						inputs.get(tag)[1]), new Point(inputs.get(tag)[2], inputs.get(tag)[3]));
			}
			
		}
		return o;
	}



	



}
