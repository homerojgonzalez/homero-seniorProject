package AddEditPanels;

import static Communications.ErrorLogger.logError;
import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Sailplane;
import DatabaseUtilities.DatabaseEntryDelete;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;


public class AddEditGlider extends JFrame {

    private JPanel contentPane;
    private JTextField nNumberField;
    private JTextField emptyWeightField;
    private JTextField grossWeightField;
    private JTextField stallSpeedField;
    private JTextField weakLinkField;
    private JTextField tensionField;
    private JTextField releaseAngleField;
    private JTextField winchingSpeedField;
    private JCheckBox ballastCheckBox;
    private JCheckBox multipleSeatsCheckBox;
    private Sailplane currentGlider;
    private boolean isEditEntry;
    private Observer parent;
    private CurrentDataObjectSet currentData;
    private JLabel emptyWeightUnitsLabel = new JLabel();
    private JLabel maxGrossWeightUnitsLabel = new JLabel();
    private JLabel stallSpeedUnitsLabel = new JLabel();
    private JLabel tensionUnitsLabel = new JLabel();
    private JLabel weakLinkStrengthUnitsLabel = new JLabel();
    private JLabel winchingSpeedUnitsLabel = new JLabel();
    private int emptyWeightUnitsID;
    private int maxGrossWeightUnitsID;
    private int stallSpeedUnitsID;
    private int tensionUnitsID;
    private int weakLinkStrengthUnitsID;
    private int winchingSpeedUnitsID;
    
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
    
    public void attach(Observer o)
    {
        parent = o;
    }
    
    
    /**
     * Create the frame.
     */
    public AddEditGlider(Sailplane sailplaneEdited, boolean isEditEntry) {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        setupUnits();
        
        this.isEditEntry = isEditEntry;

        if (!isEditEntry || sailplaneEdited == null){
            sailplaneEdited = new Sailplane("", "", "", "", 0, 0, 0, 0, 0, 0, 0, false, false, "");
        }
        currentGlider = sailplaneEdited;
        
        setTitle("Glider");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 746, 320);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel nNumberLabel = new JLabel("Registration Number:");
        nNumberLabel.setBounds(10, 11, 125, 14);
        contentPane.add(nNumberLabel);
        
        JLabel emptyWeightLabel = new JLabel("Empty Weight:");
        emptyWeightLabel.setBounds(10, 36, 86, 14);
        contentPane.add(emptyWeightLabel);
        
        JLabel maxGrossWeightLabel = new JLabel("Max Gross Weight:");
        maxGrossWeightLabel.setBounds(10, 61, 117, 14);
        contentPane.add(maxGrossWeightLabel);
        
        JLabel lblIndicatedStallSpeed = new JLabel("Indicated Stall Speed:");
        lblIndicatedStallSpeed.setBounds(10, 86, 140, 14);
        contentPane.add(lblIndicatedStallSpeed);
        
        stallSpeedField = new JTextField();
        if (isEditEntry){
            stallSpeedField.setText(String.valueOf(currentGlider.getIndicatedStallSpeed() * UnitConversionRate.convertSpeedUnitIndexToFactor(stallSpeedUnitsID)));
        }
        stallSpeedField.setBounds(160, 83, 110, 20);
        contentPane.add(stallSpeedField);
        stallSpeedField.setColumns(10);
        
        grossWeightField = new JTextField();
        if (isEditEntry){
            grossWeightField.setText(String.valueOf(currentGlider.getMaximumGrossWeight() * UnitConversionRate.convertWeightUnitIndexToFactor(maxGrossWeightUnitsID)));
        }
        grossWeightField.setBounds(160, 58, 110, 20);
        contentPane.add(grossWeightField);
        grossWeightField.setColumns(10);
        
        emptyWeightField = new JTextField();
        if (isEditEntry){
            emptyWeightField.setText(String.valueOf(currentGlider.getEmptyWeight() * UnitConversionRate.convertWeightUnitIndexToFactor(emptyWeightUnitsID)));
        }
        emptyWeightField.setBounds(160, 33, 110, 20);
        contentPane.add(emptyWeightField);
        emptyWeightField.setColumns(10);
        
        nNumberField = new JTextField(currentGlider.getRegistration());
        nNumberField.setBounds(160, 8, 110, 20);
        contentPane.add(nNumberField);
        nNumberField.setColumns(10);
        
        ballastCheckBox = new JCheckBox("Can Carry Ballast?");
        ballastCheckBox.setSelected(currentGlider.getCarryBallast());
        ballastCheckBox.setBounds(10, 117, 154, 23);
        contentPane.add(ballastCheckBox);
        
        JLabel maxWinchingSpeedLabel = new JLabel("Max Winching Speed:");
        maxWinchingSpeedLabel.setBounds(320, 11, 140, 14);
        contentPane.add(maxWinchingSpeedLabel);
        
        JLabel maxWeakLinkLabel = new JLabel("Max Weak Link Strength:");
        maxWeakLinkLabel.setBounds(320, 36, 159, 14);
        contentPane.add(maxWeakLinkLabel);
        
        JLabel maxTensionLabel = new JLabel("Max Tension:");
        maxTensionLabel.setBounds(320, 61, 140, 14);
        contentPane.add(maxTensionLabel);
        
        JLabel cableReleaseAngleLabel = new JLabel("Cable Release Angle:");
        cableReleaseAngleLabel.setBounds(320, 86, 140, 14);
        contentPane.add(cableReleaseAngleLabel);
        
        multipleSeatsCheckBox = new JCheckBox("Multiple Seats?");
        multipleSeatsCheckBox.setSelected(currentGlider.getMultipleSeats());
        multipleSeatsCheckBox.setBounds(320, 117, 159, 23);
        contentPane.add(multipleSeatsCheckBox);
        
        weakLinkField = new JTextField();
        if (isEditEntry){
            weakLinkField.setText(String.valueOf(currentGlider.getMaxWeakLinkStrength() * UnitConversionRate.convertTensionUnitIndexToFactor(weakLinkStrengthUnitsID)));
        }
        weakLinkField.setBounds(487, 33, 120, 20);
        contentPane.add(weakLinkField);
        weakLinkField.setColumns(10);
        
        tensionField = new JTextField();
        if (isEditEntry){
            tensionField.setText(String.valueOf(currentGlider.getMaxTension() * UnitConversionRate.convertTensionUnitIndexToFactor(tensionUnitsID)));
        }
        tensionField.setBounds(487, 58, 120, 20);
        contentPane.add(tensionField);
        tensionField.setColumns(10);
        
        releaseAngleField = new JTextField();
        if (isEditEntry){
            releaseAngleField.setText(String.valueOf(currentGlider.getCableReleaseAngle()));
        }
        releaseAngleField.setBounds(487, 83, 120, 20);
        contentPane.add(releaseAngleField);
        releaseAngleField.setColumns(10);
        
        winchingSpeedField = new JTextField();
        if (isEditEntry){
            winchingSpeedField.setText(String.valueOf(currentGlider.getMaxWinchingSpeed() * UnitConversionRate.convertSpeedUnitIndexToFactor(winchingSpeedUnitsID)));
        }
        winchingSpeedField.setBounds(487, 8, 120, 20);
        contentPane.add(winchingSpeedField);
        winchingSpeedField.setColumns(10);
        
        JLabel RequiredNoteLabel = new JLabel("All fields required");
        RequiredNoteLabel.setBounds(10, 150, 200, 14);
        contentPane.add(RequiredNoteLabel);

/*
        JLabel lblUnits = new JLabel("Units*");
        lblUnits.setBounds(290, 44, 46, 14);
        contentPane.add(lblUnits);

        JLabel label = new JLabel("Units*");
        label.setBounds(290, 75, 46, 14);
        contentPane.add(label);

        JLabel label_1 = new JLabel("Units*");
        label_1.setBounds(290, 106, 46, 14);
        contentPane.add(label_1);

        JLabel label_2 = new JLabel("Units*");
        label_2.setBounds(290, 137, 46, 14);
        contentPane.add(label_2);*/

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(0, 180, 89, 23);
        submitButton.setBackground(new Color(200,200,200));
        contentPane.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                submitData();
            }
        });
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.setEnabled(isEditEntry);
        deleteButton.setBounds(90, 180, 89, 23);
        deleteButton.setBackground(new Color(200,200,200));
        contentPane.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                deleteCommand();
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(180, 180, 89, 23);
        clearButton.setBackground(new Color(200,200,200));
        contentPane.add(clearButton);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                clearData();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(270, 180, 89, 23);
        cancelButton.setBackground(new Color(200,200,200));
        contentPane.add(cancelButton);
        
        emptyWeightUnitsLabel.setBounds(280, 36, 46, 14);
        contentPane.add(emptyWeightUnitsLabel);
        
        maxGrossWeightUnitsLabel.setBounds(280, 61, 46, 14);
        contentPane.add(maxGrossWeightUnitsLabel);
        
        stallSpeedUnitsLabel.setBounds(280, 86, 46, 14);
        contentPane.add(stallSpeedUnitsLabel);
        
        winchingSpeedUnitsLabel.setBounds(617, 11, 46, 14);
        contentPane.add(winchingSpeedUnitsLabel);
        
        weakLinkStrengthUnitsLabel.setBounds(617, 36, 46, 14);
        contentPane.add(weakLinkStrengthUnitsLabel);
        
        tensionUnitsLabel.setBounds(617, 61, 46, 14);
        contentPane.add(tensionUnitsLabel);
        
        JLabel releaseAngelUnitsLabel = new JLabel("degrees");
        releaseAngelUnitsLabel.setBounds(617, 86, 60, 14);
        contentPane.add(releaseAngelUnitsLabel);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                cancelCommand();
            }
        });
    }
    
    public void deleteCommand(){
            int choice = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to delete " + currentGlider.getId() + "?",
                "Delete Glider", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (choice == 0){
                DatabaseEntryDelete.DeleteEntry(currentGlider);
                CurrentDataObjectSet objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
                objectSet.clearGlider();
                JOptionPane.showMessageDialog(rootPane, currentGlider.toString() + " successfully deleted.");
                parent.update();
                this.dispose();
            }
    }
    
    public void submitData(){
        if (isComplete()){
            String nNumber = nNumberField.getText();
            String name = "Planet Express";
            String owner = "Hubert Farnsworth";
            float emptyWeight = (Float.parseFloat(emptyWeightField.getText()) / UnitConversionRate.convertWeightUnitIndexToFactor(emptyWeightUnitsID));
            float grossWeight = Float.parseFloat(grossWeightField.getText()) / UnitConversionRate.convertWeightUnitIndexToFactor(maxGrossWeightUnitsID);
            float stallSpeed = Float.parseFloat(stallSpeedField.getText()) / UnitConversionRate.convertSpeedUnitIndexToFactor(stallSpeedUnitsID);
            float weakLink = Float.parseFloat(weakLinkField.getText()) / UnitConversionRate.convertTensionUnitIndexToFactor(weakLinkStrengthUnitsID);
            float tension = Float.parseFloat(tensionField.getText()) / UnitConversionRate.convertTensionUnitIndexToFactor(tensionUnitsID);
            float releaseAngle = Float.parseFloat(releaseAngleField.getText());
            float winchingSpeed = Float.parseFloat(winchingSpeedField.getText()) / UnitConversionRate.convertSpeedUnitIndexToFactor(winchingSpeedUnitsID);
            boolean carryBallast = ballastCheckBox.isSelected();
            boolean multipleSeats = multipleSeatsCheckBox.isSelected();
            Sailplane newGlider = new Sailplane(nNumber ,name, owner,"", grossWeight,
                    emptyWeight, stallSpeed, winchingSpeed, weakLink, tension,
                    releaseAngle, carryBallast, multipleSeats, "");   
            newGlider.setId(currentGlider.getId());

            try{
                CurrentDataObjectSet ObjectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
                ObjectSet.setCurrentGlider(newGlider);
                Object[] options = {"One-time Launch", "Save to Database"};
                int choice = JOptionPane.showOptionDialog(rootPane, "Do you want to use this Glider for a one-time launch or save it to the database?",
                    "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (choice == 0){
                    parent.update();
                    this.dispose();
                }
                else
                {
                    if (isEditEntry){
                        DatabaseEntryEdit.UpdateEntry(newGlider);
                    }
                    else{
                        Random randomId = new Random();
                        newGlider.setId(randomId.nextInt(100000000));
                        while (DatabaseEntryIdCheck.IdCheck(newGlider)){
                            newGlider.setId(randomId.nextInt(100000000));
                        }
                        DatabaseUtilities.DatabaseEntryInsert.addSailplaneToDB(newGlider);
                    }

                    parent.update();
                    this.dispose();
                } 
            }catch(SQLException e1) {
                e1.printStackTrace();
                logError(e1);
                if(e1.getErrorCode() == 30000)
                    JOptionPane.showMessageDialog(rootPane, "Sorry, but the glider " + newGlider.toString() + " already exists in the database", "Error", JOptionPane.INFORMATION_MESSAGE);
            }catch (ClassNotFoundException e2) {
                JOptionPane.showMessageDialog(rootPane, "Error: No access to database currently. Please try again later.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception e3){
                e3.printStackTrace();
            }
        }
    }
    
    public boolean isComplete(){
        try
        {
            boolean emptyFields = false;
            String nNumber = nNumberField.getText();
            String emptyWeight = emptyWeightField.getText();
            String grossWeight = grossWeightField.getText();
            String stallSpeed = stallSpeedField.getText();
            String weakLink = weakLinkField.getText();
            String tension = tensionField.getText();
            String releaseAngle = releaseAngleField.getText();
            String winchingSpeed = winchingSpeedField.getText();
            
            nNumberField.setBackground(Color.WHITE);
            emptyWeightField.setBackground(Color.WHITE);
            grossWeightField.setBackground(Color.WHITE);
            stallSpeedField.setBackground(Color.WHITE);
            weakLinkField.setBackground(Color.WHITE);
            tensionField.setBackground(Color.WHITE);
            releaseAngleField.setBackground(Color.WHITE);
            winchingSpeedField.setBackground(Color.WHITE);
            
            if(nNumber.isEmpty())
            {
                nNumberField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(emptyWeight.isEmpty())
            {
                emptyWeightField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(grossWeight.isEmpty())
            {
                grossWeightField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(stallSpeed.isEmpty())
            {
                stallSpeedField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(weakLink.isEmpty())
            {
                weakLinkField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(tension.isEmpty())
            {
                tensionField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(releaseAngle.isEmpty())
            {
                releaseAngleField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(winchingSpeed.isEmpty())
            {
                winchingSpeedField.setBackground(Color.PINK);
                emptyFields = true;
            }    
            if (emptyFields){
                throw new Exception("");
            }
            
            Float.parseFloat(emptyWeight);
            Float.parseFloat(grossWeight);
            Float.parseFloat(stallSpeed);
            Float.parseFloat(weakLink);
            Float.parseFloat(tension);
            Float.parseFloat(releaseAngle);
            Float.parseFloat(winchingSpeed);
            
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(rootPane, "Please input a numerical values", "Error", JOptionPane.INFORMATION_MESSAGE);
            //ew = new ErrWindow("Please input a numerical values");
            return false;
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Please complete all required fields\n" + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    
    public void clearData(){
        nNumberField.setText("");
        emptyWeightField.setText("");
        grossWeightField.setText("");
        stallSpeedField.setText("");
        winchingSpeedField.setText("");
        weakLinkField.setText("");
        tensionField.setText("");
        releaseAngleField.setText("");
        ballastCheckBox.setSelected(false);
        multipleSeatsCheckBox.setSelected(false);
        
        nNumberField.setBackground(Color.WHITE);
        emptyWeightField.setBackground(Color.WHITE);
        grossWeightField.setBackground(Color.WHITE);
        stallSpeedField.setBackground(Color.WHITE);
        winchingSpeedField.setBackground(Color.WHITE);
        weakLinkField.setBackground(Color.WHITE);
        tensionField.setBackground(Color.WHITE);
        releaseAngleField.setBackground(Color.WHITE);
    }
    
    public void cancelCommand(){
        this.dispose();
    }

}
