package athlonix;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SceneManager {

    final private static String directoryPath = "app/themes";

    public static Scene GetScene(String view) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/"+view+".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        applyTheme(scene);
        return scene;
    }

    public static Scene GetScene(Parent parent) throws IOException {
        Scene scene = new Scene(parent);
        applyTheme(scene);
        return scene;
    }

    public static void applyTheme(Scene scene) {
        scene.getStylesheets().removeAll();
        String theme = AppSettings.getTheme();
        scene.getStylesheets().add(new File(directoryPath+"/"+theme+".css").toURI().toString());
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("/styles/global.css")).toExternalForm());
    }
}
