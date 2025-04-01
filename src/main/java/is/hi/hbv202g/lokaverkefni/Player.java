package is.hi.hbv202g.lokaverkefni;

public class Player {
    private final String name;
    private Move currentMove;
    private final boolean isComputer;
    private int score = 0;

    public Player(String name) {
        this.name = name;
        this.isComputer = false;
    }

    public Player(boolean isComputer) {
        this.name = "Computer";
        this.isComputer = isComputer;
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

    /**
     * Checks if the player is a computer.
     *
     * @return True if the player is a computer, false otherwise.
     */
    public boolean isComputer() {
        return isComputer;
    }

    /**
     * Gets the player's current score.
     *
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Increments the player's score by 1.
     */
    public void incrementScore() {
        score++;
    }
}
