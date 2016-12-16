import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;

class MyCanvas extends JComponent {

public int x;
public int y;
	
// Creo il rettangolo di prova
public void paint(Graphics g) { 
    g.drawRect (x, y, 200, 200);  
  }
}

public class Window {
  public static void main(String[] a) {
    int spostX=0, spostY=0; //in percentuali
	  
	JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setBounds(30, 30, 800, 500);
    window.setResizable(false);
    MyCanvas canvas = new MyCanvas();
    canvas.x=window.getWidth()/100*spostX;
    canvas.y=window.getHeight()/100*spostY;
	
	JButton btnLeft = new JButton("Left");
	
	JSpinner spinnerInputPos = new JSpinner();
	
	JButton btnRight = new JButton("Right");
	
	JButton btnDown = new JButton("Down");
	
	JButton btnUp = new JButton("Up");
	
	//creazione layout
	GroupLayout groupLayout = new GroupLayout(window.getContentPane());
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(btnLeft)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnRight)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnUp)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnDown)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(spinnerInputPos, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(412, Short.MAX_VALUE))
			.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(canvas, GroupLayout.PREFERRED_SIZE, 794, GroupLayout.PREFERRED_SIZE)
				.addContainerGap())
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(btnLeft, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnRight)
					.addComponent(spinnerInputPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnUp)
					.addComponent(btnDown))
				.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
				.addComponent(canvas, GroupLayout.PREFERRED_SIZE, 465, GroupLayout.PREFERRED_SIZE)
				.addContainerGap())
	);
	window.getContentPane().setLayout(groupLayout);
    window.setVisible(true);
    
    //bottone Left
    btnLeft.addActionListener(e -> {
    	if((int) spinnerInputPos.getValue() >=0){
    		canvas.x=canvas.x-(int) spinnerInputPos.getValue();
    	}
    	window.repaint();
    
    });
    
    //bottone Right
    btnRight.addActionListener(e -> {
    	if((int) spinnerInputPos.getValue() >=0){
    		canvas.x=canvas.x+(int) spinnerInputPos.getValue();
    	}
    	window.repaint();
    
    });
   
    
  //bottone Down
    btnRight.addActionListener(e -> {
    	if((int) spinnerInputPos.getValue() >=0){
    		canvas.y=canvas.y+(int) spinnerInputPos.getValue();
    	}
    	window.repaint();
    
    });
    
  //bottone Up
    btnRight.addActionListener(e -> {
    	if((int) spinnerInputPos.getValue() >=0){
    		canvas.y=canvas.y-(int) spinnerInputPos.getValue();
    	}
    	window.repaint();
    
    });
    
  }
}