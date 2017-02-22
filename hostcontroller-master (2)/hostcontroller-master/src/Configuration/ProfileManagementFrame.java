package Configuration;

import static Communications.ErrorLogger.logError;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Operator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.util.Random;
import Communications.Observer;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntrySelect;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;



public class ProfileManagementFrame extends JFrame {
    private Observer parent;
    private JPanel contentPane;
    private ProfilePilotPanel ProfilePilotPanel;
    private ProfileGliderPanel ProfileGliderPanel;
    private ProfileAirfieldPanel ProfileAirfieldPanel;
    private ProfileDisplayPanel ProfileDisplayPanel;
    private ProfileOtherPanel ProfileOtherPanel;
    private SaveAsNewFrame SaveAsNewFrame;
    private List<Operator> names = new ArrayList<Operator>();
    private JScrollPane profileScrollPane;
    private JList profileJList;
    private CurrentDataObjectSet currentData;
    private String flightWeightUnits;
    private String airfieldAltitudeUnits;
    private String gliderPosAltitudeUnits;
    private String runwayMagneticHeadingUnits;
    private String winchPosAltitudeUnits;
    private String emptyWeightUnits;
    private String stallSpeedUnits;
    private String weakLinkStrengthUnits;
    private String maxWinchingSpeedUnits;
    private String maxTensionUnits;
    private String maxGrossWeightUnits;
    private String ballastWeightUnits;
    private String baggageWeightUnits;
    private String passengerWeightUnits;
    private String avgWindSpeedUnits;
    private String crosswindUnits;
    private String densityAltitudeUnits;
    private String gustWindSpeedUnits;
    private String headwindUnits;
    private String launchWeightUnits;
    private String pressureUnits;
    private String runDirectionUnits;
    private String runLengthUnits;
    private String temperatureUnits;
    private String windDirectionUnits;
    private int flightWeightUnitsID;
    private int airfieldAltitudeUnitsID;
    private int gliderPosAltitudeUnitsID;
    private int runwayMagneticHeadingUnitsID;
    private int winchPosAltitudeUnitsID;
    private int emptyWeightUnitsID;
    private int maxGrossWeightUnitsID;
    private int stallSpeedUnitsID;
    private int tensionUnitsID;
    private int weakLinkStrengthUnitsID;
    private int winchingSpeedUnitsID;
    private int ballastWeightUnitsID;
    private int baggageWeightUnitsID;
    private int passengerWeightUnitsID;
    private int avgWindSpeedUnitsID;
    private int crosswindUnitsID;
    private int densityAltitudeUnitsID;
    private int gustWindSpeedUnitsID;
    private int headwindUnitsID;
    private int launchWeightUnitsID;
    private int pressureUnitsID;
    private int runDirectionUnitsID;
    private int runLengthUnitsID;
    private int temperatureUnitsID;
    private int windDirectionUnitsID;
    private JButton saveButton;
    private JButton saveAsNewButton;
    private JButton addNewButton;
    
    public void setParent(Observer p)
    {
        parent = p;
    }
    
    public void getUnitsForProfile()
    {
        flightWeightUnits = (String)ProfilePilotPanel.flightWeightComboBox.getSelectedItem();
        airfieldAltitudeUnits = (String)ProfileAirfieldPanel.airfieldAltitudeComboBox.getSelectedItem();
        gliderPosAltitudeUnits = (String)ProfileAirfieldPanel.gliderPosAltitudeComboBox.getSelectedItem();
        runwayMagneticHeadingUnits = (String)ProfileAirfieldPanel.runwayMagneticHeadingComboBox.getSelectedItem();
        winchPosAltitudeUnits = (String)ProfileAirfieldPanel.winchPosAltitudeComboBox.getSelectedItem();
        emptyWeightUnits = (String)ProfileGliderPanel.emptyWeightComboBox.getSelectedItem();
        maxGrossWeightUnits = (String)ProfileGliderPanel.maxGrossWeightComboBox.getSelectedItem();
        stallSpeedUnits = (String)ProfileGliderPanel.stallSpeedComboBox.getSelectedItem();
        weakLinkStrengthUnits = (String)ProfileGliderPanel.weakLinkStrengthComboBox.getSelectedItem();
        maxWinchingSpeedUnits = (String)ProfileGliderPanel.maxWinchingSpeedComboBox.getSelectedItem();
        maxTensionUnits = (String)ProfileGliderPanel.maxTensionComboBox.getSelectedItem();
        ballastWeightUnits = (String)ProfileGliderPanel.ballastWeightComboBox.getSelectedItem();
        baggageWeightUnits = (String)ProfileGliderPanel.baggageWeightComboBox.getSelectedItem();
        passengerWeightUnits = (String)ProfileGliderPanel.passengerWeightComboBox.getSelectedItem();       
        avgWindSpeedUnits = (String) ProfileOtherPanel.avgWindSpeedComboBox.getSelectedItem();
        crosswindUnits = (String) ProfileOtherPanel.crosswindComboBox.getSelectedItem();
        densityAltitudeUnits = (String) ProfileOtherPanel.densityAltitudeComboBox.getSelectedItem();
        gustWindSpeedUnits = (String) ProfileOtherPanel.gustWindSpeedComboBox.getSelectedItem();
        headwindUnits = (String) ProfileOtherPanel.headwindComboBox.getSelectedItem();
        launchWeightUnits = (String) ProfileOtherPanel.launchWeightComboBox.getSelectedItem();
        pressureUnits = (String) ProfileOtherPanel.pressureComboBox.getSelectedItem();
        runDirectionUnits = (String) ProfileOtherPanel.runDirectionComboBox.getSelectedItem();
        runLengthUnits = (String) ProfileOtherPanel.runLengthComboBox.getSelectedItem();
        temperatureUnits = (String) ProfileOtherPanel.temperatureComboBox.getSelectedItem();
        windDirectionUnits = (String) ProfileOtherPanel.windDirectionComboBox.getSelectedItem();
    }
    
    public void selectButtonClicked()
    {
        if(profileJList.getSelectedIndex() >= 0){
            Operator selectedProfile = (Operator)profileJList.getSelectedValue();
            currentData.setCurrentProfile(selectedProfile);
            parent.update();
        }
        dispose();
    }

    public void saveButtonClicked()
    {
        getUnitsForProfile();
        Operator currentProfile_ = currentData.getCurrentProfile();
        currentProfile_.setUnitSetting("flightWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(flightWeightUnits));
        currentProfile_.setUnitSetting("emptyWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(emptyWeightUnits));
        currentProfile_.setUnitSetting("maxGrossWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(maxGrossWeightUnits));
        currentProfile_.setUnitSetting("ballastWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(ballastWeightUnits));
        currentProfile_.setUnitSetting("baggageWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(baggageWeightUnits));
        currentProfile_.setUnitSetting("passengerWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(passengerWeightUnits));
        currentProfile_.setUnitSetting("airfieldAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(airfieldAltitudeUnits));
        currentProfile_.setUnitSetting("gliderPosAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(gliderPosAltitudeUnits));
        currentProfile_.setUnitSetting("winchPosAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(winchPosAltitudeUnits));
        currentProfile_.setUnitSetting("runwayMagneticHeading", UnitConversionToIndexUtilities.degreesUnitStringToIndex(runwayMagneticHeadingUnits));
        currentProfile_.setUnitSetting("maxTension", UnitConversionToIndexUtilities.tensionUnitStringToIndex(maxTensionUnits));
        currentProfile_.setUnitSetting("weakLinkStrength", UnitConversionToIndexUtilities.tensionUnitStringToIndex(weakLinkStrengthUnits));
        currentProfile_.setUnitSetting("stallSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(stallSpeedUnits));
        currentProfile_.setUnitSetting("winchingSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(maxWinchingSpeedUnits));
        currentProfile_.setUnitSetting("avgWindSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(avgWindSpeedUnits));
        currentProfile_.setUnitSetting("crosswind", UnitConversionToIndexUtilities.velocityUnitStringToIndex(crosswindUnits));
        currentProfile_.setUnitSetting("gustWindSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(gustWindSpeedUnits));
        currentProfile_.setUnitSetting("headwind", UnitConversionToIndexUtilities.velocityUnitStringToIndex(headwindUnits));
        currentProfile_.setUnitSetting("launchWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(launchWeightUnits));
        currentProfile_.setUnitSetting("densityAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(densityAltitudeUnits));
        currentProfile_.setUnitSetting("runLength", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(runLengthUnits));
        currentProfile_.setUnitSetting("pressure", UnitConversionToIndexUtilities.pressureUnitStringToIndex(pressureUnits));
        currentProfile_.setUnitSetting("temperature", UnitConversionToIndexUtilities.tempUnitStringToIndex(temperatureUnits));
        currentProfile_.setUnitSetting("runDirection", UnitConversionToIndexUtilities.degreesUnitStringToIndex(runDirectionUnits));
        currentProfile_.setUnitSetting("windDirection", UnitConversionToIndexUtilities.degreesUnitStringToIndex(windDirectionUnits));
        currentData.setCurrentProfile(currentProfile_);
        parent.update();
        try {
            DatabaseEntryEdit.UpdateEntry(currentProfile_); 
        } catch (Exception e) {
            e.printStackTrace();
            //do nothing for now...
        }
        //System.out.println(currentData.getCurrentProfile().getUnitSettingsForStorage());
    }

    public void addNewButtonClicked()
    {
        getUnitsForProfile();
        Random randomId = new Random();
        int temp = randomId.nextInt(100000000);
        Operator newProfile = new Operator(temp,"{}","{}");
        
        newProfile.setUnitSetting("flightWeight", 1);
        newProfile.setUnitSetting("emptyWeight", 1);
        newProfile.setUnitSetting("maxGrossWeight", 1);
        newProfile.setUnitSetting("stallSpeed", 1);
        newProfile.setUnitSetting("ballastWeight", 1);
        newProfile.setUnitSetting("baggageWeight", 1);
        newProfile.setUnitSetting("passengerWeight", 1);
        newProfile.setUnitSetting("maxTension", 1);
        newProfile.setUnitSetting("weakLinkStrength", 1);
        newProfile.setUnitSetting("winchingSpeed", 1);
        newProfile.setUnitSetting("airfieldAltitude", 1);
        newProfile.setUnitSetting("gliderPosAltitude", 1);
        newProfile.setUnitSetting("runwayMagneticHeading", 1);
        newProfile.setUnitSetting("winchPosAltitude", 1);
        newProfile.setUnitSetting("avgWindSpeed", 1);
        newProfile.setUnitSetting("crosswind", 1);
        newProfile.setUnitSetting("gustWindSpeed", 1);
        newProfile.setUnitSetting("headwind", 1);
        newProfile.setUnitSetting("launchWeight", 1);
        newProfile.setUnitSetting("densityAltitude", 1);
        newProfile.setUnitSetting("runLength", 1);
        newProfile.setUnitSetting("pressure", 4);
        newProfile.setUnitSetting("temperature", 1);
        newProfile.setUnitSetting("runDirection", 1);
        newProfile.setUnitSetting("windDirection", 1);
        
        currentData.setCurrentProfile(newProfile);
        SaveAsNewFrame = new SaveAsNewFrame();
        SaveAsNewFrame.setParent(getCurrentProfileManagementFrame());
        SaveAsNewFrame.setVisible(true);
        parent.update();
    }
    
    public void saveAsNewButtonClicked()
    {
        getUnitsForProfile();
        Random randomId = new Random();
        int temp = randomId.nextInt(100000000);
        Operator newProfile = new Operator(temp,"{}","{}");
        newProfile.setUnitSetting("flightWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(flightWeightUnits));
        newProfile.setUnitSetting("emptyWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(emptyWeightUnits));
        newProfile.setUnitSetting("maxGrossWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(maxGrossWeightUnits));
        newProfile.setUnitSetting("ballastWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(ballastWeightUnits));
        newProfile.setUnitSetting("baggageWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(baggageWeightUnits));
        newProfile.setUnitSetting("passengerWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(passengerWeightUnits));
        newProfile.setUnitSetting("airfieldAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(airfieldAltitudeUnits));
        newProfile.setUnitSetting("gliderPosAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(gliderPosAltitudeUnits));
        newProfile.setUnitSetting("winchPosAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(winchPosAltitudeUnits));
        newProfile.setUnitSetting("runwayMagneticHeading", UnitConversionToIndexUtilities.degreesUnitStringToIndex(runwayMagneticHeadingUnits));
        newProfile.setUnitSetting("maxTension", UnitConversionToIndexUtilities.tensionUnitStringToIndex(maxTensionUnits));
        newProfile.setUnitSetting("weakLinkStrength", UnitConversionToIndexUtilities.tensionUnitStringToIndex(weakLinkStrengthUnits));
        newProfile.setUnitSetting("stallSpeed", UnitConversionToIndexUtilities.tensionUnitStringToIndex(stallSpeedUnits));
        newProfile.setUnitSetting("winchingSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(maxWinchingSpeedUnits));
        newProfile.setUnitSetting("avgWindSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(avgWindSpeedUnits));
        newProfile.setUnitSetting("crosswind", UnitConversionToIndexUtilities.velocityUnitStringToIndex(crosswindUnits));
        newProfile.setUnitSetting("gustWindSpeed", UnitConversionToIndexUtilities.velocityUnitStringToIndex(gustWindSpeedUnits));
        newProfile.setUnitSetting("headwind", UnitConversionToIndexUtilities.velocityUnitStringToIndex(headwindUnits));
        newProfile.setUnitSetting("launchWeight", UnitConversionToIndexUtilities.weightUnitStringToIndex(launchWeightUnits));
        newProfile.setUnitSetting("densityAltitude", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(densityAltitudeUnits));
        newProfile.setUnitSetting("runLength", UnitConversionToIndexUtilities.lenghtUnitStringToIndex(runLengthUnits));
        newProfile.setUnitSetting("pressure", UnitConversionToIndexUtilities.pressureUnitStringToIndex(pressureUnits));
        newProfile.setUnitSetting("temperature", UnitConversionToIndexUtilities.tempUnitStringToIndex(temperatureUnits));
        newProfile.setUnitSetting("runDirection", UnitConversionToIndexUtilities.degreesUnitStringToIndex(runDirectionUnits));
        newProfile.setUnitSetting("windDirection", UnitConversionToIndexUtilities.degreesUnitStringToIndex(windDirectionUnits));
        currentData.setCurrentProfile(newProfile);
        SaveAsNewFrame = new SaveAsNewFrame();
        SaveAsNewFrame.setParent(getCurrentProfileManagementFrame());
        SaveAsNewFrame.setVisible(true);
        parent.update();
    }

    private void initProfileList() 
    {
            names = DatabaseEntrySelect.getOperators();
    }

    /**
    * Creates new ProfileManagementFrame
    */
    public ProfileManagementFrame()
    {
       currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
       initProfileList();
       initComponents();
    }

    private ProfileManagementFrame getCurrentProfileManagementFrame()
    {
        return this;
    }

    private void profileJListSelectionChanged(ListSelectionEvent listSelectionEvent) {
        if(profileJList.getSelectedIndex() >= 0){
            try{
                Operator selectedProfile = (Operator)profileJList.getSelectedValue();
                currentData.setCurrentProfile(selectedProfile);
                
                flightWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("flightWeight");
                String flightWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(flightWeightUnitsID);
                ProfilePilotPanel.flightWeightComboBox.setSelectedItem(flightWeightUnitsString);
                ProfilePilotPanel.flightWeightComboBox.setEnabled(true);
                
                airfieldAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("airfieldAltitude");
                String airfieldAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(airfieldAltitudeUnitsID);
                ProfileAirfieldPanel.airfieldAltitudeComboBox.setSelectedItem(airfieldAltitudeUnitsString);
                ProfileAirfieldPanel.airfieldAltitudeComboBox.setEnabled(true);
                
                gliderPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("gliderPosAltitude");
                String gliderPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(gliderPosAltitudeUnitsID);
                ProfileAirfieldPanel.gliderPosAltitudeComboBox.setSelectedItem(gliderPosAltitudeUnitsString);
                ProfileAirfieldPanel.gliderPosAltitudeComboBox.setEnabled(true);
                
                runwayMagneticHeadingUnitsID = currentData.getCurrentProfile().getUnitSetting("runwayMagneticHeading");
                String runwayMagneticHeadingUnitsString = UnitLabelUtilities.degreesUnitIndexToString(runwayMagneticHeadingUnitsID);
                ProfileAirfieldPanel.runwayMagneticHeadingComboBox.setSelectedItem(runwayMagneticHeadingUnitsString);
                ProfileAirfieldPanel.runwayMagneticHeadingComboBox.setEnabled(true);
                
                winchPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("winchPosAltitude");
                String winchPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(winchPosAltitudeUnitsID);
                ProfileAirfieldPanel.winchPosAltitudeComboBox.setSelectedItem(winchPosAltitudeUnitsString);
                ProfileAirfieldPanel.winchPosAltitudeComboBox.setEnabled(true);
                
                emptyWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("emptyWeight");
                String emptyWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(emptyWeightUnitsID);
                ProfileGliderPanel.emptyWeightComboBox.setSelectedItem(emptyWeightUnitsString);
                ProfileGliderPanel.emptyWeightComboBox.setEnabled(true);
                
                maxGrossWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("maxGrossWeight");
                String maxGrossWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(maxGrossWeightUnitsID);
                ProfileGliderPanel.maxGrossWeightComboBox.setSelectedItem(maxGrossWeightUnitsString);
                ProfileGliderPanel.maxGrossWeightComboBox.setEnabled(true);
                
                stallSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("stallSpeed");
                String stallSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(stallSpeedUnitsID);
                ProfileGliderPanel.stallSpeedComboBox.setSelectedItem(stallSpeedUnitsString);
                ProfileGliderPanel.stallSpeedComboBox.setEnabled(true);
                
                ballastWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("ballastWeight");
                String ballastWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(ballastWeightUnitsID);
                ProfileGliderPanel.ballastWeightComboBox.setSelectedItem(ballastWeightUnitsString);
                ProfileGliderPanel.ballastWeightComboBox.setEnabled(true);
                
                baggageWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("baggageWeight");
                String baggageWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(baggageWeightUnitsID);
                ProfileGliderPanel.baggageWeightComboBox.setSelectedItem(baggageWeightUnitsString);
                ProfileGliderPanel.baggageWeightComboBox.setEnabled(true);

                passengerWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("passengerWeight");
                String passengerWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(passengerWeightUnitsID);
                ProfileGliderPanel.passengerWeightComboBox.setSelectedItem(passengerWeightUnitsString);
                ProfileGliderPanel.passengerWeightComboBox.setEnabled(true);
                    
                tensionUnitsID = currentData.getCurrentProfile().getUnitSetting("maxTension");
                String tensionUnitsString = UnitLabelUtilities.tensionUnitIndexToString(tensionUnitsID);
                ProfileGliderPanel.maxTensionComboBox.setSelectedItem(tensionUnitsString);
                ProfileGliderPanel.maxTensionComboBox.setEnabled(true);
                
                weakLinkStrengthUnitsID = currentData.getCurrentProfile().getUnitSetting("weakLinkStrength");
                String weakLinkStrengthUnitsString = UnitLabelUtilities.tensionUnitIndexToString(weakLinkStrengthUnitsID);
                ProfileGliderPanel.weakLinkStrengthComboBox.setSelectedItem(weakLinkStrengthUnitsString);
                ProfileGliderPanel.weakLinkStrengthComboBox.setEnabled(true);
                
                winchingSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("winchingSpeed");
                String winchingSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(winchingSpeedUnitsID);
                ProfileGliderPanel.maxWinchingSpeedComboBox.setSelectedItem(winchingSpeedUnitsString);
                ProfileGliderPanel.maxWinchingSpeedComboBox.setEnabled(true);
                
                avgWindSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("avgWindSpeed");
                String avgWindSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(avgWindSpeedUnitsID);
                ProfileOtherPanel.avgWindSpeedComboBox.setSelectedItem(avgWindSpeedUnitsString);
                ProfileOtherPanel.avgWindSpeedComboBox.setEnabled(true);
                
                crosswindUnitsID = currentData.getCurrentProfile().getUnitSetting("crosswind");
                String crosswindUnitsString = UnitLabelUtilities.velocityUnitIndexToString(crosswindUnitsID);
                ProfileOtherPanel.crosswindComboBox.setSelectedItem(crosswindUnitsString);
                ProfileOtherPanel.crosswindComboBox.setEnabled(true);
                
                densityAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("densityAltitude");
                String densityAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(densityAltitudeUnitsID);
                ProfileOtherPanel.densityAltitudeComboBox.setSelectedItem(densityAltitudeUnitsString);
                ProfileOtherPanel.densityAltitudeComboBox.setEnabled(true);
                
                gustWindSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("gustWindSpeed");
                String gustWindSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(gustWindSpeedUnitsID);
                ProfileOtherPanel.gustWindSpeedComboBox.setSelectedItem(gustWindSpeedUnitsString);
                ProfileOtherPanel.gustWindSpeedComboBox.setEnabled(true);
                
                headwindUnitsID = currentData.getCurrentProfile().getUnitSetting("headwind");
                String headwindUnitsString = UnitLabelUtilities.velocityUnitIndexToString(headwindUnitsID);
                ProfileOtherPanel.headwindComboBox.setSelectedItem(headwindUnitsString);
                ProfileOtherPanel.headwindComboBox.setEnabled(true);
                
                launchWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("launchWeight");
                String launchWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(launchWeightUnitsID);
                ProfileOtherPanel.launchWeightComboBox.setSelectedItem(launchWeightUnitsString);
                ProfileOtherPanel.launchWeightComboBox.setEnabled(true);
                
                pressureUnitsID = currentData.getCurrentProfile().getUnitSetting("pressure");
                String pressureUnitsString = UnitLabelUtilities.pressureUnitIndexToString(pressureUnitsID);
                ProfileOtherPanel.pressureComboBox.setSelectedItem(pressureUnitsString);
                ProfileOtherPanel.pressureComboBox.setEnabled(true);
                
                runDirectionUnitsID = currentData.getCurrentProfile().getUnitSetting("runDirection");
                String runDirectionUnitsString = UnitLabelUtilities.degreesUnitIndexToString(runDirectionUnitsID);
                ProfileOtherPanel.runDirectionComboBox.setSelectedItem(runDirectionUnitsString);
                ProfileOtherPanel.runDirectionComboBox.setEnabled(true);
                
                windDirectionUnitsID = currentData.getCurrentProfile().getUnitSetting("windDirection");
                String windDirectionUnitsUnitsString = UnitLabelUtilities.degreesUnitIndexToString(windDirectionUnitsID);
                ProfileOtherPanel.windDirectionComboBox.setSelectedItem(windDirectionUnitsUnitsString);
                ProfileOtherPanel.windDirectionComboBox.setEnabled(true);
                
                runLengthUnitsID = currentData.getCurrentProfile().getUnitSetting("runLength");
                String runLengthUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(runLengthUnitsID);
                ProfileOtherPanel.runLengthComboBox.setSelectedItem(runLengthUnitsString);
                ProfileOtherPanel.runLengthComboBox.setEnabled(true);
                
                temperatureUnitsID = currentData.getCurrentProfile().getUnitSetting("temperature");
                String temperatureUnitsString = UnitLabelUtilities.tempUnitIndexToString(temperatureUnitsID);
                ProfileOtherPanel.temperatureComboBox.setSelectedItem(temperatureUnitsString);
                ProfileOtherPanel.temperatureComboBox.setEnabled(true);
                
                saveButton.setEnabled(true);
                saveAsNewButton.setEnabled(true);
                parent.update();
            } catch(Exception e) {
                //TODO respond to error
            }
        }
    }

    public void Rebuild()
    {
        initProfileList();
        DefaultListModel profileModel = new DefaultListModel();
        for(Object str: names){
            profileModel.addElement(str);
        }
        profileJList.setModel(profileModel);
        profileScrollPane.setViewportView(profileJList);
                
        flightWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("flightWeight");
        String flightWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(flightWeightUnitsID);
        ProfilePilotPanel.flightWeightComboBox.setSelectedItem(flightWeightUnitsString);
        
        airfieldAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("airfieldAltitude");
        String airfieldAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(airfieldAltitudeUnitsID);
        ProfileAirfieldPanel.airfieldAltitudeComboBox.setSelectedItem(airfieldAltitudeUnitsString);

        gliderPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("gliderPosAltitude");
        String gliderPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(gliderPosAltitudeUnitsID);
        ProfileAirfieldPanel.gliderPosAltitudeComboBox.setSelectedItem(gliderPosAltitudeUnitsString);

        runwayMagneticHeadingUnitsID = currentData.getCurrentProfile().getUnitSetting("runwayMagneticHeading");
        String runwayMagneticHeadingUnitsString = UnitLabelUtilities.degreesUnitIndexToString(runwayMagneticHeadingUnitsID);
        ProfileAirfieldPanel.runwayMagneticHeadingComboBox.setSelectedItem(runwayMagneticHeadingUnitsString);

        winchPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("winchPosAltitude");
        String winchPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(winchPosAltitudeUnitsID);
        ProfileAirfieldPanel.winchPosAltitudeComboBox.setSelectedItem(winchPosAltitudeUnitsString);
        
        emptyWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("emptyWeight");
        String emptyWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(emptyWeightUnitsID);
        ProfileGliderPanel.emptyWeightComboBox.setSelectedItem(emptyWeightUnitsString);
        
        maxGrossWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("maxGrossWeight");
        String maxGrossWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(maxGrossWeightUnitsID);
        ProfileGliderPanel.maxGrossWeightComboBox.setSelectedItem(maxGrossWeightUnitsString);
        
        stallSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("stallSpeed");
        String stallSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(stallSpeedUnitsID);
        ProfileGliderPanel.stallSpeedComboBox.setSelectedItem(stallSpeedUnitsString);
        
        ballastWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("ballastWeight");
        String ballastWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(ballastWeightUnitsID);
        ProfileGliderPanel.ballastWeightComboBox.setSelectedItem(ballastWeightUnitsString);
        
        baggageWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("baggageWeight");
        String baggageWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(baggageWeightUnitsID);
        ProfileGliderPanel.baggageWeightComboBox.setSelectedItem(baggageWeightUnitsString);
        
        passengerWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("passengerWeight");
        String passengerWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(passengerWeightUnitsID);
        ProfileGliderPanel.passengerWeightComboBox.setSelectedItem(passengerWeightUnitsString);
        
        tensionUnitsID = currentData.getCurrentProfile().getUnitSetting("maxTension");
        String tensionUnitsString = UnitLabelUtilities.tensionUnitIndexToString(tensionUnitsID);
        ProfileGliderPanel.maxTensionComboBox.setSelectedItem(tensionUnitsString);
        
        weakLinkStrengthUnitsID = currentData.getCurrentProfile().getUnitSetting("weakLinkStrength");
        String weakLinkStrengthUnitsString = UnitLabelUtilities.tensionUnitIndexToString(weakLinkStrengthUnitsID);
        ProfileGliderPanel.weakLinkStrengthComboBox.setSelectedItem(weakLinkStrengthUnitsString);
        
        winchingSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("winchingSpeed");
        String winchingSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(winchingSpeedUnitsID);
        ProfileGliderPanel.maxWinchingSpeedComboBox.setSelectedItem(winchingSpeedUnitsString);
        
        avgWindSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("avgWindSpeed");
        String avgWindSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(avgWindSpeedUnitsID);
        ProfileOtherPanel.avgWindSpeedComboBox.setSelectedItem(avgWindSpeedUnitsString);

        crosswindUnitsID = currentData.getCurrentProfile().getUnitSetting("crosswind");
        String crosswindUnitsString = UnitLabelUtilities.velocityUnitIndexToString(crosswindUnitsID);
        ProfileOtherPanel.crosswindComboBox.setSelectedItem(crosswindUnitsString);

        densityAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("densityAltitude");
        String densityAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(densityAltitudeUnitsID);
        ProfileOtherPanel.densityAltitudeComboBox.setSelectedItem(densityAltitudeUnitsString);

        gustWindSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("gustWindSpeed");
        String gustWindSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(gustWindSpeedUnitsID);
        ProfileOtherPanel.gustWindSpeedComboBox.setSelectedItem(gustWindSpeedUnitsString);

        headwindUnitsID = currentData.getCurrentProfile().getUnitSetting("headwind");
        String headwindUnitsString = UnitLabelUtilities.velocityUnitIndexToString(headwindUnitsID);
        ProfileOtherPanel.headwindComboBox.setSelectedItem(headwindUnitsString);

        launchWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("launchWeight");
        String launchWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(launchWeightUnitsID);
        ProfileOtherPanel.launchWeightComboBox.setSelectedItem(launchWeightUnitsString);

        pressureUnitsID = currentData.getCurrentProfile().getUnitSetting("pressure");
        String pressureUnitsString = UnitLabelUtilities.pressureUnitIndexToString(pressureUnitsID);
        ProfileOtherPanel.pressureComboBox.setSelectedItem(pressureUnitsString);

        runDirectionUnitsID = currentData.getCurrentProfile().getUnitSetting("runDirection");
        String runDirectionUnitsString = UnitLabelUtilities.degreesUnitIndexToString(runDirectionUnitsID);
        ProfileOtherPanel.runDirectionComboBox.setSelectedItem(runDirectionUnitsString);

        windDirectionUnitsID = currentData.getCurrentProfile().getUnitSetting("windDirection");
        String windDirectionUnitsUnitsString = UnitLabelUtilities.degreesUnitIndexToString(windDirectionUnitsID);
        ProfileOtherPanel.windDirectionComboBox.setSelectedItem(windDirectionUnitsUnitsString);

        runLengthUnitsID = currentData.getCurrentProfile().getUnitSetting("runLength");
        String runLengthUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(runLengthUnitsID);
        ProfileOtherPanel.runLengthComboBox.setSelectedItem(runLengthUnitsString);

        temperatureUnitsID = currentData.getCurrentProfile().getUnitSetting("temperature");
        String temperatureUnitsString = UnitLabelUtilities.tempUnitIndexToString(temperatureUnitsID);
        ProfileOtherPanel.temperatureComboBox.setSelectedItem(temperatureUnitsString);
    }

    private void initComponents() {
        setTitle("Operator Profile");
        ProfilePilotPanel = new ProfilePilotPanel();
        ProfileGliderPanel = new ProfileGliderPanel();
        ProfileAirfieldPanel = new ProfileAirfieldPanel();
        ProfileDisplayPanel = new ProfileDisplayPanel();
        ProfileOtherPanel = new ProfileOtherPanel();
        profileScrollPane = new javax.swing.JScrollPane();
        profileJList = new javax.swing.JList();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 1071, 704);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        contentPane.add(profileScrollPane, BorderLayout.WEST);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        contentPane.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JButton selectButton = new JButton("Set as Current Profile");
        selectButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selectButtonClicked();
                    }
        });
        selectButton.setBackground(new Color(200,200,200));
        panel.add(selectButton);
       
        addNewButton = new JButton("Add New");
        addNewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    addNewButtonClicked();
                    }
        });
        addNewButton.setBackground(new Color(200,200,200));
        panel.add(addNewButton);
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    saveButtonClicked();
                    }
        });
        saveButton.setBackground(new Color(200,200,200));
        saveButton.setEnabled(false);
        panel.add(saveButton);

        saveAsNewButton = new JButton("Save as new");
        saveAsNewButton.setBackground(new Color(200,200,200));
        saveAsNewButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    saveAsNewButtonClicked();
                }
        });
        saveAsNewButton.setEnabled(false);
        panel.add(saveAsNewButton);

        profileJList.setPreferredSize(new Dimension(200,100));
        DefaultListModel profileModel = new DefaultListModel();
        for(Object str: names){
            profileModel.addElement(str);
        }
        profileJList.setModel(profileModel);
        profileJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                profileJListSelectionChanged(listSelectionEvent);
            }
        });
        profileScrollPane.setViewportView(profileJList);

        JButton resetButton = new JButton("Reset to default");
        resetButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    ProfilePilotPanel.flightWeightComboBox.setSelectedItem("lbs");
                    ProfileAirfieldPanel.airfieldAltitudeComboBox.setSelectedItem("ft");
                    ProfileAirfieldPanel.gliderPosAltitudeComboBox.setSelectedItem("ft");
                    ProfileAirfieldPanel.runwayMagneticHeadingComboBox.setSelectedItem("magnetic");
                    ProfileAirfieldPanel.winchPosAltitudeComboBox.setSelectedItem("ft");
                    ProfileGliderPanel.emptyWeightComboBox.setSelectedItem("lbs");
                    ProfileGliderPanel.maxGrossWeightComboBox.setSelectedItem("lbs");
                    ProfileGliderPanel.stallSpeedComboBox.setSelectedItem("mph");
                    ProfileGliderPanel.weakLinkStrengthComboBox.setSelectedItem("lbf");
                    ProfileGliderPanel.maxWinchingSpeedComboBox.setSelectedItem("mph");;
                    ProfileGliderPanel.maxTensionComboBox.setSelectedItem("lbf");
                    ProfileGliderPanel.ballastWeightComboBox.setSelectedItem("lbs");
                    ProfileGliderPanel.baggageWeightComboBox.setSelectedItem("lbs");
                    ProfileGliderPanel.passengerWeightComboBox.setSelectedItem("lbs");
                    ProfileOtherPanel.avgWindSpeedComboBox.setSelectedItem("mph");
                    ProfileOtherPanel.crosswindComboBox.setSelectedItem("mph");
                    ProfileOtherPanel.densityAltitudeComboBox.setSelectedItem("ft");
                    ProfileOtherPanel.gustWindSpeedComboBox.setSelectedItem("mph");
                    ProfileOtherPanel.headwindComboBox.setSelectedItem("mph");
                    ProfileOtherPanel.launchWeightComboBox.setSelectedItem("lbs");
                    ProfileOtherPanel.pressureComboBox.setSelectedItem("millibar");
                    ProfileOtherPanel.runDirectionComboBox.setSelectedItem("magnetic");
                    ProfileOtherPanel.runLengthComboBox.setSelectedItem("ft");
                    ProfileOtherPanel.temperatureComboBox.setSelectedItem("F");
                    ProfileOtherPanel.windDirectionComboBox.setSelectedItem("magnetic");
                }
        });
        resetButton.setBackground(new Color(200,200,200));
        panel.add(resetButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(200,200,200));
        cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    dispose();
                }
        });
        panel.add(cancelButton);

        tabbedPane.addTab("Pilot", ProfilePilotPanel);
        tabbedPane.addTab("Glider", ProfileGliderPanel);
        tabbedPane.addTab("Airfield", ProfileAirfieldPanel);
        tabbedPane.addTab("Other", ProfileOtherPanel);
        tabbedPane.addTab("Display", ProfileDisplayPanel);  
    }
}
