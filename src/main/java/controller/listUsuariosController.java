package controller;

import java.io.IOException;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Administrador;
import model.ClientManager;
import model.Paquete;
import model.Usuario;
import utils.AdministradorSingleton;

public class listUsuariosController {

	@FXML
	private ImageView Volver;

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

	public static Usuario usuarioS;
	private ObservableList<Usuario> listaUsuarios;

	@FXML
	public void initialize() {
		ClientManager cm = new ClientManager("localhost", 9999);
		Paquete<Administrador> escribir = new Paquete<>();
		Administrador admin = AdministradorSingleton.getInstance().getAdmin();
		escribir.setOpcion(7);
		escribir.setObjeto(admin);
		cm.sendObjectToServer(escribir);
		Object leer = cm.getObjectFromServer();
		Paquete<List<Usuario>> a = (Paquete<List<Usuario>>) leer;
		List<Usuario> lista = a.getObjeto();
		listaUsuarios = FXCollections.observableArrayList(lista);
		TVUsuarios.setItems(listaUsuarios);
		TCApellidos.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getApellidos()));
		TCNombre.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNombre()));
		TCCorreo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCorreo()));
		// cuando hagamos doble click en una fila, se abrira una ventana con para crear
		// una cuenta y le pasamos el usuario seleccionado
		TVUsuarios.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				Usuario usuario =TVUsuarios.getSelectionModel().getSelectedItem();
				if (usuario != null) {
					crearCuentaController.setUsuario(TVUsuarios.getSelectionModel().getSelectedItem());
					FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("createCuenta.fxml"));
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
		});
	}

	@FXML
	void volver(MouseEvent event) {
		this.Volver.getScene().getWindow().hide();
	}
}
