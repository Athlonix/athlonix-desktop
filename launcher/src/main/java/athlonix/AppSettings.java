package athlonix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

public class AppSettings {
    static private final String serverURL = "http://localhost:8086";


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
