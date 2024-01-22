package es.rgs.view;

public enum Vistas {
        VIEW_LOGIN("view/LoginController.fxml"), 
        VIEW_REGISTRAR("view/RegisterController.fxml"),;

        private final String ruta;

        private Vistas(String ruta) {
                this.ruta = ruta;
        }

        public String getRuta() {
                return ruta;
        }
}