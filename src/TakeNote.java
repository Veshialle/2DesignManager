import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;

import javax.swing.*;
import java.awt.*;
/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/*
 * Created by JFormDesigner on Thu Sep 07 15:52:18 CEST 2017
 */



/**
 * @author Matteo Gruppi
 */
public class TakeNote extends JFrame {
    public TakeNote() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Matteo Gruppi
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        btnSaveNote = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new TableLayout(new double[][] {
            {212, 122, 138},
            {251, 43}}));
        ((TableLayout)contentPane.getLayout()).setHGap(5);
        ((TableLayout)contentPane.getLayout()).setVGap(5);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(textArea1);
        }
        contentPane.add(scrollPane1, new TableLayoutConstraints(0, 0, 2, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

        //---- btnSaveNote ----
        btnSaveNote.setText("Save");
        contentPane.add(btnSaveNote, new TableLayoutConstraints(2, 1, 2, 1, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Matteo Gruppi
    protected JScrollPane scrollPane1;
    protected JTextArea textArea1;
    protected JButton btnSaveNote;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
