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
import model.Administrador;
import model.Paquete;
import model.Usuario;
import utils.AdministradorSingleton;
import utils.UsuarioSingleton;

public class loginController {

	@FXML
	private Button buttAdmin;

	@FXML
	private Button buttCliente;

	@FXML
	private TextField txtContrasena;

	@FXML
	private TextField txtUsuario;

	public Socket socket;
	public OutputStream outputStream;
	public ObjectOutputStream oos;

	@FXML
	public void initialize() {
		try {
			socket = new Socket("localhost", 9999);
			outputStream = socket.getOutputStream();
			oos = new ObjectOutputStream(outputStream);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void logInAdmin(ActionEvent event) {
		String contrasena = txtContrasena.getText();
		String usuario = txtUsuario.getText();

		if (contrasena == null && usuario == null) {
			utils.Dialog.showError("Error", "Debe ingresar nombre de usuario y contraseña",
			 "Debe ingresar usuario y contraseña que se encuentran en la base de datos");
		} else {
			try {
				Paquete<Object> escribir = new Paquete<>();
				escribir.setOpcion(12);
				escribir.setObjeto(new Usuario(usuario, contrasena));
				oos.writeObject(escribir);
				// leemos la respuesta del servidor
				socket.close();
				Paquete leer = new Paquete<>();
				if (leer.getResultado()) {
					UsuarioSingleton usuarioSignleton = UsuarioSingleton.getInstance();
					usuarioSignleton.setUser((Usuario)leer.getObjeto());
					App.setRoot("adminHome");
				} else {
					utils.Dialog.showError("Error", "No se encontro el Administrador", 
					"No se encontro el administrador, por favor intente de nuevo con un administrador valido");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void logInCliente(ActionEvent event) {
		String contrasena = txtContrasena.getText();
		String usuario = txtUsuario.getText();

		if (contrasena == null && usuario == null) {
			utils.Dialog.showError("Error", "Debe ingresar nombre de administrador y contraseña",
			 "Debe ingresar administrador y contraseña que se encuentran en la base de datos");
		} else {
			try {
				Paquete<Object> escribir = new Paquete<>();
				escribir.setOpcion(11);
				escribir.setObjeto(new Administrador(usuario, contrasena));
				oos.writeObject(escribir);
				// leemos la respuesta del servidor
				socket.close();
				Paquete leer = new Paquete<>();
				if (leer.getResultado()) {
					AdministradorSingleton administradorSignleton = AdministradorSingleton.getInstance();
					administradorSignleton.setAdmin((Administrador)leer.getObjeto());
					App.setRoot("userHome");
				} else {
					utils.Dialog.showError("Error", "No se encontro el usuario", 
					"No se encontro el usuario, por favor intente de nuevo con un usuario valido");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
