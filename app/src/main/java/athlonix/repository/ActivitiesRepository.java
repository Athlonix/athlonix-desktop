package athlonix.repository;

import athlonix.auth.APIQuerier;
import athlonix.models.Activity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;

public class ActivitiesRepository {
    public List<Activity> getAllActivities(String searchParam) throws IOException, URISyntaxException, InterruptedException {
        String route = "/activities";

        if(!searchParam.isEmpty()) {
            route += "?search=" + searchParam;
        }

        HttpResponse<String> activitiesResponse = APIQuerier.getRequest(route);

        String responseString = activitiesResponse.body();

        OfflineActivitiesRepository offlineActivitiesRepository = new OfflineActivitiesRepository();
        offlineActivitiesRepository.saveActivities(responseString);

        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);
        JsonArray dataArray = response.getAsJsonObject().getAsJsonArray("data");

        Type activityListType = new TypeToken<List<Activity>>(){}.getType();

        return gson.fromJson(dataArray, activityListType);
    }
}
