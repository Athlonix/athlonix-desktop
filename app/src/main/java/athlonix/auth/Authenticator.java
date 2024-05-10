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
    final static String API_URL = "http://localhost:3101";
    static String AUTH_TOKEN = "";
    static String USER_ID;
    static String USER_NAME;


    public static void login(String email,String password) throws URISyntaxException, IOException, InterruptedException, LoginException {
        JsonObject loginRequestBody = new JsonObject();

        loginRequestBody.addProperty("email","user@example.com");
        loginRequestBody.addProperty("password","Respons11!");

        String loginRequestBodyJson = new Gson().toJson(loginRequestBody);

        HttpResponse<String> loginResponse =  APIQuerier.postRequest("/auth/login",loginRequestBodyJson);

        if(loginResponse.statusCode() == 401) {
            throw new LoginException("Identifiants invalides");
        }

        String responseString = loginResponse.body();

        Gson gson = new Gson();

        JsonElement response = gson.fromJson(responseString, JsonElement.class);

        JsonObject responseJson = response.getAsJsonObject();
        JsonObject userJson = responseJson.get("user").getAsJsonObject();

        USER_NAME = userJson.get("username").getAsString();
        USER_ID = userJson.get("id").getAsString();
        AUTH_TOKEN = responseJson.get("token").getAsString();

    }

}