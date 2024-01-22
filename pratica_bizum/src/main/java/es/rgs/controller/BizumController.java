package es.rgs.controller;

import es.rgs.App;
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
import javafx.stage.Stage;

public class BizumController extends Application {
    private static final String AVISO_CIERRE2 = "¿Seguro que quieres cerrar el programa?";
    private static final String AVISO_CERRAR = "Estás a punto de cerrar el programa";
    private static final String CERRAR = "Cerrar Aplicación";
    private static final String TITULO = "Bizum by Rubén García";
    private static final String LOGO = "file:img/bizum.png";
    private static Stage currentStage;


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
}
