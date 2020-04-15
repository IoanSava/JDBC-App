package entities;

import lombok.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ioan Sava
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Chart {
    private int id;
    private String name;
    private List<Album> albums;

    public Chart(String name) {
        this.name = name;
    }

    public Chart(String name, List<Album> albums) {
        this.name = name;
        this.albums = albums;
    }

    public void addAlbum(Album... albums) {
        this.albums.addAll(Arrays.asList(albums));
    }
}
