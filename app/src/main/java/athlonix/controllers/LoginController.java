package athlonix.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {
    public PasswordField password;
    public Text forgotPassword;
    public Text emailError;
    public Text passwordError;
    public Button loginButton;
    @FXML
    public TextField email;

    public LoginController() {
    }

    public void login(ActionEvent actionEvent) {
        System.out.println("teset");
    }
}