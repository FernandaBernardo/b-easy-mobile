package br.com.b_easy.DataBaseModel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Tiago on 9/24/2015.
 */


@DatabaseTable(tableName = "user")
public class UserBD implements Serializable {

    @DatabaseField(generatedId=true)
    private long id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String email;

    @DatabaseField
    private String password;

    @DatabaseField
    private int age;

    @DatabaseField
    private String college;

    @DatabaseField
    private String course;

    @DatabaseField
    private int semester;

    public UserBD() {
    }

    public UserBD(long id, String name, String email, String password, int age, String college, String course, int semester) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.college = college;
        this.course = course;
        this.semester = semester;
    }

    public UserBD(String name, String email, String password, int age, String college, String course, int semester) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.college = college;
        this.course = course;
        this.semester = semester;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
