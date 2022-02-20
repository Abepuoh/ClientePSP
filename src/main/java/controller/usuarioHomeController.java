package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Usuario;
import utils.UsuarioSingleton;

public class usuarioHomeController {

    @FXML
    private ImageView ImageViewSalir;

    @FXML
    private Button buttGestion;

    @FXML
    private Button buttVerSaldo;

    @FXML
    void IngresarSacarDInero(ActionEvent event) {
     	FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("IngresarSacarDinero.fxml"));
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

    @FXML
    void Salir(MouseEvent event) {
		Stage stage = (Stage) ImageViewSalir.getScene().getWindow();
		stage.close();
    }

    @FXML
    void verSaldo(ActionEvent event) {
     	FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("verSaldo.fxml"));
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
