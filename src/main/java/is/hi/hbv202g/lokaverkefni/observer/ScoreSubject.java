package is.hi.hbv202g.lokaverkefni.observer;

import is.hi.hbv202g.lokaverkefni.game.Player;

/**
 *  Interface for classes that can be observed by ScoreObserver.
 */
public interface ScoreSubject {
    /**
     *  Registers a ScoreObserver to be notified when a player's score changes.
     * @param observer The ScoreObserver to register.
     */
    void registerObserver(ScoreObserver observer);
    /**
     *  Removes a ScoreObserver from the list of observers.
     * @param observer The ScoreObserver to remove.
     */
    void removeObserver(ScoreObserver observer);
    /**
     *  Notifies all registered ScoreObservers of a player's score change.
     * @param player The player whose score has changed.
     */
    void notifyObservers(Player player);
}