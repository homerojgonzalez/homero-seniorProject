package DatabaseUtilities;

import static Communications.ErrorLogger.logError;
import ParameterSelection.ParameterSelectionPanel;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex Williams, Noah Fujioka, dbennett3
 */
public class DatabaseInitialization {
    
    public static final String HOSTCONTROLLER_VERSION = "3.0.0";
    public static final String DATABASE_VERSION = "3.0.0";
    public static final String WINCH_PRAM_VERSION = "1.0.0";
    
    /* Used to connect to the database 
    if you change the this method you should do that in connectEx as well
    */
    
    public static Connection connect() {
        String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
        String clientDriverName = "org.apache.derby.jdbc.ClientDriver";
        String databaseConnectionName = "jdbc:derby:hcDatabase;create=true";
        Connection connection = null;
        
        //Check for DB drivers
        try{
            Class.forName(clientDriverName);
            Class.forName(driverName);
        }catch(ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Can't load JavaDb ClientDriver", 
                    "Error", JOptionPane.INFORMATION_MESSAGE);
            logError(e);
        }
    
        //Try to connect to the specified database
        try{
            connection = DriverManager.getConnection(databaseConnectionName);
        }catch(SQLException e) {
            //TODO Fix error handling
            JOptionPane.showMessageDialog(null, "Correctly loaded the JavaDb ClientDriver, "
                    + "somethin else is wrong", "Error", JOptionPane.INFORMATION_MESSAGE);
            logError(e);
        }
        return connection;
    }
    
    //Simmilar to the above but will throw exceptions
    public static Connection connectEx() throws ClassNotFoundException, SQLException {
        String driverName = "org.apache.derby.jdbc.EmbeddedDriver";
        String clientDriverName = "org.apache.derby.jdbc.ClientDriver";
        String databaseConnectionName = "jdbc:derby:hcDatabase;create=true";
        Connection connection = null;
        //Check for DB drivers
        try {
            Class.forName(clientDriverName);
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Can't load JavaDb ClientDriver", "Error", JOptionPane.INFORMATION_MESSAGE);
            throw e;
        }
        //Try to connect to the specified database
        try {
            connection = DriverManager.getConnection(databaseConnectionName);
        } catch (SQLException e) {
            //TODO Fix error handling
            JOptionPane.showMessageDialog(null, "Correctly loaded the JavaDb ClientDriver, " + "somethin else is wrong", "Error", JOptionPane.INFORMATION_MESSAGE);
            throw e;
        }
        return connection;
    }
    
    /**
    *Reinitializes the database for this program using calls to the helper
    *functions
    *
     * @param psp tells panel that database is created.
     * @return false if fails
    */
    public static boolean rebuildDatabase(ParameterSelectionPanel psp) {
        Connection connection = connect();
        if(connection == null) {
            return false;
        }
        //This is to drop all tables
        try{
            dropTables(connection);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Error creating statment");
            logError(e);
        }
        
        buildDatabase(connection);
        psp.update();
        
        return true;
    }
    
    //builds the database if the database has been deleted or didn't exist
    public static boolean buildDatabase(Connection connection) {
        if(connection == null) {
            return false;
        }
        
        //Build and set Version
        createVersion(connection);
        System.out.println("Build version");
        //Build and fill Capability table
        createCapability(connection);
        System.out.println("Build capability");
        //Build the Pilot table
        createPilot(connection);
        System.out.println("Build pilot");  
        //Build the Glider table
        createGlider(connection);
        System.out.println("Build glider");  
        //Build the Airfield table  
        createAirfield(connection);
        System.out.println("Build airfield");        
        //Build the Runway table
        createRunway(connection);
        System.out.println("Build runway");        
        //Build the GliderPosition table
        createGliderPosition(connection);
        System.out.println("Build glider position");        
        //Build the WinchPosition table
        createWinchPosition(connection);
        System.out.println("Build winch position");        
        //Build the Operator Profile table
        createOperator(connection);
        System.out.println("Build operator");        
        //Build the Winch table
        createWinch(connection);
        System.out.println("Build winch");        
        //Build the Drum table
        createDrum(connection);
        System.out.println("Build drum");        
        //Build the Parachute table
        createParachute(connection);
        System.out.println("Build parachute"); 
        //Build the Previous Airfield Info
        createPrevAirfieldInfo(connection);
        System.out.println("Build previous airfield info");        
        //Build the PreviousLaunches
        createPreviousLaunches(connection);
        System.out.println("Build previous launches");
        
        //Build Distance convesion tables
        createDistanceUnits(connection);
        createTensionUnits(connection);
        createVelocityUnits(connection);
        createWeightUnits(connection);
        createTemperatureUnits(connection);
        createPressureUnits(connection);
        createPilotUnits(connection);
        createGliderUnits(connection);
        createAirfieldUnits(connection);
        createPositionUnits(connection);
        createDashboardUnits(connection);
        createEnvironmentalUnits(connection);
        
        //Depricated
        createMessagesTable(connection);
        
        return true;
    }
    
    /**
     * Creates the table in the database for storing the database's version
     * 
     * @param connect the connection to be used for creating the table in the database
     * @throws SQLException if the table can't be created
     */
    private static void createVersion(Connection connect) {
        String createPilotString = "CREATE TABLE Version"
                + "(db_version VARCHAR(10), "
                + "hc_version VARCHAR(10),"
                + "PRIMARY KEY(db_version))";
        String insertVersion = "INSERT INTO Version (db_version, hc_version) VALUES(?, ?)";
        
        try (Statement createPilotTableStatement = connect.createStatement()) {
            createPilotTableStatement.execute(createPilotString);
            PreparedStatement ps = connect.prepareStatement(insertVersion);
            ps.setString(1, DATABASE_VERSION);
            ps.setString(2, HOSTCONTROLLER_VERSION);
            ps.executeUpdate();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }        
    }
    
    /**
     * Creates the table in the database for storing data associated with a Pilot object
     * 
     * @param connect the connection to be used for creating the table in the database
     * @throws SQLException if the table can't be created
     */
    private static void createPilot(Connection connect) {
        String createPilotString = "CREATE TABLE Pilot"
                + "(pilot_id INT, "
                + "first_name VARCHAR(30), "
                + "last_name VARCHAR(30), "
                + "middle_name VARCHAR(30), "
                + "flight_weight FLOAT, "
                + "capability INT, "
                + "preference FLOAT, "
                + "emergency_contact_name VARCHAR(30), "
                + "emergency_contact_phone VARCHAR(20), "
                + "optional_info LONG VARCHAR, "
                + "PRIMARY KEY (pilot_id), "
                + "FOREIGN KEY (capability) REFERENCES Capability (capability_id))";
        try (Statement createPilotTableStatement = connect.createStatement()) {
            createPilotTableStatement.execute(createPilotString);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }        
    }
    
    /**
     * Creates the table in the database for storing data associated with a Glider object
     * 
     * @param connectthe connection to be used for creating the table in the database
     * @throws SQLException if the table can't be created
     */
    private static void createGlider(Connection connect) {
        String createGliderString = "CREATE TABLE Glider"
                + "(glider_id INT, "
                + "reg_number VARCHAR(30),"
                + "common_name VARCHAR(30),"
                + "owner VARCHAR(30),"
                + "type VARCHAR(30),"
                + "max_gross_weight FLOAT,"
                + "empty_weight FLOAT,"
                + "indicated_stall_speed FLOAT,"
                + "max_winching_speed FLOAT,"
                + "max_weak_link_strength FLOAT,"
                + "max_tension FLOAT,"
                + "cable_release_angle FLOAT, "
                + "carry_ballast BOOLEAN, "
                + "multiple_seats BOOLEAN, "
                + "optional_info LONG VARCHAR,"
                + "UNIQUE (reg_number),"
                + "PRIMARY KEY (glider_id))";
        try (Statement createPilotTableStatement = connect.createStatement()) {
            createPilotTableStatement.execute(createGliderString);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
      
    /**
     * Creates the table in the database for storing the possible Capabilities of a Pilot
     * 
     * @param connectthe connection to be used for creating the table in the database
     * @throws SQLException if the table can't be created
     */
    private static void createCapability(Connection connect) {
        String createCapablityString = "CREATE TABLE Capability"
                + "(capability_id INT,"
                + "capability_string VARCHAR(15),"
                + "PRIMARY KEY (capability_id))";

        String addStudent = "INSERT INTO Capability(capability_id, capability_string) VALUES (0, 'Student')";
        String addProficient = "INSERT INTO Capability(capability_id, capability_string) VALUES (1, 'Proficient')";
        String addAdvanced = "INSERT INTO Capability(capability_id, capability_string) VALUES (2, 'Advanced')";
        try (Statement createCapabilityTableStatement = connect.createStatement()) {
                createCapabilityTableStatement.execute(createCapablityString);
                createCapabilityTableStatement.executeUpdate(addStudent);
                createCapabilityTableStatement.executeUpdate(addProficient);
                createCapabilityTableStatement.executeUpdate(addAdvanced);
                
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }        
    }
    
    /**
     * Creates the table in the database for storing data associated with a Airfield object
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createAirfield(Connection connect) {
        String createAirfieldString = "CREATE TABLE Airfield"
                + "(airfield_id INT, "
                + "name VARCHAR(30), "
                + "designator VARCHAR(30), "
                + "elevation FLOAT, "
                + "magnetic_variation FLOAT, "
                + "latitude FLOAT, "
                + "longitude FLOAT, "
                + "utc_offset INT, "
                + "optional_info LONG VARCHAR, "
                + "UNIQUE (designator),"
                + "PRIMARY KEY (airfield_id))";
        try (Statement createAirfieldTableStatement = connect.createStatement()) {
            createAirfieldTableStatement.execute(createAirfieldString);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
    /**
     * Creates the table in the database for storing data associated with a Runway object
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createRunway(Connection connect) {
        String createRunwayString = "CREATE TABLE Runway "
                + "(runway_id INT, "
                + "parent_id INT, "
                + "runway_name VARCHAR(10), "
                + "magnetic_heading FLOAT, "
                + "optional_info LONG VARCHAR, "
                + "PRIMARY KEY (runway_id), "
                + "FOREIGN KEY (parent_id) REFERENCES Airfield (airfield_id) ON DELETE CASCADE)";
        try (Statement createRunwayTableStatement = connect.createStatement()) {
            createRunwayTableStatement.execute(createRunwayString);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
    /**
     * Creates the table in the database for storing data associated with a GliderPosition object
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createGliderPosition(Connection connect) {
        String createGliderPositionString = "CREATE TABLE GliderPosition "
                + "(glider_position_id INT, "
                + "parent_id INT, "
                + "position_name VARCHAR(30), "
                + "elevation FLOAT, "
                + "latitude FLOAT, "
                + "longitude FLOAT, "
                + "optional_info LONG VARCHAR, "
                + "PRIMARY KEY (glider_position_id), "
                + "FOREIGN KEY (parent_id) REFERENCES Runway (runway_id) ON DELETE CASCADE)";
        try (Statement createGliderPositionTableStatement = connect.createStatement()) {
            createGliderPositionTableStatement.execute(createGliderPositionString);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
    /**
     * Creates the table in the database for storing data associated with a WinchPosition object
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createWinchPosition(Connection connect) {
        String createWinchPositionString = "CREATE TABLE WinchPosition"
                + "(winch_position_id INT, "
                + "parent_id INT, "
                + "position_name VARCHAR(30), "
                + "elevation FLOAT, "
                + "latitude FLOAT, "
                + "longitude FLOAT, "
                + "optional_info LONG VARCHAR, "
                + "PRIMARY KEY (winch_position_id), "
                + "FOREIGN KEY (parent_id) REFERENCES Runway (runway_id) ON DELETE CASCADE)";
        try (Statement createWinchPositionTableStatement = connect.createStatement()) {
            createWinchPositionTableStatement.execute(createWinchPositionString);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
    /**
     * Creates the table in the database for storing data associated with a Winch object
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createWinch(Connection connect) {
        String createDrumString = "CREATE TABLE Winch "
                + "(winch_id INT, " 
                + "name VARCHAR(30), " 
                + "owner VARCHAR(30), " 
                + "wc_version VARCHAR(10), "
                + "w1 FLOAT, w2 FLOAT, w3 FLOAT, "
                + "w4 FLOAT, w5 FLOAT, w6 FLOAT, "
                + "w7 FLOAT, w8 FLOAT, w9 FLOAT, "
                + "w10 FLOAT, w11 FLOAT, w12 FLOAT, "
                + "w13 FLOAT, w14 FLOAT, w15 FLOAT, "
                + "w16 FLOAT, " 
                + "meteorological_check_time INT, " 
                + "meteorological_verify_time INT, " 
                + "run_orientation_tolerance FLOAT, " 
                + "optional_info LONG VARCHAR, "
                + "PRIMARY KEY (winch_id))";
        try (Statement createDrumTableStatement = connect.createStatement()) {
            createDrumTableStatement.execute(createDrumString);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
    /**
     * Creates the table in the database for storing data associated with a Drum object
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createDrum(Connection connect) {
        String createDrumString = "CREATE TABLE Drum "
                + "(drum_id INT, "
                + "winch_id INT, "
                + "drum_name VARCHAR(30), "
                + "drum_number INT, "
                + "core_diameter FLOAT, "
                + "kfactor FLOAT, "
                + "cable_length FLOAT, "
                + "cable_density FLOAT, "
                + "drum_system_emass FLOAT, "//Drum System Equivalent Mass
                + "number_of_launches INT, "
                + "maximum_working_tension FLOAT, "
                + "optional_info LONG VARCHAR, "
                + "PRIMARY KEY (drum_id), "
                + "FOREIGN KEY (winch_id) REFERENCES Winch (winch_id) ON DELETE CASCADE)";
        try (Statement createDrumTableStatement = connect.createStatement()) {
            createDrumTableStatement.execute(createDrumString);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
     /**
     * Creates the table in the database for storing data associated with a Parachute object
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createParachute(Connection connect) {
        String createParachuteString = "CREATE TABLE Parachute"
                + "(parachute_id INT, "
                + "name VARCHAR(30), "
                + "lift FLOAT, "
                + "drag FLOAT, "
                + "weight FLOAT, "
                + "optional_info LONG VARCHAR, "
                + "PRIMARY KEY (parachute_id))";
        try (Statement createParachuteTableStatement = connect.createStatement()) {
            createParachuteTableStatement.execute(createParachuteString);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
        
    /**
     * Creates the table to store a list of previous launches
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createPreviousLaunches(Connection connect) {
        String createPastLaunchesInfo = "CREATE TABLE PreviousLaunches"
                + "(start_timestamp TIMESTAMP, "
                + "end_timestamp TIMESTAMP, "
                + ""
                + "first_name VARCHAR(30), "
                + "last_name VARCHAR(30), "
                + "middle_name VARCHAR(30), "
                + "flight_weight FLOAT, "
                + "capability INT, "
                + "preference FLOAT, "
                + "emergency_contact_name VARCHAR(30), "
                + "emergency_contact_phone VARCHAR(20), "
                + "pilot_optional_info LONG VARCHAR, "
                + ""
                + "reg_number VARCHAR(30),"
                + "common_name VARCHAR(30),"
                + "glider_owner VARCHAR(30),"
                + "type VARCHAR(30),"
                + "max_gross_weight FLOAT,"
                + "empty_weight FLOAT,"
                + "indicated_stall_speed FLOAT,"
                + "max_winching_speed FLOAT,"
                + "max_weak_link_strength FLOAT,"
                + "max_tension FLOAT,"
                + "cable_release_angle FLOAT, "
                + "carry_ballast BOOLEAN, "
                + "multiple_seats BOOLEAN, "
                + "glider_optional_info LONG VARCHAR, "
                + ""
                + "wind_direction_winch FLOAT, "
                + "wind_average_speed_winch FLOAT, " 
                + "wind_gust_speed_winch FLOAT, " 
                + "wind_direction_glider FLOAT, " 
                + "wind_average_speed_glider FLOAT, " 
                + "wind_gust_speed_glider FLOAT, " 
                + "density_altitude FLOAT, " 
                + "temperature FLOAT, " 
                + "altimeter_setting FLOAT, "
                + "ballast FLOAT, "
                + "baggage FLOAT, "
                + "passenger_weight FLOAT, "
                + ""
                + "drum_name VARCHAR(30), " 
                + "drum_number INT, "
                + "core_diameter FLOAT, "
                + "kfactor FLOAT, "
                + "cable_length FLOAT, "
                + "cable_density FLOAT, "
                + "drum_system_emass FLOAT, " //Drum System Equivalent Mass
                + "number_of_launches INT, "
                + "maximum_working_tension FLOAT, "
                + "parachute_name VARCHAR(30), "
                + "parachute_lift FLOAT, "
                + "parachute_drag FLOAT, "
                + "parachute_weight FLOAT, "
                + ""
                + "airfield_info INT, "
                + "FOREIGN KEY (capability) REFERENCES Capability (capability_id), "
                + "FOREIGN KEY (airfield_info) REFERENCES PreviousAirfieldInfo (table_id) ON DELETE CASCADE, "
                + "PRIMARY KEY (start_timestamp))";
        try (Statement createPastLaunchesInfoTableStatement = connect.createStatement()) {
            createPastLaunchesInfoTableStatement.execute(createPastLaunchesInfo);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
    /**
     * Creates the table to store a list of winch and airfield info associated with previous launches
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createPrevAirfieldInfo(Connection connect) {
        String createPrevInfo = "CREATE TABLE PreviousAirfieldInfo "
                + "(table_id INT, "
                + "airfield_name VARCHAR(30), "
                + "airfield_designator VARCHAR(30), "
                + "airfield_elevation FLOAT, "
                + "airfield_magnetic_variation FLOAT, "
                + "airfield_latitude FLOAT, "
                + "airfield_longitude FLOAT, "
                + "airfield_utc_offset INT, "
                + ""
                + "runway_name VARCHAR(10), "
                + "runway_magnetic_heading FLOAT, "
                + "glider_position_name VARCHAR(30), "
                + "glider_position_elevation FLOAT, "
                + "glider_position_latitude FLOAT, "
                + "glider_position_longitude FLOAT, "
                + "winch_position_name VARCHAR(30), "
                + "winch_position_elevation FLOAT, "
                + "winch_position_latitude FLOAT, "
                + "winch_position_longitude FLOAT, "
                + ""
                + "winch_name VARCHAR(30), " 
                + "winch_owner VARCHAR(30), "
                + "PRIMARY KEY (table_id))";
        try (Statement createPastLaunchesInfoTableStatement = connect.createStatement()) {
            createPastLaunchesInfoTableStatement.execute(createPrevInfo);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }       
    }
    
    /**
     * Creates the table in the database for storing data associated with a Operator object
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createOperator(Connection connect) {
        String createProfileString = "CREATE TABLE Operator"
                + "(operator_id INT, "
                + "first_name VARCHAR(30),"
                + "middle_name VARCHAR(30),"
                + "last_name VARCHAR(30), "
                + "admin BOOLEAN,"
                + "salt VARCHAR(30),"
                + "hash VARCHAR(60), "
                + "optional_info LONG VARCHAR, "
                + "unitSettings LONG VARCHAR, "
                + "PRIMARY KEY (operator_id))";
        try (Statement createProfileTableStatement = connect.createStatement()) {
            createProfileTableStatement.execute(createProfileString);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
    /**
     * Creates the table to store the possible units of distance
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createDistanceUnits(Connection connect) {
        String createLengthUnitsString = "CREATE TABLE DistanceUnits"
                + "(index INT, "
                + "abbreviation VARCHAR(5), "
                + "PRIMARY KEY (index))";
        String meters = "INSERT INTO DistanceUnits(index, abbreviation) VALUES (0, 'm')";
        String feet = "INSERT INTO DistanceUnits(index, abbreviation) VALUES (1, 'ft')";
        String millimeters = "INSERT INTO DistanceUnits(index, abbreviation) VALUES (2, 'mm')";
        String centimeters = "INSERT INTO DistanceUnits(index, abbreviation) VALUES (3, 'cm')";
        String kilometers = "INSERT INTO DistanceUnits(index, abbreviation) VALUES (4, 'Km')";
        try (Statement createLengthUnitsTableStatement = connect.createStatement()) {
            createLengthUnitsTableStatement.execute(createLengthUnitsString);
            createLengthUnitsTableStatement.executeUpdate(meters);
            createLengthUnitsTableStatement.executeUpdate(feet);
            createLengthUnitsTableStatement.executeUpdate(millimeters);
            createLengthUnitsTableStatement.executeUpdate(centimeters);
            createLengthUnitsTableStatement.executeUpdate(kilometers);
            createLengthUnitsTableStatement.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
     /**
     * Creates the table to store the possible units of tension
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createTensionUnits(Connection connect) {
        String createTensionUnitsString = "CREATE TABLE TensionUnits"
                + "(index INT, "
                + "abbreviation VARCHAR(5), "
                + "PRIMARY KEY (index))";
        String newtons = "INSERT INTO TensionUnits(index, abbreviation) VALUES (0, 'N')";
        String poundForce = "INSERT INTO TensionUnits(index, abbreviation) VALUES (1, 'lbf')";
        String kilogramForce = "INSERT INTO TensionUnits(index, abbreviation) VALUES (2, 'Kgf')";
        try (Statement createTensionUnitsTableStatement = connect.createStatement()) {
            createTensionUnitsTableStatement.execute(createTensionUnitsString);
            createTensionUnitsTableStatement.executeUpdate(newtons);
            createTensionUnitsTableStatement.executeUpdate(poundForce);
            createTensionUnitsTableStatement.executeUpdate(kilogramForce);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
     /**
     * Creates the table to store the possible units of weight
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createWeightUnits(Connection connect) {
        String createWeightUnitsString = "CREATE TABLE WeightUnits"
                + "(index INT, "
                + "abbreviation VARCHAR(5), "
                + "PRIMARY KEY (index))";
        String pounds = "INSERT INTO WeightUnits(index, abbreviation) VALUES (0, 'lbs')";
        String kilograms = "INSERT INTO WeightUnits(index, abbreviation) VALUES (1, 'Kg')";
        try (Statement createWeightUnitsTableStatement = connect.createStatement()) {
            createWeightUnitsTableStatement.execute(createWeightUnitsString);
            createWeightUnitsTableStatement.executeUpdate(pounds);
            createWeightUnitsTableStatement.executeUpdate(kilograms);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
     /**
     * Creates the table to store the possible units of velocity
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createVelocityUnits(Connection connect) {
        String createVelocityUnitsString = "CREATE TABLE VelocityUnits"
                + "(index INT, "
                + "abbreviation VARCHAR(5), "
                + "PRIMARY KEY (index))";
        String milesPerHour = "INSERT INTO VelocityUnits(index, abbreviation) VALUES (0, 'mph')";
        String kmPerHour = "INSERT INTO VelocityUnits(index, abbreviation) VALUES (1, 'Km/h')";
        String metersPerSecond = "INSERT INTO VelocityUnits(index, abbreviation) VALUES (2, 'm/s')";
        String knots = "INSERT INTO VelocityUnits(index, abbreviation) VALUES (3, 'kn')";
        try (Statement createVelocityUnitsTableStatement = connect.createStatement()) {
            createVelocityUnitsTableStatement.execute(createVelocityUnitsString);
            createVelocityUnitsTableStatement.executeUpdate(milesPerHour);
            createVelocityUnitsTableStatement.executeUpdate(kmPerHour);
            createVelocityUnitsTableStatement.executeUpdate(metersPerSecond);
            createVelocityUnitsTableStatement.executeUpdate(knots);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
     /**
     * Creates the table to store the possible units of temperature
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createTemperatureUnits(Connection connect) {
        String createTemperatureUnitsString = "CREATE TABLE TemperatureUnits"
                + "(index INT, "
                + "abbreviation VARCHAR(5), "
                + "PRIMARY KEY (index))";
        String fahrenheit = "INSERT INTO TemperatureUnits(index, abbreviation) VALUES (0, 'F')";
        String celsius = "INSERT INTO TemperatureUnits(index, abbreviation) VALUES (1, 'C')";
        try (Statement createVelocityUnitsTableStatement = connect.createStatement()) {
            createVelocityUnitsTableStatement.execute(createTemperatureUnitsString);
            createVelocityUnitsTableStatement.executeUpdate(fahrenheit);
            createVelocityUnitsTableStatement.executeUpdate(celsius);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
     /**
     * Creates the table to store the possible units of pressure
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createPressureUnits(Connection connect) {
        String createPressureUnitsString = "CREATE TABLE PressureUnits"
                + "(index INT, "
                + "abbreviation VARCHAR(5), "
                + "PRIMARY KEY (index))";
        String psi = "INSERT INTO PressureUnits(index, abbreviation) VALUES (0, 'psi')";
        String megapascals = "INSERT INTO PressureUnits(index, abbreviation) VALUES (1, 'Mp')";
        String kilopascals = "INSERT INTO PressureUnits(index, abbreviation) VALUES (2, 'Kp')";
        try (Statement createPressureUnitsTableStatement = connect.createStatement()) {
            createPressureUnitsTableStatement.execute(createPressureUnitsString);
            createPressureUnitsTableStatement.executeUpdate(psi);
            createPressureUnitsTableStatement.executeUpdate(megapascals);
            createPressureUnitsTableStatement.executeUpdate(kilopascals);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
     /**
     * Creates the table to store the units for pilot data
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createPilotUnits(Connection connect) {
        String createPressureUnitsString = "CREATE TABLE PilotUnits"
                + "(unit_set INT, "
                + "weight_unit INT, "
                + "PRIMARY KEY (unit_set), "
                + "FOREIGN KEY (weight_unit) REFERENCES WeightUnits (index))";
        String insertRow = "INSERT INTO PilotUnits(unit_set, weight_unit) VALUES (0, 0)";
        try (Statement createPressureUnitsTableStatement = connect.createStatement()) {
            createPressureUnitsTableStatement.execute(createPressureUnitsString);
            createPressureUnitsTableStatement.executeUpdate(insertRow);
            createPressureUnitsTableStatement.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
     /**
     * Creates the table to store the units for Glider data
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createGliderUnits(Connection connect) {
        String createPressureUnitsString = "CREATE TABLE GliderUnits"
                + "(unit_set INT, "
                + "weight_unit INT, "
                + "velocity_unit INT, "
                + "tension_unit INT, "
                + "PRIMARY KEY (unit_set), "
                + "FOREIGN KEY (weight_unit) REFERENCES WeightUnits (index), "
                + "FOREIGN KEY (velocity_unit) REFERENCES VelocityUnits (index) ,"
                + "FOREIGN KEY (tension_unit) REFERENCES TensionUnits (index))";
        String insertRow = "INSERT INTO GliderUnits(unit_set, weight_unit, velocity_unit, tension_unit) "
                + "VALUES (0, 0, 0, 0)";
        try (Statement createPressureUnitsTableStatement = connect.createStatement()) {
            createPressureUnitsTableStatement.execute(createPressureUnitsString);
            createPressureUnitsTableStatement.executeUpdate(insertRow);
            createPressureUnitsTableStatement.close();
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
     /**
     * Creates the table to store the units for Airfield data
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createAirfieldUnits(Connection connect) {
        String createPressureUnitsString = "CREATE TABLE AirfieldUnits"
                + "(unit_set INT, "
                + "distance_unit INT, "
                + "PRIMARY KEY (unit_set), "
                + "FOREIGN KEY (distance_unit) REFERENCES DistanceUnits (index))";
        String insertRow = "INSERT INTO AirfieldUnits(unit_set, distance_unit) VALUES (0, 0)";
        try (Statement createPressureUnitsTableStatement = connect.createStatement()) {
            createPressureUnitsTableStatement.execute(createPressureUnitsString);
            createPressureUnitsTableStatement.executeUpdate(insertRow);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
    /**
     * Creates the table to store the units for Position data
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createPositionUnits(Connection connect) {
        String createPressureUnitsString = "CREATE TABLE PositionUnits"
                + "(unit_set INT, "
                + "distance_unit INT, "
                + "PRIMARY KEY (unit_set), "
                + "FOREIGN KEY (distance_unit) REFERENCES DistanceUnits (index))";
        String insertRow = "INSERT INTO PositionUnits(unit_set, distance_unit) VALUES (0, 0)";
        try (Statement createPressureUnitsTableStatement = connect.createStatement()) {
            createPressureUnitsTableStatement.execute(createPressureUnitsString);
            createPressureUnitsTableStatement.executeUpdate(insertRow);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
    /**
     * Creates the table to store the units for Dashboard display data
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createDashboardUnits(Connection connect) {
        String createPressureUnitsString = "CREATE TABLE DashboardUnits"
                + "(unit_set INT, "
                + "distance_unit INT, "
                + "tension_unit INT, "
                + "velocity_unit INT, "
                + "PRIMARY KEY (unit_set), "
                + "FOREIGN KEY (distance_unit) REFERENCES DistanceUnits (index), "
                + "FOREIGN KEY (tension_unit) REFERENCES TensionUnits (index), "
                + "FOREIGN KEY (velocity_unit) REFERENCES VelocityUnits (index))";
        String insertRow = "INSERT INTO DashboardUnits(unit_set, distance_unit, tension_unit, velocity_unit) "
                + "VALUES (0, 0, 0, 0)";
        try (Statement createPressureUnitsTableStatement = connect.createStatement()) {
            createPressureUnitsTableStatement.execute(createPressureUnitsString);
            createPressureUnitsTableStatement.executeUpdate(insertRow);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
    /**
     * Creates the table to store the units for environmental data
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createEnvironmentalUnits(Connection connect) {
        String createPressureUnitsString = "CREATE TABLE EnvironmentalUnits"
                + "(unit_set INT, "
                + "temp_unit INT, "
                + "pressure_unit INT, "
                + "PRIMARY KEY (unit_set), "
                + "FOREIGN KEY (temp_unit) REFERENCES TemperatureUnits (index), "
                + "FOREIGN KEY (pressure_unit) REFERENCES PressureUnits (index))";
        String insertRow = "INSERT INTO EnvironmentalUnits(unit_set, temp_unit, pressure_unit) VALUES (0, 0, 0)";
        try (Statement createPressureUnitsTableStatement = connect.createStatement()) {
            createPressureUnitsTableStatement.execute(createPressureUnitsString);
            createPressureUnitsTableStatement.executeUpdate(insertRow);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
    /**
     * Creates the table to store flight messages
     * 
     * @param connect the connection to be used for creating the table in the database
     */
    private static void createMessagesTable(Connection connect) {
        String createMessagesString = "CREATE TABLE Messages"
                + "(timestamp BIGINT, "
                + "message VARCHAR(40))";
        try (Statement createMessagesTableStatement = connect.createStatement()) {
            createMessagesTableStatement.execute(createMessagesString);
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            logError(e);
        }
    }
    
    /*
        used to drop tables individually for clean up methods
    */
    private static void dropVersion(Connection connect) {
        try(Statement stmt = connect.createStatement()) {
            stmt.execute("DROP TABLE VERSION");
            System.out.println("Dropped version info");
        } catch(SQLException e) { logError(e); }
    }
    private static void dropPrevLaunches(Connection connect) {
        try(Statement stmt = connect.createStatement()) {
            stmt.execute("DROP TABLE PREVIOUSLAUNCHES");
            System.out.println("Dropped previous launch");
        } catch(SQLException e) { logError(e); }
    }
    private static void dropPrevAirfield(Connection connect) {
        try(Statement stmt = connect.createStatement()) {
            stmt.execute("DROP TABLE PreviousAirfieldInfo");
            System.out.println("Dropped previous airfield info");
        } catch(SQLException e) { logError(e); }
    }
    private static void dropDrum(Connection connect) {
        try(Statement stmt = connect.createStatement()) {
            stmt.execute("DROP TABLE DRUM");
            System.out.println("Dropped drum");
        } catch(SQLException e) { logError(e); }
    }
    private static void dropWinch(Connection connect) {
        try(Statement stmt = connect.createStatement()) {
            stmt.execute("DROP TABLE WINCH");
            System.out.println("Dropped winch");
        } catch(SQLException e) { logError(e); }
    }
    private static void dropParachute(Connection connect) {
        try(Statement stmt = connect.createStatement()) {
            stmt.execute("DROP TABLE PARACHUTE");
            System.out.println("Dropped parachute");
        } catch(SQLException e) { logError(e); }
    }
    private static void dropOperator(Connection connect) {
        try(Statement stmt = connect.createStatement()) {
            stmt.execute("DROP TABLE OPERATOR");
            System.out.println("Dropped operator");
        } catch(SQLException e) { logError(e); }
    }
    private static void dropWinchPosition(Connection connect) {
        try(Statement stmt = connect.createStatement()) {
            stmt.execute("DROP TABLE WINCHPOSITION");
            System.out.println("Dropped winch position");
        } catch(SQLException e) { logError(e); }
    }
    private static void dropGliderPosition(Connection connect) {
        try(Statement stmt = connect.createStatement()) {
            stmt.execute("DROP TABLE GLIDERPOSITION");
            System.out.println("Dropped glider position");
        } catch(SQLException e) { logError(e); }
    }
    private static void dropRunway(Connection connect) {
        try(Statement stmt = connect.createStatement()) {
            stmt.execute("DROP TABLE RUNWAY");
            System.out.println("Dropped runway");
        } catch(SQLException e) { logError(e); }
    }
    private static void dropAirfield(Connection connect) {
        try(Statement stmt = connect.createStatement()) {
            stmt.execute("DROP TABLE AIRFIELD");
            System.out.println("Dropped airfield");
        } catch(SQLException e) { logError(e); }
    }
    private static void dropGlider(Connection connect) {
        try(Statement stmt = connect.createStatement()) {
            stmt.execute("DROP TABLE GLIDER");
            System.out.println("Dropped glider");
        } catch(SQLException e) { logError(e); }
    }
    private static void dropPilot(Connection connect) {
        try(Statement stmt = connect.createStatement()) {
            stmt.execute("DROP TABLE PILOT");
            System.out.println("Dropped pilot");
        } catch(SQLException e) { logError(e); }
    }
    
    /**
     * Drops all tables
     * The order at which these tables are dropped is important because of table references
     */
    private static void dropTables(Connection connect) throws SQLException {       
        Statement stmt = connect.createStatement();
        
        dropVersion(connect);
        dropPrevLaunches(connect);
        dropPrevAirfield(connect);
        dropDrum(connect);
        dropWinch(connect);
        dropParachute(connect);
        dropOperator(connect);
        dropWinchPosition(connect);
        dropGliderPosition(connect);
        dropRunway(connect);
        dropAirfield(connect);
        dropGlider(connect);
        dropPilot(connect);
        
        try {
            stmt.execute("DROP TABLE CAPABILITY");
            System.out.println("Dropped capability");
        } catch(SQLException e) { logError(e); }
        
        try {
            stmt.execute("DROP TABLE PilotUnits");
            System.out.println("Dropped PilotUnits");
        } catch(SQLException e) { logError(e); }
        
        try {
            stmt.execute("DROP TABLE GliderUnits");
            System.out.println("Dropped GliderUnits");
        } catch(SQLException e) { logError(e); }
        
        try {
            stmt.execute("DROP TABLE AirfieldUnits");
            System.out.println("Dropped AirfieldUnits");
        } catch(SQLException e) { logError(e); }
        
        try {
            stmt.execute("DROP TABLE PositionUnits");
            System.out.println("Dropped PositionUnits");
        } catch(SQLException e) { logError(e); }
        
        try {
            stmt.execute("DROP TABLE DashboardUnits");
            System.out.println("Dropped DashboardUnits");
        } catch(SQLException e) { logError(e); }
        
        try {
            stmt.execute("DROP TABLE EnvironmentalUnits");
            System.out.println("Dropped EnvironmentalUnits");
        } catch(SQLException e) { logError(e); }
            
        try {
            stmt.execute("DROP TABLE DistanceUnits");
            System.out.println("Dropped DistanceUnits");
        } catch(SQLException e) { logError(e); }
        
        try {
            stmt.execute("DROP TABLE TensionUnits");
            System.out.println("Dropped TensionUnits");
        } catch(SQLException e) { logError(e); }
        
        try {
            stmt.execute("DROP TABLE WeightUnits");
            System.out.println("Dropped WeightUnits");
        } catch(SQLException e) { logError(e); }
        
        try {
            stmt.execute("DROP TABLE VelocityUnits");
            System.out.println("Dropped VelocityUnits");
        } catch(SQLException e) { logError(e); }
        
        try {
            stmt.execute("DROP TABLE TemperatureUnits");
            System.out.println("Dropped TemperatureUnits");
        } catch(SQLException e) { logError(e); }
        
        try {
            stmt.execute("DROP TABLE PressureUnits");
            System.out.println("Dropped PressureUnits");
        } catch(SQLException e) { logError(e); }
        
        try {
            stmt.execute("DROP TABLE Messages");
            System.out.println("Dropped Messages");
        } catch(SQLException e) { logError(e); }
        
        stmt.close();
    }
    
    /* 
     * Used to scrub tables of entries
     **Caution** to clear a table that is referenced by another table
     * both tables will have to be cleared otherwise it won't work
     */
    
    static void cleanPrevLaunches(Connection connect) {
        dropPrevLaunches(connect);
        createPreviousLaunches(connect);
    }
    static void cleanPrevAirfield(Connection connect) {
        cleanPrevLaunches(connect);
        dropPrevAirfield(connect);
        createPrevAirfieldInfo(connect);
    }
    static void cleanDrum(Connection connect) {
        dropDrum(connect);
        createDrum(connect);
    }
    static void cleanWinch(Connection connect) {
        dropDrum(connect);
        dropWinch(connect);
        createWinch(connect);
        createDrum(connect);
    }
    static void cleanParachute(Connection connect) {
        dropParachute(connect);
        createParachute(connect);
    }
    static void cleanOperator(Connection connect) {
        dropOperator(connect);
        createOperator(connect);
    }
    static void cleanWinchPosition(Connection connect) {
        dropWinchPosition(connect);
        createWinchPosition(connect);
    }
    static void cleanGliderPosition(Connection connect) {
        dropGliderPosition(connect);
        createGliderPosition(connect);
    }
    static void cleanRunway(Connection connect) {
        dropGliderPosition(connect);
        dropWinchPosition(connect);
        dropRunway(connect);
        createRunway(connect);
        createWinchPosition(connect);
        createGliderPosition(connect);

    }
    static void cleanAirfield(Connection connect) {
        dropGliderPosition(connect);
        dropWinchPosition(connect);
        dropRunway(connect);
        dropAirfield(connect);
        createAirfield(connect);
        createRunway(connect);
        createWinchPosition(connect);
        createGliderPosition(connect);
    }
    static void cleanGlider(Connection connect) {
        dropGlider(connect);
        createGlider(connect);
    }
    static void cleanPilot(Connection connect) {
        dropPilot(connect);
        createPilot(connect);
    }
    
}
