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
    private static URI queryUrl;
    private static HttpRequest.BodyPublisher queryJsonBody;
    private static String queryMethod;

    public static HttpResponse<String> httpQuery(String route,String jsonBody, String method) throws URISyntaxException, IOException, InterruptedException {
        queryUrl = new URI(API_URL + route);
        queryMethod = method;

        queryJsonBody = HttpRequest.BodyPublishers.ofString(jsonBody);

        switch (queryMethod) {
            case "GET":
                return getRequest();
            case "POST":
                return postRequest();
        }

        return null;
    }

    private static HttpResponse<String> postRequest() throws IOException, InterruptedException {
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

    private static HttpResponse<String> getRequest() throws IOException, InterruptedException {
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


}
