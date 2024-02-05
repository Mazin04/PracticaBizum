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
/**
 * Controlador para la vista del menú principal en la aplicación Bizum.
 * Permite al usuario realizar diversas operaciones como agregar cuentas, realizar
 * transferencias de dinero (Bizum), ingresar dinero en una cuenta y sacar dinero de una cuenta.
 */
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

    /**
     * Inicializa el controlador.
     * Llena los label con la información del usuario y sus cuentas.
     */
    @FXML
    void initialize() {
        textoNombre.setText(SingleUsuario.getUsuario());
        textoTelefono.setText(SingleUsuario.getTelefono());
        textoCuenta.setText(textoCuentas().equals("") ? "No tienes cuentas" : textoCuentas());
    }

    /**
     * Genera un texto que muestra las cuentas del usuario y sus saldos.
     * @return Texto con la información de las cuentas del usuario.
     */
    public String textoCuentas() {
        String texto = "";
        for (int i = 0; i < SingleUsuario.getCuentas().size(); i++) {
            String numeroCuenta = formatearCuenta(SingleUsuario.getCuentas().get(i).getNumeroCuenta().toString());
            texto += numeroCuenta + " --> " + SingleUsuario.getCuentas().get(i).getDinero()+ "€\n";
        }
        return texto;
    }

    /**
     * Método invocado al hacer clic en el botón "Agregar Cuenta".
     * Abre una ventana emergente para agregar una nueva cuenta.
     * Actualiza la lista de cuentas del usuario en caso de éxito.
     * @param event Evento de clic del mouse.
     */
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

    /**
     * Método invocado al hacer clic en el botón "Bizum".
     * Abre una ventana emergente para realizar una transferencia de dinero (Bizum).
     * Actualiza la lista de cuentas del usuario en caso de éxito.
     * @param event Evento de clic del mouse.
     */
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
    /**
     * Método invocado al hacer clic en el botón "Ingresar Dinero".
     * Abre una ventana emergente para ingresar dinero en una cuenta.
     * Actualiza la lista de cuentas del usuario en caso de éxito.
     * @param event Evento de clic del mouse.
     */
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

    /**
     * Método invocado al hacer clic en el botón "Sacar Dinero".
     * Abre una ventana emergente para sacar dinero de una cuenta.
     * Actualiza la lista de cuentas del usuario en caso de éxito.
     * @param event Evento de clic del mouse.
     */
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

    /**
     * Método invocado al hacer clic en el botón "Cerrar Sesión".
     * Cierra la sesión actual del usuario.
     * @param event Evento de clic del mouse.
     */
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
    
    /**
     * Formatea el número de cuenta para mostrarlo de forma legible.
     * Agrega espacios cada cuatro dígitos para mejorar la legibilidad.
     * 
     * @param numeroCuenta El número de cuenta a formatear.
     * @return El número de cuenta formateado.
     */
    private static String formatearCuenta(String numeroCuenta) {
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
