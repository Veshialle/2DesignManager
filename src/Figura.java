import java.awt.*;
import java.awt.Polygon;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import java.awt.geom.AffineTransform;

/**
 * 
 */

/**
 * @author probelter
 *
 */

// INIZIO CLASSE FIGURA
public class Figura implements java.io.Serializable{
	protected int idFigura;
	protected double x, y, width, height;
	protected int nLati;
	public boolean visibile = true;
	protected Color colore;
	protected double angle;
	protected Float versione = 0.0f;
	protected ArrayList<Double> xPoints = new ArrayList<Double>();
	protected ArrayList<Double> yPoints = new ArrayList<Double>();
	protected double[] angleToGenerate = new double[] {60, 90, 108, 120, 128.57, 135, 140, 144};
	protected String name;
	protected Polygon p;
	protected File description;
	private boolean United;

	public Figura() {
		super();
		this.idFigura = -1;
		this.setName("createdRandom");

	}

	public Figura(String name, int nLati, int idFigura, double x, double y, double width, double height) {
		this.setName(name);
		this.idFigura = idFigura;
		this.nLati = nLati;
		this.width = width;
		this.height = height;
		this.setColor(Color.BLACK);
		this.description  = new File("save/", this.getFinalName() + "." + this.getClass().getName() + ".txt");
	}

	// mossa azzardata, let's see what happen
	public Figura(String name, Float versione, String tipo, int idFigura, int nLati, ArrayList<Double> xPoints, ArrayList<Double> yPoints,
			double angle, Color colore) {
		this.setName(name);
		this.nLati = nLati;
		this.idFigura = idFigura;
		this.xPoints = xPoints;
		this.yPoints = yPoints;
		this.angle = angle;
		this.colore = colore;
		this.setVersione(versione);
		this.description  = new File("save/", this.getFinalName() + "." + this.getClass().getCanonicalName()  + ".txt");
		System.out.println(description.getName());
	}

	public void move(double x, double y) { // muovi tutti i punti della figura
		for (int i = 0; i < this.xPoints.size(); i++) {
			xPoints.set(i, xPoints.get(i) + x);
			yPoints.set(i, yPoints.get(i) + y);
		}

	}

	public double getAngle() {
		return 0.0;
	};

	public void rotate(double angle, double centerX, double centerY) {
	};

	public void setX(double x) {
		this.move(x - this.xPoints.get(0), 0);
	}

	/*
	 * la pezzo così, forse a sto punto converrebbe spostare tutto il salvataggio
	 * nell'xml in questa classe o, ancor meglio, crearne un'altra ad hoc.
	 */
	public int getId() {
		return this.idFigura;
	}

	public double getxPoint(int i) {
		return this.xPoints.get(i);
	}

	public double getyPoint(int i) {
		return this.yPoints.get(i);
	}

	public void setY(double y) {
		this.move(0, y - this.yPoints.get(0));
	}

	public int getNLati() {
		return this.nLati;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void resize(double scaleX, double scaleY, double centerX, double centerY) {	};

	public void setHeight(double d) {
		this.height = d;
	}

	public double getWidth() {
		return this.width;
	}

	public double getHeight() {
		return this.height;
	}

	public double getCenterX() {
		return 0;
	}

	public double getCenterY() {
		return 0;
	}

	public int getNFigure(){ return 1; }

	public boolean isUnited() {
		return United;
	}

	public void setUnited(boolean united) {
		United = united;
	}

	public void draw(Graphics g) {
	}

	public boolean contains(Point test) {
		return p.contains(test);
	}

	public Float getVersione() {
		return versione;
	}

	public void setVersione(Float versione) {
		this.versione = versione;
	}

	public void incrementVersione(){
		this.versione += 0.1f;
	}


	public String getName() {
		if (name.isEmpty())
			return null;
		return name;
	}

	public String getFinalName() {
		if (name.isEmpty())
			return null;
		String finalName = name + "." + versione;
		return finalName;
	}

	@Override
	public String toString() {
		if(name.isEmpty())
			return null;
		String finalName = name + " " + versione;
		return finalName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setColor(Color color) {
		this.colore = color;
	}

	public Color getColor() {
		return colore;
	}

	public File getDescription() {
		if(!description.exists()) try{
			description.createNewFile();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
		return description;
	}



	public void setDescription (String description){
		System.out.println(description);
		BufferedWriter writer = null;
		try {
			if(!getDescription().exists()) getDescription().createNewFile();
			writer = new BufferedWriter(new FileWriter(getDescription()));
			writer.write(description);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}
		}

	}

}