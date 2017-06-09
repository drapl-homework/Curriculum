package pku.curriculum.test;

import pku.curriculum.Course;
import pku.curriculum.Storage;
import pku.curriculum.User;
import pku.curriculum.Semester;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by draplater on 17-6-8.
 */
public class DBTest {
    public static void main(String[] args) {
        Storage db = new Storage("jdbc:sqlite:data.sqlite");
        List<User> users = db.getAllUsers();
        // 列出所有用户
        System.out.println("Test list users: " + users);

        // 添加用户
        User aa = db.addNewUser(UUID.randomUUID().toString(), "bbb");
        System.out.println("Test add user: " + aa);
        System.out.println("List users:" + db.getAllUsers());

        // 删除用户
        aa.deleteFromDatabase();
        System.out.println("Test Delete User. List users:" + db.getAllUsers());

        // 获取所有学期
        List<Semester> semesters = db.getAllSementers();
        Semester semester0 = semesters.get(0);
        System.out.println("Test list semesters: " + semesters);

        // 添加学期
        Semester semester = db.addSemester(UUID.randomUUID().toString(), "xxx",
                Date.valueOf("2000-01-01"), Date.valueOf("2000-01-02"));


        System.out.println("Test add semester: " + semester);
        System.out.println("List semensters: " + db.getAllSementers());
        // 删除学期
        semester.deleteFromDatabase();

        // 添加课程
        Course course = semester0.addCourse(UUID.randomUUID().toString(), "测试课程", 2, 100, "教室", "A");

        // 获取所有课程
        System.out.println("List courses: " + semester0.getAllCourses());

        // 删除课程
        course.deleteFromDatabase();
        System.out.println("Test Delete. List courses: " + semester0.getAllCourses());
    }
}
