import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
//import java.awt.geom.AffineTransform;

/**
 * 
 */

/**
 * @author probelter
 *
 */

// INIZIO CLASSE FIGURA
public class Figura {
	protected int idFigura;
	protected double x, y, width, height;
	protected int nLati;
	public boolean visibile = true;
	private Color colore;
	protected double angle;
	private Float versione = 0.0f;
	protected double[] xPoints = new double[] { 0, 0, 0, 0 };
	protected double[] yPoints = new double[] { 0, 0, 0, 0 };
	protected String tipo;
	private String name;
	protected Polygon p;

	public Figura() {
		super();
		this.idFigura = -1;
		this.tipo = "";
		this.setName("");
	}

	public Figura(String name, String tipo, int idFigura, double x, double y, double width, double height) {
		this.idFigura = idFigura;
		this.tipo = tipo;
		this.setName(name);
		this.width = width;
		this.height = height;
		this.init(x, y, width, height); // inizializza i punti della figura
	}

	// mossa azzardata, let's see what happen
	public Figura(String name, Float versione, String tipo, int idFigura, int nLati, double[] xPoints, double[] yPoints,
			double angle, Color colore) {
		this.tipo = tipo;
		this.setName(name);
		this.nLati = nLati;
		this.idFigura = idFigura;
		this.xPoints = xPoints;
		this.yPoints = yPoints;
		this.angle = angle;
		this.colore = colore;
		this.setVersione(versione);
	}

	public void init(double x, double y, double width, double height) { // inizializza i punti della figura
		if (this.tipo == "rettangolo") {
			this.nLati = 4;
			xPoints = new double[] { x, x + width, x + width, x };
			yPoints = new double[] { y, y, y + height, y + height };
			this.colore = Color.BLACK;
		} else if (this.tipo == "triangolo") {
			this.nLati = 3;
			xPoints = new double[] { x, x + (width / 2), x + width, 0 };
			yPoints = new double[] { y, y - height, y, 0 };
			this.colore = Color.ORANGE;
		} else if (this.tipo == "quadrato") {
			this.nLati = 4;
			xPoints = new double[] { x, x + width, x + width, x };
			yPoints = new double[] { y, y, y + width, y + width };
			this.colore = Color.GREEN;
		} else if (this.tipo == "rombo") {
			this.nLati = 4;
			xPoints = new double[] { x, x + (width / 2), x + width, x + (width / 2) };
			yPoints = new double[] { y, y - (height / 2), y, y + (height / 2) };
			this.colore = Color.YELLOW;
		} else if (this.tipo == "cerchio") {
			this.nLati = 1;
			xPoints = new double[] { x, 0, 0, 0 };
			yPoints = new double[] { y, 0, 0, 0 };
			this.colore = Color.BLUE;
		}

	}

	public void move(double x, double y) { // muovi tutti i punti della figura
		for (int i = 0; i < this.nLati; i++) {
			xPoints[i] = xPoints[i] + x;
			yPoints[i] = yPoints[i] + y;
		}

	}

	public double getAngle() {
		return 0.0;
	};

	public void rotate(double angle, double centerX, double centerY) {
	};

	public void setX(double x) {
		this.move(x - this.xPoints[0], 0);
	}

	/*
	 * la pezzo così, forse a sto punto converrebbe spostare tutto il salvataggio
	 * nell'xml in questa classe o, ancor meglio, crearne un'altra ad hoc.
	 */
	public int getId() {
		return this.idFigura;
	}

	public double getxPoint(int i) {
		return this.xPoints[i];
	}

	public double getyPoint(int i) {
		return this.yPoints[i];
	}

	public void setY(double y) {
		this.move(0, y - this.yPoints[0]);
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

	public void resize(double scale) {	};

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
		double center = 0;
		if (this.tipo != "cerchio") {
			for (int i = 0; i < nLati; i++) {
				center += this.xPoints[i];
			}
			center /= nLati;
		} else {
			center = this.xPoints[0];
			center += this.width / 2;
		}
		return center;
	}

	public double getCenterY() {
		double center = 0;
		if (this.tipo != "cerchio") {
			for (int i = 0; i < nLati; i++) {
				center += this.yPoints[i];
			}
			center /= nLati;
		} else {
			center = this.yPoints[0];
			center += this.width / 2;
		}
		return center;
	}

	public void draw(Graphics g) { }

	public boolean contains(Point test) {
			return p.contains(test);
	}

	public Float getVersione() {
		return versione;
	}

	public void setVersione(Float versione) {
		this.versione = versione;
	}


	public String getName() {
		if (name.isEmpty())
			return tipo;
		return name;
	}

	public String getFinalName() {
		if (name.isEmpty())
			return tipo;
		String finalName = name + " " + versione;
		return finalName;
	}

	@Override
	public String toString() {
		if(name.isEmpty())
			return tipo;
		String finalName = name + " " + versione;
		return finalName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String name) {
		this.tipo = name;
	}

	public void setColor(Color color) {
		this.colore = color;
	}

	public Color getColor() {
		return colore;
	}
}