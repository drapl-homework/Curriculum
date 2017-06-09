package pku.curriculum;

import pku.curriculum.utils.CourseTime;
import pku.curriculum.utils.DatabaseObject;
import pku.curriculum.utils.SQLUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public Course setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Course setName(String name) {
        this.name = name;
        return this;
    }

    public double getCredit() {
        return credit;
    }

    public Course setCredit(double credit) {
        this.credit = credit;
        return this;
    }

    public double getScore() {
        return score;
    }

    public Course setScore(double score) {
        this.score = score;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Course setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getType() {
        return type;
    }

    public Course setType(String type) {
        this.type = type;
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
        return SQLUtils.getAll(connection, CourseTime.tableName, CourseTime::fromResultSet);
    }

    /*
    public Course addTime(CourseTime time) {
        if(!times.contains(time)) {
            times.add(time);
            // database actions
        }
        return this;
    }
    */
}
