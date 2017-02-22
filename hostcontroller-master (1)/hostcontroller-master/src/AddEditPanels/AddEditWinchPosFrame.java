//Should be successful if entries in DB are set in the CurrentDataObjectSet
package AddEditPanels;

import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.WinchPosition;
import DatabaseUtilities.DatabaseEntryIdCheck;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;


public class AddEditWinchPosFrame extends JFrame {
    private JPanel contentPane;
    private JTextField latitudeField;
    private JTextField longitudeField;
    private JTextField altitudeField;
    private JTextField nameField;
    private CurrentDataObjectSet objectSet;
    private WinchPosition currentWinchPos;
    private boolean isEditEntry;
    private Observer parent;
    private JLabel winchPosAltitudeUnitsLabel = new JLabel(); 
    private int winchPosAltitudeUnitsID;
    
    public void setupUnits()
    {
        winchPosAltitudeUnitsID = objectSet.getCurrentProfile().getUnitSetting("winchPosAltitude");
        String winchPosAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(winchPosAltitudeUnitsID);
        winchPosAltitudeUnitsLabel.setText(winchPosAltitudeUnitsString);
    }
    
    public void attach(Observer o)
    {
        parent = o;
    }
    
    /**
     * Create the frame.
     */
    public AddEditWinchPosFrame(WinchPosition editWinchPos, boolean isEditEntry) {
        objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
        setupUnits();


        if (!isEditEntry || editWinchPos == null){
            editWinchPos = new WinchPosition("", 0, 0, 0, "");
        }
        this.isEditEntry = isEditEntry;
        currentWinchPos = editWinchPos;

        setTitle("Winch Position");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        contentPane.add(panel, BorderLayout.CENTER);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 14, 46, 14);
        panel.add(nameLabel);

        JLabel altitudeLabel = new JLabel("Altitude:");
        altitudeLabel.setBounds(10, 39, 46, 14);
        panel.add(altitudeLabel);

        JLabel longitudeLabel = new JLabel("Longitude:");
        longitudeLabel.setBounds(10, 64, 80, 14);
        panel.add(longitudeLabel);

        JLabel latitudeLabel = new JLabel("Latitude:");
        latitudeLabel.setBounds(10, 89, 80, 14);
        panel.add(latitudeLabel);

        latitudeField = new JTextField();
        if (isEditEntry){
            latitudeField.setText(String.valueOf(currentWinchPos.getLatitude()));
        }
        latitudeField.setColumns(10);
        latitudeField.setBounds(135, 86, 200, 20);
        panel.add(latitudeField);

        longitudeField = new JTextField();
        if (isEditEntry){
            longitudeField.setText(String.valueOf(currentWinchPos.getLongitude()));
        }
        longitudeField.setColumns(10);
        longitudeField.setBounds(135, 61, 200, 20);
        panel.add(longitudeField);

        altitudeField = new JTextField();
        if (isEditEntry){
            altitudeField.setText(String.valueOf((currentWinchPos.getElevation()
                    * UnitConversionRate.convertDistanceUnitIndexToFactor(winchPosAltitudeUnitsID))));
        }
        altitudeField.setColumns(10);
        altitudeField.setBounds(135, 36, 200, 20);
        panel.add(altitudeField);

        nameField = new JTextField(currentWinchPos.getName());

        nameField.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        nameField.setColumns(10);
        nameField.setBounds(135, 11, 200, 20);
        panel.add(nameField);
        
        JLabel ParentAirfieldLabel = new JLabel("Parent Airfield: ");
        ParentAirfieldLabel.setBounds(10, 126, 220, 14);
        panel.add(ParentAirfieldLabel);
        
        try{
        JLabel ParentAirfieldNameLabel = new JLabel(objectSet.getCurrentAirfield().getName());
        ParentAirfieldNameLabel.setBounds(135, 126, 220, 14);
        panel.add(ParentAirfieldNameLabel);
        }catch(Exception e){
            
        }

        JLabel parentRunwayLabel = new JLabel("Parent Runway: ");
        parentRunwayLabel.setBounds(10, 151, 220, 14);
        panel.add(parentRunwayLabel);
        try{
        JLabel parentRunwayNameLabel = new JLabel(objectSet.getCurrentRunway().getName());
        parentRunwayNameLabel.setBounds(135, 151, 220, 14);
        panel.add(parentRunwayNameLabel);
        }catch(Exception e){
            
        }
        

        JLabel requiredNoteLabel = new JLabel("All fields are required");
        requiredNoteLabel.setBounds(10, 210, 200, 14);
        panel.add(requiredNoteLabel);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(0, 228, 89, 23);
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
        deleteButton.setBounds(90, 228, 89, 23);
        deleteButton.setBackground(new Color(200,200,200));
        panel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                deleteCommand();
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(180, 228, 89, 23);
        clearButton.setBackground(new Color(200,200,200));
        panel.add(clearButton);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                clearData();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(270, 228, 89, 23);
        cancelButton.setBackground(new Color(200,200,200));
        panel.add(cancelButton);
        
        JLabel latitudeUnitsLabel = new JLabel("degrees");
        latitudeUnitsLabel.setBounds(345, 89, 60, 14);
        panel.add(latitudeUnitsLabel);
        
        JLabel longitudeUnitsLabel = new JLabel("degrees");
        longitudeUnitsLabel.setBounds(345, 64, 60, 14);
        panel.add(longitudeUnitsLabel);
        
        winchPosAltitudeUnitsLabel.setBounds(345, 39, 46, 14);
        panel.add(winchPosAltitudeUnitsLabel);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });
    }
	
    public void deleteCommand(){
        int choice = JOptionPane.showConfirmDialog(rootPane, 
            "Are you sure you want to delete " + currentWinchPos.getName() + "?",
            "Delete Winch Position", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (choice == 0){
            DatabaseUtilities.DatabaseEntryDelete.DeleteEntry(currentWinchPos);
            objectSet.cleafGliderPosition();
            JOptionPane.showMessageDialog(rootPane, currentWinchPos.toString() + " successfully deleted.");
            parent.update("4");
            this.dispose();
        }
    }

    public void clearData(){
        nameField.setText("");
        altitudeField.setText("");
        longitudeField.setText("");
        latitudeField.setText("");
        nameField.setBackground(Color.WHITE);
        altitudeField.setBackground(Color.WHITE);
        longitudeField.setBackground(Color.WHITE);
        latitudeField.setBackground(Color.WHITE);
    }
    
    protected void submitData(){
        if (isComplete()){
            String winchPosId = nameField.getText();
            float altitude = Float.parseFloat(altitudeField.getText()) 
                    / UnitConversionRate.convertDistanceUnitIndexToFactor(winchPosAltitudeUnitsID);
            float longitude = Float.parseFloat(longitudeField.getText());
            float latitude = Float.parseFloat(latitudeField.getText());
            
            int runwayParentId = 0;
            
            try{
                runwayParentId = objectSet.getCurrentRunway().getId();
            }catch (Exception e){
                
            }
            
            WinchPosition newWinchPos = new WinchPosition(winchPosId, altitude,
                    latitude, longitude, "");
            newWinchPos.setId(currentWinchPos.getId());
            newWinchPos.setRunwayParentId(runwayParentId);
            try{
                objectSet.setCurrentWinchPosition(newWinchPos);
                Object[] options = {"One-time Launch", "Save to Database"};
                int choice = JOptionPane.showOptionDialog(rootPane, 
                        "Do you want to use this Winch Position for a one-time launch or save it to the database?",
                    "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);     
                if (choice == 0){
                    parent.update("4");
                    this.dispose();
                }
                else
                {
                    if (isEditEntry){
                        DatabaseUtilities.DatabaseEntryEdit.UpdateEntry(newWinchPos);
                    }
                    else{
                        Random randomId = new Random();
                        newWinchPos.setId(randomId.nextInt(100000000));
                        while (DatabaseEntryIdCheck.IdCheck(newWinchPos)){
                            newWinchPos.setId(randomId.nextInt(100000000));
                        }
                        DatabaseUtilities.DatabaseEntryInsert.addWinchPositionToDB(newWinchPos);
                    }
                    parent.update("4");
                    this.dispose();
                }
            }catch(SQLException e1) {
                if(e1.getErrorCode() == 30000){

                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(rootPane, "Sorry, but the Winch Position " 
                            + newWinchPos.toString() + " already exists in the database");
                }
            }catch (ClassNotFoundException e2) {
                JOptionPane.showMessageDialog(rootPane, 
                        "Error: No access to database currently. Please try again later.", 
                        "Error", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e3) {

            }
        }
    }

    public boolean isComplete()
    {
        try
        {
            boolean emptyFields = false;
            String name = nameField.getText();
            String altitude = altitudeField.getText();
            String longitude = longitudeField.getText();
            String latitude = latitudeField.getText();
            nameField.setBackground(Color.WHITE);
            altitudeField.setBackground(Color.WHITE);
            longitudeField.setBackground(Color.WHITE);
            latitudeField.setBackground(Color.WHITE);

            if(name.isEmpty())
            {
                nameField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(altitude.isEmpty())
            {
                altitudeField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(longitude.isEmpty())
            {
                longitudeField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(latitude.isEmpty())
            {
                latitudeField.setBackground(Color.PINK);
                emptyFields = true;
            }

            if (emptyFields){
                throw new Exception("");
            }
            Float.parseFloat(altitude);
            Float.parseFloat(longitude);
            Float.parseFloat(latitude);
            
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(rootPane, "Please input correct numerical values", 
                    "Error", JOptionPane.INFORMATION_MESSAGE);
            //ew = new ErrWindow("Please input correct numerical values");
            return false;
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Please complete all required fields\n" 
                    + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            //ew = new ErrWindow("Please complete all required fields\n" + e.getMessage());
            return false;
        }
        return true;
    }
}
