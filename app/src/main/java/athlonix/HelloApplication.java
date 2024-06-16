package athlonix;

import athlonix.auth.NetworkChecker;
import athlonix.controllers.SceneLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        NetworkChecker.init();
        AppSettings.loadConfiguration();

        Scene loginScene = SceneLoader.GetScene("login-view");
        stage.setTitle("Hello!");
        stage.setScene(loginScene);
        stage.show();

    }

    public static void main(String[] args) {
        PluginManager.loadPlugins();
        launch();
    }

}