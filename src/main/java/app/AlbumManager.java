package app;

import dao.AlbumController;
import dao.ArtistController;
import dao.ChartAlbumController;
import dao.ChartController;
import db.Database;
import thread_pool_executor.ThreadPoolExecutorMain;

import java.sql.*;
import java.util.Scanner;

/**
 * An application that allows to connect
 * to a relational database by using JDBC,
 * submit SQL statements and display the results.
 *
 * @author Ioan Sava
 */
public class AlbumManager {
    private static Connection connection;
    private ArtistController artistController = new ArtistController();
    private AlbumController albumController = new AlbumController();
    private ChartController chartController = new ChartController();
    private ChartAlbumController chartAlbumController = new ChartAlbumController();

    private final int NUMBER_OF_ROWS = 50;
    private final int TASKS = 5000;

    public static void main(String[] args) {
        try {
            AlbumManager albumManager = new AlbumManager();
            connection = Database.getInstance().getConnection();
            connection.setAutoCommit(false);
            albumManager.runApplication();
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
    public void runThreadPool() {
        ThreadPoolExecutorMain threadPoolExecutorMain = new ThreadPoolExecutorMain(TASKS);
        threadPoolExecutorMain.start();
    }

    /**
     * Main features
     */
    public void runApplication() {
        insertMockData();
        insertRandomData(NUMBER_OF_ROWS);
        displayRanking();
        generateHTMLReport();
    }

    public void insertMockArtists() {
        artistController.create("Eminem", "USA");
        artistController.create("Stromae", "France");
        artistController.create("Adele", "UK");
    }

    public void insertMockAlbums() {
        albumController.create("Greatest Hits", 3, 2012);
        albumController.create("Revival", 1, 2017);
        albumController.create("Cheese", 2, 2010);
    }

    public void insertMockData() {
        insertMockArtists();
        insertMockAlbums();
    }

    /**
     * Insert random data in database using Faker
     *
     * @param numberOfRows number of rows to be inserted
     * @see <a href="https://github.com/DiUS/java-faker">https://github.com/DiUS/java-faker</a>
     */
    public void insertRandomData(int numberOfRows) {
        for (int i = 0; i < numberOfRows; ++i) {
            artistController.createRandom();
            albumController.createRandom();
            chartAlbumController.createRandom();
        }
    }

    /**
     * Display ranking of a given chart.
     */
    private void displayRanking() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose chart id: ");
        int id = scanner.nextInt();
        chartController.displayRanking(id);
    }

    /**
     * Generate HTML report using FreeMarker
     * for a given chart.
     */
    private void generateHTMLReport() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose chart id: ");
        int id = scanner.nextInt();
        chartController.generateHTMLReport(id);
    }
}
