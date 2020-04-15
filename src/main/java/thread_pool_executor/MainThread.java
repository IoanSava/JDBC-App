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

    private ArtistController artistController = new ArtistController();
    private AlbumController albumController = new AlbumController();
    private ChartController chartController = new ChartController();

    @Override
    public void run() {
        insertRandomData(NUMBER_OF_ROWS);
        ChartController chartController = new ChartController();
        chartController.generateHTMLReport();
    }

    private void insertRandomData(int numberOfRows) {
        for (int i = 0; i < numberOfRows; ++i) {
            artistController.createRandom();
            albumController.createRandom();
            chartController.createRandom();
        }
    }
}
