import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.GroupLayout;
/*
 * Created by JFormDesigner on Sat Sep 02 20:52:18 CEST 2017
 */



/**
 * @author Matteo Gruppi
 */
public class GUI_2DM extends JFrame {
    public GUI_2DM() {
        initComponents();
    }

    private void canvasPropertyChange(PropertyChangeEvent e) {
        // TODO add your code here
    }

    private void canvasComponentResized(ComponentEvent e) {
        // TODO add your code here
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Matteo Gruppi
        scrollPane = new JScrollPane();
        list1 = new JList<>();
        btnAddFig = new JButton();
        btnSetY = new JButton();
        btnSetX = new JButton();
        btnRotation = new JButton();
        btnCompFig = new JButton();
        btnColor = new JButton();
        btnLoadFig = new JButton();
        btnSaveFig = new JButton();
        btnRemoveFig = new JButton();
        radioMove = new JRadioButton();
        radioResize = new JRadioButton();
        sliderLabel = new JLabel();
        rotationBar = new JSlider();
        checkboColorComp = new JCheckBox();
        btnUp = new JButton();
        btnLeft = new JButton();
        btnDown = new JButton();
        btnRight = new JButton();
        spinnerPos = new JSpinner();
        canvas = new MyCanvas();
        checkGrid = new JCheckBox();

        //======== this ========
        setMinimumSize(new Dimension(1000, 738));
        setIconImage(new ImageIcon("/home/veshialle/2DesignManager/src/2DMIcon.png").getImage());
        Container contentPane = getContentPane();

        //======== scrollPane ========
        {
            scrollPane.setViewportView(list1);
        }

        //---- btnAddFig ----
        btnAddFig.setText("Add Figure");

        //---- btnSetY ----
        btnSetY.setText("SetY");
        btnSetY.setEnabled(false);

        //---- btnSetX ----
        btnSetX.setText("SetX");
        btnSetX.setEnabled(false);

        //---- btnRotation ----
        btnRotation.setText("ZeroDegree");
        btnRotation.setEnabled(false);

        //---- btnCompFig ----
        btnCompFig.setText("Composite Figure");
        btnCompFig.setEnabled(false);

        //---- btnColor ----
        btnColor.setText("Choice Color");
        btnColor.setEnabled(false);

        //---- btnLoadFig ----
        btnLoadFig.setText("Load Figure");

        //---- btnSaveFig ----
        btnSaveFig.setText("Save Figure");
        btnSaveFig.setEnabled(false);

        //---- btnRemoveFig ----
        btnRemoveFig.setText("Remove Figure");
        btnRemoveFig.setEnabled(false);

        //---- radioMove ----
        radioMove.setText("Move");
        radioMove.setSelected(true);
        radioMove.setEnabled(false);

        //---- radioResize ----
        radioResize.setText("Resize");
        radioResize.setEnabled(false);

        //---- sliderLabel ----
        sliderLabel.setText("Rotate");

        //---- rotationBar ----
        rotationBar.setFont(rotationBar.getFont().deriveFont(rotationBar.getFont().getStyle() & ~Font.ITALIC, rotationBar.getFont().getSize() - 4f));
        rotationBar.setMaximum(180);
        rotationBar.setValue(0);
        rotationBar.setMinimum(-180);
        rotationBar.setEnabled(false);

        //---- checkboColorComp ----
        checkboColorComp.setText("Aggregate Color");
        checkboColorComp.setEnabled(false);

        //---- btnUp ----
        btnUp.setText("\u2191");
        btnUp.setEnabled(false);

        //---- btnLeft ----
        btnLeft.setText("\u2192");
        btnLeft.setEnabled(false);

        //---- btnDown ----
        btnDown.setText("\u2193");
        btnDown.setEnabled(false);

        //---- btnRight ----
        btnRight.setText("\u2190");
        btnRight.setEnabled(false);

        //---- spinnerPos ----
        spinnerPos.setEnabled(false);

        //---- canvas ----
        canvas.setMinimumSize(new Dimension(1000, 563));
        canvas.setPreferredSize(new Dimension(1200, 675));
        canvas.setFont(new Font("Roboto Medium", Font.PLAIN, 8));

        //---- checkGrid ----
        checkGrid.setText("Grid");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnAddFig, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                        .addComponent(btnSaveFig, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                        .addComponent(btnColor, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(15, 15, 15)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnRemoveFig, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLoadFig, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(radioMove)
                                    .addGap(33, 33, 33)
                                    .addComponent(sliderLabel)
                                    .addGap(30, 30, 30)
                                    .addComponent(btnRotation))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(radioResize)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(rotationBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addGap(6, 6, 6)
                            .addComponent(btnRight)
                            .addGap(0, 0, 0)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(btnUp)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(btnDown)
                                    .addGap(0, 0, 0)
                                    .addComponent(btnLeft))))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                    .addGap(307, 307, 307)
                                    .addComponent(btnSetX)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnSetY)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(spinnerPos, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(btnCompFig)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(checkboColorComp)
                                    .addGap(267, 267, 267)))
                            .addGap(18, 18, 18)
                            .addComponent(checkGrid)))
                    .addContainerGap(338, Short.MAX_VALUE))
                .addComponent(canvas, GroupLayout.DEFAULT_SIZE, 1198, Short.MAX_VALUE)
        );
        contentPaneLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnDown, btnLeft, btnRight, btnUp});
        contentPaneLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnAddFig, btnLoadFig, btnRemoveFig, btnSaveFig});
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(btnRemoveFig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAddFig, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(btnLoadFig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSaveFig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCompFig, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(checkboColorComp, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnColor, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(radioMove, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnRotation, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(sliderLabel, GroupLayout.Alignment.TRAILING))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(contentPaneLayout.createParallelGroup()
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                            .addComponent(radioResize, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                            .addGap(12, 12, 12))
                                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                            .addComponent(rotationBar, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(spinnerPos, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSetY, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSetX, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(checkGrid)))))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(btnUp)
                            .addGap(0, 0, 0)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(btnDown)
                                .addComponent(btnRight)
                                .addComponent(btnLeft))))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(canvas, GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE))
        );
        contentPaneLayout.linkSize(SwingConstants.VERTICAL, new Component[] {btnDown, btnLeft, btnRight, btnUp});
        contentPaneLayout.linkSize(SwingConstants.VERTICAL, new Component[] {btnAddFig, btnLoadFig, btnRemoveFig, btnSaveFig});
        pack();
        setLocationRelativeTo(getOwner());

        //---- MouseHandlerFigure ----
        ButtonGroup MouseHandlerFigure = new ButtonGroup();
        MouseHandlerFigure.add(radioMove);
        MouseHandlerFigure.add(radioResize);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Matteo Gruppi
    protected JScrollPane scrollPane;
    protected JList<String> list1;
    protected JButton btnAddFig;
    protected JButton btnSetY;
    protected JButton btnSetX;
    protected JButton btnRotation;
    protected JButton btnCompFig;
    protected JButton btnColor;
    protected JButton btnLoadFig;
    protected JButton btnSaveFig;
    protected JButton btnRemoveFig;
    protected JRadioButton radioMove;
    protected JRadioButton radioResize;
    protected JLabel sliderLabel;
    protected JSlider rotationBar;
    protected JCheckBox checkboColorComp;
    protected JButton btnUp;
    protected JButton btnLeft;
    protected JButton btnDown;
    protected JButton btnRight;
    protected JSpinner spinnerPos;
    protected MyCanvas canvas;
    protected JCheckBox checkGrid;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
