package es.rgs.view;

public enum Vistas {
        VIEW_LOGIN("view/Login.fxml"), 
        VIEW_MENU("view/mainMenu.fxml"),
        VIEW_AGREGAR_CUENTA("view/agregarCuenta.fxml"), 
        VIEW_SACAR_DINERO("view/sacarDinero.fxml"),;

        private final String ruta;

        private Vistas(String ruta) {
                this.ruta = ruta;
        }

        public String getRuta() {
                return ruta;
        }
}