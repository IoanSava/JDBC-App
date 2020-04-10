package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ioan Sava
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Album {
    private int id;
    private String name;
    private int artistId;
    private int releaseYear;

    public Album(String name, int artistId) {
        this.name = name;
        this.artistId = artistId;
    }

    public Album(int id, String name, int artistId) {
        this.id = id;
        this.name = name;
        this.artistId = artistId;
    }
}
