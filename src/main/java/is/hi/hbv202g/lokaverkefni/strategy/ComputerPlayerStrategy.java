/****************************************************************
 * ComputerPlayerStrategy.java
 *
 *
 *
 ****************************************************************/

package is.hi.hbv202g.lokaverkefni.strategy;

import is.hi.hbv202g.lokaverkefni.GameTheme;
import is.hi.hbv202g.lokaverkefni.model.Move;
import is.hi.hbv202g.lokaverkefni.model.Paper;
import is.hi.hbv202g.lokaverkefni.model.Rock;
import is.hi.hbv202g.lokaverkefni.model.Scissors;

import java.util.Random;

public class ComputerPlayerStrategy {
    private final Random random = new Random();
    private DifficultyLevel difficultyLevel;
    private final GameTheme theme;

    /**
     * Difficulty levels for the computer player.
     */
    public enum DifficultyLevel {
        MAN,        // Always plays Rock
        EASY,       // Completely random
        MEDIUM,     // Some basic strategy
        HARD        // Advanced strategy
    }

    /**
     * Creates a new computer player strategy with the default difficulty (EASY).
     *
     * @param theme The game theme to use for move creation
     */
    public ComputerPlayerStrategy(GameTheme theme) {
        this.difficultyLevel = DifficultyLevel.EASY;
        this.theme = theme;
    }

    /**
     * Creates a new computer player strategy with the specified difficulty.
     *
     * @param difficultyLevel The difficulty level for the computer player.
     * @param theme The game theme to use for move creation
     */
    public ComputerPlayerStrategy(DifficultyLevel difficultyLevel, GameTheme theme) {
        this.difficultyLevel = difficultyLevel;
        this.theme = theme;
    }

    /**
     * Gets the difficulty level.
     *
     * @return The current difficulty level.
     */
    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * Sets the difficulty level.
     *
     * @param difficultyLevel The new difficulty level.
     */
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * Determines the next move for the computer player.
     *
     * @param opponentLastMove The opponent's last move (can be null if first round).
     * @return The computer's selected move.
     */
    public Move selectMove(Move opponentLastMove) {
        return switch (difficultyLevel) {
            case MAN -> selectManMove();
            case EASY -> selectRandomMove();
            case MEDIUM -> selectMediumMove(opponentLastMove);
            case HARD -> selectHardMove(opponentLastMove);
        };
    }

    /**
     * Selects a move for the "Man" difficulty - always Rock.
     *
     * @return Rock move.
     */
    private Move selectManMove() {
        return new Rock(theme);
    }

    /**
     * Selects a completely random move.
     *
     * @return A random move.
     */
    private Move selectRandomMove() {
        int choice = random.nextInt(3); // 0, 1, or 2

        return switch (choice) {
            case 0 -> new Rock(theme);
            case 1 -> new Paper(theme);
            case 2 -> new Scissors(theme);
            default -> throw new IllegalArgumentException("Unexpected choice: " + choice);
        };
    }

    /**
     * Selects a move using a medium difficulty strategy.
     * At medium difficulty, the computer tends to counter the player's last move.
     *
     * @param opponentLastMove The opponent's last move.
     * @return The selected move.
     */
    private Move selectMediumMove(Move opponentLastMove) {
        // If no previous move or 30% of the time, choose randomly
        if (opponentLastMove == null || random.nextDouble() < 0.3) {
            return selectRandomMove();
        }

        // 70% of the time, try to counter the opponent's last move
        if (opponentLastMove instanceof Rock) {
            return new Paper(theme);  // Paper beats Rock
        } else if (opponentLastMove instanceof Paper) {
            return new Scissors(theme);  // Scissors beats Paper
        } else {
            return new Rock(theme);  // Rock beats Scissors
        }
    }

    /**
     * Selects a move using a hard difficulty strategy.
     * At hard difficulty, the computer uses pattern recognition and psychology.
     *
     * @param opponentLastMove The opponent's last move.
     * @return The selected move.
     */
    private Move selectHardMove(Move opponentLastMove) {
        // This would be a more sophisticated algorithm
        // For now, we'll implement a basic version:

        // If no previous move, choose randomly
        if (opponentLastMove == null) {
            return selectRandomMove();
        }

        // Players often switch to the move that would have beaten their last move
        // So we'll counter that anticipated move
        if (opponentLastMove instanceof Rock) {
            // Player might switch to Paper, so we use Scissors
            return new Scissors(theme);
        } else if (opponentLastMove instanceof Paper) {
            // Player might switch to Scissors, so we use Rock
            return new Rock(theme);
        } else {
            // Player might switch to Rock, so we use Paper
            return new Paper(theme);
        }
    }
}
