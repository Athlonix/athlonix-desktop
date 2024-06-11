package athlonix.lib;

import athlonix.auth.APIQuerier;
import athlonix.models.Task;
import athlonix.models.TeamMember;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;

public class TaskRepository {
    public List<Task> getAllActivityTasks(int idActivity, String startDate, String endDate) throws IOException, URISyntaxException, InterruptedException {

        String route = "/activities/" + idActivity + "/tasks?start_date=" + startDate + "&end_date=" + endDate;

        HttpResponse<String> tasksResponse = APIQuerier.getRequest(route);

        String responseString = tasksResponse.body();

        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);
        JsonObject jsonData = response.getAsJsonObject();
        JsonArray jsonTasks = jsonData.getAsJsonArray("data");

        Type taskType = new TypeToken<List<Task>>(){}.getType();

        return gson.fromJson(jsonTasks, taskType);
    }

    public Task getTask(int idTask) throws IOException, URISyntaxException, InterruptedException {

        String route = "/tasks/" + idTask;

        HttpResponse<String> tasksResponse = APIQuerier.getRequest(route);

        String responseString = tasksResponse.body();

        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);
        JsonObject jsonData = response.getAsJsonObject();
        Type taskType = new TypeToken<Task>(){}.getType();

        return gson.fromJson(jsonData, taskType);
    }

    public int deleteTask(int idTask) throws IOException, URISyntaxException, InterruptedException {
        String route = "/tasks/" + idTask;

        HttpResponse<String> addMemberQuery = APIQuerier.deleteRequest(route);

        return addMemberQuery.statusCode();
    }

}
