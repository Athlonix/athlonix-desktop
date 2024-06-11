package athlonix.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class CreateTaskController {
    @FXML
    private Button choose_date_button;

    @FXML
    private Text date;

    @FXML
    private TextArea description;

    @FXML
    private ComboBox<String> members;

    @FXML
    private ChoiceBox<String> priority;

    @FXML
    private Button save_button;

    @FXML
    private ChoiceBox<String> status;

    @FXML
    private TextField title;

    @FXML
    void choseDate(ActionEvent event) {

    }

    @FXML
    void saveTask(ActionEvent event) {

    }
}
