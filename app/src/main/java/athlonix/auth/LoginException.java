package athlonix.auth;

public class LoginException extends Exception {
    public LoginException(String errorMessage) {
        super(errorMessage);
    }
}