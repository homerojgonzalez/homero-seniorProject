package Configuration;

import DataObjects.CurrentDataObjectSet;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;


public class ProfileOtherPanel extends JPanel{
    private CurrentDataObjectSet currentData;
    protected JComboBox launchWeightComboBox;
    protected JComboBox runLengthComboBox;
    protected JComboBox runDirectionComboBox;
    protected JComboBox crosswindComboBox;
    protected JComboBox headwindComboBox;
    protected JComboBox densityAltitudeComboBox;
    protected JComboBox gustWindSpeedComboBox;
    protected JComboBox pressureComboBox;
    protected JComboBox temperatureComboBox;
    protected JComboBox avgWindSpeedComboBox;
    protected JComboBox windDirectionComboBox;
      
    /**
     * Creates new form sailplanePanel
     */
    public ProfileOtherPanel() {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        initComponents();
    }
    
    /**
     * Create the panel.
     */
    public void initComponents() {
        launchWeightComboBox = new JComboBox();
        runLengthComboBox = new JComboBox();
        runDirectionComboBox = new JComboBox();
        crosswindComboBox = new JComboBox();
        headwindComboBox = new JComboBox();
        densityAltitudeComboBox = new JComboBox();
        gustWindSpeedComboBox = new JComboBox();
        pressureComboBox = new JComboBox();
        temperatureComboBox = new JComboBox();
        avgWindSpeedComboBox = new JComboBox();
        windDirectionComboBox = new JComboBox();
        
        setLayout(new BorderLayout(0, 0));

        JPanel unitsPanel = new JPanel();
        unitsPanel.setBackground(Color.WHITE);
        JScrollPane unitsPanelScrollPane = new JScrollPane();
        add(unitsPanelScrollPane);
        unitsPanel.setPreferredSize(new Dimension(600,400));
        unitsPanelScrollPane.setViewportView(unitsPanel);
        unitsPanelScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        unitsPanelScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        
        JLabel launchWeightLabel = new JLabel("Launch Weight:");
        launchWeightLabel.setBounds(9, 37, 90, 14);
        launchWeightComboBox.setBounds(150, 34, 86, 20);
        launchWeightComboBox.setMaximumSize(new Dimension(32767, 20));
        launchWeightComboBox.addItem("lbs");
        launchWeightComboBox.addItem("kg");
        launchWeightComboBox.setEnabled(false);
        
        JLabel runLengthLabel = new JLabel("Run Length:");
        runLengthLabel.setBounds(9, 63, 117, 14);

        runLengthComboBox.setBounds(150, 60, 86, 20);
        runLengthComboBox.setMaximumSize(new Dimension(32767, 20));
        runLengthComboBox.addItem("ft");
        runLengthComboBox.addItem("m");
        runLengthComboBox.addItem("km");
        runLengthComboBox.addItem("mi");
        runLengthComboBox.setEnabled(false);
        
        JLabel indicatedStallSpeedLabel = new JLabel("Run Direction:");
        indicatedStallSpeedLabel.setBounds(9, 89, 140, 14);

        runDirectionComboBox.setBounds(150, 86, 86, 20);
        runDirectionComboBox.setMaximumSize(new Dimension(32767, 20));
        runDirectionComboBox.addItem("magnetic");
        runDirectionComboBox.addItem("true");
        runDirectionComboBox.setEnabled(false);

        JLabel crosswindLabel = new JLabel("Crosswind Component:");
        crosswindLabel.setBounds(280, 63, 150, 14);

        JLabel headwindLabel = new JLabel("Headwind Component:");
        headwindLabel.setBounds(280, 37, 150, 14);

        crosswindComboBox.setBounds(440, 60, 86, 20);
        crosswindComboBox.setMaximumSize(new Dimension(32767, 20));
        crosswindComboBox.addItem("kts");
        crosswindComboBox.addItem("mph");
        crosswindComboBox.addItem("kph");
        crosswindComboBox.addItem("m/s");
        crosswindComboBox.addItem("kn");
        crosswindComboBox.setEnabled(false);

        headwindComboBox.setBounds(440, 34, 86, 20);
        headwindComboBox.setMaximumSize(new Dimension(32767, 20));
        headwindComboBox.addItem("kts");
        headwindComboBox.addItem("mph");
        headwindComboBox.addItem("kph");
        headwindComboBox.addItem("m/s");
        headwindComboBox.addItem("kn");
        headwindComboBox.setEnabled(false);

        JLabel densityAltitudeLabel = new JLabel("Density Altitude:");
        densityAltitudeLabel.setBounds(280, 89, 150, 14);

        densityAltitudeComboBox.setBounds(440, 86, 86, 20);
        densityAltitudeComboBox.setMaximumSize(new Dimension(32767, 20));
        densityAltitudeComboBox.addItem("ft");
        densityAltitudeComboBox.addItem("m");
        densityAltitudeComboBox.addItem("km");
        densityAltitudeComboBox.addItem("mi");
        densityAltitudeComboBox.setEnabled(false);
        
        unitsPanel.setLayout(null);
        unitsPanel.add(launchWeightLabel);
        unitsPanel.add(runLengthLabel);
        unitsPanel.add(indicatedStallSpeedLabel);
        unitsPanel.add(runDirectionComboBox);
        unitsPanel.add(launchWeightComboBox);
        unitsPanel.add(runLengthComboBox);
        unitsPanel.add(crosswindLabel);
        unitsPanel.add(headwindLabel);
        unitsPanel.add(densityAltitudeLabel);
        unitsPanel.add(densityAltitudeComboBox);
        unitsPanel.add(headwindComboBox);
        unitsPanel.add(crosswindComboBox);
        
        JLabel environmentalAndDerivedLabel = new JLabel("Environmental and Derived");
        environmentalAndDerivedLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        environmentalAndDerivedLabel.setBounds(9, 0, 318, 31);
        unitsPanel.add(environmentalAndDerivedLabel);
        
        JLabel avgWingSpeedLabel = new JLabel("Avg. Wind Speed:");
        avgWingSpeedLabel.setBounds(9, 143, 140, 14);
        unitsPanel.add(avgWingSpeedLabel);
        
        JLabel windDirectionLabel = new JLabel("Wind Direction:");
        windDirectionLabel.setBounds(9, 117, 117, 14);
        unitsPanel.add(windDirectionLabel);
        
        windDirectionComboBox.setMaximumSize(new Dimension(32767, 20));
        windDirectionComboBox.setBounds(150, 114, 86, 20);
        windDirectionComboBox.addItem("magnetic");
        windDirectionComboBox.addItem("true");
        windDirectionComboBox.addItem("relative");
        windDirectionComboBox.setEnabled(false);
        unitsPanel.add(windDirectionComboBox);
        
        avgWindSpeedComboBox.setMaximumSize(new Dimension(32767, 20));
        avgWindSpeedComboBox.setBounds(150, 140, 86, 20);
        avgWindSpeedComboBox.addItem("kts");
        avgWindSpeedComboBox.addItem("mph");
        avgWindSpeedComboBox.addItem("kph");
        avgWindSpeedComboBox.addItem("m/s");
        avgWindSpeedComboBox.addItem("kn");
        avgWindSpeedComboBox.setEnabled(false);
        unitsPanel.add(avgWindSpeedComboBox);
        
        JLabel pressureLabel = new JLabel("Pressure:");
        pressureLabel.setBounds(280, 146, 140, 14);
        unitsPanel.add(pressureLabel);
        
        JLabel temperatureLabel = new JLabel("Temperature:");
        temperatureLabel.setBounds(280, 117, 86, 14);
        unitsPanel.add(temperatureLabel);
        
        temperatureComboBox.setMaximumSize(new Dimension(32767, 20));
        temperatureComboBox.setBounds(440, 114, 86, 20);
        temperatureComboBox.addItem("F");
        temperatureComboBox.addItem("C");
        temperatureComboBox.setEnabled(false);
        unitsPanel.add(temperatureComboBox);
        
        pressureComboBox.setMaximumSize(new Dimension(32767, 20));
        pressureComboBox.setBounds(440, 143, 86, 20);
        pressureComboBox.addItem("millibar");
        pressureComboBox.addItem("psi");
        pressureComboBox.addItem("hPa");
        pressureComboBox.addItem("kPa");
        pressureComboBox.addItem("bar");
        pressureComboBox.addItem("atm");
        pressureComboBox.setEnabled(false);
        unitsPanel.add(pressureComboBox);
        
        JLabel gustWindSpeedLabel = new JLabel("Gust Wind Speed:");
        gustWindSpeedLabel.setBounds(9, 171, 140, 14);
        unitsPanel.add(gustWindSpeedLabel);
        
        gustWindSpeedComboBox.setMaximumSize(new Dimension(32767, 20));
        gustWindSpeedComboBox.setBounds(150, 168, 86, 20);
        gustWindSpeedComboBox.addItem("kts");
        gustWindSpeedComboBox.addItem("mph");
        gustWindSpeedComboBox.addItem("kph");
        gustWindSpeedComboBox.addItem("m/s");
        gustWindSpeedComboBox.addItem("kn");
        gustWindSpeedComboBox.setEnabled(false);
        unitsPanel.add(gustWindSpeedComboBox);
        add(unitsPanelScrollPane);
        
    }   
}

