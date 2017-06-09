package pku.curriculum.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static pku.curriculum.utils.SQLUtils.executeSQLWith;

/**
 * Created by draplater on 17-6-8.
 */
abstract public class DatabaseObject {
    protected Connection connection;

    protected abstract String getTableName();
    protected abstract String getTableNameWithColumns();
    protected abstract String getId();

    protected Connection getConnection() {
        return connection;
    }

    protected void updateParam(String name, Object param) throws SQLException {
        executeSQLWith(getConnection(),
                String.format("UPDATE %s SET %s = ? WHERE id = ?",
                        getTableName(), name),
                new Object[]{param, getId()});
    }

    public void deleteFromDatabase() {
        try {
            executeSQLWith(getConnection(),
                    String.format("DELETE FROM %s WHERE id = ?",
                            getTableName()),
                    new Object[]{getId()});
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
