package athlonix.controllers;
import athlonix.models.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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

    private Task task;

    public void fillDate(Task task) {
        this.task = task;

        title.setText(task.getTitle());
        description.setText(task.getDescription());

        ObservableList<String> priorities = FXCollections.observableArrayList();

        priorities.add("P0");
        priorities.add("P1");
        priorities.add("P2");
        priority.setItems(priorities);
        priority.setValue(task.getPriority());

        title.setText(task.getTitle());
    }
    @FXML
    void modifyDate(ActionEvent event) {

    }

    @FXML
    void saveTask(ActionEvent event) {

    }
}
