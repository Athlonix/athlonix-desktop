package athlonix.repository;

import athlonix.AppSettings;
import athlonix.FileDownloader;
import athlonix.PluginManager;
import athlonix.models.Plugin;
import athlonix.auth.ServerQuerier;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;

public class PluginRepository {
    final static String SERVER_URL = AppSettings.getServerURL();

    public List<Plugin> getAllPlugins() throws IOException, URISyntaxException, InterruptedException {

        String route = "/plugin";

        HttpResponse<String> pluginResponse = ServerQuerier.getRequest(route);

        String responseString = pluginResponse.body();

        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);
        JsonArray jsonData = response.getAsJsonArray();

        Type pluginType = new TypeToken<List<Plugin>>(){}.getType();
        return gson.fromJson(jsonData, pluginType);
    }

    public Thread downloadPlugin(String pluginName) throws IOException {
        FileDownloader fileDownloader = new FileDownloader();
        String pluginRoute = SERVER_URL+"/plugin/"+pluginName;
        return fileDownloader.downloadFile(pluginRoute,"plugins");
    }

    public void deletePlugin(String name) throws IOException {
        PluginManager.deletePlugin(name);
    }
}
