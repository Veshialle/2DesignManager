import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
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
	static String fileName;
	static String pathName = "save/";
	static File path = new File(pathName);
	/**
	 * @param Figura fig
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws TransformerException 
	 */
	public static void savePolygon(Figura fig) throws ParserConfigurationException, IOException, SAXException, TransformerException {
		// TODO Auto-generated method stub
		fileName = fig.name + ".xml";
		//File file = new File(fileName);
		File  file= new File(path, fileName);
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
		Element nome = doc.createElement("nome");
		nome.appendChild(doc.createTextNode(fig.name));
		figura.appendChild(nome);
		Element tipo = doc.createElement("tipo");
		tipo.appendChild(doc.createTextNode(fig.tipo));
		figura.appendChild(tipo);
		Element nLati = doc.createElement("nLati");
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
			// da ottimizzare
		}
		Element angle = doc.createElement("angle");
		String strAngle = String.valueOf(fig.getAngle());
		angle.appendChild(doc.createTextNode(strAngle));
		figura.appendChild(angle);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);
 	
 		//debug
 		//result = new StreamResult(System.out);
 	
 		transformer.transform(source, result);
	}
	//need to fix due to adding the typo name to class Figure
	
	public static List<Figura> loadPolygon() throws ParserConfigurationException, SAXException, IOException{
		
		 List<Figura> loadFig = new ArrayList<Figura>();
		 
		if(countFigure() == 0){
			return null;
		}
		else {
			File []totalFile = path.listFiles();
			for(File file : totalFile)
			{
				docBuilder = docFactory.newDocumentBuilder();
			
				doc = docBuilder.parse(file);
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("Figura");
				for(int i=0;i<nList.getLength();i++)
				{
					String tipo = null, nome = null;
					int nLati = 0, idFigura = 0;
					double []xPoints = new double[]{0,0,0,0};
					double []yPoints = new double[]{0,0,0,0};
					double angle =0.0;
					Node nNode = nList.item(i);
					if(nNode.getNodeType() == Node.ELEMENT_NODE)
					{
						Element figElement = (Element) nNode;
						String strIdFigura = figElement.getAttribute("idFigura");
						idFigura = Integer.parseInt(strIdFigura);
						nome =figElement.getElementsByTagName("nome").item(0).getTextContent();
						tipo =figElement.getElementsByTagName("tipo").item(0).getTextContent();
						String strNLati = figElement.getElementsByTagName("nLati").item(0).getTextContent();
						nLati = Integer.parseInt(strNLati);
						for(int j=0;j< nLati; j++){
							String xPoint = figElement.getElementsByTagName("xPoint"+j).item(0).getTextContent();
							String yPoint = figElement.getElementsByTagName("yPoint"+j).item(0).getTextContent();
							xPoints[j] = Double.parseDouble(xPoint);
							yPoints[j] = Double.parseDouble(yPoint);
						}
						String strAngle = figElement.getElementsByTagName("angle").item(0).getTextContent();
						angle = Double.parseDouble(strAngle);
					}
					Figura fig = new Figura(nome, tipo,idFigura, nLati, xPoints, yPoints, angle);
					loadFig.add(fig);
				}
			}
			return loadFig;
		}
	}
	
	public static int countFigure(){
		return path.listFiles().length;
	}
	
		/*		
	public static int getLastID(){
		 int idPolygon = 0;
		 
		if(!file.exists()){
			idPolygon = 0;
			return idPolygon;
		}
		try{
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(file);
			rootElement = doc.getDocumentElement();
			docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Figura");
			Node lastNode = nList.item(nList.getLength()-1);
			if(lastNode.getNodeType() == Node.ELEMENT_NODE){
				Element lastElement = (Element) lastNode;
				idPolygon = Integer.parseInt(lastElement.getAttribute("idFigura"));
			}
		}catch(ParserConfigurationException pce){
			pce.printStackTrace();
		} catch (SAXException sxe) {
			// TODO Auto-generated catch block
			sxe.printStackTrace();
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
		return idPolygon;
	}
		*/
	
	
}