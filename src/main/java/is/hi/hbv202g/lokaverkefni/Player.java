package is.hi.hbv202g.lokaverkefni;

public class Player {
    private String name;
    private Move currentMove;

    public Player(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current move of the player.
     *
     * @return The current move of the player.
     */
    public Move getCurrentMove() {
        return currentMove;
    }

    /**
     * Sets the current move of the player.
     *
     * @param move The new current move of the player.
     */
    public void setMove(Move move) {
        this.currentMove = move;
    }
}

