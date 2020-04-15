package dao;

import freemarker.FreeMarkerConfiguration;
import freemarker.template.*;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * DAO class responsible
 * to get data regarding Chart
 * from database.
 *
 * @author Ioan Sava
 */
public class ChartController extends Controller {
    private String NAME_KEY = "name";
    private String COUNTRY_KEY = "country";
    private String RANK_KEY = "rank";

    public ChartController() {
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

    private int getRandomChartId() {
        int id = -1;
        try {
            Statement statement = connection.createStatement();

            // get random row
            String query = "select id from charts " +
                    "order by random() " +
                    "limit 1;";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return id;
    }

    public boolean chartAlreadyExists(int chartId, int albumId) {
        try {
            String query = "select id from charts_albums where chart_id = ? and album_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, chartId);
            preparedStatement.setInt(2, albumId);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public void createRandom() {
        AlbumController albumController = new AlbumController();
        int randomAlbumId = albumController.randomAlbumId();
        Random random = new Random();
        int randomRank = random.nextInt(100) + 1;
        int randomChartId = getRandomChartId();

        while (chartAlreadyExists(randomChartId, randomAlbumId)) {
            randomAlbumId = albumController.randomAlbumId();
            randomRank = random.nextInt(100) + 1;
            randomChartId = getRandomChartId();
        }

        create(randomChartId, randomAlbumId, randomRank);
    }

    /**
     * Generate the ranking of the artists,
     * considering their positions in the chart.
     */
    private List<Map<String, Object>> generateRanking() {
        List<Map<String, Object>> ranking = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "select ch.rank, ar.name, ar.country " +
                    "from artists ar join albums al on ar.id = al.artist_id join charts_albums ch on al.id = ch.album_id " +
                    "order by ch.rank;";

            ResultSet resultSet = statement.executeQuery(query);
            int i = 0;
            while (resultSet.next()) {
                ranking.add(new HashMap<>());
                int rank = resultSet.getInt(RANK_KEY);
                ranking.get(i).put(RANK_KEY, rank);
                String artistName = resultSet.getString(NAME_KEY);
                ranking.get(i).put(NAME_KEY, artistName);
                String country = resultSet.getString(COUNTRY_KEY);
                ranking.get(i).put(COUNTRY_KEY, country);
                ++i;
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return ranking;
    }

    private void displayRow(int rank, String name, String country) {
        System.out.println(rank + ". " + name + " - " + country);
    }

    public void displayRanking() {
        List<Map<String, Object>> ranking = generateRanking();
        for (Map<String, Object> stringObjectMap : ranking) {
            displayRow((int) stringObjectMap.get(RANK_KEY), (String) stringObjectMap.get(NAME_KEY),
                    (String) stringObjectMap.get(COUNTRY_KEY));
        }
    }

    private String TEMPLATE_FILE = "template.html";
    private String REPORT_FILE = "report.html";

    /**
     * Generate a HTML report using FreeMarker
     */
    public void generateHTMLReport() {
        List<Map<String, Object>> ranking = generateRanking();
        Map<String, List> root = new HashMap<>();
        root.put("ranking", ranking);
        Configuration configuration = FreeMarkerConfiguration.getInstance().getConfiguration();
        try {
            Template template = configuration.getTemplate(TEMPLATE_FILE);
            FileWriter fileWriter = new FileWriter(REPORT_FILE);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            template.process(root, printWriter);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
