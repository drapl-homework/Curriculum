package pku.curriculum;

import pku.curriculum.utils.SQLUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by draplater on 17-6-8.
 */
public class Storage {
    private Connection connection;

    private static Connection connect(String url) {
        // SQLite connection string
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return conn;
    }

    public Storage(String url) {
        connection = connect(url);
    }

    public List<User> getAllUsers() {
        return SQLUtils.getAll(connection, User.tableName, User::fromResultSet);
    }

    public List<Semester> getAllSementers() {
        return SQLUtils.getAll(connection, Semester.tableName, Semester::fromResultSet);
    }

    public User addNewUser(String id, String name) {
        return User.addNewUser(connection, id, name);
    }

    public Semester addSemester(String id, String name, Date start, Date end) {
        return Semester.addNewSemester(connection, id, name, start, end);
    }
}
