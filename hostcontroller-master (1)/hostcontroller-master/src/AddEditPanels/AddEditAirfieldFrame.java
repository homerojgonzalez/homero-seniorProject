//Successful
package AddEditPanels;

import Communications.Observer;
import Configuration.UnitConversionRate;
import Configuration.UnitLabelUtilities;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Airfield;
import DatabaseUtilities.DatabaseEntryDelete;
import DatabaseUtilities.DatabaseEntryEdit;
import DatabaseUtilities.DatabaseEntryIdCheck;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;


public class AddEditAirfieldFrame extends JFrame{
    private JPanel airfieldAttributesPanel;
    private JTextField airfieldAltitudeField;
    private JTextField designatorField;
    private JTextField airfieldNameField;
    private JTextField magneticVariationField;
    private JTextField airfieldLongitudeField;
    private JTextField airfieldLatitudeField;
    private Airfield currentAirfield;
    private CurrentDataObjectSet objectSet;
    private boolean isEditEntry;
    private Observer parent;
    private JLabel airfieldAltitudeUnitsLabel = new JLabel(); 
    private int airfieldAltitudeUnitsID;
    
    public void setupUnits()
    {
        airfieldAltitudeUnitsID = objectSet.getCurrentProfile().getUnitSetting("airfieldAltitude");
        String airfieldAltitudeUnitsString = UnitLabelUtilities.lenghtUnitIndexToString(airfieldAltitudeUnitsID);
        airfieldAltitudeUnitsLabel.setText(airfieldAltitudeUnitsString);
    }
    
    public void attach(Observer o)
    {
        parent = o;
    }

    /**
     * Create the frame.
     */
    public AddEditAirfieldFrame(Airfield editAirfield, boolean isEditEntry) {
        objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
        setupUnits();
        
        if (!isEditEntry || editAirfield == null){
            editAirfield = new Airfield("", "", 0, 0, 0, 0, "");
        }
        currentAirfield = editAirfield;
        this.isEditEntry = isEditEntry;
        setTitle("Airfield");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 650, 242);
        
        airfieldAttributesPanel = new JPanel();
        airfieldAttributesPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(airfieldAttributesPanel);
        airfieldAttributesPanel.setLayout(null);
        
        JLabel designatorLabel = new JLabel("Designator:");
        designatorLabel.setBounds(10, 36, 84, 14);
        airfieldAttributesPanel.add(designatorLabel);
        
        JLabel airfieldAltitudeLabel = new JLabel("Altitude:");
        airfieldAltitudeLabel.setBounds(10, 62, 84, 14);
        airfieldAttributesPanel.add(airfieldAltitudeLabel);
        
        JLabel magneticVariationLabel = new JLabel("Magnetic Variation:");
        magneticVariationLabel.setBounds(10, 87, 120, 14);
        airfieldAttributesPanel.add(magneticVariationLabel);
        
        JLabel airfieldLongitudeLabel = new JLabel("Longitude:");
        airfieldLongitudeLabel.setBounds(10, 112, 84, 14);
        airfieldAttributesPanel.add(airfieldLongitudeLabel);
        
        JLabel airfieldLatitudeLabel = new JLabel("Latitude:");
        airfieldLatitudeLabel.setBounds(10, 137, 84, 14);
        airfieldAttributesPanel.add(airfieldLatitudeLabel);
        
        airfieldAltitudeField = new JTextField();
        if (isEditEntry){
            airfieldAltitudeField.setText(String.valueOf(currentAirfield.getElevation() * UnitConversionRate.convertDistanceUnitIndexToFactor(airfieldAltitudeUnitsID)));
        }
        airfieldAltitudeField.setBounds(140, 58, 120, 20);
        airfieldAttributesPanel.add(airfieldAltitudeField);
        airfieldAltitudeField.setColumns(10);
        
        designatorField = new JTextField(currentAirfield.getDesignator());
        designatorField.setBounds(140, 33, 120, 20);
        airfieldAttributesPanel.add(designatorField);
        designatorField.setColumns(10);
        
        airfieldNameField = new JTextField(currentAirfield.getName());
        airfieldNameField.setBounds(140, 8, 120, 20);
        airfieldAttributesPanel.add(airfieldNameField);
        airfieldNameField.setColumns(10);
        
        magneticVariationField = new JTextField();
        if (isEditEntry){
            magneticVariationField.setText(String.valueOf(currentAirfield.getMagneticVariation()));
        }
        magneticVariationField.setBounds(140, 83, 120, 20);
        airfieldAttributesPanel.add(magneticVariationField);
        magneticVariationField.setColumns(10);
        
        JLabel airfieldNameLabel = new JLabel("Name:");
        airfieldNameLabel.setBounds(10, 11, 46, 14);
        airfieldAttributesPanel.add(airfieldNameLabel);
        
        airfieldLongitudeField = new JTextField();
        if (isEditEntry){
            airfieldLongitudeField.setText(String.valueOf(currentAirfield.getLongitude()));
        }
        airfieldLongitudeField.setBounds(140, 109, 120, 20);
        airfieldAttributesPanel.add(airfieldLongitudeField);
        airfieldLongitudeField.setColumns(10);
        
        airfieldLatitudeField = new JTextField();
        if (isEditEntry){
            airfieldLatitudeField.setText(String.valueOf(currentAirfield.getLatitude()));
        }
        airfieldLatitudeField.setBounds(140, 134, 120, 20);
        airfieldAttributesPanel.add(airfieldLatitudeField);
        airfieldLatitudeField.setColumns(10);
    
    JButton submitButton = new JButton("Submit");
        submitButton.setBounds(0, 180, 89, 23);
        submitButton.setBackground(new Color(200,200,200));
        airfieldAttributesPanel.add(submitButton);
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
        airfieldAttributesPanel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                deleteCommand();
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(180, 180, 89, 23);
        clearButton.setBackground(new Color(200,200,200));
        airfieldAttributesPanel.add(clearButton);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                clearData();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(270, 180, 89, 23);
        cancelButton.setBackground(new Color(200,200,200));
        airfieldAttributesPanel.add(cancelButton);
        
        JLabel latitudeUnitsLabel = new JLabel("degrees");
        latitudeUnitsLabel.setBounds(270, 137, 60, 14);
        airfieldAttributesPanel.add(latitudeUnitsLabel);
        
        JLabel longitudeUnitsLabel = new JLabel("degrees");
        longitudeUnitsLabel.setBounds(270, 112, 60, 14);
        airfieldAttributesPanel.add(longitudeUnitsLabel);
        
        JLabel magneticVariationUnitsLabel = new JLabel("degrees");
        magneticVariationUnitsLabel.setBounds(270, 87, 60, 14);
        airfieldAttributesPanel.add(magneticVariationUnitsLabel);
        
        airfieldAltitudeUnitsLabel.setBounds(270, 62, 46, 14);
        airfieldAttributesPanel.add(airfieldAltitudeUnitsLabel);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                cancelCommand();
            }
        });
        
    }
    
    public void deleteCommand(){

            int choice = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to delete " + currentAirfield.getName() + "?"
                    + "\n This will also delete all runways on this airfield and glider and winch positions associated with those runways.",
                    "Delete Airfield", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (choice == 0){
                DatabaseEntryDelete.DeleteEntry(currentAirfield);
                CurrentDataObjectSet objectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
                objectSet.clearAirfield();
                JOptionPane.showMessageDialog(rootPane, currentAirfield.toString() + " successfully deleted.");
                parent.update("1");
                this.dispose();
            }
    }
    
    public void submitData(){
        if (isComplete()){
            String airfieldName = airfieldNameField.getText();
            String designator = designatorField.getText();
            float airfieldAltitude = Float.parseFloat(airfieldAltitudeField.getText()) / UnitConversionRate.convertDistanceUnitIndexToFactor(airfieldAltitudeUnitsID);
            float magneticVariation = Float.parseFloat(magneticVariationField.getText());
            float airfieldLatitude = Float.parseFloat(airfieldLatitudeField.getText());
            float airfieldLongitude = Float.parseFloat(airfieldLongitudeField.getText());
            
            Airfield newAirfield = new Airfield(airfieldName, designator, airfieldAltitude,
                    magneticVariation, airfieldLatitude, airfieldLongitude, "");
            newAirfield.setId(currentAirfield.getId());
            
            try{
                CurrentDataObjectSet ObjectSet = CurrentDataObjectSet.getCurrentDataObjectSet();
                ObjectSet.setCurrentAirfield(newAirfield);
                Object[] options = {"One-time Launch", "Save to Database"};
                int choice = JOptionPane.showOptionDialog(rootPane, "Do you want to use this Airfield for a one-time launch or save it to the database?",
                    "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                System.out.println(choice);
                if (choice == 0){
                    parent.update("1");
                    this.dispose();
                }
                else
                {
                    if (isEditEntry){
                        DatabaseEntryEdit.UpdateEntry(newAirfield);
                    }
                    else{
                        Random randomId = new Random();
                        newAirfield.setId(randomId.nextInt(100000000));
                        while (DatabaseEntryIdCheck.IdCheck(newAirfield)){
                            newAirfield.setId(randomId.nextInt(100000000));
                        }
                        DatabaseUtilities.DatabaseEntryInsert.addAirfieldToDB(newAirfield);
                    }

                    parent.update("1");
                    this.dispose();
                } 
            }catch(SQLException e1) {
                if(e1.getErrorCode() == 30000)
                    System.out.println(e1.getMessage());
                    JOptionPane.showMessageDialog(rootPane, "Sorry, but the airfield " + newAirfield.toString() + " already exists in the database", "Error", JOptionPane.INFORMATION_MESSAGE);
            }catch (ClassNotFoundException e2) {
                JOptionPane.showMessageDialog(rootPane, "Error: No access to database currently. Please try again later.", "Error", JOptionPane.INFORMATION_MESSAGE);
            }catch (Exception e3){
                System.out.println(e3.getMessage());
            }
        }
    }
    
    public boolean isComplete(){
        try
        {
            boolean emptyFields = false;
            String airfieldName = airfieldNameField.getText();
            String designator = designatorField.getText();
            String airfieldAltitude = airfieldAltitudeField.getText();
            String magneticVariation = magneticVariationField.getText();
            String airfieldLatitude = airfieldLatitudeField.getText();
            String airfieldLongitude = airfieldLongitudeField.getText();
            
            airfieldNameField.setBackground(Color.WHITE);
            designatorField.setBackground(Color.WHITE);
            airfieldAltitudeField.setBackground(Color.WHITE);
            magneticVariationField.setBackground(Color.WHITE);
            airfieldLatitudeField.setBackground(Color.WHITE);
            airfieldLongitudeField.setBackground(Color.WHITE);
            
            if(airfieldName.isEmpty())
            {
                airfieldNameField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(designator.isEmpty())
            {
                designatorField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(airfieldAltitude.isEmpty())
            {
                airfieldAltitudeField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(magneticVariation.isEmpty())
            {
                magneticVariationField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(airfieldLatitude.isEmpty())
            {
                airfieldLatitudeField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if(airfieldLongitude.isEmpty())
            {
                airfieldLongitudeField.setBackground(Color.PINK);
                emptyFields = true;
            }
            if (emptyFields){
                throw new Exception("");
            }
            Float.parseFloat(airfieldAltitude);
            Float.parseFloat(magneticVariation);
            Float.parseFloat(airfieldLatitude);
            Float.parseFloat(airfieldLongitude);
            
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(rootPane, "Please input correct numerical values", "Error", JOptionPane.INFORMATION_MESSAGE);
            //ew = new ErrWindow("Please input correct numerical values");
            return false;
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, "Please complete all required fields\n" + e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            //ew = new ErrWindow("Please complete all required fields\n" + e.getMessage());
            return false;
        }
        return true;
    }
    
    public void clearData(){
        airfieldNameField.setText("");
        designatorField.setText("");
        airfieldAltitudeField.setText("");
        magneticVariationField.setText("");
        airfieldLatitudeField.setText("");
        airfieldLongitudeField.setText("");
        
        airfieldNameField.setBackground(Color.WHITE);
        designatorField.setBackground(Color.WHITE);
        airfieldAltitudeField.setBackground(Color.WHITE);
        magneticVariationField.setBackground(Color.WHITE);
        airfieldLatitudeField.setBackground(Color.WHITE);
        airfieldLongitudeField.setBackground(Color.WHITE);
    }
    
    public void cancelCommand(){
        this.dispose();
    }
    
    
}
