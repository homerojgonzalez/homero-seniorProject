/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainhost;


import Communications.MessagePipeline;

/**
 * The Main class is the launching point for this program where the UI and 
 * the MessagePipeline are started up.
 *  JTROXEL
 */
public class Main {
    

    public static void main(String[] args) {
        MainWindow a = new MainWindow();
        a.setVisible(true);
        MessagePipeline pipe = MessagePipeline.getInstance();
        Thread pipeThread = new Thread(pipe);
        pipeThread.start();//runs in background, not connected yet
    }
    
}
