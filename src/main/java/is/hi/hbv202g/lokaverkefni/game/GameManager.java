package is.hi.hbv202g.lokaverkefni.game;

import is.hi.hbv202g.lokaverkefni.options.enums.GameMode;
import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import is.hi.hbv202g.lokaverkefni.options.parsing.InputParser;
import is.hi.hbv202g.lokaverkefni.options.parsing.MoveNameMapper;
import is.hi.hbv202g.lokaverkefni.options.translation.TranslationManager;
import is.hi.hbv202g.lokaverkefni.options.translation.TranslationsInitializer;
import is.hi.hbv202g.lokaverkefni.score.ScoreManager;
import is.hi.hbv202g.lokaverkefni.strategy.ComputerPlayerStrategy;

import java.util.Scanner;

public class GameManager {
    private Player player1;
    private Player player2;
    private GameTheme gameTheme = GameTheme.STANDARD; // Default theme
    private MoveSelectionGame game;
    private final Scanner scanner;
    private int consecutiveWins = 0;
    private int consecutiveLosses = 0; // Track consecutive losses
    private GameMode gameMode;
    private ComputerPlayerStrategy.DifficultyLevel currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.EASY;
    private final ScoreManager scoreManager = new ScoreManager();
    private int roundsPlayed = 0;


    /**
     * Creates a new GameManager.
     */
    public GameManager() {
        scanner = new Scanner(System.in);
        TranslationsInitializer.load();
        TranslationManager.promptLanguageSelection(scanner);
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
                    System.out.println(scoreManager.getScoreSummary());
                    roundsPlayed++;
                    if (roundsPlayed % 5 == 0) {
                        continuePlaying = askToPlayAgain();
                    }
                }
            }

            System.out.println(scoreManager.getFinalResultsSummary());
            System.out.println(TranslationManager.get("thanks_for_playing"));
    }



    /**
     * Sets up a new game.
     */
    private void setupGame() {
        scoreManager.resetScores();
        consecutiveWins = 0;
        consecutiveLosses = 0;
        roundsPlayed = 0;

        System.out.println(TranslationManager.get("welcome"));

        GameConfiguration config = GameSetup.initializeGame(scanner);
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
        System.out.println("\n===== " + TranslationManager.get("round") + " " + (roundsPlayed + 1) + " =====");

        // Check if player has lost 5 times in a row and show secret message
        if (consecutiveLosses >= 5 && gameMode == GameMode.SINGLE_PLAYER) {
            System.out.println("\n" + TranslationManager.get("secret_hint") + "\n");
        }

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
        System.out.println("\n" + result);

        // Display a clear, concise result message
        displayRoundResult(result);

        // Update game statistics based on the result
        updateGameStats(result);

        // Record that a round was played
        scoreManager.recordRoundPlayed();

        // Check if player1 won and offer difficulty increase if needed
        if (consecutiveWins >= 5 && gameMode == GameMode.SINGLE_PLAYER && currentDifficulty != ComputerPlayerStrategy.DifficultyLevel.HARD) {
            offerDifficultyIncrease();
        }

        return true;
    }

    /**
     * Plays a round against the computer.
     *
     * @return true if the game should continue, false if the player quit
     */
    private boolean playComputerRound() {
        System.out.println("\n" + player1.getName() + TranslationManager.get("choose_move"));

        String[] moveOptions = game.getMoveOptions();
        for (int i = 0; i < moveOptions.length; i++) {
            System.out.println((i + 1) + ". " + moveOptions[i]);
        }

        String moveChoice = scanner.nextLine().toLowerCase();

        if (moveChoice.equalsIgnoreCase("q")) {
            System.out.println(TranslationManager.get("quit_game"));
            return false;
        }

        if (moveChoice.equalsIgnoreCase("u")) {
            handleUndo();
            // After undo, prompt for a move again
            return playComputerRound();
        }

        // Try to interpret the input as a move name
        Integer moveIndex = getMoveIndexFromInput(moveChoice);

        if (moveIndex != null) {
            // Valid move name input
            game.playerSelectsMove(player1, moveIndex);
            System.out.println(TranslationManager.get("you_chose") + player1.getCurrentMove().getName());
        } else {
            try {
                // Try to interpret as a number
                int choice = Integer.parseInt(moveChoice);
                if (choice >= 1 && choice <= game.getNumberOfMoveOptions()) {
                    // Convert to zero-based index for the game
                    moveIndex = choice - 1;

                    // Use the player select move method with the appropriate index
                    game.playerSelectsMove(player1, moveIndex);
                    System.out.println(TranslationManager.get("you_chose") + player1.getCurrentMove().getName());
                } else {
                    System.out.println(TranslationManager.get("invalid_choice"));
                    return playComputerRound(); // Ask again instead of defaulting
                }
            } catch (NumberFormatException e) {
                // Invalid input - not a number and not a recognized move name
                System.out.println(TranslationManager.get("invalid_choice_number"));
                return playComputerRound(); // Ask again instead of defaulting
            }
        }

        // Computer makes its move
        player2.makeComputerMove(player1.getCurrentMove());
        System.out.println(TranslationManager.get("computer_chose") + player2.getCurrentMove().getName());

        return true;
    }


    private Integer getMoveIndexFromInput(String input) {
        // Use the OptionsManager method instead of local implementation
        return MoveNameMapper.getMoveIndex(input, gameTheme);
    }

    /**
     * Plays a round with two human players.
     *
     * @return true if the game should continue, false if a player quit
     */
    private boolean playTwoPlayerRound() {
        // Player 1's turn
        if (!getPlayerMove(player1)) {
            return false;
        }

        // Player 2's turn
        return getPlayerMove(player2);
    }

    /**
     * Gets a move from a player with undo support.
     *
     * @param player The player making the move
     * @return true if the game should continue, false if the player quit
     */
    private boolean getPlayerMove(Player player) {
        System.out.println("\n" + player.getName() + TranslationManager.get("choose_move"));

        String[] moveOptions = game.getMoveOptions();
        for (int i = 0; i < moveOptions.length; i++) {
            System.out.println((i + 1) + ". " + moveOptions[i]);
        }

        String moveChoice = scanner.nextLine().toLowerCase();

        if (moveChoice.equalsIgnoreCase("q")) {
            System.out.println(player.getName() + TranslationManager.get("player_quit_game"));
            return false;
        }

        if (moveChoice.equalsIgnoreCase("u")) {
            handleUndo();
            // After undo, prompt for a move again
            return getPlayerMove(player);
        }

        // Try to interpret the input as a move name
        Integer moveIndex = getMoveIndexFromInput(moveChoice);

        if (moveIndex != null) {
            // Valid move name input
            game.playerSelectsMove(player, moveIndex);
            System.out.println(player.getName() + TranslationManager.get("player_chose") + player.getCurrentMove().getName());
            return true;
        } else {
            try {
                // Try to interpret as a number
                int choice = Integer.parseInt(moveChoice);
                if (choice >= 1 && choice <= game.getNumberOfMoveOptions()) {
                    // Convert to zero-based index
                    moveIndex = choice - 1;

                    // Use the player select move method with the appropriate player and index
                    game.playerSelectsMove(player, moveIndex);
                    System.out.println(player.getName() + TranslationManager.get("player_chose") + player.getCurrentMove().getName());
                    return true;
                } else {
                    System.out.println(TranslationManager.get("invalid_choice"));
                    return getPlayerMove(player);
                }
            } catch (NumberFormatException e) {
                System.out.println(TranslationManager.get("invalid_choice_number"));
                return getPlayerMove(player);
            }
        }
    }

    /**
     * Handles the undo operation.
     */
    private void handleUndo() {
        game.undoLastMove();
        System.out.println(TranslationManager.get("undo_move"));

        // If we're in a round where scores were already updated, we need to adjust them
        if (roundsPlayed > 0) {
            // This is a simplified approach - in a real implementation,
            // you might need more sophisticated score tracking for proper undo
            System.out.println(TranslationManager.get("undo_note"));
        }

        // If player has been losing and uses undo, add a little encouragement
        if (consecutiveLosses >= 5) {
            System.out.println(TranslationManager.get("undo_secret"));
        }
    }

    /**
     * Displays a clear message about who won the round.
     *
     * @param result The result string from the game.
     */
    private void displayRoundResult(String result) {
        // Check if player1 won
        if (result.startsWith(player1.getName() + " wins")) {
            System.out.println("🎉 " + player1.getName() + TranslationManager.get("player_wins"));
        }
        // Check if player2 won
        else if (result.startsWith(player2.getName() + " wins")) {
            if (gameMode == GameMode.SINGLE_PLAYER) {
                // If it is a computer game, then the player is the
                // computer and the message should be different
                System.out.println(TranslationManager.get("computer_wins"));
            } else {
                // If it is a two player game, then the message should
                // be the same as if player1 won
                System.out.println("🎉 " + player2.getName() + TranslationManager.get("player_wins"));
            }
        }
        // Check if it is a draw
        else if (result.startsWith("It's a draw")) {
            System.out.println(TranslationManager.get("draw"));
        }
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

    /**
     * Offer the player the option to increase the difficulty level.
     */
    private void offerDifficultyIncrease() {
        System.out.println(TranslationManager.get("win_streak"));
        System.out.println(TranslationManager.get("increase_difficulty"));

        String response = scanner.nextLine().toLowerCase();

        if (InputParser.isYes(response)) {
            switch (currentDifficulty) {
                case MAN:
                    currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.EASY;
                    player2.setComputerDifficulty(currentDifficulty);
                    System.out.println(TranslationManager.get("difficulty_increased_easy"));
                    break;
                case EASY:
                    currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.MEDIUM;
                    player2.setComputerDifficulty(currentDifficulty);
                    System.out.println(TranslationManager.get("difficulty_increased_medium"));
                    break;
                case MEDIUM:
                    currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.HARD;
                    player2.setComputerDifficulty(currentDifficulty);
                    System.out.println(TranslationManager.get("difficulty_increased_hard"));
                    break;
                case HARD:
                    // Already at max difficulty
                    System.out.println(TranslationManager.get("max_difficulty"));
                    break;
            }
            // Reset consecutive wins after increasing difficulty
            consecutiveWins = 0;
        }
    }


    /**
     * Prompts the player to play another round.
     *
     * @return True if the player wants to play again, false otherwise.
     */
    private boolean askToPlayAgain() {
        System.out.println(TranslationManager.get("continue_playing") + roundsPlayed + TranslationManager.get("rounds"));
        String response = scanner.nextLine().toLowerCase();

        return InputParser.isYes(response);
    }
}
