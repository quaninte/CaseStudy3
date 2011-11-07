/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package casestudy3;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quanmt
 */
public class Client extends Thread {
    
    Socket socket;
    DataInputStream is;
    DataOutputStream os;
    App app;

    /**
     * Constructor
     * @param socket 
     */
    public Client(Socket socket, App app) {
        
        this.socket = socket;
        this.app = app;
        
        try {
            this.is = new DataInputStream(socket.getInputStream());
            this.os = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Start listening
     */
    @Override
    public void run() {
        this.app.startChatting();
        while (true) {
            try {
                String message = "";
                try {
                    message = this.is.readUTF();
                    System.out.println("Received: " + message);
                    message = "Incoming: " + message;
                    this.app.addMessage(message);
                } catch(java.io.EOFException ex) {
                    this.app.disconnect();
                    break;
                } catch(java.net.SocketException ex) {
                    this.app.disconnect();
                    break;
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void send(String message) {
        try {
            this.os.writeUTF(message);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void disconnect() {
        try {
            this.is.close();
            this.os.close();
            this.socket.close();
            this.interrupt();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
