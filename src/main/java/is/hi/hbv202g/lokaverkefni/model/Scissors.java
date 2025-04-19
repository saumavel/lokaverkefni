package is.hi.hbv202g.lokaverkefni.model;

import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;

/**
 *  Represents the Scissors move in the game.
 */
public class Scissors implements Move {
    private final GameTheme theme;

    /**
     * Constructor for the Scissors move.
     * @param theme The game theme.
     */
    public Scissors(GameTheme theme) {
        this.theme = theme;
    }

    /**
     *  Compares the Scissors move with another move.
     * @param other The other move to compare with.
     * @return The result of the comparison.
     */
    @Override
    public Result compareWith(Move other) {
        if (other instanceof Scissors) return Result.DRAW;
        if (other instanceof Paper) return Result.WIN;
        return Result.LOSE;  // Rock beats Scissors
    }

    /**
     *  Returns the name of the Scissors move.
     * @return The name of the Scissors move.
     */
    @Override
    public String getName() {
        // Scissors is index 2
        int moveIndex = 2;
        return theme.getMoveName(moveIndex);
    }
}
