import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 */

/**
 * @author veshialle
 *
 */
public class XMLManager {
	static DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();;
	static DocumentBuilder docBuilder;
	static Document doc;
	static Element rootElement;
	static String fileName = "polygonSaved.xml";
	static File file = new File(fileName);
	/**
	 * @param Figura fig
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws TransformerException 
	 */
	public static void savePolygon(Figura fig) throws ParserConfigurationException, IOException, SAXException, TransformerException {
		// TODO Auto-generated method stub
		
		docBuilder = docFactory.newDocumentBuilder();
		doc = null;
		rootElement = null;
		
		if(!file.exists()){
			file.createNewFile();
			doc = docBuilder.newDocument();
			rootElement = doc.createElement("Polygon");
			doc.appendChild(rootElement);
		}else{
			doc = docBuilder.parse(file);
			rootElement = doc.getDocumentElement();
		}
		
		Element figura = doc.createElement("Figura");
		figura.setAttribute("idFigura", String.valueOf(fig.getId()));
		rootElement.appendChild(figura);
		Element nLati = doc.createElement("nlati");
		String strNLati = String.valueOf(fig.getNLati());
		nLati.appendChild(doc.createTextNode(strNLati));
		figura.appendChild(nLati);
		for(int i=0; i<fig.getNLati();i++){
			Element xPoint = doc.createElement("xPoint"+  String.valueOf(i));
			String strxPoint = String.valueOf(fig.getxPoint(i));
			xPoint.appendChild(doc.createTextNode(strxPoint));
			figura.appendChild(xPoint);
			Element yPoint = doc.createElement("yPoint"+ String.valueOf(i));
			String stryPoint = String.valueOf(fig.getyPoint(i));
			yPoint.appendChild(doc.createTextNode(stryPoint));
			figura.appendChild(yPoint);        		 
		}
		Element angle = doc.createElement("angle");
		String strAngle = String.valueOf(fig.getAngle());
		System.out.println(strAngle);
		angle.appendChild(doc.createTextNode(strAngle));
		figura.appendChild(angle);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);
 	
 		//debug
 		//result = new StreamResult(System.out);
 	
 		transformer.transform(source, result);
 		System.out.println("File saved!"); 
	}
	/*
	public <list>Figura loadPolygon() throws ParserConfigurationException, SAXException, IOException{
        docBuilder = docFactory.newDocumentBuilder();
        doc = docBuilder.parse(file);
        doc.getDocumentElement().normalize();
        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
        NodeList nList = doc.getElementsByTagName("student");
        System.out.println("----------------------------");
        for (int temp = 0; temp < nList.getLength(); temp++) {
           Node nNode = nList.item(temp);
           System.out.println("\nCurrent Element :" 
              + nNode.getNodeName());
           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
              Element eElement = (Element) nNode;
              System.out.println("Student roll no : " 
                 + eElement.getAttribute("rollno"));
              System.out.println("First Name : " 
                 + eElement
                 .getElementsByTagName("firstname")
                 .item(0)
                 .getTextContent());
              System.out.println("Last Name : " 
              + eElement
                 .getElementsByTagName("lastname")
                 .item(0)
                 .getTextContent());
              System.out.println("Nick Name : " 
              + eElement
                 .getElementsByTagName("nickname")
                 .item(0)
                 .getTextContent());
              System.out.println("Marks : " 
              + eElement
                 .getElementsByTagName("marks")
                 .item(0)
                 .getTextContent());
           }
        }
		return null;
	}
	*/
	public static int getLastID() throws ParserConfigurationException, SAXException, IOException{
		int idPolygon = 0;
		if(!file.exists()){
			return idPolygon;
		}
		docBuilder = docFactory.newDocumentBuilder();
		doc = docBuilder.parse(file);
		rootElement = doc.getDocumentElement();
		return idPolygon;
	}

}