package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Administrador;
import model.ClientManager;
import model.Paquete;
import model.Usuario;
import utils.AdministradorSingleton;

public class createUsuarioController {

    @FXML
    private ImageView IMvolver;

    @FXML
    private TextField TFApellidos;

    @FXML
    private TextField TFContrasena;

    @FXML
    private TextField TFEmail;

    @FXML
    private TextField TFNombre;

    @FXML
    private Button buttCrear;

    
    @FXML
    void crearUsuario(ActionEvent event) {
        ClientManager cm = new ClientManager("localhost", 9999);
        String nombre = TFNombre.getText();
        String apellidos = TFApellidos.getText();
        String email = TFEmail.getText();
        String contrasena = TFContrasena.getText();

        if(nombre==null && apellidos==null && email==null && contrasena==null){
            utils.Dialog.showError("Error", "Debe ingresar nombre, apellidos, email y contrasena",
                "Debe ingresar nombre, apellidos, email y contrasena correcto y no vacio");
        }else{
            try{
                Paquete<Usuario> paquete = new Paquete();
                Administrador admin = AdministradorSingleton.getInstance().getAdmin();
                paquete.setOpcion(5);
                paquete.setObjeto(new Usuario(nombre, apellidos, email, contrasena, admin));
                System.out.println(paquete.getObjeto().toString());
                cm.sendObjectToServer(paquete);
                // leemos la respuesta del servidor
                Object leer = cm.getObjectFromServer();
                Paquete<Usuario> usuarioAux = (Paquete<Usuario>) leer;
                if(usuarioAux.getResultado()){
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
