
// import java.awt.geom.AffineTransform;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Composite extends Figura {
	private List<Figura> Composition = new ArrayList<Figura>();
	private boolean United;

	public Composite(List<Figura> Parts, boolean United, String name) {
		super();
		this.Composition = Parts;
		this.United = United;
		if (this.United) {
		}
		super.setName(name);
	}

	public int getNFigure() {
		return this.Composition.size();
	}

	public void resize(double Scale) {

	}

	@Override
	public double getCenterX() {
		double center = 0;
		int nPoints = 0;
		int j;
		for (Figura i : Composition) {
			for (j = 0; j < i.nLati; j++) {
				center += i.xPoints.get(j);
			}
			nPoints += j;
		}
		center /= nPoints;
		return center;
	}

	@Override
	public double getCenterY() {
		double center = 0;
		int nPoints = 0;
		int j;
		for (Figura i : Composition) {
			for (j = 0; j < i.nLati; j++) {
				center += i.yPoints.get(j);
			}
			nPoints += j;
		}
		center /= nPoints;
		return center;
	}

	public double getAngle() {
		return this.angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void rotate(double angle, double centerX, double centerY) {
		double rotationAngle = angle - getAngle();
		for (Figura i : Composition) {
			i.rotate(rotationAngle, centerX, centerY);
			/*
			 * 
			 * for(int j=0;j<this.nLati;j++){ double[] pt = {i.xPoints[j], i.yPoints[j]};
			 * AffineTransform.getRotateInstance(Math.toRadians(rotationAngle), center[0],
			 * center[1]).transform(pt, 0, pt, 0, 1); // specifying to use this double[] to
			 * hold coords i.xPoints[j] = pt[0]; i.yPoints[j] = pt[1]; /* Per poter
			 * risolvere la traslazione (verso l'origine del canvas se rotazione oraria o
			 * verso +inf e +inf se antioraria) la rotazione dovrebbe essere affinata, il
			 * ritorno ad intero fa perdere parte del calcolo nella rotazione
			 */

			// }

		}
	}

	@Override
	public void draw(Graphics g) {
		for(Figura i : this.Composition){
			i.draw(g);
		}
	}

	@Override
	public void move(double x, double y) {
		super.move(x, y);
		for(Figura i: this.Composition){
			i.move(x, y);
		}
	}

	@Override
	public void setColor(Color color) {
		super.setColor(color);
	}

	@Override
	public boolean contains(Point test) {
		for(Figura i: this.Composition){
			if(i.contains(test)) return true;
		}
		return false;
	}
}
