package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * DAO class responsible
 * to get data regarding Charts
 * records from database.
 *
 * @author Ioan Sava
 */
public class ChartAlbumController extends Controller {

    public ChartAlbumController() {
        try {
            connection = getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Insert a new chart record in database.
     */
    public void create(int chartId, int albumId, int rank) {
        try {
            String query = "insert into charts_albums(chart_id, album_id, rank)" +
                    " values(?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, chartId);
            preparedStatement.setInt(2, albumId);
            preparedStatement.setInt(3, rank);

            preparedStatement.execute();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private boolean chartRecordAlreadyExists(int chartId, int albumId, int rank) {
        try {
            String query = "select id from charts_albums where chart_id = ? and (album_id = ? or rank = ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, chartId);
            preparedStatement.setInt(2, albumId);
            preparedStatement.setInt(3, rank);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public void createRandom() {
        ChartController chartController = new ChartController();
        int randomChartId = chartController.getRandomChartId();
        AlbumController albumController = new AlbumController();
        int randomAlbumId = albumController.randomAlbumId();
        Random random = new Random();
        int randomRank = random.nextInt(100) + 1;

        while (chartRecordAlreadyExists(randomChartId, randomAlbumId, randomRank)) {
            randomAlbumId = albumController.randomAlbumId();
            randomRank = random.nextInt(100) + 1;
            randomChartId = chartController.getRandomChartId();
        }

        create(randomChartId, randomAlbumId, randomRank);
    }
}
