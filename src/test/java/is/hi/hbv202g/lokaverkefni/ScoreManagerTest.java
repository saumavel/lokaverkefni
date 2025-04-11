package is.hi.hbv202g.lokaverkefni;
import is.hi.hbv202g.lokaverkefni.score.ScoreManager;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ScoreManagerTest {
    private Map<Player, Integer> playerScores;
    private ScoreManager scoreManager;

    @Before
    public void setUp() {
        scoreManager = new ScoreManager();
        playerScores = new HashMap<>();
    }

    @Test
    public void testRegisterPlayer() {
        Player player = new Player("Player 1");
        scoreManager.registerPlayer(player);
        playerScores.put(player, 0);
        assert playerScores.containsKey(player);
    }
}




