package es.rgs.view;


import es.rgs.controller.BizumController;

public abstract class ViewController {
    protected BizumController bizumController;

    public void setBizumController(BizumController controller){
        this.bizumController = controller;
    }

    private Boolean result;

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Boolean getResult() {
        return result;
    }
}
