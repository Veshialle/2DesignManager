
// import java.awt.geom.AffineTransform;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Composite extends Figura {
	private List<Figura> Composition = new ArrayList<>();
	private List<Color> stayColor = new ArrayList<>();
	private boolean United = false;

	public Composite(List<Figura> Parts, boolean United, String name) {
		super();
		this.Composition = Parts;
		for(Figura i : Composition) {
			stayColor.add(i.getColor());
			System.out.println(i.getColor().toString());
		}
		this.United = United;
		super.setName(name);
	}
	@Override
	public int getNFigure() {
		return this.Composition.size();
	}

	@Override
	public double getCenterX(){
		double center = 0;
		for(Figura i : Composition){
			center += i.getCenterX();
		}
		center /= Composition.size();
		return center;
	}

	public double getCenterXX() {
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
	public double getCenterY(){
		double center = 0;
		for(Figura i : Composition){
			center += i.getCenterY();
		}
		center /= Composition.size();
		return center;

	}
	public double getCenterYY() {
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
		if(United) {
			for (Figura i : this.Composition) {
				i.setColor(color);
			}
		}
	}

	@Override
	public boolean contains(Point test) {
		for(Figura i: this.Composition){
			if(i.contains(test)) return true;
		}
		return false;
	}

	@Override
	public void resize(double scaleX, double scaleY, double centerX, double centerY) {
		for(Figura i: this.Composition){
			i.resize(scaleX, scaleY, centerX, centerY);
		}
	}

	@Override
	public int getNLati() {
		//da fare????
		return super.getNLati();
	}

	@Override
	public Color getColor() {
		return this.colore;
	}

	@Override
	public boolean isUnited() {
		return United;
	}

	public void setUnited(boolean united) {
		United = united;
		if(!United){
			int j = 0;
			for(Figura i : this.Composition){
				i.setColor(stayColor.get(j));
				j++;
			}
		} else {
			setColor(Color.BLACK);
		}
	}
}
