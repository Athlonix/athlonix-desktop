package athlonix.controllers;
import athlonix.models.Activity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ActivityController {


    private Activity activity;

    @FXML
    private Text activity_description;

    @FXML
    private Text activity_name;

    @FXML
    private Text activity_sport;

    @FXML
    private Text adress_city;

    @FXML
    private Text adress_complement;

    @FXML
    private Text adress_country;

    @FXML
    private Text adress_name;

    @FXML
    private Text adress_number;

    @FXML
    private Text adress_postcode;

    @FXML
    private Text adress_street;

    @FXML
    private Text created_at;

    @FXML
    private Text number_participants;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void fillActivityData() {
        activity_description.setText(activity.getDescription());
        activity_name.setText(activity.getName());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        created_at.setText("Commence le " + dateFormat.format(activity.getStartDate()));
        activity_sport.setText(activity.getSport().getName());
        number_participants.setText("De " + activity.getMinParticipants() + " à " + activity.getMaxParticipants());
    }

}
