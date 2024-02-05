package es.rgs.view;

import es.rgs.controller.BizumController;
import es.rgs.model.entities.SingleUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
public class MainMenuController extends ViewController {

    @FXML
    private Button btnAgregarCuenta;

    @FXML
    private Button btnBizum;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Button btnIngresarDinero;

    @FXML
    private Button btnSacarDinero;

    @FXML
    private Label lblNCuenta;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblTelefono;

    @FXML
    private Label textoCuenta;

    @FXML
    private Label textoNombre;

    @FXML
    private Label textoTelefono;


    @FXML
    void initialize() {
        textoNombre.setText(SingleUsuario.getUsuario());
        textoTelefono.setText(SingleUsuario.getTelefono());
        textoCuenta.setText(textoCuentas().equals("") ? "No tienes cuentas" : textoCuentas());
    }

    public String textoCuentas() {
        String texto = "";
        for (int i = 0; i < SingleUsuario.getCuentas().size(); i++) {
            String numeroCuenta = formatarCuenta(SingleUsuario.getCuentas().get(i).getNumeroCuenta().toString());
            texto += numeroCuenta + " --> " + SingleUsuario.getCuentas().get(i).getDinero()+ "€\n";
        }
        return texto;
    }

    @FXML
    void agregarCuenta(MouseEvent event) {
        try {        
            if (bizumController.abrirVentanaEmergente(Vistas.VIEW_AGREGAR_CUENTA.getRuta())) {
                SingleUsuario.setCuentas(null);
                bizumController.getCuentas(SingleUsuario.getUsuario());
                textoCuenta.setText("");
                textoCuenta.setText(textoCuentas());
            }
        } catch (Exception e) {
            mostrarAviso("Ha ocurrido un error al abrir la ventana emergente", "Error", AlertType.ERROR);
        }

        textoCuenta.setText(textoCuentas());
    }

    @FXML
    void hacerBizum(MouseEvent event) {
        try {
            boolean resultado = bizumController.abrirVentanaEmergente(Vistas.VIEW_BIZUM.getRuta());
        
            if (resultado) {
                SingleUsuario.setCuentas(null);
                bizumController.getCuentas(SingleUsuario.getUsuario());
                textoCuenta.setText("");
                textoCuenta.setText(textoCuentas());
            }        
        } catch (Exception e) {
            mostrarAviso("Ha ocurrido un error al abrir la ventana emergente", "Error", AlertType.ERROR);
        }
        textoCuenta.setText(textoCuentas());
    }

    @FXML
    void ingresarDinero(MouseEvent event) {
        try {
            boolean resultado = bizumController.abrirVentanaEmergente(Vistas.VIEW_INGRESAR_DINERO.getRuta());
        
            if (resultado) {
                SingleUsuario.setCuentas(null);
                bizumController.getCuentas(SingleUsuario.getUsuario());
                textoCuenta.setText("");
                textoCuenta.setText(textoCuentas());
            }        
        } catch (Exception e) {
            mostrarAviso("Ha ocurrido un error al abrir la ventana emergente", "Error", AlertType.ERROR);
        }
        textoCuenta.setText(textoCuentas());
    }

    @FXML
    void sacarDinero(MouseEvent event) {
        try {
            boolean resultado = bizumController.abrirVentanaEmergente(Vistas.VIEW_SACAR_DINERO.getRuta());
        
            if (resultado) {
                SingleUsuario.setCuentas(null);
                bizumController.getCuentas(SingleUsuario.getUsuario());
                textoCuenta.setText("");
                textoCuenta.setText(textoCuentas());
            }        
        } catch (Exception e) {
            mostrarAviso("Ha ocurrido un error al abrir la ventana emergente", "Error", AlertType.ERROR);
        }
        textoCuenta.setText(textoCuentas());
    }

    @FXML
    void cerrarSesion(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cierre de sesión");
        alert.setHeaderText("Estás a punto de cerrar sesión.");
        alert.setContentText("Seguro que quieres cerrar sesión?");
        Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image(BizumController.LOGO));

        if (alert.showAndWait().get() == ButtonType.OK) {
            SingleUsuario.setUsuario(null);
            SingleUsuario.setTelefono(null);
            SingleUsuario.setCuentas(null);
            try {
                bizumController.cargarVista(Vistas.VIEW_LOGIN.getRuta());
            } catch (Exception e) {
                e.printStackTrace();
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

    private void cambiarVentana(Vistas vista) {
        try {
            bizumController.cargarVista(vista.getRuta());
        } catch (Exception e) {
            mostrarAviso("Ha ocurrido un error cambiando de ventana.", "Error", Alert.AlertType.ERROR);
        }
    }

    private static String formatarCuenta(String numeroCuenta) {
        StringBuilder formatoCuenta = new StringBuilder();

        for (int i = 0; i < numeroCuenta.length(); i++) {
            formatoCuenta.append(numeroCuenta.charAt(i));
            if ((i + 1) % 4 == 0 && i != numeroCuenta.length() - 1) {
                formatoCuenta.append(" ");
            }
        }

        return formatoCuenta.toString();
    }
}
