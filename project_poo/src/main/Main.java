package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.*; // SAX and DOM parsers
import org.xml.sax.*; // Generic API for SAX
import org.xml.sax.helpers.*; // Handlers 

import static_comp.Edge;
import static_comp.Grid;
import static_comp.Point;

public class Main extends DefaultHandler{
	private static String fileName ="";
	
	private int final_instant;
	private int initial_pop;
	private int max_pop;
	private int comfort_sensitivity;
	private int death_param;
	private int move_param;
	private int reprod_param;
	private int cols = 0;
	private int rows = 0;
	private int n_obst = 0;
	private Point initialPoint;
	private Point finalPoint;
	private ArrayList<Edge> sZones = new ArrayList<Edge>();
	private ArrayList<Point> obsts = new ArrayList<Point>();
	private Grid grid = null;
	private Point p1;
	private Point p2;
	private String tagGlobal;
	public static void main(String[] args) {
		fileName =args[0];
		SAXParserFactory fact = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = fact.newSAXParser();
			DefaultHandler handler = new Main();
			 saxParser.parse(new File(fileName), handler); 
	
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void startDocument(){
		 System.out.println("Beginning the parsing of"+ fileName);
	}
	public void endDocument(){
		grid = new Grid(n_obst, rows, cols, obsts, sZones);
	}
	public void startElement(String uri, String name,
		 String tag, Attributes atts){
		tagGlobal = tag;
		for(int i =0; i < atts.getLength(); i++) {
			
			if(tag.equals("simulation")) {
				if(atts.getLocalName(i).equals("finalinst")) {
					final_instant = Integer.valueOf(atts.getValue(i));
				}else if(atts.getLocalName(i).equals("initpop")) {
					initial_pop = Integer.valueOf(atts.getValue(i));
				}else if(atts.getLocalName(i).equals("maxpop")) {
					max_pop = Integer.valueOf(atts.getValue(i));
				}else if(atts.getLocalName(i).equals("comfortsens")) {
					comfort_sensitivity = Integer.valueOf(atts.getValue(i));
				}
			}else if(tag.equals("grid")) {
				if(atts.getLocalName(i).equals("colsnb")) {
					cols = Integer.valueOf(atts.getValue(i));
				}else if(atts.getLocalName(i).equals("rowsnb")) {
					rows = Integer.valueOf(atts.getValue(i));
				}
			}else if(tag.equals("initialpoint")) {
				initialPoint = new Point(Integer.valueOf(atts.getValue(0)), Integer.valueOf(atts.getValue(1)));
			}else if(tag.equals("finalpoint")) {
				initialPoint = new Point(Integer.valueOf(atts.getValue(0)), Integer.valueOf(atts.getValue(1)));
			}else if(tag.equals("specialcostzones")) {
				
			}else if(tag.equals("zone")) {
				 p1 = new Point(Integer.valueOf(atts.getValue(0)), Integer.valueOf(atts.getValue(1)));
				 p2 = new Point(Integer.valueOf(atts.getValue(2)), Integer.valueOf(atts.getValue(3)));
				
			}else if(tag.equals("obstacles")) {
				n_obst = Integer.valueOf(atts.getValue(0));
			}else if(tag.equals("obstacle")) {
				obsts.add(new Point(Integer.valueOf(atts.getValue(0)),Integer.valueOf(atts.getValue(1))));
			}else if(tag.equals("events")) {
				
				
			}else if(tag.equals("death")) {
				
				death_param = Integer.valueOf(atts.getValue(0));
			}else if(tag.equals("reproduction")) {
				reprod_param = Integer.valueOf(atts.getValue(0));
			}else if(tag.equals("move")) {
				move_param = Integer.valueOf(atts.getValue(0));
			}
		}
	}
	public void characters(char[]ch,int start,int length){
		if(tagGlobal.equals("zone")) {
			String val =new String(ch, start, length);
			if(!val.equals("\n"))
	
		sZones.add(new Edge(p1, p2, Integer.valueOf(val)));
		}
		} 
}
