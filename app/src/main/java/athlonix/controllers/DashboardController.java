package athlonix.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Objects;

public class DashboardController {
    @FXML
    public StackPane contentArea;

    @FXML
    public Button employeeButton;

    public void showEmployeePage() throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/employee-page.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
}
