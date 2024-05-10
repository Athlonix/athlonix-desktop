package athlonix.controllers;

import athlonix.auth.APIQuerier;
import athlonix.models.Activity;
import athlonix.models.Sport;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ActivityController implements Initializable {

    @FXML
    private TableView<Activity> activitiesTable;

    @FXML
    private TableColumn<Activity, String> endColumn;

    @FXML
    private TableColumn<Activity, String> nameColumn;

    @FXML
    private TableColumn<Activity, String> recurrenceColumn;

    @FXML
    private TableColumn<Activity, String> sportColumn;

    @FXML
    private TableColumn<Activity, String> startColumn;

    @FXML
    private TableColumn<Activity, String> actionColumn;

    @FXML
    private Button searchButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Activity> activities;

        try {
            activities = getAllActivities();
        } catch (Exception e) {
            //TODO: handle error
            System.out.println("error here");
            return;
        }



        ObservableList<Activity> list = FXCollections.observableArrayList();
        list.addAll(activities);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");


        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        startColumn.setCellValueFactory(cellData -> {

            Activity activity = cellData.getValue();
            Date startDate = activity.getStartDate();


            String formattedDate = dateFormat.format(startDate);

            return new SimpleStringProperty(formattedDate);
        });

        endColumn.setCellValueFactory(cellData -> {

            Activity activity = cellData.getValue();
            Date endDate = activity.getEndDate();

            String formattedDate = dateFormat.format(endDate);

            return new SimpleStringProperty(formattedDate);
        });

        recurrenceColumn.setCellValueFactory(new PropertyValueFactory<>("recurrence"));

        sportColumn.setCellValueFactory(cellData -> {

            Activity activity = cellData.getValue();
            Sport sport = activity.getSport();

            return new SimpleStringProperty(sport.getName());
        });

        actionColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty("test");
        });

        activitiesTable.setItems(list);


    }

    private List<Activity> getAllActivities() throws IOException, URISyntaxException, InterruptedException {
        String route = "/activities";

        HttpResponse<String> activitiesResponse = APIQuerier.getRequest(route);

        String responseString = activitiesResponse.body();

        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);
        JsonArray dataArray = response.getAsJsonObject().getAsJsonArray("data");

        Type activityListType = new TypeToken<List<Activity>>(){}.getType();


        return gson.fromJson(dataArray, activityListType);
    }

}
