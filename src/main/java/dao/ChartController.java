package dao;

import freemarker.FreeMarkerConfiguration;
import freemarker.template.*;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class ChartController extends Controller {
    private String NAME_KEY = "name";
    private String COUNTRY_KEY = "country";
    private String SALES_KEY = "total_sales";
    private String POSITION_KEY = "position";

    /**
     * Insert a new chart record in database.
     */
    public void create(int albumId, long sales) {
        try {
            connection = getConnection();
            String query = "insert into chart(album_id, sales)" +
                    " values(?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, albumId);
            preparedStatement.setLong(2, sales);

            preparedStatement.execute();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void insertRandomChart(int numberOfRows) {
        AlbumController albumController = new AlbumController();
        Random random = new Random();

        for (int i = 0; i < numberOfRows; ++i) {
            int albumId = albumController.randomAlbumId();
            int randomSales = random.nextInt(100) + 1;
            create(albumId, randomSales);
        }
    }

    /**
     * Generate the ranking of the artists,
     * considering their positions in the chart.
     */
    private List<Map<String, Object>> generateRanking() {
        List<Map<String, Object>> ranking = new ArrayList<>();
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();
            String query = "select ar.name, ar.country, SUM(sales) AS total_sales " +
                    "from artists ar join albums al on ar.id = al.artist_id join chart ch on al.id = ch.album_id " +
                    "group by ar.name, ar.country " +
                    "order by SUM(sales) desc;";

            ResultSet resultSet = statement.executeQuery(query);
            int i = 0;
            while (resultSet.next()) {
                ranking.add(new HashMap<>());
                ranking.get(i).put(POSITION_KEY, i + 1);
                String artistName = resultSet.getString(NAME_KEY);
                ranking.get(i).put(NAME_KEY, artistName);
                String country = resultSet.getString(COUNTRY_KEY);
                ranking.get(i).put(COUNTRY_KEY, country);
                long totalSales = resultSet.getLong(SALES_KEY);
                ranking.get(i).put(SALES_KEY, totalSales);
                ++i;
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return ranking;
    }

    private void displayRow(int position, String name, String country, long sales) {
        System.out.println(position + ". " + name + " - " + country + " - " + sales);
    }

    public void displayRanking() {
        List<Map<String, Object>> ranking = generateRanking();
        for (Map<String, Object> stringObjectMap : ranking) {
            displayRow((int) stringObjectMap.get(POSITION_KEY), (String) stringObjectMap.get(NAME_KEY),
                    (String) stringObjectMap.get(COUNTRY_KEY), (long) stringObjectMap.get(SALES_KEY));
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
