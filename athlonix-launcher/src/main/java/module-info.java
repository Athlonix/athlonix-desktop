module athlonix.athlonixlauncher {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires athlonix.athlonixdesktop;


    opens athlonix.athlonixlauncher to javafx.fxml;
    exports athlonix.athlonixlauncher;
}