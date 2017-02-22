package Communications;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MessagePipeline implements Runnable {
    
    private ArrayList<Observer> observers;
    private String oldMessage = "";
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    //private OutputStreamWriter writer;
    private String currentMessage = "";
    private static MessagePipeline instance = null;
    private boolean running = false;
    private boolean connected = false;
    private MessageListener listener = new MessageListener();
    private DataRelay relay = new DataRelay();
    private String DEBUGMessageList[] = {"5 100 100 65"
                                        ,"5.1 200 100 50"
                                        ,"5.2 100 500 100"
                                        ,"5.3 100 20 45"
                                        ,"5.4 30 46 100"
                                        ,"5.5 30 46 100"
                                        ,"5.6 56 46 500"
                                        ,"5.7 30 46 40"
                                        ,"5.8 30 46 56"
                                        ,"5.9 78 46 58"};
    private int curMessageIndex = 0;
    
    boolean debugMode = false;

    
    public static MessagePipeline getInstance()
    {
        if(instance == null)
        {
            instance = new MessagePipeline();
            instance.observers = new ArrayList<>();
            instance.init();
            instance.listener.attachRelay(instance.relay);
            instance.relay.setParent(instance.listener);
        }
        return instance;
    }
   
    public static DataRelay getDataRelay()
    {
        if(instance != null) return instance.relay;
        else return null;
    }
    
    public void init()
    {
        running = true;
    }
    
    public boolean connect(String address, int port)
    {
        try {
            if(address.equals("DEBUG"))
            {
                debugMode = true;
            }
            else
            {
                socket = new Socket(address, port);
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            }
            connected = true;
            System.out.println("Connected");
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        } 
    }
    
    public void disconnect()
    {
        connected = false;
        currentMessage = "";
    }
    
    public void attach(Observer ob)
    {
        observers.add(ob);
    }
    
    private void notifyObservers()
    {
        //System.out.println("Notifying");
        /*for(Observer ob : observers)
        {
            ob.update(currentMessage);
        }*/
    }
    
    public void TEMPReadFromSocket()
    {
        currentMessage = DEBUGMessageList[curMessageIndex];
        ++curMessageIndex;
        curMessageIndex%=10;
        /*try {
            String s = reader.readLine();
            currentMessage = s;
            if(!s.equals(""))
            {
                notifyObservers();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
    }
    
    public void ReadFromSocket()
    {
        try {
            String s = reader.readLine();
            currentMessage = s;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void TEMPWriteToSocket(String s)
    {
        try {
            writer.write(s);
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
    }
    
    public void WriteToSocket(String s)
    {   /*
        try {
            writer.write(s);
            writer.flush();
            try {
                if(!currentMessage.equals(oldMessage) && !currentMessage.equals(""))
                {
                    DatabaseEntryInsert.addMessageToBlackBox(listener.getCurrentUnixTime(), currentMessage);
                    oldMessage = currentMessage;
                }
            } catch (SQLException | ClassNotFoundException ex) {
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }    
        */
    }
    
    public void run()
    {
        while(running)
        {
            if(connected)
            {
                if(debugMode) TEMPReadFromSocket();
                else ReadFromSocket();
            }
            /*else
            {
                //if not connected, wait a bit before rechecking
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    //error related to sleeping?
                }
            }*/
            listener.update(currentMessage);
            /*
            try {
                if(!currentMessage.equals(oldMessage) && !currentMessage.equals(""))
                {
                    DatabaseEntryInsert.addMessageToBlackBox(listener.getCurrentUnixTime(), currentMessage);
                    oldMessage = currentMessage;
                }
            } catch (SQLException | ClassNotFoundException ex) {
            }       
            */
        }
    }
    
    public void close()
    {
        running = false;
        try {
            socket.close();
            writer.close();
            reader.close();
        } catch (IOException ex) {
        }
    }
}
