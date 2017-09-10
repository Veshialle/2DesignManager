import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//import java.awt.Dimension;
//import java.awt.Graphics2D;
//import java.awt.Polygon;
//import java.awt.BorderLayout;
//import javax.swing.JTextField;
//import javax.swing.JMenuBar;
//import javax.swing.JRadioButtonMenuItem;
//import javax.swing.JMenu;
//import java.awt.geom.AffineTransform;
//import java.io.File;


public class Main {

	/*
	 * private static ImageIcon loadIcon(String strPath) { URL imgURL =
	 * getClass().getResource(strPath); if(imgURL != null) return new
	 * ImageIcon(imgURL); else return null; }
	 */
	private static Point mousePt;
	private static boolean isInside;
	private static int idFigura = (Manager.countFigure()-1)/2;
	private static List<Figura> fig = new ArrayList<>(); // fig e' una lista di oggetti Figura



	public static void main(String[] a) {

		GUI_2DM window = new GUI_2DM();
		window.canvas.setArray(fig);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1200,675);
		window.pack();
		window.validate();
		window.setVisible(true);
		DefaultListModel<Figura> model = new DefaultListModel<>(); // lista di stringhe
		// figura.tipo -> i.tipo
		for (Figura i : fig)
			model.addElement(i); // aggiungo dentro la lista di stringhe "model" il tipo di figura ->
        window.list1 = new JList<>(model);
        window.scrollPane.setViewportView(window.list1);
		window.canvas.paintImage();






		// Per poter settare la barra dell'angolo quando si seleziona tramite la lista
		// (scrollPane)


		window.list1.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				if (window.list1.getSelectedIndex() >= 0) {
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
					window.rotationBar.setValue((int) fig.get(window.list1.getSelectedIndex()).getAngle());
					if(window.list1.getSelectedIndices().length >= 2){
						window.btnCompFig.setEnabled(true);
						System.out.println(Composite.class);
						System.out.println(fig.get(window.list1.getSelectedIndex()).getClass());
					} else window.btnCompFig.setEnabled(false);
					if(fig.get(window.list1.getSelectedIndex()).getClass() == Composite.class) {
						window.checkboColorComp.setEnabled(true);
						window.checkboColorComp.setSelected(fig.get(window.list1.getSelectedIndex()).isUnited());
					} else window.checkboColorComp.setEnabled(false);
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
					window.rotationBar.setValue(0);
					window.rotationBar.setEnabled(false);
				}
			}
		});


		window.btnSetY.addActionListener(new ActionListener() { // Imposta val Y
			public void actionPerformed(ActionEvent e) {
				Figura f = fig.get(window.list1.getSelectedIndex());
				f.setY(Double.valueOf((int)window.spinnerPos.getValue()));
				window.canvas.paintImage();
			}
		});

		window.btnSetX.addActionListener(new ActionListener() { // Imposta val X
			public void actionPerformed(ActionEvent e) {
				Figura f = fig.get(window.list1.getSelectedIndex());
				f.setX(Double.valueOf((int)window.spinnerPos.getValue()));
				window.canvas.paintImage();
			}
		});

		window.btnRotation.addActionListener(new ActionListener() { // Riportare alla posizione iniziale (0 gradi)
			public void actionPerformed(ActionEvent e) {
				Figura f = fig.get(window.list1.getSelectedIndex());
				f.rotate(0.0, f.getCenterX(), f.getCenterY());
				window.rotationBar.setValue((int) f.getAngle());
				window.canvas.paintImage();
			}

		});

		window.rotationBar.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(!model.isEmpty()) {
					JSlider rotation = (JSlider) e.getSource();
					double angle = (double) rotation.getValue();
					Figura f = fig.get(window.list1.getSelectedIndex());
					f.rotate(angle, f.getCenterX(), f.getCenterY());
					window.canvas.paintImage();
				}
			}
		});

		window.btnAddFig.addActionListener((ActionEvent e) -> { // Bottone aggiungi/crea una nuova Figura

			// --------------------------------------------------------- Grazie google

			//Object[] possibilities = { "3", "4", "5", "6", "7", "8", "9", "10", "0" };
			Component frame = null;
			Icon icon = null;
			String s = JOptionPane.showInputDialog("Inserire numero di lati del poligono.", "3");
			int nLati = Integer.valueOf(s);

			// If a string was returned, say so.
			if ((s != null) && (s.length() > 0) && (nLati == 0 || nLati > 2)) {

				String name = JOptionPane.showInputDialog("Inserire nome della figura.", s);
				Figura f;
				if (name != null && name.length() > 0) {
					if (nLati == 0) {
						//f = new Circle(name, nLati, idFigura, 200, 200, 200, 100); // inizializzo/creo la Figura(s=tipo di
						// figura, x=0, y=0, larghezza,
						// altezza);
						nLati = 450;
					}

						f = new Polygon(name, nLati, idFigura, 200, 200, 200, 100); // inizializzo/creo la Figura(s=tipo di
																				// figura, x=0, y=0, larghezza,
																				// altezza);

					fig.add(f); // aggiungi la figura appena creata f alla lista di Figure "fig"
					window.canvas.paintImage(); // disegna
					model.addElement(f); // aggiungi alla lista di stringhe il tipo della nuova figura
					// inserita
					window.list1.setSelectedIndex(model.getSize() - 1);// seleziona nella lista output l'ultima figura
					window.scrollPane.validate();
					idFigura++;
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Hai annullato la creazione di una nuova figura");
			}
		});

		window.btnRemoveFig.addActionListener(e -> { // Bottone rimuovi Figura
			// dammi l'indice della figura selezionata

			System.out.print(fig.get(window.list1.getSelectedIndex()).getName());
			//model.remove(window.list1.getSelectedIndex());// rimuovi la stringa della figura nella lista dei tipi
			int removeFig = window.list1.getSelectedIndex();
			model.removeElement(fig.get(window.list1.getSelectedIndex()));
			fig.remove(removeFig); // rimuovi dalla lista di figure la figura con l'indice appena ricavato
			window.scrollPane.revalidate();
			window.list1.setSelectedIndex(model.getSize() - 1);
			window.canvas.paintImage();// disegna di nuovo
			if(!model.isEmpty()) window.list1.setSelectedIndex(model.getSize() - 1);

		});

		// bottone scelta colore

		window.btnColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
				fig.get(window.list1.getSelectedIndex()).setColor(newColor);
				window.canvas.paintImage();
			}
		});





		// bottone Left
		window.btnLeft.addActionListener(e -> { // bottone muovi a Sinistra
			// dammi l'indice della figura selezionata nella finestra di
														// figure
			if (!window.spinnerPos.getValue().equals(0)) {// se il valore della finestra di input delle posizioni e'
														// positivo
				double x1 = (Integer)window.spinnerPos.getValue();
				//double x1 = (-(double) window.spinnerPos.getValue());// prendi il valore negativo
				fig.get(window.list1.getSelectedIndex()).move(-x1, 0); // muovi la figura selezionata del valore x1, y rimangono uguali
			}
			window.canvas.paintImage();// disegna di nuovo

		});

		// bottone Right
		window.btnRight.addActionListener(e -> { // stessa cosa di prima, muovi a destra
			if (!window.spinnerPos.getValue().equals(0)) {
				double x1 = (+(Integer) window.spinnerPos.getValue());
				fig.get(window.list1.getSelectedIndex()).move(x1, 0);

			}
			window.canvas.paintImage();

		});

		// bottone Down
		window.btnDown.addActionListener(e -> { // stessa cosa di prima, muovi in giu'
			if ( !window.spinnerPos.getValue().equals(0)) {
				double y1 = (+(Integer) window.spinnerPos.getValue());
				fig.get(window.list1.getSelectedIndex()).move(0, y1);

			}
			window.canvas.paintImage();

		});

		// bottone Up
		window.btnUp.addActionListener(e -> { // stessa cosa di prima, muovi in su
			if (!window.spinnerPos.getValue().equals(0)) {
				double y1 = (-(Integer) window.spinnerPos.getValue());
				fig.get(window.list1.getSelectedIndex()).move(0, y1);

			}
			window.canvas.paintImage();

		});
		// bottone Save
		window.btnSaveFig.addActionListener(e -> {
			Component frame = null;
			if (fig.isEmpty()) {
				JOptionPane.showMessageDialog(frame, "Error, first create or load a figure\n");
			} else {
				try {
					//XMLManager.savePolygon(fig.get(window.list1.getSelectedIndex()));
					if(JOptionPane.showConfirmDialog(null, "Do you want to take a note of the Figure saved?") == JOptionPane.YES_OPTION){
						TakeNote note = new TakeNote();
						note.pack();
						note.validate();
						note.setVisible(true);
						note.btnSaveNote.addActionListener(acl ->{
							if(note.textArea1.getText().isEmpty()){
								JOptionPane.showMessageDialog(null, "Note is empty");
							} else {
								fig.get(window.list1.getSelectedIndex()).setDescription(note.textArea1.getText());
								note.setVisible(false);
								JOptionPane.showMessageDialog(frame, "Figure saved!");
							}
						});
					}
					Manager.saveObj(fig.get(window.list1.getSelectedIndex()));
				} catch ( IOException  e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Error saving the Figure!");
				}
			}
		});

		window.btnDB.addActionListener(e -> {
			Manager m = new Manager();
			DefaultTableModel modelElement;
			String[] columns = {"Name", "Version", "Class", "Number of Fields", "File Name", "Description"};
			modelElement = new DefaultTableModel(m.loadModelDB(), columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			DB table = new DB();
			table.tableDB.setModel(modelElement);
			table.setSize(980, 465);
			table.pack();
			table.validate();
			table.setVisible(true);
			table.setLocationRelativeTo(null);
			table.btnLoad.addActionListener(acl -> {
				if(table.tableDB.getSelectedRow() < 0){
					JOptionPane.showMessageDialog(null, "Select a Figure to load");
				} else {
					try {
						Figura loadedFig = m.loadObj(table.tableDB.getSelectedRow());
						fig.add(loadedFig); // aggiungi la figura appena creata f alla lista di Figure "fig"
						window.canvas.paintImage(); // disegna
						model.addElement(loadedFig); // aggiungi alla lista di stringhe il tipo della nuova figura
						// inserita
						window.list1.setSelectedIndex(model.getSize() - 1);// seleziona nella lista output l'ultima figura
						window.scrollPane.validate();
						idFigura++;
						table.setVisible(false);

					} catch (IOException ioe){
						ioe.printStackTrace();
					}
				}
			});
			table.btnRemove.addActionListener(acl ->{
				if(table.tableDB.getSelectedRow() < 0){
					JOptionPane.showMessageDialog(null, "Select a Figure to remove");
				} else {
					m.rmvFig(table.tableDB.getSelectedRow());
				}
			});

			table.btnDescription.addActionListener(acl ->{
				if(table.tableDB.getSelectedRow() < 0){
					JOptionPane.showMessageDialog(null, "Select a Figure to show description");
				} else {
					TakeNote note = new TakeNote();
					note.btnSaveNote.setText("Change");
					note.textArea1.setText(m.getStringDescription(table.tableDB.getSelectedRow()));
					note.pack();
					note.validate();
					note.setVisible(true);
					note.btnSaveNote.addActionListener(last ->{
						if(note.textArea1.getText().isEmpty()){
							JOptionPane.showMessageDialog(null, "Note is empty");
						} else {
							fig.get(window.list1.getSelectedIndex()).setDescription(note.textArea1.getText());
						}
						note.setVisible(false);;
					});
					/*
					if(JOptionPane.showConfirmDialog(null, m.getStringDescription(table.tableDB.getSelectedRow()) + "\n" + "Modificare?")== JOptionPane.YES_OPTION){
						TakeNote note = new TakeNote();

					}
					*/
				}
			});

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

						/*
						if (fig.get(i).getClass() != Circle.class) {
							window.rotationBar.setEnabled(true);
							window.btnRotation.setEnabled(true);
							window.rotationBar.setValue((int) fig.get(window.list1.getSelectedIndex()).getAngle());
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
					double dx = e.getX() - mousePt.x;
					double dy = e.getY() - mousePt.y;
					if (window.radioResize.isSelected()) {
						double xVar = (e.getX() - mousePt.x) / (fig.get(window.list1.getSelectedIndex()).getCenterX() - mousePt.x);
						double scaleX = (-xVar * 8 / 10) + 1.0; // era troppo veloce a ingrandire
						double yVar = (e.getY() - mousePt.y) / (fig.get(window.list1.getSelectedIndex()).getCenterY() - mousePt.y);
						double scaleY = (-yVar * 8 / 10) + 1.0; // era troppo veloce a ingrandire
						fig.get(window.list1.getSelectedIndex()).resize(scaleX, scaleX, fig.get(window.list1.getSelectedIndex()).getCenterX(), fig.get(window.list1.getSelectedIndex()).getCenterY());
					} else {
						fig.get(window.list1.getSelectedIndex()).move(dx, dy);
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
			List<Figura> composition = new ArrayList<>();
			if (JOptionPane.showConfirmDialog(frame, "Are You sure to join figures selected?")
					== JOptionPane.YES_OPTION){
				String name = JOptionPane.showInputDialog("Inserire nome della figura.", fig.get(window.list1.getSelectedIndex()).getName());
				for(int k : window.list1.getSelectedIndices()) composition.add(fig.get(k));
				Figura f;
				f = new Composite(composition, true, name);
				window.list1.getSelectedIndices();
				for(Figura i : composition){
					fig.remove(window.list1.getSelectedIndex()); // rimuovi dalla lista di figure la figura con l'indice appena ricavato
					model.remove(window.list1.getSelectedIndex());// rimuovi la stringa della figura nella lista
					window.list1.setSelectedIndex(model.getSize() - 1);
				}

				window.scrollPane.validate();
				fig.add(f); // aggiungi la figura appena creata f alla lista di Figure "fig"
				model.addElement(f); // aggiungi alla lista di stringhe il tipo della nuova figura
				window.list1.setSelectedIndex(model.getSize() - 1);// seleziona nella lista output l'ultima figura
				window.scrollPane.validate();
				// inserita

				idFigura++;
				window.rotationBar.setEnabled(!(fig.isEmpty()));
				window.rotationBar.setValue((int) fig.get(window.list1.getSelectedIndex()).getAngle());
				window.canvas.paintImage();
			}

		});

		window.checkboColorComp.addActionListener(e -> {
			fig.get(window.list1.getSelectedIndex()).setUnited(!fig.get(window.list1.getSelectedIndex()).isUnited());
		});


	}
}