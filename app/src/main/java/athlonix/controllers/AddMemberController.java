package athlonix.controllers;

import athlonix.lib.EmployeeRepository;
import athlonix.lib.TeamRepository;
import athlonix.models.Activity;
import athlonix.models.TeamMember;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddMemberController {

    @FXML
    private TableView<TeamMember> teamTable;
    @FXML
    private TableColumn<TeamMember, String> member_actions;

    @FXML
    private TableColumn<TeamMember, String> member_firstName;

    @FXML
    private TableColumn<TeamMember, String> member_name;

    @FXML
    private TableColumn<TeamMember, String> member_username;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchInput;

    private int idActivity;

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    private List<Integer> idMembers;

    public void setMembersId(List<Integer> idMembers) {
        this.idMembers = idMembers;
    }

    private ActivityController activityController;
    public void setActivityController(ActivityController activityController) {
        this.activityController = activityController;
    }

    EmployeeRepository employeeRepository = new EmployeeRepository();
    TeamRepository teamRepository = new TeamRepository();

    public void fillActivityData() {
        try {
            List<TeamMember> employees =  employeeRepository.getEmployees("");
            fillEmployeeData(employees);
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }

    @FXML
    public void searchMembers() throws IOException {
        String searchParam = searchInput.getText();
        try {
            List<TeamMember> employees =  employeeRepository.getEmployees(searchParam);
            fillEmployeeData(employees);
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }

    }

    private void fillEmployeeData(List<TeamMember> employees) {
        teamTable.getItems().clear();

        ObservableList<TeamMember> list = FXCollections.observableArrayList();
        list.addAll(employees);

        member_firstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        member_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        member_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        member_actions.setCellValueFactory(cellData -> {
            return new SimpleStringProperty("dummy");
        });

        member_actions.setCellFactory(memberActionFoctory);

        teamTable.setItems(list);


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

                    TeamMember member = getTableView().getItems().get(getIndex());

                    for(int id : idMembers) {
                     if (id == member.getId()) {
                         setGraphic(new Text("IN TEAM"));
                         return;
                     }
                    }

                    Button addButton = new Button("Ajouter");
                    addButton.getStyleClass().add("accent");

                    addButton.setOnAction(event -> {

                        try {
                            int status = teamRepository.addTeamMember(idActivity, member.getId());

                            if(status != 201) {
                                throw new RuntimeException("wrong status code : " + status);
                            }

                            idMembers.add(member.getId());
                            activityController.fillTeamData();
                            searchMembers();


                        } catch (Exception e) {
                            System.out.println("failed to add member");
                        }

                    });

                    setText(null);
                    setGraphic(addButton);

                }
            }

        };
    };

}
