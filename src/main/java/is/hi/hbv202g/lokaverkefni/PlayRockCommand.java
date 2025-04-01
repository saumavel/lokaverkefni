package is.hi.hbv202g.lokaverkefni;

public class PlayRockCommand implements Command {
    private final Player player;
    private Move previousMove;

    public PlayRockCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        previousMove = player.getCurrentMove();
        player.setMove(new Rock());
    }

    @Override
    public void undo() {
        player.setMove(previousMove);
    }
}