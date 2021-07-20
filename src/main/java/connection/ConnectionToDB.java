package connection;

import org.postgresql.ds.PGSimpleDataSource;

import static connection.Constants.*;
import static connection.Constants.PASSWORD;

public class ConnectionToDB {

    public PGSimpleDataSource openConnection(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(URL);
        dataSource.setDatabaseName(DATABASE);
        dataSource.setUser(USERNAME);
        dataSource.setPassword(PASSWORD);
        return dataSource;
    }
}
