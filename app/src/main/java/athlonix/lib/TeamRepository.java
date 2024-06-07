package athlonix.lib;

import athlonix.auth.APIQuerier;
import athlonix.models.Adress;
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

public class TeamRepository {
    public List<TeamMember> getTeamMembers(int idActivity) throws IOException, URISyntaxException, InterruptedException {
        String route = "/activities/" + idActivity + "/team";

        HttpResponse<String> teamResponse = APIQuerier.getRequest(route);

        String responseString = teamResponse.body();

        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);
        JsonObject jsonData = response.getAsJsonObject();
        JsonArray members = jsonData.getAsJsonArray("members");

        Type adressType = new TypeToken<List<TeamMember>>(){}.getType();

        List<TeamMember> a = gson.fromJson(members, adressType);

        return gson.fromJson(members, adressType);
    }

    public int addTeamMember(int idActivity,int idEmployee) throws IOException, URISyntaxException, InterruptedException {
        String route = "/activities/" + idActivity + "/team/" + idEmployee;

        HttpResponse<String> addMemberQuery = APIQuerier.postRequest(route);

        return addMemberQuery.statusCode();
    }

    public int removeTeamMember(int idActivity,int idEmployee) throws IOException, URISyntaxException, InterruptedException {
        String route = "/activities/" + idActivity + "/team/" + idEmployee;

        HttpResponse<String> addMemberQuery = APIQuerier.deleteRequest(route);

        return addMemberQuery.statusCode();
    }
}
