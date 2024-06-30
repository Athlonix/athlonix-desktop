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

    }

    @FXML
    void updateApplication(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("starting athlonix launcher!");

        try {
            Version mostRecentVersion = getMostRecentVersion();
            String currentVersion = AppSettings.getCurrentVersion();
            String mostRecentName = mostRecentVersion.getName();

            if(currentVersion.compareTo(mostRecentName) > 0) {
                System.out.println("no recent version available");
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