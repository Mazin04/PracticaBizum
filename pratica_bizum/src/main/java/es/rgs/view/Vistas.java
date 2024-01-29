package es.rgs.view;

public enum Vistas {
        VIEW_LOGIN("view/Login.fxml"), 
        VIEW_MENU("view/mainMenu.fxml"),;

        private final String ruta;

        private Vistas(String ruta) {
                this.ruta = ruta;
        }

        public String getRuta() {
                return ruta;
        }
}