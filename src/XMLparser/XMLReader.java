package XMLparser;

/**
 * 
 * adjusted from online resource: https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * 
 */
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.HashMap;

public class XMLReader {
	private  String imageURL;
	private int backgroundColor;
	private String language;
	private String turtleCount;
	private int penColor;
	private String penDown;
	private int lineStyle;
	private int penThickness;
	private int xLoc;
	private int yLoc;
	private int turtleID;

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

			backgroundColor= Integer.parseInt(workspaceElement.getElementsByTagName("backgroundcolor").item(0).getTextContent());
			language = workspaceElement.getElementsByTagName("language").item(0).getTextContent();
			turtleCount = workspaceElement.getElementsByTagName("turtlecount").item(0).getTextContent();
			
			NodeList nList = doc.getElementsByTagName("turtle");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					turtleID = Integer.parseInt(eElement.getElementsByTagName("turtleID").item(0).getTextContent());
					imageURL = eElement.getElementsByTagName("imageURL").item(0).getTextContent();
				//	penColor = Integer.parseInt(eElement.getElementsByTagName("pencolor").item(0).getTextContent());
				//	penThickness = Integer.parseInt(eElement.getElementsByTagName("penthickness").item(0).getTextContent());
					penDown = eElement.getElementsByTagName("pendown").item(0).getTextContent();
				//	lineStyle = Integer.parseInt(eElement.getElementsByTagName("linestyle").item(0).getTextContent());
					xLoc = Integer.parseInt(eElement.getElementsByTagName("xLoc").item(0).getTextContent());
					yLoc = Integer.parseInt(eElement.getElementsByTagName("yLoc").item(0).getTextContent());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getTurtleID(){
		return turtleID;
	}
	public String getImageURL(Integer turtleID) {
		return imageURL;
	}
	
	public int getXLoc(Integer turtleID){
		return xLoc;
	}
	public int getYLoc(Integer turtleID){
		return yLoc;
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

	public int getPenColor() {
		return penColor;
	}

	public String getPenDown(Integer turtleID) {
		return penDown;
	}

	public int getLineStyle() {
		return lineStyle;
	}
	public int getPenThickness() {
		return penThickness;
	}
}
