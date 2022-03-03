package controller;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.ClientManager;
import model.Cuenta;
import model.Paquete;

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
    private TableColumn<Cuenta, String> TCUsuario;

    @FXML
    private Button buttBorrarCuenta;

    @FXML
	public void initialize() {
        //disable button hasta que se seleccione una cuenta
        buttBorrarCuenta.setDisable(true);
		ClientManager cm = new ClientManager("localhost", 9999);
        Paquete<Cuenta> escribir = new Paquete<>();
        escribir.setOpcion(10);
        cm.sendObjectToServer(escribir);
        Object leer = cm.getObjectFromServer();
        Paquete<List<Cuenta>> a = (Paquete<List<Cuenta>>) leer;
        List<Cuenta> lista = a.getObjeto();
        ObservableList<Cuenta> listaCuentas = FXCollections.observableArrayList(lista);
        TCCuentas.setItems(listaCuentas);
        TCNumero.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNumero()));
        TCSaldo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSaldo()));
        TCUsuario.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUsuario().getNombre()));
        TCCuentas.setOnMouseClicked((MouseEvent event) -> {
            Cuenta cuenta = TCCuentas.getSelectionModel().getSelectedItem();
            if (cuenta != null) {
                buttBorrarCuenta.setDisable(false);
            }
        });
    }
    
    @FXML
    void volver(MouseEvent event) {
        IMVolver.getScene().getWindow().hide();
    }
    
    @FXML
    void borrarCuenta(ActionEvent event) {
       ClientManager cm = new ClientManager("localhost", 9999);
       Cuenta cuenta = TCCuentas.getSelectionModel().getSelectedItem();
       Long id = TCCuentas.getSelectionModel().getSelectedItem().getId();
       Paquete<Long> escribir = new Paquete<>();
       escribir.setOpcion(9);
       escribir.setObjeto(id);
       cm.sendObjectToServer(escribir);
       Object leer = cm.getObjectFromServer();
       Paquete<Cuenta> a = (Paquete<Cuenta>) leer;
       if(a.getResultado()){
           utils.Dialog.showConfirm("Cuenta borrada", "La cuenta se ha borrado correctamente", null);
           TCCuentas.getItems().remove(cuenta);
       }else{
        utils.Dialog.showError("Error", "No se pudo borrar la cuenta", null);
       }
    }
}
