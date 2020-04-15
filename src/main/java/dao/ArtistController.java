package dao;

import com.github.javafaker.Faker;
import entities.Artist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class responsible
 * to get data regarding Albums
 * from database.
 *
 * @author Ioan Sava
 */
public class ArtistController extends Controller {

    public ArtistController() {
        try {
            connection = getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Insert a new Artist in database.
     */
    public void create(String name, String country) {
        try {
            String query = "insert into artists(name, country)" +
                    " values(?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, country);

            preparedStatement.execute();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Return a list of Artists which have the
     * specified @param name
     */
    public List<Artist> findByName(String name) {
        List<Artist> listOfArtists = new ArrayList<>();
        try {
            String query = "select * from artists where " +
                    "upper(trim(name)) = upper(trim(?));";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String country = resultSet.getString("country");
                listOfArtists.add(new Artist(id, name, country));
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return listOfArtists;
    }

    /**
     * @return an id of a random artist from database
     */
    public int randomArtistId() {
        int id = -1;
        try {
            Statement statement = connection.createStatement();

            // get random row
            String query = "select id from artists " +
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

    public void createRandom() {
        Faker faker = new Faker();
        String fakeName = faker.artist().name();
        String fakeCountry = faker.country().name();
        create(fakeName, fakeCountry);
    }
}
