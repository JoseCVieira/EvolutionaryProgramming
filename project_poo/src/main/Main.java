package main;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.helpers.DefaultHandler;

import dynamic_comp.Simulation;
import static_comp.Parser;

public class Main {
	/**
	 * Main expects only one argument which is the file to parse
	 * @param args
	 */
	public static void main(String[] args){
		Simulation simulation;
		Parser parser;
		SAXParserFactory fact = SAXParserFactory.newInstance();
		
		try {
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
		} catch (org.xml.sax.SAXException e) {
			System.out.println(e.getMessage());
		}
	}
	
}