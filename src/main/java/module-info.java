module athlonix.athlonixdesktop {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;

    opens athlonix.athlonixdesktop to javafx.fxml;
    exports athlonix.athlonixdesktop;
}