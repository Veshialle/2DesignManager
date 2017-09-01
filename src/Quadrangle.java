import java.awt.*;

public class Quadrangle extends Figura {
	public Quadrangle(String name, String tipo, int idFigura, double x, double y, double width, double height) {
		super(name, tipo, idFigura, x, y, width, height);
	}

	public Quadrangle(String name, Float versione, String tipo, int idFigura, int nLati, double[] xPoints,
			double[] yPoints, double angle, Color colore) {
		super(name, versione, tipo, idFigura, nLati, xPoints, yPoints, angle, colore);
	}
}
