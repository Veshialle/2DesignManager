import java.awt.Cursor;

//import java.awt.Dimension;
//import java.awt.Graphics2D;
import java.awt.Point;
//import java.awt.Polygon;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

//import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JList;
//import javax.swing.JTextField;
//import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
//import javax.swing.JRadioButtonMenuItem;
//import javax.swing.JMenu;
import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.JColorChooser;
import java.awt.Color;

//import java.awt.geom.AffineTransform;
//import java.io.File;
import java.io.IOException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;



import org.xml.sax.SAXException;


public class Main {

	/*
	 * private static ImageIcon loadIcon(String strPath) { URL imgURL =
	 * getClass().getResource(strPath); if(imgURL != null) return new
	 * ImageIcon(imgURL); else return null; }
	 */

	private static Point mousePt;
	private static int firstSelIx;
	private static boolean isInside;
	private static int idFigura = XMLManager.countFigure();
	private static List<Figura> fig = new ArrayList<>(); // fig e' una lista di oggetti Figura


	public static void main(String[] a) {

		GUI_2DM window = new GUI_2DM();
		window.canvas.setArray(fig);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1200,675);
		window.pack();
		window.validate();
		window.setVisible(true);
		DefaultListModel<String> model = new DefaultListModel<>(); // lista di stringhe
		// figura.tipo -> i.tipo
		for (Figura i : fig)
			model.addElement(i.getFinalName()); // aggiungo dentro la lista di stringhe "model" il tipo di figura ->
        window.list1 = new JList<>(model);
        window.scrollPane.setViewportView(window.list1);
		window.canvas.paintImage();



		window.btnSetX.addActionListener(new ActionListener() { // Imposta val X
			public void actionPerformed(ActionEvent e) {
				int firstSelIx = window.list1.getSelectedIndex();
				Figura f = fig.get(firstSelIx);
				f.setX((double) window.spinnerPos.getValue());
				window.canvas.paintImage();
			}
		});

		// Per poter settare la barra dell'angolo quando si seleziona tramite la lista
		// (scrollPane)

		window.list1.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				firstSelIx = window.list1.getSelectedIndex();
				if (!model.isEmpty()) {
					window.btnRemoveFig.setEnabled(true);
					window.btnSaveFig.setEnabled(true);
					window.btnDown.setEnabled(true);
					window.btnUp.setEnabled(true);
					window.btnRight.setEnabled(true);
					window.btnLeft.setEnabled(true);
					window.btnSetX.setEnabled(true);
					window.btnSetY.setEnabled(true);
					window.spinnerPos.setEnabled(true);
					window.radioMove.setEnabled(true);
					window.radioResize.setEnabled(true);
					window.btnColor.setEnabled(true);
					window.btnRotation.setEnabled(true);
					window.rotationBar.setEnabled(true);
					window.rotationBar.setValue((int) fig.get(firstSelIx).getAngle());
					if(model.getSize() >= 2){
						window.btnCompFig.setEnabled(true);
						if(fig.get(firstSelIx).getClass() != Composite.class) window.checkboColorComp.setEnabled(true);
						else window.checkboColorComp.setEnabled(false);
					} else window.btnCompFig.setEnabled(false);
				} else {
					window.btnRemoveFig.setEnabled(false);
					window.btnSaveFig.setEnabled(false);
					window.btnDown.setEnabled(false);
					window.btnUp.setEnabled(false);
					window.btnRight.setEnabled(false);
					window.btnLeft.setEnabled(false);
					window.btnSetX.setEnabled(false);
					window.btnSetY.setEnabled(false);
					window.spinnerPos.setEnabled(false);
					window.radioMove.setEnabled(false);
					window.radioResize.setEnabled(false);
					window.btnColor.setEnabled(false);
					window.btnRotation.setEnabled(false);
					window.rotationBar.setEnabled(false);
				}
			}
		});
		window.btnSetY.addActionListener(new ActionListener() { // Imposta val Y
			public void actionPerformed(ActionEvent e) {
				int firstSelIx = window.list1.getSelectedIndex();
				Figura f = fig.get(firstSelIx);
				f.setY((double) window.spinnerPos.getValue());
				window.canvas.paintImage();
			}
		});
		window.btnRotation.addActionListener(new ActionListener() { // Riportare alla posizione iniziale (0 gradi)
			public void actionPerformed(ActionEvent e) {
				int firstSelIx = window.list1.getSelectedIndex();
				Figura f = fig.get(firstSelIx);
				f.rotate(0.0, f.getCenterX(), f.getCenterY());
				window.rotationBar.setValue((int) f.getAngle());
				window.canvas.paintImage();
			}

		});
		window.rotationBar.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
                JSlider rotation = (JSlider) e.getSource();
				double angle = (double) rotation.getValue();
				Figura f = fig.get(firstSelIx);
				f.rotate(angle, f.getCenterX(), f.getCenterY());
				window.canvas.paintImage();
			}
		});

		window.btnAddFig.addActionListener((ActionEvent e) -> { // Bottone aggiungi/crea una nuova Figura

			// --------------------------------------------------------- Grazie google

			Object[] possibilities = { "rettangolo", "quadrato", "triangolo", "rombo", "cerchio" };
			Component frame = null;
			Icon icon = null;
			String s = (String) JOptionPane.showInputDialog(frame, "Select fig type:\n", "Customized Dialog",
					JOptionPane.PLAIN_MESSAGE, icon, possibilities, "rettangolo");

			// If a string was returned, say so.
			if ((s != null) && (s.length() > 0)) {
				String name = JOptionPane.showInputDialog("Inserire nome della figura.", s);
				Figura f;
				if (name != null && name.length() > 0) {
					if (s == "cerchio")
						f = new Circle(name, s, idFigura, 200, 200, 200, 100); // inizializzo/creo la Figura(s=tipo di
																				// figura, x=0, y=0, larghezza,
																				// altezza);
					else
						f = new Polygon(name, s, idFigura, 200, 200, 200, 100); // inizializzo/creo la Figura(s=tipo di
																				// figura, x=0, y=0, larghezza,
																				// altezza);

					fig.add(f); // aggiungi la figura appena creata f alla lista di Figure "fig"
					window.canvas.paintImage(); // disegna

					model.addElement(f.getFinalName()); // aggiungi alla lista di stringhe il tipo della nuova figura
					window.list1.setSelectedIndex(model.getSize() - 1);// seleziona nella lista output l'ultima figura
																	// inserita
					firstSelIx = window.list1.getSelectedIndex();
					idFigura++;
					window.rotationBar.setEnabled(!(fig.isEmpty()) || (fig.get(firstSelIx).getClass() != Circle.class));
					window.rotationBar.setValue((int) fig.get(firstSelIx).getAngle());
					if (!(model.isEmpty()))
						window.btnRemoveFig.setEnabled(true);
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Hai annullato la creazione di una nuova figura");
			}
		});

		window.btnRemoveFig.addActionListener(e -> { // Bottone rimuovi Figura

			int firstSelIx = window.list1.getSelectedIndex(); // dammi l'indice della figura selezionata
			fig.remove(firstSelIx); // rimuovi dalla lista di figure la figura con l'indice appena ricavato
			model.remove(firstSelIx);// rimuovi la stringa della figura nella lista dei tipi
			window.scrollPane.validate();
			window.canvas.paintImage();// disegna di nuovo
			window.list1.setSelectedIndex(model.getSize() - 1);
			boolean enableRotationbar = true;
			firstSelIx = window.list1.getSelectedIndex();

			if (firstSelIx < 0) {
				enableRotationbar = false;
			}
			window.rotationBar.setEnabled(enableRotationbar);

			if (enableRotationbar) {
				window.rotationBar.setValue((int) fig.get(firstSelIx).getAngle());
			}
			if (model.isEmpty())
				window.btnRemoveFig.setEnabled(false);

		});

		// bottone scelta colore

		window.btnColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
				fig.get(firstSelIx).setColor(newColor);
				window.canvas.paintImage();
			}
		});





		// bottone Left
		window.btnLeft.addActionListener(e -> { // bottone muovi a Sinistra
			int firstSelIx = window.list1.getSelectedIndex(); // dammi l'indice della figura selezionata nella finestra di
														// figure
			if ((double) window.spinnerPos.getValue() >= 0) {// se il valore della finestra di input delle posizioni e'
														// positivo
				double x1 = (-(double) window.spinnerPos.getValue());// prendi il valore negativo
				fig.get(firstSelIx).move(x1, 0); // muovi la figura selezionata del valore x1, y rimangono uguali
			}
			window.canvas.paintImage();// disegna di nuovo

		});

		// bottone Right
		window.btnRight.addActionListener(e -> { // stessa cosa di prima, muovi a destra
			int firstSelIx = window.list1.getSelectedIndex();
			if ((double) window.spinnerPos.getValue() >= 0) {
				double x1 = (+(double) window.spinnerPos.getValue());
				fig.get(firstSelIx).move(x1, 0);

			}
			window.canvas.paintImage();

		});

		// bottone Down
		window.btnDown.addActionListener(e -> { // stessa cosa di prima, muovi in giu'
			int firstSelIx = window.list1.getSelectedIndex();
			if ((double) window.spinnerPos.getValue() >= 0) {
				double y1 = (+(double) window.spinnerPos.getValue());
				fig.get(firstSelIx).move(0, y1);

			}
			window.canvas.paintImage();

		});

		// bottone Up
		window.btnUp.addActionListener(e -> { // stessa cosa di prima, muovi in su
			int firstSelIx = window.list1.getSelectedIndex();
			if ((double) window.spinnerPos.getValue() >= 0) {
				double y1 = (-(double) window.spinnerPos.getValue());
				fig.get(firstSelIx).move(0, y1);

			}
			window.canvas.paintImage();

		});
		// bottone Save
		window.btnSaveFig.addActionListener(e -> {
			int firstSelIx = window.list1.getSelectedIndex();
			Component frame = null;
			if (fig.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Error, first create or load a figure\n");
			} else {
				try {
					XMLManager.savePolygon(fig.get(firstSelIx));
					JOptionPane.showMessageDialog(frame, "Figure saved!");
				} catch (ParserConfigurationException | IOException | SAXException | TransformerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Error saving the Figure!");
				}
			}
		});

		window.btnLoadFig.addActionListener(e -> {
			List<Figura> loadFig = new ArrayList<Figura>();
			int L;
			int loadSelIx = 0;
			try {
				loadFig = XMLManager.loadPolygon();
			} catch (ParserConfigurationException | SAXException | IOException all) {
				// TODO Auto-generated catch block
				all.printStackTrace();
			}
			if (loadFig == null) {
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "Error, first save a figure");
			} else {
				L = loadFig.size();
				Object[] loadedChoose = new Object[L];
				for (int i = 0; i < L; i++) {
					loadedChoose[i] = "Versione numero: " + loadFig.get(i).getVersione();
					// System.out.println( "Prendo il primo punto della figura " + i + " versione :"
					// + loadFig.get(i).getVersione() + " per GUI_2DM: " + loadFig.get(i).getxPoint(0)+
					// " , "+ loadFig.get(i).getyPoint(0)+ " !");
				}
				Component frame = null;
				Icon icon = null;
				String choosed = null;
				choosed = (String) JOptionPane.showInputDialog(frame, "Select figure:\n", "Load",
						JOptionPane.PLAIN_MESSAGE, icon, loadedChoose, loadedChoose[0]);

				// If a string was returned, say so.
				if ((choosed != null) && (choosed.length() > 0)) {
					// System.out.println(choosed);
					String[] parts = choosed.split(": ");
					Float versione = Float.valueOf(parts[1]);
					for (int i = 0; i < L; i++) {
						if (Float.compare(versione, loadFig.get(i).getVersione()) == 0) {
							loadSelIx = i;
						}
					}
					/*
					 * GUI_2DM System.out.println("Verra' caricata la versione " +
					 * loadFig.get(loadSelIx).getVersione() +" della figura: " +
					 * loadFig.get(loadSelIx).getName() + "!"); System.out.println(
					 * "Prendo il primo punto per GUI_2DM: " + loadFig.get(loadSelIx).getxPoint(0)+
					 * " , "+ loadFig.get(loadSelIx).getyPoint(0)+ " !");
					 */
					fig.add(loadFig.get(loadSelIx)); // aggiungi la figura appena creata f alla lista di Figure "fig"

					window.canvas.paintImage(); // disegna

					model.addElement(loadFig.get(loadSelIx).getFinalName()); // aggiungi alla lista di stringhe il tipo
																				// della nuova figura
					window.list1.setSelectedIndex(model.getSize() - 1);// seleziona nella lista output l'ultima figura
																	// inserita
					firstSelIx = window.list1.getSelectedIndex();
					idFigura++;
					window.rotationBar.setValue((int) fig.get(firstSelIx).getAngle());
				} else {
					JOptionPane.showMessageDialog(frame, "Hai annullato il caricamento di una nuova figura");
				}
				if (!(model.isEmpty()))
					window.btnRemoveFig.setEnabled(true);
			}
		});
		// mouse listener
		window.canvas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		window.canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mousePt = e.getPoint();
				for (int i = fig.size() - 1; i >= 0; i--) {
					if (fig.get(i).contains(mousePt)) {//
						isInside = true;
						window.list1.setSelectedIndex(i);

						firstSelIx = window.list1.getSelectedIndex();
						/*
						if (fig.get(i).getClass() != Circle.class) {
							window.rotationBar.setEnabled(true);
							window.btnRotation.setEnabled(true);
							window.rotationBar.setValue((int) fig.get(firstSelIx).getAngle());
						} else {
							window.rotationBar.setEnabled(false);
							window.btnRotation.setEnabled(false);
						}
						*/
						break;
					} else {
						isInside = false;
					}
				}
				window.canvas.paintImage();
			}
		});
		window.canvas.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if (isInside) {
					firstSelIx = window.list1.getSelectedIndex();
					double dx = e.getX() - mousePt.x;
					double dy = e.getY() - mousePt.y;
					if (window.radioResize.isSelected()) {
						double xVar = (e.getX() - mousePt.x) / (fig.get(firstSelIx).getCenterX() - mousePt.x);
						double scaleX = (-xVar * 8 / 10) + 1.0; // era troppo veloce a ingrandire
						fig.get(firstSelIx).resize(scaleX);
					} else {
						fig.get(firstSelIx).move(dx, dy);
					}
					window.canvas.paintImage();
					mousePt = e.getPoint();
				}
			}
		});

		window.checkGrid.addActionListener(e -> {
			window.canvas.setGridFlag(window.checkGrid.isSelected());
			window.canvas.paintImage();
		});

		window.canvas.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				window.canvas.paintImage();
			}
		});

		window.btnCompFig.addActionListener(e ->{
			Component frame = null;
			Icon icon = null;
			String choosed = null;

			if (JOptionPane.showConfirmDialog(frame, "Are You sure to join the figures in the canvas?")
					== JOptionPane.YES_OPTION){
				String name = JOptionPane.showInputDialog("Inserire nome della figura.", fig.get(firstSelIx).getName());
				Figura f;
				f = new Composite(fig, true, name);
				for(Figura i : fig){
					firstSelIx = window.list1.getSelectedIndex(); // dammi l'indice della figura selezionata
					fig.remove(firstSelIx); // rimuovi dalla lista di figure la figura con l'indice appena ricavato
					model.remove(firstSelIx);// rimuovi la stringa della figura nella lista
					window.list1.setSelectedIndex(model.getSize() - 1);
					firstSelIx = window.list1.getSelectedIndex();
					window.rotationBar.setValue((int) fig.get(firstSelIx).getAngle());
				}

				window.scrollPane.validate();
				fig.add(f); // aggiungi la figura appena creata f alla lista di Figure "fig"
				model.addElement(f.getFinalName()); // aggiungi alla lista di stringhe il tipo della nuova figura
				window.list1.setSelectedIndex(model.getSize() - 1);// seleziona nella lista output l'ultima figura
				// inserita
				firstSelIx = window.list1.getSelectedIndex();
				idFigura++;
				window.rotationBar.setEnabled(!(fig.isEmpty()) || (fig.get(firstSelIx).getClass() != Circle.class));
				window.rotationBar.setValue((int) fig.get(firstSelIx).getAngle());
				window.canvas.paintImage();
			}

		});


	}
}