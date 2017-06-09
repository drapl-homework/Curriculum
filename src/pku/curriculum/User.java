package pku.curriculum;

import pku.curriculum.utils.SQLUtils;
import pku.curriculum.utils.DatabaseObject;

import java.sql.*;

/**
 * Created by draplater on 17-6-8.
 */
public class User extends DatabaseObject {
    public static String tableName = "user";
    public static String columns = "id, name";
    private String id;
    private String name;

    User(Connection connection, String id, String name) {
        super.connection = connection;
        this.name = name;
        this.id = id;
    }

    static User addNewUser(Connection connection, String id, String name) {
        try {
            //noinspection ConstantConditions
            SQLUtils.insert(connection, getTableNameWithColumnsStatic(), id, name);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return new User(connection, id, name);
    }

    static User fromResultSet(Connection connection, ResultSet rs) {
        try {
            return new User(connection, rs.getString("id"), rs.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static User getCurrentUser() {
        return null;
    }

    static String getTableNameWithColumnsStatic() {
        return String.format("%s(%s)", tableName, columns);
    }

    protected String getTableNameWithColumns() {
        return getTableNameWithColumnsStatic();
    }
    protected String getTableName() { return tableName; }

    protected String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        try {
            updateParam("name", name);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return this;
    }

    public double getCredits() {
        // TODO
        return 0.0;
    }

    public double getGPA() {
        // TODO
        return 0.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
