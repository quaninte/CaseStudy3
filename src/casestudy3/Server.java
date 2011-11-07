/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package casestudy3;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 *
 * @author quanmt
 */
public class Server extends Thread {
    
    protected App app;
    protected int port;

    public Server(App app, int port) {
        this.app = app;
        this.port = port;
    }
    
    @Override
    public void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            this.app.changeStatus("Waiting for connection ...");
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept();
            this.app.changeStatus("Client connected");
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // create client
        Client client = new Client(socket, this.app);
        this.app.setClient(client);
        client.start();
        this.app.setServerSocket(serverSocket);
    }
    
}
