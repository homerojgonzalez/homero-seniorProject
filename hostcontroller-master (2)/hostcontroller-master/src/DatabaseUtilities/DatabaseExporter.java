/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseUtilities;

import static Communications.ErrorLogger.logError;
import static DatabaseUtilities.DatabaseInitialization.connect;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author dbennett3
 */
public class DatabaseExporter {
    
    /**
     * Exports tables to a csv file using a unique timestamp and the name of the name of the table
     * The location of the files is its own folder in the final build 
     * 
     * @param tableName name of the table that is to be exported
     * @param zipName
     * @return 
     */
    public static boolean exportDatabase(List<String> tableName, String zipName) {
        Connection connection = connect();
        //try to connect
        if(connection == null) {
            return false;
        }
        return exportTable(connection, tableName, zipName);
        
    }
    
    //exports tables listed in names into a single zip file
    private static boolean exportTable(Connection connect, List<String> names, String zipName) {
        
        FileOutputStream fos;
        ZipOutputStream zos;
        ZipEntry ze;
        
        try {
            fos = new FileOutputStream(zipName);
            zos = new ZipOutputStream(fos);
            
            String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            
            for(String str : names)
            {
                String resultString;
                String fileName;
                if(str.equalsIgnoreCase("VERSION")) {
                    fileName = "__output_" + str + "_" + timestamp + ".csv";                    
                } else if(str.equalsIgnoreCase("airfield") || 
                        str.equalsIgnoreCase("runway") || str.equalsIgnoreCase("winch")) {
                    fileName = "_output_" + str + "_" + timestamp + ".csv";                    
                } else {
                    fileName = "output_" + str + "_" + timestamp + ".csv";
                }
                
                ze = new ZipEntry(fileName);
                zos.putNextEntry(ze);
                
                PreparedStatement ps = connect.prepareStatement("SELECT * FROM " + str);
                ResultSet rs = ps.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                
                int columnCount = rsmd.getColumnCount();
                
                while(rs.next())
                {
                    resultString = "";
                    for(int i = 1; i <= columnCount; i++)
                    {
                        resultString += rs.getString(i) + ",";
                    }
                    resultString += "\n";
                    
                    byte[] buffer = resultString.getBytes();
                    zos.write(buffer);
                }
                
                zos.closeEntry();
            }
            zos.finish();
            zos.close();
            
        }catch(IOException e) { 
            JOptionPane.showMessageDialog(null, "File not Found", "Error", JOptionPane.INFORMATION_MESSAGE);
            logError(e);
            return false;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error exporting", "Error", JOptionPane.INFORMATION_MESSAGE);
            logError(e);
            return false;
        }
        
        return true;
    } 

    
}
