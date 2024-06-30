package athlonix.athlonixlauncher;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SceneManager {

    final private static String directoryPath = "app/themes";

    public static Scene GetScene(Parent parent) throws IOException {
        return new Scene(parent);
    }

}
