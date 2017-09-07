import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/*
 * Created by JFormDesigner on Wed Sep 06 11:24:48 CEST 2017
 */



/**
 * @author Matteo Gruppi
 */
public class DB extends JFrame {
    public DB() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Matteo Gruppi
        scrollPane1 = new JScrollPane();
        tableDB = new JTable();
        btnLoad = new JButton();
        btnRemove = new JButton();
        btnDescription = new JButton();

        //======== this ========
        setTitle("Database");
        Container contentPane = getContentPane();
        contentPane.setLayout(new TableLayout(new double[][] {
            {766, 97, 98},
            {41, 38, 36, TableLayout.PREFERRED}}));
        ((TableLayout)contentPane.getLayout()).setHGap(7);
        ((TableLayout)contentPane.getLayout()).setVGap(7);

        //======== scrollPane1 ========
        {

            //---- tableDB ----
            tableDB.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                    "Name", "Version", "Type", "Number of sides/figures", "File Name", "Annotations"
                }
            ));
            scrollPane1.setViewportView(tableDB);
        }
        contentPane.add(scrollPane1, new TableLayoutConstraints(0, 0, 0, 3, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

        //---- btnLoad ----
        btnLoad.setText("Load");
        contentPane.add(btnLoad, new TableLayoutConstraints(1, 0, 1, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

        //---- btnRemove ----
        btnRemove.setText("Remove");
        contentPane.add(btnRemove, new TableLayoutConstraints(2, 0, 2, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

        //---- btnDescription ----
        btnDescription.setText("Read Description");
        contentPane.add(btnDescription, new TableLayoutConstraints(1, 1, 2, 1, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Matteo Gruppi
    protected JScrollPane scrollPane1;
    protected JTable tableDB;
    protected JButton btnLoad;
    protected JButton btnRemove;
    protected JButton btnDescription;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
