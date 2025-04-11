package is.hi.hbv202g.lokaverkefni.game;

import is.hi.hbv202g.lokaverkefni.command.Command;
import is.hi.hbv202g.lokaverkefni.command.GameInvoker;
import is.hi.hbv202g.lokaverkefni.command.PlayMoveCommand;
import is.hi.hbv202g.lokaverkefni.model.Move;
import is.hi.hbv202g.lokaverkefni.model.MoveFactory;
import is.hi.hbv202g.lokaverkefni.model.Result;
import is.hi.hbv202g.lokaverkefni.options.GameTheme;

public class MoveSelectionGame {
    private final Player player1;
    private final Player player2;
    private final GameInvoker invoker;
    private final GameTheme theme;
    private final MoveFactory moveFactory;

    public MoveSelectionGame(Player player1, Player player2, GameTheme theme) {
        this.player1 = player1;
        this.player2 = player2;
        this.theme = theme;
        this.invoker = new GameInvoker();
        this.moveFactory = new MoveFactory(theme);
    }


    /**
     * Generic method for a player to select a move by index.
     *
     * @param player The player making the move
     * @param moveIndex The index of the move (0 for rock/first option, 1 for paper/second option, etc.)
     */
    public void playerSelectsMove(Player player, int moveIndex) {
        Move move = moveFactory.createMove(moveIndex);
        Command command = new PlayMoveCommand(player, move);
        invoker.executeCommand(command);
    }

    /**
     * Gets the available move options for the current theme
     *
     * @return Array of move names
     */
    public String[] getMoveOptions() {
        return theme.getMoveNames();
    }



    /**
     * Determines the winner of the game.
     * @return A string indicating the winner. If both players have not made a move, a message is returned
     * indicating that both players must make a move.
     */
    public String determineWinner() {
        if (player1.getCurrentMove() == null || player2.getCurrentMove() == null) {
            return "Both players must make a move!";
        }

        // Compare the moves of the two players
        Result result = player1.getCurrentMove().compareWith(player2.getCurrentMove());

        // Determine the winner based on the result
        return switch (result) {
            case WIN -> {
                yield player1.getName() + " wins! " + player1.getCurrentMove().getName() +
                        " beats " + player2.getCurrentMove().getName();
            }
            case LOSE -> {
                yield player2.getName() + " wins! " + player2.getCurrentMove().getName() +
                        " beats " + player1.getCurrentMove().getName();
            }
            case DRAW -> {
                yield "It's a draw! Both players chose " + player1.getCurrentMove().getName();
            }
        };
    }

    /**
     * Undoes the last move made by either player.
     */
    public void undoLastMove() {
        // Undo the last command
        invoker.undoLastCommand();
    }
    public int getNumberOfMoveOptions() {
        // Get the number of moves from the game theme
        return theme.getNumberOfMoves();
    }
}
