package curriculum;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by draplater on 17-6-8.
 */
public class CourseTime {
    private static String days = "零一二三四五六日";
    private static String[] alternateWeekString = {"每周", "单周", "双周"};

    public static String tableName = "course_time";
    public static String columns = "course_id, day_of_week, alternate_week, start_hour, start_minute, end_hour, end_minute";
    private int dayOfWeek;
    private int alternateWeek;
    private int startHour;
    private int endHour;
    private int startMinute;
    private int endMinute;

    public CourseTime(int dayOfWeek, int startHour, int startMinute, int endHour, int endMinute) {
        this(dayOfWeek, 0, startHour, startMinute, endHour, endMinute);
    }

    public CourseTime(int dayOfWeek, int alternateWeek, int startHour, int startMinute, int endHour, int endMinute) {
        this.dayOfWeek = dayOfWeek;
        this.alternateWeek = alternateWeek;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public static CourseTime fromResultSet(Connection connection, ResultSet rs) {
        try {
            return new CourseTime(rs.getInt("day_of_week"), rs.getInt("alternate_week"),
                    rs.getInt("start_hour"), rs.getInt("start_minute"),
                    rs.getInt("end_hour"), rs.getInt("end_minute"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTableNameWithColumnsStatic() { return String.format("%s(%s)", tableName, columns); }
    protected String getTableNameWithColumns() { return getTableNameWithColumnsStatic(); }
    protected String getTableName() { return tableName; }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public int getAlternateWeek() {
        return alternateWeek;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public String getDayofWeekString() {
        return alternateWeekString[this.alternateWeek] + "星期" + days.charAt(this.dayOfWeek);
    }

    public String getStartTime() {
        return String.format("%s:%02d", this.startHour, this.startMinute);
    }

    public String getEndTime() {
        return String.format("%s:%02d", this.endHour, this.endMinute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseTime that = (CourseTime) o;

        if (dayOfWeek != that.dayOfWeek) return false;
        if (alternateWeek != that.alternateWeek) return false;
        if (startHour != that.startHour) return false;
        if (endHour != that.endHour) return false;
        if (startMinute != that.startMinute) return false;
        return endMinute == that.endMinute;
    }

    @Override
    public int hashCode() {
        int result = dayOfWeek;
        result = 31 * result + alternateWeek;
        result = 31 * result + startHour;
        result = 31 * result + endHour;
        result = 31 * result + startMinute;
        result = 31 * result + endMinute;
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s %s-%s", getDayofWeekString(), getStartTime(), getEndTime());
    }
}