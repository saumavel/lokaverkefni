package is.hi.hbv202g.lokaverkefni.game;

import is.hi.hbv202g.lokaverkefni.model.Move;
import is.hi.hbv202g.lokaverkefni.observer.ScoreObserver;
import is.hi.hbv202g.lokaverkefni.observer.ScoreSubject;
import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import is.hi.hbv202g.lokaverkefni.strategy.ComputerPlayerStrategy;

import java.util.ArrayList;
import java.util.List;

public class Player implements ScoreSubject {
    private final String name;
    private Move currentMove;
    private final boolean isComputer;
    private int score = 0;
    private ComputerPlayerStrategy computerStrategy;
    private final GameTheme theme; // Added theme field

    // List of observers for the score
    private final List<ScoreObserver> observers = new ArrayList<>();

     /**
     * Creates a new player with a theme.
     *
     * @param name The name of the player.
     * @param theme The game theme to use.
     */
    public Player(String name, GameTheme theme) {
        this.name = name;
        this.isComputer = false;
        this.theme = theme;
    }

    /**
     * Creates a new player.
     *
     * @param isComputer Whether this player is a computer player.
     * @param theme The game theme to use for moves.
     */
    public Player(boolean isComputer, GameTheme theme) {
        this.theme = theme;
        this.isComputer = isComputer;

        if (isComputer) {
            this.name = "Computer";
            this.computerStrategy = new ComputerPlayerStrategy(theme);
        } else {
            this.name = "Player";
        }
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
     * Increments the player's score by 1.
     * Package-private so it can be accessed by ScoreKeeper.
     */
    public void incrementScoreInternal() {
        score++;
    }

    /**
     * Wins a round and notifies observers to update the score.
     */
    public void winRound() {
        notifyObservers(this);
    }

    /**
     * Sets the computer difficulty level.
     *
     * @param level The difficulty level to set.
     */
    public void setComputerDifficulty(ComputerPlayerStrategy.DifficultyLevel level) {
        if (isComputer && computerStrategy != null) {
            computerStrategy.setDifficultyLevel(level);
        }
    }

    /**
     * Makes a computer move based on the opponent's last move.
     *
     * @param opponentLastMove The opponent's last move.
     */
    public void makeComputerMove(Move opponentLastMove) {
        if (isComputer && computerStrategy != null) {
            this.currentMove = computerStrategy.selectMove(opponentLastMove);
        }
    }

    // Observer Pattern methods

    @Override
    public void registerObserver(ScoreObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ScoreObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Player player) {
        for (ScoreObserver observer : observers) {
            observer.updateScore(player);
        }
    }
}
