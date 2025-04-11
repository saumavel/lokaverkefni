package is.hi.hbv202g.lokaverkefni.game;

import is.hi.hbv202g.lokaverkefni.options.GameMode;
import is.hi.hbv202g.lokaverkefni.options.GameTheme;
import is.hi.hbv202g.lokaverkefni.options.OptionsManager;
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

    // Replace individual score tracking with ScoreManager
    private final ScoreManager scoreManager = new ScoreManager();

    // Track the number of rounds played
    private int roundsPlayed = 0;

    /**
     * Creates a new GameManager.
     */
    public GameManager() {
        scanner = new Scanner(System.in);

        // Prompt for language selection at the start
        OptionsManager.promptLanguageSelection(scanner);
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

                // Increment rounds played
                roundsPlayed++;

                // Only ask to continue every 5 rounds
                if (roundsPlayed % 5 == 0) {
                    continuePlaying = askToPlayAgain();
                }
            }
        }

        System.out.println(scoreManager.getFinalResultsSummary());
        System.out.println(OptionsManager.get("thanks_for_playing"));
        scanner.close();
    }

    /**
     * Sets up the theme for the game.
     */
    private void setupTheme() {
        System.out.println(OptionsManager.get("select_theme"));
        System.out.println("1. " + OptionsManager.get("theme_standard"));
        System.out.println("2. " + OptionsManager.get("theme_bathroom"));

        String themeChoice = scanner.nextLine();

        if (themeChoice.equals("2")) {
            gameTheme = GameTheme.BATHROOM;
            System.out.println(OptionsManager.get("theme_bathroom_selected"));
        } else {
            gameTheme = GameTheme.STANDARD;
            System.out.println(OptionsManager.get("theme_standard_selected"));
        }
    }

    /**
     * Sets up the game by getting player information and creating the game.
     */
    private void setupGame() {
        // Reset game statistics
        scoreManager.resetScores();
        consecutiveWins = 0;
        consecutiveLosses = 0;
        roundsPlayed = 0;

        System.out.println(OptionsManager.get("welcome"));

        // Set up theme first
        setupTheme();

        System.out.println(OptionsManager.get("select_players"));

        String input = scanner.nextLine();

        if (input.equals("1")) {
            setupOnePlayerGame();
            gameMode = GameMode.SINGLE_PLAYER;
        } else if (input.equals("2")) {
            setupTwoPlayerGame();
            gameMode = GameMode.MULTIPLAYER;
        } else {
            System.out.println(OptionsManager.get("invalid_input"));
            setupDefaultOnePlayerGame();
            gameMode = GameMode.SINGLE_PLAYER;
        }

        // Register players with the score manager
        scoreManager.registerPlayer(player1);
        scoreManager.registerPlayer(player2);

        // Create the game with the selected theme
        game = new MoveSelectionGame(player1, player2, gameTheme);
    }

    /**
     * Sets up a one-player game against the computer.
     */
    private void setupOnePlayerGame() {
        System.out.println(OptionsManager.get("one_player_selected"));
        String playerName = scanner.nextLine();
        player1 = new Player(playerName, gameTheme); // Pass the theme
        player2 = new Player(true, gameTheme); // Pass the theme to computer player

        setupDifficulty();
    }

    /**
     * Sets up a default one-player game with default values.
     */
    private void setupDefaultOnePlayerGame() {
        player1 = new Player("Player", gameTheme); // Pass the theme
        player2 = new Player(true, gameTheme); // Pass the theme to computer player
        player2.setComputerDifficulty(ComputerPlayerStrategy.DifficultyLevel.EASY);
        System.out.println(OptionsManager.get("difficulty_easy_selected"));
    }

    /**
     * Sets up the difficulty level for the computer player.
     */
    private void setupDifficulty() {
        System.out.println(OptionsManager.get("select_difficulty"));
        // Use the first move (index 0) which is Rock in standard game or Poop in bathroom theme
        System.out.println("1. " + OptionsManager.get("difficulty_man") + gameTheme.getMoveName(0) + ")");
        System.out.println("2. " + OptionsManager.get("difficulty_easy"));
        System.out.println("3. " + OptionsManager.get("difficulty_medium"));
        System.out.println("4. " + OptionsManager.get("difficulty_hard"));

        String difficultyChoice = scanner.nextLine();

        switch (difficultyChoice) {
            case "1":
                currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.MAN;
                player2.setComputerDifficulty(currentDifficulty);
                System.out.println(OptionsManager.get("difficulty_man_selected") + gameTheme.getMoveName(0) + ".");
                break;
            case "3":
                currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.MEDIUM;
                player2.setComputerDifficulty(currentDifficulty);
                System.out.println(OptionsManager.get("difficulty_medium_selected"));
                break;
            case "4":
                currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.HARD;
                player2.setComputerDifficulty(currentDifficulty);
                System.out.println(OptionsManager.get("difficulty_hard_selected"));
                break;
            default:
                currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.EASY;
                player2.setComputerDifficulty(currentDifficulty);
                System.out.println(OptionsManager.get("difficulty_easy_selected"));
        }
    }

    /**
     * Sets up a two-player game.
     */
    private void setupTwoPlayerGame() {
        System.out.println(OptionsManager.get("two_player_selected"));

        System.out.println(OptionsManager.get("enter_name_player1"));
        String player1Name = scanner.nextLine();
        player1 = new Player(player1Name, gameTheme); // Pass the theme

        System.out.println(OptionsManager.get("enter_name_player2"));
        String player2Name = scanner.nextLine();
        player2 = new Player(player2Name, gameTheme); // Pass the theme
    }

    /**
     * Plays a single round of the game.
     *
     * @return true if the game should continue, false if the player quit
     */
    private boolean playRound() {
        // Display round number
        System.out.println("\n===== " + OptionsManager.get("round") + " " + (roundsPlayed + 1) + " =====");

        // Check if player has lost 5 times in a row and show secret message
        if (consecutiveLosses >= 5 && gameMode == GameMode.SINGLE_PLAYER) {
            System.out.println("\n" + OptionsManager.get("secret_hint") + "\n");
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
        System.out.println("\n" + player1.getName() + OptionsManager.get("choose_move"));

        String[] moveOptions = game.getMoveOptions();
        for (int i = 0; i < moveOptions.length; i++) {
            System.out.println((i + 1) + ". " + moveOptions[i]);
        }

        String moveChoice = scanner.nextLine();

        if (moveChoice.equalsIgnoreCase("q")) {
            System.out.println(OptionsManager.get("quit_game"));
            return false;
        }

        if (moveChoice.equalsIgnoreCase("u")) {
            handleUndo();
            // After undo, prompt for a move again
            return playComputerRound();
        }

        try {
            int choice = Integer.parseInt(moveChoice);
            if (choice >= 1 && choice <= game.getNumberOfMoveOptions()) {
                // Convert to zero-based index for the game
                int moveIndex = choice - 1;

                // Use the player select move method with the appropriate index
                game.playerSelectsMove(player1, moveIndex);
                System.out.println(OptionsManager.get("you_chose") + player1.getCurrentMove().getName());
            } else {
                System.out.println(OptionsManager.get("invalid_choice"));
                return playComputerRound();
            }
        } catch (NumberFormatException e) {
            System.out.println(OptionsManager.get("invalid_choice_default"));
            game.playerOneSelectsFirstOption();
            System.out.println(OptionsManager.get("you_chose") + player1.getCurrentMove().getName());
        }

        // Computer makes its move
        player2.makeComputerMove(player1.getCurrentMove());
        System.out.println(OptionsManager.get("computer_chose") + player2.getCurrentMove().getName());

        return true;
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
        System.out.println("\n" + player.getName() + OptionsManager.get("choose_move"));

        String[] moveOptions = game.getMoveOptions();
        for (int i = 0; i < moveOptions.length; i++) {
            System.out.println((i + 1) + ". " + moveOptions[i]);
        }

        String moveChoice = scanner.nextLine();

        if (moveChoice.equalsIgnoreCase("q")) {
            System.out.println(player.getName() + OptionsManager.get("player_quit_game"));
            return false;
        }

        if (moveChoice.equalsIgnoreCase("u")) {
            handleUndo();
            // After undo, prompt for a move again
            return getPlayerMove(player);
        }

        try {
            int choice = Integer.parseInt(moveChoice);
            if (choice >= 1 && choice <= game.getNumberOfMoveOptions()) {
                // Convert to zero-based index
                int moveIndex = choice - 1;

                // Use the player select move method with the appropriate player and index
                game.playerSelectsMove(player, moveIndex);
                System.out.println(player.getName() + OptionsManager.get("player_chose") + player.getCurrentMove().getName());
                return true;
            } else {
                System.out.println(OptionsManager.get("invalid_choice"));
                return getPlayerMove(player);
            }
        } catch (NumberFormatException e) {
            System.out.println(OptionsManager.get("invalid_choice_number"));
            return getPlayerMove(player);
        }
    }

    /**
     * Handles the undo operation.
     */
    private void handleUndo() {
        game.undoLastMove();
        System.out.println(OptionsManager.get("undo_move"));

        // If we're in a round where scores were already updated, we need to adjust them
        if (roundsPlayed > 0) {
            // This is a simplified approach - in a real implementation,
            // you might need more sophisticated score tracking for proper undo
            System.out.println(OptionsManager.get("undo_note"));
        }

        // If player has been losing and uses undo, add a little encouragement
        if (consecutiveLosses >= 5) {
            System.out.println(OptionsManager.get("undo_secret"));
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
            System.out.println("🎉 " + player1.getName() + OptionsManager.get("player_wins"));
        }
        // Check if player2 won
        else if (result.startsWith(player2.getName() + " wins")) {
            if (gameMode == GameMode.SINGLE_PLAYER) {
                // If it is a computer game, then the player is the
                // computer and the message should be different
                System.out.println(OptionsManager.get("computer_wins"));
            } else {
                // If it is a two player game, then the message should
                // be the same as if player1 won
                System.out.println("🎉 " + player2.getName() + OptionsManager.get("player_wins"));
            }
        }
        // Check if it is a draw
        else if (result.startsWith("It's a draw")) {
            System.out.println(OptionsManager.get("draw"));
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
     * Offers to increase the difficulty after 5 consecutive wins.
     */
    private void offerDifficultyIncrease() {
        System.out.println(OptionsManager.get("win_streak"));
        System.out.println(OptionsManager.get("increase_difficulty"));

        String response = scanner.nextLine().toLowerCase();

        if (response.equals("y") || response.equals("yes")) {
            switch (currentDifficulty) {
                case MAN:
                    currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.EASY;
                    player2.setComputerDifficulty(currentDifficulty);
                    System.out.println(OptionsManager.get("difficulty_increased_easy"));
                    break;
                case EASY:
                    currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.MEDIUM;
                    player2.setComputerDifficulty(currentDifficulty);
                    System.out.println(OptionsManager.get("difficulty_increased_medium"));
                    break;
                case MEDIUM:
                    currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.HARD;
                    player2.setComputerDifficulty(currentDifficulty);
                    System.out.println(OptionsManager.get("difficulty_increased_hard"));
                    break;
                case HARD:
                    // Already at max difficulty
                    System.out.println(OptionsManager.get("max_difficulty"));
                    break;
            }
            // Reset consecutive wins after increasing difficulty
            consecutiveWins = 0;
        }
    }

    /**
     * Asks the player if they want to play another round.
     * This is only called every 5 rounds.
     *
     * @return true if the player wants to play again, false otherwise.
     */
    private boolean askToPlayAgain() {
        System.out.println(OptionsManager.get("continue_playing") + roundsPlayed + OptionsManager.get("rounds"));
        String response = scanner.nextLine().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }
}
