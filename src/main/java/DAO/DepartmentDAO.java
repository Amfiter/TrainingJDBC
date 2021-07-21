package DAO;

import connection.ConnectionToDB;
import models.Department;

import java.sql.*;

import static connection.Constants.*;

public class DepartmentDAO implements DAO<Department> {

    private ConnectionToDB connectionToDB = new ConnectionToDB();

    @Override
    public void getAll() {
        try (Connection connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {
            Department department = new Department();
            ResultSet resultSet = statement.executeQuery("select * from department");
            while (resultSet.next()) {
                department.setId(resultSet.getInt(1));
                department.setName(resultSet.getString("name"));
                System.out.println(
                        "Department id: '" + department.getId()
                                + "' Name: '" + department.getName() + "'"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLastId() {
        try (Connection connection = connectionToDB.openConnection().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "select id from department", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE
                )) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            int id = resultSet.getInt(1);
            System.out.println(id);
            return id + 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getMaxId() {
        try ( Connection connection = connectionToDB.openConnection().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select max(id) from department")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            int id = resultSet.getInt(1);
            System.out.println(id);
            return id + 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Department getById(int id) {
        try (Connection connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {
            Department department = new Department();
            ResultSet resultSet = statement.executeQuery("select * from department WHERE id =" + id);
            while (resultSet.next()) {
                department.setId(resultSet.getInt(1));
                department.setName(resultSet.getString("name"));
               /* System.out.println(
                        "Department id: '" + department.getId()
                                + "' Department Name: '" + department.getName() + "'"
                );*/
                return new Department(department.getId(), department.getName());
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Department create(Department department) {
        try (Connection connection = connectionToDB.openConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into department(name) values (?)")
        ) {
            preparedStatement.setString(1, department.getName());
            preparedStatement.executeUpdate();
            System.out.println("Entity was created");
            return department;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Department update(Department department) {
        try (Connection connection = connectionToDB.openConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE department SET name = ? where id = ?")
        ) {
            preparedStatement.setString(1, department.getName());
            preparedStatement.setLong(2, department.getId());
            System.out.println("Entity was updated");
            preparedStatement.executeUpdate();
            return getById(department.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        try (Connection connection = connectionToDB.openConnection().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from department where id=?")
        ) {
            preparedStatement.setInt(1, id);
            System.out.println("Entity was deleted");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
