package is.hi.hbv202g.lokaverkefni;
import is.hi.hbv202g.lokaverkefni.score.ScoreManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class GameManagerTest {
    private ScoreManager scoreManager;

    @Before
    public void setUp() {
        scoreManager = new ScoreManager();
    }

    @Test
    public void testSetupGame() {
        scoreManager.resetScores();
    }
}
