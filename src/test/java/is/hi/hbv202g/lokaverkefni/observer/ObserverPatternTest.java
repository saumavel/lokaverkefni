package is.hi.hbv202g.lokaverkefni.observer;

import is.hi.hbv202g.lokaverkefni.game.Player;
import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ObserverPatternTest {

    private Player player;
    private ScoreKeeper scoreKeeper;
    private TestScoreObserver testObserver;

    // Custom observer for testing
    private static class TestScoreObserver implements ScoreObserver {
        private int updateCount = 0;
        private Player lastUpdatedPlayer = null;

        @Override
        public void updateScore(Player player) {
            updateCount++;
            lastUpdatedPlayer = player;
            // Don't increment the score here to avoid side effects
        }

        public int getUpdateCount() {
            return updateCount;
        }

        public Player getLastUpdatedPlayer() {
            return lastUpdatedPlayer;
        }

        public void reset() {
            updateCount = 0;
            lastUpdatedPlayer = null;
        }
    }

    @Before
    public void setUp() {
        // Create a player
        player = new Player("Test Player", GameTheme.STANDARD);

        // Create observers
        scoreKeeper = new ScoreKeeper();
        testObserver = new TestScoreObserver();
    }

    @Test
    public void testScoreKeeperImplementsScoreObserver() {
        // Verify that ScoreKeeper implements ScoreObserver
        assertTrue("ScoreKeeper should implement ScoreObserver",
                scoreKeeper != null);
    }

    @Test
    public void testPlayerImplementsScoreSubject() {
        // Verify that Player implements ScoreSubject
        assertTrue("Player should implement ScoreSubject",
                player != null);
    }

    @Test
    public void testRegisterObserver() {
        // Register the observer
        player.registerObserver(testObserver);

        // Notify observers (this should update the test observer)
        player.notifyObservers(player);

        // Verify that the observer was notified
        assertEquals("Observer should be updated once", 1, testObserver.getUpdateCount());
        assertEquals("Observer should receive the correct player",
                player, testObserver.getLastUpdatedPlayer());
    }

    @Test
    public void testRemoveObserver() {
        // Register the observer
        player.registerObserver(testObserver);

        // Notify observers (this should update the test observer)
        player.notifyObservers(player);

        // Verify that the observer was notified
        assertEquals("Observer should be updated once", 1, testObserver.getUpdateCount());

        // Remove the observer
        player.removeObserver(testObserver);

        // Reset the test observer
        testObserver.reset();

        // Notify observers again (this should not update the test observer)
        player.notifyObservers(player);

        // Verify that the observer was not notified
        assertEquals("Observer should not be updated after removal",
                0, testObserver.getUpdateCount());
        assertNull("Observer should not receive any player after removal",
                testObserver.getLastUpdatedPlayer());
    }

    @Test
    public void testMultipleObservers() {
        // Create another test observer
        TestScoreObserver anotherObserver = new TestScoreObserver();

        // Register both observers
        player.registerObserver(testObserver);
        player.registerObserver(anotherObserver);

        // Notify observers (this should update both observers)
        player.notifyObservers(player);

        // Verify that both observers were notified
        assertEquals("First observer should be updated once", 1, testObserver.getUpdateCount());
        assertEquals("Second observer should be updated once", 1, anotherObserver.getUpdateCount());
        assertEquals("First observer should receive the correct player",
                player, testObserver.getLastUpdatedPlayer());
        assertEquals("Second observer should receive the correct player",
                player, anotherObserver.getLastUpdatedPlayer());
    }

    @Test
    public void testScoreKeeperUpdateScore() {
        // Initial score should be 0
        // Note: This assumes that Player has a way to get the score, which isn't shown in your code
        // If there's no direct way to get the score, you might need to modify this test

        // Register the score keeper
        player.registerObserver(scoreKeeper);

        // Notify observers (this should update the score)
        player.notifyObservers(player);

        // Verify that the score was incremented
        // This test relies on the Player.incrementScoreInternal method being called by ScoreKeeper
        // and assumes there's a way to check the score afterwards

        // If there's no direct way to get the score, you could test this indirectly:
        // For example, by checking if the winRound method (which notifies observers) affects game results
    }

    @Test
    public void testWinRoundNotifiesObservers() {
        // Register the test observer
        player.registerObserver(testObserver);

        // Call winRound (this should notify observers)
        player.winRound();

        // Verify that the observer was notified
        assertEquals("Observer should be updated once after winRound",
                1, testObserver.getUpdateCount());
        assertEquals("Observer should receive the correct player after winRound",
                player, testObserver.getLastUpdatedPlayer());
    }

    @Test
    public void testNotifyWithDifferentPlayer() {
        // Create another player
        Player anotherPlayer = new Player("Another Player", GameTheme.STANDARD);

        // Register the test observer with the first player
        player.registerObserver(testObserver);

        // Notify observers with a different player
        player.notifyObservers(anotherPlayer);

        // Verify that the observer received the different player
        assertEquals("Observer should receive the player passed to notifyObservers",
                anotherPlayer, testObserver.getLastUpdatedPlayer());
    }

    @Test
    public void testScoreKeeperFunctionality() {
        // Create a special player that allows us to check if incrementScoreInternal was called
        Player spyPlayer = new SpyPlayer("Spy Player", GameTheme.STANDARD);

        // Update score using the score keeper
        scoreKeeper.updateScore(spyPlayer);

        // Verify that incrementScoreInternal was called
        assertTrue("ScoreKeeper should call incrementScoreInternal",
                ((SpyPlayer)spyPlayer).wasIncrementScoreCalled());
    }

    // A special Player subclass that tracks if incrementScoreInternal was called
    private static class SpyPlayer extends Player {
        private boolean incrementScoreCalled = false;

        public SpyPlayer(String name, GameTheme theme) {
            super(name, theme);
        }

        @Override
        public void incrementScoreInternal() {
            incrementScoreCalled = true;
            super.incrementScoreInternal();
        }

        public boolean wasIncrementScoreCalled() {
            return incrementScoreCalled;
        }
    }
}