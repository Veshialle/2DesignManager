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
        btnExpand = new JButton();
        scrollPane2 = new JScrollPane();

        //======== this ========
        setTitle("Database");
        Container contentPane = getContentPane();
        contentPane.setLayout(new TableLayout(new double[][] {
            {766, 97, 98},
            {41, 38, 36, 76, TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED}}));
        ((TableLayout)contentPane.getLayout()).setHGap(7);
        ((TableLayout)contentPane.getLayout()).setVGap(7);

        //======== scrollPane1 ========
        {

            //---- tableDB ----
            tableDB.setModel(new DefaultTableModel(
                new Object[][] {
                    {null, null, null, null, null, null, null},
                },
                new String[] {
                    "ID", "Name", "Version", "Type", "Number of sides/figures", "File Name", "Note File"
                }
            ) {
                boolean[] columnEditable = new boolean[] {
                    true, false, true, true, true, true, true
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            tableDB.setAutoCreateRowSorter(true);
            tableDB.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPane1.setViewportView(tableDB);
        }
        contentPane.add(scrollPane1, new TableLayoutConstraints(0, 0, 0, 6, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

        //---- btnLoad ----
        btnLoad.setText("Load");
        btnLoad.setToolTipText("Load to Canvas the figure selected");
        contentPane.add(btnLoad, new TableLayoutConstraints(1, 0, 1, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

        //---- btnRemove ----
        btnRemove.setText("Remove");
        btnRemove.setToolTipText("Remove the figure selected");
        contentPane.add(btnRemove, new TableLayoutConstraints(2, 0, 2, 0, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

        //---- btnDescription ----
        btnDescription.setText("Description");
        btnDescription.setToolTipText("See Description of the Figure");
        contentPane.add(btnDescription, new TableLayoutConstraints(1, 1, 1, 1, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));

        //---- btnExpand ----
        btnExpand.setText("Expand");
        btnExpand.setToolTipText("See the detail of all the Figure saved");
        contentPane.add(btnExpand, new TableLayoutConstraints(2, 1, 2, 1, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
        contentPane.add(scrollPane2, new TableLayoutConstraints(0, 4, 0, 4, TableLayoutConstraints.FULL, TableLayoutConstraints.FULL));
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
    protected JButton btnExpand;
    protected JScrollPane scrollPane2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
