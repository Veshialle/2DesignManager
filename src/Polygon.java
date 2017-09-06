import java.awt.*;
import java.awt.geom.AffineTransform;

public class Polygon extends Figura {
	public Polygon(String name, String tipo, int idFigura, double x, double y, double width, double height) {
		super(name, tipo, idFigura, x, y, width, height);
	}

	public Polygon(String name, Float versione, String tipo, int idFigura, int nLati, double[] xPoints,
			double[] yPoints, double angle, Color colore) {
		super(name, versione, tipo, idFigura, nLati, xPoints, yPoints, angle, colore);
	}

	public double getAngle() {
		return this.angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void rotate(double angle, double centerX, double centerY) { // ancora non implementata
		//
		// double centerX = getCenterX();
		// double centerY = getCenterY();
		double rotationAngle = angle - getAngle();
		this.setAngle(angle);
		for (int i = 0; i < this.nLati; i++) {
			double[] pt = { xPoints[i], yPoints[i] };
			AffineTransform.getRotateInstance(Math.toRadians(rotationAngle), centerX, centerY).transform(pt, 0, pt, 0,
					1); // specifying to use this double[] to hold coords
			xPoints[i] = pt[0];
			yPoints[i] = pt[1];
			/*
			 * Per poter risolvere la traslazione (verso l'origine del canvas se rotazione
			 * oraria o verso +inf e +inf se antioraria) la rotazione dovrebbe essere
			 * affinata, il ritorno ad intero fa perdere parte del calcolo nella rotazione
			 */

		}
	}

	public void resize(double scaleX, double scaleY, double centerX, double centerY) {
		for (int i = 0; i < this.nLati; i++) {
			double[] pt = { xPoints[i] - centerX, yPoints[i] - centerY };
			AffineTransform.getScaleInstance(scaleX, scaleY).transform(pt, 0, pt, 0, 1);
			//if(pt[0] < centerX +2 || centerX > pt[0] - 2)
				xPoints[i] = pt[0] + centerX;
			//else
			//	xPoints[i] = pt[0] + 3;
			//if(pt[1] < centerY + 2 && centerY > pt[1] - 2)
				yPoints[i] = pt[1] + centerY;
			//else
			//	yPoints[i] = pt[1] + 3;

		}
	}


	@Override
	public void draw(Graphics g) {
		Graphics2D d = (Graphics2D) g;
		super.draw(g);
		// d.drawPolygon(xPoints, yPoints, nLati);
		int[] xP = { 0, 0, 0, 0 };
		int[] yP = { 0, 0, 0, 0 };
		for (int i = 0; i < this.getNLati(); i++) {
			xP[i] = (int) xPoints[i];
			yP[i] = (int) yPoints[i];
		}
		p = new java.awt.Polygon(xP, yP, nLati);
		d.setColor(getColor());
		d.fillPolygon(p);
		d.drawPolygon(p);
	}

	@Override
	public boolean contains(Point test) {
		return super.contains(test);
	}
}
