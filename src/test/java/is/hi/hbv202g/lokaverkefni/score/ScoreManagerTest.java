package is.hi.hbv202g.lokaverkefni.score;

import is.hi.hbv202g.lokaverkefni.game.Player;
import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ScoreManagerTest {

    private ScoreManager scoreManager;
    private Player player1;
    private Player player2;

    @Before
    public void setUp() {
        // Initialize the ScoreManager
        scoreManager = new ScoreManager();

        // Use the standard theme for testing
        GameTheme theme = GameTheme.STANDARD;

        // Create test players
        player1 = new Player("Player 1", theme);
        player2 = new Player("Player 2", theme);

        // Register players with the score manager
        scoreManager.registerPlayer(player1);
        scoreManager.registerPlayer(player2);
    }

    @Test
    public void testInitialScores() {
        // Check initial score summary
        String scoreSummary = scoreManager.getScoreSummary();

        // Verify all players have 0 points
        assertTrue("Player 1 should have 0 points", scoreSummary.contains("Player 1: 0"));
        assertTrue("Player 2 should have 0 points", scoreSummary.contains("Player 2: 0"));
        assertTrue("Draws should be 0", scoreSummary.contains("Draws: 0"));
        assertTrue("Rounds played should be 0", scoreSummary.contains("Rounds played: 0"));
    }

    @Test
    public void testUpdateScore() {
        // Player 1 wins a round (3 points)
        player1.winRound();

        // Player 2 wins a round (3 points)
        player2.winRound();

        // Player 1 wins another round (3 more points, total 6)
        player1.winRound();

        // Check score summary
        String scoreSummary = scoreManager.getScoreSummary();
        assertTrue("Player 1 should have 6 points", scoreSummary.contains("Player 1: 6"));
        assertTrue("Player 2 should have 3 points", scoreSummary.contains("Player 2: 3"));
    }

    @Test
    public void testRecordDraw() {
        // Record a draw (1 point for each player)
        scoreManager.recordDraw();

        // Check score summary
        String scoreSummary = scoreManager.getScoreSummary();
        assertTrue("Player 1 should have 1 point", scoreSummary.contains("Player 1: 1"));
        assertTrue("Player 2 should have 1 point", scoreSummary.contains("Player 2: 1"));
        assertTrue("Draws should be 1", scoreSummary.contains("Draws: 1"));
    }

    @Test
    public void testRecordRoundPlayed() {
        // Record rounds played
        scoreManager.recordRoundPlayed();
        scoreManager.recordRoundPlayed();
        scoreManager.recordRoundPlayed();

        // Check score summary
        String scoreSummary = scoreManager.getScoreSummary();
        assertTrue("Rounds played should be 3", scoreSummary.contains("Rounds played: 3"));
    }

    @Test
    public void testGetWinningPlayer() {
        // No winner initially (all tied at 0)
        assertNull("No winner should be determined initially", scoreManager.getWinningPlayer());

        // Player 1 wins a round
        player1.winRound();
        assertEquals("Player 1 should be winning", player1, scoreManager.getWinningPlayer());

        // Player 2 wins two rounds, becoming the leader
        player2.winRound();
        player2.winRound();
        assertEquals("Player 2 should be winning", player2, scoreManager.getWinningPlayer());

        // Player 1 wins another round, creating a tie
        player1.winRound();
        assertNull("No winner should be determined when tied", scoreManager.getWinningPlayer());
    }

    @Test
    public void testIsTied() {
        // Initially all players have 0 points, so it's a tie
        assertTrue("Game should be tied initially", scoreManager.isTied());

        // Player 1 wins a round
        player1.winRound();
        assertFalse("Game should not be tied after Player 1 wins", scoreManager.isTied());

        // Player 2 wins a round
        player2.winRound();
        assertTrue("Game should be tied when both players have same score", scoreManager.isTied());

        // Two players with same high score should be a tie
        player1.winRound(); // Player 1: 6, Player 2: 3
        player2.winRound(); // Player 1: 6, Player 2: 6
        assertTrue("Game should be tied when top players have same score", scoreManager.isTied());
    }

    @Test
    public void testResetScores() {
        // Add some scores
        player1.winRound();
        player2.winRound();
        scoreManager.recordDraw();
        scoreManager.recordRoundPlayed();
        scoreManager.recordRoundPlayed();

        // Reset scores
        scoreManager.resetScores();

        // Check score summary after reset
        String scoreSummary = scoreManager.getScoreSummary();
        assertTrue("Player 1 should have 0 points after reset", scoreSummary.contains("Player 1: 0"));
        assertTrue("Player 2 should have 0 points after reset", scoreSummary.contains("Player 2: 0"));
        assertTrue("Draws should be 0 after reset", scoreSummary.contains("Draws: 0"));
        assertTrue("Rounds played should be 0 after reset", scoreSummary.contains("Rounds played: 0"));
    }

    @Test
    public void testGetFinalResultsSummary() {
        // Set up a game scenario
        player1.winRound(); // 3 points
        player2.winRound(); // 3 points
        player2.winRound(); // 6 points
        scoreManager.recordDraw(); // 1 point each
        scoreManager.recordRoundPlayed();
        scoreManager.recordRoundPlayed();
        scoreManager.recordRoundPlayed();
        scoreManager.recordRoundPlayed();

        // Get final results
        String finalResults = scoreManager.getFinalResultsSummary();

        // Check that it contains the expected information
        assertTrue("Final results should show rounds played",
                finalResults.contains("Final Score after 4 rounds"));
        assertTrue("Final results should show Player 1's score",
                finalResults.contains("Player 1: 4"));
        assertTrue("Final results should show Player 2's score",
                finalResults.contains("Player 2: 7"));
        assertTrue("Final results should show draws",
                finalResults.contains("Draws: 1"));
        assertTrue("Final results should show the winner",
                finalResults.contains("Overall Winner: Player 2"));
    }

    @Test
    public void testFinalResultsWithTie() {
        // Create a tie scenario
        player1.winRound(); // 3 points
        player2.winRound(); // 3 points
        scoreManager.recordRoundPlayed();
        scoreManager.recordRoundPlayed();

        // Get final results
        String finalResults = scoreManager.getFinalResultsSummary();

        // Check that it indicates a tie
        assertTrue("Final results should indicate a tie",
                finalResults.contains("The game ended in a tie"));
    }

    @Test
    public void testScoreObserverIntegration() {
        // Test that the ScoreManager properly observes player wins

        // Initial state
        assertNull("No player should be winning initially", scoreManager.getWinningPlayer());

        // Player 1 wins through the observer pattern
        player1.winRound();

        // Check that ScoreManager was notified and updated the score
        assertEquals("Player 1 should be winning after winRound()", player1, scoreManager.getWinningPlayer());

        // Check score value directly through summary
        String scoreSummary = scoreManager.getScoreSummary();
        assertTrue("Player 1 should have 3 points", scoreSummary.contains("Player 1: 3"));
    }

    @Test
    public void testEmptyScoreManager() {
        // Create a new empty score manager
        ScoreManager emptyManager = new ScoreManager();

        // Test behavior with no players
        assertNull("Empty manager should have no winning player", emptyManager.getWinningPlayer());
        assertFalse("Empty manager should not be tied", emptyManager.isTied());

        // Should not throw exceptions
        emptyManager.recordDraw();
        emptyManager.recordRoundPlayed();
        emptyManager.resetScores();

        // Get summaries (should not throw exceptions)
        String scoreSummary = emptyManager.getScoreSummary();
        String finalResults = emptyManager.getFinalResultsSummary();

        assertTrue("Score summary should contain basic structure",
                scoreSummary.contains("CURRENT SCORE"));
        assertTrue("Final results should contain basic structure",
                finalResults.contains("GAME OVER"));
    }
}
