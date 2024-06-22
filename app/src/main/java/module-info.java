module athlonix.athlonixdesktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.net.http;

    opens athlonix.models to com.google.gson, javafx.base;
    opens athlonix to com.google.gson, javafx.fxml, com.calendarfx.view;
    requires com.google.gson;
    requires java.xml;
    requires com.calendarfx.view;
    requires java.desktop;

    exports athlonix;
    exports athlonix.controllers;
    opens athlonix.controllers to javafx.fxml;
    exports athlonix.repository;
    opens athlonix.repository to javafx.fxml;
}