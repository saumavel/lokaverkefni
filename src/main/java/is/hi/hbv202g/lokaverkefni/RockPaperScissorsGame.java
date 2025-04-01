package is.hi.hbv202g.lokaverkefni;

public class RockPaperScissorsGame {
    private final Player player1;
    private final Player player2;
    private final GameInvoker invoker;

    public RockPaperScissorsGame(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
        this.invoker = new GameInvoker();
    }

    /**
     * Allows player one to select rock.
     */
    public void playerOneSelectsRock() {
        // Create a new command that changes player one's move to rock
        // and execute it with the invoker
        invoker.executeCommand(new PlayRockCommand(player1));
    }

    /**
     * Allows player one to select paper.
     */
    public void playerOneSelectsPaper() {
        // Create a new command that changes player one's move to paper
        // and execute it with the invoker
        invoker.executeCommand(new PlayPaperCommand(player1));
    }

    /**
     * Allows player one to select scissors.
     */
    public void playerOneSelectsScissors() {
        // Create a new command that changes player one's move to scissors
        // and execute it with the invoker
        invoker.executeCommand(new PlayScissorsCommand(player1));
    }

    /**
     * Allows player two to select rock.
     */
    public void playerTwoSelectsRock() {
        // Create a new command that changes player two's move to rock
        // and execute it with the invoker
        invoker.executeCommand(new PlayRockCommand(player2));
    }

    /**
     * Allows player two to select paper.
     */
    public void playerTwoSelectsPaper() {
        invoker.executeCommand(new PlayPaperCommand(player2));
    }

    /**
     * Allows player two to select scissors.
     */
    public void playerTwoSelectsScissors() {
        invoker.executeCommand(new PlayScissorsCommand(player2));
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
                // Player 1 wins
                yield player1.getName() + " wins! " + player1.getCurrentMove().getName() +
                        " beats " + player2.getCurrentMove().getName();
            }
            case LOSE -> {
                // Player 2 wins
                yield player2.getName() + " wins! " + player2.getCurrentMove().getName() +
                        " beats " + player1.getCurrentMove().getName();
            }
            case DRAW -> {
                // It's a draw
                yield "It's a draw! Both players chose " + player1.getCurrentMove().getName();
            }
            default -> {
                // Unknown result
                yield "Unknown result";
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
}

