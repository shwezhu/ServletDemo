package database;
// java.sql.*属于Java内置的JDBC API的一部分
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDatabase implements Database{
    private final String url, user, password;

    public MysqlDatabase(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection connect() throws SQLException {
        try {
            // You must load jdbc driver, otherwise you will get null for connection.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Instead of printing error simply, you should do some logging here
            System.err.println("Cannot find sql drive: com.mysql.jdbc.Driver");
            return null;
        }
        return DriverManager.getConnection(this.url, this.user, this.password);
    }
}
