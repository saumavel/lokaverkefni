package is.hi.hbv202g.lokaverkefni;

public class Rock implements Move {
    @Override
    public Result compareWith(Move other) {
        if (other instanceof Rock) return Result.DRAW;
        if (other instanceof Scissors) return Result.WIN;
        return Result.LOSE;  // Paper beats Rock
    }

    @Override
    public String getName() {
        return "Rock";
    }
}