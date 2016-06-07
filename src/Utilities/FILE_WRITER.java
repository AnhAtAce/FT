/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author AN2
 */
public class FILE_WRITER {  
    private static java.io.FileWriter fw;

    public static boolean saveJSONFile(String jsonString, File file) {
        boolean errorFree = true;

        try {
            fw = new java.io.FileWriter(file.getAbsoluteFile(), false);
            fw.write(jsonString);
            fw.close();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            errorFree = false;
        }
        System.err.println("write successful = " + errorFree);
        return errorFree;
    }

    public static java.io.FileWriter getFw(File file) {
        try {
            fw = new java.io.FileWriter(file.getAbsoluteFile(), false);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return fw;
    }
}

