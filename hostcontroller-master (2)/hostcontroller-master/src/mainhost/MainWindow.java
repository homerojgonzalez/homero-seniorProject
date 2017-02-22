package mainhost;

import Configuration.ProfileManagementFrame;
import Configuration.DatabaseExportFrame;
import Configuration.DatabaseImportFrame;
import ParameterSelection.ParameterSelectionPanel;
import ParameterSelection.RecentLaunchesPanel;
import DashboardInterface.FlightDashboard;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Operator;
import javax.swing.*;
import java.awt.Dimension;   
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.BoxLayout;
import ParameterSelection.CurrentScenario;
import ParameterSelection.EnvironmentalWindow;
import ParameterSelection.DEBUGWinchEditPanel;
import java.io.File;
import Communications.MessagePipeline;
import DataObjects.LaunchParameterList;
import DataObjects.LaunchParameterGenerator;
import static DatabaseUtilities.DatabaseInitialization.HOSTCONTROLLER_VERSION;

public class MainWindow extends JFrame {
    private JMenuBar topMenu;
    private JPanel mainWindow;
    private String currentProfile;
    private JPanel leftSidePanelScenario;
    private JPanel leftSidePanelDashboard;
    private JPanel leftSidePanelWinch;
    private JLabel statusLabel;
    private JPanel rightSidePanel;
    private JPanel lowerLeftSidePanelScenario;    
    private JPanel lowerLeftSidePanelDashboard;
    private JPanel lowerLeftSidePanelWinch;
    private JPanel upperLeftSidePanelScenario;    
    private JPanel upperLeftSidePanelDashboard;
    private JPanel upperLeftSidePanelWinch;
    private JTabbedPane tabbedPane;
    private ParameterSelectionPanel ParameterSelectionPanel_;
    private ProfileManagementFrame ProfileManagementFrame;
    private FlightDashboard FlightDashboard_;
    private DatabaseExportFrame DatabaseExportFrame;
    private DatabaseImportFrame DatabaseImportFrame;
    private EnvironmentalWindow EnvironmentalWindow_;
    private CurrentScenario CurrentScenario_;
    private CurrentDataObjectSet currentData;
    private CardLayout selectionLayout;
    private DEBUGWinchEditPanel winchPanel;

    public MainWindow() {
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();
        initializeDefaultProfile();
        topMenu = new JMenuBar();
        mainWindow = new JPanel(new BorderLayout());
        leftSidePanelScenario = new JPanel();
        leftSidePanelDashboard = new JPanel();
        leftSidePanelWinch = new JPanel();
        //rightSidePanel = new JPanel();
        statusLabel = new JLabel();
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        currentProfile = "NO PROFILE";
        winchPanel = new DEBUGWinchEditPanel(ParameterSelectionPanel_);
        ParameterSelectionPanel_ = new ParameterSelectionPanel();
        FlightDashboard_ = new FlightDashboard();
        EnvironmentalWindow_ = new EnvironmentalWindow();
        rightSidePanel = EnvironmentalWindow_;
        selectionLayout = (CardLayout)ParameterSelectionPanel_.getLayout();
        upperLeftSidePanelScenario = new CurrentScenario(selectionLayout, ParameterSelectionPanel_);
        upperLeftSidePanelDashboard = new CurrentScenario(selectionLayout, ParameterSelectionPanel_);
        upperLeftSidePanelWinch = new CurrentScenario(selectionLayout, ParameterSelectionPanel_);
        lowerLeftSidePanelScenario = new RecentLaunchesPanel(ParameterSelectionPanel_);
        lowerLeftSidePanelDashboard = new RecentLaunchesPanel(ParameterSelectionPanel_);
        lowerLeftSidePanelWinch = new RecentLaunchesPanel(ParameterSelectionPanel_);
        createAndShowGUI();
            }

    private void initializeDefaultProfile()
    {
        Operator defaultProfile = new Operator(0, "Default", "{}"); 
        defaultProfile.setUnitSetting("flightWeight", 1);
        
        defaultProfile.setUnitSetting("emptyWeight", 1);
        defaultProfile.setUnitSetting("maxGrossWeight", 1);
        defaultProfile.setUnitSetting("stallSpeed", 1);
        defaultProfile.setUnitSetting("ballastWeight", 1);
        defaultProfile.setUnitSetting("baggageWeight", 1);
        defaultProfile.setUnitSetting("passengerWeight", 1);
        defaultProfile.setUnitSetting("maxTension", 1);
        defaultProfile.setUnitSetting("weakLinkStrength", 1);
        defaultProfile.setUnitSetting("winchingSpeed", 1);
        
        defaultProfile.setUnitSetting("airfieldAltitude", 1);
        defaultProfile.setUnitSetting("gliderPosAltitude", 1);
        defaultProfile.setUnitSetting("runwayMagneticHeading", 1);
        defaultProfile.setUnitSetting("winchPosAltitude", 1);
        
        defaultProfile.setUnitSetting("cableLength", 1);
        defaultProfile.setUnitSetting("coreDiameter", 6);
        
        defaultProfile.setUnitSetting("avgWindSpeed", 1);
        defaultProfile.setUnitSetting("crosswind", 1);
        defaultProfile.setUnitSetting("gustWindSpeed", 1);
        defaultProfile.setUnitSetting("headwind", 1);
        defaultProfile.setUnitSetting("launchWeight", 1);
        defaultProfile.setUnitSetting("densityAltitude", 1);
        defaultProfile.setUnitSetting("runLength", 1);
        defaultProfile.setUnitSetting("pressure", 4);
        defaultProfile.setUnitSetting("temperature", 1);
        defaultProfile.setUnitSetting("runDirection", 1);
        defaultProfile.setUnitSetting("windDirection", 1);
                
        currentData.setCurrentProfile(defaultProfile);
    }
    
    private void createAndShowGUI() {
        //setupMainWindow();
        //setepMenu();
        //setupLeftSideBar()
        //setupTabbedPane();
        //setupRightSideBar();
    	setTitle("Winch Host Manager v" + HOSTCONTROLLER_VERSION);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setUpMenus();
        setJMenuBar(topMenu);
        
        leftSidePanelScenario.setPreferredSize(new Dimension(200, 600));
        leftSidePanelDashboard.setPreferredSize(new Dimension(200, 600));
        leftSidePanelWinch.setPreferredSize(new Dimension(200, 600));
        leftSidePanelScenario.setBorder(BorderFactory.createLineBorder(Color.black));
        leftSidePanelDashboard.setBorder(BorderFactory.createLineBorder(Color.black));
        leftSidePanelWinch.setBorder(BorderFactory.createLineBorder(Color.black));
        leftSidePanelScenario.setBackground(Color.WHITE);
        leftSidePanelDashboard.setBackground(Color.WHITE);
        leftSidePanelWinch.setBackground(Color.WHITE);

        rightSidePanel.setPreferredSize(new Dimension(200, 600));
        rightSidePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        rightSidePanel.setBackground(Color.WHITE);
        
        leftSidePanelScenario.add(upperLeftSidePanelScenario);
        leftSidePanelScenario.add(lowerLeftSidePanelScenario);
        
        leftSidePanelDashboard.add(upperLeftSidePanelDashboard);
        leftSidePanelDashboard.add(lowerLeftSidePanelDashboard);
        
        leftSidePanelWinch.add(upperLeftSidePanelWinch);
        leftSidePanelWinch.add(lowerLeftSidePanelWinch);
                
        
        //upperLeftSidePanelDashboard.add(new JLabel("REPLAY LIST HERE"));
        //lowerLeftSidePanelDashboard.add(new JLabel("Current Scenario"));
       
        
        tabbedPane.setPreferredSize(new Dimension(800, 620));
        tabbedPane.addTab("Edit Scenario", makePanel(ParameterSelectionPanel_, 1));
        tabbedPane.addTab("Flight Dashboard", makePanel(FlightDashboard_, 2));
        tabbedPane.addTab("Edit Winch", makePanel(winchPanel, 3));
        mainWindow.add(tabbedPane, BorderLayout.CENTER);

        mainWindow.add(rightSidePanel, BorderLayout.LINE_END);

        mainWindow.add(statusLabel, BorderLayout.PAGE_END);
        statusLabel.setText(" ");

        getContentPane().add(mainWindow);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);  
        setVisible(true);       
    }

    private JPanel makePanel(JPanel innerPanel, int tab) {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout(1, 1));
        if(tab==1) p.add(leftSidePanelScenario, BorderLayout.LINE_START);
        else if (tab==2) p.add(leftSidePanelDashboard, BorderLayout.LINE_START);
        else p.add(leftSidePanelWinch, BorderLayout.LINE_START);
        p.add(innerPanel, BorderLayout.CENTER);
        return p;
    }

    private void setUpMenus() {
        final JMenu fileMenu = new JMenu("File");
        final JMenu editMenu = new JMenu("Edit");
        final JMenu editAddMenu = new JMenu("Add New...");
//FILE MENU
        JMenuItem setupDBItem = new JMenuItem("Setup Database");
        setupDBItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int choice = JOptionPane.showConfirmDialog (null, "This will clear all databases. Are you sure you want to proceed?", "Warning",JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION){
                    DatabaseUtilities.DatabaseInitialization.rebuildDatabase(ParameterSelectionPanel_);
                }
                else{}
            }
        });
	fileMenu.add(setupDBItem);
        /*
        JMenuItem clearBlackboxItem = new JMenuItem("Clear Blackbox");
        clearBlackboxItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                int choice = JOptionPane.showConfirmDialog (null, "This will clear the Blackbox Database. Are you sure you want to proceed?", "Warning",JOptionPane.YES_NO_OPTION);
                if(choice == JOptionPane.YES_OPTION)
                {
                    try {
                        DatabaseEntryInsert.clearBlackbox();
                    } catch (SQLException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        fileMenu.add(clearBlackboxItem);
        */
       
        JMenuItem exportDBItem = new JMenuItem("Export From Database");
        exportDBItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                DatabaseExportFrame = new DatabaseExportFrame();
                DatabaseExportFrame.setVisible(true);
            }
        });
	fileMenu.add(exportDBItem);
        

        //WRITTEN BY HOMERO TESTING
        JMenuItem templateGen = new JMenuItem("Generate template");
        templateGen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
               LaunchParameterGenerator lpGen = new LaunchParameterGenerator(); 
               float[] parameters = lpGen.GenerateParameters();
            
            
            
            
            
            }
        });
	fileMenu.add(templateGen);
        
        
        JMenuItem importDBItem = new JMenuItem("Import From File");
        importDBItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Import");
                chooser.setApproveButtonText("Select");
                String filePath = "";
                String fileName = "";
                String zipLocation = "";
                File file = null;
                int option = chooser.showOpenDialog(topMenu);
                if(option == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                    File chosen = chooser.getCurrentDirectory();
                    filePath = chosen.getPath();
                    fileName = file.getName();
                    zipLocation = filePath + "/" + fileName;
                    if(!fileName.contains(".zip")) {
                        zipLocation += ".zip";
                    }                    
                }
                
                try {
                        DatabaseImportFrame = new DatabaseImportFrame(file, ParameterSelectionPanel_);
                        DatabaseImportFrame.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                       
            }
        });
	fileMenu.add(importDBItem);
 
        JMenuItem connectMenuItem = new JMenuItem("Connect to Winch");
        connectMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JTextField address = new JTextField("147.222.165.75", 10);
                JTextField port = new JTextField("32123", 10);
                JPanel connectPanel = new JPanel();
                connectPanel.setLayout(new BoxLayout(connectPanel, BoxLayout.PAGE_AXIS));
                connectPanel.add(new JLabel("Enter an IP Address and Port Number"));
                connectPanel.add(address);
                connectPanel.add(port);
                int result = JOptionPane.showConfirmDialog(null, connectPanel, "Connect to Server", JOptionPane.OK_CANCEL_OPTION);
                if(result == JOptionPane.OK_OPTION)
                {
                    String adString = address.getText();
                    int portNum = Integer.parseInt(port.getText());
                    if(!MessagePipeline.getInstance().connect(adString, portNum))
                    {
                        JOptionPane.showMessageDialog(null, "The Connection Failed", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        statusLabel.setText("Connected to " + adString + ":" + port.getText());                    
                    }
                }
            }
        });
	fileMenu.add(connectMenuItem);
        
        JMenuItem disconnectMenuItem = new JMenuItem("Disconnect from Winch");
        disconnectMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                MessagePipeline.getInstance().disconnect();
                statusLabel.setText("Disconnected");
            }
        });
        //disconnectMenuItem.setEnabled(false);
	fileMenu.add(disconnectMenuItem);
        
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
	fileMenu.add(exitMenuItem);
        
        
//EDIT MENU
        //editMenu.add(editAddMenu);

    	JMenuItem preferencesItem = new JMenuItem("Operator Profiles");
    	preferencesItem.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent event) {
                    ProfileManagementFrame = new ProfileManagementFrame();
                    ProfileManagementFrame.setParent(ParameterSelectionPanel_);
                    ProfileManagementFrame.setVisible(true);
        	}
    	});
	editMenu.add(preferencesItem);

        topMenu.add(fileMenu);
        topMenu.add(editMenu);
    }

    //TODO (jtroxel): remove this guy...necessary?
    public static void run() {

    }
}
