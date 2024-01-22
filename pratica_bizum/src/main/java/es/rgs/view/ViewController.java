package es.rgs.view;


import es.rgs.controller.BizumController;

public abstract class ViewController {
    protected BizumController bizumController;

    public void setBizumController(BizumController controller){
        this.bizumController = controller;
    }

}
