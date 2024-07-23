package athlonix;

import athlonix.auth.NetworkChecker;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        NetworkChecker.init();

        boolean tt = NetworkChecker.isOnline;

        AppSettings.loadConfiguration();
        Scene loginScene = SceneManager.GetScene("login-view");
        stage.setTitle(AppSettings.appVersion);
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args) {
        if(args.length > 0) {
            AppSettings.setVersion(args[0]);
        } else {
            AppSettings.setVersion("Athlonix");
        }
        PluginManager.loadPlugins();
        launch();
    }

}