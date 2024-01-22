package es.rgs.view;

import com.password4j.Hash;

import es.rgs.controller.BizumController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController extends ViewController{

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

    public String encriptar(String contraseña){
        Hash hash = Hash.password(contraseña).withBCrypt();
    }
    
}
