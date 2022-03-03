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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Paquete;
import model.Usuario;

public class createUsuarioController {

    @FXML
    private ImageView IMvolver;

    @FXML
    private TextField TFApellidos;

    @FXML
    private TextField TFContraseña;

    @FXML
    private TextField TFEmail;

    @FXML
    private TextField TFNombre;

    @FXML
    private Button buttCrear;

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
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    @FXML
    void crearUsuario(ActionEvent event) {
        String nombre = TFNombre.getText();
        String apellidos = TFApellidos.getText();
        String email = TFEmail.getText();
        String contrasena = TFContraseña.getText();

        if(nombre==null && apellidos==null && email==null && contrasena==null){
            utils.Dialog.showError("Error", "Debe ingresar nombre, apellidos, email y contrasena",
                "Debe ingresar nombre, apellidos, email y contrasena correcto y no vacio");
        }else{
            try{
                Paquete<Usuario> paquete = new Paquete();
                paquete.setOpcion(5);
                paquete.setObjeto(new Usuario(null, nombre, apellidos, email, contrasena, null, null));
                oos.writeObject(paquete);
                oos.flush();
                paquete = (Paquete) ois.readObject();
                if(paquete.getResultado()){
                    utils.Dialog.showConfirm("Confirmacion", "Usuario creado correctamente",
                        "Usuario creado correctamente");
                }else{
                    utils.Dialog.showError("Error", "Usuario no creado",
                        "Usuario no creado");
                }
            }catch(Exception e){
                utils.Dialog.showError("Error", "No se pudo conectar con el servidor",
                    "No se pudo conectar con el servidor");
            }
        }
    }

    @FXML
    void volver(MouseEvent event) {
        IMvolver.getScene().getWindow().hide();
    }

}
