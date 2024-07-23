package athlonix.repository;

import athlonix.auth.APIQuerier;
import athlonix.models.Task;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
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

    public void createTask(int idOccurence, String priority, String status, String title, String description, int idEmployee) throws IOException, URISyntaxException, InterruptedException {
        String route = "/activities_exceptions/" + idOccurence + "/tasks";

        JsonObject createTaskBody = new JsonObject();

        createTaskBody.addProperty("priority",priority);
        createTaskBody.addProperty("status",status);
        createTaskBody.addProperty("title",title);
        createTaskBody.addProperty("description",description);
        createTaskBody.addProperty("id_employee",idEmployee);

        String createTaskBodyJson = new Gson().toJson(createTaskBody);
        HttpResponse<String> query = APIQuerier.postRequest(route,createTaskBodyJson);
        if (query.statusCode() != 201) {
            throw new IOException(query.body());
        }
    }

}
