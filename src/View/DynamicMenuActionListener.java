/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.FilteredFiles;
import Utilities.FILE_WRITER;
import com.opencsv.CSVParser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import org.json.JSONObject;

/**
 *
 * @author AN2
 */
public class DynamicMenuActionListener implements ActionListener {

    FilteredFiles ff = new FilteredFiles();
    JFileChooser fc = new JFileChooser();
    CSVParser csvParser = new CSVParser(',');

    public DynamicMenuActionListener() {
        fc.setFileFilter(ff);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // called object identity
        String clickedItem = ((JMenuItem) e.getSource()).getName();
        String action = clickedItem.substring(clickedItem.lastIndexOf(":") + 1).trim();
        String profileName = clickedItem.substring(0, clickedItem.lastIndexOf(":")).trim();
        //

        perform(action, profileName);
    }
    
    private void perform(String action, String profileName) {
        JSONObject theProfile = new JSONObject(GUI.jsonProfiles.get(profileName).toString());
        System.err.println(theProfile.toString());
        File file = new File(profileName + ".json");

        switch (action) {
            case "Open":
                GUI.loadProfile(profileName);
                System.err.println(profileName + " " + action);
                break;
            case "Input Header":
                if (fc.showDialog(fc, "Open") == JFileChooser.APPROVE_OPTION) {
                    theProfile.put("inFile", fc.getSelectedFile());
                    GUI.jsonProfiles.put(profileName, theProfile);
                    FILE_WRITER.saveJSONFile(GUI.jsonProfiles.toString(3), GUI.profilesFile);
                    GUI.loadProfile(profileName);
                    GUI.mapped.delete();
                }
                break;
            case "Add Input":
                if (fc.showDialog(fc, "Open") == JFileChooser.APPROVE_OPTION) {
                    theProfile.put("addInFile", fc.getSelectedFile());
                    GUI.jsonProfiles.put(profileName, theProfile);
                    FILE_WRITER.saveJSONFile(GUI.jsonProfiles.toString(3), GUI.profilesFile);
                    GUI.loadProfile(profileName);
                    GUI.mapped.delete();
                }
                break;
            case "Output Header":
                if (fc.showDialog(fc, "Open") == JFileChooser.APPROVE_OPTION) {
                    theProfile.put("outFile", fc.getSelectedFile());
                    GUI.jsonProfiles.put(profileName, theProfile);
                    FILE_WRITER.saveJSONFile(GUI.jsonProfiles.toString(3), GUI.profilesFile);
                    GUI.loadProfile(profileName);
                    GUI.mapped.delete();
                }
                break;
            case "Remove":
                GUI.jsonProfiles.remove(profileName);
                if (file.exists()) {
                    file.delete();
                }
                FILE_WRITER.saveJSONFile(GUI.jsonProfiles.toString(3), GUI.profilesFile);
                GUI.loadFirstProfile();
                break;
            default:
                break;
        }
    }
}
