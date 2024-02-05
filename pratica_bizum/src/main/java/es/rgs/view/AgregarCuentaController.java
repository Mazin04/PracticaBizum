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

/**
 * Controlador para la vista de agregar cuenta.
 * Maneja la lógica y la interacción con la interfaz de usuario relacionada con la creación de cuentas.
 * Extiende de la clase ViewController.
 * 
 * @author Rubén García
 * @version 1.0
 */
public class AgregarCuentaController extends ViewController {

    @FXML
    private Button btnAgregar;

    @FXML
    private TextField txfDinero;

    @FXML
    private TextField txfNumeroCuenta;

    /**
     * Método para manejar el evento de agregar una nueva cuenta.
     * 
     * @param event Evento de clic del ratón.
     */
    @FXML
    void agregarCuenta(MouseEvent event) {
        try {
            // Validaciones para los campos de entrada
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
                // Obtener datos de los campos de entrada
                BigInteger numCuenta = new BigInteger(txfNumeroCuenta.getText());
                Double dinero = Double.valueOf(txfDinero.getText());
                // Validar si el número de cuenta ya existe
                if (bizumController.comprobarCuenta(numCuenta)) {
                    mostrarAviso("El número de cuenta ya existe", "Error", AlertType.ERROR);
                    setResult(false);
                } else {
                    // Agregar la cuenta y mostrar mensaje de éxito
                    bizumController.agregarCuenta(SingleUsuario.getUsuario(), numCuenta, dinero);
                    txfDinero.setText("");
                    txfNumeroCuenta.setText("");
                    mostrarAviso("Se ha creado correctamente su cuenta", "Creación de cuenta", AlertType.INFORMATION);
                    // Cerrar la ventana actual
                    Stage stage = (Stage) btnAgregar.getScene().getWindow();
                    stage.close();
                    setResult(true);
                }
            }
        } catch (Exception e) {
            // Manejar excepciones relacionadas con valores numéricos
            e.printStackTrace();
            mostrarAviso("Deben introducirse valores numéricos", "Error", AlertType.ERROR);
        }
    }
    
    /**
     * Método para mostrar un aviso utilizando un cuadro de diálogo.
     * 
     * @param msgTexto Mensaje a mostrar en el aviso.
     * @param titulo Título del cuadro de diálogo.
     * @param tipo Tipo de aviso (INFORMATION, ERROR, etc.).
     */
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
