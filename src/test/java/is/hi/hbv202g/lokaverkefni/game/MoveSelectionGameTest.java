package is.hi.hbv202g.lokaverkefni.game;

import is.hi.hbv202g.lokaverkefni.model.MoveFactory;
import is.hi.hbv202g.lokaverkefni.observer.ScoreObserver;
import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import is.hi.hbv202g.lokaverkefni.strategy.ComputerPlayerStrategy;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MoveSelectionGameTest {

    private MoveSelectionGame game;
    private Player player1;
    private Player player2;
    private Player computerPlayer;
    private GameTheme standardTheme;
    private GameTheme bathroomTheme;
    private MoveFactory standardMoveFactory;
    private TestScoreObserver scoreObserver;

    // Mock score observer for testing
    private static class TestScoreObserver implements ScoreObserver {
        private Player lastUpdatedPlayer;
        private int updateCount = 0;

        @Override
        public void updateScore(Player player) {
            lastUpdatedPlayer = player;
            updateCount++;
            player.incrementScoreInternal(); // Increment the score for testing
        }

        public Player getLastUpdatedPlayer() {
            return lastUpdatedPlayer;
        }

        public int getUpdateCount() {
            return updateCount;
        }
    }

    @Before
    public void setUp() {
        // Use the actual GameTheme enum values
        standardTheme = GameTheme.STANDARD;
        bathroomTheme = GameTheme.BATHROOM;

        // Create move factories
        standardMoveFactory = new MoveFactory(standardTheme);

        // Initialize players with standard theme
        player1 = new Player("Player 1", standardTheme);
        player2 = new Player("Player 2", standardTheme);
        computerPlayer = new Player(true, standardTheme);

        // Set up score observer
        scoreObserver = new TestScoreObserver();
        player1.registerObserver(scoreObserver);
        player2.registerObserver(scoreObserver);
        computerPlayer.registerObserver(scoreObserver);

        // Initialize game with human players using standard theme
        game = new MoveSelectionGame(player1, player2, standardTheme);
    }

    @Test
    public void testStandardThemeInitialization() {
        assertNotNull("Game should be initialized", game);
        assertEquals("Game should have 3 move options", 3, game.getNumberOfMoveOptions());

        String[] moveOptions = game.getMoveOptions();
        assertEquals("First move should be Rock", standardTheme.getMoveName(0), moveOptions[0]);
        assertEquals("Second move should be Paper", standardTheme.getMoveName(1), moveOptions[1]);
        assertEquals("Third move should be Scissors", standardTheme.getMoveName(2), moveOptions[2]);
    }

    @Test
    public void testBathroomThemeInitialization() {
        // Create a new game with bathroom theme
        Player bathroomPlayer1 = new Player("Player 1", bathroomTheme);
        Player bathroomPlayer2 = new Player("Player 2", bathroomTheme);
        MoveSelectionGame bathroomGame = new MoveSelectionGame(bathroomPlayer1, bathroomPlayer2, bathroomTheme);

        assertEquals("Game should have 3 move options", 3, bathroomGame.getNumberOfMoveOptions());

        String[] moveOptions = bathroomGame.getMoveOptions();
        assertEquals("First move should be Poop", bathroomTheme.getMoveName(0), moveOptions[0]);
        assertEquals("Second move should be Toilet Paper", bathroomTheme.getMoveName(1), moveOptions[1]);
        assertEquals("Third move should be Pee", bathroomTheme.getMoveName(2), moveOptions[2]);
    }

    @Test
    public void testPlayerSelectsMove() {
        // Player 1 selects Rock (index 0)
        game.playerSelectsMove(player1, 0);
        assertNotNull("Player 1 should have a move", player1.getCurrentMove());
        assertEquals("Player 1 should have selected Rock", standardTheme.getMoveName(0), player1.getCurrentMove().getName());

        // Player 2 selects Paper (index 1)
        game.playerSelectsMove(player2, 1);
        assertNotNull("Player 2 should have a move", player2.getCurrentMove());
        assertEquals("Player 2 should have selected Paper", standardTheme.getMoveName(1), player2.getCurrentMove().getName());
    }

    @Test
    public void testDetermineWinnerWithoutMoves() {
        // No moves selected yet
        String result = game.determineWinner();
        assertEquals("Should indicate both players need to make a move",
                "Both players must make a move!", result);
    }

    @Test
    public void testDetermineWinnerWithMoves() {
        // Player 1 selects Rock (index 0)
        game.playerSelectsMove(player1, 0);

        // Player 2 selects Paper (index 1)
        game.playerSelectsMove(player2, 1);

        // Determine winner (Paper beats Rock, so Player 2 should win)
        String result = game.determineWinner();
        assertTrue("Result should indicate Player 2 wins",
                result.contains("Player 2 wins"));
        assertTrue("Result should mention Paper beats Rock",
                result.contains(standardTheme.getMoveName(1) + " beats " + standardTheme.getMoveName(0)));
    }

    @Test
    public void testUndoLastMove() {
        // Player 1 selects Rock
        game.playerSelectsMove(player1, 0);
        assertNotNull("Player 1 should have a move", player1.getCurrentMove());

        // Player 2 selects Paper
        game.playerSelectsMove(player2, 1);
        assertNotNull("Player 2 should have a move", player2.getCurrentMove());

        // Undo last move (Player 2's move)
        game.undoLastMove();
        assertNull("Player 2's move should be null after undo", player2.getCurrentMove());
        assertNotNull("Player 1's move should still exist", player1.getCurrentMove());

        // Try to determine winner (should fail as Player 2 has no move)
        String result = game.determineWinner();
        assertEquals("Should indicate both players need to make a move",
                "Both players must make a move!", result);
    }

    @Test
    public void testDrawScenario() {
        // Both players select Rock
        game.playerSelectsMove(player1, 0);
        game.playerSelectsMove(player2, 0);

        // Determine winner (should be a draw)
        String result = game.determineWinner();
        assertTrue("Result should indicate a draw",
                result.contains("It's a draw"));
        assertTrue("Result should mention both chose Rock",
                result.contains("Both players chose " + standardTheme.getMoveName(0)));
    }

    @Test
    public void testComputerPlayerStrategy() {
        // Create a game with a computer player
        MoveSelectionGame computerGame = new MoveSelectionGame(player1, computerPlayer, standardTheme);

        // Set computer difficulty
        computerPlayer.setComputerDifficulty(ComputerPlayerStrategy.DifficultyLevel.MEDIUM);

        // Player 1 makes a move
        computerGame.playerSelectsMove(player1, 0); // Rock

        // Computer makes a move based on player's move
        computerPlayer.makeComputerMove(player1.getCurrentMove());

        // Verify computer made a move
        assertNotNull("Computer should have made a move", computerPlayer.getCurrentMove());
    }

    @Test
    public void testScoreObserverNotification() {
        // Player 1 wins a round
        player1.winRound();

        // Check if observer was notified
        assertEquals("Score observer should have been updated once", 1, scoreObserver.getUpdateCount());
        assertEquals("Last updated player should be Player 1", player1, scoreObserver.getLastUpdatedPlayer());

        // Player 2 wins a round
        player2.winRound();

        // Check if observer was notified again
        assertEquals("Score observer should have been updated twice", 2, scoreObserver.getUpdateCount());
        assertEquals("Last updated player should be Player 2", player2, scoreObserver.getLastUpdatedPlayer());
    }

    @Test
    public void testRemoveScoreObserver() {
        // Remove observer from player 1
        player1.removeObserver(scoreObserver);

        // Player 1 wins a round
        player1.winRound();

        // Observer should not be updated for player 1
        assertEquals("Score observer should not have been updated", 0, scoreObserver.getUpdateCount());

        // Player 2 still has the observer
        player2.winRound();

        // Observer should be updated for player 2
        assertEquals("Score observer should have been updated once", 1, scoreObserver.getUpdateCount());
        assertEquals("Last updated player should be Player 2", player2, scoreObserver.getLastUpdatedPlayer());
    }

    @Test
    public void testBathroomThemeGameplay() {
        // Create players with bathroom theme
        Player bathroomPlayer1 = new Player("Player 1", bathroomTheme);
        Player bathroomPlayer2 = new Player("Player 2", bathroomTheme);

        // Create game with bathroom theme
        MoveSelectionGame bathroomGame = new MoveSelectionGame(bathroomPlayer1, bathroomPlayer2, bathroomTheme);

        // Player 1 selects Poop (index 0)
        bathroomGame.playerSelectsMove(bathroomPlayer1, 0);

        // Player 2 selects Toilet Paper (index 1)
        bathroomGame.playerSelectsMove(bathroomPlayer2, 1);

        // Determine winner (Toilet Paper beats Poop, so Player 2 should win)
        String result = bathroomGame.determineWinner();
        assertTrue("Result should indicate Player 2 wins",
                result.contains("Player 2 wins"));
        assertTrue("Result should mention Toilet Paper beats Poop",
                result.contains(bathroomTheme.getMoveName(1) + " beats " + bathroomTheme.getMoveName(0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidMoveIndex() {
        // Try to create a move with an invalid index
        game.playerSelectsMove(player1, 5); // There are only 3 moves (0, 1, 2)
    }

    @Test
    public void testComputerPlayerDifficulty() {
        // Test that the computer player can have its difficulty set
        computerPlayer.setComputerDifficulty(ComputerPlayerStrategy.DifficultyLevel.EASY);

        // Make a move with the player
        player1.setMove(standardMoveFactory.createMove(0)); // Rock

        // Computer makes a move based on player's move
        computerPlayer.makeComputerMove(player1.getCurrentMove());

        // Verify computer made a move
        assertNotNull("Computer should have made a move with EASY difficulty",
                computerPlayer.getCurrentMove());

        // Change difficulty and test again
        computerPlayer.setComputerDifficulty(ComputerPlayerStrategy.DifficultyLevel.HARD);

        // Reset computer move
        computerPlayer.setMove(null);

        // Player makes a different move
        player1.setMove(standardMoveFactory.createMove(1)); // Paper

        // Computer makes a move based on player's new move
        computerPlayer.makeComputerMove(player1.getCurrentMove());

        // Verify computer made a move
        assertNotNull("Computer should have made a move with HARD difficulty",
                computerPlayer.getCurrentMove());
    }

    @Test
    public void testHumanVsComputerGame() {
        // Create a game with human vs computer
        MoveSelectionGame humanVsComputer = new MoveSelectionGame(player1, computerPlayer, standardTheme);

        // Set computer difficulty
        computerPlayer.setComputerDifficulty(ComputerPlayerStrategy.DifficultyLevel.MEDIUM);

        // Human player selects Rock
        humanVsComputer.playerSelectsMove(player1, 0);

        // Computer makes a move based on human's move
        computerPlayer.makeComputerMove(player1.getCurrentMove());

        // Determine winner
        String result = humanVsComputer.determineWinner();

        // The result should contain either "Player 1 wins", "Computer wins", or "It's a draw"
        assertTrue("Result should indicate a winner or draw",
                result.contains("wins") || result.contains("draw"));
    }
}
