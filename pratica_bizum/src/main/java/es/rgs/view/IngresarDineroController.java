package es.rgs.view;

import java.util.List;

import es.rgs.controller.BizumController;
import es.rgs.model.entities.Cuenta;
import es.rgs.model.entities.SingleUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controlador para la vista de ingreso de dinero en una cuenta.
 * Maneja la lógica y la interacción con la interfaz de usuario relacionada con el ingreso de dinero.
 * Extiende de la clase ViewController.
 * 
 * @author Rubén García
 * @version 1.0
 */
public class IngresarDineroController extends ViewController {

    @FXML
    private Button btnIngresar;

    @FXML
    private MenuButton mbCuenta;

    @FXML
    private TextField txfDinero;
    
    /**
     * Método de inicialización que se ejecuta después de cargar la interfaz de usuario.
     * Carga las cuentas asociadas al usuario y realiza configuraciones iniciales.
     */
    @FXML
    void initialize() {
        mbCuenta.getItems().clear();
        List<Cuenta> cuentas = SingleUsuario.getCuentas();
        for (Cuenta cuenta : cuentas) {
            MenuItem menuItem = new MenuItem(cuenta.getNumeroCuenta().toString());
            menuItem.setOnAction(e -> {
                mbCuenta.setText(menuItem.getText());
            });
            mbCuenta.getItems().add(menuItem);
        }
    }

    /**
     * Método para manejar el evento de ingresar dinero en una cuenta.
     * 
     * @param event Evento de clic del ratón.
     */
    @FXML
    void sacarDinero(MouseEvent event) {
        try {
            if (txfDinero.getText().isEmpty()) {
                mostrarAviso("Deben rellenarse todos los campos", "Error", Alert.AlertType.ERROR);
                setResult(false);
            } else if (mbCuenta.getText().equals("Número de cuenta")) {
                mostrarAviso("Debes elegir un número de cuenta", "Error", AlertType.ERROR);
            } else if (txfDinero.getText().length() > 6) {
                mostrarAviso("El dinero debe tener menos de 6 dígitos", "Error", Alert.AlertType.ERROR);
                setResult(false);
            } else if (Double.valueOf(txfDinero.getText()) < 0) {
                mostrarAviso("El dinero debe ser positivo", "Error dinero", Alert.AlertType.ERROR);
                setResult(false);
            } else {
                // Obtener el dinero a ingresar
                Double dinero = Double.valueOf(txfDinero.getText());
                // Ingresar dinero en la cuenta
                bizumController.ingresarDinero(mbCuenta.getText(), dinero);
                txfDinero.setText("");
                // Mostrar aviso de ingreso correcto
                mostrarAviso("Se ha ingresado correctamente el dinero", "Ingresar dinero", Alert.AlertType.INFORMATION);
                // Cerrar la ventana
                Stage stage = (Stage) btnIngresar.getScene().getWindow();
                stage.close();
                setResult(true);
            }
        } catch (Exception e) {
            mostrarAviso("Error al sacar el dinero", "Error", Alert.AlertType.ERROR);
            setResult(false);
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
