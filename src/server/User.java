package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;

public class User {

    private String name;
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream out;
    private int id;

    public User(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new DataOutputStream(this.socket.getOutputStream());
        this.is = new DataInputStream(this.socket.getInputStream());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataInputStream getIs() {
        return is;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public boolean reg() throws Exception {
        Message.sendMessage(this, "Введите имя: ");
        name = (Message.readMessage(this)).getMsg();
        Message.sendMessage(this, "Введите Email: ");
        String email = (Message.readMessage(this)).getMsg().toLowerCase();
        Message.sendMessage(this, "Введите пароль: ");
        String pass = (Message.readMessage(this)).getMsg();
        String[] params = {name, email, pass};
        Database.update("INSERT INTO users (name, email, pass) VALUES (?,?,?)", params);
        this.setName(name);
        return login(email, pass);
    }
    public boolean login(String email, String pass){
        String[] params = {email, pass};
        ResultSet resultSet = Database.query("SELECT * FROM users WHERE email= ? AND pass= ?", params);
        try {
            if(resultSet.next()){
                name = resultSet.getString("name");
                int id = resultSet.getInt("id");
                this.setName(name);
                this.setId(id);
                return true;
            }else{
                Message.sendMessage(this, "Неправильный логин или пароль");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean login() throws Exception {
        Message.sendMessage(this, "Введите Email: ");
        String email = (Message.readMessage(this)).getMsg().toLowerCase();
        Message.sendMessage(this, "Введите пароль: ");
        String pass = (Message.readMessage(this)).getMsg();
        return login(email, pass);
    }
}
