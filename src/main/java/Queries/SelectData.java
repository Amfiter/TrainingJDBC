package Queries;

import models.Department;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;

import static connection.Constants.*;
import static connection.Constants.PASSWORD;

public class SelectData {

    public void getDepartment(){
        try (
                Connection connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from department");
            while (resultSet.next()) {
                int departmentId = resultSet.getInt(1);
                String departmentName = resultSet.getString("name");
                System.out.println(
                        "Department id: '" + departmentId
                                + "' Department Name: '" + departmentName + "'"
                );
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public void getPerson() {
        try (
                Connection connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from person");
            while (resultSet.next()) {
                int personId = resultSet.getInt(1);
                String firstName = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                System.out.println(
                        "Person id: '" + personId
                                + "' First_name: '" + firstName
                                + "' Second_name: '" + secondName + "'"
                );

            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
