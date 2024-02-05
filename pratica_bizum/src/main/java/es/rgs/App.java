package es.rgs;

import es.rgs.controller.BizumController;
import javafx.application.Application;
/**
 * La clase principal de la aplicación Bizum.
 * Esta clase inicia la aplicación JavaFX y configura el punto de entrada principal.
 */
public class App {
    /**
     * El método principal de la aplicación, que inicia la aplicación JavaFX.
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) throws Exception{
        Application.launch(BizumController.class, args);
    }
}