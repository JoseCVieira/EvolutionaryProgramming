package main;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import dynamic_comp.Simulation;
import static_comp.Parser;

public class Main {
	/**
	 * Main expects only one argument which is the file to parse
	 * It then parses the file while validating, if there is something not in conformity 
	 * with DTD file, it will catch a SAXException and won't start the simulation.
	 * @param args
	 * Input arguments
	 */
	public static void main(String[] args){
		Simulation simulation;
		Parser parser;
		SAXParserFactory fact = SAXParserFactory.newInstance();
		
		try {
			fact.setValidating(true);
			fact.setNamespaceAware(true);
			
			SAXParser saxParser = fact.newSAXParser();
			DefaultHandler handler = new Parser();
			
			saxParser.parse(new File(args[0]), handler);
			parser = ((Parser) handler);
			
			simulation = new Simulation(parser);
			simulation.startSimulation();
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (SAXException e) {
			System.out.print("Could not start simulation due to:\n");
			System.out.print("\t"+e.getMessage());
		}
	
	}
	
}