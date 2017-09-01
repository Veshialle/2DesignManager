import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JOptionPane;
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
	 * @param Figura
	 *            fig
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws TransformerException
	 */
	public static void initializationDir() {
		if (!path.exists()) {
			path.mkdir();
		}
	}

	public static void savePolygon(Figura fig)
			throws ParserConfigurationException, IOException, SAXException, TransformerException {
		initializationDir();
		// TODO Auto-generated method stub
		fileName = fig.getName() + ".xml";
		// File file = new File(fileName);
		File file = new File(path, fileName);
		docBuilder = docFactory.newDocumentBuilder();
		doc = null;
		rootElement = null;

		if (!file.exists()) {
			file.createNewFile();
			doc = docBuilder.newDocument();
			rootElement = doc.createElement("Figura");
			doc.appendChild(rootElement);
			fig.setVersione((float) 1);
			rootElement.setAttribute("idFigura", String.valueOf(fig.getId()));
			Element nome = doc.createElement("nome");
			nome.appendChild(doc.createTextNode(fig.getName()));
			rootElement.appendChild(nome);
			Element tipo = doc.createElement("tipo");
			tipo.appendChild(doc.createTextNode(fig.getTipo()));
			rootElement.appendChild(tipo);
			Element nLati = doc.createElement("nLati");
			String strNLati = String.valueOf(fig.getNLati());
			nLati.appendChild(doc.createTextNode(strNLati));
			rootElement.appendChild(nLati);
		} else {
			doc = docBuilder.parse(file);
			rootElement = doc.getDocumentElement();
			Float newVersione = (float) (fig.getVersione() + 0.1);
			// System.out.println("La nuova versione salvata è la: " +newVersione);
			fig.setVersione(newVersione);
		}
		Element versione = doc.createElement("versione");
		String strVersione = String.valueOf(fig.getVersione());
		versione.setAttribute("versione", strVersione);
		rootElement.appendChild(versione);
		for (int i = 0; i < fig.getNLati(); i++) {
			Element xPoint = doc.createElement("xPoint" + String.valueOf(i));
			String strxPoint = String.valueOf(fig.getxPoint(i));
			xPoint.appendChild(doc.createTextNode(strxPoint));
			versione.appendChild(xPoint);
			Element yPoint = doc.createElement("yPoint" + String.valueOf(i));
			String stryPoint = String.valueOf(fig.getyPoint(i));
			yPoint.appendChild(doc.createTextNode(stryPoint));
			versione.appendChild(yPoint);
			// da ottimizzare
		}
		Element angle = doc.createElement("angle");
		String strAngle;
		if (fig.getClass() != Circle.class)
			strAngle = String.valueOf(fig.getAngle());
		else
			strAngle = "0.0";
		angle.appendChild(doc.createTextNode(strAngle));
		versione.appendChild(angle);
		Element color = doc.createElement("color");
		String strColor = String.valueOf(fig.getColor().getRGB());
		color.appendChild(doc.createTextNode(strColor));
		versione.appendChild(color);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(file);

		// debug
		// result = new StreamResult(System.out);

		transformer.transform(source, result);
	}
	// need to fix due to adding the typo name to class Figure

	public static List<Figura> loadPolygon() throws ParserConfigurationException, SAXException, IOException {
		initializationDir();
		List<Figura> loadFig = new ArrayList<Figura>();
		if (countFigure() == 0) {
			return null;
		} else {
			File[] totalFile = path.listFiles();
			Object[] chooseFile = new Object[totalFile.length];
			int k = 0;
			for (File file : totalFile) {
				String fileComplete = file.getName();
				chooseFile[k] = fileComplete.replaceAll(".xml", "");
				k++;
			}
			Component frame = null;
			Icon icon = null;
			String strfileChoosed = (String) JOptionPane.showInputDialog(frame, "Select figure:\n", "Load",
					JOptionPane.PLAIN_MESSAGE, icon, chooseFile, chooseFile[0]);
			File fileChoosed = new File(path, strfileChoosed + ".xml");
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(fileChoosed);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Figura");
			if (nList.getLength() > 0) {
				String tipo = null, nome = null;
				int nLati = 0, idFigura = 0;
				Node nNode = nList.item(0);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Float versione = null;
					Element figElement = (Element) nNode;
					String strIdFigura = figElement.getAttribute("idFigura");
					// System.out.println("L'id della figura è:" +strIdFigura);
					idFigura = Integer.parseInt(strIdFigura);
					nome = figElement.getElementsByTagName("nome").item(0).getTextContent();
					tipo = figElement.getElementsByTagName("tipo").item(0).getTextContent();
					String strNLati = figElement.getElementsByTagName("nLati").item(0).getTextContent();
					nLati = Integer.parseInt(strNLati);
					NodeList verList = doc.getElementsByTagName("versione");
					for (int j = 0; j < verList.getLength(); j++) {
						double angle = 0.0;
						Color colore = null;
						double[] xPoints = new double[] { 0, 0, 0, 0 };
						double[] yPoints = new double[] { 0, 0, 0, 0 };
						Node verNode = verList.item(j);
						if (verNode.getNodeType() == Node.ELEMENT_NODE) {
							Element verElement = (Element) verNode;
							String strVersione = verElement.getAttribute("versione");
							versione = Float.valueOf(strVersione);
							String strAngle = verElement.getElementsByTagName("angle").item(0).getTextContent();
							angle = Double.parseDouble(strAngle);
							String strColor = verElement.getElementsByTagName("color").item(0).getTextContent();
							colore = new Color(Integer.parseInt(strColor));
							for (int i = 0; i < nLati; i++) {
								String xPoint = verElement.getElementsByTagName("xPoint" + i).item(0).getTextContent();
								String yPoint = verElement.getElementsByTagName("yPoint" + i).item(0).getTextContent();
								xPoints[i] = Double.parseDouble(xPoint);
								yPoints[i] = Double.parseDouble(yPoint);
							}
						} else {
							return null;
						}
						Figura fig;
						if (nLati > 1)
							fig = new Polygon(nome, versione, tipo, idFigura, nLati, xPoints, yPoints, angle, colore);
						else
							fig = new Circle(nome, versione, tipo, idFigura, nLati, xPoints, yPoints, angle, colore);
						// System.out.println( "Prendo il primo punto della figura " + j + " versione :"
						// + fig.getVersione() + " per test: " + fig.getxPoint(0)+ " , "+
						// fig.getyPoint(0)+ " !"); //te
						loadFig.add(fig);
					}
					/*
					 * test for(int i=0;i<loadFig.size(); i++){ System.out.println(
					 * "Prendo il primo punto della figura " + i + " versione :" +
					 * loadFig.get(i).getVersione() + " per test: " + loadFig.get(i).getxPoint(0)+
					 * " , "+ loadFig.get(i).getyPoint(0)+ " !"); }
					 */
					return loadFig;
				} else {
					return null;
				}
			}
		}
		return null;
	}

	public static int countFigure() {
		initializationDir();
		return path.listFiles().length;
	}

	/*
	 * public static int getLastID(){ initializationDir(); int idPolygon = 0;
	 * 
	 * if(!file.exists()){ idPolygon = 0; return idPolygon; } try{ docBuilder =
	 * docFactory.newDocumentBuilder(); doc = docBuilder.parse(file); rootElement =
	 * doc.getDocumentElement(); docBuilder = docFactory.newDocumentBuilder();
	 * Document doc = docBuilder.parse(file); doc.getDocumentElement().normalize();
	 * NodeList nList = doc.getElementsByTagName("Figura"); Node lastNode =
	 * nList.item(nList.getLength()-1); if(lastNode.getNodeType() ==
	 * Node.ELEMENT_NODE){ Element lastElement = (Element) lastNode; idPolygon =
	 * Integer.parseInt(lastElement.getAttribute("idFigura")); }
	 * }catch(ParserConfigurationException pce){ pce.printStackTrace(); } catch
	 * (SAXException sxe) { // TODO Auto-generated catch block
	 * sxe.printStackTrace(); } catch (IOException ioe) { // TODO Auto-generated
	 * catch block ioe.printStackTrace(); } return idPolygon; }
	 */

}