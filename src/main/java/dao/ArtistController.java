package dao;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;
import models.Artist;

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
@NoArgsConstructor
public class ArtistController extends Controller {

    /**
     * Insert a new Artist in database.
     */
    public void create(String name, String country) {
        try {
            connection = getConnection();
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
            connection = getConnection();
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
            connection = getConnection();
            Statement statement = connection.createStatement();

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

    public void insertRandomArtists(int numberOfRows) {
        Faker faker = new Faker();
        for (int i = 0; i < numberOfRows; ++i) {
            String fakeName = faker.artist().name();
            String fakeCountry = faker.country().name();
            create(fakeName, fakeCountry);
        }
    }
}
