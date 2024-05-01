package athlonix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("/styles/primer-light.css")).toExternalForm());
        scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("/styles/global.css")).toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        runPlugins();
    }

    public static void runPlugins() {
        try {
            File pluginsDir = new File("app/plugins");

            File[] plugins = pluginsDir.listFiles();


            for (File plugin_jar : plugins) {
                try {
                    URL url = plugin_jar.toURI().toURL();
                    URL[] urls = new URL[] { url };

                    ClassLoader cl = new URLClassLoader(urls);
                    Class<Plugin> cls = (Class<Plugin>) cl.loadClass("plugins.AthlonixPlugin");
                    Plugin circle = cls.getDeclaredConstructor().newInstance();
                    circle.run();
                } catch (Exception e) {
                    System.out.println("Error while adding plugin " + plugin_jar.getName());
                }
            }
        } catch (Exception e) {
            System.out.println("Adding Plugins Failed Due To : " + e.toString());
        }

    }
}