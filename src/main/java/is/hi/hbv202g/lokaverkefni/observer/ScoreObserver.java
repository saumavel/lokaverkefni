package is.hi.hbv202g.lokaverkefni.observer;

import is.hi.hbv202g.lokaverkefni.game.Player;

/**
 *  Interface for classes that want to be notified when a player's score changes.
 */
public interface ScoreObserver {
    /**
     *  Updates the score for a player when notified of a win.
     * @param player The player who won.
     */
    void updateScore(Player player);
}
