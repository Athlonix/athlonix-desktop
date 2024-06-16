package athlonix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AppSettings {
    public static final String SETTINGS_FILE_PATH = "./settings.txt";
    static private String theme;

    public static String getTheme() {
        if(theme.equals("default")) {
            return "primer-light";
        }

        return theme;
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
