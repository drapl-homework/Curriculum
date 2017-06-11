package curriculum.test;

import curriculum.*;

import java.io.File;
import java.nio.file.Files;
import java.sql.Date;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Created by draplater on 17-6-8.
 */
public class DBTest {
    public static void main(String[] args) {
        String databaseFile = "data.sqlite";
        Storage db = new Storage("jdbc:sqlite:" + databaseFile, !new File(databaseFile).exists());
        List<User> users = db.getAllUsers();
        // 列出所有用户
        System.out.println("Test list users: " + users);

        // 添加用户
        User aa = db.addNewUser(UUID.randomUUID().toString(), "bbb");
        System.out.println("Test add user: " + aa);

        // 修改用户属性
        aa.setName("abcdefg");
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

        //修改学期属性
        semester.setEndDate(Date.valueOf("2010-01-01"));
        System.out.println("List semensters: " + db.getAllSementers());
        // 删除学期
        semester.deleteFromDatabase();

        // 添加课程
        Course course = semester0.addCourse(UUID.randomUUID().toString(), "测试课程", 2, 100, "教室", "A");

        // 获取所有课程
        // 修改课程属性
        course.setCredit(5);
        System.out.println("List courses: " + semester0.getAllCourses());

        // 删除课程
        course.deleteFromDatabase();
        System.out.println("Test Delete. List courses: " + semester0.getAllCourses());

        // 查看上课时间
        System.out.println(course.getTimes());

        // 增加上课时间
        CourseTime newTime = new CourseTime(2, 1, 14, 00, 15, 00);
        course.addTime(newTime);
        System.out.println(course.getTimes());

        // 删除上课时间
        course.delTime(newTime);
        System.out.println(course.getTimes());

        // 列出所有老师
        System.out.println(db.getAllTeachers());

        // 增加老师
        Teacher teacher = db.addNewTeacher(UUID.randomUUID().toString(), "li", "cc@pku.edu.cn",
                "110-120-119", "hhh");
        System.out.println(db.getAllTeachers());


        // 列出某课的老师
        Course course0 = semester0.getAllCourses().get(0);
        System.out.println(course0.getTeachers());

        // 将老师添加到某课
        course0.addTeacher(teacher);
        System.out.println(course0.getTeachers());

        // 从某课中删除老师
        course0.delTeacher(teacher);
        System.out.println(course0.getTeachers());

        // 删除老师
        teacher.deleteFromDatabase();
        System.out.println(db.getAllTeachers());

        // 查看某课程任务
        System.out.println(course0.getTasks());

        // 课程增加任务
        Task task = course0.addTask(UUID.randomUUID().toString(), "task2", "yyyyy",
                "2017-06-11", 0);
        System.out.println(course0.getTasks());

        // 删除任务
        task.deleteFromDatabase();
        System.out.println(course0.getTasks());
    }
}
