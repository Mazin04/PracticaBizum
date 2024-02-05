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
 * Controlador para la vista de sacar dinero en la aplicación Bizum.
 * Permite al usuario sacar dinero de una de sus cuentas.
 */
public class SacarDineroController extends ViewController{
    @FXML
    private Button btnSacar;

    @FXML
    private MenuButton mbCuenta;

    @FXML
    private TextField txfDinero;

    /**
     * Inicializa el controlador.
     * Llena el menú desplegable con las cuentas disponibles del usuario.
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
     * Maneja el evento de sacar dinero.
     * Extrae la cantidad especificada de dinero de la cuenta seleccionada.
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
            } else if (Double.valueOf(txfDinero.getText()) < 0){
                mostrarAviso("El dinero debe ser positivo", "Error dinero", Alert.AlertType.ERROR);
                setResult(false);
            } else if (Double.valueOf(txfDinero.getText()) > bizumController.getDinero(mbCuenta.getText())){
                mostrarAviso("No se puede sacar más dinero del que hay en la cuenta", "Error dinero", Alert.AlertType.ERROR);
                setResult(false);
            } else {
                Double dinero = Double.valueOf(txfDinero.getText());
                bizumController.sacarDinero(mbCuenta.getText(), dinero);
                txfDinero.setText("");
                mostrarAviso("Se ha sacado correctamente el dinero", "Sacar dinero", Alert.AlertType.INFORMATION);
                Stage stage = (Stage) btnSacar.getScene().getWindow();
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
