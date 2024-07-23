package athlonix.athlonixlauncher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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


            if(version.isEmpty()) {
                version = file;
            }

            if(version.compareTo(file) < 0) {
                version = file;
            }
        }

        if(version.isEmpty()) {
            return null;
        }

        return version;
    }

    public static String getServerUrl() {
        return AppSettings.serverURL;
    }

}
