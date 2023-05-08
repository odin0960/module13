package com;

import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.dto.Comment;
import com.dto.Post;
import com.dto.ToDo;
import com.dto.User;
import com.google.gson.Gson;

public class HttpTest {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        String url = "https://jsonplaceholder.typicode.com";

        //GET
        HttpResponse<String> response = HttpUtils.sendGet(url);
        System.out.println("Server response for GET - " + response.statusCode());

        //*** створення нового об'єкта ***
        System.out.println("*** створення нового об'єкта ***");
        User newUser = new User();
        newUser.setId(22);
        newUser.setName("Robert Bobkoff");
        newUser.setUsername("Bobb");

        HttpResponse<String> createNewUser = HttpUtils.sendPost(url + "/users", newUser);

        System.out.println("Status code " + createNewUser.statusCode());
        System.out.println("New user is created \n" + createNewUser.body());
//        System.out.println(new Gson().fromJson(createNewUser.body(), User.class));
        System.out.println("======================================================\n");

        // *** оновлення об'єкту ***
        System.out.println("*** оновлення об'єкту ***");
        User updateUser = new User();
        int id = 5;
        updateUser.setName("Maria Mirabella");
        updateUser.setUsername("Masha");
        HttpResponse<String> responseUpdateUser = HttpUtils.sendPut(url + "/users/" + id, updateUser);
        System.out.println("Status code " + responseUpdateUser.statusCode());
        System.out.println("Updated user id:" + id);
        System.out.println(responseUpdateUser.body());
        System.out.println("======================================================\n");

        //*** видалення об'єкта ***
        System.out.println("*** видалення об'єкта ***");
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("To delete user enter id:");
        int deleteId = scanner1.nextInt();
        HttpUtils.sendDelete(url + "/users/" + deleteId, deleteId);
        System.out.println("======================================================\n");


        //*** отримання інформації про всіх користувачів ***
        System.out.println("*** отримання інформації про всіх користувачів ***");
        System.out.println("All users:");
        List<User> userList = HttpUtils.getList(url + "/users", User.class);
        userList.forEach(System.out::println);
        // записати інфо про всіх користувачів у файл "users.json"
        HttpResponse<String> responseAll = HttpUtils.sendGet(url + "/users");
        FileWriter fw1 = new FileWriter("./files/users.json");
        fw1.write(responseAll.body());
        fw1.close();
        System.out.println("======================================================\n");

        //*** отримання інформації про користувача по username ***
        System.out.println("*** отримання інформації про користувача по username ***");
        String username = "Kamren";

        HttpResponse<String> responseUserName = HttpUtils.sendGet(url + "/users?username=" + username);
        System.out.println("Status code " + responseUserName.statusCode());
        System.out.println("User with username " + username + ":");
        System.out.println(responseUserName.body());
//        User userName = new Gson().fromJson(responseUserName.body(), User.class);
//        System.out.println("User info: \n" + userName);
        System.out.println("======================================================\n");

        //*** отримання інформації про користувача по id ***
        // (для тестів ввести один id для усіх наступних задач)
        System.out.println("*** отримання інформації про користувача по id ***");
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Enter user id:");
        int inputId = scanner2.nextInt();

        HttpResponse<String> responseId = HttpUtils.sendGet(url + "/users/" + inputId);
        System.out.println("Status code " + responseId.statusCode());
//        System.out.println("User info: \n" + responseId.body());
        User user = new Gson().fromJson(responseId.body(), User.class);
        System.out.println("User info: \n" + user);
        System.out.println("======================================================\n");

        //*** всі коментарі до останнього поста певного користувача
        System.out.println("*** всі коментарі до останнього поста певного користувача");
//        Scanner scanner3 = new Scanner(System.in);
//        System.out.println("To get the last user comments enter its id:");
//        int userId = scanner3.nextInt();

        List<Post> userPosts = HttpUtils.getList(url + "/users/" + inputId + "/posts", Post.class);

        int lastPost = userPosts.stream()
                .map(Post::getId)
                .max(Integer::compare).get();

        List<Comment> comments = HttpUtils.getList(url + "/posts/" + lastPost + "/comments", Comment.class);
        System.out.println("Comments for the last user post - " + lastPost);
//        comments.forEach(System.out::println);
        comments.forEach(c -> System.out.println(c.getBody()));

        HttpResponse<String> responseComment = HttpUtils.sendGet(url + "/posts/" + lastPost + "/comments");
        FileWriter fw = new FileWriter("./files/user-" + inputId + "-post-" + lastPost + "-comments.json");
        fw.write(responseComment.body());
        fw.close();
        System.out.println("======================================================\n");

        //всі відкриті задачі для користувача inputId
        System.out.println("всі відкриті задачі для користувача: " + inputId);
//        Scanner scanner4 = new Scanner(System.in);
//        System.out.println("Enter user id:");
//        int userId = scanner4.nextInt();
        List<ToDo> todos = HttpUtils.getList(url + "/users/" + inputId + "/todos", ToDo.class);

        todos.stream()
                .filter(t -> !t.isCompleted())
                .forEach(t -> System.out.println(t.getId() + ": " + t.getTitle()));
//                .forEach(System.out::println);

        HttpResponse<String> responseToDos = HttpUtils.sendGet(url + "/users/" + inputId + "/todos");
        FileWriter fw3 = new FileWriter("./files/todos.json");
        fw3.write(responseToDos.body());
        fw3.close();
        System.out.println("======================================================\n");
    }
}
