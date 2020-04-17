package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ioan Sava
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChartAlbum {
    private int id;
    private int chartId;
    private int albumId;
    private int rank;

    public ChartAlbum(int chartId, int albumId, int rank) {
        this.chartId = chartId;
        this.albumId = albumId;
        this.rank = rank;
    }
}
