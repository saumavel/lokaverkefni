package is.hi.hbv202g.lokaverkefni.game;

import is.hi.hbv202g.lokaverkefni.options.enums.GameMode;
import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import is.hi.hbv202g.lokaverkefni.options.parsing.DifficultyParser;
import is.hi.hbv202g.lokaverkefni.options.translation.TranslationManager;
import is.hi.hbv202g.lokaverkefni.strategy.ComputerPlayerStrategy;

import java.util.Scanner;

/**
 * Handles setup flow
 */
public class GameSetup {

    /**
     * Initializes the game.
     * @return the game.
     */
    public static GameConfiguration initializeGame(Scanner scanner) {
        GameTheme theme = selectTheme(scanner);
        GameMode mode;
        Player player1;
        Player player2;
        ComputerPlayerStrategy.DifficultyLevel difficulty = ComputerPlayerStrategy.DifficultyLevel.EASY;

        System.out.println(TranslationManager.get("select_players"));
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("1")) {
                player1 = createHumanPlayer(scanner, theme);
                player2 = new Player(true, theme);
                difficulty = selectDifficulty(scanner, theme, player2);
                mode = GameMode.SINGLE_PLAYER;
                break;
            } else if (input.equals("2")) {
                player1 = createNamedPlayer(scanner, "enter_name_player1", theme);
                player2 = createNamedPlayer(scanner, "enter_name_player2", theme);
                mode = GameMode.MULTIPLAYER;
                break;
            } else {
                System.out.println(TranslationManager.get("invalid_choice"));
            }
        }

        return new GameConfiguration(player1, player2, theme, mode, difficulty);
    }

    private static GameTheme selectTheme(Scanner scanner) {
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

    private static Player createHumanPlayer(Scanner scanner, GameTheme theme) {
        System.out.println(TranslationManager.get("one_player_selected"));
        while (true) {
            String name = scanner.nextLine();
            if (!name.trim().isEmpty() && name.matches("^[a-zA-Z]+$")) {
                return new Player(name, theme);
            }
            System.out.println(TranslationManager.get("invalid_input"));
        }
    }

    private static Player createNamedPlayer(Scanner scanner, String promptKey, GameTheme theme) {
        while (true) {
            System.out.println(TranslationManager.get(promptKey));
            String name = scanner.nextLine();
            if (!name.trim().isEmpty()) {
                return new Player(name, theme);
            }
            System.out.println(TranslationManager.get("invalid_input"));
        }
    }

    private static ComputerPlayerStrategy.DifficultyLevel selectDifficulty(Scanner scanner, GameTheme theme, Player computerPlayer) {
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
}
