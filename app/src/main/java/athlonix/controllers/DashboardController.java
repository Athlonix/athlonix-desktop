package athlonix.controllers;

import athlonix.Plugin;
import athlonix.PluginManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    public StackPane contentArea;

    @FXML
    private VBox dashboardMenu;

    public void showActivityPage() throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/activity-page.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for(Plugin plugin : PluginManager.plugins) {
            Button pluginButton = new Button(plugin.getName());
            pluginButton.setStyle("-fx-background-color:  -color-accent-2");
            pluginButton.prefWidth(200);
            pluginButton.prefHeight(47);
            pluginButton.setPrefWidth(200);
            pluginButton.setPrefHeight(47);
            pluginButton.setOnAction(event -> {
                Scene pluginScene = plugin.getScene();
                Stage newStage = new Stage();
                newStage.setScene(pluginScene);
                newStage.show();

            });



            dashboardMenu.getChildren().add(pluginButton);
        }
    }
}
