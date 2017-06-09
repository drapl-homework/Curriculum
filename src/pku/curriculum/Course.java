package pku.curriculum;

import pku.curriculum.utils.DatabaseObject;
import pku.curriculum.utils.SQLUtils;

import java.sql.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by draplater on 17-6-8.
 */
public class Course extends DatabaseObject {
    public static String tableName = "course";
    public static String columns = "id, semester_id, name, credit, score, location, type";

    private Semester semester;
    private String id;
    private String name;
    private double credit;
    private double score;
    private String location;
    private String type;

    static String getTableNameWithColumnsStatic() { return String.format("%s(%s)", tableName, columns); }
    protected String getTableNameWithColumns() { return getTableNameWithColumnsStatic(); }
    protected String getTableName() { return tableName; }

    Course(Connection connection, Semester semester, String id, String name, double credit, double score, String location, String type) {
        super.connection = connection;
        this.id = id;
        this.semester = semester;
        this.name = name;
        this.credit = credit;
        this.score = score;
        this.location = location;
        this.type = type;
    }

    static Course addCourse(Connection connection, Semester semester, String id, String name, double credit, double score, String location, String type) {
        try {
            //noinspection ConstantConditions
            SQLUtils.insert(connection, getTableNameWithColumnsStatic(), id, semester.getId(), name, credit, score, location, type);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return new Course(connection, semester, id, name, credit, score, location, type);
    }

    static Course fromResultSet(Connection connection, Semester semester, ResultSet rs) {
        try {
            return new Course(connection, semester, rs.getString("id"), rs.getString("name"),
                    rs.getDouble("credit"), rs.getDouble("score"), rs.getString("location"), rs.getString("type"));
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
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

    public Course setName(String name) {
        this.name = name;
        try {
            updateParam("name", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public double getCredit() {
        return credit;
    }

    public Course setCredit(double credit) {
        this.credit = credit;
        try {
            updateParam("credit", credit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public double getScore() {
        return score;
    }

    public Course setScore(double score) {
        this.score = score;
        try {
            updateParam("score", score);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Course setLocation(String location) {
        this.location = location;
        try {
            updateParam("location", location);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getType() {
        return type;
    }

    public Course setType(String type) {
        this.type = type;
        try {
            updateParam("type", type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return id != null ? id.equals(course.id) : course.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Course{" +
                "semester=" + semester +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", credit=" + credit +
                ", score=" + score +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public List<CourseTime> getTimes() {
        String sql = String.format("SELECT * from %s WHERE course_id = \"%s\"", CourseTime.tableName, id);
        return SQLUtils.getAllWithSQL(connection, sql, CourseTime::fromResultSet);
    }

    public Course addTime(CourseTime time) {
        try {
            SQLUtils.insert(connection, CourseTime.getTableNameWithColumnsStatic(), id,
                    time.getDayOfWeek(), time.getStartHour(), time.getStartMinute(),
                    time.getEndHour(), time.getEndMinute());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Course delTime(CourseTime time) {
        try {
            SQLUtils.executeSQLWith(connection,
                    String.format("DELETE FROM %s WHERE course_id = ? AND day_of_week = ? " +
                                    "AND start_hour = ? AND start_minute = ? AND end_hour = ? " +
                            "AND end_minute = ?", time.getTableName()),
                    new Object[]{id, time.getDayOfWeek(), time.getStartHour(), time.getStartMinute(),
                            time.getEndHour(), time.getEndMinute()});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public List<Teacher> getTeachers() {
        String sql = String.format("SELECT teacher_id AS id, name, mail, tel, office from course_teacher " +
                "INNER JOIN %s ON teacher_id = teacher.id WHERE course_id = ?", Teacher.tableName);
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            return SQLUtils.getAllWithStatement(connection, stmt, Teacher::fromResultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Course addTeacher(Teacher teacher) {
        try {
            SQLUtils.insert(connection, "course_teacher", id, teacher.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Course delTeacher(Teacher teacher) {
        try {
            SQLUtils.executeSQLWith(connection,
                    "DELETE FROM course_teacher WHERE course_id = ? AND teacher_id = ?",
                    new Object[]{id, teacher.getId()});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public List<Task> getTasks() {
        String sql = String.format("SELECT * FROM %s WHERE course_id = ?", Task.tableName);
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            return SQLUtils.getAllWithStatement(connection, stmt,
                    (connection, rs) -> Task.fromResultSet(connection, this, rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Task addTask(String id, String name, String content, ZonedDateTime end_time, int status) {
        return Task.addTask(connection, id, this, name, content, end_time, status);
    }
}
