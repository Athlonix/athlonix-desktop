package athlonix.repository;

import athlonix.auth.APIQuerier;
import athlonix.models.ActivityOccurence;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
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

    public int createActivityException(int idActivity, ActivityOccurence activityOccurence) throws IOException, URISyntaxException, InterruptedException {
        String route = "/activities/" + idActivity + "/exceptions";

        JsonObject createExceptionBody = new JsonObject();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        createExceptionBody.addProperty("date",formatter.format(activityOccurence.getDate()));
        createExceptionBody.add("min_participants", JsonNull.INSTANCE);
        createExceptionBody.add("max_participants", JsonNull.INSTANCE);

        Gson gsonSerializer = new GsonBuilder().serializeNulls().create();
        String createExceptionBodyJson = gsonSerializer.toJson(createExceptionBody);

        HttpResponse<String> query = APIQuerier.postRequest(route,createExceptionBodyJson);
        if (query.statusCode() != 201) {
            throw new IOException(query.body());
        }

        String responseString = query.body();
        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);
        JsonObject jsonData = response.getAsJsonObject();
        return jsonData.get("id").getAsInt();
    }
}
