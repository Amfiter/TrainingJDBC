package Queries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static connection.Constants.*;

public class InsertData {

    public void insertData(String query) {
        try (Connection connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Data was inserted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
