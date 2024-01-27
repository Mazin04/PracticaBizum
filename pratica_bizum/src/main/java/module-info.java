module es.rgs {
    requires transitive javafx.controls;    
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires bcrypt;
    requires java.sql;
    opens es.rgs to javafx.fxml;
    opens es.rgs.view to javafx.fxml;
    exports es.rgs.controller;
    exports es.rgs.view;
}
