package es.rgs.view;

/**
 * Enumeración que representa las diferentes vistas de la aplicación.
 * Cada vista está asociada a una ruta de archivo FXML correspondiente.
 */
public enum Vistas {
        VIEW_LOGIN("view/Login.fxml"),
        VIEW_MENU("view/mainMenu.fxml"),
        VIEW_AGREGAR_CUENTA("view/agregarCuenta.fxml"),
        VIEW_SACAR_DINERO("view/sacarDinero.fxml"),
        VIEW_INGRESAR_DINERO("view/ingresarDinero.fxml"),
        VIEW_BIZUM("view/hacerBizum.fxml"),;

        /** La ruta del archivo FXML asociado a la vista. */
        private final String ruta;

        /**
         * Constructor privado para la enumeración Vistas.
         * 
         * @param ruta La ruta del archivo FXML asociado a la vista.
         */
        private Vistas(String ruta) {
                this.ruta = ruta;
        }

        /**
         * Obtiene la ruta del archivo FXML asociado a la vista.
         * 
         * @return La ruta del archivo FXML.
         */
        public String getRuta() {
                return ruta;
        }
}