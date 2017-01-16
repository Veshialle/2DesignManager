import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

/**
 * 
 */

/**
 * @author probelter
 *
 */

// INIZIO CLASSE FIGURA
public class Figura{
	private int idFigura;
    private double x,y,width,height;
    private int nLati;
    public boolean visibile=true;
    private Color colore;
    private double angle;
	private Float versione;
    private double [] xPoints = new double[]{0,0,0,0};
    private double [] yPoints = new double[]{0,0,0,0};
    private String tipo;
    private String name;
    private Polygon p;
    public Figura(String name,String tipo,int idFigura, double x,double y,double width,double height) {
    	this.idFigura = idFigura;
    	this.tipo=tipo;
    	this.setName(name);
    	this.x=x;
    	this.y=y;
    	this.width=width;
    	this.height=height;
    	this.init(x, y, width, height);  //inizializza i punti della figura
	}
    //mossa azzardata, let's see what happen
    public Figura(String name,Float versione, String tipo,int idFigura,int nLati, double[] xPoints, double[] yPoints, double angle, Color colore){
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
    public void init(double x,double y,double width,double height){ //inizializza i punti della figura
    	if(this.tipo=="rettangolo"){
            this.nLati=4;
            xPoints = new double[] {x, x+width, x+width, x};
            yPoints = new double[] {y, y, y+height,y+height};
            this.colore=Color.BLACK;
        }else if(this.tipo=="triangolo"){
            this.nLati=3;
            xPoints = new double[] {x, x+(width/2), x+width, 0 };
            yPoints = new double[] {y, y-height, y, 0};
            this.colore=Color.ORANGE;
        }else if(this.tipo=="quadrato"){
            this.nLati=4;
            xPoints = new double[] {x, x+width, x+width, x};
            yPoints = new double[] {y, y, y+width,y+width};
            this.colore=Color.GREEN;
        }else if(this.tipo=="rombo"){
            this.nLati=4;
            xPoints = new double[] {x, x+(width/2), x+width, x+(width/2)};
            yPoints = new double[] {y, y-(height/2), y,y+(height/2)};
            this.colore=Color.YELLOW;
        }else if(this.tipo=="cerchio"){
        	this.nLati=1;
        	xPoints = new double[] {x, 0, 0, 0};
            yPoints = new double[] {y, 0, 0, 0};
            this.colore=Color.BLUE;
        }
    	
    }
    public void move(double x,double y){   //muovi tutti i punti della figura
    	for(int i=0; i<this.nLati;i++){
    		xPoints[i]=xPoints[i]+x;
    		yPoints[i]=yPoints[i]+y;
    	}
    	
    }

    public void rotate(double angle){ //ancora non implementata
		
		double centerX = getCenterX();		
		double centerY = getCenterY();
		double rotationAngle = angle - getAngle();
		this.setAngle(angle);	
		for(int i=0;i<this.nLati;i++){
			double[] pt = {xPoints[i], yPoints[i]};
			AffineTransform.getRotateInstance(Math.toRadians(rotationAngle), centerX, centerY).transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
			xPoints[i] = pt[0];
			yPoints[i] = pt[1];
			/*
			 * Per poter risolvere la traslazione (verso l'origine del canvas se rotazione oraria o verso +inf e +inf se antioraria)
			 * la rotazione dovrebbe essere affinata, il ritorno ad intero fa perdere parte del calcolo nella rotazione
			 */
			
		}
	}

	public void setX(double x){
        this.move(x-this.xPoints[0], 0);
    }
	/*la pezzo così, forse a sto punto converrebbe spostare tutto il salvataggio 
		nell'xml in questa classe o, ancor meglio, crearne un'altra ad hoc.
	*/
	public int getId(){
		return this.idFigura;
	}
	public double getxPoint(int i)
	{
		return this.xPoints[i];
	}
	public double getyPoint(int i)
	{
		return this.yPoints[i];
	}
    public void setY(double y){
    	this.move(0, y-this.yPoints[0]);
    }
    public int getNLati(){
        return this.nLati;
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }   
    public double getAngle(){
    	return this.angle;
    }
    public void setAngle(double angle){
    	this.angle = angle;
    }
    public void setWidth(double width){
        this.width=width;
    }
    public void resize(double dx, double dy){
    	double cx = this.getCenterX();
    	double cy = this.getCenterY();
    	

    	for(int i=0;i<xPoints.length;i++){
    		if(xPoints[i]<cx)
    			if(!(xPoints[i]+5>cx))
    				xPoints[i]-=dx;
    			else
    				xPoints[i]-=5;
    		else if(cx==xPoints[i]){
    			//non fare nulla
    		}else{
    			if(!(xPoints[i]-5<cx))
    				xPoints[i]+=dx;
    			else
    				xPoints[i]+=5;
    		}
    		
    		if(yPoints[i]<cy)
    			if(!(yPoints[i]+5>cy))
    				yPoints[i]-=dy;
    			else
    				yPoints[i]-=5;
    		else if(cy==yPoints[i]){
    			//non fare nulla
    		}else{
    			if(!(yPoints[i]-5<cy))
    				yPoints[i]+=dy;
    			else
    				yPoints[i]+=5;
    		}
    	}
    }        

    public void setHeight(double d){
        this.height=d;
    }
    public double getWidth(){
        return this.width;
    }
    public double getHeight(){
        return this.height;
    }
    public double getCenterX(){
    	double center = 0;
    	if(this.tipo!="cerchio"){
    		for(int i=0;i < nLati; i++){
    			center += this.xPoints[i];
    		}
    		center /= nLati;
    	}
    	else{
    		center = this.xPoints[0];
    		center += this.width /2;
    	}
    	return center;
    }
    public double getCenterY(){
    	double center = 0;
    	if(this.tipo!="cerchio"){
    		for(int i=0;i < nLati; i++){
    			center += this.yPoints[i];
    		}
    		center /= nLati;
    	}
    	else{
    		center = this.yPoints[0];
    		center += this.width /2;
    	}
    	return center;
    }
    public void draw(Graphics g){
    	
    	Graphics2D d = (Graphics2D)g;
    	
    	
    	//this.initFig(this.x, this.y, this.width, this.height);
        if(this.tipo=="cerchio"){ 
            d.drawOval((int)xPoints[0], (int)yPoints[0], (int)width, (int)width);// in questo caso width e' il diametro            
        }
        if(this.tipo!="cerchio"){
        	//d.drawPolygon(xPoints, yPoints, nLati);
        	int [] xP = {0,0,0,0};
        	int [] yP = {0,0,0,0};
        	for(int i=0;i<this.getNLati(); i++)
        	{
        		xP[i] = (int)xPoints[i];
        		yP[i] = (int)yPoints[i];
        	}
        	p = new Polygon(xP, yP, nLati);
        	d.setColor(colore);
        	d.fillPolygon(p);
        	d.drawPolygon(p);	
        }
    }
    
    public boolean contains(Point test) {
    	if(this.tipo!="cerchio")
    		return p.contains(test);
    	else{
    		double xc = this.xPoints[0]+(this.width/2);
    		double yc = this.yPoints[0]+(this.width/2);
    		double quadratica = ((test.x-xc)*(test.x-xc)) + ((test.y - yc)*(test.y - yc));
    		double raggio2 = (this.width/2)*(this.width/2);
    		if(quadratica <= raggio2){ // disuguaglianza per vedere se il punto in cui ho clickato col mouse è all'interno (circorferenza compresa) nel cerchio disegnato
    			return true;
    		}else
    			return false;
    	}
    	
      }
	public Float getVersione() {
		return versione;
	}
	public void setVersione(Float versione) {
		this.versione = versione;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTipo(){
		return tipo;
	}
    public void setTipo(String name){
    	this.tipo = name;
    }
    public void setColor(Color color){
    	this.colore = color;
    }public Color getColor(){
		return colore;
	}
}
//FINE CLASSE FIGURA