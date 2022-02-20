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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Cuenta;
import model.Paquete;
import utils.UsuarioSingleton;

public class IngresarSacarDineroController {

    @FXML
    private ComboBox<Cuenta> CBCuentas;

    @FXML
    private ImageView ImageViewSalir;

    @FXML
    private TableColumn<Cuenta,Double> TBSaldo;

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
            Paquete<Object> cuentas = (Paquete<Object>) ois.readObject();
			CBCuentas.getItems().addAll((Cuenta[]) cuentas.getObjeto());
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
    void IngresarDinero(ActionEvent event) {

    }

    @FXML
    void SacarDInero(ActionEvent event) {

    }

    @FXML
    void Salir(MouseEvent event) {
		ImageViewSalir.getScene().getWindow().hide();
    }

    @FXML
    void mostrarSaldo(ActionEvent event) {

    }

}
