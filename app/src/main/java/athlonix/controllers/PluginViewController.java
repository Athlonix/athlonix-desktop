package athlonix.controllers;

import athlonix.AppSettings;
import athlonix.models.Plugin;
import athlonix.PluginManager;
import athlonix.repository.PluginRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class PluginViewController implements Initializable {
    @FXML
    private VBox pluginContainer;

    public DashboardController dashboardController;
    private final PluginRepository pluginRepository = new PluginRepository();
    private List<Plugin> plugins;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
             this.plugins = pluginRepository.getAllPlugins();
            creatPluginBoxes(plugins);
        } catch (Exception e) {
            System.out.println("failed to fetch themes");
        }
    }

    private void creatPluginBoxes(List<Plugin> plugins) {
        HashSet<String> installedPlugins = PluginManager.getInstalledPlugins();

        for (Plugin plugin : plugins) {
            Pane pluginBox;
            if(installedPlugins.contains(plugin.getName())) {
                pluginBox = createInstalledBox(plugin);
            } else {
                pluginBox = createDownloadBox(plugin);
            }
            pluginContainer.getChildren().add(pluginBox);
        }
    }

    private Pane createDownloadBox(Plugin plugin) {
        Pane pane = new Pane();
        pane.setPrefHeight(121.0);
        pane.setPrefWidth(866.0);
        pane.setStyle("-fx-border-color: black; -fx-border-radius: 8;");

        String formatedName = plugin.getName() + " (" + plugin.getSize() + ")";
        Text title = new Text(formatedName);
        title.setLayoutX(20.0);
        title.setLayoutY(37.0);
        title.getStyleClass().add("title-1");

        Text description = new Text(plugin.getDescription());
        description.setLayoutX(20.0);
        description.setLayoutY(64.0);
        description.setWrappingWidth(445.90234375);

        Button installButton = new Button("Installer");
        installButton.setLayoutX(708.0);
        installButton.setLayoutY(43.0);
        installButton.setPrefHeight(36.0);
        installButton.setPrefWidth(135.0);
        installButton.getStyleClass().add("accent");

        installButton.setOnAction(event -> {
            try {
                Thread downloadThread = pluginRepository.downloadPlugin(plugin.getName());
                downloadThread.join();
                PluginManager.laodOnePlugin(plugin.getName());
                dashboardController.reload();
                refreshPlugins();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        pane.getChildren().addAll(title, description, installButton);
        return pane;
    }

    private Pane createInstalledBox(Plugin plugin) {
        Pane pane = new Pane();
        pane.setPrefHeight(121.0);
        pane.setPrefWidth(866.0);
        pane.setStyle("-fx-border-color: black; -fx-border-radius: 8;");

        String formatedName = plugin.getName() + " (" + plugin.getSize() + ")";
        Text title = new Text(formatedName);
        title.setLayoutX(20.0);
        title.setLayoutY(37.0);
        title.getStyleClass().add("title-1");

        Text description = new Text(plugin.getDescription());
        description.setLayoutX(20.0);
        description.setLayoutY(64.0);
        description.setWrappingWidth(445.90234375);

        Button uninstallButton = new Button("Désinstaller");
        uninstallButton.setLayoutX(708.0);
        uninstallButton.setLayoutY(43.0);
        uninstallButton.setPrefHeight(36.0);
        uninstallButton.setPrefWidth(135.0);
        uninstallButton.getStyleClass().add("danger");

        uninstallButton.setOnAction(event -> {

            try {
                pluginRepository.deletePlugin(plugin.getName());
                dashboardController.reload();
                refreshPlugins();
            } catch (IOException e) {
                System.out.println("failed to reload dashboard");
            }
        });

        pane.getChildren().addAll(title, description, uninstallButton);
        return pane;
    }

    private void refreshPlugins() {
        pluginContainer.getChildren().clear();
        creatPluginBoxes(plugins);
    }

}
