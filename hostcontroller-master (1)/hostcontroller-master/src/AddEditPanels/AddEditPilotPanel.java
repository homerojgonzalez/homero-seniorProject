package AddEditPanels;

import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Pilot;
import DatabaseUtilities.DatabaseEntryDelete;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;
import ParameterSelection.Preference;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;


public class AddEditPilotPanel extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField middleNameField;
    private JTextField flightWeightField;
    private ButtonGroup pilotCapability;
    private ButtonGroup pilotLaunchPref;
    private JTextField emergencyContactNameField;
    private JTextField emergencyContactPhoneField;
    private JTextField medInfoNameField;
    private JTextField medInfoPhoneField;
    private JTextArea optionalInfoField;
    private Pilot currentPilot;
    private boolean isEditEntry;
    private Observer parent;
    private CurrentDataObjectSet currentData;
    private int flightWeightUnitsID;
    private JLabel flightWeightUnitsLabel = new JLabel();
    
    public void setupUnits()
    {
        flightWeightUnitsID = currentData.getCurrentProfile().getUnitSetting("flightWeight");
        String flightWeightUnitsString = UnitLabelUtilities.weightUnitIndexToString(flightWeightUnitsID);
        flightWeightUnitsLabel.setText(flightWeightUnitsString);
    }
    
    public void attach(Observer o)
    {
        parent = o;
    }
    
    /**
     * Create the frame.
     */
    public AddEditPilotPanel(Pilot editPilot, boolean isEditEntry) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        setupUnits();
        
        if (!isEditEntry || editPilot == null){
            editPilot = new Pilot(0, "", "", "", 0, "", 0, "", "", "");
        }
        this.isEditEntry = isEditEntry;
        currentPilot = editPilot;
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        setTitle("Pilot");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        JLabel firstNameLabel = new JLabel("First Name: *");
        firstNameLabel.setBounds(10, 11, 86, 14);
        panel.add(firstNameLabel);
        
        JLabel middleNameLabel = new JLabel("Middle Name:");
        middleNameLabel.setBounds(10, 36, 86, 14);
        panel.add(middleNameLabel);
        
        JLabel lastNameLabel = new JLabel("Last Name: *");
        lastNameLabel.setBounds(10, 61, 117, 14);
        panel.add(lastNameLabel);
        
        JLabel flightWeightLabel = new JLabel("Flight Weight: *");
        flightWeightLabel.setBounds(10, 86, 140, 14);
        panel.add(flightWeightLabel);
        
        flightWeightField = new JTextField();
        if (isEditEntry){

            flightWeightField.setText(String.valueOf((currentPilot.getWeight() * UnitConversionRate.convertWeightUnitIndexToFactor(flightWeightUnitsID))));
        }
        flightWeightField.setBounds(160, 83, 110, 20);
        panel.add(flightWeightField);
        flightWeightField.setColumns(10);
        
        lastNameField = new JTextField(editPilot.getLastName());
        lastNameField.setBounds(160, 58, 110, 20);
        panel.add(lastNameField);
        lastNameField.setColumns(10);
        
        middleNameField = new JTextField(editPilot.getMiddleName());
        middleNameField.setBounds(160, 33, 110, 20);
        panel.add(middleNameField);
        middleNameField.setColumns(10);
        
        firstNameField = new JTextField(editPilot.getFirstName());
        firstNameField.setBounds(160, 8, 110, 20);
        panel.add(firstNameField);
        firstNameField.setColumns(10);
        
        JLabel CapabilityLabel = new JLabel("Capability: *");
        CapabilityLabel.setBounds(10, 132, 69, 14);
        panel.add(CapabilityLabel);
        
        JRadioButton studentRadioButton = new JRadioButton("Student");
        studentRadioButton.setActionCommand("Student");
        studentRadioButton.setBounds(85, 128, 109, 23);
        panel.add(studentRadioButton);
        
        JRadioButton proficientRadioButton = new JRadioButton("Proficient");
        proficientRadioButton.setActionCommand("Proficient");
        proficientRadioButton.setBounds(85, 153, 109, 23);
        panel.add(proficientRadioButton);
        
        JRadioButton advancedRadioButton = new JRadioButton("Advanced");
        advancedRadioButton.setActionCommand("Advanced");
        advancedRadioButton.setBounds(85, 178, 109, 23);
        panel.add(advancedRadioButton);
        
        pilotCapability = new ButtonGroup();
        pilotCapability.add(studentRadioButton);
        pilotCapability.add(proficientRadioButton);
        pilotCapability.add(advancedRadioButton);
        switch(editPilot.getCapability()){
            case "Student":
                studentRadioButton.doClick();
                break;
            case "Proficient":
                proficientRadioButton.doClick();
                break;
            case "Advanced":
                advancedRadioButton.doClick();
                break;
        }
        
        JLabel preferenceLabel = new JLabel("Preference: *");
        preferenceLabel.setBounds(245, 132, 75, 14);
        panel.add(preferenceLabel);
        
        JRadioButton mildRadioButton = new JRadioButton("Mild");
        mildRadioButton.setActionCommand("Mild");
        mildRadioButton.setBounds(326, 128, 109, 23);
        panel.add(mildRadioButton);
        
        JRadioButton nominalRadioButton = new JRadioButton("Nominal");
        nominalRadioButton.setActionCommand("Nominal");
        nominalRadioButton.setBounds(326, 153, 109, 23);
        panel.add(nominalRadioButton);
        
        JRadioButton performanceRadioButton = new JRadioButton("Performance");
        performanceRadioButton.setActionCommand("Performance");
        performanceRadioButton.setBounds(326, 178, 109, 23);
        panel.add(performanceRadioButton);
        
        pilotLaunchPref = new ButtonGroup();
        pilotLaunchPref.add(mildRadioButton);
        pilotLaunchPref.add(nominalRadioButton);
        pilotLaunchPref.add(performanceRadioButton);
        if(editPilot.getPreference() == 0){
                mildRadioButton.doClick();
        }
        if(editPilot.getPreference() == 1){
                nominalRadioButton.doClick();
        }
        if(editPilot.getPreference() == 2){
                performanceRadioButton.doClick();
        }
        
        JLabel emergencyContactLabel = new JLabel("Emergency Contact:");
        emergencyContactLabel.setBounds(10, 208, 117, 14);
        panel.add(emergencyContactLabel);
        
        JLabel emergencyContactNameLabel = new JLabel("Name:");
        emergencyContactNameLabel.setBounds(33, 233, 46, 14);
        panel.add(emergencyContactNameLabel);
        
        JLabel emergencyContactPhoneLabel = new JLabel("Phone:");
        emergencyContactPhoneLabel.setBounds(33, 258, 46, 14);
        panel.add(emergencyContactPhoneLabel);
        
        String emergencyContact = editPilot.getEmergencyName();
        String emergencyContactName;
        String emergencyContactPhone;
        int p = emergencyContact.indexOf('%');
        if (p >= 0) 
        {
            emergencyContactName = emergencyContact.substring(0, p);
            emergencyContactPhone = emergencyContact.substring(p + 1);
        }
        else
        {
            emergencyContactName = "";
            emergencyContactPhone = "";
        }

        emergencyContactNameField = new JTextField(emergencyContactName);
        emergencyContactNameField.setBounds(85, 230, 110, 20);
        panel.add(emergencyContactNameField);
        emergencyContactNameField.setColumns(10);
        
        emergencyContactPhoneField = new JTextField(emergencyContactPhone);
        emergencyContactPhoneField.setBounds(85, 255, 109, 20);
        panel.add(emergencyContactPhoneField);
        emergencyContactPhoneField.setColumns(10);
        
        /*JLabel medInfoLabel = new JLabel("Primary Physician:");
        medInfoLabel.setBounds(244, 205, 117, 14);
        panel.add(medInfoLabel);
        
        JLabel medInfoNameLabel = new JLabel("Name:");
        medInfoNameLabel.setBounds(267, 230, 46, 14);
        panel.add(medInfoNameLabel);
        
        String medInfo = editPilot.getMedInfo();
        String medInfoName;
        String medInfoPhone;
        int t = medInfo.indexOf('%');
        if (t >= 0) 
        {
            medInfoName = medInfo.substring(0, t);
            medInfoPhone = medInfo.substring(t + 1);
        }
        else
        {
            medInfoName = "";
            medInfoPhone = "";
        }
        
        medInfoNameField = new JTextField(medInfoName);
        medInfoNameField.setColumns(10);
        medInfoNameField.setBounds(319, 227, 110, 20);
        panel.add(medInfoNameField);
        
        medInfoPhoneField = new JTextField(medInfoPhone);
        medInfoPhoneField.setColumns(10);
        medInfoPhoneField.setBounds(319, 252, 109, 20);
        panel.add(medInfoPhoneField);
        
        JLabel medInfoPhoneLabel = new JLabel("Phone:");
        medInfoPhoneLabel.setBounds(267, 255, 46, 14);
        panel.add(medInfoPhoneLabel);*/
        
        JLabel additionalInformationLabel = new JLabel("Additional Information:");
        additionalInformationLabel.setBounds(10, 300, 152, 14);
        panel.add(additionalInformationLabel);
        
        optionalInfoField = new JTextArea(editPilot.getOptionalInfo());
        optionalInfoField.setBounds(10, 325, 450, 88);
        panel.add(optionalInfoField);
        optionalInfoField.setColumns(10);
        
        JLabel RequiredNoteLabel = new JLabel("* Indicates required field");
        RequiredNoteLabel.setBounds(10, 419, 200, 14);
        panel.add(RequiredNoteLabel);
        
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(0, 438, 89, 23);
        submitButton.setBackground(new Color(200,200,200));
        panel.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                submitData();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setEnabled(isEditEntry);
        deleteButton.setBounds(90, 438, 89, 23);
        deleteButton.setBackground(new Color(200,200,200));
        panel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                deleteCommand();
            }
        });
        
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(180, 438, 89, 23);
        clearButton.setBackground(new Color(200,200,200));
        panel.add(clearButton);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                clearData();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(270, 438, 89, 23);
        cancelButton.setBackground(new Color(200,200,200));
        panel.add(cancelButton);
        
        flightWeightUnitsLabel.setBounds(280, 86, 46, 14);
        panel.add(flightWeightUnitsLabel);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                cancelCommand();
            }
        });

    }
        
    protected void submitData(){
        if (isComplete()){
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String middleName = middleNameField.getText();
            String emergencyContact = emergencyContactNameField.getText() +
                    "%" + emergencyContactPhoneField.getText();
            //String medicalInformation = medInfoNameField.getText() +
            //        "%" + medInfoPhoneField.getText();
            String optionalInformation = optionalInfoField.getText();
            float weight = 0;
            try {
                weight = Float.parseFloat(flightWeightField.getText()) / UnitConversionRate.convertWeightUnitIndexToFactor(flightWeightUnitsID);
            }catch (NumberFormatException e) {
                weight = -1;
            }
            String capability = pilotCapability.getSelection().getActionCommand();
            float preference = Preference
                    .convertPreferenceStringToNum(pilotLaunchPref.getSelection().getActionCommand());
            try{
            int newPilotId = currentPilot.getPilotId();
            Pilot newPilot = new Pilot(newPilotId, firstName, lastName, middleName, 
                        weight, capability, preference, emergencyContact,
                        "", optionalInformation);
            
                CurrentDataObjectSet ObjectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
                ObjectSet.setCurrentPilot(newPilot);
                Object[] options = {"One-time Launch", "Save to Database"};
                int choice = JOptionPane.showOptionDialog(rootPane, "Do you want to use this Glider Position for a one-time launch or save it to the database?",
                    "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                
                if (choice == 0){
                    parent.update();
                    dispose();
                }
                else
                {
                    if (isEditEntry){
                        DatabaseEntryEdit.updateEntry(newPilot);
                    }
                    else{
                        Random randomId = new Random();
                        newPilot.setPilotId(randomId.nextInt(100000000));
                        while (DatabaseEntryIdCheck.IdCheck(newPilot)){
                            newPilotId = randomId.nextInt(100000000);
                        }
                        DatabaseUtilities.DatabaseEntryInsert.addPilotToDB(newPilot);
                    }

                    parent.update();
                    dispose();
                }
            }catch(SQLException e1) {
                e1.printStackTrace();
            }catch (ClassNotFoundException e2) {
                JOptionPane.showMessageDialog(rootPane, "Error: No access to database currently. Please try again later.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }
    
    public void deleteCommand(){
            DatabaseEntryDelete.DeleteEntry(currentPilot);
            CurrentDataObjectSet objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
            objectSet.clearPilot();
            JOptionPane.showMessageDialog(rootPane, currentPilot.toString() + " successfully deleted.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            parent.update();
            this.dispose(); 
    }
    
    public void cancelCommand(){
        this.dispose();
    }
    
    public boolean isComplete()
    {
        try
        {
            boolean emptyFields = false;
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String weightStr = flightWeightField.getText();
            firstNameField.setBackground(Color.WHITE);
            lastNameField.setBackground(Color.WHITE);
            flightWeightField.setBackground(Color.WHITE);
            
            if(firstName.isEmpty())
            {
                firstNameField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(lastName.isEmpty())
            {
                lastNameField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(weightStr.isEmpty())
            {
                flightWeightField.setBackground(Color.PINK);
                emptyFields = true;
            }
            
            if(pilotCapability.getSelection() == null){
                if (pilotLaunchPref.getSelection() == null){
                    throw new Exception("Select Capability and Launch Pref");
                }
                throw new Exception("Select Capability");
            }
            
            if(pilotLaunchPref.getSelection() == null){
                throw new Exception("Select Launch Preference");
            }
            
            if (emptyFields){
                throw new Exception("");
            }
            
            if (!weightStr.isEmpty()){
                Float.parseFloat(weightStr);
            }
            
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(rootPane, "Please input a numerical weight value", "Error", JOptionPane.INFORMATION_MESSAGE);
            //ew = new ErrWindow("Please input a numerical weight value");
            return false;
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Please complete all required fields\n" + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            //ew = new ErrWindow("Please complete all required fields\n" + e.getMessage());
            return false;
        }
        return true;
    }
    
    public void clearData(){
        firstNameField.setText("");
	lastNameField.setText("");
	middleNameField.setText("");
	flightWeightField.setText("");
        pilotCapability.clearSelection();
        pilotLaunchPref.clearSelection();
        emergencyContactNameField.setText("");
        emergencyContactPhoneField.setText("");
	medInfoNameField.setText("");
	medInfoPhoneField.setText("");
	optionalInfoField.setText("");
        
        firstNameField.setBackground(Color.WHITE);
	lastNameField.setBackground(Color.WHITE);
	flightWeightField.setBackground(Color.WHITE);
    }
}
