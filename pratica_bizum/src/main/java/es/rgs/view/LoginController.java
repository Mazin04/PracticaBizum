package es.rgs.view;

import es.rgs.controller.BizumController;
import es.rgs.model.entities.SingleUsuario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controlador para la vista de inicio de sesión y registro.
 * Maneja la lógica y la interacción con la interfaz de usuario relacionada con el inicio de sesión y registro.
 * Extiende de la clase ViewController.
 * 
 * @author Rubén García
 * @version 1.0
 */
public class LoginController extends ViewController {
    private boolean modoInicioSesion = true;

    @FXML
    private Button btnInicioSesion;

    @FXML
    private Label lblContraseña;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblRegistrate;

    @FXML
    private Label lblTelefono;

    @FXML
    private Label lblUsuario;

    @FXML
    private ToggleButton toggleContraseña;

    @FXML
    private PasswordField txfContraseña;

    @FXML
    private TextField txfNombre;

    @FXML
    private TextField txfTelefono;

    @FXML
    private TextField txfUsuario;

    /**
     * Método de inicialización que se ejecuta después de cargar la interfaz de usuario
     */
     @FXML
    void initialize() {
        // Añadir un listener para detectar cambios en el campo de contraseña
        txfContraseña.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Si el botón de mostrar contraseña está seleccionado, deseleccionarlo
                if (toggleContraseña.isSelected()) {
                    toggleContraseña.setSelected(false);
                }
            }
        });
    }

    /**
     * Método para mostrar u ocultar la contraseña según el estado del botón de alternar contraseña
    */
     @FXML
    void mostrarContraseña(MouseEvent event) {
        if (toggleContraseña.isSelected()) {
            // Mostrar la contraseña y limpiar el campo de texto
            txfContraseña.setPromptText(txfContraseña.getText());
            txfContraseña.setText("");
        } else {
            // Ocultar la contraseña y restaurar el valor original
            txfContraseña.setText(txfContraseña.getPromptText());
        }
    }

    /**
     * Método para cambiar entre el modo de inicio de sesión y el modo de registro
     */
    @FXML
    void cambiarRegistro(MouseEvent event) {
        // Alternar entre inicio de sesión y registro
        modoInicioSesion = !modoInicioSesion;
        // Mostrar u ocultar elementos según el modo
        lblNombre.setVisible(!modoInicioSesion);
        lblTelefono.setVisible(!modoInicioSesion);
        txfNombre.setVisible(!modoInicioSesion);
        txfTelefono.setVisible(!modoInicioSesion);
    
        // Actualizar el texto del botón y la etiqueta
        btnInicioSesion.setText(modoInicioSesion ? "Iniciar sesión" : "Registrarse");
        lblRegistrate.setText(modoInicioSesion ? "¿No tienes cuenta? Regístrate" : "¿Ya tienes cuenta? Inicia sesión");
    }
    /**
     * Método para manejar el evento de inicio de sesión o registro
     * @param event
     */
    @FXML
    void iniciarSesion(MouseEvent event) {
        if (modoInicioSesion) {
            String usuario = txfUsuario.getText();
            String contraseña = txfContraseña.getText();

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                mostrarAviso("Debes rellenar todos los campos.", "Error", AlertType.ERROR);
            } else {
                if (bizumController.iniciarSesion(usuario, contraseña)) {
                    String nombre = bizumController.getNombre(usuario);
                    SingleUsuario.setUsuario(usuario);
                    SingleUsuario.setTelefono(bizumController.getTelefono(usuario));
                    bizumController.getCuentas(usuario);
                    mostrarAviso("Bienvenido " + nombre, "Bienvenido", AlertType.INFORMATION);
                    cambiarVentana(Vistas.VIEW_MENU);
                } else {
                    mostrarAviso("Usuario o contraseña incorrectos.", "Error", AlertType.ERROR);
                }
            }
        } else {
            String usuario = txfUsuario.getText();
            String contraseña = txfContraseña.getText();
            String nombre = txfNombre.getText();
            String telefono = txfTelefono.getText();
            if (usuario.isEmpty() || contraseña.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
                mostrarAviso("Debes rellenar todos los campos.", "Error", AlertType.ERROR);
            } else if (telefono.length() != 9) {
                mostrarAviso("El teléfono debe tener 9 dígitos.", "Error", AlertType.ERROR);
            } else {
                if (bizumController.comprobarUsuario(usuario)) {
                    mostrarAviso("Ya existe un usuario con ese nombre.", "Error", AlertType.ERROR);
                } else if (bizumController.comprobarRegistroTelefono(telefono)) {
                    mostrarAviso("Ya existe un usuario con ese teléfono.", "Error", AlertType.ERROR);
                } else {
                    try {
                        Integer numTelefono = Integer.parseInt(telefono);
                        bizumController.registrarUsuario(usuario, contraseña, nombre, numTelefono);
                        SingleUsuario.setUsuario(usuario);
                        SingleUsuario.setTelefono(telefono);
                        bizumController.getCuentas(usuario);
                        mostrarAviso("Usuario registrado correctamente.", "Registro", AlertType.INFORMATION);
                        cambiarVentana(Vistas.VIEW_MENU);
                    } catch (Exception e) {
                        mostrarAviso("El teléfono debe ser un número.", "Error", AlertType.ERROR);
                    }
                }
            }

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

    private void cambiarVentana(Vistas vista) {
        try {
            bizumController.cargarVista(vista.getRuta());
        } catch (Exception e) {
            mostrarAviso("Ha ocurrido un error cambiando de ventana.", "Error", Alert.AlertType.ERROR);
        }
    }
}
