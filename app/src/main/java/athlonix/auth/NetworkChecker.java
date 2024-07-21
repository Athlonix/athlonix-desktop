package athlonix.auth;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkChecker {

    private final static String API_URL = "https://athlonix-api.jayllyz.fr";
    public static boolean isOnline = false;
    public static void init() {
        try {
            final URL url = new URL(API_URL);
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            NetworkChecker.isOnline = true;
        } catch (IOException e) {
            NetworkChecker.isOnline = false;
        }
    }
}