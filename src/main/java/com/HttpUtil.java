package com;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.dto.User;
import com.google.gson.reflect.TypeToken;


public class HttpUtil {

    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();
//    private static final Gson GSON = new Gson();

    //GET**************************************
    public static HttpResponse<String> sendGet(String url) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
//                .method("GET", HttpRequest.BodyPublishers.noBody())
                .GET()
                .build();

        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static <T> List<T> getList(String url, Class<T> clazz) throws IOException, InterruptedException {
        HttpResponse<String> response = sendGet(url);
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Type typeToken = TypeToken
                .getParameterized(List.class, clazz)
                .getType();

        return new Gson().fromJson(response.body(), typeToken);
    }


//    //POST***************************
    public static HttpResponse<String> sendPost(String url, User user) throws IOException, InterruptedException {

        String body = new Gson().toJson(user);
//        System.out.println(json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
//                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-type", "application/json")
                .build();

        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
//        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
//        HttpResponse<String> responsePost = futureResponse.get();
//        assertThat(responsePost.statusCode()).isEqualTo(200);
//        assertThat(responsePost.body()).isEqualTo("{\"message\":\"ok\"}");
    }


    //DELETE***************************************
    public static void sendDelete(String url, int id) throws IOException, InterruptedException {
//    public static void sendDelete(URI uri, User user) throws IOException, InterruptedException {
//        String body = new Gson().toJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .DELETE()
//                .method("DELETE", HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> responseDelete = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("User id: " + id + " is deleted. \nStatus code " + responseDelete.statusCode());
//        System.out.println(CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).statusCode());
    }

    //PUT***************************************
    public static HttpResponse<String> sendPut(String url, User user) throws IOException, InterruptedException {

        String body = new Gson().toJson(user);
//        System.out.println(json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .build();

        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
