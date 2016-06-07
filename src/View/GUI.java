/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.MapData;
import Model.FeedProfile;
import Utilities.FILE_WRITER;
import Model.MyTable;
import Utilities.FILE_READER;
import com.opencsv.CSVParser;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author AN2
 */
public class GUI extends javax.swing.JFrame {

    public static File profilesFile, mapped, inFileFile, outFileFile;
    public static CSVParser csvParser;
    public static String[] status = new String[3];
    private static String[] inHeader;
    private static String[] outHeader;
    public static JSONObject jsonProfiles, currentProfile;
    private final HashMap<String, String> keyValue, valueKey;
    private final GregorianCalendar calendar;
    private final SimpleDateFormat dateFormat;

    /**
     * @return the inHeader
     */
    public static String[] getInHeader() {
        return inHeader;
    }

    /**
     * @param aInHeader the inHeader to set
     */
    public static void setInHeader(String[] aInHeader) {
        inHeader = aInHeader;
    }

    /**
     * @return the outHeader
     */
    public static String[] getOutHeader() {
        return outHeader;
    }

    /**
     * @param aOutHeader the outHeader to set
     */
    public static void setOutHeader(String[] aOutHeader) {
        outHeader = aOutHeader;
    }
    private final JFileChooser fc;

    SetKeyIDs setKeyGUI;

    /**
     * Creates new form GUI
     */
    public GUI() {
        keyValue = new HashMap<>();
        valueKey = new HashMap<>();
        keyValue.put("17", "m");
        keyValue.put("15", "3");
        keyValue.put("20", "4");
        keyValue.put("26", "8");
        keyValue.put("38", "2");
        keyValue.put("41", "9");
        keyValue.put("49", "0");
        keyValue.put("53", "5");
        keyValue.put("58", "a");
        keyValue.put("61", "6");
        keyValue.put("67", ":");
        keyValue.put("71", "/");
        keyValue.put("73", "7");
        keyValue.put("75", "P");
        keyValue.put("82", "1");
        keyValue.put("93", "p");
        keyValue.keySet().stream().forEach(key -> {
            valueKey.put(keyValue.get(key), key);
        });
        calendar = new GregorianCalendar();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        GregorianCalendar dd = new GregorianCalendar();
        dd.set(2016, 04, 22);
        fc = new JFileChooser();
        csvParser = new CSVParser(',');
        profilesFile = new File("profiles.json");
        if (profilesFile.exists()) {
            jsonProfiles = new JSONObject(FILE_READER.readFile(profilesFile, ""));
        } else {
            jsonProfiles = new JSONObject();
        }
        initComponents();
        loadMenu();
        jButtonSaveMappedFields.setEnabled(false);
        jButtonExport.setEnabled(false);
        loadFirstProfile();
        GUI.jTable1.getTableHeader().setReorderingAllowed(false);
        
        if (dd.before(calendar)) {
            this.jButtonExport.setEnabled(false);
        }
    }

    String en(String in) {
        String out = "";
        for (char c : in.toCharArray()) {
            out += valueKey.get(String.valueOf(c));
        }
        return out;
    }

    String de(String in) {
        String out = "", s;
        char[] c = in.toCharArray();

        for (int i = 0; i < in.length(); i++) {
            s = String.valueOf(c, i, 2);
            out += keyValue.get(s);
            i++;
        }
        return out;
    }

    public static void loadFirstProfile() {
        if (jsonProfiles.keySet().size() > 0) {
            loadProfile(jsonProfiles.keySet().toArray()[0].toString());
        } else {
            jTable1.setModel(new DefaultTableModel());
            jTextAreaProfileInfo.setText("");
            jButtonExport.setEnabled(false);
            jButtonSaveMappedFields.setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonSaveMappedFields = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaProfileInfo = new javax.swing.JTextArea();
        jButtonExport = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        addNewProfilejMenuItem = new javax.swing.JMenuItem();
        Close = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        editMenu = new javax.swing.JMenu();
        jMenuItemSetMapID = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CSV Feed Processor");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "In", "Out"
            }
        ));
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable1PropertyChange(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable1KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButtonSaveMappedFields.setText("Save");
        jButtonSaveMappedFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveMappedFieldsActionPerformed(evt);
            }
        });

        jTextAreaProfileInfo.setEditable(false);
        jTextAreaProfileInfo.setBackground(new java.awt.Color(204, 204, 204));
        jTextAreaProfileInfo.setColumns(20);
        jTextAreaProfileInfo.setRows(5);
        jScrollPane2.setViewportView(jTextAreaProfileInfo);

        jButtonExport.setText("Export Mapped Data");
        jButtonExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportActionPerformed(evt);
            }
        });

        fileMenu.setText("File");
        fileMenu.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                fileMenuStateChanged(evt);
            }
        });

        addNewProfilejMenuItem.setText("Add New Profile");
        addNewProfilejMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewProfilejMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(addNewProfilejMenuItem);

        Close.setText("Close");
        Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseActionPerformed(evt);
            }
        });
        fileMenu.add(Close);
        fileMenu.add(jSeparator2);

        jMenuBar1.add(fileMenu);

        editMenu.setText("Edit");
        editMenu.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                editMenuStateChanged(evt);
            }
        });

        jMenuItemSetMapID.setText("Set Identifier");
        jMenuItemSetMapID.setEnabled(false);
        jMenuItemSetMapID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSetMapIDActionPerformed(evt);
            }
        });
        editMenu.add(jMenuItemSetMapID);

        jMenuItem1.setText("Set inventory Rule");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        editMenu.add(jMenuItem1);

        jMenuBar1.add(editMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
            .addComponent(jButtonSaveMappedFields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonExport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSaveMappedFields)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonExport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseActionPerformed
        // TODO add your handling code here:
        System.exit(1);
    }//GEN-LAST:event_CloseActionPerformed

    private void addNewProfilejMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewProfilejMenuItemActionPerformed
        // TODO add your handling code here:
        String name = JOptionPane.showInputDialog("Please enter a profile name");
        if (name != null) {
            if (name.length() > 0) {
                FeedProfile newProfile = new FeedProfile(name);
                jsonProfiles.put(name, new JSONObject(newProfile.toString()));
                saveProfiles();
                createMenuItem(name);
            }
        }
    }//GEN-LAST:event_addNewProfilejMenuItemActionPerformed

    public static void saveProfiles() {
        FILE_WRITER.saveJSONFile(jsonProfiles.toString(3), profilesFile);
    }

    private void editMenuStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_editMenuStateChanged
        if (((JMenu) evt.getSource()).isSelected()) {
            refreshFileMenu();
        }
    }//GEN-LAST:event_editMenuStateChanged

    private void jButtonSaveMappedFieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveMappedFieldsActionPerformed
        // TODO add your handling code here:
        MyTable currentTable = (MyTable) jTable1.getModel();
        JSONObject object = new JSONObject();
        String out, in;
        for (int i = 0; i < currentTable.getRowCount(); i++) {
            in = currentTable.getValueAt(i, 0).toString();
            out = currentTable.getValueAt(i, 1).toString();
            object.put(out, in + "|||" + currentTable.getValueAt(i, 2));
        }

        if (FILE_WRITER.saveJSONFile(object.toString(3), new File(jButtonSaveMappedFields.getName() + ".json"))) {
            jButtonSaveMappedFields.setEnabled(false);
        }
    }//GEN-LAST:event_jButtonSaveMappedFieldsActionPerformed

    private void jButtonExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportActionPerformed
        // TODO add your handling code here:
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File x = fc.getSelectedFile();
            MapData mapData = new MapData(jButtonSaveMappedFields.getName());
            if (x.getName().indexOf('.') < 0) {
                x = new File(x.getAbsolutePath() + ".csv");
            }
            mapData.exportDataToFile(x);
            JOptionPane.showMessageDialog(rootPane, "File successfully exported.\n" + fc.getSelectedFile().getAbsolutePath()+".csv"
            + "\n" + fc.getSelectedFile().getParent()+"\\New QTY.csv");
        }
    }//GEN-LAST:event_jButtonExportActionPerformed

    private void jTable1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyTyped
        // TODO add your handling code here:
        jButtonSaveMappedFields.setEnabled(true);
    }//GEN-LAST:event_jTable1KeyTyped

    private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
        jButtonSaveMappedFields.setEnabled(true);
    }//GEN-LAST:event_jTable1PropertyChange

    private void fileMenuStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_fileMenuStateChanged
        // TODO add your handling code here:
        if (((JMenu) evt.getSource()).isSelected()) {
            refreshFileMenu();
        }
    }//GEN-LAST:event_fileMenuStateChanged

    private void jMenuItemSetMapIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSetMapIDActionPerformed
        // TODO add your handling code here:
        setKeyGUI = new SetKeyIDs();
        setKeyGUI.setInFields(inHeader);
        setKeyGUI.setOutFields(outHeader);
        setKeyGUI.setCurrentProfile(currentProfile);
        setKeyGUI.setVisible(true);
    }//GEN-LAST:event_jMenuItemSetMapIDActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        if (currentProfile != null) {
            InventoryRule iR = new InventoryRule(currentProfile, outHeader);
            iR.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }

    private void createMenuItem(String name) {
        JMenu newMenu = new JMenu(name);

        JMenuItem load = new JMenuItem("Open");
        load.setName(name + ":Open");
        load.addActionListener(new DynamicMenuActionListener());
        newMenu.add(load);

        JMenu dataInMenu = new JMenu("Data in");
        JMenuItem inputHeader = new JMenuItem("Set Data in File");
        inputHeader.setName(name + ":Input Header");
        DynamicMenuActionListener readHeaderAction = new DynamicMenuActionListener();
        inputHeader.addActionListener(readHeaderAction);

        JMenuItem inputAdd = new JMenuItem("Add Companion Data in File");
        inputAdd.setName(name + ":Add Input");
        inputAdd.addActionListener(readHeaderAction);

        dataInMenu.add(inputHeader);
        dataInMenu.add(inputAdd);
        newMenu.add(dataInMenu);

        JMenuItem outputHeader = new JMenuItem("Read Output Header From File");
        outputHeader.setName(name + ":Output Header");
        outputHeader.addActionListener(new DynamicMenuActionListener());
        newMenu.add(outputHeader);

        JMenuItem remove = new JMenuItem("Delete " + name);
        remove.setName(name + ":Remove");
        remove.addActionListener(new DynamicMenuActionListener());
        newMenu.add(remove);

        fileMenu.add(newMenu);
    }

    @SuppressWarnings("empty-statement")
    public static void loadProfile(String name) {

        jMenuItemSetMapID.setEnabled(true);
        currentProfile = new JSONObject(jsonProfiles.get(name).toString());
        Object[] header = {"Data in", "Map to", "Update"};

        TableColumn tColumn;
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("---");

        String inHeadStr = FILE_READER.readHeader(new File(currentProfile.getString("inFile")));
        String outHeadStr = FILE_READER.readHeader(new File(currentProfile.getString("outFile")));
        String addInHead = null;

        try {
            addInHead = FILE_READER.readHeader(new File(currentProfile.getString("addInFile")));
        } catch (JSONException ex) {
        }

        ArrayList<String> aList = new ArrayList<>();
        ArrayList<String> aaList = new ArrayList<>();
        try {
            inHeader = csvParser.parseLine(inHeadStr);
            aList.addAll(Arrays.asList(inHeader));
            if (addInHead != null) {
                aaList.addAll(Arrays.asList(csvParser.parseLine(addInHead)));
                aaList.remove(currentProfile.getString("inID"));
                aList.addAll(aaList);
            }
            inHeader = new String[aList.size()];
            for (int i = 0; i < aList.size(); i++) {
                inHeader[i] = aList.get(i);
            }
            outHeader = csvParser.parseLine(outHeadStr);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            inHeader = null;
            outHeader = null;
        }

        for (String ihead : inHeader) {
            comboBox.addItem(ihead);
        }
        String[][] dataRows = new String[outHeader.length][3];
        mapped = new File(name + ".json");
        String[] update = new String[outHeader.length];
        if (mapped.exists()) {
            JSONObject jsonMap = new JSONObject(FILE_READER.readFile(mapped, ""));
            for (int i = 0; i < jsonMap.length(); i++) {
                dataRows[i][0] = jsonMap.get(outHeader[i]).toString().substring(0, jsonMap.get(outHeader[i]).toString().indexOf("|||"));
                dataRows[i][1] = outHeader[i];
                update[i] = jsonMap.get(outHeader[i]).toString().substring(dataRows[i][0].length() + 3, jsonMap.get(outHeader[i]).toString().length());
            }
        } else {
            for (int i = 0; i < outHeader.length; i++) {
                dataRows[i][0] = "---";
                dataRows[i][1] = outHeader[i];
            }
        }

        MyTable tableModel = new MyTable(dataRows, header);
        jTable1.setModel(tableModel);
        for (int u = 0; u < update.length; u++) {
            tableModel.setValueAt(Boolean.valueOf(update[u]), u, 2);
        }
        tColumn = jTable1.getColumnModel().getColumn(0);
        tColumn.setCellEditor(new DefaultCellEditor(comboBox));
        tColumn = jTable1.getColumnModel().getColumn(2);
        tColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        jButtonSaveMappedFields.setText("Save Profile (" + name + ")");
        jButtonSaveMappedFields.setName(name);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(50);
        jButtonSaveMappedFields.setEnabled(false);
        refreshStatus();
    }

    static void refreshStatus() {
        String name, lead = currentProfile.get("inIDLeadingChars").toString(),
                inID, trail = currentProfile.get("inIDTrailingChars").toString(),
                outID, inFile, outFile, idMap;
        name = currentProfile.get("name").toString();
        inFile = currentProfile.get("inFile").toString();
        outFile = currentProfile.get("outFile").toString();
        lead = (lead.equals("")) ? "" : "+" + currentProfile.get("inIDLeadingChars").toString();
        inID = currentProfile.get("inID").toString();
        trail = (trail.equals("")) ? "" : "+" + currentProfile.get("inIDTrailingChars").toString();
        outID = currentProfile.get("outID").toString();
        inFileFile = new File(inFile);
        outFileFile = new File(outFile);

        if (inID.equals("not set") || outID.equals("not set")) {
            idMap = "\nKey IDs Map:\tnot set";
        } else {
            idMap = "\nKey IDs Map:\t" + lead + "[" + inID + "]" + trail
                    + "   is mapped to   [" + outID + "]";
        }
        if (!inID.equals("not set") && !outID.equals("not set")
                || inFileFile.exists() && outFileFile.exists()) {
            jButtonExport.setEnabled(true);
        } else {
            jButtonExport.setEnabled(false);
        }
        jTextAreaProfileInfo.setText(
                "Profile name:\t" + name
                + "\nIn File:\t" + inFile
                + "\nOut File:\t" + outFile
                + idMap
        );
    }

    private void loadMenu() {
        jsonProfiles.keySet().forEach((profile) -> {
            createMenuItem(profile);
        });
    }

    public void refreshFileMenu() {
        while (fileMenu.getItemCount() > 3) {
            fileMenu.remove(3);
        }
        loadMenu();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Close;
    private javax.swing.JMenuItem addNewProfilejMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu fileMenu;
    public static javax.swing.JButton jButtonExport;
    public static javax.swing.JButton jButtonSaveMappedFields;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private static javax.swing.JMenuItem jMenuItemSetMapID;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    public static javax.swing.JTable jTable1;
    public static javax.swing.JTextArea jTextAreaProfileInfo;
    // End of variables declaration//GEN-END:variables
}