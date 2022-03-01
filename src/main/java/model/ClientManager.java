package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientManager {
    private String server;
    private int port;

    private ObjectInputStream inputObjectStream = null; 
    private ObjectOutputStream outputObjectStream = null; 

    public ClientManager(String server,int port){
        this.server = server;
        this.port = port;
    }

    public void sendObjectToServer(Object obj){
        try(Socket socket = new Socket(server,port)){

            outputObjectStream = new ObjectOutputStream(socket.getOutputStream());
            outputObjectStream.writeObject(obj);

            outputObjectStream.flush();
            outputObjectStream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Object getObjectFromServer(){
        return new Object();
    }
}