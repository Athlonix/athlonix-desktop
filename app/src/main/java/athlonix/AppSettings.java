package athlonix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class AppSettings {
    public static final String SETTINGS_FILE_PATH = "./settings.txt";
    public static final String themesDirectory = "app/themes";
    static private String theme;
    static private final String serverURL = "https://spingboot.jayllyz.fr";
    public static String appVersion = "";

    public static String getTheme() {
        if(theme.equals("default")) {
            return "primer-light";
        }

        return theme;
    }

    public static String getServerURL() {
        return serverURL;
    }

    public static void loadConfiguration() throws IOException {
        AppSettings.theme = loadTheme();
    }

    private static String loadTheme() throws IOException {
        File file = new File(SETTINGS_FILE_PATH);
        Scanner scanner = new Scanner(file);
        String line = scanner.nextLine();
        scanner.close();
        String[] parts = line.split(":");
        return parts[1].substring(0, parts[1].length() - 1);
    }

    public static void setTheme(String newTheme) {
        File saveFile = new File(SETTINGS_FILE_PATH);

        try (FileWriter fileWriter = new FileWriter(saveFile)) {
            fileWriter.write("theme:"+newTheme+";\n");
            theme = newTheme;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static HashSet<String> getInstalledThemes() {
        File themesPath = new File(themesDirectory);
        if(!themesPath.isDirectory()) {
            throw new RuntimeException("Themes directory does not exist");
        }

        HashSet<String> existingThemes = new HashSet<String>();
        for(String file : Objects.requireNonNull(themesPath.list())) {
            int dotIndex = file.lastIndexOf('.');
            if(dotIndex != -1) {
                String fileName = file.substring(0, dotIndex);
                existingThemes.add(fileName);
            }
        }

        return existingThemes;
    }

    public static void setVersion(String version) {
        appVersion = version;
    }
}
