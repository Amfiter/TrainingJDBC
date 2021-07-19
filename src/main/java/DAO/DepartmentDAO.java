package DAO;

import models.Department;

import java.sql.*;
import java.util.List;

import static connection.Constants.*;
import static connection.Constants.PASSWORD;

public class DepartmentDAO implements DAO {

    @Override
    public Object getById(Object id) {
        try (
                Connection connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {
            Department department = new Department();
            ResultSet resultSet = statement.executeQuery("select * from department WHERE id =" + id);
            while (resultSet.next()) {
                department.id = resultSet.getInt(1);
                department.name = resultSet.getString("name");
                System.out.println(
                        "Department id: '" + department.id
                                + "' Department Name: '" + department.name + "'"
                );
                return new Department(department.id,department.name);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object create(Object entity) {
        return null;
    }

    @Override
    public Object update(Object entity) {
        return null;
    }

    @Override
    public boolean delete(Object id) {
        return false;
    }

}
