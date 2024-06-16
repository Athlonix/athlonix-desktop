package athlonix.controllers;

import athlonix.auth.NetworkChecker;
import athlonix.models.Activity;
import athlonix.models.Sport;
import athlonix.repository.ActivitiesRepository;
import athlonix.repository.OfflineActivitiesRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ActivitiesController implements Initializable {

    @FXML
    private Pagination pagination;
    @FXML
    private TextField searchInput;

    @FXML
    private TableView<Activity> activitiesTable;

    @FXML
    private TableColumn<Activity, String> endColumn;

    @FXML
    private TableColumn<Activity, String> timeColumn;

    @FXML
    private TableColumn<Activity, String> nameColumn;

    @FXML
    private TableColumn<Activity, String> frequencyColumn;

    @FXML
    private TableColumn<Activity, String> sportColumn;

    @FXML
    private TableColumn<Activity, String> startColumn;

    @FXML
    private TableColumn<Activity, String> actionColumn;

    @FXML
    private Button searchButton;

    private final ActivitiesRepository activitiesRepository = new ActivitiesRepository();
    private final OfflineActivitiesRepository offlineActivitiesRepository = new OfflineActivitiesRepository();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDataWithActivities("");
    }

    private void fillDataWithActivities(String searchParam) {
        List<Activity> activities;

        try {
            if(NetworkChecker.isOnline) {
                activities = activitiesRepository.getAllActivities(searchParam);
            } else {
                activities = offlineActivitiesRepository.getAllActivities();
            }
        } catch (Exception e) {
            //TODO: handle error
            System.out.println("error here");
            return;
        }

        ObservableList<Activity> list = FXCollections.observableArrayList();
        list.addAll(activities);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


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
            if(endDate == null) {
                return new SimpleStringProperty("Aucune");
            }
            String formattedDate = dateFormat.format(endDate);
            return new SimpleStringProperty(formattedDate);
        });

        timeColumn.setCellValueFactory(cellData -> {
            Activity activity = cellData.getValue();
            String startTime = activity.getStartTime();
            String endTime = activity.getEndTime();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            //String formatedStart = startTime.format(formatter);
            //String formatedEnd = endTime.format(formatter);
            return new SimpleStringProperty(startTime + " - " + endTime);
        });

        frequencyColumn.setCellValueFactory(cellData -> {
            Activity activity = cellData.getValue();
            String frequency = activity.getFrequency();

            if(frequency == null) {
                return new SimpleStringProperty("unique");
            }

            return new SimpleStringProperty(frequency);
        });


        sportColumn.setCellValueFactory(cellData -> {
            Activity activity = cellData.getValue();
            Sport sport = activity.getSport();
            return new SimpleStringProperty(sport.getName());
        });

        actionColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty("dummy");
        });

        Callback<TableColumn<Activity, String>, TableCell<Activity, String>> cellFoctory = (TableColumn<Activity, String> param) -> {

            return new TableCell<Activity, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        if(!NetworkChecker.isOnline) {
                            setGraphic(new Text("NON DISPONIBLE"));
                            return;
                        }

                        Button button = new Button("Plus");
                        button.getStyleClass().add("accent");
                        button.getStyleClass().add("button-outlined");

                        button.setOnAction(event -> {
                            Activity activity = getTableView().getItems().get(getIndex());

                            try {
                                showActivityPage(activity);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        setText(null);
                        setGraphic(button);

                    }
                }

            };
        };

        actionColumn.setCellFactory(cellFoctory);

        activitiesTable.setItems(list);


    }

    private void showActivityPage(Activity activity) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/single-activity-view.fxml"));
        Parent root = fxmlLoader.load();

        ActivityController activityController = fxmlLoader.getController();
        activityController.setActivity(activity);
        activityController.fillActivityData();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(activity.getName());
        stage.setScene(scene);

        stage.show();
    }

    public void searchActivities() throws IOException {
        String searchParam = searchInput.getText();
        fillDataWithActivities(searchParam);
    }

}
