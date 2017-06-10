package curriculum;

import curriculum.utils.SQLUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by draplater on 17-6-8.
 */
public class Storage {
    private Connection connection;

    private static Connection connect(String url, boolean isNew) {
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

        if(isNew) {
            try {
                System.err.println("Database not exist. Create a default one.");
                Path path = Paths.get(ClassLoader.getSystemResource("default.sql").toURI());
                String sql = Files.lines(path).collect(Collectors.joining("\n"));
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            } catch (IOException | URISyntaxException | SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        return conn;
    }

    public Storage(String url, boolean isNew) {
        connection = connect(url, isNew);
    }

    public Storage(String url) {
        connection = connect(url, false);
    }

    public List<User> getAllUsers() {
        return SQLUtils.getAll(connection, User.tableName, User::fromResultSet);
    }

    public List<Semester> getAllSementers() {
        return SQLUtils.getAll(connection, Semester.tableName, Semester::fromResultSet);
    }

    public List<Teacher> getAllTeachers() {
        return SQLUtils.getAll(connection, Teacher.tableName, Teacher::fromResultSet);
    }

    public User addNewUser(String id, String name) {
        return User.addNewUser(connection, id, name);
    }

    public Semester addSemester(String id, String name, Date start, Date end) {
        return Semester.addNewSemester(connection, id, name, start, end);
    }

    public Teacher addNewTeacher(String id, String name, String mail, String tel, String office) {
        return Teacher.addTeacher(connection, id, name, mail, tel, office);
    }
}
