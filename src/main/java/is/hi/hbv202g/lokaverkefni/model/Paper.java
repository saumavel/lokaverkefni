package is.hi.hbv202g.lokaverkefni.model;

import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;

/**
 *  Represents the paper move in the game.
 */
public class Paper implements Move {
    private final GameTheme theme;

    /**
     *  Constructor for the Paper move.
     * @param theme The game theme
     */
    public Paper(GameTheme theme) {
        this.theme = theme;
    }

    /**
     *  Compares the Paper move with another move.
     * @param other The other move to compare with
     * @return The result of the comparison
     */
    @Override
    public Result compareWith(Move other) {
        if (other instanceof Paper) return Result.DRAW;
        if (other instanceof Rock) return Result.WIN;
        return Result.LOSE;  // Scissors beats Paper
    }

    /**
     *  Returns the name of the Paper move.
     * @return The name of the Paper move
     */
    @Override
    public String getName() {
        // Paper is index 1
        int moveIndex = 1;
        return theme.getMoveName(moveIndex);
    }
}
