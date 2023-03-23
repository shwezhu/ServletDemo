package database;
import java.sql.Connection;
import java.sql.SQLException;

public interface Database {
    public Connection connect() throws SQLException;
}
