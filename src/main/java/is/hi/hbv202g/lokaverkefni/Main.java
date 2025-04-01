package is.hi.hbv202g.lokaverkefni;

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Prump");
        Player player2 = new Player("Piss");
        RockPaperScissorsGame game = new RockPaperScissorsGame(player1.getName(), player2.getName());

        game.playerOneSelectsRock();
        game.playerTwoSelectsPaper();

        System.out.println(game.determineWinner());
    }
}