package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Paquete;
import model.Usuario;

public class loginController {

	@FXML
	private Button buttAdmin;

	@FXML
	private Button buttCliente;

	@FXML
	private TextField txtContrasena;

	@FXML
	private TextField txtUsuario;

	@FXML
	void logInAdmin(ActionEvent event) {
		try {
			Socket socket = new Socket("localhost", 9999); // conecta con el servidor en el puerto 9999
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			Usuario usuario = new Usuario(1L, "Pedro", "Pastor", "test@test.com", "123445");
			Paquete p1 = new Paquete<>();
			p1.setCantidad(1);
			p1.setObjeto(usuario);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void logInCliente(ActionEvent event) {

	}

}
