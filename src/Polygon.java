import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Polygon extends Figura {
	public Polygon(String name, int nLati, int idFigura, double x, double y, double width, double height) {
		super(name, nLati, idFigura, x, y, width, height);
		xPoints.add(200.0);
		yPoints.add(200.0);
		double newPoint;
		double theta = (2 * Math.PI) /nLati;
		double translation = 200.0;
		for(int i = 0; i< nLati; i++){
			double z = Math.cos(theta *i);
			double k = Math.sin(theta *i);
			newPoint = translation +  (width/2)*k;
			xPoints.add(i,newPoint);
			newPoint = translation +  (width/2)*z;
			yPoints.add(i,newPoint);
		}
	}

	public Polygon(String name, Float versione, String tipo, int idFigura, int nLati, ArrayList<Double> xPoints, ArrayList<Double> yPoints, double angle, Color colore) {
		super(name, versione, tipo, idFigura, nLati, xPoints, yPoints, angle, colore);
	}

	public void init(double x, double y, double width, double height) {

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
			double[] pt = { xPoints.get(i), yPoints.get(i) };
			AffineTransform.getRotateInstance(Math.toRadians(rotationAngle), centerX, centerY).transform(pt, 0, pt, 0,
					1); // specifying to use this double[] to hold coords
			xPoints.set(i, pt[0]);
			yPoints.set(i, pt[1]);
			/*
			 * Per poter risolvere la traslazione (verso l'origine del canvas se rotazione
			 * oraria o verso +inf e +inf se antioraria) la rotazione dovrebbe essere
			 * affinata, il ritorno ad intero fa perdere parte del calcolo nella rotazione
			 */

		}
	}

	public void resize(double scaleX, double scaleY, double centerX, double centerY) {
		/*
		double xVar = (newX - oldX) / (centerX - oldX);
		double scaleX = (-xVar * 8 / 10) + 1.0; // era troppo veloce a ingrandire
		*/
		for (int i = 0; i < this.nLati; i++) {
			double[] pt = { xPoints.get(i) - centerX, yPoints.get(i) - centerY };
			AffineTransform.getScaleInstance(scaleX, scaleY).transform(pt, 0, pt, 0, 1);
			//if(pt[0] < centerX +2 || centerX > pt[0] - 2)
				xPoints.set(i, pt[0] + centerX);
			//else
			//	xPoints[i] = pt[0] + 3;
			//if(pt[1] < centerY + 2 && centerY > pt[1] - 2)
				yPoints.set(i, pt[1] + centerY);
			//else
			//	yPoints[i] = pt[1] + 3;

		}
	}

	@Override
	public double getCenterX() {
		double center = 0;
		for (int i = 0; i < nLati; i++) {
			center += this.xPoints.get(i);
		}
		center /= nLati;
		return center;
	}

	@Override
	public double getCenterY() {
		double center = 0;
		for (int i = 0; i < nLati; i++) {
			center += this.yPoints.get(i);
		}
		center /= nLati;
		return center;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D d = (Graphics2D) g;
		int[] xP = new int[nLati];
		int[] yP = new int[nLati];
		for (int i = 0; i < this.getNLati(); i++) {
			xP[i] = xPoints.get(i).intValue();
			yP[i] = yPoints.get(i).intValue();
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
