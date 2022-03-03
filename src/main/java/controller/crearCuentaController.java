package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.ClientManager;
import model.Cuenta;
import model.Paquete;
import model.Usuario;

public class crearCuentaController {

  
    @FXML
    private ImageView IMvolver;

    @FXML
    private TextField TFSaldo;

    @FXML
    private Button buttCrear;

    @FXML
    private TextField txtNumeroCuenta; 


    static Usuario usuarioCuenta = new Usuario();
	@FXML
	public void initialize() {
		System.out.println(usuarioCuenta);
    }
    @FXML
    void creaCuenta(ActionEvent event) {
        //enviamos la cuenta al servidor
        Double saldo = Double.parseDouble(TFSaldo.getText());
        String numeroCuenta = txtNumeroCuenta.getText();
        ClientManager cm = new ClientManager("localhost", 9999);
        Paquete<Cuenta> escribir = new Paquete<>();
        escribir.setOpcion(6);
        escribir.setObjeto(new Cuenta(numeroCuenta, saldo, usuarioCuenta));
        cm.sendObjectToServer(escribir); 
        Object leer = cm.getObjectFromServer();
        Paquete<Cuenta> a = (Paquete<Cuenta>) leer;
        if(a.getResultado()){
            utils.Dialog.showDialog(Alert.AlertType.INFORMATION, "Cuenta creada", "Cuenta creada con exito", "La cuenta se creo con exito");
        }
        else{
            utils.Dialog.showError("Error", "No se pudo crear la cuenta", "El numero de cuenta ya existe");
        }
    }

    @FXML
    void volver(MouseEvent event) {
        IMvolver.getScene().getWindow().hide();
    }
    public static void setUsuario(Usuario usuario){
        usuarioCuenta = usuario;
    }


}
