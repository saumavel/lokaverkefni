package is.hi.hbv202g.lokaverkefni.model;

import is.hi.hbv202g.lokaverkefni.options.GameTheme;

public class Scissors implements Move {
    private final GameTheme theme;
    private final int moveIndex = 2;  // Scissors is index 2

    public Scissors(GameTheme theme) {
        this.theme = theme;
    }

    @Override
    public Result compareWith(Move other) {
        if (other instanceof Scissors) return Result.DRAW;
        if (other instanceof Paper) return Result.WIN;
        return Result.LOSE;  // Rock beats Scissors
    }

    @Override
    public String getName() {
        return theme.getMoveName(moveIndex);
    }
}
