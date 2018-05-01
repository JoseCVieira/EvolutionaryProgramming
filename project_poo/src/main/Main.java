package main;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

import dynamic_comp.Simulation;
import static_comp.Point;

public class Main {

	public static void main(String[] args){
		Simulation simulation = null;
		SAXParserFactory fact = SAXParserFactory.newInstance();
		
		try {
			SAXParser saxParser = fact.newSAXParser();
			DefaultHandler handler = new Parser(args[0]);
			saxParser.parse(new File(args[0]), handler); 
			Parser p = ((Parser) handler);
			simulation = new Simulation(p);
			simulation.startSimulation();
		} catch (ParserConfigurationException e) {
			
			System.out.println(e.getMessage());
		} catch (IOException e) {
			
			System.out.println(e.getMessage());
		} catch (org.xml.sax.SAXException e) {
			
			System.out.println(e.getMessage());
		}
		
	}
	
}
