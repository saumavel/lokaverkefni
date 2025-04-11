package is.hi.hbv202g.lokaverkefni.observer;

import is.hi.hbv202g.lokaverkefni.game.Player;

public interface ScoreSubject {
    void registerObserver(ScoreObserver observer);
    void removeObserver(ScoreObserver observer);
    void notifyObservers(Player player);
}