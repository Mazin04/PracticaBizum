package es.rgs;

import es.rgs.controller.BizumController;
import javafx.application.Application;

public class App {
    public static void main(String[] args) throws Exception{
        BizumController controller = new BizumController();
        Application.launch(BizumController.class, args);
    }
}