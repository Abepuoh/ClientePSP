package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientManager {
	private String server;
	private int port;

	private Object obj;
	private Socket socket = null;

	private ObjectInputStream inputStream = null;
	private ObjectOutputStream outputStream = null;

	public ClientManager(String server, int port) {
		this.server = server;
		this.port = port;
	}

	public void sendObjectToServer(Object obj) {
		try{
			this.socket = new Socket(server, port);
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(obj);

			outputStream.flush();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public Object getObjectFromServer() {
			try {
				System.out.println("Linea antes del input");
				inputStream = new ObjectInputStream(socket.getInputStream());
				System.out.println("Linea despues del input");
				obj = inputStream.readObject();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return obj;

	}
}