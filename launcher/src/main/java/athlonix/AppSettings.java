package athlonix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

public class AppSettings {
    static private final String serverURL = "http://localhost:8086";


//    public static HashSet<String> getCurrentVersion() {
////        File themesPath = new File(themesDirectory);
//        if(!themesPath.isDirectory()) {
//            throw new RuntimeException("Themes directory does not exist");
//        }
//
//        HashSet<String> existingThemes = new HashSet<String>();
//        for(String file : Objects.requireNonNull(themesPath.list())) {
//            int dotIndex = file.lastIndexOf('.');
//            if(dotIndex != -1) {
//                String fileName = file.substring(0, dotIndex);
//                existingThemes.add(fileName);
//            }
//        }
//
//        return existingThemes;
//    }

    public static String getServerUrl() {
        return AppSettings.serverURL;
    }

}
