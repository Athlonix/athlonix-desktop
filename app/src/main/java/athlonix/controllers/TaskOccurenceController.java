package athlonix.controllers;

import athlonix.models.ActivityOccurence;
import athlonix.models.TeamMember;
import athlonix.repository.ActivityOccurenceRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class TaskOccurenceController {
    @FXML
    private DatePicker endDate;

    @FXML
    private TableColumn<ActivityOccurence, String> occurenceActions;

    @FXML
    private TableColumn<ActivityOccurence, String> occurenceDate;

    @FXML
    private TableView<ActivityOccurence> occurenceTable;

    @FXML
    private Button searchButton;

    private int idActivity;

    CreateTaskController createTaskController;

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    public void setTaskController(CreateTaskController createTaskController) {
        this.createTaskController = createTaskController;
    }

    public void fillOccurencesData() {
        try {
            String startDateFormated = getStartDateFormated();
            String endDateFormated = getEndDateFormated();

            ActivityOccurenceRepository ocRepo = new ActivityOccurenceRepository();
            List<ActivityOccurence> occurences =  ocRepo.getAll(idActivity,startDateFormated,endDateFormated);
            fillOccurenceTable(occurences);
        } catch (Exception e) {
            System.out.println("Something went wrong while fetching occurence data");
            System.out.println(e.getMessage());
        }
    }

    private void fillOccurenceTable(List<ActivityOccurence> occurences) {
        occurenceTable.getItems().clear();

        ObservableList<ActivityOccurence> list = FXCollections.observableArrayList();
        list.addAll(occurences);

        occurenceDate.setCellValueFactory(cellData -> {
            ActivityOccurence activityOccurence = cellData.getValue();
            return new SimpleStringProperty(formatDateTime(activityOccurence.getDate()));

        });
        occurenceActions.setCellValueFactory(cellData -> {
            return new SimpleStringProperty("dummy");
        });

        occurenceActions.setCellFactory(occurenceActionFactory);

        occurenceTable.setItems(list);


    }

    Callback<TableColumn<ActivityOccurence, String>, TableCell<ActivityOccurence, String>> occurenceActionFactory = (TableColumn<ActivityOccurence, String> param) -> {

        return new TableCell<ActivityOccurence, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);

                } else {

                    ActivityOccurence occurence = getTableView().getItems().get(getIndex());

                    Button addButton = new Button("Choisir");
                    addButton.getStyleClass().add("accent");
                    addButton.setOnAction(event -> {
                    createTaskController.setOccurence(occurence);
                    Stage stage = (Stage) addButton.getScene().getWindow();
                    stage.close();
                    });

                    setText(null);
                    setGraphic(addButton);

                }
            }

        };
    };

    private String getStartDateFormated() {
        LocalDate today = LocalDate.now();

        LocalDate dateIn7Days = today.plusDays(7);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return today.format(formatter);
    }

    private String getEndDateFormated() {

        if(endDate.getValue() != null) {
            return endDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        LocalDate today = LocalDate.now();
        LocalDate dateIn7Days = today.plusDays(7);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return dateIn7Days.format(formatter);
    }

    @FXML
    void searchOccurences(ActionEvent event) {
        fillOccurencesData();
    }

    private String formatDateTime(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        return formatter.format(date);
    }
}
