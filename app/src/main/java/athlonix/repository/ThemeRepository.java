package athlonix.repository;

import athlonix.FileDownloader;
import athlonix.auth.ServerQuerier;
import athlonix.models.Theme;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;

public class ThemeRepository {

    final static String SERVER_URL = "http://localhost:8086";

    public List<Theme> getAllThemes() throws IOException, URISyntaxException, InterruptedException {

        String route = "/theme";

        HttpResponse<String> themeResponse = ServerQuerier.getRequest(route);

        String responseString = themeResponse.body();

        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);
        JsonArray jsonData = response.getAsJsonArray();

        Type themeType = new TypeToken<List<Theme>>(){}.getType();
        return gson.fromJson(jsonData, themeType);
    }

    public void downloadTheme(String themeName) throws IOException {
        FileDownloader fileDownloader = new FileDownloader();
        String themeRoute = SERVER_URL+"/theme/"+themeName;
        fileDownloader.downloadFile(themeRoute,"themes");
    }
}
