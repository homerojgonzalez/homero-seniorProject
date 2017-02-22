package Configuration;

import DataObjects.CurrentDataObjectSet;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import javax.swing.JComboBox;


public class ProfileAirfieldPanel extends JPanel{
    private javax.swing.JScrollPane airfieldScrollPane;
    private javax.swing.JScrollPane gliderPositionsScrollPane;
    private javax.swing.JScrollPane winchPositionsScrollPane;
    private javax.swing.JScrollPane runwaysScrollPane;
    private CurrentDataObjectSet currentData;       
    protected JComboBox airfieldAltitudeComboBox;
    protected JComboBox gliderPosAltitudeComboBox;
    protected JComboBox runwayMagneticHeadingComboBox;
    protected JComboBox winchPosAltitudeComboBox;
    
    /**
     * Creates new form sailplanePanel
     */
    public ProfileAirfieldPanel() {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        initComponents();
    }
    
    /**
     * Create the panel.
     */
    public void initComponents() {
        airfieldScrollPane = new javax.swing.JScrollPane();
        gliderPositionsScrollPane = new javax.swing.JScrollPane();
        winchPositionsScrollPane = new javax.swing.JScrollPane();
        runwaysScrollPane = new javax.swing.JScrollPane();
        airfieldAltitudeComboBox = new JComboBox();
        gliderPosAltitudeComboBox = new JComboBox();
        runwayMagneticHeadingComboBox = new JComboBox();
        winchPosAltitudeComboBox = new JComboBox();

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
        
        JScrollPane airfieldAttributesPanelScrollPane = new JScrollPane();
        airfieldSubPanel.add(airfieldAttributesPanelScrollPane, BorderLayout.CENTER);
        JPanel airfieldAttributesPanel = new JPanel();
        airfieldAttributesPanel.setBackground(Color.WHITE);
        airfieldAttributesPanel.setPreferredSize(new Dimension(300, 200));
        airfieldAttributesPanel.setLayout(null);
        airfieldAttributesPanelScrollPane.setViewportView(airfieldAttributesPanel);
        airfieldAttributesPanelScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        airfieldAttributesPanelScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        
        JLabel airfieldAltitudeLabel = new JLabel("Altitude:");
        airfieldAltitudeLabel.setBounds(10, 104, 84, 14);
        airfieldAttributesPanel.add(airfieldAltitudeLabel);
               
        JLabel airfieldLabel = new JLabel("Airfield");
        airfieldLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        airfieldLabel.setBounds(10, 20, 100, 22);
        airfieldAttributesPanel.add(airfieldLabel);
        
        airfieldAltitudeComboBox.setMaximumSize(new Dimension(32767, 20));
        airfieldAltitudeComboBox.setBounds(120, 101, 86, 20);
        airfieldAttributesPanel.add(airfieldAltitudeComboBox);
        airfieldAltitudeComboBox.addItem("ft");
        airfieldAltitudeComboBox.addItem("m");
        airfieldAltitudeComboBox.addItem("km");
        airfieldAltitudeComboBox.addItem("mi");
        airfieldAltitudeComboBox.setEnabled(false);

        JPanel gliderPostitionSubPanel = new JPanel();
        gliderPostitionSubPanel.setLayout(new BorderLayout(0, 0));
        gliderPostitionSubPanel.add(gliderPositionsScrollPane, BorderLayout.NORTH);
        
        JScrollPane gliderPosAttributesPanelScrollPane = new JScrollPane();
        JPanel gliderPositionAttributesPanel = new JPanel();
        gliderPositionAttributesPanel.setBackground(Color.WHITE);
        gliderPostitionSubPanel.add(gliderPosAttributesPanelScrollPane, BorderLayout.CENTER);
        gliderPositionAttributesPanel.setPreferredSize(new Dimension(300, 200));
        gliderPosAttributesPanelScrollPane.setViewportView(gliderPositionAttributesPanel);
        gliderPosAttributesPanelScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        gliderPosAttributesPanelScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        gliderPositionAttributesPanel.setLayout(null);
        
        JLabel gliderPosAltitudeLabel = new JLabel("Altitude:");
        gliderPosAltitudeLabel.setBounds(10, 78, 46, 14);
        gliderPositionAttributesPanel.add(gliderPosAltitudeLabel);
                       
        JLabel gliderPositionLabel = new JLabel("Glider Position");
        gliderPositionLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        gliderPositionLabel.setBounds(10, 20, 180, 31);
        gliderPositionAttributesPanel.add(gliderPositionLabel);
        
        gliderPosAltitudeComboBox.setMaximumSize(new Dimension(32767, 20));
        gliderPosAltitudeComboBox.setBounds(120, 75, 86, 20);
        gliderPositionAttributesPanel.add(gliderPosAltitudeComboBox);
        gliderPosAltitudeComboBox.addItem("ft");
        gliderPosAltitudeComboBox.addItem("m");
        gliderPosAltitudeComboBox.addItem("km");
        gliderPosAltitudeComboBox.addItem("mi");
        gliderPosAltitudeComboBox.setEnabled(false);
               
        JPanel runwaySubPanel = new JPanel();
        panel_1.add(runwaySubPanel);
        runwaySubPanel.setLayout(new BorderLayout(0, 0));
        runwaySubPanel.add(runwaysScrollPane, BorderLayout.NORTH);
        
        panel_1.add(gliderPostitionSubPanel);

        JPanel runwayAttributesPanel = new JPanel();
        runwayAttributesPanel.setBackground(Color.WHITE);
        JScrollPane runwayAttributesPanelScrollPane = new JScrollPane();
        runwaySubPanel.add(runwayAttributesPanelScrollPane, BorderLayout.CENTER);
        runwayAttributesPanel.setLayout(null);
        runwayAttributesPanel.setPreferredSize(new Dimension(300, 200));
        runwayAttributesPanelScrollPane.setViewportView(runwayAttributesPanel);
        runwayAttributesPanelScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        runwayAttributesPanelScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        
        JLabel runwayAltitudeLabel = new JLabel("Heading:");
        runwayAltitudeLabel.setBounds(10, 103, 100, 14);
        runwayAttributesPanel.add(runwayAltitudeLabel);
        
        JLabel runwayLabel = new JLabel("Runway");
        runwayLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        runwayLabel.setBounds(10, 20, 140, 31);
        runwayAttributesPanel.add(runwayLabel);
        
        runwayMagneticHeadingComboBox.setMaximumSize(new Dimension(32767, 20));
        runwayMagneticHeadingComboBox.setBounds(120, 100, 86, 20);
        runwayAttributesPanel.add(runwayMagneticHeadingComboBox);
        runwayMagneticHeadingComboBox.addItem("magnetic");
        runwayMagneticHeadingComboBox.addItem("true");
        runwayMagneticHeadingComboBox.setEnabled(false);
                
        JPanel winchPostitionSubPanel = new JPanel();
        panel.add(winchPostitionSubPanel);
        winchPostitionSubPanel.setLayout(new BorderLayout(0, 0));
        winchPostitionSubPanel.add(winchPositionsScrollPane, BorderLayout.NORTH);
                
        JPanel winchPositionAttributesPanel = new JPanel();
        winchPositionAttributesPanel.setBackground(Color.WHITE);
        JScrollPane winchPositionAttributesPanelScrollPane = new JScrollPane();
        winchPostitionSubPanel.add(winchPositionAttributesPanelScrollPane, BorderLayout.CENTER);
        winchPositionAttributesPanel.setLayout(null);
        winchPositionAttributesPanel.setPreferredSize(new Dimension(300, 200));
        winchPositionAttributesPanelScrollPane.setViewportView(winchPositionAttributesPanel);
        winchPositionAttributesPanelScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_ALWAYS);
        winchPositionAttributesPanelScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
                
        JLabel winchPosAltitudeLabel = new JLabel("Altitude:");
        winchPosAltitudeLabel.setBounds(10, 78, 46, 14);
        winchPositionAttributesPanel.add(winchPosAltitudeLabel);
                
        JLabel winchPositionLabel = new JLabel("Winch Position");
        winchPositionLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        winchPositionLabel.setBounds(10, 20, 180, 31);
        winchPositionAttributesPanel.add(winchPositionLabel);
        
        winchPosAltitudeComboBox.setMaximumSize(new Dimension(32767, 20));
        winchPosAltitudeComboBox.setBounds(120, 75, 86, 20);
        winchPositionAttributesPanel.add(winchPosAltitudeComboBox);
        winchPosAltitudeComboBox.addItem("ft");
        winchPosAltitudeComboBox.addItem("m");
        winchPosAltitudeComboBox.addItem("km");
        winchPosAltitudeComboBox.addItem("mi");
        winchPosAltitudeComboBox.setEnabled(false);
    }
}
