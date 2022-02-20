package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Cuenta;
import model.Paquete;
import utils.UsuarioSingleton;

public class verSaldoController {

    @FXML
    private ComboBox<Cuenta> CBCuentas;

    @FXML
    private ImageView ImageViewSalir;
	
    @FXML
    private TableView<Cuenta> TBVerCuentas;
	
    @FXML
    private TableColumn<Cuenta, String> TBSaldo;

    @FXML
    private TableColumn<Cuenta, Double> TCNumero;

	public Socket socket;
	public OutputStream outputStream;
	public ObjectOutputStream oos;
	public ObjectInputStream ois;
	
	@FXML
	public void initialize() {
		try {
			socket = new Socket("localhost", 9999);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			Paquete<Object> escribir = new Paquete<>();
			escribir.setOpcion(2);
			escribir.setObjeto(UsuarioSingleton.getInstance());
			oos.writeObject(escribir);
			oos.flush();
			Paquete<Cuenta> leer = (Paquete<Cuenta>) ois.readObject();
			CBCuentas.getItems().addAll(leer.getObjeto());
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
    @FXML
    void Salir(MouseEvent event) {
		ImageViewSalir.getScene().getWindow().hide();
    }

    @FXML
    void mostrarSaldo(ActionEvent event) {
		//seteamos los valores de la cuenta en la tabla
		Cuenta cuenta = CBCuentas.getValue();
		
    }
}
