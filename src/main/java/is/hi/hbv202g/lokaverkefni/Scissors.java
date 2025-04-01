package is.hi.hbv202g.lokaverkefni;

public class Scissors implements Move {
    @Override
    public Result compareWith(Move other) {
        if (other instanceof Scissors) return Result.DRAW;
        if (other instanceof Paper) return Result.WIN;
        return Result.LOSE;  // Rock beats Scissors
    }

    @Override
    public String getName() {
        return "Scissors";
    }
}
