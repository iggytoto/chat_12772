package dal;

import java.sql.ResultSet;

/**
 * Общий интерфейс для операций с базой данных
 */
public interface Database {

    /**
     * Чтение данных
     * @param query
     * @param params
     * @return
     */
    ResultSet query (String query, String... params);

    /**
     * Обновление данных
     * @param sql
     * @param params
     */
    void update (String sql,String... params);

    /**
     * типы баз данных
     */
    enum DatabaseType {
        POSTGRES,
        MSSQL
    }

}
