package athlonix.athlonixlauncher;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Text newVersionText;

    @FXML
    private Button update;

    @FXML
    void ignoreUpdate(ActionEvent event) {
        try {
            startApp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startApp() throws IOException {
        String appJarPath = currentVersionDirectory + "/app.jar";
        String javaPath = "java";
        String modulePath = "--module-path";
        String modulePathValue = currentVersionDirectory + "/lib";
        String addModules = "--add-modules";
        String modules = "javafx.controls,javafx.fxml";
        String jarOption = "-jar";

        ProcessBuilder processBuilder = new ProcessBuilder(
                javaPath, modulePath, modulePathValue, addModules, modules, jarOption, appJarPath
        );

        try {
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            System.out.println("Process exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateApplication(ActionEvent event) {

    }

    private String currentVersionDirectory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("starting athlonix launcher!");

        try {
            Version mostRecentVersion = getMostRecentVersion();
            String currentVersion = AppSettings.getCurrentVersion();
            currentVersionDirectory = currentVersion;
            String mostRecentName = mostRecentVersion.getName();

            if(currentVersion.compareTo(mostRecentName) > 0) {
                System.out.println("no recent version available");
                startApp();
            }

            newVersionText.setText("Nouvelle version disponible : " + mostRecentName);

            System.out.println(mostRecentVersion.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("starting application.");
        }
    }

    private static Version getMostRecentVersion() throws IOException, URISyntaxException, InterruptedException {
        String route = "/version";

        HttpResponse<String> versionResponse = ServerQuerier.getRequest(route);

        String responseString = versionResponse.body();

        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);
        JsonObject jsonData = response.getAsJsonObject();

        Type versionType = new TypeToken<Version>(){}.getType();
        return gson.fromJson(jsonData, versionType);
    }
}