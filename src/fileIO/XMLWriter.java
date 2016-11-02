package fileIO;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gui.BoardStateDataSource;
import gui.TurtleStateDataSource;
import javafx.scene.paint.Color;
import model.PathLine;

public class XMLWriter {
	/**adjusted from internet resource
	 * @author Lucia Martos
	 */
	private static final String MY_PATH = "data/examples/";

	public XMLWriter(String fileName, BoardStateDataSource boardStateDataSource, TurtleStateDataSource turtleStateDataSource) {

		/*
		public double getXCoordinate();
		public double getYCoordinate();
		public double getAngle();
		public boolean getTurtleIsShowing();
		public boolean getTurtleIsDrawing();
		public List<PathLine> getLineCoordinates();
		public Map<String, String> getUserDefinedVariables();
		public Color getPenColor();
		public Color getBackgroundColor();
		public double getPenThickness();
		public String getImage();
		*/
	  try {		  
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("scene");
		doc.appendChild(rootElement);

		// workspace elements
		Element workspace = doc.createElement("workspace");
		rootElement.appendChild(workspace);

//		Color colorBackground = boardStateDataSource.getBackgroundColorIndex(); 
//		String colorBackgroundString = String.format( "#%02X%02X%02X",
//	            (int)( colorBackground.getRed() * 255 ),
//	            (int)( colorBackground.getGreen() * 255 ),
//	            (int)( colorBackground.getBlue() * 255 ) );
		Element backgroundcolor = doc.createElement("backgroundcolor");
		backgroundcolor.appendChild(doc.createTextNode(Integer.toString(boardStateDataSource.getBackgroundColorIndex())));
		workspace.appendChild(backgroundcolor);

		Element language = doc.createElement("language");
		language.appendChild(doc.createTextNode("NEED TO DO THIS"));
		workspace.appendChild(language);

		Element turtlecount = doc.createElement("turtlecount");
		turtlecount.appendChild(doc.createTextNode("NEED TO IMPLEMENT"));
		workspace.appendChild(turtlecount);
		
		// turtle elements
		Element turtle = doc.createElement("turtle");
		rootElement.appendChild(turtle);
	
		// shorten way
		// staff.setAttribute("id", "1");
		Iterator<Integer> turtleIds = turtleStateDataSource.getTurtleIDs();
		int i=1;
		while(turtleIds.hasNext()){
			
			Integer turtleID = turtleIds.next();
			
			// set attribute to staff element
			Attr attr = doc.createAttribute("id");
			attr.setValue(Integer.toString(i));
			turtle.setAttributeNode(attr);
			
			Element imageURL = doc.createElement("imageURL");
			imageURL.appendChild(doc.createTextNode(Integer.toString(turtleStateDataSource.getShape(turtleID))));
			turtle.appendChild(imageURL);
			
			Element heading = doc.createElement("heading");
			heading.appendChild(doc.createTextNode(Double.toString(turtleStateDataSource.getAngle(turtleID))));
			turtle.appendChild(heading);
			
			Element pendown = doc.createElement("pendown");
			pendown.appendChild(doc.createTextNode(turtleStateDataSource.getTurtleIsDrawing(turtleID)?"1":"0"));
			turtle.appendChild(pendown);
			
			Element turtleshowing = doc.createElement("turtleshowing");
			turtleshowing.appendChild(doc.createTextNode(turtleStateDataSource.getTurtleIsDrawing(turtleID)?"1":"0"));
			turtle.appendChild(turtleshowing);
		}
		

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(MY_PATH + fileName + ".xml"));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");

	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
	}
}