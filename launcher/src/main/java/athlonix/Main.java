package athlonix;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("starting athlonix!");

        try {
            Version mostRecentVersion = getMostRecentVersion();
            System.out.println(mostRecentVersion.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static Version getMostRecentVersion() throws IOException, URISyntaxException, InterruptedException {
        String route = "/version";

        HttpResponse<String> versionResponse = ServerQuerier.getRequest(route);

        String responseString = versionResponse.body();

        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);
        JsonArray jsonData = response.getAsJsonArray();

        Type versionType = new TypeToken<List<Version>>(){}.getType();
        return gson.fromJson(jsonData, versionType);
    }
}