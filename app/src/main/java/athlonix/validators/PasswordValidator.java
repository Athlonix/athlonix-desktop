package athlonix.validators;

public class PasswordValidator {

    public static void validate(String password) {
        if(password == null || password.equals("")) {
            throw new RuntimeException("Veuillez remplir ce champs");
        }

        if(password.length() < 3) {
            throw new RuntimeException("Le mot de passe doit contenir plus de 2 caractères");
        }

    }

}