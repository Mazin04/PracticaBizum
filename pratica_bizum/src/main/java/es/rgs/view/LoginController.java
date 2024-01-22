package es.rgs.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
}
