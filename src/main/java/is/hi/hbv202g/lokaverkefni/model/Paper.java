package is.hi.hbv202g.lokaverkefni.model;

import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;

public class Paper implements Move {
    private final GameTheme theme;

    public Paper(GameTheme theme) {
        this.theme = theme;
    }

    @Override
    public Result compareWith(Move other) {
        if (other instanceof Paper) return Result.DRAW;
        if (other instanceof Rock) return Result.WIN;
        return Result.LOSE;  // Scissors beats Paper
    }

    @Override
    public String getName() {
        // Paper is index 1
        int moveIndex = 1;
        return theme.getMoveName(moveIndex);
    }
}
