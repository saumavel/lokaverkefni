package is.hi.hbv202g.lokaverkefni.model;

import is.hi.hbv202g.lokaverkefni.options.GameTheme;

public class Rock implements Move {
    private final GameTheme theme;

    public Rock(GameTheme theme) {
        this.theme = theme;
    }

    @Override
    public Result compareWith(Move other) {
        if (other instanceof Rock) return Result.DRAW;
        if (other instanceof Scissors) return Result.WIN;
        return Result.LOSE;  // Paper beats Rock
    }

    @Override
    public String getName() {
        // Rock is index 0
        int moveIndex = 0;
        return theme.getMoveName(moveIndex);
    }
}
