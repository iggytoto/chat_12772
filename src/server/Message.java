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
        this.msg = msg;
        this.fromId = fromId;
    }

    public String getMsg() {
        return msg;
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
        Database.update("INSERT INTO messages (msg, from_id) VALUES ('"+msg+"', '"+fromId+"')");
    }
    public static void loadHistoryMessage(User user){
        ResultSet resultSet = Database.query("SELECT * FROM messages WHERE to_id=0");
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
        Message message = new Message(jsonObject.get("msg").toString(), user.getId());
        return message;
    }
}