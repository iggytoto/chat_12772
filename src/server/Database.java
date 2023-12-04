package server;

import java.sql.*;

public class Database {
    static final String DB_URL = "jdbc:mysql://127.0.0.1/chat12772";
    static final String DB_LOGIN = "root";
    static final String DB_PASS = "";
    private static Connection connection;
    private static Statement statement;
    public static ResultSet query(String sql){
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASS);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    public static void update(String sql){
        try {
            connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASS);
            statement  = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
