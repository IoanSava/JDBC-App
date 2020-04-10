package dao;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;
import models.Album;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * DAO class responsible
 * to get data regarding Albums
 * from database.
 *
 * @author Ioan Sava
 */
@NoArgsConstructor
public class AlbumController extends Controller {

    /**
     * Insert a new album in database.
     */
    public void create(String name, int artistId, int releaseYear) {
        try {
            connection = getConnection();
            String query = "insert into albums(name, artist_id, release_year)" +
                    " values(?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, artistId);
            preparedStatement.setInt(3, releaseYear);
            preparedStatement.execute();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Return a list of albums composed
     * by a specified artist.
     */
    public List<Album> findByArtist(int artistId) {
        List<Album> listOfAlbums = new ArrayList<>();
        try {
            connection = getConnection();
            String query = "select * from albums where " +
                    "artist_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, artistId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int releaseYear = resultSet.getInt("release_year");
                listOfAlbums.add(new Album(id, name, artistId, releaseYear));
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return listOfAlbums;
    }

    /**
     * @return an id of a random album from database
     */
    public int randomAlbumId() {
        int id = -1;
        try {
            connection = getConnection();
            Statement statement = connection.createStatement();

            String query = "select id from albums " +
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

    public void insertRandomAlbums(int numberOfRows) {
        ArtistController artistController = new ArtistController();
        Random random = new Random();
        Faker faker = new Faker();

        for (int i = 0; i < numberOfRows; ++i) {
            String fakeName = faker.music().instrument();
            int artistId = artistController.randomArtistId();
            int randomYear = random.nextInt(20) + 2000;
            create(fakeName, artistId, randomYear);
        }
    }
}
