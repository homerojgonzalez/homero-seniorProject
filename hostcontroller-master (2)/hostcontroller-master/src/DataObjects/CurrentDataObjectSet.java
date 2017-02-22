package DataObjects;
/**
 *
 * @author Jacob Troxel
 */

import java.util.ArrayList;
import Communications.Observer;

public class CurrentDataObjectSet {
    
    private static CurrentDataObjectSet instance = null;
    private Pilot currentPilot;
    private Sailplane currentSailplane;
    private Operator currentProfile;
    private Runway currentRunway;
    private WinchPosition currentWinchPos;
    private GliderPosition currentGliderPos;
    private Winch currentWinch;
    private Airfield currentAirfield;
    private ArrayList<Observer> observers;
    private Drum currentDrum;
    
    public static CurrentDataObjectSet getCurrentDataObjectSet()
    {
        if(instance == null)
        {
            instance = new CurrentDataObjectSet();
            instance.observers = new ArrayList();
        }
        return instance;
    }
    
    //add an observer
    public void attach(Observer ob)
    {
        observers.add(ob);
    }
    
    //notify's Observers
    public void forceUpdate()
    {
        notifyObservers();
    }
    
    //I don't know why these are seprate
    private void notifyObservers()
    {
        for(Observer ob : observers)
        {
            ob.update();
        }
    }
    
    //use with caution!!!
    //this will clear the current loaded objects.
    public static void Clear()
    {
        instance = null;
    }
    
    //check if object exists
    public static boolean IsInitialized()
    {
        return(instance != null);
    }
    
    //clears a part of the data object
    public void clearPilot()
    {
        if(instance != null) instance.currentPilot = null;
        notifyObservers();
    }    
    public void clearGlider()
    {
        if(instance != null) instance.currentSailplane = null;
        notifyObservers();        
    }
    public void clearAirfield()
    {
        if(instance != null) instance.currentAirfield = null;
        notifyObservers();        
    }
    public void clearRunway()
    {
        if(instance != null) instance.currentRunway = null;
        notifyObservers();        
    }
    public void clearWinchPosition()
    {
        if(instance != null) instance.currentWinchPos = null;
        notifyObservers();        
    }
    public void cleafGliderPosition()
    {
        if(instance != null) instance.currentGliderPos = null;
        notifyObservers();        
    }
    public void clearWinch()
    {
        if(instance != null) instance.currentWinch = null;
        notifyObservers();        
    }
    public void clearProfile()
    {
        if(instance != null) instance.currentProfile = null;
        notifyObservers();        
    }    
    public void clearDrum()
    {
        if(instance != null) instance.currentDrum = null;
        notifyObservers();        
    }
    
    //Setters
    public void setCurrentPilot(Pilot pilot)
    {
        if(instance != null)
        {
            instance.currentPilot = pilot;
        }
        instance.notifyObservers();
    }
    public void setCurrentGlider(Sailplane sailplane)
    {
        if(instance != null)
        {
            instance.currentSailplane = sailplane;
        }       
        instance.notifyObservers();
    }
    public void setCurrentRunway(Runway runway)
    {
        if(instance != null)
        {
            instance.currentRunway = runway;
        }    
        instance.notifyObservers();
    }
    public void setCurrentAirfield(Airfield airfield)
    {
        if(instance != null)
        {
            instance.currentAirfield = airfield;
        }        
        instance.notifyObservers();
    }
    public void setCurrentProfile(Operator profile)
    {
        if(instance != null)
        {
            instance.currentProfile = profile;
        }        
        instance.notifyObservers();
    }
    public void setCurrentGliderPosition(GliderPosition pos)
    {
        if(instance != null)
        {
            instance.currentGliderPos = pos;
        }        
        instance.notifyObservers();
    }
    public void setCurrentWinchPosition(WinchPosition pos)
    {
        if(instance != null)
        {
            instance.currentWinchPos = pos;
        }        
        instance.notifyObservers();
    }
    public void setCurrentWinch(Winch winch)
    {
        if(instance != null)
        {
            instance.currentWinch = winch;
        }        
        instance.notifyObservers();
    }
    public void setCurrentDrum(Drum drum)
    {
        if(instance != null)
        {
            instance.currentDrum = drum;
        }        
        instance.notifyObservers();
    }    
    
    //Getters
    public Pilot getCurrentPilot()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentPilot;
        }
    }
    public Sailplane getCurrentGlider()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentSailplane;
        }        
    }
    public Airfield getCurrentAirfield()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentAirfield;
        }        
    }
    public Runway getCurrentRunway()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentRunway;
        }        
    }
    public GliderPosition getCurrentGliderPosition()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentGliderPos;
        }        
    }
    public WinchPosition getCurrentWinchPosition()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentWinchPos;
        }        
    }
    public Winch getCurrentWinch()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentWinch;
        }        
    }
    public Operator getCurrentProfile()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentProfile;
        }        
    }
    public Drum getCurrentDrum()
    {
        if(instance == null)
        {
            return null;
        }
        else
        {
            return instance.currentDrum;
        }
    }
    
    //check if we're ready to launch
    public boolean check() {
        if(instance == null) {
            return false;
        }
        else {
            boolean check = instance.currentPilot.check();
            check = check && instance.currentSailplane.check();
            check = check && instance.currentAirfield.check();
            check = check && instance.currentRunway.check();
            check = check && instance.currentGliderPos.check();
            check = check && instance.currentWinchPos.check();
            check = check && instance.currentWinch.check();
            check = check && instance.currentDrum.check();
            check = check && instance.currentDrum.getParachute().check();
            return check;
        }
    }
}
