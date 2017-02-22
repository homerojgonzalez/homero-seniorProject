/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObjects;

import org.junit.Test;
import static org.junit.Assert.*;
import DataObjects.*;

/**
 *
 * @author jtroxel
 */
public class DataObjectTestSuite {
    
    public DataObjectTestSuite() {
    }
    
    @Test
    public void ProfileUnitSettingsAreSet()
    {
        Profile p = new Profile("DUMMY","{'DUMMY_VALUE':0}","");
        assertEquals(p.getUnitSetting("DUMMY_VALUE"), 0); 
    }

    @Test
    public void ProfileDisplayPrefsAreSet()
    {
        Profile p = new Profile("DUMMY","","{'DUMMY_VALUE':0}");
        assertEquals(p.getDisplayPref("DUMMY_VALUE"), 0); 
    } 
    
    @Test
    public void ProfileDisplayPrefOutputIsFormatted()
    {
        Profile p = new Profile("DUMMY","","{'DUMMY_VALUE':0}");
        assertEquals(p.getDisplayPrefsForStorage(), "{'DUMMY_VALUE':0}");
    }
    
    @Test
    public void ProfileUnitSettingsOutputIsFormatted()
    {
        Profile p = new Profile("DUMMY","{'DUMMY_VALUE':0}","");
        assertEquals(p.getUnitSettingsForStorage(),"{'DUMMY_VALUE':0}");
    }
    
        @Test
    public void ProfileUnitSettingsReturnsDefault()
    {
        Profile p = new Profile("DUMMY","{'DUMMY_VALUE':0}","");
        assertEquals(p.getUnitSetting("FAKE_DUMMY_VALUE"), -1); 
    }

    @Test
    public void ProfileDisplayPrefsReturnsDefault()
    {
        Profile p = new Profile("DUMMY","","{'DUMMY_VALUE':0}");
        assertEquals(p.getDisplayPref("FAKE_DUMMY_VALUE"), -1); 
    }
    
    @Test
    public void EnvironmentalAltitudeDensityIsCalculated()
    {
        Environmental e = new Environmental();
        e.setTemperature(75);
        e.setPressure(20);
        e.calculateAltitudeDensity();
        assertEquals(e.getAltitudeDensity(), 9020);
    }
    
    
}
