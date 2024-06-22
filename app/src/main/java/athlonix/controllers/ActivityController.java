package athlonix.controllers;
import athlonix.SceneLoader;
import athlonix.auth.APIQuerier;
import athlonix.repository.TaskRepository;
import athlonix.repository.TeamRepository;
import athlonix.models.Activity;
import athlonix.models.Adress;
import athlonix.models.Task;
import athlonix.models.TeamMember;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
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

    @FXML
    private Button addMemberButton;

    @FXML
    private DatePicker taskEndDate;

    @FXML
    private Button taskRefreshButton;

    @FXML
    private DatePicker taskStartDate;

    @FXML
    private TableView<Task> task_table;

    @FXML
    private TableColumn<Task, String> task_title;
    @FXML
    private TableColumn<Task, String> task_action;

    @FXML
    private TableColumn<Task, String> task_date;

    @FXML
    private TableColumn<Task, String> task_employee;

    @FXML
    private TableColumn<Task, String> task_priority;

    @FXML
    private TableColumn<Task, String> task_status;

    @FXML
    private Button addTaskButton;

    TeamRepository teamRepository = new TeamRepository();
    TaskRepository taskRepository = new TaskRepository();

    List<TeamMember> teamMembers;

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

        LocalDate start_date  = LocalDate.now();
        LocalDate end_date = start_date.plusDays(7);

        taskStartDate.setValue(start_date);
        taskEndDate.setValue(end_date);

        refreshTask(null);
    }
    @FXML
    void refreshTask(ActionEvent event) {
        LocalDate startDate = taskStartDate.getValue();
        LocalDate endDate = taskEndDate.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        fillTasksData(startDate.format(formatter),endDate.format(formatter));
    }

    @FXML
    void addTask(ActionEvent event) {

    }

    private void fillTasksData(String startDate, String endDate) {

        TaskRepository repository = new TaskRepository();
        List<Task> tasks;

        try {
            tasks = repository.getAllActivityTasks(activity.getId(),startDate,endDate);
        } catch (Exception e) {
            throw new RuntimeException("Unable to fetch tasks");
        }

        ObservableList<Task> taskList = FXCollections.observableArrayList();
        taskList.addAll(tasks);

        task_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        task_priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        task_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        task_date.setCellValueFactory(cellData -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            Task task = cellData.getValue();
            Date date = task.getOccurence().getDate();
            String formattedDate = dateFormat.format(date);
            return new SimpleStringProperty(formattedDate);
        });


        task_employee.setCellValueFactory(cellData -> {
            Task task = cellData.getValue();
            return new SimpleStringProperty(task.getEmployee().getUsername());
        });

        task_action.setCellValueFactory(cellData -> {
            return new SimpleStringProperty("dummy");
        });
        task_action.setCellFactory(taskActionFoctory);

        task_table.setItems(taskList);
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

    public void fillTeamData() {
        try{
        this.teamMembers = teamRepository.getTeamMembers(activity.getId());

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

    @FXML
    void addMember(ActionEvent event) {
        try {
            showAddEmployeePage();
        } catch (IOException e) {
            System.out.println("error while opening employees add page");
        }
    }

    private void showAddEmployeePage() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/add-member-view.fxml"));
        Parent root = fxmlLoader.load();

        AddMemberController addMemberController = fxmlLoader.getController();
        addMemberController.setIdActivity(activity.getId());
        addMemberController.setMembersId(teamMembers.stream().map(TeamMember::getId).collect(Collectors.toList()));
        addMemberController.setActivityController(this);
        addMemberController.fillActivityData();

        Scene addMemberScene = SceneLoader.GetScene(root);
        Stage stage = new Stage();
        stage.setTitle("Add team member to activity");
        stage.setScene(addMemberScene);
        stage.show();
    }

    private void showEditTaskPage(Task task) throws IOException, URISyntaxException, InterruptedException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/task-view.fxml"));
        Parent root = fxmlLoader.load();

        TaskController taskController = fxmlLoader.getController();
        taskController.fillData(task, activity.getId());
        taskController.setActivityController(this);

        Scene editTaskScene = SceneLoader.GetScene(root);
        Stage stage = new Stage();
        stage.setTitle(task.getTitle());
        stage.setScene(editTaskScene);

        stage.show();
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

                            if (status != 200) {
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

        Callback<TableColumn<Task, String>, TableCell<Task, String>> taskActionFoctory = (TableColumn<Task, String> taskParam) -> {

            return new TableCell<Task, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {

                        Button modifyButton = new Button("Modifier");
                        modifyButton.getStyleClass().add("accent");

                        Button deleteButton = new Button("Supprimer");
                        deleteButton.getStyleClass().add("danger");

                        deleteButton.setOnAction(event -> {
                            Task task = getTableView().getItems().get(getIndex());
                            try {
                                int status = taskRepository.deleteTask(task.getId());

                                if(status != 200) {
                                    throw new RuntimeException("task could not be deleted");
                                }

                                refreshTask(null);
                            } catch (Exception e) {
                                System.out.println("error fetching");
                            }
                         });

                        modifyButton.setOnAction(event -> {
                            Task task = getTableView().getItems().get(getIndex());
                            try {
                                showEditTaskPage(task);
                            } catch (IOException | URISyntaxException | InterruptedException e) {
                                System.out.println("failed to open edit task");
                            }
                        });

                        HBox hbox = new HBox(10);
                        hbox.getChildren().addAll(deleteButton, modifyButton);

                        setText(null);
                        setGraphic(hbox);

                    }
                }
        };
    };

}
