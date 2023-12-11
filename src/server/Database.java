package server;

import java.sql.*;

public class Database {
    static final String DB_URL = "jdbc:mysql://127.0.0.1/chat12772";
    static final String DB_LOGIN = "root";
    static final String DB_PASS = "";
    private static Connection connection;
    private static Statement statement;
    public static ResultSet query(String sql, String[] params){
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setString(i+1, params[i]);
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;
    }
    public static void update(String sql, String[] params){
        try {
            connection = DriverManager.getConnection(DB_URL, DB_LOGIN, DB_PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setString(i+1, params[i]);
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
