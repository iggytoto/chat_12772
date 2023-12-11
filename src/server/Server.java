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
                            command = (Message.readMessage(user)).getMsg().toLowerCase(); // Ждём команду от пользователя
                            if(command.equals("/reg")){ // Если пользователь ввёл /reg
                                user.reg(); // Вызываем метод регистрации пользователя
                                break;
                            }else if (command.equals("/login"))
                                if(user.login()) break;
                            else Message.sendMessage(user, "неверная команда");
                        }

                        System.out.println(user.getName()+" подключился");
                        Message.sendMessage(user, user.getName() + ", добро пожаловать на сервер!");
                        sendOnlineUsers(users);
                        Message.loadHistoryMessage(user);
                        while (true){
                            Message msg = Message.readMessage(user);
                            message = msg.getMsg(); // Сообщение
                            //int formId = msg.getFromId(); // От кого
                            int toId = msg.getToId(); // Кому
                            System.out.println(user.getName()+": "+message);
                            msg.save();
                            for (User user1 : users) {
                                if(user == user1 || (toId!=0 && toId != user1.getId())) continue;
                                Message.sendMessage(user1, user.getName()+": "+message);
                            }
                        }
                    } catch (Exception e) {
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
        JSONArray jsonArray = new JSONArray(); // Список пользователей
        users.forEach(user -> { // Перебираем пользователей
            JSONObject jsonUser = new JSONObject(); // Создаём JSON объект для хранения информации о пользователе
            jsonUser.put("name", user.getName()); // Записываем имя пользователя
            jsonUser.put("id", user.getId()); // Записываем ID пользователя
            jsonArray.add(jsonUser); // Добавляем JSON объект пользователя в коллекцию JSONArray
        });
        JSONObject jsonObject = new JSONObject(); // JSON объект который отправится клиенту
        jsonObject.put("onlineUsers", jsonArray); // Кладём в него список пользователей
        users.forEach(user -> { // Перебираем всех пользователей online
            try {
                user.getOut().writeUTF(jsonObject.toJSONString()); // Отправляем каждому пользователю список
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}