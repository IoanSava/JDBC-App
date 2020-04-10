package models;

import lombok.*;

/**
 * @author Ioan Sava
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Chart {
    private int id;
    private int albumId;
    private long sales;

    public Chart(int albumId, int sales) {
        this.albumId = albumId;
        this.sales = sales;
    }
}
