package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(9123);
            System.out.println("Сервер запущен");
            while (true){
                Socket socket = serverSocket.accept(); // Ожидаем подключения клиента
                User user = new User(socket);
                users.add(user);
                Thread thread = new Thread(()->{
                    String message = null;
                    String name = null;
                    try {
                        user.getOut().writeUTF("Введите имя: ");
                        name = user.getIs().readUTF();
                        user.setName(name);
                        System.out.println(name+" подключился");
                        user.getOut().writeUTF("Добро пожаловать на сервер!");
                        while (true){
                            message = user.getIs().readUTF();
                            System.out.println(user.getName()+": "+message);
                            for (User user1 : users) {
                                if(user == user1) continue;
                                user1.getOut().writeUTF(user.getName()+": "+message);
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
}