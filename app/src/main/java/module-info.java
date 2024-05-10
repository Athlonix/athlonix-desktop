module athlonix.athlonixdesktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.net.http;

    opens athlonix.models to com.google.gson, javafx.base;
    opens athlonix to com.google.gson, javafx.fxml;
    requires com.google.gson;
    requires java.xml;

    exports athlonix;
    exports athlonix.controllers;
    opens athlonix.controllers to javafx.fxml;
}