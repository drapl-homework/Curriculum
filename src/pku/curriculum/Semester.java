package pku.curriculum;

import pku.curriculum.utils.SQLUtils;
import pku.curriculum.utils.DatabaseObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by draplater on 17-6-8.
 */
public class Semester extends DatabaseObject {
    public static String tableName = "semester";
    public static String columns = "id, name, start, end";
    private String id;
    private String name;
    private Date startDate;
    private Date endDate;

    Semester(Connection connection, String id, String name, Date start, Date end) {
        super.connection = connection;
        this.name = name;
        this.id = id;
        this.startDate = start;
        this.endDate = end;
    }

    public static Semester addNewSemester(Connection connection, String id, String name, Date start, Date end) {
        Semester empty = null;
        try {
            //noinspection ConstantConditions
            SQLUtils.insert(connection, getTableNameWithColumnsStatic(),
                    id, name, start.toString(), end.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return new Semester(connection, id, name, start, end);
    }

    public static Semester fromResultSet(Connection connection, ResultSet rs) {
        try {
            return new Semester(connection, rs.getString("id"), rs.getString("name"),
                    Date.valueOf(rs.getString("start")), Date.valueOf(rs.getString("end")));
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public List<Course> getAllCourses() {
        List<Course> ret = new ArrayList<>();
        String sql = String.format("SELECT * from %s WHERE semester_id == ?", Course.tableName);
        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                ret.add(Course.fromResultSet(connection, this, rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return ret;
    }

    static String getTableNameWithColumnsStatic() { return String.format("%s(%s)", tableName, columns); }
    protected String getTableNameWithColumns() { return getTableNameWithColumnsStatic(); }
    protected String getTableName() { return tableName; }

    protected String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Semester setName(String name) {
        this.name = name;
        try {
            updateParam("name", name);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Semester setStartDate(Date startDate) {
        this.startDate = startDate;
        try {
            updateParam("start", startDate);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Semester setEndDate(Date endDate) {
        this.endDate = endDate;
        try {
            updateParam("end", endDate);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return this;
    }

    public Course addCourse(String id, String name, double credit, double score, String location, String type) {
        return Course.addCourse(connection, this, id, name, credit, score, location, type);
    }

    @Override
    public String toString() {
        return "Semester{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
