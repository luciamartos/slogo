package xmlparsing;

import java.io.File;
import com.sun.org.apache.xerces.internal.parsers.XMLParser;

public class ParserMain {
		    private static final String XML_FILES_LOCATION = "data/xml/";
		    private static final String XML_SUFFIX = ".xml";
		    public static void main (String[] args) {
		        XMLParser parser = new XMLParser();
		        PersonXMLFactory factory = new ProfessorXMLFactory();
		        File folder = new File(XML_FILES_LOCATION);
		        for (File f : folder.listFiles()) {
		            if (f.isFile() && f.getName().endsWith(XML_SUFFIX)) {
		                try {
		                    Person p = factory.getPerson(parser.getRootElement(f.getAbsolutePath()));
		                    System.out.println(p);
		                }
		                catch (XMLFactoryException e) {
		                    System.err.println("Reading file " + f.getPath());
		                    e.printStackTrace();
		                }
		            }
		        }
		    }
		}}

}
