package db;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Singleton class that manages
 * a connection pool to the database.
 *
 * @author Ioan Sava
 */
public class ConnectionPool {
    private static ConnectionPool instance;
    private static Connection connection;

    private final String URL = "jdbc:postgresql://localhost:5432/musicalbums";
    private final String USERNAME = "dba";
    private final String PASSWORD = "sql";

    private ConnectionPool() throws SQLException {
        // Creates a connection factory object which will be use by
        // the pool to create the connection object. We passes the
        // JDBC url info, username and password.
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
                URL, USERNAME, PASSWORD
        );

        // Creates a PoolableConnectionFactory that will wraps the
        // connection object created by the ConnectionFactory to add
        // object pooling functionality.
        PoolableConnectionFactory poolableConnectionFactory = new
                PoolableConnectionFactory(connectionFactory, null);
        poolableConnectionFactory.setValidationQuery("select 1");

        // Creates an instance of GenericObjectPool that holds our
        // pool of connections object.
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(15);
        GenericObjectPool<org.apache.commons.dbcp2.PoolableConnection> connectionPool = new
                GenericObjectPool<>(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);
        DataSource dataSource = new PoolingDataSource<>(connectionPool);
        connection = dataSource.getConnection();
    }

    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            instance = new ConnectionPool();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
