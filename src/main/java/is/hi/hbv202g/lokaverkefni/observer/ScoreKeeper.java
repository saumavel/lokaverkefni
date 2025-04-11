package is.hi.hbv202g.lokaverkefni.observer;

import is.hi.hbv202g.lokaverkefni.game.Player;

public class ScoreKeeper implements ScoreObserver {

    @Override
    public void updateScore(Player player) {
        // Increment the player's score
        player.incrementScoreInternal();

        // You could add additional logic here, like:
        // - Logging score changes
        // - Updating a database
        // - Triggering achievements
        // - Playing sound effects
    }
}

