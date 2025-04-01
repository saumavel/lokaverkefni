package is.hi.hbv202g.lokaverkefni;

public class PlayScissorsCommand implements Command {
    private final Player player;
    private Move previousMove;

    public PlayScissorsCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        previousMove = player.getCurrentMove();
        player.setMove(new Scissors());
    }

    @Override
    public void undo() {
        player.setMove(previousMove);
    }
}
