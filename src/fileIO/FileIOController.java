package fileIO;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import general.MainController;
import gui.TabViewController;
import model.BoardStateController;
import model.TurtleStatesController;

public class FileIOController {
	
	public static void loadBoardWithFile(String xmlFile, TabViewController tabViewController){
		
		XMLReader fileReader = new XMLReader(xmlFile);
		BoardStateController boardController  = new BoardStateController();
		TurtleStatesController turtleController = boardController.getTurtleStatesController();
		NodeList turtleNodes = fileReader.getTurtleNodes();
		for (int id = 0; id < turtleNodes.getLength(); id++) {
			Node nNode = turtleNodes.item(id);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				TurtleData myTurtle = new TurtleData();
				Element eElement = (Element) nNode;
				int imageIndex = Integer.parseInt(eElement.getElementsByTagName("imageIndex").item(0).getTextContent());
				int penThickness = Integer
						.parseInt(eElement.getElementsByTagName("penthickness").item(0).getTextContent());
				String penDown = eElement.getElementsByTagName("pendown").item(0).getTextContent();
				int lineStyle = Integer.parseInt(eElement.getElementsByTagName("linestyle").item(0).getTextContent());
				double xLoc = Double.parseDouble(eElement.getElementsByTagName("xLoc").item(0).getTextContent());
				double yLoc = Double.parseDouble(eElement.getElementsByTagName("yLoc").item(0).getTextContent());
				
				myTurtle.setShapeIndex(imageIndex);
				myTurtle.setDrawing(penDown.equals("1") ? true : false);
				myTurtle.setPenSize(penThickness);
				myTurtle.setPenType(lineStyle);
				myTurtle.setX(xLoc);
				myTurtle.setY(yLoc);	
				turtleController.addNewTurtle(myTurtle, id);
			}
			MainController.initializeControllerConnections(boardController, turtleController, tabViewController);
		}
	}
	
}
