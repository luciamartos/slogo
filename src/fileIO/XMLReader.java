package fileIO;
import model.BoardStateController;
import model.TurtleStatesController;

/**
 * 
 * adjusted from online resource: https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * 
 */
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import general.MainController;
import gui.TabViewController;

import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLReader {
	private int backgroundColor;
	private String language;
	private String turtleCount;
	NodeList turtleNodes;

	public XMLReader(String file) {

		try {
			
			File fXmlFile = new File(file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList workspaceList = doc.getElementsByTagName("workspace");
			Node workspaceNode = workspaceList.item(0);
			Element workspaceElement = (Element) workspaceNode;
//			backgroundColor = Integer
//					.parseInt(workspaceElement.getElementsByTagName("backgroundcolor").item(0).getTextContent());
			language = workspaceElement.getElementsByTagName("language").item(0).getTextContent();
			turtleCount = workspaceElement.getElementsByTagName("turtlecount").item(0).getTextContent();

			turtleNodes = doc.getElementsByTagName("turtle");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public String getLanguage() {
		return language;
	}

	public String getTurtleCount() {
		return turtleCount;
	}
	
	public NodeList getTurtleNodes(){
		return turtleNodes;
	}
	
	// public int getTurtleID(){
	// return turtleID;
	// }
	// public int getImageIndex() {
	// return imageIndex;
	// }
	//
	// public int getXLoc(){
	// return xLoc;
	// }
	// public int getYLoc(){
	// return yLoc;
	// }
	//
	// public int getPenColor() {
	// return penColor;
	// }
	//
	// public String getPenDown() {
	// return penDown;
	// }
	//
	// public int getLineStyle() {
	// return lineStyle;
	// }
	// public int getPenThickness() {
	// return penThickness;
	// }
}
