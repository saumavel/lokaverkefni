package is.hi.hbv202g.lokaverkefni;

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
        System.out.println("Thanks for playing!");
        scanner.close();
    }

    /**
     * Sets up the theme for the game.
     */
    private void setupTheme() {
        System.out.println("Select game theme:");
        System.out.println("1. Standard (Rock, Paper, Scissors)");
        System.out.println("2. Bathroom (Poop, Toilet Paper, Pee)");

        String themeChoice = scanner.nextLine();

        if (themeChoice.equals("2")) {
            gameTheme = GameTheme.BATHROOM;
            System.out.println("Bathroom theme selected!");
        } else {
            gameTheme = GameTheme.STANDARD;
            System.out.println("Standard theme selected.");
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

        System.out.println("Welcome to the Game!");

        // Set up theme first
        setupTheme();

        System.out.println("Write 1 for a one player game or 2 for a two-player game");

        String input = scanner.nextLine();

        if (input.equals("1")) {
            setupOnePlayerGame();
            gameMode = GameMode.SINGLE_PLAYER;
        } else if (input.equals("2")) {
            setupTwoPlayerGame();
            gameMode = GameMode.MULTIPLAYER;
        } else {
            System.out.println("Invalid input. Defaulting to one player game.");
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
        System.out.println("You have selected one player game.\nWhat is your name?");
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
        System.out.println("Easy difficulty selected by default.");
    }

    /**
     * Sets up the difficulty level for the computer player.
     */
    private void setupDifficulty() {
        System.out.println("Select difficulty level:");
        // Use the first move (index 0) which is Rock in standard game or Poop in bathroom theme
        System.out.println("1. Man (Always plays " + gameTheme.getMoveName(0) + ")");
        System.out.println("2. Easy");
        System.out.println("3. Medium");
        System.out.println("4. Hard");

        String difficultyChoice = scanner.nextLine();

        switch (difficultyChoice) {
            case "1":
                currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.MAN;
                player2.setComputerDifficulty(currentDifficulty);
                System.out.println("Man difficulty selected. Your opponent will always play " + gameTheme.getMoveName(0) + ".");
                break;
            case "3":
                currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.MEDIUM;
                player2.setComputerDifficulty(currentDifficulty);
                System.out.println("Medium difficulty selected.");
                break;
            case "4":
                currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.HARD;
                player2.setComputerDifficulty(currentDifficulty);
                System.out.println("Hard difficulty selected.");
                break;
            default:
                currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.EASY;
                player2.setComputerDifficulty(currentDifficulty);
                System.out.println("Easy difficulty selected.");
        }
    }

    /**
     * Sets up a two-player game.
     */
    private void setupTwoPlayerGame() {
        System.out.println("You have selected two player game.");

        System.out.println("Enter name for Player 1:");
        String player1Name = scanner.nextLine();
        player1 = new Player(player1Name, gameTheme); // Pass the theme

        System.out.println("Enter name for Player 2:");
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
        System.out.println("\n===== ROUND " + (roundsPlayed + 1) + " =====");

        // Check if player has lost 5 times in a row and show secret message
        if (consecutiveLosses >= 5 &&  gameMode == GameMode.SINGLE_PLAYER) {
            System.out.println("\n🤫 Hate losing? Press 'u' and see what happens. It will be our little secret. 🤫\n");
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
        System.out.println("\n" + player1.getName() + ", choose your move:");

        String[] moveOptions = game.getMoveOptions();
        for (int i = 0; i < moveOptions.length; i++) {
            System.out.println((i + 1) + ". " + moveOptions[i]);
        }

        String moveChoice = scanner.nextLine();

        if (moveChoice.equalsIgnoreCase("q")) {
            System.out.println("You chose to quit the game.");
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
                System.out.println("You chose " + player1.getCurrentMove().getName());
            } else {
                System.out.println("Invalid choice. Please select a valid option.");
                return playComputerRound();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Defaulting to first option.");
            game.playerOneSelectsFirstOption();
            System.out.println("You chose " + player1.getCurrentMove().getName());
        }

        // Computer makes its move
        player2.makeComputerMove(player1.getCurrentMove());
        System.out.println("Computer chose " + player2.getCurrentMove().getName());

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
        System.out.println("\n" + player.getName() + ", choose your move:");

        String[] moveOptions = game.getMoveOptions();
        for (int i = 0; i < moveOptions.length; i++) {
            System.out.println((i + 1) + ". " + moveOptions[i]);
        }

        String moveChoice = scanner.nextLine();

        if (moveChoice.equalsIgnoreCase("q")) {
            System.out.println(player.getName() + " chose to quit the game.");
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
                System.out.println(player.getName() + " chose " + player.getCurrentMove().getName());
                return true;
            } else {
                System.out.println("Invalid choice. Please select a valid option.");
                return getPlayerMove(player);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Please enter a number.");
            return getPlayerMove(player);
        }
    }

    /**
     * Handles the undo operation.
     */
    private void handleUndo() {
        game.undoLastMove();
        System.out.println("Last move undone.");

        // If we're in a round where scores were already updated, we need to adjust them
        if (roundsPlayed > 0) {
            // This is a simplified approach - in a real implementation,
            // you might need more sophisticated score tracking for proper undo
            System.out.println("Note: Scores for the current round may need to be recalculated.");
        }

        // If player has been losing and uses undo, add a little encouragement
        if (consecutiveLosses >= 5) {
            System.out.println("😉 A clean game is not always the best... It will be our little secret. 😉");
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
            System.out.println("🎉 " + player1.getName() + " WINS THIS ROUND! 🎉");
        }
        // Check if player2 won
        else if (result.startsWith(player2.getName() + " wins")) {
            if (gameMode == GameMode.SINGLE_PLAYER) {
                // If it is a computer game, then the player is the
                // computer and the message should be different
                System.out.println("💻 COMPUTER WINS THIS ROUND! 💻");
            } else {
                // If it is a two player game, then the message should
                // be the same as if player1 won
                System.out.println("🎉 " + player2.getName() + " WINS THIS ROUND! 🎉");
            }
        }
        // Check if it is a draw
        else if (result.startsWith("It's a draw")) {
            System.out.println("🤝 IT'S A DRAW! 🤝");
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
        System.out.println("Impressive! You've won 5 games in a row!");
        System.out.println("Would you like to increase the difficulty? (y/n)");

        String response = scanner.nextLine().toLowerCase();

        if (response.equals("y") || response.equals("yes")) {
            switch (currentDifficulty) {
                case MAN:
                    currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.EASY;
                    player2.setComputerDifficulty(currentDifficulty);
                    System.out.println("Difficulty increased to Easy.");
                    break;
                case EASY:
                    currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.MEDIUM;
                    player2.setComputerDifficulty(currentDifficulty);
                    System.out.println("Difficulty increased to Medium.");
                    break;
                case MEDIUM:
                    currentDifficulty = ComputerPlayerStrategy.DifficultyLevel.HARD;
                    player2.setComputerDifficulty(currentDifficulty);
                    System.out.println("Difficulty increased to Hard.");
                    break;
                case HARD:
                    // Already at max difficulty
                    System.out.println("You're already at the highest difficulty!");
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
        System.out.println("\nYou've played " + roundsPlayed + " rounds. Do you want to continue playing? (y/n)");
        String response = scanner.nextLine().toLowerCase();
        return response.equals("y") || response.equals("yes");
    }
}
