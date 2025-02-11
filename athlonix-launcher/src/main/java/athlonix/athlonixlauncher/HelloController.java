package athlonix.athlonixlauncher;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
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
        String modulePathValue = "./lib";
        String addModules = "--add-modules";
        String modules = "javafx.controls,javafx.fxml";
        String jarOption = "-jar";
        String versionName = currentVersionDirectory;

        ProcessBuilder processBuilder = new ProcessBuilder(
                javaPath, jarOption, modulePath, modulePathValue, addModules, modules, appJarPath,currentVersionDirectory
        );

        try {
            System.out.println(processBuilder.command());
            Process process = processBuilder.start();
            Platform.exit();
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
        }

    }

    @FXML
    void updateApplication(ActionEvent event) {
        FileDownloader fileDownloader = new FileDownloader();
        Thread dl = null;
        try {
            dl = fileDownloader.downloadFile(ServerQuerier.SERVER_URL + "/version/" + newVersion,"./");
            dl.join();
            String zipPath = "./" + newVersion + ".zip";
            String unzipFolder = "./" + newVersion;
            Unzipper.unzip(zipPath, unzipFolder);
            File currentDirectory = new File("./" + currentVersionDirectory);
            deleteDirectory(currentDirectory);

            File file = new File(zipPath);

            if (file.delete()) {
                System.out.println("File deleted successfully");
            }
            else {
                System.out.println("Failed to delete the file");
            }

            currentVersionDirectory = newVersion;
            startApp();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String currentVersionDirectory;
    private String newVersion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("starting athlonix launcher!");

        try {
            String currentVersion = AppSettings.getCurrentVersion();
            currentVersionDirectory = currentVersion;

            NetworkChecker.init();
            System.out.println("network : " + NetworkChecker.isOnline);
            if(!NetworkChecker.isOnline) {
                System.out.println("not connected to server.");
                try {
                    startApp();
                    return;
                } catch (IOException e) {
                    System.out.println("failed to start app");
                    System.out.println(e.getMessage());
                }
            }


            Version mostRecentVersion = getMostRecentVersion();
            String mostRecentName = mostRecentVersion.getName();


            String currV = currentVersion.split("-")[1];
            String remoteV = mostRecentName.split("-")[1];

            System.out.println("current version : " +currV);
            System.out.println("remote version : " + remoteV);

            if(currentVersion.compareTo(mostRecentName) >= 0) {
                System.out.println("no recent version available");
                startApp();
            }

            this.newVersion = mostRecentName;
            newVersionText.setText("Nouvelle version disponible : " + newVersion);

            System.out.println(mostRecentVersion.getName());
        } catch (Exception e) {
            System.out.println("errrror " + e.getMessage());
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

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}