package is.hi.hbv202g.lokaverkefni.observer;

import is.hi.hbv202g.lokaverkefni.game.Player;

/**
 *  This class implements the ScoreObserver interface and is responsible for keeping track of player scores.
 */
public class ScoreKeeper implements ScoreObserver {

    /**
     *  Updates the score for a player when notified of a win.
     *
     *  @param player The player who won.
     */
    @Override
    public void updateScore(Player player) {
        player.incrementScoreInternal();
    }
}

