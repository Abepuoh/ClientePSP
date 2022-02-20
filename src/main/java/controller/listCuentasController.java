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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Cuenta;
import model.Paquete;
import model.Usuario;

public class listCuentasController {

    @FXML
    private ImageView IMVolver;

    @FXML
    private TableView<Cuenta> TCCuentas;

    @FXML
    private TableColumn<Cuenta, String> TCNumero;

    @FXML
    private TableColumn<Cuenta, Double> TCSaldo;

    @FXML
    private TableColumn<Cuenta, Usuario> TCUsuario;

    @FXML
    private Button buttBorrarCuenta;

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
            //Enviamos el paquete con el tipo de operacion
            Paquete paquete = new Paquete();
            paquete.setOpcion(2);
            oos.writeObject(paquete);
            oos.flush();
            //Recibimos la lista de usuarios
            paquete = (Paquete) ois.readObject();
            if(paquete.getResultado()){
                //SETEAMOS LAS COSAS EN LOS COSOS
            }else{
                utils.Dialog.showError("Error", "No se pudo obtener la lista de cuentas", "");
            }
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
    @FXML
    void volver(MouseEvent event) {
        IMVolver.getScene().getWindow().hide();
    }
    
    @FXML
    void borrarCuenta(ActionEvent event) {
        //seleccionar la cuenta a borrar
        Cuenta cuenta = TCCuentas.getSelectionModel().getSelectedItem();
        if(cuenta != null){
            //Enviamos el paquete con el tipo de operacion
            Paquete paquete = new Paquete();
            paquete.setOpcion(9);
            paquete.setObjeto(cuenta);
            try {
                oos.writeObject(paquete);
                oos.flush();
                //Recibimos la lista de usuarios
                paquete = (Paquete) ois.readObject();
                if(paquete.getResultado()){
                    //SETEAMOS LAS COSAS EN LOS COSOS
                }else{
                    utils.Dialog.showError("Error", "No se pudo borrar la cuenta", "");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            utils.Dialog.showError("Error", "No se selecciono ninguna cuenta", "");
        }
    }
}
