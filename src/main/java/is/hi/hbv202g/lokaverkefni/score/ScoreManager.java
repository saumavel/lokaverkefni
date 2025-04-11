package is.hi.hbv202g.lokaverkefni.score;

import is.hi.hbv202g.lokaverkefni.game.Player;
import is.hi.hbv202g.lokaverkefni.observer.ScoreObserver;

import java.util.HashMap;
import java.util.Map;

/**
 * ScoreManager handles tracking and updating scores for all players.
 * It implements the Observer pattern to receive score update notifications.
 * Scoring system: Win = 3 points, Draw = 1 point
 */
public class ScoreManager implements ScoreObserver {
    // Map to store player scores
    private final Map<Player, Integer> playerScores = new HashMap<>();
    // Count of draws
    private int draws = 0;
    // Track total rounds played
    private int totalRoundsPlayed = 0;

    /**
     * Registers a player with the score manager.
     *
     * @param player The player to register.
     */
    public void registerPlayer(Player player) {
        playerScores.put(player, 0);
        player.registerObserver(this);
    }

    /**
     * Unregisters a player from the score manager.
     *
     * @param player The player to unregister.
     */
    public void unregisterPlayer(Player player) {
        playerScores.remove(player);
        player.removeObserver(this);
    }

    /**
     * Gets the score for a specific player.
     *
     * @param player The player whose score to retrieve.
     * @return The player's score, or 0 if the player is not registered.
     */
    public int getScore(Player player) {
        return playerScores.getOrDefault(player, 0);
    }

    /**
     * Gets the number of draws.
     *
     * @return The number of draws.
     */
    public int getDraws() {
        return draws;
    }

    /**
     * Gets the total number of rounds played.
     *
     * @return The total number of rounds played.
     */
    public int getTotalRoundsPlayed() {
        return totalRoundsPlayed;
    }

    /**
     * Records a draw and awards 1 point to each player.
     */
    public void recordDraw() {
        draws++;

        // Award 1 point to each player for a draw
        for (Player player : playerScores.keySet()) {
            int currentScore = playerScores.get(player);
            playerScores.put(player, currentScore + 1);
        }
    }

    /**
     * Records a round played without affecting scores.
     * Used for tracking total rounds when needed.
     */
    public void recordRoundPlayed() {
        totalRoundsPlayed++;
    }

    /**
     * Updates the score for a player when notified of a win.
     * Awards 3 points for a win.
     *
     * @param player The player who won.
     */
    @Override
    public void updateScore(Player player) {
        int currentScore = playerScores.getOrDefault(player, 0);
        playerScores.put(player, currentScore + 3); // 3 points for a win
        // Don't increment totalRoundsPlayed here as it should be done once per round
    }

    /**
     * Determines the player with the highest score.
     *
     * @return The player with the highest score, or null if there are no player's or it's a tie.
     */
    public Player getWinningPlayer() {
        if (playerScores.isEmpty()) {
            return null;
        }
        Player winningPlayer = null;
        int highestScore = -1;
        boolean isTie = false;
        for (Map.Entry<Player, Integer> entry : playerScores.entrySet()) {
            Player player = entry.getKey();
            int score = entry.getValue();
            if (score > highestScore) {
                winningPlayer = player;
                highestScore = score;
                isTie = false;
            } else if (score == highestScore) {
                isTie = true;
            }
        }
        return isTie ? null : winningPlayer;
    }

    /**
     * Checks if the game is tied (multiple players with the same highest score).
     *
     * @return True if the game is tied, false otherwise.
     */
    public boolean isTied() {
        if (playerScores.isEmpty()) {
            return false;
        }
        int highestScore = -1;
        int playersWithHighestScore = 0;
        for (int score : playerScores.values()) {
            if (score > highestScore) {
                highestScore = score;
                playersWithHighestScore = 1;
            } else if (score == highestScore) {
                playersWithHighestScore++;
            }
        }
        return playersWithHighestScore > 1;
    }

    /**
     * Resets all scores to zero.
     */
    public void resetScores() {
        for (Player player : playerScores.keySet()) {
            playerScores.put(player, 0);
        }
        draws = 0;
        totalRoundsPlayed = 0;
    }

    /**
     * Displays the current scores for all players.
     *
     * @return A formatted string with all scores.
     */
    public String getScoreSummary() {
        StringBuilder summary = new StringBuilder("\n===== CURRENT SCORE =====\n");
        for (Map.Entry<Player, Integer> entry : playerScores.entrySet()) {
            Player player = entry.getKey();
            int score = entry.getValue();
            summary.append(player.getName()).append(": ").append(score).append("\n");
        }
        summary.append("Draws: ").append(draws).append("\n");
        summary.append("Rounds played: ").append(totalRoundsPlayed).append("\n");
        summary.append("=========================");
        return summary.toString();
    }

    /**
     * Gets a detailed final results summary.
     *
     * @return A formatted string with final results.
     */
    public String getFinalResultsSummary() {
        StringBuilder summary = new StringBuilder("\n======= GAME OVER =======\n");
        summary.append("Final Score after ").append(totalRoundsPlayed).append(" rounds:\n");
        for (Map.Entry<Player, Integer> entry : playerScores.entrySet()) {
            Player player = entry.getKey();
            int score = entry.getValue();
            summary.append(player.getName()).append(": ").append(score).append("\n");
        }
        summary.append("Draws: ").append(draws).append("\n\n");
        // Determine overall winner
        Player winner = getWinningPlayer();
        if (winner != null) {
            summary.append("Overall Winner: ").append(winner.getName()).append("! 🏆\n");
        } else if (isTied()) {
            summary.append("The game ended in a tie! 🤝\n");
        } else {
            summary.append("No winner determined.\n");
        }
        summary.append("=========================");
        return summary.toString();
    }
}
