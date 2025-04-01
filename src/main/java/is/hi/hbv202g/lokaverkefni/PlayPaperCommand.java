package is.hi.hbv202g.lokaverkefni;

public class PlayPaperCommand implements Command {
    private final Player player;
    private Move previousMove;

    public PlayPaperCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        previousMove = player.getCurrentMove();
        player.setMove(new Paper());
    }

    @Override
    public void undo() {
        player.setMove(previousMove);
    }
}

