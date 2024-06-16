package athlonix.repository;

import athlonix.models.Activity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

public class OfflineActivitiesRepository {

    public static final String SAVE_PATH = "./save.json";

    public void saveActivities(String data) {
        File saveFile = new File(SAVE_PATH);

        try (FileWriter fileWriter = new FileWriter(saveFile)) {
            fileWriter.write(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Activity> getAllActivities() {
        File saveFile = new File(SAVE_PATH);
        StringBuilder stringBuilder = new StringBuilder();

        try (Scanner scanner = new Scanner(saveFile)) {
            while(scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String activitiesData = stringBuilder.toString();
        Gson gson = new Gson();

        JsonElement response = gson.fromJson(activitiesData, JsonElement.class);
        JsonArray dataArray = response.getAsJsonObject().getAsJsonArray("data");

        Type activityListType = new TypeToken<List<Activity>>(){}.getType();

        return gson.fromJson(dataArray, activityListType);
    }
}
