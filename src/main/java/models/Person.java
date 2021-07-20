package models;

import java.util.Date;
import java.util.StringJoiner;

public class Person {
    public int id;
    public String firstName;
    public String secondName;
    public Date birthday;
    public Department department;

    public Person() {
    }

    public Person(String firstName) {
        this.firstName = firstName;
    }

    public Person(int id, String firstName, String secondName, Date birthday, Department department) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthday = birthday;
        this.department = department;
    }
    public Person( String firstName, String secondName, Date birthday, Department department) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.birthday = birthday;
        this.department = department;
    }


    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Person.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("secondName='" + secondName + "'")
                .add("birthday=" + birthday)
                .add("department=" + department)
                .toString();
    }
}
