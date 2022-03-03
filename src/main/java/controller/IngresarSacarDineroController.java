package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.ClientManager;
import model.Cuenta;
import model.Paquete;
import model.Usuario;
import utils.UsuarioSingleton;

public class IngresarSacarDineroController {

	@FXML
	private ComboBox<Cuenta> CBCuentas;

	@FXML
	private ImageView ImageViewSalir;

	@FXML
	private TableColumn<Cuenta, String> TBSaldo;

	@FXML
	private TableView<Cuenta> TBVerCuentas;

	@FXML
	private TableColumn<Cuenta, String> TCNumero;

	@FXML
	private TextField TFDinero;

	@FXML
	private Button buttIngresar;

	@FXML
	private Button buttSacar;

	protected static ObservableList<Cuenta> listas = FXCollections.observableArrayList();

	public Socket socket;
	public OutputStream outputStream;
	public ObjectOutputStream oos;
	public ObjectInputStream ois;

	@FXML
	public void initialize() {
		
		setInfo();
	}

	@FXML
	void IngresarDinero(ActionEvent event) {
		String cantidad = this.TFDinero.getText();

		if (cantidad.isEmpty()) {
			utils.Dialog.showError("Error", "Debe ingresar una cantidad", "Debe ingresar una cantidad válida.");
		} else {
			Cuenta tmp = this.TBVerCuentas.getSelectionModel().getSelectedItem();
			List<Cuenta> cuentas = UsuarioSingleton.getInstance().getUser().getCuentas();
			for (Cuenta cuenta : cuentas) {
				if (cuenta.equals(tmp)) {
					cuenta.setSaldo(cuenta.getSaldo() + Double.parseDouble(cantidad));
				}
			}
		}
		ClientManager cm = new ClientManager("localhost", 9999);
		System.out.println(UsuarioSingleton.getInstance().getUser());
		Paquete<Usuario> escribir = new Paquete<>();
		escribir.setOpcion(3);
		escribir.setObjeto(UsuarioSingleton.getInstance().getUser());
		cm.sendObjectToServer(escribir);
		// Leemos la respuesta del servidor
		Object leer = cm.getObjectFromServer();
		Paquete<Usuario> a = (Paquete<Usuario>) leer;
		// Si el usuario devuelto es correcto, lo seteamos al singleton.
		if (a.getResultado()) {
			UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
			usuarioSingleton.setUser(a.getObjeto());
			setInfo();
		}

	}

	@FXML
	void SacarDInero(ActionEvent event) {
		String cantidad = this.TFDinero.getText();

		if (cantidad.isEmpty()) {
			utils.Dialog.showError("Error", "Debe ingresar una cantidad", "Debe ingresar una cantidad válida.");
		} else {
			Cuenta tmp = this.TBVerCuentas.getSelectionModel().getSelectedItem();
			List<Cuenta> cuentas = UsuarioSingleton.getInstance().getUser().getCuentas();
			for (Cuenta cuenta : cuentas) {
				if (cuenta.equals(tmp)) {
					cuenta.setSaldo(cuenta.getSaldo() - Double.parseDouble(cantidad));
				}
			}
		}
		ClientManager cm = new ClientManager("localhost", 9999);
		System.out.println(UsuarioSingleton.getInstance().getUser());
		Paquete<Usuario> escribir = new Paquete<>();
		escribir.setOpcion(3);
		escribir.setObjeto(UsuarioSingleton.getInstance().getUser());
		cm.sendObjectToServer(escribir);
		// Leemos la respuesta del servidor
		Object leer = cm.getObjectFromServer();
		Paquete<Usuario> a = (Paquete<Usuario>) leer;
		// Si el usuario devuelto es correcto, lo seteamos al singleton.
		if (a.getResultado()) {
			UsuarioSingleton usuarioSingleton = UsuarioSingleton.getInstance();
			usuarioSingleton.setUser(a.getObjeto());
			setInfo();
		}

	}

	@FXML
	void Salir(MouseEvent event) {
		ImageViewSalir.getScene().getWindow().hide();
	}

	void setInfo() {
		listas.setAll(UsuarioSingleton.getInstance().getUser().getCuentas());

		this.TBSaldo.setCellValueFactory(tuco -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(tuco.getValue().getSaldo().toString());
			return v;
		});
		;
		this.TCNumero.setCellValueFactory(tuco -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(tuco.getValue().getNumero());
			return v;
		});
		;
		this.TBVerCuentas.setItems(listas);
	}
	
	void updateInfo() {
		
	}

	@FXML
	void mostrarSaldo(ActionEvent event) {
		
	}

}
