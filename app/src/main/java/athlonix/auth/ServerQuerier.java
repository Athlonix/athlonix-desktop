package athlonix.auth;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ServerQuerier {

    final static String SERVER_URL = "http://localhost:8086";

    public static HttpResponse<String> postRequest(String route,String jsonBody) throws IOException, InterruptedException, URISyntaxException {

        URI queryUrl = new URI(SERVER_URL + route);
        HttpRequest.BodyPublisher queryJsonBody = HttpRequest.BodyPublishers.ofString(jsonBody);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(queryUrl)
                .header("Content-Type","application/json")
                .POST(queryJsonBody)
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        return httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> postRequest(String route) throws IOException, InterruptedException, URISyntaxException {

        URI queryUrl = new URI(SERVER_URL + route);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(queryUrl)
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        return httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
    }


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

    public static HttpResponse<String> deleteRequest(String route) throws IOException, InterruptedException, URISyntaxException {

        URI queryUrl = new URI(SERVER_URL + route);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(queryUrl)
                .header("Content-Type","application/json")
                .DELETE()
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        return httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> patchRequest(String route,String jsonBody) throws IOException, InterruptedException, URISyntaxException {

        URI queryUrl = new URI(SERVER_URL + route);
        HttpRequest.BodyPublisher queryJsonBody = HttpRequest.BodyPublishers.ofString(jsonBody);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(queryUrl)
                .header("Content-Type","application/json")
                .method("PATCH",queryJsonBody)
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        return httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
    }


}
