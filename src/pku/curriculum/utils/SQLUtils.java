package pku.curriculum.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by draplater on 17-6-8.
 */
public class SQLUtils {
     public static void executeSQLWith(Connection connection, String sql, Object[] attrs) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(sql);
        for(int i=0; i<attrs.length; i++) {
            Object obj = attrs[i];
            stmt.setObject(i+1, obj);
        }
        stmt.executeUpdate();
    }

    public static void insert(Connection connection, String tableNameWithColumns, Object... attrs) throws SQLException {
        String placeholder = String.join(",", Collections.nCopies(attrs.length, "?"));
        executeSQLWith(connection,
                String.format("INSERT INTO %s VALUES(%s)", tableNameWithColumns, placeholder), attrs);
    }

    public static void updateParam(Connection connection, String tableNameWithColumns, String name, String id, Object param) throws SQLException {
        executeSQLWith(connection,
                String.format("UPDATE %s SET %s = ? WHERE id = ?", tableNameWithColumns, name),
                new Object[]{param, id});
    }

    public static <T> List<T> getAll(Connection connection, String tableName, BiFunction<Connection, ResultSet, T> factory) {
        List<T> ret = new ArrayList<>();
        String sql = "SELECT * from " + tableName;
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                ret.add(factory.apply(connection, rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return ret;
    }
}
