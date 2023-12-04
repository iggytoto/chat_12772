package server;

import org.json.simple.JSONObject;

import java.io.IOException;

public class Message {
    public static void sendMessage(User user, String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        try {
            user.getOut().writeUTF(jsonObject.toJSONString());
        } catch (IOException e) {
            System.out.println("Ошибка при передаче сообщения пользователю "+user.getName());
        }
    }
}
