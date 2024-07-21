package athlonix.athlonixlauncher;

import java.io.File;
import java.util.Objects;

public class AppSettings {
    static private final String serverURL = "https://spingboot.jayllyz.fr";


    public static String getCurrentVersion() {
        File themesPath = new File(".");
        if(!themesPath.isDirectory()) {
            throw new RuntimeException("Themes directory does not exist");
        }

        String version = "";
        for(String file : Objects.requireNonNull(themesPath.list())) {
            if(!file.startsWith("athlonix-")) {
                continue;
            }
            return file;
        }

        return null;
    }

    public static String getServerUrl() {
        return AppSettings.serverURL;
    }

}
