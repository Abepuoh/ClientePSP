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
import model.ClientManager;
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
		ClientManager cm = new ClientManager("localhost", 9999);
		String contrasena = this.txtContrasena.getText();
		String usuario = this.txtUsuario.getText();

		if (contrasena.isEmpty() & usuario.isEmpty()) {
			utils.Dialog.showError("Error", "Debe ingresar nombre de usuario y contraseña",
					"Debe ingresar usuario y contraseña que se encuentran en la base de datos");
		} else {
			Paquete<Object> escribir = new Paquete<>();
			escribir.setOpcion(12);
			//escribir.setObjeto(new Administrador(usuario, contrasena));
			cm.sendObjectToServer(escribir);
			System.out.println("hola");
			// leemos la respuesta del servidor
			Object leer = cm.getObjectFromServer();
			System.out.println(leer.toString());
			Paquete<Administrador> a = (Paquete<Administrador>) leer;
			System.out.println(a.getResultado());
			if (a.getResultado()) {
				AdministradorSingleton administradorSignleton = AdministradorSingleton.getInstance();
				administradorSignleton.setAdmin((Administrador) a.getObjeto());
				try {
					App.setRoot("adminHome");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				utils.Dialog.showError("Error", "No se encontro el usuario",
						"No se encontro el usuario, por favor intente de nuevo con un usuario valido");
			}
		}
	}

	@FXML
	void logInCliente(ActionEvent event) {
		ClientManager cm = new ClientManager("localhost", 9999);
		String contrasena = this.txtContrasena.getText();
		String usuario = this.txtUsuario.getText();

		if (contrasena.isEmpty() & usuario.isEmpty()) {
			utils.Dialog.showError("Error", "Debe ingresar nombre de usuario y contraseña",
					"Debe ingresar usuario y contraseña que se encuentran en la base de datos");
		} else {
			Paquete<Usuario> escribir = new Paquete<>();
			escribir.setOpcion(11);
			escribir.setObjeto(new Usuario(usuario, contrasena));
			cm.sendObjectToServer(escribir);
			System.out.println("Entro en logInCliente");
			// leemos la respuesta del servidor
			Object leer = cm.getObjectFromServer();
			System.out.println(leer.toString());
			Paquete<Usuario> a = (Paquete<Usuario>) leer;
			System.out.println(a.getResultado());
			if (a.getResultado()) {
				UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
				a.getObjeto().setId((long) a.getCantidad());
				usuarioSingleton.setUser(a.getObjeto());
				System.out.println(usuarioSingleton.getUser());
				try {
					App.setRoot("usuarioHome");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				utils.Dialog.showError("Error", "No se encontro el usuario",
						"No se encontro el usuario, por favor intente de nuevo con un usuario valido");
			}
		}
	}

}
