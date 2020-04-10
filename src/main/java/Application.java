import dao.AlbumController;
import dao.ArtistController;
import dao.ChartController;
import db.ConnectionPool;
import db.Database;
import thread_pool_executor.ThreadPoolExecutorMain;

import java.sql.*;

/**
 * An application that allows to connect
 * to a relational database by using JDBC,
 * submit SQL statements and display the results.
 *
 * @author Ioan Sava
 */
public class Application {
    private static Connection connection;

    static {
        try {
            connection = Database.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final int NUMBER_OF_ROWS = 50;
    private static final int TASKS = 5000;

    public static void main(String[] args) {
        try {
            connection.setAutoCommit(false);
            runApplication();
            connection.commit();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    /**
     * Using a ThreadPoolExecutor, create a large
     * number of concurrent tasks,
     * each requiring a database connection
     * in order to perform various SQL operations on the database.
     */
    public static void runThreadPool() {
        ThreadPoolExecutorMain threadPoolExecutorMain = new ThreadPoolExecutorMain(TASKS);
        threadPoolExecutorMain.start();
    }

    /**
     * Main features
     */
    public static void runApplication() {
        createTables();
        insertMockData();
        insertRandomData(NUMBER_OF_ROWS);
        ChartController chartController = new ChartController();
        chartController.displayRanking();
        chartController.generateHTMLReport();
    }

    public static void createArtistsTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "create table artists(" +
                    "    id integer not null generated always as identity," +
                    "    name varchar(100) not null," +
                    "    country varchar(100)," +
                    "    primary key (id)" +
                    ");";

            statement.execute(query);
        } catch (SQLException exception) {
            System.out.println("create table artists exception");
        }
    }

    public static void createAlbumsTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "create table albums(" +
                    "    id integer not null generated always as identity," +
                    "    name varchar(100) not null," +
                    "    artist_id integer not null references artists on delete restrict," +
                    "    release_year integer," +
                    "    primary key (id)" +
                    ");";

            statement.execute(query);
        } catch (SQLException exception) {
            System.out.println("create table albums exception");
        }
    }

    public static void createChartsTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "create table chart (" +
                    "    id integer not null generated always as identity," +
                    "    album_id integer not null references albums on delete restrict," +
                    "    sales bigint," +
                    "    primary key (id)" +
                    ");";

            statement.execute(query);
        } catch (SQLException exception) {
            System.out.println("create table chart exception");
        }
    }

    /**
     * Create the necessary tables
     */
    public static void createTables() {
        createArtistsTable();
        createAlbumsTable();
        createChartsTable();
    }

    public static void insertMockArtists() {
        ArtistController artistController = new ArtistController();
        artistController.create("Eminem", "USA");
        artistController.create("Stromae", "France");
        artistController.create("Adele", "UK");
    }

    public static void insertMockAlbums() {
        AlbumController albumController = new AlbumController();
        albumController.create("Greatest Hits", 3, 2012);
        albumController.create("Revival", 1, 2017);
        albumController.create("Cheese", 2, 2010);
    }

    public static void insertMockData() {
        insertMockArtists();
        insertMockAlbums();
    }

    /**
     * Insert random data in database using Faker
     *
     * @param numberOfRows number of rows to be inserted
     * @see <a href="https://github.com/DiUS/java-faker">https://github.com/DiUS/java-faker</a>
     */
    public static void insertRandomData(int numberOfRows) {
        ArtistController artistController = new ArtistController();
        artistController.insertRandomArtists(numberOfRows);
        AlbumController albumController = new AlbumController();
        albumController.insertRandomAlbums(numberOfRows);
        ChartController chartController = new ChartController();
        chartController.insertRandomChart(numberOfRows);
    }
}
