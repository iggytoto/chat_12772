package dal.postgresql;

import dal.Database;

import java.sql.ResultSet;

public class PostgreSqlDatabase implements Database {

    @Override
    public ResultSet query(String query, String... params) {
        return null;
    }

    @Override
    public void update(String sql, String... params) {

    }
}
