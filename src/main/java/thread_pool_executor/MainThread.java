package thread_pool_executor;

import dao.AlbumController;
import dao.ArtistController;
import dao.ChartController;

/**
 * Task executed by ThreadPoolExecutor
 *
 * @author Ioan Sava
 */
public class MainThread implements Runnable {
    private final int NUMBER_OF_ROWS = 50;

    @Override
    public void run() {
        insertRandomData(NUMBER_OF_ROWS);
        ChartController chartController = new ChartController();
        chartController.generateHTMLReport();
    }

    private void insertRandomData(int numberOfRows) {
        ArtistController artistController = new ArtistController();
        artistController.insertRandomArtists(numberOfRows);
        AlbumController albumController = new AlbumController();
        albumController.insertRandomAlbums(numberOfRows);
        ChartController chartController = new ChartController();
        chartController.insertRandomChart(numberOfRows);
    }
}
