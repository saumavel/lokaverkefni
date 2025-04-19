package is.hi.hbv202g.lokaverkefni.command;

import is.hi.hbv202g.lokaverkefni.game.Player;
import is.hi.hbv202g.lokaverkefni.model.Move;

/**
 *  A command to play a move in a game of Rock-Paper-Scissors.
 */
public class PlayMoveCommand implements Command {
    private final Player player;
    private final Move newMove;
    private Move previousMove;

    /**
     *  Creates a new PlayMoveCommand.
     * @param player player
     * @param newMove move to be played
     */
    public PlayMoveCommand(Player player, Move newMove) {
        this.player = player;
        this.newMove = newMove;
    }

    /**
     *  Executes the command to play a move in a game of Rock-Paper-Scissors.
     */
    @Override
    public void execute() {
        previousMove = player.getCurrentMove();
        player.setMove(newMove);
    }

    /**
     *  Undoes the command to play a move in a game of Rock-Paper-Scissors.
     */
    @Override
    public void undo() {
        player.setMove(previousMove);
    }
}
