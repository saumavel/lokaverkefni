package is.hi.hbv202g.lokaverkefni.model;

import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;

/**
 *  Represents the Rock move in the game.
 */
public class Rock implements Move {
    private final GameTheme theme;

    /**
     *  Constructs a new Rock move.
     * @param theme The theme of the game
     */
    public Rock(GameTheme theme) {
        this.theme = theme;
    }

    /**
     *  Compares the Rock move with another move.
     * @param other The other move to compare with
     * @return The result of the comparison
     */
    @Override
    public Result compareWith(Move other) {
        if (other instanceof Rock) return Result.DRAW;
        if (other instanceof Scissors) return Result.WIN;
        return Result.LOSE;  // Paper beats Rock
    }

    /**
     *  Returns the name of the Rock move.
     * @return The name of the Rock move
     */
    @Override
    public String getName() {
        // Rock is index 0
        int moveIndex = 0;
        return theme.getMoveName(moveIndex);
    }
}
