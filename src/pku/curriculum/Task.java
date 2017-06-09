package pku.curriculum;

import pku.curriculum.utils.DatabaseObject;
import pku.curriculum.utils.SQLUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by draplater on 17-6-9.
 */
public class Task extends DatabaseObject {
    public static String tableName = "task";
    public static String columns = "id, course_id, name, content, end_time, status";

    private String id;
    private Course course;
    private String name;private String content;
    private ZonedDateTime endTime;
    private int status;

    static String getTableNameWithColumnsStatic() { return String.format("%s(%s)", tableName, columns); }
    protected String getTableNameWithColumns() { return getTableNameWithColumnsStatic(); }
    protected String getTableName() { return tableName; }

    Task(Connection connection, String id, Course course, String name, String content, ZonedDateTime endTime, int status) {
        this.connection = connection;
        this.id = id;
        this.course = course;
        this.name = name;
        this.content = content;
        this.endTime = endTime;
        this.status = status;
    }

    static Task addTask(Connection connection, String id, Course course, String name, String content, ZonedDateTime end_time, int status) {
        try {
            //noinspection ConstantConditions
            SQLUtils.insert(connection, getTableNameWithColumnsStatic(), id, course.getId(), name, content,
                    end_time.toEpochSecond(), status);
            return new Task(connection, id, course, name, content, end_time, status);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static Task fromResultSet(Connection connection, Course course, ResultSet rs) {
        try {
            return new Task(connection, rs.getString("id"), course,
                    rs.getString("name"), rs.getString("content"),
                    ZonedDateTime.ofInstant(Instant.ofEpochSecond(rs.getInt("end_time")), ZoneId.systemDefault()),
                            rs.getInt("status"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Task setName(String name) {
        this.name = name;
        try {
            updateParam("name", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getContent() {
        return content;
    }

    public Task setContent(String content) {
        this.content = content;
        try {
            updateParam("content", content);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public Task setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        try {
            updateParam("end_time", endTime.toEpochSecond());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Task setStatus(int status) {
        this.status = status;
        try {
            updateParam("status", status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return id != null ? id.equals(task.id) : task.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", course=" + course +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}
