package is.hi.hbv202g.lokaverkefni;



public class Paper implements Move {
    /**
     * Compares the current move with another move.
     *
     * @param other the other move to compare with
     * @return the result of the comparison
     */
    @Override
    public Result compareWith(Move other) {
        // Paper beats Rock but loses to Scissors
        if (other instanceof Paper) return Result.DRAW;
        if (other instanceof Rock) return Result.WIN;
        return Result.LOSE;  // Scissors beats Paper
    }

    /**
     * Gets the name of this move.
     *
     * @return the name of this move
     */
    @Override
    public String getName() {
        return "Paper";
    }
}





