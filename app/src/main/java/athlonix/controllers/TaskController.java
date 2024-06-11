package athlonix.controllers;
import athlonix.auth.APIQuerier;
import athlonix.lib.TeamRepository;
import athlonix.models.Task;
import athlonix.models.TeamMember;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class TaskController {
    @FXML
    private Text created_at;

    @FXML
    private Text date;

    @FXML
    private TextArea description;

    @FXML
    private ChoiceBox<String> employee;

    @FXML
    private Button modify_button;

    @FXML
    private ChoiceBox<String> priority;

    @FXML
    private Button save_button;

    @FXML
    private ChoiceBox<String> status;

    @FXML
    private TextField title;

    @FXML
    private ComboBox<Pair<String, Integer>> members;

    private Task task;

    private int idActivity;

    private ActivityController activityController;

    public void fillData(Task task, int idActivity) throws IOException, URISyntaxException, InterruptedException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        this.task = task;
        this.idActivity =idActivity;

        title.setText(task.getTitle());
        description.setText(task.getDescription());

        ObservableList<String> priorities = FXCollections.observableArrayList();

        priorities.add("P0");
        priorities.add("P1");
        priorities.add("P2");
        priority.setItems(priorities);
        priority.setValue(task.getPriority());

        ObservableList<String> statusChoices = FXCollections.observableArrayList();
        statusChoices.add("not started");
        statusChoices.add("in progress");
        statusChoices.add("completed");
        status.setItems(statusChoices);
        status.setValue(task.getStatus());

        created_at.setText("Crée le " + dateFormat.format(task.getCreatedAt()));

        TeamRepository teamRepository = new TeamRepository();
        List<TeamMember> teamMembers = teamRepository.getTeamMembers(idActivity);


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
        TeamMember assignedMember = task.getEmployee();
        members.setValue(new Pair<>(assignedMember.getUsername(),assignedMember.getId()));

        String formatedDate = dateFormat.format(task.getOccurence().getDate());
        date.setText("Date : " + formatedDate);

    }

    public void setActivityController(ActivityController activityController) {
        this.activityController = activityController;
    }

    @FXML
    void saveTask(ActionEvent event) throws IOException, URISyntaxException, InterruptedException {

        JsonObject updateTaskBody = new JsonObject();

        boolean needUpdate = false;

        if(!task.getTitle().equals(title.getText())) {
            updateTaskBody.addProperty("title",title.getText());
            needUpdate = true;
        }

        if(!Objects.equals(task.getDescription(), description.getText())) {
            updateTaskBody.addProperty("description",description.getText());
            needUpdate = true;
        }

        if(!Objects.equals(task.getStatus(), status.getValue())) {
            updateTaskBody.addProperty("status",status.getValue());
            needUpdate = true;
        }

        if(!Objects.equals(task.getPriority(), priority.getValue())) {
            updateTaskBody.addProperty("priority",priority.getValue());
            needUpdate = true;
        }

        int teamMemberId = members.getValue().getValue();

        if(teamMemberId != task.getEmployee().getId()) {
            updateTaskBody.addProperty("id_employee",teamMemberId);
            needUpdate = true;
        }


        if(!needUpdate) {
            return;
        }

        String updateTaskBodyJson = new Gson().toJson(updateTaskBody);

        HttpResponse<String> taskResponse =  APIQuerier.patchRequest("/tasks/" + task.getId(),updateTaskBodyJson);

        if(taskResponse.statusCode() != 200) {
            throw new RuntimeException("failed to save task");
        }

        Stage stage = (Stage) title.getScene().getWindow();
        activityController.refreshTask(null);
        stage.close();

    }

}
