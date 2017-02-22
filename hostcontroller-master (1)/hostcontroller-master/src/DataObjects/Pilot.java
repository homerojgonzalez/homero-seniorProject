/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DataObjects;

/**
 *  This Class stores the data about a Pilot
 * 
 * @author awilliams5, dbennett3, Noah Fujioka
 */
public class Pilot {
    
    int pilotId;                //randomly genrated id
    String lastName;            //Pilot's last name
    String firstName;           //Pilot's first name
    String middleName;          //Pilot's middle name
    float flightWeight;         //Pilot's weight
    String capability;          //Pilot's capability
    float preference;           //Pilot's launch preference
    String emergencyContact;    //Emergency Contact name
    String emergencyPhone;      //Emergency Contact number
    String optional_info;
    
    //constructors
    public Pilot(int pilotId, String firstName, String lastName, String middleName,
            float weight, String capability, float preference, String emergencyContact,
            String medInfo, String optional) {
        this.pilotId = pilotId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.flightWeight = weight;
        this.capability = capability;
        this.preference = preference;
        this.emergencyContact = emergencyContact;
        this.emergencyPhone = medInfo;
        this.optional_info = optional;
    }
    
    //setters and getters
    
    public int getPilotId() {
        return pilotId;
    }
    public void setPilotId(int newId) {
        pilotId = newId;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public float getWeight() {
        return flightWeight;
    }
    
    public String getCapability() {
        return capability;
    }
    
    public float getPreference() {
        return preference;
    }
    
    public String getEmergencyName() {
        return emergencyContact;
    }
    
    public String getEmergencyPhone() {
        return emergencyPhone;
    }
    
    public String getOptionalInfo() {
        return optional_info;
    }
    
    @Override
    public String toString() { 
        return (firstName + " " + middleName + " " + lastName);
    }
    
    //check if pilot is set
    public boolean check() {
        return !lastName.equals("") && !firstName.equals("") && flightWeight != 0;
    }
    
    /**
     * 
     * @param other Pilot being compared to this pilot
     * @return true if the pilot id's are the same 
     *
    public boolean pilotEquals(Pilot other) {
        return pilotId == other.pilotId;
    }
    */
}
