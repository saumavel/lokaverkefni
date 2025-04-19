package is.hi.hbv202g.lokaverkefni.game;

import is.hi.hbv202g.lokaverkefni.options.OptionsManager;
import is.hi.hbv202g.lokaverkefni.options.enums.GameMode;
import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import is.hi.hbv202g.lokaverkefni.options.parsing.DifficultyParser;
import is.hi.hbv202g.lokaverkefni.options.parsing.MoveNameMapper;
import is.hi.hbv202g.lokaverkefni.options.translation.TranslationManager;
import is.hi.hbv202g.lokaverkefni.score.ScoreManager;
import is.hi.hbv202g.lokaverkefni.strategy.ComputerPlayerStrategy;

import java.util.Scanner;

/**
 * Handles all user interface interactions for the game.
 */
public class GameUI {
    private final Scanner scanner;

    /**
     * Creates a new GameUI with a scanner for user input.
     */
    public GameUI() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prompts the user to select a language.
     */
    public void promptLanguageSelection() {
        TranslationManager.promptLanguageSelection(scanner);
    }

    /**
     * Sets up a game by gathering user input for game configuration.
     *
     * @return The complete game configuration.
     */
    public GameConfiguration setupGame() {
        System.out.println(TranslationManager.get("welcome"));

        GameTheme theme = selectTheme();
        GameMode mode;
        Player player1;
        Player player2;
        ComputerPlayerStrategy.DifficultyLevel difficulty = ComputerPlayerStrategy.DifficultyLevel.EASY;

        System.out.println(TranslationManager.get("select_players"));
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("1")) {
                player1 = createHumanPlayer(theme);
                player2 = new Player(true, theme);
                difficulty = selectDifficulty(theme, player2);
                mode = GameMode.SINGLE_PLAYER;
                break;
            } else if (input.equals("2")) {
                player1 = createNamedPlayer("enter_name_player1", theme);
                player2 = createNamedPlayer("enter_name_player2", theme);
                mode = GameMode.MULTIPLAYER;
                break;
            } else {
                System.out.println(TranslationManager.get("invalid_choice"));
            }
        }

        return new GameConfiguration(player1, player2, theme, mode, difficulty);
    }

    /**
     * Prompts the user to select a game theme.
     *
     * @return The selected game theme.
     */
    private GameTheme selectTheme() {
        while (true) {
            System.out.println(TranslationManager.get("select_theme"));
            System.out.println("1. " + TranslationManager.get("theme_standard"));
            System.out.println("2. " + TranslationManager.get("theme_bathroom"));

            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    System.out.println(TranslationManager.get("theme_standard_selected"));
                    return GameTheme.STANDARD;
                case "2":
                    System.out.println(TranslationManager.get("theme_bathroom_selected"));
                    return GameTheme.BATHROOM;
                default:
                    System.out.println(TranslationManager.get("invalid_choice"));
            }
        }
    }

    /**
     * Creates a human player with a name provided by user input.
     *
     * @param theme The game theme.
     * @return A new human player.
     */
    private Player createHumanPlayer(GameTheme theme) {
        System.out.println(TranslationManager.get("one_player_selected"));
        while (true) {
            String name = scanner.nextLine();
            if (!name.trim().isEmpty() && name.matches("^[a-zA-ZÁÉÍÓÚÝÞÆÖáéíóúýþæö]+$")) {
                return new Player(name, theme);
            }
            System.out.println(TranslationManager.get("invalid_name_letters"));
        }
    }

    /**
     * Creates a player with a name provided by user input.
     *
     * @param promptKey The translation key for the prompt.
     * @param theme The game theme.
     * @return A new named player.
     */
    private Player createNamedPlayer(String promptKey, GameTheme theme) {
        while (true) {
            System.out.println(TranslationManager.get(promptKey));
            String name = scanner.nextLine();
            if (!name.trim().isEmpty() && name.matches("^[a-zA-ZÁÉÍÓÚÝÞÆÖáéíóúýþæö]+$")) {
                return new Player(name, theme);
            }
            System.out.println(TranslationManager.get("invalid_name_letters"));
        }
    }

    /**
     * Prompts the user to select a difficulty level for the computer player.
     *
     * @param theme The game theme.
     * @param computerPlayer The computer player.
     * @return The selected difficulty level.
     */
    private ComputerPlayerStrategy.DifficultyLevel selectDifficulty(GameTheme theme, Player computerPlayer) {
        while (true) {
            System.out.println(TranslationManager.get("select_difficulty"));
            System.out.println("1. " + TranslationManager.get("difficulty_man") + theme.getMoveName(0) + ")");
            System.out.println("2. " + TranslationManager.get("difficulty_easy"));
            System.out.println("3. " + TranslationManager.get("difficulty_medium"));
            System.out.println("4. " + TranslationManager.get("difficulty_hard"));

            String input = scanner.nextLine();
            String parsed = DifficultyParser.parse(input);

            switch (parsed) {
                case "1":
                    computerPlayer.setComputerDifficulty(ComputerPlayerStrategy.DifficultyLevel.MAN);
                    System.out.println(TranslationManager.get("difficulty_man_selected") + theme.getMoveName(0));
                    return ComputerPlayerStrategy.DifficultyLevel.MAN;
                case "2":
                    computerPlayer.setComputerDifficulty(ComputerPlayerStrategy.DifficultyLevel.EASY);
                    System.out.println(TranslationManager.get("difficulty_easy_selected"));
                    return ComputerPlayerStrategy.DifficultyLevel.EASY;
                case "3":
                    computerPlayer.setComputerDifficulty(ComputerPlayerStrategy.DifficultyLevel.MEDIUM);
                    System.out.println(TranslationManager.get("difficulty_medium_selected"));
                    return ComputerPlayerStrategy.DifficultyLevel.MEDIUM;
                case "4":
                    computerPlayer.setComputerDifficulty(ComputerPlayerStrategy.DifficultyLevel.HARD);
                    System.out.println(TranslationManager.get("difficulty_hard_selected"));
                    return ComputerPlayerStrategy.DifficultyLevel.HARD;
                default:
                    System.out.println(TranslationManager.get("invalid_choice"));
            }
        }
    }

    /**
     * Gets a move from the user for the specified player.
     *
     * @param player The player making the move.
     * @param game The game instance.
     * @return The move index or null if the player wants to quit or undo.
     */
    public Integer getPlayerMove(Player player, MoveSelectionGame game) {
        System.out.println("\n" + player.getName() + TranslationManager.get("choose_move"));

        String[] moveOptions = game.getMoveOptions();
        for (int i = 0; i < moveOptions.length; i++) {
            System.out.println((i + 1) + ". " + moveOptions[i]);
        }

        String moveChoice = scanner.nextLine().toLowerCase();

        if (moveChoice.equalsIgnoreCase("q")) {
            System.out.println(player.getName() + TranslationManager.get("player_quit_game"));
            return null; // Signal quit
        }

        if (moveChoice.equalsIgnoreCase("u")) {
            return -1; // Signal undo
        }

        // Try to interpret the input as a move name
        Integer moveIndex = MoveNameMapper.getMoveIndex(moveChoice, game.getGameTheme());

        if (moveIndex != null) {
            // Valid move name input
            System.out.println(player.getName() + TranslationManager.get("player_chose") +
                    game.getMoveName(moveIndex));
            return moveIndex;
        } else {
            try {
                // Try to interpret as a number
                int choice = Integer.parseInt(moveChoice);
                if (choice >= 1 && choice <= game.getNumberOfMoveOptions()) {
                    // Convert to zero-based index
                    moveIndex = choice - 1;
                    System.out.println(player.getName() + TranslationManager.get("player_chose") +
                            game.getMoveName(moveIndex));
                    return moveIndex;
                } else {
                    System.out.println(TranslationManager.get("invalid_choice"));
                    return getPlayerMove(player, game); // Recursive call for invalid choice
                }
            } catch (NumberFormatException e) {
                System.out.println(TranslationManager.get("invalid_choice_number"));
                return getPlayerMove(player, game); // Recursive call for non-numeric input
            }
        }
    }

    /**
     * Handles the undo operation.
     *
     * @param consecutiveLosses The number of consecutive losses.
     */
    public void displayUndoMessage(int consecutiveLosses) {
        System.out.println(TranslationManager.get("undo_move"));
        System.out.println(TranslationManager.get("undo_note"));

        // If player has been losing and uses undo, add a little encouragement
        if (consecutiveLosses >= 5) {
            System.out.println(TranslationManager.get("undo_secret"));
        }
    }

    /**
     * Displays the result of a round.
     *
     * @param result The result string from the game.
     * @param player1 The first player.
     * @param player2 The second player.
     * @param gameMode The game mode.
     */
    public void displayRoundResult(String result, Player player1, Player player2, GameMode gameMode) {
        System.out.println("\n" + result);

        // Check if player1 won
        if (result.startsWith(player1.getName() + " wins")) {
            System.out.println("🎉 " + player1.getName() + TranslationManager.get("player_wins"));
        }
        // Check if player2 won
        else if (result.startsWith(player2.getName() + " wins")) {
            if (gameMode == GameMode.SINGLE_PLAYER) {
                // If it is a computer game, then the player is the computer
                System.out.println(TranslationManager.get("computer_wins"));
            } else {
                // If it is a two player game
                System.out.println("🎉 " + player2.getName() + TranslationManager.get("player_wins"));
            }
        }
        // Check if it is a draw
        else if (result.startsWith("It's a draw")) {
            System.out.println(TranslationManager.get("draw"));
        }
    }

    /**
     * Shows a hint for the secret undo feature after several losses.
     *
     * @param consecutiveLosses The number of consecutive losses.
     * @param gameMode The current game mode.
     */
    public void showSecretHintIfNeeded(int consecutiveLosses, GameMode gameMode) {
        if (consecutiveLosses >= 5 && gameMode == GameMode.SINGLE_PLAYER) {
            System.out.println("\n" + TranslationManager.get("secret_hint") + "\n");
        }
    }

    /**
     * Displays the computer's move.
     *
     * @param moveName The name of the move.
     */
    public void displayComputerMove(String moveName) {
        System.out.println(TranslationManager.get("computer_chose") + moveName);
    }

    /**
     * Displays the player's move.
     *
     * @param moveName The name of the move.
     */
    public void displayPlayerMove(String moveName) {
        System.out.println(TranslationManager.get("you_chose") + moveName);
    }

    /**
     * Offers the player a chance to increase difficulty after a winning streak.
     *
     * @param currentDifficulty The current difficulty level.
     * @param computerPlayer The computer player.
     * @return The new difficulty level.
     */
    public ComputerPlayerStrategy.DifficultyLevel offerDifficultyIncrease(
            ComputerPlayerStrategy.DifficultyLevel currentDifficulty,
            Player computerPlayer) {

        System.out.println(TranslationManager.get("win_streak"));
        System.out.println(TranslationManager.get("increase_difficulty"));

        String response = scanner.nextLine().toLowerCase();

        if (OptionsManager.isAffirmativeResponse(response)) {
            switch (currentDifficulty) {
                case MAN:
                    computerPlayer.setComputerDifficulty(ComputerPlayerStrategy.DifficultyLevel.EASY);
                    System.out.println(TranslationManager.get("difficulty_increased_easy"));
                    return ComputerPlayerStrategy.DifficultyLevel.EASY;
                case EASY:
                    computerPlayer.setComputerDifficulty(ComputerPlayerStrategy.DifficultyLevel.MEDIUM);
                    System.out.println(TranslationManager.get("difficulty_increased_medium"));
                    return ComputerPlayerStrategy.DifficultyLevel.MEDIUM;
                case MEDIUM:
                    computerPlayer.setComputerDifficulty(ComputerPlayerStrategy.DifficultyLevel.HARD);
                    System.out.println(TranslationManager.get("difficulty_increased_hard"));
                    return ComputerPlayerStrategy.DifficultyLevel.HARD;
                case HARD:
                    // Already at max difficulty
                    System.out.println(TranslationManager.get("max_difficulty"));
                    return ComputerPlayerStrategy.DifficultyLevel.HARD;
            }
        }
        return currentDifficulty;
    }

    /**
     * Asks the player if they want to continue playing.
     *
     * @param roundsPlayed The number of rounds played so far.
     * @return True if the player wants to continue, false otherwise.
     */
    public boolean askToPlayAgain(int roundsPlayed) {
        System.out.println(TranslationManager.get("continue_playing") + roundsPlayed +
                TranslationManager.get("rounds"));
        String response = scanner.nextLine().toLowerCase();

        return OptionsManager.isAffirmativeResponse(response);
    }

    /**
     * Displays the final game results.
     *
     * @param scoreManager The score manager containing the final scores.
     */
    public void displayFinalResults(ScoreManager scoreManager) {
        System.out.println(scoreManager.getFinalResultsSummary());
        System.out.println(TranslationManager.get("thanks_for_playing"));
    }

    /**
     * Displays the score summary.
     *
     * @param scoreManager The score manager containing the current scores.
     */
    public void displayScoreSummary(ScoreManager scoreManager) {
        System.out.println(scoreManager.getScoreSummary());
    }

    /**
     * Displays the current round number.
     *
     * @param roundNumber The current round number.
     */
    public void displayRoundNumber(int roundNumber) {
        System.out.println("\n===== " + TranslationManager.get("round") + " " + roundNumber + " =====");
    }
}