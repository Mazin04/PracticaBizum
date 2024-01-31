package es.rgs.view;

import java.math.BigInteger;

import es.rgs.controller.BizumController;
import es.rgs.model.entities.SingleUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AgregarCuentaController extends ViewController {

    @FXML
    private Button btnAgregar;

    @FXML
    private TextField txfDinero;

    @FXML
    private TextField txfNumeroCuenta;

    @FXML
    void agregarCuenta(MouseEvent event) {
        try {
            if (txfNumeroCuenta.getText().isEmpty() || txfDinero.getText().isEmpty()) {
                mostrarAviso("Deben rellenarse todos los campos", "Error", AlertType.ERROR);
                setResult(false);
            } else if (txfNumeroCuenta.getText().length() != 12) {
                mostrarAviso("El número de cuenta debe tener 12 dígitos", "Error", AlertType.ERROR);
                setResult(false);
            } else if (txfDinero.getText().length() > 6) {
                mostrarAviso("El dinero debe tener menos de 6 dígitos", "Error", AlertType.ERROR);
                setResult(false);
            } else if (Double.valueOf(txfDinero.getText()) < 0){
                mostrarAviso("El dinero debe ser positivo", "Error dinero", AlertType.ERROR);
                setResult(false);
            } else {
                BigInteger numCuenta = new BigInteger(txfNumeroCuenta.getText());
                Double dinero = Double.valueOf(txfDinero.getText());
                if (bizumController.comprobarCuenta(numCuenta)) {
                    mostrarAviso("El número de cuenta ya existe", "Error", AlertType.ERROR);
                    setResult(false);
                } else {
                    bizumController.agregarCuenta(SingleUsuario.getUsuario(), numCuenta, dinero);
                    txfDinero.setText("");
                    txfNumeroCuenta.setText("");
                    mostrarAviso("Se ha creado correctamente su cuenta", "Creación de cuenta", AlertType.INFORMATION);
    
                    Stage stage = (Stage) btnAgregar.getScene().getWindow();
                    stage.close();
                    setResult(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAviso("Deben introducirse valores numéricos", "Error", AlertType.ERROR);
        }
    }

    public void mostrarAviso(String msgTexto, String titulo, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setTitle(titulo);
        alerta.setContentText(msgTexto);

        ImageView imageView = new ImageView(new Image(BizumController.LOGO));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        alerta.setGraphic(imageView);

        Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(BizumController.LOGO));

        alerta.showAndWait();
    }

}
