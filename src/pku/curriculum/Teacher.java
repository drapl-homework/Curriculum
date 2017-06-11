package curriculum;

import javafx.scene.chart.PieChart;
import curriculum.utils.DatabaseObject;
import curriculum.utils.SQLUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by draplater on 17-6-8.
 */
public class Teacher extends DatabaseObject {
    public static String tableName = "teacher";
    public static String columns = "id, name, mail, tel, office";

    private String id;
    private String name;
    private String mail;
    private String tel;
    private String office;

    Teacher(Connection connection, String id, String name, String mail, String tel, String office) {
        super.connection = connection;
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.tel = tel;
        this.office = office;
    }
    
    

    static Teacher addTeacher(Connection connection, String id, String name, String mail, String tel, String office) {
        try {
            //noinspection ConstantConditions
            SQLUtils.insert(connection, getTableNameWithColumnsStatic(),
                    id, name, mail, tel, office);
            return new Teacher(connection, id, name, mail, tel, office);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Teacher fromResultSet(Connection connection, ResultSet rs) {
        try {
            return new Teacher(connection, rs.getString("id"), rs.getString("name"),
                    rs.getString("mail"), rs.getString("tel"), rs.getString("office"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static String getTableNameWithColumnsStatic() { return String.format("%s(%s)", tableName, columns); }
    protected String getTableNameWithColumns() { return getTableNameWithColumnsStatic(); }
    protected String getTableName() { return tableName; }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Teacher setName(String name) {
        this.name = name;
        try {
            updateParam("name", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getMail() {
        return mail;
    }

    public Teacher setMail(String mail) {
        this.mail = mail;
        try {
            updateParam("mail", mail);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getTel() {
        return tel;
    }

    public Teacher setTel(String tel) {
        this.tel = tel;
        try {
            updateParam("tel", tel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getOffice() {
        return office;
    }

    public Teacher setOffice(String office) {
        this.office = office;
        try {
            updateParam("office", office);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public void deleteFromDatabase() {
        super.deleteFromDatabase();
        try {
            SQLUtils.executeSQLWith(getConnection(),
                    "DELETE FROM course_teacher WHERE teacher_id = ?",
                    new Object[]{getId()});
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        return id != null ? id.equals(teacher.id) : teacher.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", tel='" + tel + '\'' +
                ", office='" + office + '\'' +
                '}';
    }
}
