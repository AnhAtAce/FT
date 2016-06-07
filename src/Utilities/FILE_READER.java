/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author AN2
 */
public class FILE_READER {
    private static BufferedReader br;

    
    FILE_READER()
    {
    }
    
    public static String readFile(File file, String newLineSpeparator)
    {
        String string;
        StringBuilder fileContentString = new StringBuilder();
        if(file.exists()) // 1 = cancel, 0 = file selected
        {
            try {
                br = new BufferedReader(new java.io.FileReader(file));
                while ((string = br.readLine()) != null) {
                    fileContentString.append(string + newLineSpeparator);
                }
                br.close();                
                                
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);            
            }            
        }
  
        return fileContentString.toString();
    }
    
    public static String readHeader(File file)
    {
        String string;
        StringBuilder fileContentString = new StringBuilder();
        if(file.exists()) // 1 = cancel, 0 = file selected
        {
            try {
                br = new BufferedReader(new java.io.FileReader(file));
                while ((string = br.readLine()) != null) {
                    fileContentString.append(string);
                    break;
                }
                br.close();                
                                
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);            
            }            
        }
  
        return fileContentString.toString().trim();
    }

    public static List<String[]> ReadCSV(File file) {
        List<String[]> allData = null;
        if (file.exists()) // 1 = cancel, 0 = file selected
        {
            try {
                br = new BufferedReader(new java.io.FileReader(file));
                //CSVReader csvReader = new CSVReader(br, ',', '"', '\\', 0, false, true, true);
                CSVReader csvReader = new CSVReader(br);
                allData = csvReader.readAll();

                br.close();

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return allData;
    }
}
