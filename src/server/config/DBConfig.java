package server.config;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DBConfig {
	public static String HOST;
	public static int PORT;
	public static String NAME;
	public static String USERNAME;
	public static String PASSWORD;
	
	private Document document = null;
	
	public DBConfig() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(new File("src/database.xml"));
			document.getDocumentElement().normalize();
			DBConfig.HOST = getText("host");
			DBConfig.PORT = Integer.parseInt(getText("port"));
			DBConfig.NAME = getText("name");
			DBConfig.USERNAME = getText("username");
			DBConfig.PASSWORD = getText("password");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getText(String tagName) {
		return document.getElementsByTagName(tagName).item(0).getTextContent();
	}
}
