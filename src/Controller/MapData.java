/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.MyTable;
import Utilities.FILE_READER;
import Utilities.FILE_WRITER;
import View.GUI;
import static View.GUI.jTable1;
import com.opencsv.CSVParser;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author AN2
 */
public class MapData {

    JSONObject thisProfile, inDataJSON, currentInventories, newFeedInventories;
    CSVParser csvParser;
    CSVWriter csvWriter;
    File addInFile, inFile, outFile;

    public MapData(String profileName) {
        thisProfile = (JSONObject) GUI.jsonProfiles.get(profileName);
        inDataJSON = new JSONObject();
        newFeedInventories = new JSONObject();
        csvParser = new CSVParser(',');
        currentInventories = new JSONObject();
        inFile = new File(thisProfile.get("inFile").toString());
        outFile = new File(thisProfile.get("outFile").toString());
        try {
            addInFile = new File(thisProfile.get("addInFile").toString());
        } catch (JSONException ex) {
            addInFile = new File("");
        }
    }
  
    public void exportDataToFile(File xfile) {
        csvWriter = new CSVWriter(FILE_WRITER.getFw(xfile));
        String[] header = null, lineFields;
        JSONObject mapOutToIn, mapInToOut, updateMap;
        String outIDFieldName = thisProfile.get("outID").toString();
        String inIDFieldName = thisProfile.get("inID").toString();
        String lead = thisProfile.get("inIDLeadingChars").toString();
        String trail = thisProfile.get("inIDTrailingChars").toString(), inventoryField;

        if (inFile.exists() && outFile.exists()) {
            MyTable currentTable = (MyTable) jTable1.getModel();
            mapOutToIn = new JSONObject();
            mapInToOut = new JSONObject();
            updateMap = new JSONObject();
            String out, in;
            for (int i = 0; i < currentTable.getRowCount(); i++) {
                in = currentTable.getValueAt(i, 0).toString();
                out = currentTable.getValueAt(i, 1).toString();
                mapOutToIn.put(out, in);
                mapInToOut.put(in, out);
                updateMap.put(out, currentTable.getValueAt(i, 2).toString());
            }

            //Read main data in file
            List<String[]> allData = FILE_READER.ReadCSV(inFile);
            for (int i = 0; i < allData.size(); i++) {
                switch (i) {
                    case 0:
                        header = allData.get(i);
                        break;
                    default:
                        lineFields = allData.get(i);
                        for (int f = 0; f < lineFields.length; f++) {
                            inDataJSON.put(header[f], lineFields[f]);
                        }
                        newFeedInventories.put(inDataJSON.get(inIDFieldName).toString(), inDataJSON);
                        inDataJSON = new JSONObject();
                        break;
                }
            }

            //Read additional data in file
            String oldData;
            if (addInFile.exists()) {
                allData = FILE_READER.ReadCSV(addInFile);
                for (int i = 0; i < allData.size(); i++) {
                    switch (i) {
                        case 0:
                            header = allData.get(i);
                            break;
                        default:
                            lineFields = allData.get(i);
                            inDataJSON = null;
                            for (int f = 0; f < lineFields.length; f++) {
                                if (header[f].equals(inIDFieldName)) {
                                    try {
                                        inDataJSON = new JSONObject(newFeedInventories.get(lineFields[f]).toString());
                                    } catch (JSONException ex) {
                                        //System.err.println("-\n"+ex.getMessage()+"\nItem in add in file not in main in file\n"+lineFields[f]+" in Companion file ignored\n-");
                                    }
                                }
                            }

                            if (inDataJSON == null) {
                                continue;
                            }

                            for (int f = 0; f < lineFields.length; f++) {
                                if (!header[f].equals(inIDFieldName)) {
                                    try {
                                        oldData = inDataJSON.get(header[f]).toString();
                                    } catch (JSONException ex) {
                                        oldData = "";
                                    }

                                    if (oldData.equals("")) {
                                        inDataJSON.put(header[f], lineFields[f]);
                                    } else {
                                        inDataJSON.put(header[f], oldData + "; " + lineFields[f]);
                                    }
                                }
                            }
                            newFeedInventories.put(inDataJSON.get(inIDFieldName).toString(), inDataJSON);
                            inDataJSON = new JSONObject();
                            break;
                    }
                }
            }

            allData = FILE_READER.ReadCSV(outFile);
            System.out.println(allData.size() + " lines from " + outFile.getName());
            for (int i = 0; i < allData.size(); i++) {
                switch (i) {
                    case 0:
                        header = allData.get(i);
                        System.out.println("Header: " + Arrays.asList(header).toString());
                        break;
                    default:
                        try {
                            inDataJSON = new JSONObject();
                            lineFields = allData.get(i);
                            //System.out.println(i + ":" + lineFields.length);
                            for (int f = 0; f < lineFields.length; f++) {
                                inDataJSON.put(header[f], lineFields[f]);
                            }
                            //System.out.println(inDataJSON.toString(3));
                            currentInventories.put(inDataJSON.get(outIDFieldName).toString(), inDataJSON);
                        } catch (Exception ex) {
                            System.err.println("\n****************************\n"
                                    + "Error in line # " + (i + 1) + " of " + outFile.getName() + "\n"
                                    + ex.getMessage()
                                    + "\n****************************\n"
                                    + inDataJSON.toString(3)
                                    + "\n****************************\n"
                            );
                        }
                        break;
                }
            }

            //Write data
            String[] outDataLine;
            try {
                header = csvParser.parseLineMulti(FILE_READER.readHeader(outFile));
                csvWriter.writeNext(header);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }

            ArrayList<String[]> sqtUpdateOnly = new ArrayList<>();
            String[] qtyOnlyHeader = {"SKU <sku>", "QTY", "Status", "Stock Availability"};
            String[] qtyLine;
            sqtUpdateOnly.add(qtyOnlyHeader);
            
            String inField, outField;
            JSONObject inItem, currentItem = new JSONObject();
            Object[] inItemKeysArray = newFeedInventories.keySet().toArray();
            JSONArray newItemsArray = new JSONArray();
            String currentItemID;
            for (Object o : inItemKeysArray) {
                inItem = new JSONObject(newFeedInventories.get(o.toString()).toString());
                outDataLine = new String[header.length];
                qtyLine = new String[4];
                try {
                    //check if item already in inventory
                    currentItemID = lead + inItem.get(inIDFieldName).toString() + trail;
                    currentItem = (JSONObject) currentInventories.get(currentItemID);
                    //System.err.println("Found: " + currentItemID);

                } catch (JSONException ex) {
                    newItemsArray.put(inItem);
                    continue;
                }

                try {
                    inventoryField = thisProfile.get("inventoryField").toString();
                    for (int h = 0; h < header.length; h++) {
                        inField = mapOutToIn.get(header[h]).toString();
                        if (!inField.equals("")) {
                            try {
                                if (inField.equals("---")) {
                                    outDataLine[h] = currentItem.get(header[h]).toString();
                                    continue;
                                }

                                if (Boolean.valueOf(updateMap.get(header[h]).toString()) == true) {
                                    outDataLine[h] = inItem.get(inField).toString();
                                } else {
                                    outDataLine[h] = currentItem.get(header[h]).toString();
                                }
                            } catch (JSONException ex) {
                                System.err.println(ex.getMessage()+":::Marker A:::" + Thread.currentThread().getStackTrace()[3].getLineNumber());
                            }

                        }
                    }

                    if (currentInventories.get(currentItemID) != null) {
                        qtyLine[0] = currentItemID;
                        System.err.println(inventoryField);
                        System.err.println(mapOutToIn.get(inventoryField).toString());
                                
                        qtyLine[1] = inItem.get(mapOutToIn.get(inventoryField).toString()).toString();
 
                        int qtyBuffer = Integer.valueOf(thisProfile.get("inventoryBuffer").toString());
                        if (Integer.valueOf(qtyLine[1]) > qtyBuffer) {
                            qtyLine[2] = "1";
                            qtyLine[3] = "1";
                        } else {
                            qtyLine[2] = "2";
                            qtyLine[3] = "0";
                        }
                    }
                    
                    sqtUpdateOnly.add(qtyLine);
                    csvWriter.writeNext(outDataLine);
                } catch (JSONException ex) {
                    //System.err.println(ex.getMessage()+":::Marker 1:::" + Thread.currentThread().getStackTrace()[3].getLineNumber());
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (NumberFormatException nex) {
                    JOptionPane.showMessageDialog(null, "Data type mismatch.\n\nPossiple causes of error:\nSelected quantity field is not a number.");
                    break;
                }
            }

            //write new items
            for (int n = 0; n < newItemsArray.length(); n++) {
                inItem = new JSONObject(newItemsArray.get(n).toString());
                outDataLine = new String[header.length];

                for (int h = 0; h < header.length; h++) {
                    inField = mapOutToIn.get(header[h]).toString();
                    try {
                        if (!inField.equals("") && !inField.equals("---")) {
                            if (inField.equals(inIDFieldName)) {
                                outDataLine[h] = "{NEW}-" + lead + inItem.get(inField).toString() + trail;
                            } else {
                                outDataLine[h] = inItem.get(inField).toString();
                            }
                        }
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                    //outDataLine[h] = applyFieldsRule(header[h], outDataLine[h]);
                }
                csvWriter.writeNext(outDataLine);
            }

            try {
                csvWriter.close();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }

            try {
                csvWriter = new CSVWriter(FILE_WRITER.getFw(new File(xfile.getParent() + "\\New QTY.csv")));
                csvWriter.writeAll(sqtUpdateOnly);
                csvWriter.close();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }

    }
}
