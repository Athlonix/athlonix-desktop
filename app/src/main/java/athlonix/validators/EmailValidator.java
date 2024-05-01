package athlonix.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    public static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@" + "[^-][A-Za-z0-9+-]+(\\.[A-Za-z0-9+-]+)*(\\.[A-Za-z]{2,})$";

    public static void validate(String email) {
        if(email == null || email.equals("")) {
            throw new RuntimeException("Veuillez remplir ce champs");
        }

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()) {
            throw new RuntimeException("Le format d'email est invalide");
        }
    }
}