package XMLparser;

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
		Element rootElement = doc.createElement("workspace");
		doc.appendChild(rootElement);

		// staff elements
		Element staff = doc.createElement("type");
		rootElement.appendChild(staff);

		Element backgroundcolor = doc.createElement("backgroundcolor");
		Color colorBackground = boardStateDataSource.getBackgroundColor(); 
		String colorBackgroundString = String.format( "#%02X%02X%02X",
	            (int)( colorBackground.getRed() * 255 ),
	            (int)( colorBackground.getGreen() * 255 ),
	            (int)( colorBackground.getBlue() * 255 ) );
		backgroundcolor.appendChild(doc.createTextNode(colorBackgroundString));
		staff.appendChild(backgroundcolor);

		Element language = doc.createElement("language");
		language.appendChild(doc.createTextNode("NEED TO DO THIS"));
		staff.appendChild(language);

		Element turtlecount = doc.createElement("turtlecount");
		turtlecount.appendChild(doc.createTextNode("NEED TO IMPLEMENT"));
		staff.appendChild(turtlecount);
		
		
		// set attribute to staff element
		Attr attr = doc.createAttribute("id");
		attr.setValue("1");
		staff.setAttributeNode(attr);

		// shorten way
		// staff.setAttribute("id", "1");
		Iterator<Integer> turtleIds = turtleStateDataSource.getTurtleIDs();
		while(turtleIds.hasNext()){
			
			
			
			
		}
		Element imageURL = doc.createElement("imageURL");
		imageURL.appendChild(doc.createTextNode(turtleStateDataSource.getShape(turtleID)));
		staff.appendChild(imageURL);

		
		
		Element pencolor = doc.createElement("pencolor");
		Color colorPen = boardStateDataSource.getBackgroundColor(); 
		String penColor = String.format( "#%02X%02X%02X",
	            (int)( colorPen.getRed() * 255 ),
	            (int)( colorPen.getGreen() * 255 ),
	            (int)( colorPen.getBlue() * 255 ) );
		pencolor.appendChild(doc.createTextNode(penColor));
		staff.appendChild(pencolor);
		
		Element penthickness = doc.createElement("penthickness");
		penthickness.appendChild(doc.createTextNode(turtleStateDataSource.get)));
		staff.appendChild(penthickness);
		
		Element pendown = doc.createElement("pendown");
		pendown.appendChild(doc.createTextNode("100000"));
		staff.appendChild(pendown);
		
		Element linestyle = doc.createElement("linestyle");
		linestyle.appendChild(doc.createTextNode("100000"));
		staff.appendChild(linestyle);

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