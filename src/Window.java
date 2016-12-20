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
    private int x,y,width,height;
    private int nLati;
    public boolean visibile=true;
    public int angle;
    public int [] xPoints = new int[]{0,0,0,0};
    public int [] yPoints = new int[]{0,0,0,0};
    public String tipo;
    public Polygon p;
    public Figura(String s, int x,int y,int width,int height) {
    	this.tipo=s;
    	this.x=x;
    	this.y=y;
    	this.width=width;
    	this.height=height;
    	this.init(x, y, width, height);  //inizializza i punti della figura
	}
    public void init(int x,int y,int width,int height){ //inizializza i punti della figura
    	if(this.tipo=="rettangolo"){
            this.nLati=4;
            xPoints = new int[] {x, x+width, x+width, x};
            yPoints = new int[] {y, y, y+height,y+height};
        }else if(this.tipo=="triangolo"){
            this.nLati=3;
            xPoints = new int[] {x, x+(width/2), x+width, 0 };
            yPoints = new int[] {y, y-height, y, 0};
        }else if(this.tipo=="quadrato"){
            this.nLati=4;
            xPoints = new int[] {x, x+width, x+width, x};
            yPoints = new int[] {y, y, y+width,y+width};
        }else if(this.tipo=="rombo"){
            this.nLati=4;
            xPoints = new int[] {x, x+(width/2), x+width, x+(width/2)};
            yPoints = new int[] {y, y-(height/2), y,y+(height/2)};
        }else if(this.tipo=="cerchio"){
        	this.nLati=1;
        	xPoints = new int[] {x, 0, 0, 0};
            yPoints = new int[] {y, 0, 0, 0};
        }
    	
    }
    public void move(int x,int y){   //muovi tutti i punti della figura
    	for(int i=0; i<this.nLati;i++){
    		xPoints[i]=xPoints[i]+x;
    		yPoints[i]=yPoints[i]+y;
    	}
    	
    }

    public void rotate(float angle){ //ancora non implementata
		
		//trovare center.x center.y
		int maxX = 0;
	    int maxY = 0;
	    int minX = 0;
	    int minY = 0;
	    for (int index = 0; index < this.nLati; index++) {
	        maxX = Math.max(maxX, xPoints[index]);
	        minX = Math.min(minX, xPoints[index]);
	    }
	    for (int index = 0; index < this.nLati; index++) {
	        maxY = Math.max(maxY, yPoints[index]);
	        minY = Math.min(minY, yPoints[index]);
	    }
	    
	    int xc = (maxX-minX)/2;
		int yc = (maxY-minY)/2;
		
		
		//farlo per tutti i xPoints
		//ruotare
		for(int i=0;i<this.nLati;i++){
		double[] pt = {xPoints[i], yPoints[i]};
		AffineTransform.getRotateInstance(Math.toRadians(angle), xc, yc)
		  .transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
		xPoints[i] = (int) pt[0];
		yPoints[i] = (int) pt[1];
		}
	}
    
	public void setX(int x){
        this.move(x-this.xPoints[0], 0);
    }
	
    public void setY(int y){
    	this.move(0, y-this.yPoints[0]);
    }
    public int getNLati(){
        return this.nLati;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void setWidth(int width){
        this.width=width;
    }
    public void setHeight(int height){
        this.height=height;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public void draw(Graphics g){
    	
    	Graphics2D d = (Graphics2D)g;
    	
    	//this.initFig(this.x, this.y, this.width, this.height);
        if(this.tipo=="cerchio"){ 
            d.drawOval(xPoints[0], yPoints[0], width, width);// in questo caso width e' il diametro
            
        }
        if(this.tipo!="cerchio"){
        	//d.drawPolygon(xPoints, yPoints, nLati);
        	p = new Polygon(xPoints, yPoints, nLati);
        	d.drawPolygon(p);
        	
        	
        }
    }
    public boolean contains(Point test) {
    	if(this.tipo!="cerchio")
    		return p.contains(test);
    	else{
    		int xc = this.xPoints[0]+(this.width/2);
    		int yc = this.yPoints[0]+(this.width/2);
    		int quadratica = ((test.x-xc)*(test.x-xc)) + ((test.y - yc)*(test.y - yc));
    		int raggio2 = (this.width/2)*(this.width/2);
    		System.out.print(quadratica);
			System.out.print(" ");
			System.out.print(raggio2);
			System.out.print("\n");
    		if(quadratica <= raggio2){
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
        
        JButton btnRotateRight = new JButton("Rotate Right"); // Bottone Ruota (destra?)
        btnRotateRight.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
        
        
        
        
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
        					.addComponent(spinnerSizes, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnRotateRight))
        				.addComponent(canvas, GroupLayout.PREFERRED_SIZE, 1196, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(91, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
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
        									.addComponent(btnDown)))
        							.addGap(42))
        						.addGroup(groupLayout.createSequentialGroup()
        							.addGap(29)
        							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
        								.addComponent(spinnerPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(btnLeft, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
        								.addComponent(btnRight))))
        					.addGroup(groupLayout.createSequentialGroup()
        						.addGap(27)
        						.addComponent(spinnerSizes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addPreferredGap(ComponentPlacement.RELATED)))
        				.addGroup(groupLayout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(btnRotateRight)
        					.addPreferredGap(ComponentPlacement.RELATED)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(canvas, GroupLayout.PREFERRED_SIZE, 776, GroupLayout.PREFERRED_SIZE)
        			.addGap(286))
        );
        //fine impostazione layout
        
        JList<String> list_1 = new JList<>( model ); //una JList che prende la lista di stringhe model
        scrollPane.setViewportView(list_1); //passo la JList al panello di visualizzazione
        window.getContentPane().setLayout(groupLayout);
        
        
        window.setVisible(true);
        canvas.paintImage();
        
        btnSetX.addActionListener(new ActionListener() { //Imposta val X
            public void actionPerformed(ActionEvent e) {
                int firstSelIx = list_1.getSelectedIndex();
                Figura f = fig.get(firstSelIx);
                f.setX((int) spinnerPos.getValue());
                canvas.paintImage();
            }
        });
        
        btnSetY.addActionListener(new ActionListener() { //Imposta val Y
            public void actionPerformed(ActionEvent e) {
                int firstSelIx = list_1.getSelectedIndex();
                Figura f = fig.get(firstSelIx);
                f.setY((int) spinnerPos.getValue());
                canvas.paintImage();
            }
        });
        
        btnSetheight.addActionListener(new ActionListener() { //Imposta Altezza
            public void actionPerformed(ActionEvent e) {
                int firstSelIx = list_1.getSelectedIndex();
                Figura f = fig.get(firstSelIx);
                f.setHeight((int) spinnerSizes.getValue());
                canvas.paintImage();
            }
        });
        
        btnSetWidth.addActionListener(new ActionListener() { //Imposta Larghezza
            public void actionPerformed(ActionEvent e) {
                int firstSelIx = list_1.getSelectedIndex();
                fig.get(firstSelIx).setWidth((int) spinnerSizes.getValue());
                canvas.paintImage();
            }
        });
        
        btnRotateRight.addActionListener(new ActionListener() { //Rotate Destra
            public void actionPerformed(ActionEvent e) {
            	
            	//tutto da fare ancora-------
                int firstSelIx = list_1.getSelectedIndex();
                Figura f = fig.get(firstSelIx);
                /*int x=f.getWidth();
                if(f.tipo!="triangolo"){
                    f.setWidth(f.getHeight());
                    f.setHeight(x);
                }else{
                    x=f.getX();
                    f.setX(f.getY());
                    f.setY(x);
                }*/
                
                f.angle=45;
                canvas.paintImage();
                
                //---------------------------
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
            if((int) spinnerPos.getValue() >=0){// se il valore della finestra di input delle posizioni e' positivo
                int x1=(-(int) spinnerPos.getValue());//prendi il valore negativo
                fig.get(firstSelIx).move(x1, 0);  //muovi la figura selezionata del valore x1, y rimangono uguali
            }
            canvas.paintImage();//disegna di nuovo
            
        });
        
        
        //bottone Right
        btnRight.addActionListener(e -> {  //stessa cosa di prima, muovi a destra
        	int firstSelIx = list_1.getSelectedIndex();
            if((int) spinnerPos.getValue() >=0){
            	int x1=(+(int) spinnerPos.getValue());
                fig.get(firstSelIx).move(x1, 0); 
                
            }
            canvas.paintImage();
            
        });
        
        
        //bottone Down
        btnDown.addActionListener(e -> {  //stessa cosa di prima, muovi in giu'
        	int firstSelIx = list_1.getSelectedIndex();
            if((int) spinnerPos.getValue() >=0){
            	int y1=(+(int) spinnerPos.getValue());
                fig.get(firstSelIx).move(0, y1); 
                
            }
            canvas.paintImage();
            
        });
        
        //bottone Up
        btnUp.addActionListener(e -> {   //stessa cosa di prima, muovi in su
        	int firstSelIx = list_1.getSelectedIndex();
            if((int) spinnerPos.getValue() >=0){
            	int y1=(-(int) spinnerPos.getValue());
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
        			System.out.print("dragged\n");
					firstSelIx = list_1.getSelectedIndex();
					int dx = e.getX() - mousePt.x;
					int dy = e.getY() - mousePt.y;
					fig.get(firstSelIx).move(dx, dy);
					mousePt = e.getPoint();
					canvas.paintImage();
				}
        		}        	
        });     
    }
    
    
    private static void setLabel(String string) {
        // TODO Auto-generated method stub
        
    }
}












