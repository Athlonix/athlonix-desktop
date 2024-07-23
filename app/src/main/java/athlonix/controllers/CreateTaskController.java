package athlonix.controllers;

import athlonix.SceneManager;
import athlonix.models.ActivityOccurence;
import athlonix.models.TeamMember;
import athlonix.repository.ActivityOccurenceRepository;
import athlonix.repository.TaskRepository;
import athlonix.repository.TeamRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CreateTaskController {

    @FXML
    private Button choose_date_button;

    @FXML
    private Text date;

    @FXML
    private TextArea description;

    @FXML
    private ComboBox<Pair<String, Integer>> members;

    @FXML
    private ChoiceBox<String> priority;

    @FXML
    private Button save_button;

    @FXML
    private ChoiceBox<String> status;

    @FXML
    private TextField title;

    public int idActivity;

    public ActivityOccurence activityOccurence;

    public ActivityController activityController;

    public void fillData(ActivityController activityController) {
        try {
            this.activityController = activityController;
            buildChoices();
        } catch (Exception e) {
            System.out.println("failed to build choices for task creation");
            throw new RuntimeException(e);
        }
    }

    void buildChoices() throws IOException, URISyntaxException, InterruptedException {
        ObservableList<String> priorities = FXCollections.observableArrayList();

        priorities.add("P0");
        priorities.add("P1");
        priorities.add("P2");
        priority.setItems(priorities);
        priority.setValue("P0");

        ObservableList<String> statusChoices = FXCollections.observableArrayList();
        statusChoices.add("not started");
        statusChoices.add("in progress");
        statusChoices.add("completed");
        status.setItems(statusChoices);
        status.setValue("not started");

        TeamRepository teamRepository = new TeamRepository();
        List<TeamMember> teamMembers = null;
        teamMembers = teamRepository.getTeamMembers(idActivity);


        ObservableList<Pair<String, Integer>> teamMembersChoice = FXCollections.observableArrayList();

        for(TeamMember member : teamMembers) {
            teamMembersChoice.add(new Pair<>(member.getUsername(), member.getId()){
                @Override
                public String toString() {
                    return getKey();
                }
            });
        }

        members.setCellFactory(lv -> new ListCell<Pair<String, Integer>>() {
            @Override
            protected void updateItem(Pair<String, Integer> item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getKey());
            }
        });

        members.setItems(teamMembersChoice);
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    private void showChooseOccurence() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/task-occurence-view.fxml"));
        Parent root = fxmlLoader.load();

        TaskOccurenceController taskOccurenceController = fxmlLoader.getController();
        taskOccurenceController.setIdActivity(this.idActivity);
        taskOccurenceController.setTaskController(this);
        taskOccurenceController.fillOccurencesData();

        Scene chooseOccurence = SceneManager.GetScene(root);
        Stage stage = new Stage();
        stage.setTitle("Choose occurence for task");
        stage.setScene(chooseOccurence);
        stage.show();
    }


    @FXML
    void choseDate(ActionEvent event) {
        try {
            showChooseOccurence();
        } catch (IOException e) {
            System.out.println("error while opening choose task window");
        }
    }

    void setOccurence(ActivityOccurence activityOccurence) {
        this.activityOccurence = activityOccurence;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        date.setText(formatter.format(activityOccurence.getDate()));
    }

    @FXML
    void saveTask(ActionEvent event) {
        ActivityOccurenceRepository activityOccurenceRepository = new ActivityOccurenceRepository();
        TaskRepository taskRepository = new TaskRepository();
        try {
            int idOccurence = activityOccurenceRepository.createActivityException(idActivity, activityOccurence);
            String titleS = title.getText();
            String priorityS = priority.getValue();
            String statusS = status.getValue();
            String descriptionS = description.getText();
            int memberId = members.getValue().getValue();
            taskRepository.createTask(idOccurence,priorityS,statusS,titleS,descriptionS,memberId);
            activityController.refreshTask(null);
            Stage stage = (Stage) title.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.out.println("error while creating task : " + e.getMessage());
        }


    }
}
