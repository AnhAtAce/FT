/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author AN2
 */
public class FilteredFiles extends FileFilter{

    @Override
    public boolean accept(File f) {
       
        if (f.isDirectory()) {
            return true;
        }
        
        String extension = "";
        if(f.getName().lastIndexOf(".") > -1)
        {
            extension = f.getName().substring(f.getName().lastIndexOf("."));

        }
        return (extension.equals(".txt") || extension.equals(".csv"));
    }

    @Override
    public String getDescription() {
        return "Comma Delimited (.txt .csv)";
    }
    
}
