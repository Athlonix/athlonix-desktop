package athlonix.lib;

import athlonix.auth.APIQuerier;
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

public class EmployeeRepository {
    public List<TeamMember> getEmployees(String search) throws IOException, URISyntaxException, InterruptedException {
        String route = "/users?role=employee&take=20";

        if(search != null && !search.isEmpty()) {
            route += "&search=" + search;
        }

        HttpResponse<String> employeesResponse = APIQuerier.getRequest(route);

        String responseString = employeesResponse.body();
        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);
        JsonObject jsonData = response.getAsJsonObject();
        JsonArray employees = jsonData.getAsJsonArray("data");

        Type adressType = new TypeToken<List<TeamMember>>(){}.getType();

        return gson.fromJson(employees, adressType);
    }
}
