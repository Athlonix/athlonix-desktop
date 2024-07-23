package athlonix.repository;

import athlonix.auth.APIQuerier;
import athlonix.models.ActivityOccurence;
import athlonix.models.Task;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ActivityOccurenceRepository {
    public List<ActivityOccurence> getAll(int idActivity, String startDate, String endDate) throws IOException, URISyntaxException, InterruptedException {
        String route = "/activities/" + idActivity + "/occurences?start_date=" + startDate + "&end_date=" + endDate;

        HttpResponse<String> occurenceResponse = APIQuerier.getRequest(route);

        String responseString = occurenceResponse.body();

        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);
        JsonObject jsonData = response.getAsJsonObject();
        JsonArray jsonOccurences = jsonData.getAsJsonArray("occurences");

        Type occurenceType = new TypeToken<List<ActivityOccurence>>(){}.getType();

        return gson.fromJson(jsonOccurences, occurenceType);
    }
}
