package es.rgs.view;

import es.rgs.controller.BizumController;
import es.rgs.model.entities.Cuenta;
import es.rgs.model.entities.SingleUsuario;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controlador para la vista de realizar un Bizum.
 * Maneja la lógica y la interacción con la interfaz de usuario relacionada con
 * la transferencia de dinero mediante Bizum.
 * Extiende de la clase ViewController.
 * 
 * @author Rubén García
 * @version 1.0
 */
public class HacerBizumController extends ViewController {
    @FXML
    private Button btnRealizar;

    @FXML
    private MenuButton mbNCuenta;

    @FXML
    private TextField txfDinero;

    @FXML
    private TextField txfTelefono;

    // Variable para indicar si el usuario está registrado
    private boolean registrado = true;

    /**
     * Método de inicialización que se ejecuta después de cargar la interfaz de
     * usuario.
     * Carga las cuentas asociadas al usuario y realiza configuraciones iniciales.
     */
    @FXML
    void initialize() {
        Platform.runLater(() -> {
            try {
                // Limpiar elementos del menú desplegable
                mbNCuenta.getItems().clear();

                // Verificar si el teléfono del usuario está registrado en Bizum
                if (bizumController.comprobarTelefono(SingleUsuario.getTelefono())) {
                    mbNCuenta.setText(bizumController.getCuentaTelefono(SingleUsuario.getTelefono()));
                } else {
                    // Cargar cuentas asociadas al usuario
                    for (Cuenta cuenta : SingleUsuario.getCuentas()) {
                        MenuItem itemCuenta = new MenuItem(cuenta.getNumeroCuenta().toString());
                        itemCuenta.setOnAction(e -> {
                            mbNCuenta.setText(itemCuenta.getText());
                        });
                        mbNCuenta.getItems().add(itemCuenta);
                    }
                    registrado = false;
                    // Mostrar aviso en caso de no estar registrado
                    mostrarAviso(
                            "No se ha registrado nunca, elija una de sus cuentas, tras hacer el primer bizum, se le asignará el teléfono a su cuenta. \nEn caso de solo querer registrarse, envíe 0 euros al admin (637693140)",
                            "Cuenta Bizum", AlertType.INFORMATION);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAviso("Error al cargar las cuentas", "Error", AlertType.ERROR);
            }
        });
    }

    /**
     * Método para manejar el evento de realizar un Bizum.
     * 
     * @param event Evento de clic del ratón.
     */
    @FXML
    void agregarCuenta(MouseEvent event) {
        try {
            // Validaciones para los campos de entrada
            if (txfTelefono.getText().isEmpty() || txfDinero.getText().isEmpty()) {
                mostrarAviso("Deben rellenarse todos los campos", "Error", AlertType.ERROR);
                setResult(false);
            } else if (txfTelefono.getText().length() != 9) {
                mostrarAviso("El número de teléfono debe tener 9 dígitos", "Error", AlertType.ERROR);
                setResult(false);
            } else if (mbNCuenta.getText().equals("Número de cuenta")) {
                mostrarAviso("Debes elegir un número de cuenta", "Error", AlertType.ERROR);
                setResult(false);
            } else if (txfDinero.getText().length() > 6) {
                mostrarAviso("El dinero debe tener menos de 6 dígitos", "Error", AlertType.ERROR);
                setResult(false);
            } else if (Double.valueOf(txfDinero.getText()) < 0) {
                mostrarAviso("El dinero debe ser positivo", "Error dinero", AlertType.ERROR);
                setResult(false);
            } else if (txfTelefono.getText().equals(SingleUsuario.getTelefono())) {
                mostrarAviso("No se puede hacer un bizum a sí mismo", "Aviso", AlertType.WARNING);
            } else if (Double.valueOf(txfDinero.getText()) > bizumController.getDinero(mbNCuenta.getText())) {
                mostrarAviso("No se puede hacer una transferencia de una cantidad mayor a la que se tiene",
                        "Aviso, dinero", AlertType.WARNING);
            } else {
                // Obtener datos de los campos de entrada
                Double dinero = Double.valueOf(txfDinero.getText());
                String telefono = txfTelefono.getText();
                // Validar si el número de teléfono está registrado
                if (bizumController.comprobarTelefonoExiste(telefono)) {
                    // Registrar el teléfono si no está registrado
                    if (!registrado) {
                        bizumController.registrarBizumUsuario(mbNCuenta.getText());
                    }
                    // Realizar el bizum y mostrar mensaje de éxito
                    bizumController.hacerBizum(telefono, dinero);
                    txfDinero.setText("");
                    txfTelefono.setText("");
                    mostrarAviso("Se ha realizado correctamente el bizum", "Bizum", AlertType.INFORMATION);
                    // Cerrar la ventana actual
                    Stage stage = (Stage) btnRealizar.getScene().getWindow();
                    stage.close();
                    setResult(true);
                } else {
                    mostrarAviso("El número de teléfono no está registrado en Bizum", "Error", AlertType.ERROR);
                    setResult(false);
                }
            }
        } catch (Exception e) {
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
