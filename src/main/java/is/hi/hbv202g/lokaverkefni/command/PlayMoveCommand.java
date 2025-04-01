package is.hi.hbv202g.lokaverkefni.command;

import is.hi.hbv202g.lokaverkefni.Player;
import is.hi.hbv202g.lokaverkefni.model.Move;

public class PlayMoveCommand implements Command {
    private final Player player;
    private final Move newMove;
    private Move previousMove;

    public PlayMoveCommand(Player player, Move newMove) {
        this.player = player;
        this.newMove = newMove;
    }

    @Override
    public void execute() {
        previousMove = player.getCurrentMove();
        player.setMove(newMove);
    }

    @Override
    public void undo() {
        player.setMove(previousMove);
    }
}
