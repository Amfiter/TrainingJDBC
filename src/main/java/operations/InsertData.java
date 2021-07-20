package operations;

import models.Department;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;

import static connection.Constants.*;
import static connection.Constants.PASSWORD;
import static operations.SQL.*;

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
