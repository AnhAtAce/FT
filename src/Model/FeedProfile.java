/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import org.json.JSONObject;

/**
 *
 * @author AN2
 */
public class FeedProfile {
    private String name, inID, inIDLeadingChars, inIDTrailingChars, outID;
    private File inFile, outFile;

    public FeedProfile(String name){
        this.name = name;
        inFile = new File("not set");
        outFile = new File("not set");
        inID = "not set";
        inIDLeadingChars = "not set";
        inIDTrailingChars = "not set";
        outID = "ot set";
    };

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the inFile
     */
    public File getInFile() {
        return inFile;
    }

    /**
     * @param inFile the inFile to set
     */
    public void setInFile(File inFile) {
        this.inFile = inFile;
    }

    /**
     * @return the outFile
     */
    public File getOutFile() {
        return outFile;
    }

    /**
     * @param outFile the outFile to set
     */
    public void setOutFile(File outFile) {
        this.outFile = outFile;
    }
    
    @Override
    public String toString()
    {
        JSONObject profile = new JSONObject();
        profile.put("name", getName());
        profile.put("inFile", getInFile().getName());
        profile.put("outFile", getOutFile().getName());
        profile.put("inID", getInID());
        profile.put("outID", getOutID());
        profile.put("inIDLeadingChars", getInIDLeadingChars());
        profile.put("inIDTrailingChars", getInIDTrailingChars());
        return profile.toString();
    }

    /**
     * @return the id
     */
    public String getinID() {
        return getInID();
    }

    /**
     * @param id the id to set
     */
    public void setinID(String id) {
        this.setInID(id);
    }

    /**
     * @return the inID
     */
    public String getInID() {
        return inID;
    }

    /**
     * @param inID the inID to set
     */
    public void setInID(String inID) {
        this.inID = inID;
    }

    /**
     * @return the inIDLeadingChars
     */
    public String getInIDLeadingChars() {
        return inIDLeadingChars;
    }

    /**
     * @param inIDLeadingChars the inIDLeadingChars to set
     */
    public void setInIDLeadingChars(String inIDLeadingChars) {
        this.inIDLeadingChars = inIDLeadingChars;
    }

    /**
     * @return the inIDTrailingChars
     */
    public String getInIDTrailingChars() {
        return inIDTrailingChars;
    }

    /**
     * @param inIDTrailingChars the inIDTrailingChars to set
     */
    public void setInIDTrailingChars(String inIDTrailingChars) {
        this.inIDTrailingChars = inIDTrailingChars;
    }

    /**
     * @return the outID
     */
    public String getOutID() {
        return outID;
    }

    /**
     * @param outID the outID to set
     */
    public void setOutID(String outID) {
        this.outID = outID;
    }
};
