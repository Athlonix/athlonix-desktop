package athlonix.controllers;
import athlonix.auth.APIQuerier;
import athlonix.lib.TeamRepository;
import athlonix.models.Activity;
import athlonix.models.Adress;
import athlonix.models.TeamMember;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ActivityController {

    private Activity activity;
    @FXML
    private VBox adressTab;
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
    @FXML
    private TableView<TeamMember> teamTable;
    @FXML
    private TableColumn<TeamMember, String> member_firstName;
    @FXML
    private TableColumn<TeamMember, String> member_name;
    @FXML
    private TableColumn<TeamMember, String> member_username;
    @FXML
    private TableColumn<TeamMember, String> member_actions;

    TeamRepository teamRepository = new TeamRepository();

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

        try {
            if(activity.getIdAddress() == 0) {
              throw new RuntimeException();
            }
            Adress adress = getActivityAdress(activity.getIdAddress());
            fillAdressInformations(adress);

        } catch (Exception e) {
            adressTab.getChildren().clear();
            adressTab.getChildren().add(new Text("Aucune adresse trouvée associée à cette activité"));
        }

        fillTeamData();
    }

    private Adress getActivityAdress(int idAdress) throws IOException, URISyntaxException, InterruptedException {
        String route = "/addresses/" + idAdress;

        HttpResponse<String> adressResponse = APIQuerier.getRequest(route);

        String responseString = adressResponse.body();

        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);
        JsonObject jsonAdress = response.getAsJsonObject();

        Type adressType = new TypeToken<Adress>(){}.getType();

        return gson.fromJson(jsonAdress, adressType);
    }

    private void fillAdressInformations(Adress address) {
        adress_street.setText("Rue : " + address.getRoad());
        adress_number.setText("Numero : " + Integer.toString(address.getNumber()));
        adress_postcode.setText("Ville : " + address.getPostal_code());
        adress_city.setText("Ville : " + address.getCity());
        adress_complement.setText("Complement : " + address.getComplement());
        adress_name.setText("Nom : " + address.getName());
    }

    private void fillTeamData() {
        try{
        List<TeamMember> teamMembers = teamRepository.getTeamMembers(activity.getId());

        ObservableList<TeamMember> list = FXCollections.observableArrayList();
        list.addAll(teamMembers);

        member_firstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        member_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        member_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        member_actions.setCellValueFactory(cellData -> {
            return new SimpleStringProperty("dummy");
        });

        member_actions.setCellFactory(memberActionFoctory);

        teamTable.setItems(list);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    Callback<TableColumn<TeamMember, String>, TableCell<TeamMember, String>> memberActionFoctory = (TableColumn<TeamMember, String> param) -> {

        return new TableCell<TeamMember, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);

                } else {

                    Button deleteButton = new Button("Retirer");
                    deleteButton.getStyleClass().add("danger");

                    deleteButton.setOnAction(event -> {
                        TeamMember member = getTableView().getItems().get(getIndex());

                        try {
                            int status = teamRepository.removeTeamMember(activity.getId(), member.getId());

                            if(status != 200) {
                                throw new RuntimeException("wrong status code : " + status);
                            }

                            fillTeamData();
                        } catch (Exception e) {
                            System.out.println("failed to remove team member");
                        }

                    });

                    setText(null);
                    setGraphic(deleteButton);

                }
            }

        };
    };

}
