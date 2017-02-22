package ParameterSelection;

import Communications.Observer;
import Configuration.UnitConversionRate;
import DataObjects.CurrentDataObjectSet;
import DataObjects.RecentLaunchSelections;
import DataObjects.Drum;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import javax.swing.border.MatteBorder;
import Configuration.UnitLabelUtilities;
import DataObjects.Drive;
import DataObjects.Parachute;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DrumPanel extends JPanel implements Observer{
    private javax.swing.JList drumJList;
    private JTextField drumNameField;
    private JTextField kFactorField;
    private JTextField coreDiameterField;
    private JTextField cableLengthField;
    private JLabel DrumLabel;
    private CurrentDataObjectSet currentData;
    private List<Drum> drumNames = new ArrayList<Drum>();
    private JScrollPane drumScrollPane = new JScrollPane();
    private JLabel cableLengthUnits = new JLabel();
    private JLabel coreDiameterUnits = new JLabel();
    private int cableLengthUnitsID;
    private int coreDiameterUnitsID;
    private JTextField numLaunchesField;
    //private JTextField attachedParachuteField;
    private JComboBox attachedParachuteField;
    
    public void setupUnits()
    {
        cableLengthUnitsID = currentData.getCurrentProfile().getUnitSetting("cableLength");
        String cableLengthUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(cableLengthUnitsID);
        cableLengthUnits.setText(cableLengthUnitsString);
        
        coreDiameterUnitsID = currentData.getCurrentProfile().getUnitSetting("coreDiameter");
        String coreDiameterUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(coreDiameterUnitsID);
        coreDiameterUnits.setText(coreDiameterUnitsString);
    }
    
    @Override
    public void update()
    {
        setupUnits();
        initDrumList();
        DefaultListModel drumModel = new DefaultListModel();
        drumModel.clear();
        for(Object str: drumNames){
            drumModel.addElement(str);
        }
        drumJList.setModel(drumModel);
        Drum currentDrum = currentData.getCurrentDrum();
        //loadParachutesFromWinch();
        try{
            drumJList.setSelectedValue(currentDrum.toString(), true);
        
            drumScrollPane.setViewportView(drumJList);

            drumNameField.setText((currentDrum.getName()));
            drumNameField.setBackground(Color.GREEN);

            kFactorField.setText(String.valueOf(currentDrum.getKFactor()));
            kFactorField.setBackground(Color.GREEN);
            
            coreDiameterField.setText(String.valueOf(currentDrum.getCoreDiameter() * UnitConversionRate.convertDistanceUnitIndexToFactor(coreDiameterUnitsID)));
            coreDiameterField.setBackground(Color.GREEN);

            cableLengthField.setText(String.valueOf(currentDrum.getCableLength() * UnitConversionRate.convertDistanceUnitIndexToFactor(cableLengthUnitsID)));
            cableLengthField.setBackground(Color.GREEN);
            
            numLaunchesField.setText(String.valueOf(currentDrum.getNumLaunches()));
            numLaunchesField.setBackground(Color.GREEN);
            //attachedParachuteField.setText(String.valueOf(currentDrum.getParachute()));
            //attachedParachuteField.setBackground(Color.GREEN);
        }
        catch(Exception e){
        }
    }
    
    private void loadParachutesFromWinch()
    {
        attachedParachuteField.removeAllItems();
        attachedParachuteField.addItem("No Parachute");
        if(CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentWinch() != null)
        {   
            /*
            for(Parachute p : CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentWinch().getParachuteList())
            {
                attachedParachuteField.addItem(p);
            } 
            */
        }
    }
    
    private Observer getObserver() {
        return this;
    }
    
    private void initDrumList() {
        try{
            RecentLaunchSelections recent = RecentLaunchSelections.getRecentLaunchSelections();
            drumNames = new ArrayList<Drum>();
            /*
            for(Drive d : CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentWinch().getDriveList())
            {
                drumNames.addAll(CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentWinch().getDrumsForDrive(d.getName()));
            }
            */
            /*if (RecentLaunchSelections.IsInitialized()) {
                List<Drum> recentDrums = recent.getRecentDrum();
                for (int i = 0; i < recentDrums.size(); i++){
                    drumNames.add(0, recentDrums.get(i));
                }
            }*/
        }
        catch(Exception exp){
            //exp.printStackTrace();
        }
    }

    public void clear()
    {
        drumJList.clearSelection();
        drumNameField.setText("No Drum Selected");
        drumNameField.setBackground(Color.WHITE);
        kFactorField.setText("No Drum Selected");
        kFactorField.setBackground(Color.WHITE);
        coreDiameterField.setText("No Drum Selected");
        coreDiameterField.setBackground(Color.WHITE);
        cableLengthField.setText("No Drum Selected");
        cableLengthField.setBackground(Color.WHITE);
        numLaunchesField.setText("No Drum Selected");
        numLaunchesField.setBackground(Color.WHITE);
        //attachedParachuteField.setText("No Drum Selected");
        attachedParachuteField.setBackground(Color.WHITE);
    }
    
    private void drumJListSelectionChanged(ListSelectionEvent listSelectionEvent) {
        //loadParachutesFromWinch();
        if(drumJList.getSelectedIndex() >= 0){
            try{
                Drum currentDrum = (Drum)drumJList.getSelectedValue();
                currentData.setCurrentDrum(currentDrum);

                drumNameField.setText((currentDrum.getName()));
                drumNameField.setBackground(Color.GREEN);

                kFactorField.setText(String.valueOf(currentDrum.getKFactor()));
                kFactorField.setBackground(Color.GREEN);

                coreDiameterField.setText(String.valueOf(currentDrum.getCoreDiameter() * UnitConversionRate.convertDistanceUnitIndexToFactor(coreDiameterUnitsID)));
                coreDiameterField.setBackground(Color.GREEN);

                cableLengthField.setText(String.valueOf(currentDrum.getCableLength() * UnitConversionRate.convertDistanceUnitIndexToFactor(cableLengthUnitsID)));
                cableLengthField.setBackground(Color.GREEN);

                numLaunchesField.setText(String.valueOf(currentDrum.getNumLaunches()));
                numLaunchesField.setBackground(Color.GREEN);

                //attachedParachuteField.setText(String.valueOf(currentDrum.getParachute()));
                //attachedParachuteField.setBackground(Color.GREEN);  
            } catch(Exception e) {
                //TODO respond to error
            }
        }
    }
    
    /**
     * Creates new form DrumPanel
     */
    public DrumPanel() {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        initDrumList();
        initComponents();
        setupUnits();
        currentData.attach(this);
    }
    
    /**
     * Create the panel.
     */
    public void initComponents() {
        drumJList = new javax.swing.JList();
        
        setLayout(new BorderLayout(0, 0));

        add(drumScrollPane, BorderLayout.NORTH);
        DefaultListModel drumModel = new DefaultListModel();
        for(Object str: drumNames){
            drumModel.addElement(str);
        }
        drumJList.setModel(drumModel);

        drumJList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                drumJListSelectionChanged(listSelectionEvent);
            }
        });
        drumJList.setSelectedIndex(-1);
        drumScrollPane.setViewportView(drumJList);

        JPanel attributesPanel = new JPanel();
        attributesPanel.setBackground(Color.WHITE);
        JScrollPane attributesPanelScrollPane = new JScrollPane();
        add(attributesPanelScrollPane, BorderLayout.CENTER);
        attributesPanel.setPreferredSize(new Dimension(700,500));
        attributesPanelScrollPane.setViewportView(attributesPanel);
        attributesPanelScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        attributesPanelScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        attributesPanel.setLayout(null);
        
        JLabel drumNameLabel = new JLabel("Drum Name:");
        drumNameLabel.setBounds(10, 53, 86, 14);
        attributesPanel.add(drumNameLabel);
        
        JLabel coreDiameterLabel = new JLabel("Core Diameter:");
        coreDiameterLabel.setBounds(10, 78, 86, 14);
        attributesPanel.add(coreDiameterLabel);
        
        JLabel kFactorLabel = new JLabel("K-Factor:");
        kFactorLabel.setBounds(10, 103, 117, 14);
        attributesPanel.add(kFactorLabel);
        
        JLabel cableLengthLabel = new JLabel("Cable Length:");
        cableLengthLabel.setBounds(10, 128, 140, 14);
        attributesPanel.add(cableLengthLabel);
        
        cableLengthField = new JTextField();
        cableLengthField.setBackground(Color.WHITE);
        cableLengthField.setEditable(false);
        cableLengthField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        cableLengthField.setBounds(160, 125, 110, 20);
        attributesPanel.add(cableLengthField);
        cableLengthField.setColumns(10);
        cableLengthField.setText("No Drum Selected");
        
        kFactorField = new JTextField();
        kFactorField.setBackground(Color.WHITE);
        kFactorField.setEditable(false);
        kFactorField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        kFactorField.setBounds(160, 100, 110, 20);
        attributesPanel.add(kFactorField);
        kFactorField.setColumns(10);
        kFactorField.setText("No Drum Selected");
        
        coreDiameterField = new JTextField();
        coreDiameterField.setBackground(Color.WHITE);
        coreDiameterField.setEditable(false);
        coreDiameterField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        coreDiameterField.setBounds(160, 75, 110, 20);
        attributesPanel.add(coreDiameterField);
        coreDiameterField.setColumns(10);
        coreDiameterField.setText("No Drum Selected");
        
        drumNameField = new JTextField();
        drumNameField.setBackground(Color.WHITE);
        drumNameField.setEditable(false);
        drumNameField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        drumNameField.setBounds(160, 50, 110, 20);
        attributesPanel.add(drumNameField);
        drumNameField.setColumns(10);
        drumNameField.setText("No Drum Selected");
        
        DrumLabel = new JLabel("Drum");
        DrumLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        DrumLabel.setBounds(10, 20, 86, 31);
        attributesPanel.add(DrumLabel);
        
        cableLengthUnits.setBounds(280, 128, 46, 14);
        attributesPanel.add(cableLengthUnits);
        
        JLabel numLaunchesLabel = new JLabel("Number of Launches:");
        numLaunchesLabel.setBounds(10, 156, 140, 14);
        attributesPanel.add(numLaunchesLabel);
        
        numLaunchesField = new JTextField();
        numLaunchesField.setText("No Drum Selected");
        numLaunchesField.setEditable(false);
        numLaunchesField.setColumns(10);
        numLaunchesField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        numLaunchesField.setBackground(Color.WHITE);
        numLaunchesField.setBounds(160, 153, 110, 20);
        attributesPanel.add(numLaunchesField);
        
        JLabel attachedParachuteLabel = new JLabel("Attached Parachute:");
        attachedParachuteLabel.setBounds(10, 184, 140, 14);
        attributesPanel.add(attachedParachuteLabel);
        
        //attachedParachuteField = new JTextField();
        attachedParachuteField = new JComboBox();
        attachedParachuteField.addItem("No Drum Selected");
        attachedParachuteField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    JComboBox cb = (JComboBox)e.getSource();
                    try 
                    {
                        Parachute para = (Parachute)cb.getSelectedItem();
                        String driveName = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentDrum().getDrive().getName();
                        /*
                        for(Drum d : CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentWinch().getDrumsForDrive(driveName))
                        {
                            if(d.getName().equals(CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentDrum().getName()))
                            {
                                d.setParachute(para);
                            }
                        }
                        */
                        //update();
                    }
                    catch (ClassCastException cce)
                    {   
                        if(CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentDrum() != null)
                        {
                            String driveName = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentDrum().getDrive().getName();
                            /*
                            for(Drum d : CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentWinch().getDrumsForDrive(driveName))
                            {
                                if(d.getName().equals(CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentDrum().getName()))
                                {
                                    d.clearParachute();
                                }
                            }
                            */
                        }
                    }
                }
            }
        });  
        //loadParachutesFromWinch();
        //attachedParachuteField.setText("No Drum Selected");
        attachedParachuteField.setEditable(false);
        //attachedParachuteField.setColumns(10);
        attachedParachuteField.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
        attachedParachuteField.setBackground(Color.WHITE);
        attachedParachuteField.setBounds(160, 181, 110, 20);
        attributesPanel.add(attachedParachuteField);
        
        coreDiameterUnits.setBounds(280, 78, 46, 14);
        attributesPanel.add(coreDiameterUnits);
    }
            
    @Override
    public void update(String msg) {
        loadParachutesFromWinch();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
