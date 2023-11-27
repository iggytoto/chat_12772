package server;

import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>(); // Список онлайн пользователей
        try {
            ServerSocket serverSocket = new ServerSocket(9123); // Открываем порт для прослушивания
            System.out.println("Сервер запущен");
            while (true){
                Socket socket = serverSocket.accept(); // Ожидаем подключения клиента
                User user = new User(socket); // Создаём объект клиента
                users.add(user); // Добавляем пользователя список онлайн пользователей
                Thread thread = new Thread(()->{
                    String message = null;
                    String name = null;
                    try {
                        sendMessage(user, "Введите имя: ");
                        name = user.getIs().readUTF();
                        user.setName(name);
                        System.out.println(name+" подключился");
                        sendMessage(user, user.getName() + " добро пожаловать на сервер!");
                        while (true){
                            message = user.getIs().readUTF();
                            System.out.println(user.getName()+": "+message);
                            for (User user1 : users) {
                                if(user == user1) continue;
                                sendMessage(user1, user.getName()+": "+message);
                            }
                        }
                    } catch (IOException e) {
                        System.out.println(user.getName()+" отключился");
                        users.remove(user);
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMessage(User user, String msg) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        user.getOut().writeUTF(jsonObject.toJSONString());
    }
}