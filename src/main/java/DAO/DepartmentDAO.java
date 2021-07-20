package DAO;

import connection.ConnectionToDB;
import models.Department;
import org.postgresql.ds.PGSimpleDataSource;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static connection.Constants.*;
import static connection.Constants.PASSWORD;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class DepartmentDAO implements DAO<Department> {

    private ConnectionToDB connectionToDB = new ConnectionToDB();

    @Override
    public void getAll() {
        try (
                Connection connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {
            Department department = new Department();
            ResultSet resultSet = statement.executeQuery("select * from department");
            while (resultSet.next()) {
                department.id = resultSet.getInt(1);
                department.name = resultSet.getString("name");
                System.out.println(
                        "Department id: '" + department.id
                                + "' Name: '" + department.name + "'"
                );
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLastId() {
        connectionToDB.openConnection();
        try (
                Connection connection = connectionToDB.openConnection().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "select id from department", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE
                )) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            int id = resultSet.getInt(1);
            System.out.println(id);
            return id + 1;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getMaxId() {
        connectionToDB.openConnection();
        try (
                Connection connection = connectionToDB.openConnection().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select max(id) from department")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int id = resultSet.getInt(1);
            System.out.println(id);
            return id + 1;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Department getById(int id) {
        try (
                Connection connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {
            Department department = new Department();
            ResultSet resultSet = statement.executeQuery("select * from department WHERE id =" + id);
            while (resultSet.next()) {
                department.id = resultSet.getInt(1);
                department.name = resultSet.getString("name");
                /*System.out.println(
                        "Department id: '" + department.id
                                + "' Department Name: '" + department.name + "'"
                );*/
                return new Department(department.id, department.name);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Department create(Department department) {
        connectionToDB.openConnection();
        try (Connection connection = connectionToDB.openConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into department(name) values (?)"
             )
        ) {
            preparedStatement.setString(1, department.name);
            preparedStatement.executeUpdate();
            System.out.println("Entity was created");
            return department;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Department update(Department department) {
        connectionToDB.openConnection();
        try (Connection connection = connectionToDB.openConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE department SET name = ? where id = ?")
        ) {
            preparedStatement.setString(1, department.name);
            preparedStatement.setLong(2, department.id);
            System.out.println("Entity was updated");
            preparedStatement.executeUpdate();
            return getById(department.id);
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
                     "delete from department where id=?")
        ) {
            preparedStatement.setInt(1, id);
            System.out.println("Entity was deleted");
            preparedStatement.executeUpdate();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

}
