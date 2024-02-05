package es.rgs.view;


import es.rgs.controller.BizumController;
/**
 * Clase abstracta base para los controladores de vista en la aplicación Bizum.
 * Proporciona funcionalidades comunes para los controladores de las diferentes vistas.
 */
public abstract class ViewController {
    /** El controlador principal de Bizum que gestiona la lógica de la aplicación. */
    protected BizumController bizumController;
    
    /**
     * Establece el controlador de Bizum para el controlador de vista actual.
     * @param controller El controlador principal de Bizum.
     */
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
