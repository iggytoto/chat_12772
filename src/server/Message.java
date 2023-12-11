package server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Message {
    private String msg;
    private int id;
    private int fromId;
    private int toId;

    public Message(String msg, int fromId) {
        this(msg, fromId, 0);
    }
    public Message(String msg, int fromId, int toId) {
        this.msg = msg;
        this.fromId = fromId;
        this.toId = toId;
    }

    public String getMsg() {
        return msg;
    }

    public int getToId() {
        return toId;
    }

    public int getFromId() {
        return fromId;
    }

    public static void sendMessage(User user, String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        try {
            user.getOut().writeUTF(jsonObject.toJSONString());
        } catch (IOException e) {
            System.out.println("Ошибка при передаче сообщения пользователю "+user.getName());
        }
    }
    public void save(){
        String[] params = {msg, String.valueOf(fromId), String.valueOf(toId)};
        Database.update("INSERT INTO messages (msg, from_id, to_id) VALUES (?, ?, ?)", params);
    }
    public static void loadHistoryMessage(User user){
        String[] params = {"0"};
        ResultSet resultSet = Database.query("SELECT * FROM messages WHERE to_id=?", params);
        try {
            while(resultSet.next()){
                sendMessage(user, resultSet.getString("msg"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Message readMessage(User user) throws Exception{
        String msg = user.getIs().readUTF();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(msg);
        Message message = new Message(
                jsonObject.get("msg").toString(),  // Сообщение
                user.getId(), // От кого
                Integer.parseInt(jsonObject.get("to_id").toString()) // Кому
        );
        return message;
    }
}