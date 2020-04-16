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
 * to get data regarding Charts
 * from database.
 *
 * @author Ioan Sava
 */
public class ChartController extends Controller {
    private String RANK_KEY = "rank";
    private String ALBUM_NAME_KEY = "albumName";
    private String ARTIST_NAME_KEY = "artistName";

    public ChartController() {
        try {
            connection = getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void create(String name) {
        try {
            String query = "insert into charts (name) values(?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getRandomChartId() {
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

    /**
     * Generate the ranking of the artists,
     * considering their positions in the specified chart.
     */
    private List<Map<String, Object>> generateRanking(int id) {
        List<Map<String, Object>> ranking = new ArrayList<>();
        try {
            String query = "select ch.rank, al.name as albumName, ar.name as artistName " +
                    "from artists ar join albums al on ar.id = al.artist_id join charts_albums ch on al.id = ch.album_id " +
                    "where chart_id = ? " +
                    "order by ch.rank;";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            int i = 0;
            while (resultSet.next()) {
                ranking.add(new HashMap<>());
                int rank = resultSet.getInt(RANK_KEY);
                ranking.get(i).put(RANK_KEY, rank);
                String albumName = resultSet.getString(ALBUM_NAME_KEY);
                ranking.get(i).put(ALBUM_NAME_KEY, albumName);
                String artistName = resultSet.getString(ARTIST_NAME_KEY);
                ranking.get(i).put(ARTIST_NAME_KEY, artistName);
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

    public void displayRanking(int id) {
        List<Map<String, Object>> ranking = generateRanking(id);
        for (Map<String, Object> stringObjectMap : ranking) {
            displayRow((int) stringObjectMap.get(RANK_KEY), (String) stringObjectMap.get(ALBUM_NAME_KEY),
                    (String) stringObjectMap.get(ARTIST_NAME_KEY));
        }
    }

    private String TEMPLATE_FILE = "template.html";
    private String REPORT_FILE = "report.html";

    /**
     * Generate a HTML report using FreeMarker
     */
    public void generateHTMLReport(int id) {
        List<Map<String, Object>> ranking = generateRanking(id);
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
