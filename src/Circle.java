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
	public void draw(Graphics g) {
		Graphics2D d = (Graphics2D) g;
		super.draw(g);
		d.setColor(getColor());
		d.fillOval((int) xPoints[0], (int) yPoints[0], (int) width, (int) width);
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

	@Override
	public void resize(double scale) {
		super.resize(scale);

	}

	@Override
	public boolean contains(Point test) {
		double xc = this.xPoints[0] + (this.width / 2);
		double yc = this.yPoints[0] + (this.width / 2);
		double quadratica = ((test.x - xc) * (test.x - xc)) + ((test.y - yc) * (test.y - yc));
		double raggio2 = (this.width / 2) * (this.width / 2);
		if (quadratica <= raggio2) { // disuguaglianza per vedere se il punto in cui ho clickato col mouse è
			// all'interno (circorferenza compresa) nel cerchio disegnato
			return true;
		} else
			return false;
	}
}