/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.FeedProfile;
import java.util.HashMap;
/**
 *
 * @author AN2
 */
public class Profiles {
    public static HashMap<String, FeedProfile> profilesHashMap = new HashMap();
    
    public static FeedProfile getProfile(String name)
    {
        FeedProfile theProfile;
        if(profilesHashMap.get(name.trim()) == null)
        {
            theProfile = new FeedProfile(name);
        }
        else
        {
            theProfile = profilesHashMap.get(name.trim());
        }
        return theProfile;
    };        
}
