package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class adminHomeController {

    @FXML
    private Button buttCrearCliente;

    @FXML
    private Button buttVerClientes;

    @FXML
    private Button buttVerCuentas;

    @FXML
    void crearCliente(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("createUSuario.fxml"));
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
    void verClientes(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("listUsuarios.fxml"));
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
    void verCuentas(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("listCuentas.fxml"));
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
