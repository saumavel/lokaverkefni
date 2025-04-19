package is.hi.hbv202g.lokaverkefni.game;

import is.hi.hbv202g.lokaverkefni.options.enums.GameMode;
import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import is.hi.hbv202g.lokaverkefni.score.ScoreManager;
import is.hi.hbv202g.lokaverkefni.strategy.ComputerPlayerStrategy;

public class GameManager {
    private Player player1;
    private Player player2;
    private GameTheme gameTheme = GameTheme.STANDARD; // Default theme
    private MoveSelectionGame game;
    private int consecutiveWins = 0;
    private int consecutiveLosses = 0; // Track consecutive losses
    private GameMode gameMode;
    private ComputerPlayerStrategy.DifficultyLevel currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.EASY;
    private final ScoreManager scoreManager = new ScoreManager();
    private int roundsPlayed = 0;
    private final GameUI ui;
    private final StatisticsTracker stats = new StatisticsTracker();

    /**
     * Creates a new GameManager.
     */
    public GameManager() {
        ui = new GameUI();
        ui.promptLanguageSelection();
    }

    /**
     * Starts the game flow.
     */
    public void start() {
        setupGame();
        boolean continuePlaying = true;

        while (continuePlaying) {
            continuePlaying = playRound();
            if (continuePlaying) {
                ui.displayScoreSummary(scoreManager);
                stats.incrementRoundsPlayed();
                roundsPlayed++;
                if (roundsPlayed % 5 == 0) {
                    continuePlaying = ui.askToPlayAgain(roundsPlayed);
                }
            }
        }

        ui.displayFinalResults(scoreManager);
    }

    /**
     * Sets up a new game.
     */
    private void setupGame() {
        scoreManager.resetScores();
        stats.reset();

        GameConfiguration config = ui.setupGame();
        this.player1 = config.player1();
        this.player2 = config.player2();
        this.gameTheme = config.theme();
        this.gameMode = config.mode();
        this.currentDifficulty = config.difficulty();

        scoreManager.registerPlayer(player1);
        scoreManager.registerPlayer(player2);
        game = new MoveSelectionGame(player1, player2, gameTheme);
    }

    /**
     * Plays a single round of the game.
     *
     * @return true if the game should continue, false if the player quit
     */
    private boolean playRound() {
        // Display round number
        ui.displayRoundNumber(roundsPlayed + 1);

        // Check if player has lost 5 times in a row and show secret message
        ui.showSecretHintIfNeeded(consecutiveLosses, gameMode);

        boolean continueGame;
        if (gameMode == GameMode.SINGLE_PLAYER) {
            continueGame = playComputerRound();
        } else {
            continueGame = playTwoPlayerRound();
        }

        // If player chose to quit, return false to end the game
        if (!continueGame) {
            return false;
        }

        String result = game.determineWinner();

        // Display a clear, concise result message
        ui.displayRoundResult(result, player1, player2, gameMode);

        // Update game statistics based on the result
        updateGameStats(result);

        // Record that a round was played
        scoreManager.recordRoundPlayed();

        // Check if player1 won and offer difficulty increase if needed
        if (consecutiveWins >= 5 && gameMode == GameMode.SINGLE_PLAYER &&
                currentDifficulty != ComputerPlayerStrategy.DifficultyLevel.HARD) {
            currentDifficulty = ui.offerDifficultyIncrease(currentDifficulty, player2);
            consecutiveWins = 0;
        }

        return true;
    }

    /**
     * Plays a round against the computer.
     *
     * @return true if the game should continue, false if the player quit
     */
    private boolean playComputerRound() {
        Integer moveIndex = ui.getPlayerMove(player1, game);

        // Check for special commands
        if (moveIndex == null) {
            return false; // Player quit
        } else if (moveIndex == -1) {
            handleUndo();
            return playComputerRound(); // Try again after undo
        }

        // Valid move index
        game.playerSelectsMove(player1, moveIndex);
        ui.displayPlayerMove(player1.getCurrentMove().getName());

        // Computer makes its move
        player2.makeComputerMove(player1.getCurrentMove());
        ui.displayComputerMove(player2.getCurrentMove().getName());

        return true;
    }

    /**
     * Plays a round with two human players.
     *
     * @return true if the game should continue, false if a player quit
     */
    private boolean playTwoPlayerRound() {
        // Player 1's turn
        Integer moveIndex = ui.getPlayerMove(player1, game);

        // Check for special commands
        if (moveIndex == null) {
            return false; // Player quit
        } else if (moveIndex == -1) {
            handleUndo();
            return playTwoPlayerRound(); // Try again after undo
        }

        game.playerSelectsMove(player1, moveIndex);

        // Player 2's turn
        moveIndex = ui.getPlayerMove(player2, game);

        // Check for special commands
        if (moveIndex == null) {
            return false; // Player quit
        } else if (moveIndex == -1) {
            handleUndo();
            return playTwoPlayerRound(); // Try again after undo
        }

        game.playerSelectsMove(player2, moveIndex);

        return true;
    }

    /**
     * Handles the undo operation.
     */
    private void handleUndo() {
        game.undoLastMove();
        ui.displayUndoMessage(consecutiveLosses);
    }

    /**
     * Updates game statistics based on the result.
     *
     * @param result The result string from the game.
     */
    private void updateGameStats(String result) {
        if (result.startsWith(player1.getName() + " wins")) {
            player1.winRound(); // This will notify observers to increment the score
            consecutiveWins++;
            consecutiveLosses = 0; // Reset consecutive losses when player wins
        } else if (result.startsWith(player2.getName() + " wins")) {
            player2.winRound(); // This will notify observers to increment the score
            consecutiveWins = 0;
            consecutiveLosses++; // Increment consecutive losses when computer wins
        } else if (result.startsWith("It's a draw")) {
            scoreManager.recordDraw(); // Record a draw in the score manager
            consecutiveWins = 0;
            consecutiveLosses = 0; // Reset consecutive losses on draw
        }
    }
}