package is.hi.hbv202g.lokaverkefni.model;

/**
 *  Interface for moves in the game.
 */
public interface Move {
    /**
     *  Compares the move with another move.
     * @param other The other move to compare with
     * @return The result of the comparison
     */
    Result compareWith(Move other);
    /**
     *  Returns the name of the move.
     * @return The name of the move
     */
    String getName();
}