package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Paquete;
import model.Usuario;

public class listUsuariosController {

    @FXML
    private TableColumn<Usuario, String> TCApellidos;

    @FXML
    private TableColumn<Usuario, String> TCCorreo;

    @FXML
    private TableColumn<Usuario, String> TCNombre;

    @FXML
    private TableView<Usuario> TVUsuarios;

    @FXML
    private Button buttCreateCuenta;

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
            escribir.setOpcion(7);
            oos.writeObject(escribir);
            oos.flush();
            Paquete<Usuario> leer = (Paquete<Usuario>) ois.readObject();
            TVUsuarios.getItems().addAll(leer.getObjeto());
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
    void createCuenta(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("createUsuario.fxml"));
		Parent modal;
		try {
			modal = fxmlLoader.load();
			Stage modalStage = new Stage();
			modalStage.initModality(Modality.APPLICATION_MODAL);
			modalStage.initOwner(App.rootstage);
			Scene modalScene = new Scene(modal);
			modalStage.setScene(modalScene);
			modalStage.showAndWait();
			modalStage.setResizable(false);
		} catch (IOException e) {
			e.printStackTrace();
			utils.Dialog.showError("Error", "Error al cargar la pagina", "");
		}
    }

}

