package Queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static connection.Constants.*;

public class CreateTables {

    public void createTable(String query) {
        try (Connection connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Table was created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
