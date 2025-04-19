package is.hi.hbv202g.lokaverkefni.game;

/**
 * Tracks game statistics like consecutive wins and losses.
 */
public class StatisticsTracker {
    private int consecutiveWins = 0;
    private int consecutiveLosses = 0;
    private int roundsPlayed = 0;

    /**
     * Records a win, resetting the loss streak.
     */
    public void recordWin() {
        consecutiveWins++;
        consecutiveLosses = 0;
    }

    /**
     * Records a loss, resetting the win streak.
     */
    public void recordLoss() {
        consecutiveWins = 0;
        consecutiveLosses++;
    }

    /**
     * Records a draw, resetting both streaks.
     */
    public void recordDraw() {
        consecutiveWins = 0;
        consecutiveLosses = 0;
    }

    /**
     * Increments the rounds played counter.
     */
    public void incrementRoundsPlayed() {
        roundsPlayed++;
    }

    /**
     * Gets the number of consecutive wins.
     *
     * @return The number of consecutive wins
     */
    public int getConsecutiveWins() {
        return consecutiveWins;
    }

    /**
     * Gets the number of consecutive losses.
     *
     * @return The number of consecutive losses
     */
    public int getConsecutiveLosses() {
        return consecutiveLosses;
    }

    /**
     * Gets the number of rounds played.
     *
     * @return The number of rounds played
     */
    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    /**
     * Resets all statistics.
     */
    public void reset() {
        consecutiveWins = 0;
        consecutiveLosses = 0;
        roundsPlayed = 0;
    }
}