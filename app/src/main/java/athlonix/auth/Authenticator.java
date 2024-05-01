package athlonix.auth;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Authenticator {
    final static String API_URL = "http://localhost:3101/";
    static String AUTH_TOKEN = "";
    static private Credentials user;


    public static void login(String email,String password) throws URISyntaxException, IOException, InterruptedException {
        URI lo = new URI(API_URL + "/auth/login");

        JsonObject loginRequestBody = new JsonObject();

        loginRequestBody.addProperty("email",email);
        loginRequestBody.addProperty("password",password);

        String loginRequestBodyJson = new Gson().toJson(loginRequestBody);

        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(loginRequestBodyJson);

        HttpRequest loginRequest = HttpRequest.newBuilder()
                .uri(lo)
                .header("Content-Type","application/json")
                .POST(bodyPublisher)
                .timeout(Duration.ofSeconds(10))
                .build();
        HttpClient httpClient = HttpClient.newBuilder()
                .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL)).build();

        HttpResponse<String> loginResponse =  httpClient.send(loginRequest, HttpResponse.BodyHandlers.ofString());
        String responseString = loginResponse.body();

        Gson gson = new Gson();

        JsonElement userJson = gson.fromJson(responseString, JsonElement.class);

        JsonObject jsonObject = userJson.getAsJsonObject();

        AUTH_TOKEN = jsonObject.get("token").getAsString();
    }

}