package pku.curriculum.utils;

import pku.curriculum.Course;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by draplater on 17-6-8.
 */
public class CourseTime {
    private static String days = "零一二三四五六日";

    public static String tableName = "course_time";
    public static String columns = "course_id, day_of_week, start_hour, end_hour, start_minute, end_minute";
    private int dayOfWeek;
    private int startHour;
    private int endHour;
    private int startMinute;
    private int endMinute;

    public CourseTime(int dayOfWeek, int startHour, int endHour, int startMinute, int endMinute) {
        this.dayOfWeek = dayOfWeek;
        this.startHour = startHour;
        this.endHour = endHour;
        this.startMinute = startMinute;
        this.endMinute = endMinute;
    }

    public static CourseTime fromResultSet(Connection connection, ResultSet rs) {
        try {
            return new CourseTime(rs.getInt("day_of_week"), rs.getInt("start_hour"),
                    rs.getInt("end_hour"), rs.getInt("start_minute"), rs.getInt("end_minute"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
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
        return "星期" + days.charAt(this.dayOfWeek);
    }

    public String getStartTime() {
        return String.format("%s:%s", this.startHour, this.startMinute);
    }

    public String getEndTime() {
        return String.format("%s:%s", this.endHour, this.endMinute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseTime that = (CourseTime) o;

        if (dayOfWeek != that.dayOfWeek) return false;
        if (startHour != that.startHour) return false;
        if (endHour != that.endHour) return false;
        if (startMinute != that.startMinute) return false;
        return endMinute == that.endMinute;
    }

    @Override
    public int hashCode() {
        int result = dayOfWeek;
        result = 31 * result + startHour;
        result = 31 * result + endHour;
        result = 31 * result + startMinute;
        result = 31 * result + endMinute;
        return result;
    }
}