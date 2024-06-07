package athlonix.auth;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class APIQuerier {

    final static String API_URL = "http://localhost:3101";

    public static HttpResponse<String> postRequest(String route,String jsonBody) throws IOException, InterruptedException, URISyntaxException {

        URI queryUrl = new URI(API_URL + route);
        HttpRequest.BodyPublisher queryJsonBody = HttpRequest.BodyPublishers.ofString(jsonBody);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(queryUrl)
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+Authenticator.AUTH_TOKEN)
                .POST(queryJsonBody)
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        return httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> postRequest(String route) throws IOException, InterruptedException, URISyntaxException {

        URI queryUrl = new URI(API_URL + route);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(queryUrl)
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+Authenticator.AUTH_TOKEN)
                .POST(HttpRequest.BodyPublishers.noBody())
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        return httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
    }


    public static HttpResponse<String> getRequest(String route) throws IOException, InterruptedException, URISyntaxException {

        URI queryUrl = new URI(API_URL + route);

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(queryUrl)
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+Authenticator.AUTH_TOKEN)
                .GET()
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        return httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> deleteRequest(String route) throws IOException, InterruptedException, URISyntaxException {

        URI queryUrl = new URI(API_URL + route);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(queryUrl)
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+Authenticator.AUTH_TOKEN)
                .DELETE()
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpClient httpClient = HttpClient.newBuilder().build();

        return httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
    }


}
