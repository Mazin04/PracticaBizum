package es.rgs.view;

import java.net.URL;
import java.util.ResourceBundle;

import at.favre.lib.crypto.bcrypt.BCrypt;
import es.rgs.controller.BizumController;
import es.rgs.model.BancoDAO;
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

public class LoginController extends ViewController {

    @FXML
    private Button btnInicioSesion;

    @FXML
    private Label lblContraseña;

    @FXML
    private Label lblRegistrate;

    @FXML
    private Label lblUsuario;

    @FXML
    private PasswordField txfContraseña;

    @FXML
    private TextField txfUsuario;

    @FXML
    private ToggleButton toggleContraseña;
    
    @FXML
    void initialize() {
        txfContraseña.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (toggleContraseña.isSelected()) {
                    toggleContraseña.setSelected(false);
                }
            }
        });
    }

    @FXML
    void mostrarContraseña(MouseEvent event) {
        if (toggleContraseña.isSelected()) {
            txfContraseña.setPromptText(txfContraseña.getText());
            txfContraseña.setText("");
        } else {
            txfContraseña.setText(txfContraseña.getPromptText());
        }
    }

    @FXML
    void cambiarRegistro(MouseEvent event) {
        try {
            bizumController.cargarVista(Vistas.VIEW_REGISTRAR.getRuta());
        } catch (Exception e) {
            mostrarAviso("Ha ocurrido un error cambiando de ventana.", "Error", AlertType.ERROR);
        }
    }

    @FXML
    void iniciarSesion(MouseEvent event) {
        String usuario = txfUsuario.getText();
        String contraseña = txfContraseña.getText();

        if (usuario.isEmpty() || contraseña.isEmpty()) {
            mostrarAviso("Debes rellenar todos los campos.", "Error", AlertType.ERROR);
        } else {
            if (bizumController.iniciarSesion(usuario, contraseña)) {
                String nombre = bizumController.getNombre(usuario);
                mostrarAviso("Bienvenido " + nombre, "Bienvenido", AlertType.INFORMATION);
                try {
                    bizumController.cargarVista(Vistas.VIEW_LOGIN.getRuta());
                } catch (Exception e) {
                    mostrarAviso("Ha ocurrido un error cambiando de ventana.", "Error", AlertType.ERROR);
                }
            } else {
                mostrarAviso("Usuario o contraseña incorrectos.", "Error", AlertType.ERROR);
            }
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

    public String encriptar(String contraseña) {
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, contraseña.toCharArray());
        return bcryptHashString;
    }
}
