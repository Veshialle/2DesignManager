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
	
	JButton btnNewButton = new JButton("<-->");
	
	JSpinner spinnerInputPos = new JSpinner();
	GroupLayout groupLayout = new GroupLayout(window.getContentPane());
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(canvas, GroupLayout.PREFERRED_SIZE, 794, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(spinnerInputPos, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addComponent(spinnerInputPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(25)
				.addComponent(canvas, GroupLayout.PREFERRED_SIZE, 416, GroupLayout.PREFERRED_SIZE)
				.addContainerGap())
	);
	window.getContentPane().setLayout(groupLayout);
    window.setVisible(true);
    btnNewButton.addActionListener(e -> {
    	canvas.x=canvas.x+(int) spinnerInputPos.getValue();
    	window.repaint();
    
    });
   
    
  }
}