import dao.ArtistController;
import org.junit.Assert;
import org.junit.Test;

/**
 * Simple unit tests
 *
 * @author Ioan Sava
 */
public class ApplicationTest {
    @Test
    public void testFindByName() {
        ArtistController artistController = new ArtistController();
        Assert.assertEquals(1, artistController.findByName("Eminem").size());
    }
}
