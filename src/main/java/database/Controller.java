package database;
import java.sql.Connection;
import java.sql.SQLException;

public class Controller {
    private final Database database;
    public Controller(Database database) {
        this.database = database;
    }

    public Connection getConnection() throws SQLException {
        return this.database.connect();
    }
}
