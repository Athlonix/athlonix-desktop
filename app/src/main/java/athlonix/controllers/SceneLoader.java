package athlonix.controllers;

import athlonix.AppSettings;
import athlonix.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class SceneLoader {

    public static Scene GetScene(String view) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/"+view+".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String theme = AppSettings.getTheme();
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("/styles/"+ theme +".css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("/styles/global.css")).toExternalForm());
        return scene;
    }

    public static Scene GetScene(Parent parent) throws IOException {
        Scene scene = new Scene(parent);
        String theme = AppSettings.getTheme();
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("/styles/"+theme+".css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("/styles/global.css")).toExternalForm());
        return scene;
    }
}
