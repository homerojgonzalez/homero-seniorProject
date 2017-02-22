package ParameterSelection;

import AddEditPanels.AddEditGlider;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Sailplane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Font;
import javax.swing.JButton;
import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import javax.swing.border.MatteBorder;
import DataObjects.CurrentLaunchInformation;
import DataObjects.RecentLaunchSelections;
import DatabaseUtilities.DatabaseEntrySelect;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class SailplanePanel extends JPanel implements Observer{
    private List<Sailplane> sailplanes = new ArrayList();
    private CurrentDataObjectSet currentData;
    JScrollPane sailplaneScrollPane = new JScrollPane();
    private JLabel emptyWeightUnitsLabel = new JLabel();
    private JLabel maxGrossWeightUnitsLabel = new JLabel();
    private JLabel stallSpeedUnitsLabel = new JLabel();
    private JLabel ballastWeightUnitsLabel = new JLabel();
    private JLabel baggageWeightUnitsLabel = new JLabel();
    private JLabel passengerWeightUnitsLabel = new JLabel();
    private JLabel tensionUnitsLabel = new JLabel();
    private JLabel weakLinkStrengthUnitsLabel = new JLabel();
    private JLabel winchingSpeedUnitsLabel = new JLabel();
    private int emptyWeightUnitsID;
    private int maxGrossWeightUnitsID;
    private int stallSpeedUnitsID;
    private int tensionUnitsID;
    private int weakLinkStrengthUnitsID;
    private int winchingSpeedUnitsID;
    private int ballastWeightUnitsID;
    private int baggageWeightUnitsID;
    private int passengerWeightUnitsID;
    private JCheckBox baggageCheckBox;
    private javax.swing.JList sailplaneJList;
    private JTextField stallSpeedField;
    private JTextField grossWeightField;
    private JTextField emptyWeightField;
    private JTextField nNumberField;
    private JTextField ballastField;
    private JTextField weakLinkField;
    private JTextField tensionField;
    private JTextField releaseAngleField;
    private JTextField passengerWeightField;
    private JTextField winchingSpeedField;
    private JTextField baggageField;
    private CurrentLaunchInformation launchInfo;
    private JButton editButton;
    
    public String getbaggageField()
    {
        return(baggageField.getText());
    }
    
    public String getballastField()
    {
        return(ballastField.getText());
    }
    
    public String getpassengerWeightField()
    {
        return(passengerWeightField.getText());
    }
    
    public Boolean getbaggageCheckBox()
    {
        return(baggageCheckBox.isSelected());
    }
    
    public void setbaggageField(float setValue)
    {
        baggageField.setText(String.format("%.2f", (setValue * 
                                    UnitConversionRate.convertWeightUnitIndexToFactor(
                                            currentData.getCurrentProfile().getUnitSetting("baggageWeight")))));
    }
    
    public void setballastField(float setValue)
    {
        ballastField.setText(String.format("%.2f", (setValue * 
                                    UnitConversionRate.convertWeightUnitIndexToFactor(
                                            currentData.getCurrentProfile().getUnitSetting("ballastWeight")))));
    }
    
    public void setpassengerWeightField(float setValue)
    {
        passengerWeightField.setText(String.format("%.2f", (setValue * 
                                    UnitConversionRate.convertWeightUnitIndexToFactor(
                                            currentData.getCurrentProfile().getUnitSetting("passengerWeight")))));
    }
    
    public void setupUnits()
    {
        emptyWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("emptyWeight");
        String emptyWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(emptyWeightUnitsID);
        emptyWeightUnitsLabel.setText(emptyWeightUnitsString);
        
        maxGrossWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("maxGrossWeight");
        String maxGrossWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(maxGrossWeightUnitsID);
        maxGrossWeightUnitsLabel.setText(maxGrossWeightUnitsString);
        
        stallSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("stallSpeed");
        String stallSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(stallSpeedUnitsID);
        stallSpeedUnitsLabel.setText(stallSpeedUnitsString);
        
        ballastWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("ballastWeight");
        String ballastWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(ballastWeightUnitsID);
        ballastWeightUnitsLabel.setText(ballastWeightUnitsString);
        
        baggageWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("baggageWeight");
        String baggageWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(baggageWeightUnitsID);
        baggageWeightUnitsLabel.setText(baggageWeightUnitsString);
        
        passengerWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("passengerWeight");
        String passengerWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(passengerWeightUnitsID);
        passengerWeightUnitsLabel.setText(passengerWeightUnitsString);
        
        tensionUnitsID = currentData.getCurrentProfile().getUnitSetting("maxTension");
        String tensionUnitsString = UnitLabelUtilities.tensionUnitIndexToString(tensionUnitsID);
        tensionUnitsLabel.setText(tensionUnitsString);
        
        weakLinkStrengthUnitsID = currentData.getCurrentProfile().getUnitSetting("weakLinkStrength");
        String weakLinkStrengthUnitsString = UnitLabelUtilities.tensionUnitIndexToString(weakLinkStrengthUnitsID);
        weakLinkStrengthUnitsLabel.setText(weakLinkStrengthUnitsString);
        
        winchingSpeedUnitsID = currentData.getCurrentProfile().getUnitSetting("winchingSpeed");
        String winchingSpeedUnitsString = UnitLabelUtilities.velocityUnitIndexToString(winchingSpeedUnitsID);
        winchingSpeedUnitsLabel.setText(winchingSpeedUnitsString);
    }
    
    @Override
    public void update()
    {
        setupUnits();
        initSailPlaneList();
        DefaultListModel sailplaneModel = new DefaultListModel();
        sailplaneModel.clear();
        for(Object str: sailplanes){
                sailplaneModel.addElement(str);
        }
        sailplaneJList.setModel(sailplaneModel);
        Sailplane currentSailplane = currentData.getCurrentGlider();
        try{
            sailplaneJList.setSelectedValue(currentSailplane.toString(), true);
            sailplaneScrollPane.setViewportView(sailplaneJList);

            nNumberField.setText("" + currentSailplane.getRegistration());
            nNumberField.setBackground(Color.GREEN);

            weakLinkField.setText(String.valueOf(currentSailplane.getMaxWeakLinkStrength() 
                    * UnitConversionRate.convertTensionUnitIndexToFactor(weakLinkStrengthUnitsID)));
            weakLinkField.setBackground(Color.GREEN);

            tensionField.setText(String.valueOf(currentSailplane.getMaxTension() 
                    * UnitConversionRate.convertTensionUnitIndexToFactor(tensionUnitsID)));
            tensionField.setBackground(Color.GREEN);

            releaseAngleField.setText(String.valueOf(currentSailplane.getCableReleaseAngle()));
            releaseAngleField.setBackground(Color.GREEN);

            stallSpeedField.setText(String.valueOf(currentSailplane.getIndicatedStallSpeed() 
                    * UnitConversionRate.convertSpeedUnitIndexToFactor(stallSpeedUnitsID)));
            stallSpeedField.setBackground(Color.GREEN);

            grossWeightField.setText(String.valueOf(currentSailplane.getMaximumGrossWeight() 
                    * UnitConversionRate.convertWeightUnitIndexToFactor(maxGrossWeightUnitsID)));
            grossWeightField.setBackground(Color.GREEN);

            emptyWeightField.setText(String.valueOf(currentSailplane.getEmptyWeight() 
                    * UnitConversionRate.convertWeightUnitIndexToFactor(emptyWeightUnitsID)));
            emptyWeightField.setBackground(Color.GREEN);

            winchingSpeedField.setText(String.valueOf(currentSailplane.getMaxWinchingSpeed() 
                    * UnitConversionRate.convertSpeedUnitIndexToFactor(winchingSpeedUnitsID)));
            winchingSpeedField.setBackground(Color.GREEN);

            if(currentSailplane.getCarryBallast())
            {
                ballastField.setEnabled(true);
                ballastField.setBackground(Color.WHITE);
            }
            if(!currentSailplane.getCarryBallast())
            {
                ballastField.setEnabled(false);
                ballastField.setBackground(Color.LIGHT_GRAY);
            }
            if(currentSailplane.getMultipleSeats())
            {
                passengerWeightField.setEnabled(true);
                passengerWeightField.setBackground(Color.WHITE);
            }
            if(!currentSailplane.getMultipleSeats())
            {
                passengerWeightField.setEnabled(false);
                passengerWeightField.setBackground(Color.LIGHT_GRAY);
            }
        }
        catch(Exception e){}
    }
    
    private Observer getObserver() {
        return this;
    }
    
    public void clear()
    {
        sailplaneJList.clearSelection();
        nNumberField.setText("No Glider Selected");
        nNumberField.setBackground(Color.WHITE);
        weakLinkField.setText("No Glider Selected");
        weakLinkField.setBackground(Color.WHITE);
        tensionField.setText("No Glider Selected");
        tensionField.setBackground(Color.WHITE);
        releaseAngleField.setText("No Glider Selected");
        releaseAngleField.setBackground(Color.WHITE);
        stallSpeedField.setText("No Glider Selected");
        stallSpeedField.setBackground(Color.WHITE);
        grossWeightField.setText("No Glider Selected");
        grossWeightField.setBackground(Color.WHITE);
        emptyWeightField.setText("No Glider Selected");
        emptyWeightField.setBackground(Color.WHITE);
        winchingSpeedField.setText("No Glider Selected");
        winchingSpeedField.setBackground(Color.WHITE);
        editButton.setEnabled(false);
    }
    
    private void sailplaneJListSelectionChanged(ListSelectionEvent listSelectionEvent) 
    {
        if(sailplaneJList.getSelectedIndex() >= 0){
            try{
                Sailplane theSailplane = (Sailplane)sailplaneJList.getSelectedValue();
                currentData.setCurrentGlider(theSailplane);
                
                nNumberField.setText("" + theSailplane.getRegistration());
                nNumberField.setBackground(Color.GREEN);
                nNumberField.setHorizontalAlignment(JTextField.RIGHT);

                weakLinkField.setText(String.valueOf(theSailplane.getMaxWeakLinkStrength() * UnitConversionRate.convertTensionUnitIndexToFactor(weakLinkStrengthUnitsID)));
                weakLinkField.setBackground(Color.GREEN);
                weakLinkField.setHorizontalAlignment(JTextField.RIGHT);

                tensionField.setText(String.valueOf(theSailplane.getMaxTension() * UnitConversionRate.convertTensionUnitIndexToFactor(tensionUnitsID)));
                tensionField.setBackground(Color.GREEN);
                tensionField.setHorizontalAlignment(JTextField.RIGHT);

                releaseAngleField.setText(String.valueOf(theSailplane.getCableReleaseAngle()));
                releaseAngleField.setBackground(Color.GREEN);
                releaseAngleField.setHorizontalAlignment(JTextField.RIGHT);

                stallSpeedField.setText(String.valueOf(theSailplane.getIndicatedStallSpeed() * UnitConversionRate.convertSpeedUnitIndexToFactor(stallSpeedUnitsID)));
                stallSpeedField.setBackground(Color.GREEN);
                stallSpeedField.setHorizontalAlignment(JTextField.RIGHT);

                grossWeightField.setText(String.valueOf(theSailplane.getMaximumGrossWeight() * UnitConversionRate.convertWeightUnitIndexToFactor(maxGrossWeightUnitsID)));
                grossWeightField.setBackground(Color.GREEN);
                grossWeightField.setHorizontalAlignment(JTextField.RIGHT);

                emptyWeightField.setText(String.valueOf(theSailplane.getEmptyWeight() * UnitConversionRate.convertWeightUnitIndexToFactor(emptyWeightUnitsID)));
                emptyWeightField.setBackground(Color.GREEN);
                emptyWeightField.setHorizontalAlignment(JTextField.RIGHT);

                winchingSpeedField.setText(String.valueOf(theSailplane.getMaxWinchingSpeed() * UnitConversionRate.convertSpeedUnitIndexToFactor(winchingSpeedUnitsID)));
                winchingSpeedField.setBackground(Color.GREEN);
                winchingSpeedField.setHorizontalAlignment(JTextField.RIGHT);
                
                if(theSailplane.getCarryBallast())
                {
                    ballastField.setEnabled(true);
                    ballastField.setBackground(Color.WHITE);
                    ballastField.setHorizontalAlignment(JTextField.RIGHT);
                }
                if(!theSailplane.getCarryBallast())
                {
                    ballastField.setEnabled(false);
                    ballastField.setBackground(Color.LIGHT_GRAY);
                    ballastField.setHorizontalAlignment(JTextField.RIGHT);
                }
                if(theSailplane.getMultipleSeats())
                {
                    passengerWeightField.setEnabled(true);
                    passengerWeightField.setBackground(Color.WHITE);
                    passengerWeightField.setHorizontalAlignment(JTextField.RIGHT);
                }
                if(!theSailplane.getMultipleSeats())
                {
                    passengerWeightField.setEnabled(false);
                    passengerWeightField.setBackground(Color.LIGHT_GRAY);
                    passengerWeightField.setHorizontalAlignment(JTextField.RIGHT);
                }
            } catch(Exception e) {
                //TODO respond to error
            }
            editButton.setEnabled(true);
        }
    }
    
    private void initSailPlaneList() {
            RecentLaunchSelections recent = RecentLaunchSelections.getRecentLaunchSelections();
            sailplanes = DatabaseEntrySelect.getSailplanes();
            List<Sailplane> recentSailplanes = recent.getRecentSailplane();
            for (int i = 0; i < recentSailplanes.size(); i++){
                sailplanes.add(0, recentSailplanes.get(i));
            }
    }
    
    /**
     * Creates new form sailplanePanel
     */
    public SailplanePanel() {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        launchInfo = CurrentLaunchInformation.getCurrentLaunchInformation();
        launchInfo.setSailplanePanel(this);
        initSailPlaneList();
        initComponents();
        setupUnits();
    }
    
    /**
     * Create the panel.
     */
    public void initComponents() {
        sailplaneJList = new javax.swing.JList();
        
        setLayout(new BorderLayout(0, 0));
        
        add(sailplaneScrollPane, BorderLayout.NORTH);
        DefaultListModel sailplaneModel = new DefaultListModel();
        for(Object str: sailplanes){
            sailplaneModel.addElement(str);
        }
        sailplaneJList.setModel(sailplaneModel);
        sailplaneJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                sailplaneJListSelectionChanged(listSelectionEvent);
            }
        });
        sailplaneJList.setSelectedIndex(-1);
        sailplaneScrollPane.setViewportView(sailplaneJList);

        JPanel attributesPanel = new JPanel();
        attributesPanel.setBackground(Color.WHITE);
        JScrollPane attributesPanelScrollPane = new JScrollPane();
        add(attributesPanelScrollPane, BorderLayout.CENTER);
        attributesPanel.setPreferredSize(new Dimension(700,400));
        attributesPanelScrollPane.setViewportView(attributesPanel);
        attributesPanelScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        attributesPanelScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        attributesPanel.setLayout(null);
        
        JLabel nNumberLabel = new JLabel("Registration Number:");
        nNumberLabel.setBounds(10, 53, 125, 14);
        attributesPanel.add(nNumberLabel);
        
        JLabel emptyWeightLabel = new JLabel("Empty Weight:");
        emptyWeightLabel.setBounds(10, 78, 86, 14);
        attributesPanel.add(emptyWeightLabel);
        
        JLabel maxGrossWeightLabel = new JLabel("Max Gross Weight:");
        maxGrossWeightLabel.setBounds(10, 103, 117, 14);
        attributesPanel.add(maxGrossWeightLabel);
        
        JLabel lblIndicatedStallSpeed = new JLabel("Indicated Stall Speed:");
        lblIndicatedStallSpeed.setBounds(10, 128, 140, 14);
        attributesPanel.add(lblIndicatedStallSpeed);
        
        stallSpeedField = new JTextField();
        stallSpeedField.setBackground(Color.WHITE);
        stallSpeedField.setEditable(false);
        stallSpeedField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        stallSpeedField.setBounds(160, 125, 110, 20);
        attributesPanel.add(stallSpeedField);
        stallSpeedField.setColumns(10);
        stallSpeedField.setText("No Glider Selected");
        
        grossWeightField = new JTextField();
        grossWeightField.setBackground(Color.WHITE);
        grossWeightField.setEditable(false);
        grossWeightField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        grossWeightField.setBounds(160, 100, 110, 20);
        attributesPanel.add(grossWeightField);
        grossWeightField.setColumns(10);
        grossWeightField.setText("No Glider Selected");
        
        emptyWeightField = new JTextField();
        emptyWeightField.setBackground(Color.WHITE);
        emptyWeightField.setEditable(false);
        emptyWeightField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        emptyWeightField.setBounds(160, 75, 110, 20);
        attributesPanel.add(emptyWeightField);
        emptyWeightField.setColumns(10);
        emptyWeightField.setText("No Glider Selected");
        
        nNumberField = new JTextField();
        nNumberField.setBackground(Color.WHITE);
        nNumberField.setEditable(false);
        nNumberField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        nNumberField.setBounds(160, 50, 110, 20);
        attributesPanel.add(nNumberField);
        nNumberField.setColumns(10);
        nNumberField.setText("No Glider Selected");
                
        JLabel ballastLabel = new JLabel("Ballast Weight:");
        ballastLabel.setBounds(10, 189, 117, 14);
        attributesPanel.add(ballastLabel);
        
        ballastField = new JTextField();
        ballastField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        ballastField.setEnabled(false);
        ballastField.setBounds(160, 186, 110, 20);
        attributesPanel.add(ballastField);
        ballastField.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                    updateLaunchInfo(ballastField);
        	}
        });
        ballastField.setBackground(Color.LIGHT_GRAY);
        ballastField.setColumns(10);
        
        JLabel maxWinchingSpeedLabel = new JLabel("Max Winching Speed:");
        maxWinchingSpeedLabel.setBounds(320, 53, 140, 14);
        attributesPanel.add(maxWinchingSpeedLabel);
        
        JLabel maxWeakLinkLabel = new JLabel("Max Weak Link Strength:");
        maxWeakLinkLabel.setBounds(320, 78, 159, 14);
        attributesPanel.add(maxWeakLinkLabel);
        
        JLabel maxTensionLabel = new JLabel("Max Tension:");
        maxTensionLabel.setBounds(320, 103, 140, 14);
        attributesPanel.add(maxTensionLabel);
        
        JLabel cableReleaseAngleLabel = new JLabel("Cable Release Angle:");
        cableReleaseAngleLabel.setBounds(320, 128, 140, 14);
        attributesPanel.add(cableReleaseAngleLabel);
        
        JLabel passengerWeightLabel = new JLabel("Total Passenger Weight:");
        passengerWeightLabel.setBounds(320, 189, 140, 14);
        attributesPanel.add(passengerWeightLabel);
        
        weakLinkField = new JTextField();
        weakLinkField.setBackground(Color.WHITE);
        weakLinkField.setEditable(false);
        weakLinkField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        weakLinkField.setBounds(487, 75, 120, 20);
        attributesPanel.add(weakLinkField);
        weakLinkField.setColumns(10);
        weakLinkField.setText("No Glider Selected");
        
        tensionField = new JTextField();
        tensionField.setBackground(Color.WHITE);
        tensionField.setEditable(false);
        tensionField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        tensionField.setBounds(487, 100, 120, 20);
        attributesPanel.add(tensionField);
        tensionField.setColumns(10);
        tensionField.setText("No Glider Selected");
        
        releaseAngleField = new JTextField();
        releaseAngleField.setBackground(Color.WHITE);
        releaseAngleField.setEditable(false);
        releaseAngleField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        releaseAngleField.setBounds(487, 125, 120, 20);
        attributesPanel.add(releaseAngleField);
        releaseAngleField.setColumns(10);
        releaseAngleField.setText("No Glider Selected");
        
        passengerWeightField = new JTextField();
        passengerWeightField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        passengerWeightField.setEnabled(false);
        passengerWeightField.setBounds(487, 186, 120, 20);
        attributesPanel.add(passengerWeightField);
        passengerWeightField.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                    updateLaunchInfo(passengerWeightField);
        	}
        });
        passengerWeightField.setColumns(10);
        passengerWeightField.setBackground(Color.LIGHT_GRAY);
        
        winchingSpeedField = new JTextField();
        winchingSpeedField.setBackground(Color.WHITE);
        winchingSpeedField.setEditable(false);
        winchingSpeedField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        winchingSpeedField.setBounds(487, 50, 120, 20);
        attributesPanel.add(winchingSpeedField);
        winchingSpeedField.setColumns(10);
        winchingSpeedField.setText("No Glider Selected");
        
        baggageCheckBox = new JCheckBox();
        baggageCheckBox.setText("Baggage?");
        baggageCheckBox.setBackground(Color.WHITE);
        baggageCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    baggageField.setEnabled(true);
                    baggageField.setBackground(Color.WHITE);
                }
                else
                {
                    baggageField.setEnabled(false);
                    baggageField.setBackground(Color.LIGHT_GRAY);
                }
            }
        });
        baggageCheckBox.setBounds(10, 220, 97, 23);
        attributesPanel.add(baggageCheckBox);
        
        JLabel lblBaggageWeight = new JLabel("Baggage Weight:");
        lblBaggageWeight.setBounds(10, 250, 97, 14);
        attributesPanel.add(lblBaggageWeight);
        
        baggageField = new JTextField();
        baggageField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        baggageField.setEnabled(false);
        baggageField.setBounds(160, 247, 110, 20);
        attributesPanel.add(baggageField);
        baggageField.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                    updateLaunchInfo(baggageField);
        	}
        });
        baggageField.setColumns(10);
        baggageField.setBackground(Color.LIGHT_GRAY);
        
        JLabel lblGlider = new JLabel("Glider");
        lblGlider.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblGlider.setBounds(10, 20, 140, 31);
        attributesPanel.add(lblGlider);
        
        JButton addNewButton = new JButton("Add New");
        addNewButton.setBackground(new Color(200,200,200));
        addNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                    AddEditGlider AddEditGlider_ = new AddEditGlider(currentData.getCurrentGlider(), false);
                    AddEditGlider_.setVisible(true);
                    AddEditGlider_.attach(getObserver());
        	}
        });
        addNewButton.setBounds(201, 0, 89, 23);
        attributesPanel.add(addNewButton);
        
        editButton = new JButton("Edit");
        editButton.setBackground(new Color(200,200,200));
        editButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                    AddEditGlider AddEditGlider_ = new AddEditGlider(currentData.getCurrentGlider(), true);
                    AddEditGlider_.setVisible(true);
                    AddEditGlider_.attach(getObserver());
        	}
        });
        editButton.setBounds(289, 0, 89, 23);
        editButton.setEnabled(false);
        attributesPanel.add(editButton);
        
        emptyWeightUnitsLabel.setBounds(280, 78, 46, 14);
        attributesPanel.add(emptyWeightUnitsLabel);
        
        maxGrossWeightUnitsLabel.setBounds(280, 103, 46, 14);
        attributesPanel.add(maxGrossWeightUnitsLabel);
        
        stallSpeedUnitsLabel.setBounds(280, 128, 46, 14);
        attributesPanel.add(stallSpeedUnitsLabel);
        
        ballastWeightUnitsLabel.setBounds(280, 189, 46, 14);
        attributesPanel.add(ballastWeightUnitsLabel);
        
        baggageWeightUnitsLabel.setBounds(280, 250, 46, 14);
        attributesPanel.add(baggageWeightUnitsLabel);
        
        passengerWeightUnitsLabel.setBounds(617, 189, 46, 14);
        attributesPanel.add(passengerWeightUnitsLabel);
        
        JLabel cableReleaseAngleUnits = new JLabel("degrees");
        cableReleaseAngleUnits.setBounds(617, 128, 65, 14);
        attributesPanel.add(cableReleaseAngleUnits);
        
        tensionUnitsLabel.setBounds(617, 103, 46, 14);
        attributesPanel.add(tensionUnitsLabel);
        
        weakLinkStrengthUnitsLabel.setBounds(617, 78, 46, 14);
        attributesPanel.add(weakLinkStrengthUnitsLabel);
        
        winchingSpeedUnitsLabel.setBounds(617, 53, 46, 14);
        attributesPanel.add(winchingSpeedUnitsLabel);
    }
    
    private void updateLaunchInfo(JTextField textField){
        try{
            launchInfo.update("Manual Entry");
            Float.parseFloat(textField.getText());
            textField.setBackground(Color.GREEN);
        }catch(NumberFormatException e){
            textField.setBackground(Color.PINK);
        }
    }
            
    @Override
    public void update(String msg) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
