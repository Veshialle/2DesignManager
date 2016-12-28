import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JList;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import javax.swing.JScrollBar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


//INIZIO CLASSE MYCANVAS
class MyCanvas extends JComponent {
    
    private int x,y,index;
    private List<Figura> array;
    
    
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public MyCanvas(List<Figura> fig) {
        this.array = fig;
    }
    
    public void paintImage(){
        Graphics d = getGraphics();
        d.clearRect(0, 0, getWidth(), getHeight() ); //pulisce il canvas
        for (Figura i : array) { //per ogni figura nella lista di figure
            if (i.visibile == true) {//se la figura e' visibile
                i.draw(d);//chiama il metodo "disegna" della figura
                
                
            }
        }
        this.validate();
    }
}
//FINE CLASSE MYCANVAS


// INIZIO CLASSE FIGURA
class Figura{
    private double x,y,width,height;
    private int nLati;
    public boolean visibile=true;
    private double angle;
    public double [] xPoints = new double[]{0,0,0,0};
    public double [] yPoints = new double[]{0,0,0,0};
    public String tipo;
    public Polygon p;
    public Figura(String s, double x,double y,double width,double height) {
    	this.tipo=s;
    	this.x=x;
    	this.y=y;
    	this.width=width;
    	this.height=height;
    	this.init(x, y, width, height);  //inizializza i punti della figura
	}
    public void init(double x,double y,double width,double height){ //inizializza i punti della figura
    	if(this.tipo=="rettangolo"){
            this.nLati=4;
            xPoints = new double[] {x, x+width, x+width, x};
            yPoints = new double[] {y, y, y+height,y+height};
        }else if(this.tipo=="triangolo"){
            this.nLati=3;
            xPoints = new double[] {x, x+(width/2), x+width, 0 };
            yPoints = new double[] {y, y-height, y, 0};
        }else if(this.tipo=="quadrato"){
            this.nLati=4;
            xPoints = new double[] {x, x+width, x+width, x};
            yPoints = new double[] {y, y, y+width,y+width};
        }else if(this.tipo=="rombo"){
            this.nLati=4;
            xPoints = new double[] {x, x+(width/2), x+width, x+(width/2)};
            yPoints = new double[] {y, y-(height/2), y,y+(height/2)};
        }else if(this.tipo=="cerchio"){
        	this.nLati=1;
        	xPoints = new double[] {x, 0, 0, 0};
            yPoints = new double[] {y, 0, 0, 0};
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
}
//FINE CLASSE FIGURA


//INIZIO CLASSE WINDOW (MAIN)
public class Window {
	public static Point mousePt;
	public static int firstSelIx;
	public static boolean isInside;
	public static boolean Resize = false;
    public static void main(String[] a) {
        
        List<Figura> fig = new ArrayList<Figura>(); //fig e' una lista di oggetti Figura
        
        
        JFrame window = new JFrame();  //window sara' la mia finestra principale
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 1200, 1200);
        window.setResizable(false);
        MyCanvas canvas = new MyCanvas(fig); // canvas sara' lo spazio dove disegno le figure
        
        
        
        JButton btnLeft = new JButton("Left"); //Bottone "Left"
        btnLeft.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        JSpinner spinnerPos = new JSpinner(); //Finestrina Input spostamenti, a destra del bottone "Right"
        spinnerPos.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
        JButton btnRight = new JButton("Right");//Bottone "Right"
        btnRight.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        JButton btnDown = new JButton("Down");//Bottone "Down"
        btnDown.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        JButton btnUp = new JButton("Up");//Bottone "Up"
        btnUp.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        
        
        
        DefaultListModel<String> model = new DefaultListModel<>();  //lista di stringhe
        for ( Figura i : fig ){
            model.addElement( i.tipo ); //aggiungo dentro la lista di stringe "model" il tipo di figura -> figura.tipo -> i.tipo
        }
        
        JScrollPane scrollPane = new JScrollPane(); //Panello dove verra' visualizzata la lista degli oggetti presenti
        JButton btnAddFig = new JButton("+Fig"); //Bottone aggiungi Figura
        btnAddFig.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        JButton btnRemoveFig = new JButton("-Fig");//Bottone rimuovi Figura
        btnRemoveFig.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        JButton btnSetX = new JButton("Set X");//Bottone set X
        btnSetX.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        JButton btnSetY = new JButton("Set Y");//Bottone set Y
        btnSetY.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        
        JButton btnSetheight = new JButton("Set Height"); //bottone set height (altezza)
        btnSetheight.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        
        JButton btnSetWidth = new JButton("Set Width");// bottone set width (larghezza)
        btnSetWidth.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        
        JSpinner spinnerSizes = new JSpinner();// Finestrina input delle dimensioni
        spinnerSizes.setModel(new SpinnerNumberModel(0.0, 0.0, 360.0, 1.0));
        
        JButton btnRotation = new JButton("Zero Degree");
        btnRotation.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        
        JScrollBar rotationBar = new JScrollBar();
        rotationBar.setEnabled(fig.isEmpty());
        rotationBar.setMinimum(-180);
        rotationBar.setMaximum(190); //non capisco come mai, ma settando a 180 (come dovrebbe essere) arriva solo fino a 170
        rotationBar.setOrientation(JScrollBar.HORIZONTAL);
        
        JButton btnSave = new JButton("Salva Canvas");
        btnSave.setFont(new Font("Dialog", Font.PLAIN, 10));
        
        
        
        
        
        //creazione layout... come sono disposte le finestre/bottoni
        //autogenerata
        //ignorare
        GroupLayout groupLayout = new GroupLayout(window.getContentPane());
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(btnLeft)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(btnUp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(btnDown, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnRight)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(spinnerPos, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(btnSetY, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(btnSetX, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        						.addComponent(btnAddFig)
        						.addComponent(btnRemoveFig))
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(btnSetWidth, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(btnSetheight, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        						.addGroup(groupLayout.createSequentialGroup()
        							.addComponent(spinnerSizes, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(btnRotation)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(btnSave))
        						.addComponent(rotationBar, GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)))
        				.addComponent(canvas, GroupLayout.PREFERRED_SIZE, 1196, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGap(29)
        					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(spinnerPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(btnLeft, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
        						.addComponent(btnRight)))
        				.addGroup(groupLayout.createSequentialGroup()
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        						.addGroup(groupLayout.createSequentialGroup()
        							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        								.addGroup(groupLayout.createSequentialGroup()
        									.addGap(27)
        									.addComponent(spinnerSizes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        								.addGroup(groupLayout.createSequentialGroup()
        									.addContainerGap()
        									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        										.addComponent(btnRotation)
        										.addComponent(btnSave))))
        							.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
        							.addComponent(rotationBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        						.addGroup(groupLayout.createSequentialGroup()
        							.addGap(9)
        							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
        								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        									.addComponent(scrollPane, 0, 0, Short.MAX_VALUE)
        									.addGroup(groupLayout.createSequentialGroup()
        										.addPreferredGap(ComponentPlacement.RELATED)
        										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        											.addComponent(btnAddFig)
        											.addComponent(btnSetheight))
        										.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
        										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        											.addComponent(btnRemoveFig)
        											.addComponent(btnSetWidth)))
        									.addGroup(groupLayout.createSequentialGroup()
        										.addComponent(btnSetX)
        										.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
        										.addComponent(btnSetY)))
        								.addGroup(groupLayout.createSequentialGroup()
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(btnUp)
        									.addGap(18)
        									.addComponent(btnDown)))))
        					.addGap(42)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(canvas, GroupLayout.PREFERRED_SIZE, 776, GroupLayout.PREFERRED_SIZE)
        			.addGap(286))
        );
        //fine impostazione layout
        
        JList<String> list_1 = new JList<>( model ); //una JList che prende la lista di stringhe model
        scrollPane.setViewportView(list_1); //passo la JList al pannello di visualizzazione
        window.getContentPane().setLayout(groupLayout);
        
        // Per poter settare la barra dell'angolo quando si seleziona tramite la lista (scrollPane)
        list_1.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
        			firstSelIx = list_1.getSelectedIndex();
        			System.out.print(firstSelIx);
    				rotationBar.setValue((int)fig.get(firstSelIx).getAngle());
        			
			}
    	});
        
        window.setVisible(true);
        canvas.paintImage();
        
        btnSetX.addActionListener(new ActionListener() { //Imposta val X
            public void actionPerformed(ActionEvent e) {
                int firstSelIx = list_1.getSelectedIndex();
                Figura f = fig.get(firstSelIx);
                f.setX((double) spinnerPos.getValue());
                canvas.paintImage();
            }
        });
        
        btnSetY.addActionListener(new ActionListener() { //Imposta val Y
            public void actionPerformed(ActionEvent e) {
                int firstSelIx = list_1.getSelectedIndex();
                Figura f = fig.get(firstSelIx);
                f.setY((double) spinnerPos.getValue());
                canvas.paintImage();
            }
        });
        
        btnSetheight.addActionListener(new ActionListener() { //Imposta Altezza
            public void actionPerformed(ActionEvent e) {
                int firstSelIx = list_1.getSelectedIndex();
                Figura f = fig.get(firstSelIx);
                f.setHeight((double) spinnerSizes.getValue());
                canvas.paintImage();
            }
        });
        
        btnSetWidth.addActionListener(new ActionListener() { //Imposta Larghezza
            public void actionPerformed(ActionEvent e) {
                int firstSelIx = list_1.getSelectedIndex();
                fig.get(firstSelIx).setWidth((double) spinnerSizes.getValue());
                canvas.paintImage();
                Resize=(!Resize);
            }
        });
        
        btnRotation.addActionListener(new ActionListener() { //Riportare alla posizione iniziale (0 gradi)
            public void actionPerformed(ActionEvent e) {
                int firstSelIx = list_1.getSelectedIndex();
                Figura f = fig.get(firstSelIx);
        		f.rotate(0.0);
        		rotationBar.setValue((int)f.getAngle());
                canvas.paintImage();
            }
                
            
        });

        rotationBar.addAdjustmentListener(new AdjustmentListener() {
        	public void adjustmentValueChanged(AdjustmentEvent e) {
        		double angle = (double) e.getValue();
        		Figura f = fig.get(firstSelIx);
        		System.out.println("Rotation "+ angle + "\n");
        		f.rotate(angle);
        		canvas.paintImage();
          }
        });
        
        btnAddFig.addActionListener(e -> { //Bottone aggiungi/crea una nuova Figura

            
            //--------------------------------------------------------- Grazie google
            
            Object[] possibilities = {"rettangolo", "quadrato", "triangolo", "rombo", "cerchio"};
            Component frame = null;
            Icon icon = null;
            String s = (String)JOptionPane.showInputDialog(
                                                           frame,
                                                           "Select fig type:\n",
                                                           "Customized Dialog",
                                                           JOptionPane.PLAIN_MESSAGE,
                                                           icon,
                                                           possibilities,
                                                           "rettangolo");
            
            //If a string was returned, say so.
            if ((s != null) && (s.length() > 0)) {
                setLabel("Verra' creato un " + s + "!");
            }
            
            //If you're here, the return value was null/empty.
            setLabel("Seleziona una figura");
            
            //---------------------------------------------------------
            Figura f = new Figura(s,200,200,200,100); //inizializzo/creo la Figura(s=tipo di figura, x=0, y=0, larghezza, altezza);
            fig.add(f); //aggiungi la figura appena creata f alla lista di Figure "fig"
            canvas.paintImage(); //disegna
            
            model.addElement( f.tipo );	//aggiungi alla lista di stringhe il tipo della nuova figura
            list_1.setSelectedIndex(model.getSize()-1);//seleziona nella lista output l'ultima figura inserita
            firstSelIx = list_1.getSelectedIndex();
			rotationBar.setValue((int)fig.get(firstSelIx).getAngle());
            
        });
        
        btnRemoveFig.addActionListener(e -> { //Bottone rimuovi Figura
            int firstSelIx = list_1.getSelectedIndex(); //dammi l'indice della figura selezionata
            fig.remove(firstSelIx); //rimuovi dalla lista di figure la figura con l'indice appena ricavato
            model.remove(firstSelIx);//rimuovi la stringa della figura nella lista dei tipi
            canvas.paintImage();//disegna di nuovo
            
            
        });
        
        
        //bottone Left
        btnLeft.addActionListener(e -> { //bottone muovi a Sinistra
        	int firstSelIx = list_1.getSelectedIndex(); //dammi l'indice della figura selezionata nella finestra di figure
            if((double) spinnerPos.getValue() >=0){// se il valore della finestra di input delle posizioni e' positivo
                double x1=(-(double) spinnerPos.getValue());//prendi il valore negativo
                fig.get(firstSelIx).move(x1, 0);  //muovi la figura selezionata del valore x1, y rimangono uguali
            }
            canvas.paintImage();//disegna di nuovo
            
        });
        
        
        //bottone Right
        btnRight.addActionListener(e -> {  //stessa cosa di prima, muovi a destra
        	int firstSelIx = list_1.getSelectedIndex();
            if((double) spinnerPos.getValue() >=0){
            	double x1=(+(double) spinnerPos.getValue());
                fig.get(firstSelIx).move(x1, 0); 
                
            }
            canvas.paintImage();
            
        });
        
        
        //bottone Down
        btnDown.addActionListener(e -> {  //stessa cosa di prima, muovi in giu'
        	int firstSelIx = list_1.getSelectedIndex();
            if((double) spinnerPos.getValue() >=0){
            	double y1=(+(double) spinnerPos.getValue());
                fig.get(firstSelIx).move(0, y1); 
                
            }
            canvas.paintImage();
            
        });
        
        //bottone Up
        btnUp.addActionListener(e -> {   //stessa cosa di prima, muovi in su
        	int firstSelIx = list_1.getSelectedIndex();
            if((double) spinnerPos.getValue() >=0){
            	double y1=(-(double) spinnerPos.getValue());
                fig.get(firstSelIx).move(0, y1);
                
            }
            canvas.paintImage();
            
        });
        
        //mouse listener     
        canvas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));   
        canvas.addMouseListener(new MouseAdapter() {  	
        	@Override
            public void mousePressed(MouseEvent e) {
        		mousePt = e.getPoint();
        		for(int i=fig.size()-1;i>=0;i--){
        			if(fig.get(i).contains(mousePt)){//
        				isInside=true;
        				list_1.setSelectedIndex(i); 
        				
        	            firstSelIx = list_1.getSelectedIndex();
        				rotationBar.setValue((int)fig.get(firstSelIx).getAngle());
        				break;
        			}else{
        				isInside=false;
        			}
        		}
                canvas.paintImage();                
            }   	
        }); 
        canvas.addMouseMotionListener(new MouseAdapter() {
        	@Override	
        	public void mouseDragged(MouseEvent e) {
        		if (isInside) {
    				firstSelIx = list_1.getSelectedIndex(); 
    				double dx = e.getX() - mousePt.x;
    				double dy = e.getY() - mousePt.y;
    				if(Resize){	
    					fig.get(firstSelIx).resize(dx,dy);
    					canvas.paintImage();
    				}
    				
        			else
        			{
        				System.out.print("dragged\n");
						fig.get(firstSelIx).move(dx, dy);
						canvas.paintImage();
        		}
					mousePt = e.getPoint();
				}
        	}        	
        });   
    }
    public static double getAngle(int y, int x){
    	double angle = Math.atan2(y, x);
    	if (angle < 0){
    		angle += 2*Math.PI;
    	}
    	return angle; 
    }
    
    
    private static void setLabel(String string) {
        // TODO Auto-generated method stub
        
    }
}











