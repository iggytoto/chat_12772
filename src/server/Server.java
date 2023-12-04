package server;

import com.mysql.cj.jdbc.Driver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class Server {

    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>(); // Список онлайн пользователей
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            ServerSocket serverSocket = new ServerSocket(9123); // Открываем порт для прослушивания
            System.out.println("Сервер запущен");
            while (true){
                Socket socket = serverSocket.accept(); // Ожидаем подключения клиента
                User user = new User(socket); // Создаём объект клиента
                users.add(user); // Добавляем пользователя список онлайн пользователей
                Thread thread = new Thread(()->{
                    String message = null;
                    try {
                        String command;
                        while (true){
                            Message.sendMessage(user, "Для регистрации /reg, \n Для авторизации /login");
                            command = user.getIs().readUTF().toLowerCase();
                            if(command.equals("/reg")){
                                user.reg();
                                break;
                            }else if (command.equals("/login"))
                                if(user.login()) break;
                            else Message.sendMessage(user, "неверная команда");
                        }

                        System.out.println(user.getName()+" подключился");
                        Message.sendMessage(user, user.getName() + " добро пожаловать на сервер!");
                        sendOnlineUsers(users);
                        while (true){
                            message = user.getIs().readUTF();
                            System.out.println(user.getName()+": "+message);
                            for (User user1 : users) {
                                if(user == user1) continue;
                                Message.sendMessage(user1, user.getName()+": "+message);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println(user.getName()+" отключился");
                        users.remove(user);
                        sendOnlineUsers(users);
                    }
                });
                thread.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public static void sendOnlineUsers(ArrayList<User> users){
        JSONArray jsonArray = new JSONArray(); // Список имён пользователей
        users.forEach(user -> {
            jsonArray.add(user.getName());
        });
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("onlineUsers", jsonArray);
        users.forEach(user -> {
            try {
                user.getOut().writeUTF(jsonObject.toJSONString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}