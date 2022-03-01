package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
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

	
	public OutputStream outputStream;
	public ObjectOutputStream oos;
	public ObjectInputStream ois;

	@FXML
	void logInAdmin(ActionEvent event) {
		try {
		Socket socket1 = new Socket("localhost", 9999);
		ObjectOutputStream oos1 = new ObjectOutputStream(socket1.getOutputStream());
		ObjectInputStream ois1 = new ObjectInputStream(socket1.getInputStream());
	
		String contrasena = this.txtContrasena.getText();
		String usuario =this.txtUsuario.getText();
		
		if (contrasena.isEmpty() & usuario.isEmpty()) {
			utils.Dialog.showError("Error", "Debe ingresar nombre de usuario y contraseña",
			 "Debe ingresar usuario y contraseña que se encuentran en la base de datos");
		} else {
				Paquete<Object> escribir = new Paquete<>();
				escribir.setOpcion(12);
				escribir.setObjeto(new Usuario(usuario, contrasena));
				oos1.writeObject(escribir);
				oos1.flush();
				// leemos la respuesta del servidor
				Paquete<Object> leer = (Paquete<Object>) ois1.readObject();
				if (leer.getResultado()) {
					UsuarioSingleton usuarioSignleton = UsuarioSingleton.getInstance();
					usuarioSignleton.setUser((Usuario)leer.getObjeto());
					App.setRoot("adminHome");
				} else {
					utils.Dialog.showError("Error", "No se encontro el Administrador", 
					"No se encontro el administrador, por favor intente de nuevo con un administrador valido");
				}
				socket1.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}

	@FXML
	void logInCliente(ActionEvent event) {
		String contrasena = txtContrasena.getText();
		String usuario = txtUsuario.getText();

		if (contrasena.isEmpty() & usuario.isEmpty()) {
			utils.Dialog.showError("Error", "Debe ingresar nombre de administrador y contraseña",
			 "Debe ingresar administrador y contraseña que se encuentran en la base de datos");
		} else {
			try {
				Paquete<Object> escribir = new Paquete<>();
				escribir.setOpcion(11);
				escribir.setObjeto(new Administrador(usuario, contrasena));
				oos.writeObject(escribir);
				oos.flush();
				Paquete<Object> leer = (Paquete<Object>) ois.readObject();
				if (leer.getResultado()) {
					AdministradorSingleton administradorSignleton = AdministradorSingleton.getInstance();
					administradorSignleton.setAdmin((Administrador)leer.getObjeto());
					App.setRoot("usuarioHome");
				} else {
					utils.Dialog.showError("Error", "No se encontro el usuario", 
					"No se encontro el usuario, por favor intente de nuevo con un usuario valido");
				}
					//close
			} catch (IOException e) {
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

}
