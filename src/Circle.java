import java.awt.*;

public class Circle extends Figura {
	public Circle(String name, String tipo, int idFigura, double x, double y, double width, double height) {
		super(name, tipo, idFigura, x, y, width, height);
	}

	public Circle(String name, Float versione, String tipo, int idFigura, int nLati, double[] xPoints, double[] yPoints,
			double angle, Color colore) {
		super(name, versione, tipo, idFigura, nLati, xPoints, yPoints, angle, colore);
	}

	public void setXPoints(double centerX) {
		this.xPoints[0] = centerX - width / 2;
	}

	public void setYPoints(double centerY) {
		this.yPoints[0] = centerY - width / 2;
	}

	@Override
	public double getCenterX() {
		double center;
		center = this.xPoints[0];
		center += this.width / 2;
		return center;
	}

	@Override
	public double getCenterY() {
		return 0;
	}

	@Override
	public void rotate(double angle, double centerX, double centerY) {
		if (this.getCenterX() != centerX || this.getCenterY() != centerY) {
			double centerCircleX = this.getCenterX();
			double centerCircleY = this.getCenterY();
			// invece di calcolare la rotazione ogni volta, potrei fare un if, anche per
			// eliminare eventuali imprecisioni
			centerCircleX = centerCircleX * Math.cos(angle) - centerCircleY * Math.sin(angle)
					- centerX * (Math.cos(angle) - 1) + centerY * Math.sin(angle);
			centerCircleY = centerCircleX * Math.sin(angle) + centerCircleY * Math.cos(angle)
					- centerX * Math.sin(angle) - centerY * (Math.cos(angle) - 1);
			this.setXPoints(centerCircleX);
			this.setYPoints(centerCircleY);
		}
	}
}