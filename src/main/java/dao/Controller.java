package dao;

import db.ConnectionPool;
import db.Database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Generic type of controller (DAO)
 * that holds the connection
 * to database.
 *
 * @author Ioan Sava
 * @see <a href="https://en.wikipedia.org/wiki/Data_access_object">Data access object</a>
 */
public abstract class Controller {
    protected Connection connection = null;

    protected Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = Database.getInstance().getConnection();
        }
        return connection;
    }
}
