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
    private List<ChartAlbum> chartAlbums;

    public Chart(String name) {
        this.name = name;
    }

    public Chart(String name, List<ChartAlbum> chartAlbums) {
        this.name = name;
        this.chartAlbums = chartAlbums;
    }

    public void addAlbum(ChartAlbum... chartAlbums) {
        this.chartAlbums.addAll(Arrays.asList(chartAlbums));
    }
}
