module athlonix.athlonixdesktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.net.http;
    requires com.google.gson;

    opens athlonix to javafx.fxml;
    exports athlonix;
    exports athlonix.controllers;
    opens athlonix.controllers to javafx.fxml;
}