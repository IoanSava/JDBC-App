package thread_pool_executor;

import dao.AlbumController;
import dao.ArtistController;
import dao.ChartAlbumController;

/**
 * Task executed by ThreadPoolExecutor
 *
 * @author Ioan Sava
 */
public class MainThread implements Runnable {
    private final int NUMBER_OF_ROWS = 50;

    private ArtistController artistController = new ArtistController();
    private AlbumController albumController = new AlbumController();
    private ChartAlbumController chartAlbumController = new ChartAlbumController();

    @Override
    public void run() {
        insertRandomData(NUMBER_OF_ROWS);
    }

    private void insertRandomData(int numberOfRows) {
        for (int i = 0; i < numberOfRows; ++i) {
            artistController.createRandom();
            albumController.createRandom();
            chartAlbumController.createRandom();
        }
    }
}
