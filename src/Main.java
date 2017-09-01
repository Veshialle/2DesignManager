import java.awt.Cursor;

//import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
//import java.awt.Graphics2D;
import java.awt.Point;
//import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JButton;

//import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
//import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;
//import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
//import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
//import javax.swing.JMenu;
import javax.swing.JList;
import java.awt.Toolkit;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.geom.AffineTransform;
//import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import javax.swing.JScrollBar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

//INIZIO CLASSE MYCANVAS
class MyCanvas extends JComponent {

	/**
	 * mi dava troppo fastidio vedere giallo
	 */
	private static final long serialVersionUID = -943538260601289904L;
	private int x, y;
	private List<Figura> array;

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public MyCanvas(List<Figura> fig) {
		this.array = fig;
	}

	public void paintImage() {
		Graphics d = getGraphics();
		d.clearRect(0, 0, getWidth(), getHeight()); // pulisce il canvas
		for (Figura i : array) { // per ogni figura nella lista di figure
			if (i.visibile == true) {// se la figura e' visibile
				i.draw(d);// chiama il metodo "disegna" della figura
			}
		}
		this.validate();
	}
}
// FINE CLASSE MYCANVAS

// INIZIO CLASSE WINDOW (MAIN)
public class Main {

	/*
	 * private static ImageIcon loadIcon(String strPath) { URL imgURL =
	 * getClass().getResource(strPath); if(imgURL != null) return new
	 * ImageIcon(imgURL); else return null; }
	 */

	public static Point mousePt;
	public static int firstSelIx;
	public static boolean isInside;
	public static boolean Resize = false;
	public static int idFigura = XMLManager.countFigure();

	static Image icon = Toolkit.getDefaultToolkit().getImage("save/2DMIcon.png");

	public static void main(String[] a) {
		/*
		 * Sleak sleak = new Sleak(); sleak.open();
		 */
		List<Figura> fig = new ArrayList<Figura>(); // fig e' una lista di oggetti Figura

		JFrame window = new JFrame("2DesignManager"); // window sara' la mia finestra principale
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(30, 30, 1200, 1200);
		window.setResizable(true);
		if (icon != null)
			window.setIconImage(icon);
		MyCanvas canvas = new MyCanvas(fig); // canvas sara' lo spazio dove disegno le figure

		JButton btnLeft = new JButton("Left"); // Bottone "Left"
		btnLeft.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		JSpinner spinnerPos = new JSpinner(); // Finestrina Input spostamenti, a destra del bottone "Right"
		spinnerPos.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		JButton btnRight = new JButton("Right");// Bottone "Right"
		btnRight.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		JButton btnDown = new JButton("Down");// Bottone "Down"
		btnDown.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		JButton btnUp = new JButton("Up");// Bottone "Up"
		btnUp.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		DefaultListModel<String> model = new DefaultListModel<>(); // lista di stringhe
		for (Figura i : fig) {
			model.addElement(i.getFinalName()); // aggiungo dentro la lista di stringhe "model" il tipo di figura ->
												// figura.tipo -> i.tipo
		}

		JScrollPane scrollPane = new JScrollPane(); // Panello dove verra' visualizzata la lista degli oggetti presenti
		JButton btnAddFig = new JButton("+Fig"); // Bottone aggiungi Figura
		btnAddFig.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		JButton btnRemoveFig = new JButton("-Fig");// Bottone rimuovi Figura
		btnRemoveFig.setEnabled(false);
		btnRemoveFig.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		JButton btnSetX = new JButton("Set X");// Bottone set X
		btnSetX.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		JButton btnSetY = new JButton("Set Y");// Bottone set Y
		btnSetY.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		JButton btnSetWidth = new JButton("Enable Resizing");// bottone set width (larghezza)
		btnSetWidth.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		JButton btnRotation = new JButton("Zero Degree");
		btnRotation.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

		JScrollBar rotationBar = new JScrollBar();
		rotationBar.setEnabled(false);
		rotationBar.setMinimum(-180);
		rotationBar.setMaximum(190); // non capisco come mai, ma settando a 180 (come dovrebbe essere) arriva solo
										// fino a 170
		rotationBar.setOrientation(JScrollBar.HORIZONTAL);

		JButton btnSave = new JButton("Save Figure");
		btnSave.setFont(new Font("Dialog", Font.PLAIN, 10));

		JButton btnLoad = new JButton("Load Figure");
		btnLoad.setFont(new Font("Dialog", Font.PLAIN, 10));

		// creazione layout... come sono disposte le finestre/bottoni
		// autogenerata
		// ignorare
		GroupLayout groupLayout = new GroupLayout(window.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup().addContainerGap()
						.addComponent(btnLeft, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnUp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(btnDown, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnRight)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(spinnerPos, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnSetY, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(btnSetX, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(btnAddFig)
								.addComponent(btnRemoveFig))
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnSetWidth)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(62).addComponent(btnRotation)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSave)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnLoad))
								.addComponent(rotationBar, GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)))
						.addComponent(canvas, GroupLayout.PREFERRED_SIZE, 1196, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
								.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addContainerGap()
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(btnRotation).addComponent(btnSave).addComponent(btnLoad))
										.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
										.addComponent(rotationBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup().addGap(9).addGroup(groupLayout
										.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(scrollPane, 0, 0, Short.MAX_VALUE)
												.addGroup(groupLayout.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(btnAddFig)
														.addPreferredGap(ComponentPlacement.RELATED, 18,
																Short.MAX_VALUE)
														.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
																.addComponent(btnRemoveFig).addComponent(btnSetWidth)))
												.addGroup(groupLayout.createSequentialGroup().addComponent(btnSetX)
														.addPreferredGap(ComponentPlacement.RELATED, 18,
																Short.MAX_VALUE)
														.addComponent(btnSetY)))
										.addGroup(groupLayout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnUp)
												.addGap(18).addComponent(btnDown)))))
								.addGap(42))
						.addGroup(groupLayout.createSequentialGroup().addGap(30)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnLeft)
										.addComponent(btnRight).addComponent(spinnerPos, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(59)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(canvas, GroupLayout.PREFERRED_SIZE, 776, GroupLayout.PREFERRED_SIZE).addGap(286)));
		// fine impostazione layout

		JList<String> list_1 = new JList<>(model); // una JList che prende la lista di stringhe model
		scrollPane.setViewportView(list_1); // passo la JList al pannello di visualizzazione
		window.getContentPane().setLayout(groupLayout);

		window.setVisible(true);
		canvas.paintImage();

		btnSetX.addActionListener(new ActionListener() { // Imposta val X
			public void actionPerformed(ActionEvent e) {
				int firstSelIx = list_1.getSelectedIndex();
				Figura f = fig.get(firstSelIx);
				f.setX((double) spinnerPos.getValue());
				canvas.paintImage();
			}
		});

		// Per poter settare la barra dell'angolo quando si seleziona tramite la lista
		// (scrollPane)

		list_1.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				firstSelIx = list_1.getSelectedIndex();
				if (firstSelIx >= 0) {
					rotationBar.setEnabled(true);
					rotationBar.setValue((int) fig.get(firstSelIx).getAngle());
				}
			}
		});
		btnSetY.addActionListener(new ActionListener() { // Imposta val Y
			public void actionPerformed(ActionEvent e) {
				int firstSelIx = list_1.getSelectedIndex();
				Figura f = fig.get(firstSelIx);
				f.setY((double) spinnerPos.getValue());
				canvas.paintImage();
			}
		});

		btnSetWidth.addActionListener(new ActionListener() { // Imposta Larghezza
			public void actionPerformed(ActionEvent e) {
				// int firstSelIx = list_1.getSelectedIndex();

				canvas.paintImage();
				Resize = (!Resize);
				if (Resize)
					btnSetWidth.setText("Disable Resizing");
				else
					btnSetWidth.setText("Enable Resizing");

			}
		});

		btnRotation.addActionListener(new ActionListener() { // Riportare alla posizione iniziale (0 gradi)
			public void actionPerformed(ActionEvent e) {
				int firstSelIx = list_1.getSelectedIndex();
				Figura f = fig.get(firstSelIx);
				f.rotate(0.0, f.getCenterX(), f.getCenterY());
				rotationBar.setValue((int) f.getAngle());
				canvas.paintImage();
			}

		});

		rotationBar.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				double angle = (double) e.getValue();
				Figura f = fig.get(firstSelIx);
				f.rotate(angle, f.getCenterX(), f.getCenterY());
				canvas.paintImage();
			}
		});

		btnAddFig.addActionListener(e -> { // Bottone aggiungi/crea una nuova Figura

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
					canvas.paintImage(); // disegna

					model.addElement(f.getFinalName()); // aggiungi alla lista di stringhe il tipo della nuova figura
					list_1.setSelectedIndex(model.getSize() - 1);// seleziona nella lista output l'ultima figura
																	// inserita
					firstSelIx = list_1.getSelectedIndex();
					idFigura++;
					rotationBar.setEnabled(!(fig.isEmpty()) || (fig.get(firstSelIx).getClass() != Circle.class));
					rotationBar.setValue((int) fig.get(firstSelIx).getAngle());
					if (!(model.isEmpty()))
						btnRemoveFig.setEnabled(true);
				}
			} else {
				JOptionPane.showMessageDialog(frame, "Hai annullato la creazione di una nuova figura");
			}
		});

		btnRemoveFig.addActionListener(e -> { // Bottone rimuovi Figura

			int firstSelIx = list_1.getSelectedIndex(); // dammi l'indice della figura selezionata
			fig.remove(firstSelIx); // rimuovi dalla lista di figure la figura con l'indice appena ricavato
			model.remove(firstSelIx);// rimuovi la stringa della figura nella lista dei tipi
			scrollPane.validate();
			canvas.paintImage();// disegna di nuovo
			list_1.setSelectedIndex(model.getSize() - 1);
			boolean enableRotationbar = true;
			firstSelIx = list_1.getSelectedIndex();

			if (firstSelIx < 0) {
				enableRotationbar = false;
			}
			rotationBar.setEnabled(enableRotationbar);

			if (enableRotationbar) {
				rotationBar.setValue((int) fig.get(firstSelIx).getAngle());
			}
			if (model.isEmpty())
				btnRemoveFig.setEnabled(false);

		});

		// bottone Left
		btnLeft.addActionListener(e -> { // bottone muovi a Sinistra
			int firstSelIx = list_1.getSelectedIndex(); // dammi l'indice della figura selezionata nella finestra di
														// figure
			if ((double) spinnerPos.getValue() >= 0) {// se il valore della finestra di input delle posizioni e'
														// positivo
				double x1 = (-(double) spinnerPos.getValue());// prendi il valore negativo
				fig.get(firstSelIx).move(x1, 0); // muovi la figura selezionata del valore x1, y rimangono uguali
			}
			canvas.paintImage();// disegna di nuovo

		});

		// bottone Right
		btnRight.addActionListener(e -> { // stessa cosa di prima, muovi a destra
			int firstSelIx = list_1.getSelectedIndex();
			if ((double) spinnerPos.getValue() >= 0) {
				double x1 = (+(double) spinnerPos.getValue());
				fig.get(firstSelIx).move(x1, 0);

			}
			canvas.paintImage();

		});

		// bottone Down
		btnDown.addActionListener(e -> { // stessa cosa di prima, muovi in giu'
			int firstSelIx = list_1.getSelectedIndex();
			if ((double) spinnerPos.getValue() >= 0) {
				double y1 = (+(double) spinnerPos.getValue());
				fig.get(firstSelIx).move(0, y1);

			}
			canvas.paintImage();

		});

		// bottone Up
		btnUp.addActionListener(e -> { // stessa cosa di prima, muovi in su
			int firstSelIx = list_1.getSelectedIndex();
			if ((double) spinnerPos.getValue() >= 0) {
				double y1 = (-(double) spinnerPos.getValue());
				fig.get(firstSelIx).move(0, y1);

			}
			canvas.paintImage();

		});
		// bottone Save
		btnSave.addActionListener(e -> {
			int firstSelIx = list_1.getSelectedIndex();
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

		btnLoad.addActionListener(e -> {
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
					// + loadFig.get(i).getVersione() + " per test: " + loadFig.get(i).getxPoint(0)+
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
					 * test System.out.println("Verra' caricata la versione " +
					 * loadFig.get(loadSelIx).getVersione() +" della figura: " +
					 * loadFig.get(loadSelIx).getName() + "!"); System.out.println(
					 * "Prendo il primo punto per test: " + loadFig.get(loadSelIx).getxPoint(0)+
					 * " , "+ loadFig.get(loadSelIx).getyPoint(0)+ " !");
					 */
					fig.add(loadFig.get(loadSelIx)); // aggiungi la figura appena creata f alla lista di Figure "fig"

					canvas.paintImage(); // disegna

					model.addElement(loadFig.get(loadSelIx).getFinalName()); // aggiungi alla lista di stringhe il tipo
																				// della nuova figura
					list_1.setSelectedIndex(model.getSize() - 1);// seleziona nella lista output l'ultima figura
																	// inserita
					firstSelIx = list_1.getSelectedIndex();
					idFigura++;
					rotationBar.setValue((int) fig.get(firstSelIx).getAngle());
				} else {
					JOptionPane.showMessageDialog(frame, "Hai annullato il caricamento di una nuova figura");
				}
				if (!(model.isEmpty()))
					btnRemoveFig.setEnabled(true);
			}
		});
		// mouse listener
		canvas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mousePt = e.getPoint();
				for (int i = fig.size() - 1; i >= 0; i--) {
					if (fig.get(i).contains(mousePt)) {//
						isInside = true;
						list_1.setSelectedIndex(i);

						firstSelIx = list_1.getSelectedIndex();
						if (fig.get(i).getClass() != Circle.class) {
							rotationBar.setEnabled(true);
							btnRotation.setEnabled(true);
							rotationBar.setValue((int) fig.get(firstSelIx).getAngle());
						} else {
							rotationBar.setEnabled(false);
							btnRotation.setEnabled(false);
						}
						break;
					} else {
						isInside = false;
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
					if (Resize) {
						double xVar = (e.getX() - mousePt.x) / (fig.get(firstSelIx).getCenterX() - mousePt.x);
						double scaleX = (-xVar * 8 / 10) + 1.0; // era troppo veloce a ingrandire
						fig.get(firstSelIx).resize(scaleX);
					} else {
						fig.get(firstSelIx).move(dx, dy);
					}
					canvas.paintImage();
					mousePt = e.getPoint();
				}
			}
		});
	}

}