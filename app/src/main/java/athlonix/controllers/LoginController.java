package athlonix.controllers;

import athlonix.auth.Authenticator;
import athlonix.validators.EmailValidator;
import athlonix.validators.PasswordValidator;
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
        validateLoginFormat();
    }

    private void validateLoginFormat() {
        String emailText = email.getText();
        String passwordText = password.getText();

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

        Authenticator auth = new Authenticator();
        try {

            auth.login(emailText,passwordText);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void SetEmailError(String error) {
        emailError.setText(error);
        emailError.setStyle("-fx-fill: -color-danger-5");
        email.setStyle("-fx-border-color: -color-danger-5");
    }

    private void ResetEmailStatus() {
        emailError.setText("");
        emailError.setStyle("-fx-fill: -color-base-8");
        email.setStyle("-fx-border-color: -color-base-8");
    }

    private void SetPasswordError(String error) {
        passwordError.setText(error);
        passwordError.setStyle("-fx-fill: -color-danger-5");
        password.setStyle("-fx-border-color: -color-danger-5");
    }

    private void ResetPasswordStatus() {
        passwordError.setText("");
        passwordError.setStyle("-fx-fill: -color-base-8");
        password.setStyle("-fx-border-color: -color-base-8");
    }


}