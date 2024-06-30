package athlonix;

import athlonix.AppSettings;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ServerQuerier {

    final static String SERVER_URL = AppSettings.getServerUrl();

    public static HttpResponse<String> getRequest(String route) throws IOException, InterruptedException, URISyntaxException {

        URI queryUrl = new URI(SERVER_URL + route);

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(queryUrl)
                .header("Content-Type","application/json")
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        return httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
    }


}
