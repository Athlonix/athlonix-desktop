package athlonix.controllers;

import athlonix.Plugin;
import athlonix.PluginManager;
import athlonix.SceneLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
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

    @FXML
    private MenuButton button_settings;

    @FXML
    void handleSettingsClick(ActionEvent event) {

    }
    @FXML
    void handleLogout(ActionEvent event) {

    }
    @FXML
    void showActivityPage(ActionEvent event) {

    }

    @FXML
    void showTemesSettings(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/themes-view.fxml"));
            Parent root = fxmlLoader.load();
            ThemesViewController downloadViewControlerr = fxmlLoader.getController();
            downloadViewControlerr.dashboardController=this;

            Scene scene = SceneLoader.GetScene(root);
            Stage stage = new Stage();
            stage.setTitle("Themes settings");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("failed to load themes page");
        }
    }
    @FXML
    void showPluginsSettings(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/plugins-view.fxml"));
            Parent root = fxmlLoader.load();
            PluginViewController downloadViewControlerr = fxmlLoader.getController();

            Scene scene = SceneLoader.GetScene(root);
            Stage stage = new Stage();
            stage.setTitle("Plugins settings");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println("failed to load themes page");
        }
    }


    public void showActivityPage() throws IOException {
        Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/activity-page.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dashboardMenu.setSpacing(16);
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

    public void reload() throws IOException {
        Stage stage = (Stage) dashboardMenu.getScene().getWindow();
        stage.close();
        openDashboard();
    }

    private void openDashboard() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/dashboard-view.fxml"));
        Parent root = fxmlLoader.load();

        DashboardController dashboardController = fxmlLoader.getController();

        Scene scene = SceneLoader.GetScene(root);
        Stage stage = new Stage();
        stage.setTitle("dashboard");
        stage.setScene(scene);
        stage.show();

    }
}
