package Queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static connection.Constants.*;

public class DropTables {

    public void dropTable(String query) {
        try (Connection connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Table was dropped");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
