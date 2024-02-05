package es.rgs.controller;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

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

public class BizumController extends Application {
    private static final String AVISO_CIERRE2 = "¿Seguro que quieres cerrar el programa?";
    private static final String AVISO_CERRAR = "Estás a punto de cerrar el programa";
    private static final String CERRAR = "Cerrar Aplicación";
    private static final String TITULO = "Bizum by Rubén García";
    public static final String LOGO = "file:pratica_bizum/src/main/java/es/rgs/img/bizum.png";
    private static Stage currentStage;
    private BancoDAO dao;

    public BizumController() throws SQLException{
        dao = DAOFactory.getDao(DAOFactory.MODO_MYSQL);
    }

    @Override
    public void start(Stage stage) throws Exception {
        currentStage = stage;
        LoginController viewController = (LoginController) cargarVista(Vistas.VIEW_LOGIN.getRuta());

        stage.setOnCloseRequest(event -> {
            event.consume();
            cerrar(stage);
        });
    }

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

    public boolean iniciarSesion(String usuario, String contraseña) {
        return dao.iniciarSesion(usuario, contraseña);
    }

    public String getNombre(String usuario){
        return dao.getNombre(usuario);
    }

    public boolean comprobarUsuario(String usuario) {
        return dao.comprobarUsuario(usuario);
    }

    public boolean comprobarRegistroTelefono(String telefono) {
        return dao.comprobarRegistroTelefono(telefono);
    }

    public void registrarUsuario(String usuario, String contraseña, String nombre, Integer telefono) {
        dao.registrarUsuario(usuario, contraseña, nombre, telefono);
    }

    public String getTelefono(String usuario) {
        return dao.getTelefono(usuario);
    }

    public void getCuentas(String usuario) {
        dao.getCuentas(usuario);
    }

    public void agregarCuenta(String username, BigInteger numCuenta, Double dinero) {
        dao.agregarCuenta(username, numCuenta, dinero);
    }

    public boolean comprobarCuenta(BigInteger numCuenta) {
        return dao.comprobarCuenta(numCuenta);
    }

    public Double getDinero(String numeroCuenta) {
        return dao.getDinero(numeroCuenta);
    }

    public void sacarDinero(String numCuenta, Double dinero) {
        dao.sacarDinero(numCuenta, dinero);
    }

    public void ingresarDinero(String numeroCuenta, Double dinero) {
        dao.ingresarDinero(numeroCuenta, dinero);
    }

    public boolean comprobarTelefono(String telefono) {
        return dao.comprobarTelefono(telefono);
    }

    public String getCuentaTelefono(String telefono) {
        return dao.getCuentaTelefono(telefono);
    }

    public boolean comprobarTelefonoExiste(String telefono) {
        return dao.comprobarTelefonoExiste(telefono);
    }

    public void hacerBizum(String telefono, Double dinero) {
        dao.hacerBizum(telefono, dinero);
    }

    public void registrarBizumUsuario(String numeroCuenta) {
        dao.registrarBizumUsuario(numeroCuenta);
    }
}
