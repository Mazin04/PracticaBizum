package es.rgs.controller;

import java.math.BigInteger;
import java.sql.SQLException;
import es.rgs.App;
import es.rgs.model.BancoDAO;
import es.rgs.model.DAOFactory;
import es.rgs.view.LoginController;
import es.rgs.view.ViewController;
import es.rgs.view.Vistas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Clase que representa el controlador principal de la aplicación Bizum.
 * Contiene métodos para la gestión de la interfaz gráfica y la lógica de negocio.
 * Extiende de la clase Application de JavaFX.
 *
 * @author Rubén García
 * @version 1.0
 */
public class BizumController extends Application {
    // Constantes para mensajes y títulos
    private static final String AVISO_CIERRE2 = "¿Seguro que quieres cerrar el programa?";
    private static final String AVISO_CERRAR = "Estás a punto de cerrar el programa";
    private static final String CERRAR = "Cerrar Aplicación";
    private static final String TITULO = "Bizum by Rubén García";
    public static final String LOGO = "file:pratica_bizum/src/main/java/es/rgs/img/bizum.png";

    // Instancia del escenario actual
    private static Stage currentStage;
    // Instancia del objeto DAO para la gestión de la base de datos
    private BancoDAO dao;
    
    /**
     * Constructor de la clase que inicializa el objeto DAO.
     *
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    public BizumController() throws SQLException{
        dao = DAOFactory.getDao(DAOFactory.MODO_MYSQL);
    }

    /**
     * Método principal de la aplicación JavaFX. Se ejecuta al iniciar la aplicación.
     * También se encarga de la acción en caso de pulsar la X para cerrar la ventana
     * 
     * @param stage Escenario principal de la aplicación.
     * @throws Exception Si ocurre un error durante la ejecución.
     */
    @Override
    public void start(Stage stage) throws Exception {
        currentStage = stage;
        LoginController viewController = (LoginController) cargarVista(Vistas.VIEW_LOGIN.getRuta());

        stage.setOnCloseRequest(event -> {
            event.consume();
            cerrar(stage);
        });
    }

    /**
     * Método privado para gestionar el cierre de la aplicación con un cuadro de diálogo de confirmación.
     *
     * @param stage Escenario actual que se va a cerrar.
     */
    private void cerrar(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(CERRAR);
        alert.setHeaderText(AVISO_CERRAR);
        alert.setContentText(AVISO_CIERRE2);
        Stage stageAlert = (Stage)alert.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image(LOGO));
        
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }

    /**
     * Método para cargar una vista FXML y mostrarla en el escenario actual.
     *
     * @param ficheroView Ruta del archivo FXML que se va a cargar.
     * @return Objeto ViewController asociado a la vista cargada.
     * @throws Exception Si ocurre un error al cargar la vista.
     */
    public ViewController cargarVista(String ficheroView) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(ficheroView));
        Parent root = (Parent)fxmlLoader.load();

        ViewController viewController = fxmlLoader.<ViewController>getController();
        viewController.setBizumController(this);
        Scene scene = new Scene(root);
        currentStage.close();

        currentStage.setScene(scene);
        currentStage.resizableProperty().setValue(false);
        currentStage.getIcons().add(new Image(LOGO));
        currentStage.setTitle(TITULO);
        currentStage.show();
        return viewController;
    }

    /**
     * Método para abrir una ventana emergente y esperar hasta que se cierre, bloquea las acciones de la ventana principal.
     *
     * @param vista Ruta del archivo FXML que se va a cargar en la ventana emergente.
     * @return true si la acción en la ventana emergente fue exitosa, false en caso contrario.
     * @throws Exception Si ocurre un error al cargar la ventana emergente.
     */
    public boolean abrirVentanaEmergente(String vista) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(vista));
            Parent root = (Parent)loader.load();
    
            ViewController viewController = loader.<ViewController>getController();
            viewController.setBizumController(this);

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(currentStage);
            dialogStage.initStyle(StageStyle.UTILITY);
    
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
            return viewController.getResult();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método para iniciar sesión en la aplicación.
     *
     * @param usuario Nombre de usuario.
     * @param contraseña Contraseña del usuario.
     * @return true si la sesión se inicia correctamente, false en caso contrario.
     */
    public boolean iniciarSesion(String usuario, String contraseña) {
        return dao.iniciarSesion(usuario, contraseña);
    }

    /**
     * Método para obtener el nombre asociado a un usuario.
     *
     * @param usuario Nombre de usuario.
     * @return Nombre asociado al usuario.
     */
    public String getNombre(String usuario){
        return dao.getNombre(usuario);
    }

    /**
     * Método para comprobar la existencia de un usuario en la base de datos.
     *
     * @param usuario Nombre de usuario a comprobar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public boolean comprobarUsuario(String usuario) {
        return dao.comprobarUsuario(usuario);
    }

    /**
     * Método para verificar si un número de teléfono ya está registrado.
     *
     * @param telefono Número de teléfono a verificar.
     * @return true si el teléfono ya está registrado, false en caso contrario.
     */
    public boolean comprobarRegistroTelefono(String telefono) {
        return dao.comprobarRegistroTelefono(telefono);
    }

    /**
     * Método para registrar un nuevo usuario en la base de datos.
     *
     * @param usuario Nombre de usuario.
     * @param contraseña Contraseña del usuario.
     * @param nombre Nombre real del usuario.
     * @param telefono Número de teléfono del usuario.
     */
    public void registrarUsuario(String usuario, String contraseña, String nombre, Integer telefono) {
        dao.registrarUsuario(usuario, contraseña, nombre, telefono);
    }

    /**
     * Método para obtener el número de teléfono asociado a un usuario.
     *
     * @param usuario Nombre de usuario.
     * @return Número de teléfono asociado al usuario.
     */
    public String getTelefono(String usuario) {
        return dao.getTelefono(usuario);
    }

    /**
     * Método para obtener las cuentas asociadas a un usuario.
     *
     * @param usuario Nombre de usuario.
     */
    public void getCuentas(String usuario) {
        dao.getCuentas(usuario);
    }

    /**
     * Método para agregar una nueva cuenta a un usuario.
     *
     * @param username Nombre de usuario.
     * @param numCuenta Número de cuenta a agregar.
     * @param dinero Saldo inicial de la cuenta.
     */
    public void agregarCuenta(String username, BigInteger numCuenta, Double dinero) {
        dao.agregarCuenta(username, numCuenta, dinero);
    }

    /**
     * Método para comprobar la existencia de una cuenta en la base de datos.
     *
     * @param numCuenta Número de cuenta a comprobar.
     * @return true si la cuenta existe, false en caso contrario.
     */
    public boolean comprobarCuenta(BigInteger numCuenta) {
        return dao.comprobarCuenta(numCuenta);
    }

    /**
     * Método para obtener el saldo de una cuenta.
     *
     * @param numeroCuenta Número de cuenta.
     * @return Saldo actual de la cuenta.
     */
    public Double getDinero(String numeroCuenta) {
        return dao.getDinero(numeroCuenta);
    }

    /**
     * Método para retirar dinero de una cuenta.
     *
     * @param numCuenta Número de cuenta.
     * @param dinero Cantidad a retirar.
     */
    public void sacarDinero(String numCuenta, Double dinero) {
        dao.sacarDinero(numCuenta, dinero);
    }

    /**
     * Método para depositar dinero en una cuenta.
     *
     * @param numeroCuenta Número de cuenta.
     * @param dinero Cantidad a depositar.
     */
    public void ingresarDinero(String numeroCuenta, Double dinero) {
        dao.ingresarDinero(numeroCuenta, dinero);
    }

    /**
     * Método para comprobar la existencia de un número de teléfono en la base de datos.
     *
     * @param telefono Número de teléfono a comprobar.
     * @return true si el teléfono existe, false en caso contrario.
     */
    public boolean comprobarTelefono(String telefono) {
        return dao.comprobarTelefono(telefono);
    }

    /**
     * Método para obtener la cuenta asociada a un número de teléfono.
     *
     * @param telefono Número de teléfono.
     * @return Número de cuenta asociado al teléfono.
     */
    public String getCuentaTelefono(String telefono) {
        return dao.getCuentaTelefono(telefono);
    }

    /**
     * Método para comprobar la existencia de un número de teléfono en la base de datos.
     *
     * @param telefono Número de teléfono a comprobar.
     * @return true si el teléfono existe, false en caso contrario.
     */
    public boolean comprobarTelefonoExiste(String telefono) {
        return dao.comprobarTelefonoExiste(telefono);
    }

    /**
     * Método para realizar una transferencia Bizum a otro usuario.
     *
     * @param telefono Número de teléfono del destinatario.
     * @param dinero Cantidad a transferir.
     */
    public void hacerBizum(String telefono, Double dinero) {
        dao.hacerBizum(telefono, dinero);
    }
    
    /**
     * Método para registrar un usuario en el sistema Bizum.
     *
     * @param numeroCuenta Número de cuenta a registrar para Bizum.
     */
    public void registrarBizumUsuario(String numeroCuenta) {
        dao.registrarBizumUsuario(numeroCuenta);
    }
}
