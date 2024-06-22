package athlonix.controllers;

import athlonix.SceneLoader;
import athlonix.auth.Authenticator;
import athlonix.auth.LoginException;
import athlonix.auth.NetworkChecker;
import athlonix.validators.EmailValidator;
import athlonix.validators.PasswordValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private BorderPane rootPane;

    public PasswordField password;
    public Text forgotPassword;
    public Text emailError;
    public Text passwordError;
    public Text globalError;
    public Button loginButton;
    @FXML
    public TextField email;

    public LoginController() {
    }

    public void login(ActionEvent actionEvent) {
        validateLoginFormat();
    }

    private void validateLoginFormat() {
//        String emailText = email.getText();
//        String passwordText = password.getText();
        String emailText = "admin@example.com";
        String passwordText = "admin123!";

        try {
            EmailValidator.validate(emailText);
            ResetEmailStatus();
        } catch (Exception e) {
            SetEmailError(e.getMessage());
        }

        try {
            PasswordValidator.validate(passwordText);
            ResetPasswordStatus();
        } catch (Exception e) {
            SetPasswordError(e.getMessage());
        }

        try {
            Authenticator.login(emailText,passwordText);
            ResetGlobalStatus();
            openDashboard();

        } catch (LoginException e) {
            SetGlobalError(e.getMessage());
        } catch (Exception e) {
            SetGlobalError("Erreur du serveur lors de l'authentification");
        }

    }

    private void SetEmailError(String error) {
        emailError.setText(error);
        emailError.setStyle("-fx-fill: -color-danger-5");
        email.setStyle("-fx-border-color: -color-danger-5");
    }

    private void ResetEmailStatus() {
        emailError.setText("");
        emailError.setStyle("-fx-fill: none");
        email.setStyle("-fx-border-color: none");
    }

    private void SetPasswordError(String error) {
        passwordError.setText(error);
        passwordError.setStyle("-fx-fill: -color-danger-5");
        password.setStyle("-fx-border-color: -color-danger-5");
    }

    private void ResetPasswordStatus() {
        passwordError.setText("");
        passwordError.setStyle("-fx-fill: none");
        password.setStyle("-fx-border-color: none");
    }

    private void SetGlobalError(String error) {
        globalError.setStyle("-fx-fill: -color-danger-5");
        globalError.setText(error);
    }

    private void ResetGlobalStatus() {
        globalError.setText("");
        globalError.setStyle("-fx-fill: none");
    }

    private void openDashboard() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/dashboard-view.fxml"));
        Parent root = fxmlLoader.load();

        DashboardController dashboardController = fxmlLoader.getController();

        Scene scene = SceneLoader.GetScene(root);
        Stage stage = new Stage();
        stage.setTitle("dashboard");
        stage.setScene(scene);

        Stage loginStage = (Stage) loginButton.getScene().getWindow();
        loginStage.close();

        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(NetworkChecker.isOnline) {
           return;
        }

        HBox band = new HBox();
        band.setPrefHeight(40);
        band.setSpacing(0);
        band.setAlignment(Pos.CENTER);
        Label label = new Label("Il semble que vous soyez hors ligne. ");
        Hyperlink link = new Hyperlink("Passer en mode hors ligne");
        band.getChildren().addAll(label, link);
        rootPane.setTop(band);

        link.setOnAction(event -> {
            try {
                openDashboard();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}