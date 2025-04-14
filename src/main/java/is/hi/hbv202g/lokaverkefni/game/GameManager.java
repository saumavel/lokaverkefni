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
     * Sets up the game theme.
     */
    private void setupTheme() {
        String themeChoice;
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.println(OptionsManager.get("select_theme"));
            System.out.println("1. " + OptionsManager.get("theme_standard"));
            System.out.println("2. " + OptionsManager.get("theme_bathroom"));
            System.out.println("3. " + OptionsManager.get("rainbow"));


            themeChoice = scanner.nextLine().toLowerCase();
            if (themeChoice.equals("1")) {
                gameTheme = GameTheme.STANDARD;
                System.out.println(OptionsManager.get("theme_standard_selected"));
                isValidInput = true;
            } else if (themeChoice.equals("2")) {
                gameTheme = GameTheme.BATHROOM;
                System.out.println(OptionsManager.get("theme_bathroom_selected"));
                isValidInput = true;
            } else {
                System.out.println(OptionsManager.get("invalid_choice"));
            }
        }
    }

    /**
     * Sets up a new game.
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

        // Initialize players
        player1 = null;
        player2 = null;

        while (player1 == null || player2 == null) {
            System.out.println(OptionsManager.get("select_players"));

            boolean isValidInput = false;
            while (!isValidInput) {
                String input = scanner.nextLine();

                if (input.equals("1")) {
                    setupOnePlayerGame();
                    gameMode = GameMode.SINGLE_PLAYER;
                    isValidInput = true;
                } else if (input.equals("2")) {
                    setupTwoPlayerGame();
                    gameMode = GameMode.MULTIPLAYER;
                    isValidInput = true;
                } else {
                    System.out.println(OptionsManager.get("invalid_choice")); // Removed extra semicolon
                    System.out.println(OptionsManager.get("select_players"));
                }
            }
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
        boolean isValidInput = false;
        String playerName;
        while(!isValidInput){
            playerName = scanner.nextLine();
            if (playerName.trim().isEmpty()) {
                System.out.println(OptionsManager.get("invalid_input"));
            } else {
                isValidInput = true;
            }
            player1 = new Player(playerName, gameTheme); // Pass the theme
        }

        player2 = new Player(true, gameTheme); // Pass the theme to computer player

        setupDifficulty();
    }

    private void setupDifficulty() {
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.println(OptionsManager.get("select_difficulty"));
            System.out.println("1. " + OptionsManager.get("difficulty_man") + gameTheme.getMoveName(0) + ")");
            System.out.println("2. " + OptionsManager.get("difficulty_easy"));
            System.out.println("3. " + OptionsManager.get("difficulty_medium"));
            System.out.println("4. " + OptionsManager.get("difficulty_hard"));
            String difficultyChoice = scanner.nextLine().toLowerCase();
            String difficultyLevel = OptionsManager.getDifficultyFromInput(difficultyChoice);

            switch (difficultyLevel) {
                case "1":
                    currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.MAN;
                    player2.setComputerDifficulty(currentDifficulty);
                    System.out.println(OptionsManager.get("difficulty_man_selected") + gameTheme.getMoveName(0) + ".");
                    isValidInput = true;
                    break;
                case "2":
                    currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.EASY;
                    player2.setComputerDifficulty(currentDifficulty);
                    System.out.println(OptionsManager.get("difficulty_easy_selected"));
                    isValidInput = true;
                    break;
                case "3":
                    currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.MEDIUM;
                    player2.setComputerDifficulty(currentDifficulty);
                    System.out.println(OptionsManager.get("difficulty_medium_selected"));
                    isValidInput = true;
                    break;
                case "4":
                    currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.HARD;
                    player2.setComputerDifficulty(currentDifficulty);
                    System.out.println(OptionsManager.get("difficulty_hard_selected"));
                    isValidInput = true;
                    break;
                default:
                    System.out.println(OptionsManager.get("invalid_choice"));
                    break;
            }
        }
    }

    /**
     * Sets up a two-player game.
     */
    private void setupTwoPlayerGame() {
        System.out.println(OptionsManager.get("two_player_selected"));

        // Get player 1's name
        String player1Name = "";
        while (player1Name.trim().isEmpty()) {
            System.out.println(OptionsManager.get("enter_name_player1"));
            player1Name = scanner.nextLine().trim();
            if (player1Name.isEmpty()) {
                System.out.println(OptionsManager.get("invalid_input"));
            }
        }
        player1 = new Player(player1Name, gameTheme);

        // Get player 2's name
        String player2Name = "";
        while (player2Name.trim().isEmpty()) {
            System.out.println(OptionsManager.get("enter_name_player2"));
            player2Name = scanner.nextLine().trim();
            if (player2Name.isEmpty()) {
                System.out.println(OptionsManager.get("invalid_name"));
            }
        }
        player2 = new Player(player2Name, gameTheme);
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

        String moveChoice = scanner.nextLine().toLowerCase();

        if (moveChoice.equalsIgnoreCase("q")) {
            System.out.println(OptionsManager.get("quit_game"));
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
            System.out.println(OptionsManager.get("you_chose") + player1.getCurrentMove().getName());
        } else {
            try {
                // Try to interpret as a number
                int choice = Integer.parseInt(moveChoice);
                if (choice >= 1 && choice <= game.getNumberOfMoveOptions()) {
                    // Convert to zero-based index for the game
                    moveIndex = choice - 1;

                    // Use the player select move method with the appropriate index
                    game.playerSelectsMove(player1, moveIndex);
                    System.out.println(OptionsManager.get("you_chose") + player1.getCurrentMove().getName());
                } else {
                    System.out.println(OptionsManager.get("invalid_choice"));
                    return playComputerRound(); // Ask again instead of defaulting
                }
            } catch (NumberFormatException e) {
                // Invalid input - not a number and not a recognized move name
                System.out.println(OptionsManager.get("invalid_choice_number"));
                return playComputerRound(); // Ask again instead of defaulting
            }
        }

        // Computer makes its move
        player2.makeComputerMove(player1.getCurrentMove());
        System.out.println(OptionsManager.get("computer_chose") + player2.getCurrentMove().getName());

        return true;
    }


    private Integer getMoveIndexFromInput(String input) {
        // Use the OptionsManager method instead of local implementation
        return OptionsManager.getMoveIndexFromInput(input, gameTheme);
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

        String moveChoice = scanner.nextLine().toLowerCase();

        if (moveChoice.equalsIgnoreCase("q")) {
            System.out.println(player.getName() + OptionsManager.get("player_quit_game"));
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
            System.out.println(player.getName() + OptionsManager.get("player_chose") + player.getCurrentMove().getName());
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
     * Offer the player the option to increase the difficulty level.
     */
    private void offerDifficultyIncrease() {
        System.out.println(OptionsManager.get("win_streak"));
        System.out.println(OptionsManager.get("increase_difficulty"));

        String response = scanner.nextLine().toLowerCase();

        if (OptionsManager.isAffirmativeResponse(response)) {
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
     * Prompts the player to play another round.
     *
     * @return True if the player wants to play again, false otherwise.
     */
    private boolean askToPlayAgain() {
        System.out.println(OptionsManager.get("continue_playing") + roundsPlayed + OptionsManager.get("rounds"));
        String response = scanner.nextLine().toLowerCase();

        // Use the new OptionsManager method for yes/no responses
        return OptionsManager.isAffirmativeResponse(response);
    }
}
