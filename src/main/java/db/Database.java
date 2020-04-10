package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class that manages
 * a connection to the database.
 *
 * @author Ioan Sava
 */
public class Database {
    private static Database instance;
    private Connection connection;
    /**
     * jdbc:sub-protocol:identifier
     */
    private final String URL = "jdbc:postgresql://localhost:5432/musicalbums";
    private final String USERNAME = "dba";
    private final String PASSWORD = "sql";

    private Database() {
        try {
            String postgresqlDriver = "org.postgresql.Driver";
            Class.forName(postgresqlDriver);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException exception) {
            System.out.println("db.Database Connection Creation Failed: " + exception.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Restricts the instantiation of Database class
     * to one "single" instance
     */
    public static Database getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new Database();
        }

        return instance;
    }
}
