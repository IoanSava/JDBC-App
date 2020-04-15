package entities;

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
public class Artist {
    private int id;
    private String name;
    private String country;

    public Artist(String name) {
        this.name = name;
    }

    public Artist(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
