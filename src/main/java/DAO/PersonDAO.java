package DAO;

import connection.ConnectionToDB;
import connection.Constants;
import models.Department;
import models.Person;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static connection.Constants.*;
import static connection.Constants.PASSWORD;

public class PersonDAO implements DAO<Person> {
    private ConnectionToDB connectionToDB = new ConnectionToDB();
    private DepartmentDAO departmentDAO = new DepartmentDAO();

    @Override
    public Person getById(int id) {

        try (
                Connection connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {
            Person person = new Person();
            ResultSet resultSet = statement.executeQuery("select * from person WHERE id =" + id);
            while (resultSet.next()) {
                person.id = resultSet.getInt(1);
                person.firstName = resultSet.getString(2);
                person.secondName = resultSet.getString(3);
                person.birthday = resultSet.getDate(4);
                int departmentId = resultSet.getInt("department_id");
                person.department = departmentDAO.getById(departmentId);
                System.out.println(
                        "person id: '" + person.id + "'" +
                                "person firstName: '" + person.firstName + "'" +
                                "person secondName: '" + person.secondName + "'" +
                                "person birthday: '" + person.birthday + "'" +
                                "person department: '" + person.department.id + "'"
                );
                return new Person(person.id, person.firstName, person.secondName, person.birthday, person.department);
            }

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Person create(Person person) {
        connectionToDB.openConnection();
        try (Connection connection = connectionToDB.openConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into person(first_name,second_name,birthday,department_id) values (?,?,?,?)"
             )
        ) {
            preparedStatement.setString(1, person.firstName);
            preparedStatement.setString(2, person.secondName);
            preparedStatement.setDate(3, (Date) person.birthday);
            preparedStatement.setInt(4, person.department.id);
            preparedStatement.executeUpdate();
            System.out.println("Entity was created");
            return person;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Person update(Person person) {
        connectionToDB.openConnection();
        try (Connection connection = connectionToDB.openConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE person SET first_name = ? , second_name = ? , birthday = ? , department_id = ? where id = ?")
        ) {
            preparedStatement.setString(1, person.firstName);
            preparedStatement.setString(2, person.secondName);
            preparedStatement.setDate(3, (Date) person.birthday);
            preparedStatement.setInt(4, person.department.id);
            preparedStatement.setInt(5, person.id);
            preparedStatement.executeUpdate();
            System.out.println("Entity was updated");
            return getById(person.id);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        connectionToDB.openConnection();
        try (Connection connection = connectionToDB.openConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from person where id=?")
        ) {
            preparedStatement.setInt(1, id);
            System.out.println("Entity was deleted");
            preparedStatement.executeUpdate();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public Person batch(int id) {

        try (
                Connection connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {
            Person person = new Person();
            ResultSet resultSet = statement.executeQuery("select * from person WHERE id =" + id);
            while (resultSet.next()) {
                person.id = resultSet.getInt(1);
                person.firstName = resultSet.getString(2);
                person.secondName = resultSet.getString(3);
                person.birthday = resultSet.getDate(4);
                int departmentId = resultSet.getInt("department_id");
                person.department = departmentDAO.getById(departmentId);
                System.out.println(
                        "person id: '" + person.id + "'" +
                                "person firstName: '" + person.firstName + "'" +
                                "person secondName: '" + person.secondName + "'" +
                                "person birthday: '" + person.birthday + "'" +
                                "person department: '" + person.department.id + "'"
                );
                return new Person(person.id, person.firstName, person.secondName, person.birthday, person.department);
            }

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    //without fetchSize
    @Override
    public void getAll() {
        connectionToDB.openConnection();
        try (
                Connection connection = connectionToDB.openConnection().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select * from person  INNER JOIN department ON person.department_id = department.id")) {
            Person person = new Person();
            System.out.println("before select all");
            LocalDateTime beforeSimpleSimpleSelect = LocalDateTime.now();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                person.id = resultSet.getInt(1);
                person.firstName = resultSet.getString(2);
                person.secondName = resultSet.getString(3);
                person.birthday = resultSet.getDate(4);
                int departmentId = resultSet.getInt(5);
                person.department = departmentDAO.getById(departmentId);
                System.out.println(
                        "person id: '" + person.id + "'" +
                                "person firstName: '" + person.firstName + "'" +
                                "person secondName: '" + person.secondName + "'" +
                                "person birthday: '" + person.birthday + "'" +
                                "person department: '" + person.department.id + "'"
                );
            }
            System.out.println("after select all");
            LocalDateTime afterSimpleSimpleSelect = LocalDateTime.now();
            long simpleSelectAllTime = ChronoUnit.MILLIS.between(beforeSimpleSimpleSelect, afterSimpleSimpleSelect);
            System.out.println("Select all time: " + simpleSelectAllTime);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
    //FetchSize
    public void fetchSize(){
            connectionToDB.openConnection();
            try (
                    Connection connection = connectionToDB.openConnection().getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("select * from person  INNER JOIN department ON person.department_id = department.id")) {
                connection.setAutoCommit(false);
                preparedStatement.setFetchSize(1);
                Person person = new Person();
                System.out.println("before select all");
                LocalDateTime beforeSimpleSimpleSelect = LocalDateTime.now();
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    person.id = resultSet.getInt(1);
                    person.firstName = resultSet.getString(2);
                    person.secondName = resultSet.getString(3);
                    person.birthday = resultSet.getDate(4);
                    int departmentId = resultSet.getInt(5);
                    person.department = departmentDAO.getById(departmentId);
                    System.out.println(
                            "person id: '" + person.id + "'" +
                                    "person firstName: '" + person.firstName + "'" +
                                    "person secondName: '" + person.secondName + "'" +
                                    "person birthday: '" + person.birthday + "'" +
                                    "person department: '" + person.department.id + "'"
                    );
                }
                System.out.println("after select all");
                LocalDateTime afterSimpleSimpleSelect = LocalDateTime.now();
                long simpleSelectAllTime = ChronoUnit.MILLIS.between(beforeSimpleSimpleSelect, afterSimpleSimpleSelect);
                System.out.println("Select all time: " + simpleSelectAllTime);
            } catch (
                    SQLException e) {
                e.printStackTrace();
            }
    }
}
