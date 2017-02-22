package ParameterSelection;

import AddEditPanels.AddEditWinchPosFrame;
import AddEditPanels.AddEditGliderPosFrame;
import AddEditPanels.AddEditAirfieldFrame;
import AddEditPanels.AddEditRunwayFrame;
import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.Airfield;
import DataObjects.GliderPosition;
import DataObjects.CurrentDataObjectSet;
import DataObjects.RecentLaunchSelections;
import DataObjects.Runway;
import DataObjects.WinchPosition;
import DatabaseUtilities.DatabaseEntrySelect;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JScrollPane;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class AirfieldPanel extends JPanel implements Observer{

    private javax.swing.JScrollPane airfieldScrollPane;
    private List<Airfield> airfields = new ArrayList<Airfield>();
    private javax.swing.JScrollPane gliderPositionsScrollPane;
    private List<GliderPosition> gliderPositions = new ArrayList<GliderPosition>();
    private javax.swing.JScrollPane winchPositionsScrollPane;
    private List<WinchPosition> winchPositions = new ArrayList<WinchPosition>();
    private javax.swing.JScrollPane runwaysScrollPane;
    private List<Runway> runways = new ArrayList<Runway>();
    DefaultListModel airfieldModel = new DefaultListModel();
    private DefaultListModel runwaysModel = new DefaultListModel();
    private DefaultListModel gliderPositionModel = new DefaultListModel();
    private DefaultListModel winchPositionModel = new DefaultListModel();
    private javax.swing.JList airfieldJList = new javax.swing.JList();
    private javax.swing.JList runwaysJList = new javax.swing.JList();
    private javax.swing.JList winchPositionsJList = new javax.swing.JList();
    private javax.swing.JList gliderPositionsJList = new javax.swing.JList();
    private JTextField airfieldAltitudeField;
    private JTextField designatorField;
    private JTextField airfieldNameField;
    private JTextField magneticVariationField;
    private JTextField airfieldLongitudeField;
    private JTextField airfieldLatitudeField;
    private JTextField magneticHeadingField;
    private JTextField runwayNameField;
    private JTextField runwayAltitudeField;
    private JTextField runwayParentAirfieldField;
    private JTextField gliderPosParentRunwayField;
    private JTextField gliderPosParentAirfieldField;
    private JTextField gliderPosLatitudeField;
    private JTextField gliderPosLongitudeField;
    private JTextField gliderPosAltitudeField;
    private JTextField gliderPosNameField;
    private JTextField winchPosNameField;
    private JTextField winchPosAltitudeField;
    private JTextField winchPosLongitudeField;
    private JTextField winchPosLatitudeField;
    private JTextField winchPosParentAirfieldField;
    private JTextField winchPosParentRunwayField;
    private CurrentDataObjectSet currentData;
    private JButton airfieldEditButton;
    private JButton runwayEditButton;
    private JButton gliderPosEditButton;
    private JButton winchPosEditButton;
    private JButton airfieldAddNewButton;
    private JButton runwayAddNewButton;
    private JButton gliderPosAddNewButton;
    private JButton winchPosAddNewButton;
    private JLabel airfieldAltitudeUnits = new JLabel();
    private JLabel gliderPosAltitudeUnits = new JLabel();
    private JLabel winchPosAltitudeUnits = new JLabel();
    private JLabel magneticHeadingUnits = new JLabel();
    private int airfieldAltitudeUnitsID;
    private int gliderPosAltitudeUnitsID;
    private int runwayMagneticHeadingUnitsID;
    private int winchPosAltitudeUnitsID;
    
    public void setupUnits()
    {
        airfieldAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("airfieldAltitude");
        String airfieldAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(airfieldAltitudeUnitsID);
        airfieldAltitudeUnits.setText(airfieldAltitudeUnitsString);

        gliderPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("gliderPosAltitude");
        String gliderPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(gliderPosAltitudeUnitsID);
        gliderPosAltitudeUnits.setText(gliderPosAltitudeUnitsString);

        runwayMagneticHeadingUnitsID = currentData.getCurrentProfile().getUnitSetting("runwayMagneticHeading");
        String runwayMagneticHeadingUnitsString = UnitLabelUtilities.degreesUnitIndexToString(runwayMagneticHeadingUnitsID);
        magneticHeadingUnits.setText("degrees " + runwayMagneticHeadingUnitsString);

        winchPosAltitudeUnitsID = currentData.getCurrentProfile().getUnitSetting("winchPosAltitude");
        String winchPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(winchPosAltitudeUnitsID);
        winchPosAltitudeUnits.setText(winchPosAltitudeUnitsString);
    }
    
    @Override
    public void update(String s)
    {          
        setupUnits();
        if(s.equals("1"))
        {
            initAirfieldList();
            airfieldModel.clear();
            for(Object str: airfields){
                    airfieldModel.addElement(str);
            }
            airfieldJList.setModel(airfieldModel);
            Airfield currentAirfield = currentData.getCurrentAirfield();
            try{
                airfieldJList.setSelectedValue(currentAirfield.toString(), true);
                airfieldScrollPane.setViewportView(airfieldJList); 

                airfieldNameField.setText(currentAirfield.getName());
                airfieldNameField.setBackground(Color.GREEN);

                designatorField.setText(String.valueOf(currentAirfield.getDesignator()));
                designatorField.setBackground(Color.GREEN);

                airfieldAltitudeField.setText(String.valueOf(currentAirfield.getElevation() * UnitConversionRate.convertDistanceUnitIndexToFactor(airfieldAltitudeUnitsID)));
                airfieldAltitudeField.setBackground(Color.GREEN);

                magneticVariationField.setText(String.valueOf(currentAirfield.getMagneticVariation()));
                magneticVariationField.setBackground(Color.GREEN);

                airfieldLongitudeField.setText(String.valueOf(currentAirfield.getLongitude()));
                airfieldLongitudeField.setBackground(Color.GREEN);

                airfieldLatitudeField.setText(String.valueOf(currentAirfield.getLatitude()));
                airfieldLatitudeField.setBackground(Color.GREEN);
            }
            catch(Exception e){}
        }
        else if(s.equals("2"))
        {
            initRunwaysList();
            runwaysModel.clear();
            for(Object str: runways){
                    runwaysModel.addElement(str);
            }
            runwaysJList.setModel(runwaysModel);
            Runway currentRunway = currentData.getCurrentRunway();
            try
            {
                runwaysJList.setSelectedValue(currentRunway.toString(), true);
                runwaysScrollPane.setViewportView(runwaysJList);  
            
                runwayNameField.setText(currentRunway.getName());
                runwayNameField.setBackground(Color.GREEN);

                if(runwayMagneticHeadingUnitsID == 1)
                {
                    magneticHeadingField.setText(String.valueOf(currentRunway.getMagneticHeading() + currentData.getCurrentAirfield().getMagneticVariation()));
                    magneticHeadingField.setBackground(Color.GREEN); 
                }
                else
                {
                    magneticHeadingField.setText(String.valueOf(currentRunway.getMagneticHeading()));
                    magneticHeadingField.setBackground(Color.GREEN); 
                }
            }
            catch(Exception e){}
        }
        else if(s.equals("3"))
        {
            initGliderPositionsList();
            gliderPositionModel.clear();
            for(Object str: gliderPositions){
                    gliderPositionModel.addElement(str);
            }
            gliderPositionsJList.setModel(gliderPositionModel);
            GliderPosition currentGliderPosition = currentData.getCurrentGliderPosition();
            try
            {
                gliderPositionsJList.setSelectedValue(currentGliderPosition.toString(), true);
                gliderPositionsScrollPane.setViewportView(gliderPositionsJList); 

                gliderPosNameField.setText(currentGliderPosition.getName());
                gliderPosNameField.setBackground(Color.GREEN);

                gliderPosAltitudeField.setText(String.valueOf(currentGliderPosition.getElevation() * UnitConversionRate.convertDistanceUnitIndexToFactor(gliderPosAltitudeUnitsID)));
                gliderPosAltitudeField.setBackground(Color.GREEN);

                gliderPosLongitudeField.setText(String.valueOf(currentGliderPosition.getLongitude()));
                gliderPosLongitudeField.setBackground(Color.GREEN);

                gliderPosLatitudeField.setText(String.valueOf(currentGliderPosition.getLatitude()));
                gliderPosLatitudeField.setBackground(Color.GREEN);
            }
            catch(Exception e){}
        }
        else
        {
            initWinchPositionsList();
            winchPositionModel.clear();
            for(Object str: winchPositions){
                    winchPositionModel.addElement(str);
            }
            winchPositionsJList.setModel(winchPositionModel);
            WinchPosition currentWinchPos = currentData.getCurrentWinchPosition();
            try
            {
                winchPositionsJList.setSelectedValue(currentWinchPos.toString(), true);
                winchPositionsScrollPane.setViewportView(winchPositionsJList); 

                winchPosNameField.setText(currentWinchPos.getName());
                winchPosNameField.setBackground(Color.GREEN);

                winchPosAltitudeField.setText(String.valueOf(currentWinchPos.getElevation() * UnitConversionRate.convertDistanceUnitIndexToFactor(winchPosAltitudeUnitsID)));
                winchPosAltitudeField.setBackground(Color.GREEN);

                winchPosLongitudeField.setText(String.valueOf(currentWinchPos.getLongitude()));
                winchPosLongitudeField.setBackground(Color.GREEN);

                winchPosLatitudeField.setText(String.valueOf(currentWinchPos.getLatitude()));
                winchPosLatitudeField.setBackground(Color.GREEN); 
            }
            catch(Exception e){}
        }     
    }
    
    private Observer getObserver() {
        return this;
    }
    
    private void initAirfieldList() {
            RecentLaunchSelections recent = RecentLaunchSelections.getRecentLaunchSelections();
            airfields = DatabaseEntrySelect.getAirfields();
            List<Airfield> recentAirfields = recent.getRecentAirfield();
            for (int i = 0; i < recentAirfields.size(); i++){
                airfields.add(0, recentAirfields.get(i));
            }
    }

    private void initGliderPositionsList() {
            RecentLaunchSelections recent = RecentLaunchSelections.getRecentLaunchSelections();
            gliderPositions = DatabaseEntrySelect.getGliderPositions();
            List<GliderPosition> recentGliderPositions = recent.getRecentGliderPosition();
            for (int i = 0; i < recentGliderPositions.size(); i++){
                gliderPositions.add(0, recentGliderPositions.get(i));
            }
    }
    
    private void initWinchPositionsList() {
            RecentLaunchSelections recent = RecentLaunchSelections.getRecentLaunchSelections();
            winchPositions = DatabaseEntrySelect.getWinchPositions();
            List<WinchPosition> recentWinchPositions = recent.getRecentWinchPosition();
            for (int i = 0; i < recentWinchPositions.size(); i++){
                winchPositions.add(0, recentWinchPositions.get(i));
            }
    }
    
    private void initRunwaysList() {
            RecentLaunchSelections recent = RecentLaunchSelections.getRecentLaunchSelections();
            runways = DatabaseEntrySelect.getRunways();
            List<Runway> recentRunways = recent.getRecentRunway();
            for (int i = 0; i < recentRunways.size(); i++){
                runways.add(0, recentRunways.get(i));
            }
    }

    public void clear()
    {
        airfieldJList.clearSelection();
        airfieldNameField.setText("No Airfield Selected");
        airfieldNameField.setBackground(Color.WHITE);
        designatorField.setText("No Airfield Selected");
        designatorField.setBackground(Color.WHITE);
        airfieldAltitudeField.setText("No Airfield Selected");
        airfieldAltitudeField.setBackground(Color.WHITE);
        magneticVariationField.setText("No Airfield Selected");
        magneticVariationField.setBackground(Color.WHITE);
        airfieldLongitudeField.setText("No Airfield Selected");
        airfieldLongitudeField.setBackground(Color.WHITE);
        airfieldLatitudeField.setText("No Airfield Selected");
        airfieldLatitudeField.setBackground(Color.WHITE);
        runwayNameField.setText("No Runway Selected");
        runwayNameField.setBackground(Color.WHITE);
        //runwayAltitudeField.setText("No Runway Selected");
        //runwayAltitudeField.setBackground(Color.WHITE);
        magneticHeadingField.setText("No Runway Selected");
        magneticHeadingField.setBackground(Color.WHITE);
        gliderPosNameField.setText("No Glider Position Selected");
        gliderPosNameField.setBackground(Color.WHITE);
        gliderPosAltitudeField.setText("No Glider Position Selected");
        gliderPosAltitudeField.setBackground(Color.WHITE);
        gliderPosLongitudeField.setText("No Glider Position Selected");
        gliderPosLongitudeField.setBackground(Color.WHITE);
        gliderPosLatitudeField.setText("No Glider Position Selected");
        gliderPosLatitudeField.setBackground(Color.WHITE);
        winchPosNameField.setText("No Winch Position Selected");
        winchPosNameField.setBackground(Color.WHITE);
        winchPosAltitudeField.setText("No Winch Position Selected");
        winchPosAltitudeField.setBackground(Color.WHITE);
        winchPosLongitudeField.setText("No Winch Position Selected");
        winchPosLongitudeField.setBackground(Color.WHITE);
        winchPosLatitudeField.setText("No Winch Position Selected");
        winchPosLatitudeField.setBackground(Color.WHITE);

        runwaysModel.removeAllElements();
        winchPositionModel.removeAllElements();
        gliderPositionModel.removeAllElements();
        airfieldEditButton.setEnabled(false);
        runwayEditButton.setEnabled(false);
        gliderPosEditButton.setEnabled(false);
        winchPosEditButton.setEnabled(false);
        runwayAddNewButton.setEnabled(false);
        gliderPosAddNewButton.setEnabled(false);
        winchPosAddNewButton.setEnabled(false);
    }
            
    private void airfieldJListSelectionChanged(ListSelectionEvent listSelectionEvent) 
    {
        if(airfieldJList.getSelectedIndex() >= 0){
            try{
                Airfield theAirfield = (Airfield)airfieldJList.getSelectedValue();
                currentData.setCurrentAirfield(theAirfield);
                currentData.cleafGliderPosition();
                currentData.clearRunway();
                currentData.clearWinchPosition();
                
                airfieldNameField.setText(theAirfield.getName());
                airfieldNameField.setBackground(Color.GREEN);
                airfieldNameField.setHorizontalAlignment(JTextField.RIGHT);
                
                designatorField.setText(String.valueOf(theAirfield.getDesignator()));
                designatorField.setBackground(Color.GREEN);
                designatorField.setHorizontalAlignment(JTextField.RIGHT);
                
                airfieldAltitudeField.setText(String.valueOf(theAirfield.getElevation() * UnitConversionRate.convertDistanceUnitIndexToFactor(airfieldAltitudeUnitsID)));
                airfieldAltitudeField.setBackground(Color.GREEN);
                airfieldAltitudeField.setHorizontalAlignment(JTextField.RIGHT);
                
                magneticVariationField.setText(String.valueOf(theAirfield.getMagneticVariation()));
                magneticVariationField.setBackground(Color.GREEN);
                magneticVariationField.setHorizontalAlignment(JTextField.RIGHT);
                
                airfieldLongitudeField.setText(String.valueOf(theAirfield.getLongitude()));
                airfieldLongitudeField.setBackground(Color.GREEN);
                airfieldLongitudeField.setHorizontalAlignment(JTextField.RIGHT);
                
                airfieldLatitudeField.setText(String.valueOf(theAirfield.getLatitude()));
                airfieldLatitudeField.setBackground(Color.GREEN);
                airfieldLatitudeField.setHorizontalAlignment(JTextField.RIGHT);
                
                runwayNameField.setText("No Runway Selected");
                runwayNameField.setBackground(Color.WHITE);
                //runwayAltitudeField.setText("No Runway Selected");
                //runwayAltitudeField.setBackground(Color.WHITE);
                magneticHeadingField.setText("No Runway Selected");
                magneticHeadingField.setBackground(Color.WHITE);
                gliderPosNameField.setText("No Glider Position Selected");
                gliderPosNameField.setBackground(Color.WHITE);
                gliderPosAltitudeField.setText("No Glider Position Selected");
                gliderPosAltitudeField.setBackground(Color.WHITE);
                gliderPosLongitudeField.setText("No Glider Position Selected");
                gliderPosLongitudeField.setBackground(Color.WHITE);
                gliderPosLatitudeField.setText("No Glider Position Selected");
                gliderPosLatitudeField.setBackground(Color.WHITE);
                winchPosNameField.setText("No Winch Position Selected");
                winchPosNameField.setBackground(Color.WHITE);
                winchPosAltitudeField.setText("No Winch Position Selected");
                winchPosAltitudeField.setBackground(Color.WHITE);
                winchPosLongitudeField.setText("No Winch Position Selected");
                winchPosLongitudeField.setBackground(Color.WHITE);
                winchPosLatitudeField.setText("No Winch Position Selected");
                winchPosLatitudeField.setBackground(Color.WHITE);
                
                runwaysModel.removeAllElements();
                winchPositionModel.removeAllElements();
                gliderPositionModel.removeAllElements();
                for(Runway str: runways){
                    if(str.getParentId() == theAirfield.getId())
                        runwaysModel.addElement(str);
                }
                runwaysJList.setModel(runwaysModel);
                runwaysScrollPane.setViewportView(runwaysJList); 
            } catch(Exception e) {
                //TODO respond to error
            }
            airfieldEditButton.setEnabled(true);
            runwayAddNewButton.setEnabled(true);
        }
    }
    
    private void runwayJListSelectionChanged(ListSelectionEvent listSelectionEvent) 
    {
        if(runwaysJList.getSelectedIndex() >= 0){
            try{
                Runway theRunway = (Runway)runwaysJList.getSelectedValue();
                currentData.setCurrentRunway(theRunway);
                currentData.cleafGliderPosition();
                currentData.clearWinchPosition();
                
                runwayNameField.setText(theRunway.getName());
                runwayNameField.setBackground(Color.GREEN);
                runwayNameField.setHorizontalAlignment(JTextField.RIGHT);
                
                if(runwayMagneticHeadingUnitsID == 1)
                {
                    magneticHeadingField.setText(String.valueOf(theRunway.getMagneticHeading() + currentData.getCurrentAirfield().getMagneticVariation()));
                    magneticHeadingField.setBackground(Color.GREEN); 
                    magneticHeadingField.setHorizontalAlignment(JTextField.RIGHT);
                }
                else
                {
                    magneticHeadingField.setText(String.valueOf(theRunway.getMagneticHeading()));
                    magneticHeadingField.setBackground(Color.GREEN); 
                    magneticHeadingField.setHorizontalAlignment(JTextField.RIGHT);
                }
                
                gliderPosNameField.setText("No Glider Position Selected");
                gliderPosNameField.setBackground(Color.WHITE);
                gliderPosAltitudeField.setText("No Glider Position Selected");
                gliderPosAltitudeField.setBackground(Color.WHITE);
                gliderPosLongitudeField.setText("No Glider Position Selected");
                gliderPosLongitudeField.setBackground(Color.WHITE);
                gliderPosLatitudeField.setText("No Glider Position Selected");
                gliderPosLatitudeField.setBackground(Color.WHITE);
                winchPosNameField.setText("No Winch Position Selected");
                winchPosNameField.setBackground(Color.WHITE);
                winchPosAltitudeField.setText("No Winch Position Selected");
                winchPosAltitudeField.setBackground(Color.WHITE);
                winchPosLongitudeField.setText("No Winch Position Selected");
                winchPosLongitudeField.setBackground(Color.WHITE);
                winchPosLatitudeField.setText("No Winch Position Selected");
                winchPosLatitudeField.setBackground(Color.WHITE);
                
                gliderPositionModel.removeAllElements();
                for(GliderPosition str: gliderPositions){
                    if(str.getRunwayParentId() == theRunway.getId())
                        gliderPositionModel.addElement(str);
                }
                
                winchPositionModel.removeAllElements();
                for(WinchPosition str: winchPositions){
                    if(str.getRunwayParentId() == theRunway.getId())
                        winchPositionModel.addElement(str);
                }
            } catch(Exception e) {
                //TODO respond to error
            }
            runwayEditButton.setEnabled(true);
            gliderPosAddNewButton.setEnabled(true);
            winchPosAddNewButton.setEnabled(true);
        }
    }
    
    private void gliderPositionsJListSelectionChanged(ListSelectionEvent listSelectionEvent)
    {
        if(gliderPositionsJList.getSelectedIndex() >= 0){
            try{
                GliderPosition theGliderPosition = (GliderPosition)gliderPositionsJList.getSelectedValue();
                currentData.setCurrentGliderPosition(theGliderPosition);
                
                gliderPosNameField.setText(theGliderPosition.getName());
                gliderPosNameField.setBackground(Color.GREEN);
                gliderPosNameField.setHorizontalAlignment(JTextField.RIGHT);
                
                gliderPosAltitudeField.setText(String.valueOf(theGliderPosition.getElevation() * UnitConversionRate.convertDistanceUnitIndexToFactor(gliderPosAltitudeUnitsID)));
                gliderPosAltitudeField.setBackground(Color.GREEN);
                gliderPosAltitudeField.setHorizontalAlignment(JTextField.RIGHT);
 
                gliderPosLongitudeField.setText(String.valueOf(theGliderPosition.getLongitude()));
                gliderPosLongitudeField.setBackground(Color.GREEN);
                gliderPosLongitudeField.setHorizontalAlignment(JTextField.RIGHT);
                
                gliderPosLatitudeField.setText(String.valueOf(theGliderPosition.getLatitude()));
                gliderPosLatitudeField.setBackground(Color.GREEN);
                gliderPosLatitudeField.setHorizontalAlignment(JTextField.RIGHT);
            } catch(Exception e) {
                //TODO respond to error
            }
            gliderPosEditButton.setEnabled(true);
        }
    }
    
    private void winchPositionsJListSelectionChanged(ListSelectionEvent listSelectionEvent)
    {
        if(winchPositionsJList.getSelectedIndex() >= 0){
            try{
                WinchPosition theWinchPosition = (WinchPosition)winchPositionsJList.getSelectedValue();
                currentData.setCurrentWinchPosition(theWinchPosition);
                
                winchPosNameField.setText(theWinchPosition.getName());
                winchPosNameField.setBackground(Color.GREEN);
                winchPosNameField.setHorizontalAlignment(JTextField.RIGHT);
                
                winchPosAltitudeField.setText(String.valueOf(theWinchPosition.getElevation() * UnitConversionRate.convertDistanceUnitIndexToFactor(winchPosAltitudeUnitsID)));
                winchPosAltitudeField.setBackground(Color.GREEN);
                winchPosAltitudeField.setHorizontalAlignment(JTextField.RIGHT);
 
                winchPosLongitudeField.setText(String.valueOf(theWinchPosition.getLongitude()));
                winchPosLongitudeField.setBackground(Color.GREEN);
                winchPosLongitudeField.setHorizontalAlignment(JTextField.RIGHT);
                
                winchPosLatitudeField.setText(String.valueOf(theWinchPosition.getLatitude()));
                winchPosLatitudeField.setBackground(Color.GREEN);
                winchPosLatitudeField.setHorizontalAlignment(JTextField.RIGHT);
            } catch(Exception e) {
                //TODO respond to error
            }
            winchPosEditButton.setEnabled(true);
        }
    }
    
    /**
     * Creates new form sailplanePanel
     */
    public AirfieldPanel() {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        initAirfieldList();
        initWinchPositionsList();
        initGliderPositionsList();
        initRunwaysList();
        initComponents();
        setupUnits();
    }
    
    /**
     * Create the panel.
     */
    public void initComponents() {
        airfieldScrollPane = new javax.swing.JScrollPane();
        gliderPositionsScrollPane = new javax.swing.JScrollPane();
        winchPositionsScrollPane = new javax.swing.JScrollPane();
        runwaysScrollPane = new javax.swing.JScrollPane();

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        JPanel panel_1 = new JPanel();
        add(panel_1);
        panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.PAGE_AXIS));

        JPanel airfieldSubPanel = new JPanel();
        panel.add(airfieldSubPanel);
        airfieldSubPanel.setLayout(new BorderLayout(0, 0));
        airfieldSubPanel.add(airfieldScrollPane, BorderLayout.NORTH);
        for(Object str: airfields){
                airfieldModel.addElement(str);
        }
        airfieldJList.setModel(airfieldModel);
        airfieldJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                airfieldJListSelectionChanged(listSelectionEvent);
            }
        });
        airfieldJList.setSelectedIndex(-1);
        airfieldScrollPane.setViewportView(airfieldJList);
        
        JScrollPane airfieldAttributesPanelScrollPane = new JScrollPane();
        airfieldSubPanel.add(airfieldAttributesPanelScrollPane, BorderLayout.CENTER);
        JPanel airfieldAttributesPanel = new JPanel();
        airfieldAttributesPanel.setBackground(Color.WHITE);
        airfieldAttributesPanel.setPreferredSize(new Dimension(500,300));
        airfieldAttributesPanel.setLayout(null);
        airfieldAttributesPanelScrollPane.setViewportView(airfieldAttributesPanel);
        airfieldAttributesPanelScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        airfieldAttributesPanelScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        
        JLabel designatorLabel = new JLabel("Designator:");
        designatorLabel.setBounds(10, 78, 84, 14);
        airfieldAttributesPanel.add(designatorLabel);
        
        JLabel airfieldAltitudeLabel = new JLabel("Altitude:");
        airfieldAltitudeLabel.setBounds(10, 104, 84, 14);
        airfieldAttributesPanel.add(airfieldAltitudeLabel);
        
        JLabel magneticVariationLabel = new JLabel("Magnetic Variation:");
        magneticVariationLabel.setBounds(10, 129, 200, 14);
        airfieldAttributesPanel.add(magneticVariationLabel);
        
        JLabel airfieldLongitudeLabel = new JLabel("Longitude:");
        airfieldLongitudeLabel.setBounds(10, 154, 84, 14);
        airfieldAttributesPanel.add(airfieldLongitudeLabel);
        
        JLabel airfieldLatitudeLabel = new JLabel("Latitude:");
        airfieldLatitudeLabel.setBounds(10, 179, 84, 14);
        airfieldAttributesPanel.add(airfieldLatitudeLabel);
        
        airfieldAltitudeField = new JTextField();
        airfieldAltitudeField.setBackground(Color.WHITE);
        airfieldAltitudeField.setDisabledTextColor(Color.WHITE);
        airfieldAltitudeField.setEditable(false);
        airfieldAltitudeField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        airfieldAltitudeField.setBounds(140, 100, 200, 20);
        airfieldAttributesPanel.add(airfieldAltitudeField);
        airfieldAltitudeField.setColumns(10);
        airfieldAltitudeField.setText("No Airfield Selected");
        
        designatorField = new JTextField();
        designatorField.setBackground(Color.WHITE);
        designatorField.setDisabledTextColor(Color.WHITE);
        designatorField.setEditable(false);
        designatorField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        designatorField.setBounds(140, 75, 200, 20);
        airfieldAttributesPanel.add(designatorField);
        designatorField.setColumns(10);
        designatorField.setText("No Airfield Selected");
        
        airfieldNameField = new JTextField();
        airfieldNameField.setBackground(Color.WHITE);
        airfieldNameField.setDisabledTextColor(Color.WHITE);
        airfieldNameField.setEditable(false);
        airfieldNameField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        airfieldNameField.setBounds(140, 50, 200, 20);
        airfieldAttributesPanel.add(airfieldNameField);
        airfieldNameField.setColumns(10);
        airfieldNameField.setText("No Airfield Selected");
        
        magneticVariationField = new JTextField();
        magneticVariationField.setBackground(Color.WHITE);
        magneticVariationField.setDisabledTextColor(Color.WHITE);
        magneticVariationField.setEditable(false);
        magneticVariationField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        magneticVariationField.setBounds(140, 125, 200, 20);
        airfieldAttributesPanel.add(magneticVariationField);
        magneticVariationField.setColumns(10);
        magneticVariationField.setText("No Airfield Selected");
        
        JLabel airfieldNameLabel = new JLabel("Name:");
        airfieldNameLabel.setBounds(10, 53, 46, 14);
        airfieldAttributesPanel.add(airfieldNameLabel);
        
        airfieldLongitudeField = new JTextField();
        airfieldLongitudeField.setBackground(Color.WHITE);
        airfieldLongitudeField.setDisabledTextColor(Color.WHITE);
        airfieldLongitudeField.setEditable(false);
        airfieldLongitudeField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        airfieldLongitudeField.setBounds(140, 151, 200, 20);
        airfieldAttributesPanel.add(airfieldLongitudeField);
        airfieldLongitudeField.setColumns(10);
        airfieldLongitudeField.setText("No Airfield Selected");
        
        airfieldLatitudeField = new JTextField();
        airfieldLatitudeField.setBackground(Color.WHITE);
        airfieldLatitudeField.setDisabledTextColor(Color.WHITE);
        airfieldLatitudeField.setEditable(false);
        airfieldLatitudeField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        airfieldLatitudeField.setBounds(140, 176, 200, 20);
        airfieldAttributesPanel.add(airfieldLatitudeField);
        airfieldLatitudeField.setColumns(10);
        airfieldLatitudeField.setText("No Airfield Selected");
        
        airfieldAddNewButton = new JButton("Add New");
        airfieldAddNewButton.setBackground(new Color(200,200,200));
        airfieldAddNewButton.addActionListener(new ActionListener() {
                @Override
        	public void actionPerformed(ActionEvent arg0) {
                    AddEditAirfieldFrame AddEditAirfieldFrame_ = new AddEditAirfieldFrame(currentData.getCurrentAirfield(), false);
                    AddEditAirfieldFrame_.setVisible(true);
                    AddEditAirfieldFrame_.attach(getObserver());
        	}
        });
        airfieldAddNewButton.setBounds(200, 0, 89, 23);
        airfieldAttributesPanel.add(airfieldAddNewButton);
        
        airfieldEditButton = new JButton("Edit");
        airfieldEditButton.setBackground(new Color(200,200,200));
        airfieldEditButton.addActionListener(new ActionListener() {
                @Override
        	public void actionPerformed(ActionEvent e) {
                    AddEditAirfieldFrame AddEditAirfieldFrame_ = new AddEditAirfieldFrame(currentData.getCurrentAirfield(), true);
                    AddEditAirfieldFrame_.setVisible(true);
                    AddEditAirfieldFrame_.attach(getObserver());
        	}
        });
        airfieldEditButton.setBounds(288, 0, 89, 23);
        airfieldEditButton.setEnabled(false);
        airfieldAttributesPanel.add(airfieldEditButton);
        
        JLabel airfieldLabel = new JLabel("Airfield");
        airfieldLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        airfieldLabel.setBounds(10, 20, 100, 22);
        airfieldAttributesPanel.add(airfieldLabel);
        
        airfieldAltitudeUnits.setBounds(350, 104, 46, 14);
        airfieldAttributesPanel.add(airfieldAltitudeUnits);
        
        JLabel airfieldLongitudeUnits = new JLabel("degrees");
        airfieldLongitudeUnits.setBounds(350, 154, 65, 14);
        airfieldAttributesPanel.add(airfieldLongitudeUnits);
        
        JLabel airfieldLatitudeUnits = new JLabel("degrees");
        airfieldLatitudeUnits.setBounds(350, 179, 65, 14);
        airfieldAttributesPanel.add(airfieldLatitudeUnits);
        
        JLabel magneticVariationUnits = new JLabel("degrees");
        magneticVariationUnits.setBounds(350, 129, 65, 14);
        airfieldAttributesPanel.add(magneticVariationUnits);

        JPanel gliderPostitionSubPanel = new JPanel();
        gliderPostitionSubPanel.setLayout(new BorderLayout(0, 0));
        gliderPostitionSubPanel.add(gliderPositionsScrollPane, BorderLayout.NORTH);

        gliderPositionsJList.setModel(gliderPositionModel);
        gliderPositionsScrollPane.setViewportView(gliderPositionsJList);
        
        JScrollPane gliderPosAttributesPanelScrollPane = new JScrollPane();
        JPanel gliderPositionAttributesPanel = new JPanel();
        gliderPositionAttributesPanel.setBackground(Color.WHITE);
        gliderPostitionSubPanel.add(gliderPosAttributesPanelScrollPane, BorderLayout.CENTER);
        gliderPositionAttributesPanel.setPreferredSize(new Dimension(500,300));
        gliderPosAttributesPanelScrollPane.setViewportView(gliderPositionAttributesPanel);
        gliderPosAttributesPanelScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        gliderPosAttributesPanelScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        gliderPositionAttributesPanel.setLayout(null);
        
        JLabel gliderPosNameLabel = new JLabel("Name:");
        gliderPosNameLabel.setBounds(10, 53, 46, 14);
        gliderPositionAttributesPanel.add(gliderPosNameLabel);
        
        JLabel gliderPosAltitudeLabel = new JLabel("Altitude:");
        gliderPosAltitudeLabel.setBounds(10, 78, 46, 14);
        gliderPositionAttributesPanel.add(gliderPosAltitudeLabel);
        
        JLabel gliderPosLongitudeLabel = new JLabel("Longitude:");
        gliderPosLongitudeLabel.setBounds(10, 103, 80, 14);
        gliderPositionAttributesPanel.add(gliderPosLongitudeLabel);
        
        JLabel gliderPosLatitudeLabel = new JLabel("Latitude:");
        gliderPosLatitudeLabel.setBounds(10, 128, 80, 14);
        gliderPositionAttributesPanel.add(gliderPosLatitudeLabel);
        
        /*JLabel gliderPosParentAirfieldLabel = new JLabel("Parent Airfield:");
        gliderPosParentAirfieldLabel.setBounds(10, 111, 103, 14);
        gliderPositionAttributesPanel.add(gliderPosParentAirfieldLabel);
        
        JLabel gliderPosParentRunwayLabel = new JLabel("Parent Runway:");
        gliderPosParentRunwayLabel.setBounds(10, 136, 103, 14);
        gliderPositionAttributesPanel.add(gliderPosParentRunwayLabel);
        
        gliderPosParentRunwayField = new JTextField();
        gliderPosParentRunwayField.setBounds(135, 133, 120, 20);
        gliderPositionAttributesPanel.add(gliderPosParentRunwayField);
        gliderPosParentRunwayField.setColumns(10);
        
        gliderPosParentAirfieldField = new JTextField();
        gliderPosParentAirfieldField.setBounds(135, 108, 120, 20);
        gliderPositionAttributesPanel.add(gliderPosParentAirfieldField);
        gliderPosParentAirfieldField.setColumns(10);*/
        
        gliderPosLatitudeField = new JTextField();
        gliderPosLatitudeField.setEditable(false);
        gliderPosLatitudeField.setBackground(Color.WHITE);
        gliderPosLatitudeField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        gliderPosLatitudeField.setBounds(135, 125, 200, 20);
        gliderPositionAttributesPanel.add(gliderPosLatitudeField);
        gliderPosLatitudeField.setColumns(10);
        gliderPosLatitudeField.setText("No Winch Position Selected");
        
        gliderPosLongitudeField = new JTextField();
        gliderPosLongitudeField.setEditable(false);
        gliderPosLongitudeField.setBackground(Color.WHITE);
        gliderPosLongitudeField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        gliderPosLongitudeField.setBounds(135, 100, 200, 20);
        gliderPositionAttributesPanel.add(gliderPosLongitudeField);
        gliderPosLongitudeField.setColumns(10);
        gliderPosLongitudeField.setText("No Winch Position Selected");
        
        gliderPosAltitudeField = new JTextField();
        gliderPosAltitudeField.setEditable(false);
        gliderPosAltitudeField.setBackground(Color.WHITE);
        gliderPosAltitudeField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        gliderPosAltitudeField.setBounds(135, 75, 200, 20);
        gliderPositionAttributesPanel.add(gliderPosAltitudeField);
        gliderPosAltitudeField.setColumns(10);
        gliderPosAltitudeField.setText("No Winch Position Selected");
        
        gliderPosNameField = new JTextField();
        gliderPosNameField.setEditable(false);
        gliderPosNameField.setBackground(Color.WHITE);
        gliderPosNameField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        gliderPosNameField.setBounds(135, 50, 200, 20);
        gliderPositionAttributesPanel.add(gliderPosNameField);
        gliderPosNameField.setColumns(10);
        gliderPosNameField.setText("No Winch Position Selected");
        
        gliderPosAddNewButton = new JButton("Add New");
        gliderPosAddNewButton.setBackground(new Color(200,200,200));
        gliderPosAddNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                    AddEditGliderPosFrame AddEditGliderPosFrame_ = new AddEditGliderPosFrame(currentData.getCurrentGliderPosition(), false);
                    AddEditGliderPosFrame_.setVisible(true);
                    AddEditGliderPosFrame_.attach(getObserver());
        	}
        });
        gliderPosAddNewButton.setBounds(200, 0, 89, 23);
        gliderPosAddNewButton.setEnabled(false);
        gliderPositionAttributesPanel.add(gliderPosAddNewButton);
        
        gliderPosEditButton = new JButton("Edit");
        gliderPosEditButton.setBackground(new Color(200,200,200));
        gliderPosEditButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                    AddEditGliderPosFrame AddEditGliderPosFrame_ = new AddEditGliderPosFrame(currentData.getCurrentGliderPosition(), true);
                    AddEditGliderPosFrame_.setVisible(true);
                    AddEditGliderPosFrame_.attach(getObserver());
        	}
        });
        gliderPosEditButton.setBounds(288, 0, 89, 23);
        gliderPosEditButton.setEnabled(false);
        gliderPositionAttributesPanel.add(gliderPosEditButton);
        
        JLabel gliderPositionLabel = new JLabel("Glider Position");
        gliderPositionLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        gliderPositionLabel.setBounds(10, 20, 180, 31);
        gliderPositionAttributesPanel.add(gliderPositionLabel);
        
        JLabel gliderPosLongitudeUnits = new JLabel("degrees");
        gliderPosLongitudeUnits.setBounds(345, 103, 65, 14);
        gliderPositionAttributesPanel.add(gliderPosLongitudeUnits);
        
        JLabel gliderPosLatitudeUnits = new JLabel("degrees");
        gliderPosLatitudeUnits.setBounds(345, 128, 65, 14);
        gliderPositionAttributesPanel.add(gliderPosLatitudeUnits);
        
        gliderPosAltitudeUnits.setBounds(345, 78, 46, 14);
        gliderPositionAttributesPanel.add(gliderPosAltitudeUnits);
        
        gliderPositionsJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                gliderPositionsJListSelectionChanged(listSelectionEvent);
            }
        });
        gliderPositionsJList.setSelectedIndex(-1);
        JPanel runwaySubPanel = new JPanel();
        panel_1.add(runwaySubPanel);
        runwaySubPanel.setLayout(new BorderLayout(0, 0));
        runwaySubPanel.add(runwaysScrollPane, BorderLayout.NORTH);
        
        panel_1.add(gliderPostitionSubPanel);
        
        DefaultListModel runwayModel = new DefaultListModel();
        runwaysJList.setModel(runwayModel);
        runwaysScrollPane.setViewportView(runwaysJList);
        
        JPanel runwayAttributesPanel = new JPanel();
        runwayAttributesPanel.setBackground(Color.WHITE);
        JScrollPane runwayAttributesPanelScrollPane = new JScrollPane();
        runwaySubPanel.add(runwayAttributesPanelScrollPane, BorderLayout.CENTER);
        runwayAttributesPanel.setLayout(null);
        runwayAttributesPanel.setPreferredSize(new Dimension(500,300));
        runwayAttributesPanelScrollPane.setViewportView(runwayAttributesPanel);
        runwayAttributesPanelScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        runwayAttributesPanelScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        
        JLabel runwayNameLabel = new JLabel("Name:");
        runwayNameLabel.setBounds(10, 53, 106, 14);
        runwayAttributesPanel.add(runwayNameLabel);
        
        JLabel magneticHeadingLabel = new JLabel("Heading:");
        magneticHeadingLabel.setBounds(10, 78, 106, 14);
        runwayAttributesPanel.add(magneticHeadingLabel);
        
        //JLabel runwayAltitudeLabel = new JLabel("Altitude:");
        //runwayAltitudeLabel.setBounds(10, 103, 106, 14);
        //runwayAttributesPanel.add(runwayAltitudeLabel);
        
        /*JLabel runwayParentAirfieldLabel = new JLabel("Parent Airfield:");
        runwayParentAirfieldLabel.setBounds(10, 111, 106, 14);
        runwayAttributesPanel.add(runwayParentAirfieldLabel);*/
        
        magneticHeadingField = new JTextField();
        magneticHeadingField.setBackground(Color.WHITE);
        magneticHeadingField.setDisabledTextColor(Color.WHITE);
        magneticHeadingField.setEditable(false);
        magneticHeadingField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        magneticHeadingField.setBounds(140, 75, 200, 20);
        runwayAttributesPanel.add(magneticHeadingField);
        magneticHeadingField.setColumns(10);
        magneticHeadingField.setText("No Runway Selected");
        
        runwayNameField = new JTextField();
        runwayNameField.setBackground(Color.WHITE);
        runwayNameField.setDisabledTextColor(Color.WHITE);
        runwayNameField.setEditable(false);
        runwayNameField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        runwayNameField.setBounds(140, 50, 200, 20);
        runwayAttributesPanel.add(runwayNameField);
        runwayNameField.setColumns(10);
        runwayNameField.setText("No Runway Selected");
        
        /*runwayAltitudeField = new JTextField();
        runwayAltitudeField.setBackground(Color.WHITE);
        runwayAltitudeField.setDisabledTextColor(Color.WHITE);
        runwayAltitudeField.setEditable(false);
        runwayAltitudeField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        runwayAltitudeField.setBounds(140, 100, 200, 20);
        runwayAttributesPanel.add(runwayAltitudeField);
        runwayAltitudeField.setColumns(10);
        runwayAltitudeField.setText("No Runway Selected");*/
        
        /*runwayParentAirfieldField = new JTextField();
        runwayParentAirfieldField.setBounds(140, 108, 120, 20);
        runwayAttributesPanel.add(runwayParentAirfieldField);
        runwayParentAirfieldField.setColumns(10);*/
        
        runwayEditButton = new JButton("Edit");
        runwayEditButton.setBackground(new Color(200,200,200));
        runwayEditButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                    AddEditRunwayFrame AddEditRunwayFrame_ = new AddEditRunwayFrame(currentData.getCurrentRunway(), true);
                    AddEditRunwayFrame_.setVisible(true);
                    AddEditRunwayFrame_.attach(getObserver());
        	}
        });
        runwayEditButton.setBounds(288, 0, 89, 23);
        runwayEditButton.setEnabled(false);
        runwayAttributesPanel.add(runwayEditButton);
        
        runwayAddNewButton = new JButton("Add New");
        runwayAddNewButton.setBackground(new Color(200,200,200));
        runwayAddNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                    AddEditRunwayFrame AddEditRunwayFrame_ = new AddEditRunwayFrame(currentData.getCurrentRunway(), false);
                    AddEditRunwayFrame_.setVisible(true);
                    AddEditRunwayFrame_.attach(getObserver());
        	}
        });
        runwayAddNewButton.setBounds(200, 0, 89, 23);
        runwayAddNewButton.setEnabled(false);
        runwayAttributesPanel.add(runwayAddNewButton);
        
        JLabel runwayLabel = new JLabel("Runway");
        runwayLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        runwayLabel.setBounds(10, 20, 140, 31);
        runwayAttributesPanel.add(runwayLabel);
        
        //runwayAltitudeUnits.setBounds(350, 103, 46, 14);
        //runwayAttributesPanel.add(runwayAltitudeUnits);
        
        magneticHeadingUnits.setText("degrees " + "magnetic");
        magneticHeadingUnits.setBounds(350, 78, 125, 14);
        runwayAttributesPanel.add(magneticHeadingUnits);
        
        runwaysJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                runwayJListSelectionChanged(listSelectionEvent);
            }
        });
        runwaysJList.setSelectedIndex(-1);
        JPanel winchPostitionSubPanel = new JPanel();
        panel.add(winchPostitionSubPanel);
        winchPostitionSubPanel.setLayout(new BorderLayout(0, 0));
        winchPostitionSubPanel.add(winchPositionsScrollPane, BorderLayout.NORTH);
        
        winchPositionsJList.setModel(winchPositionModel);
        winchPositionsScrollPane.setViewportView(winchPositionsJList);
        
        JPanel winchPositionAttributesPanel = new JPanel();
        winchPositionAttributesPanel.setBackground(Color.WHITE);
        JScrollPane winchPositionAttributesPanelScrollPane = new JScrollPane();
        winchPostitionSubPanel.add(winchPositionAttributesPanelScrollPane, BorderLayout.CENTER);
        winchPositionAttributesPanel.setLayout(null);
        winchPositionAttributesPanel.setPreferredSize(new Dimension(500,300));
        winchPositionAttributesPanelScrollPane.setViewportView(winchPositionAttributesPanel);
        winchPositionAttributesPanelScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        winchPositionAttributesPanelScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        
        JLabel winchPosNameLabel = new JLabel("Name:");
        winchPosNameLabel.setBounds(10, 53, 46, 14);
        winchPositionAttributesPanel.add(winchPosNameLabel);
        
        winchPosNameField = new JTextField();
        winchPosNameField.setEditable(false);
        winchPosNameField.setBackground(Color.WHITE);
        winchPosNameField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        winchPosNameField.setColumns(10);
        winchPosNameField.setBounds(135, 50, 200, 20);
        winchPositionAttributesPanel.add(winchPosNameField);
        winchPosNameField.setText("No Winch Position Selected");
        
        JLabel winchPosAltitudeLabel = new JLabel("Altitude:");
        winchPosAltitudeLabel.setBounds(10, 78, 46, 14);
        winchPositionAttributesPanel.add(winchPosAltitudeLabel);
        
        winchPosAltitudeField = new JTextField();
        winchPosAltitudeField.setEditable(false);
        winchPosAltitudeField.setBackground(Color.WHITE);
        winchPosAltitudeField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        winchPosAltitudeField.setColumns(10);
        winchPosAltitudeField.setBounds(135, 75, 200, 20);
        winchPositionAttributesPanel.add(winchPosAltitudeField);
        winchPosAltitudeField.setText("No Winch Position Selected");
        
        JLabel winchPosLongitudeLabel = new JLabel("Longitude:");
        winchPosLongitudeLabel.setBounds(10, 103, 80, 14);
        winchPositionAttributesPanel.add(winchPosLongitudeLabel);
        
        winchPosLongitudeField = new JTextField();
        winchPosLongitudeField.setEditable(false);
        winchPosLongitudeField.setBackground(Color.WHITE);
        winchPosLongitudeField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        winchPosLongitudeField.setColumns(10);
        winchPosLongitudeField.setBounds(135, 100, 200, 20);
        winchPositionAttributesPanel.add(winchPosLongitudeField);
        winchPosLongitudeField.setText("No Winch Position Selected");
        
        JLabel winchPosLatitudeLabel = new JLabel("Latitude:");
        winchPosLatitudeLabel.setBounds(10, 128, 80, 14);
        winchPositionAttributesPanel.add(winchPosLatitudeLabel);
        
        winchPosLatitudeField = new JTextField();
        winchPosLatitudeField.setEditable(false);
        winchPosLatitudeField.setBackground(Color.WHITE);
        winchPosLatitudeField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        winchPosLatitudeField.setColumns(10);
        winchPosLatitudeField.setBounds(135, 125, 200, 20);
        winchPositionAttributesPanel.add(winchPosLatitudeField);
        winchPosLatitudeField.setText("No Winch Position Selected");
        
        winchPosAddNewButton = new JButton("Add New");
        winchPosAddNewButton.setBackground(new Color(200,200,200));
        winchPosAddNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                    AddEditWinchPosFrame AddEditWinchPosFrame_ = new AddEditWinchPosFrame(currentData.getCurrentWinchPosition(), false);
                    AddEditWinchPosFrame_.setVisible(true);
                    AddEditWinchPosFrame_.attach(getObserver());
        	}
        });
        winchPosAddNewButton.setBounds(200, 0, 89, 23);
        winchPosAddNewButton.setEnabled(false);
        winchPositionAttributesPanel.add(winchPosAddNewButton);
        
        winchPosEditButton = new JButton("Edit");
        winchPosEditButton.setBackground(new Color(200,200,200));
        winchPosEditButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                    AddEditWinchPosFrame AddEditWinchPosFrame_ = new AddEditWinchPosFrame(currentData.getCurrentWinchPosition(), true);
                    AddEditWinchPosFrame_.setVisible(true);
                    AddEditWinchPosFrame_.attach(getObserver());
        	}
        });
        winchPosEditButton.setBounds(288, 0, 89, 23);
        winchPosEditButton.setEnabled(false);
        winchPositionAttributesPanel.add(winchPosEditButton);
        
        JLabel winchPositionLabel = new JLabel("Winch Position");
        winchPositionLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        winchPositionLabel.setBounds(10, 20, 180, 31);
        winchPositionAttributesPanel.add(winchPositionLabel);
        
        winchPosAltitudeUnits.setBounds(345, 78, 46, 14);
        winchPositionAttributesPanel.add(winchPosAltitudeUnits);
        
        JLabel winchPosLongitudeUnits = new JLabel("degrees");
        winchPosLongitudeUnits.setBounds(345, 103, 65, 14);
        winchPositionAttributesPanel.add(winchPosLongitudeUnits);
        
        JLabel winchPosLatitudeUnits = new JLabel("degrees");
        winchPosLatitudeUnits.setBounds(345, 128, 65, 14);
        winchPositionAttributesPanel.add(winchPosLatitudeUnits);
        
        /*JLabel winchPosParentAirfieldLabel = new JLabel("Parent Airfield:");
        winchPosParentAirfieldLabel.setBounds(10, 111, 103, 14);
        winchPositionAttributesPanel.add(winchPosParentAirfieldLabel);
        
        winchPosParentAirfieldField = new JTextField();
        winchPosParentAirfieldField.setColumns(10);
        winchPosParentAirfieldField.setBounds(135, 108, 120, 20);
        winchPositionAttributesPanel.add(winchPosParentAirfieldField);
        
        JLabel winchPosParentRunwayLabel = new JLabel("Parent Runway:");
        winchPosParentRunwayLabel.setBounds(10, 136, 103, 14);
        winchPositionAttributesPanel.add(winchPosParentRunwayLabel);
        
        winchPosParentRunwayField = new JTextField();
        winchPosParentRunwayField.setColumns(10);
        winchPosParentRunwayField.setBounds(135, 133, 120, 20);
        winchPositionAttributesPanel.add(winchPosParentRunwayField);*/
        
        winchPositionsJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                winchPositionsJListSelectionChanged(listSelectionEvent);
            }
        });
        winchPositionsJList.setSelectedIndex(-1);
    }

    @Override
    public void update() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
