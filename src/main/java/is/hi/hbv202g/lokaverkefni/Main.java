package is.hi.hbv202g.lokaverkefni;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Write 1 for a one player game or 2 for a two-player game");

        String input = scanner.nextLine();

        Player player1;
        Player player2;

        if(input.equals("1")) {
            System.out.println("You have selected one player game.\nWhat is your name?");

            String playerName = scanner.nextLine();
            player1 = new Player(playerName);
            player2 = new Player(true);
        } else if(input.equals("2")) {
            System.out.println("You have selected two player game.");

            System.out.println("Enter name for Player 1:");
            String player1Name = scanner.nextLine();
            player1 = new Player(player1Name);

            System.out.println("Enter name for Player 2:");
            String player2Name = scanner.nextLine();
            player2 = new Player(player2Name);
        } else {
            System.out.println("Invalid input. Defaulting to one player game.");
            player1 = new Player("Player");
            player2 = new Player(true);
        }

        // Error 6: You're creating a new player2 that overwrites the previous one
        // Error 7: You're not creating the game with the player objects
        RockPaperScissorsGame game = new RockPaperScissorsGame(player1.getName(), player2.getName());

        // Error 8: You're not getting user input for moves
        if(player2.isComputer()) {
            // One player game
            System.out.println("Choose your move:");
            System.out.println("1. Rock");
            System.out.println("2. Paper");
            System.out.println("3. Scissors");

            String moveChoice = scanner.nextLine();

            switch(moveChoice) {
                case "1":
                    game.playerOneSelectsRock();
                    break;
                case "2":
                    game.playerOneSelectsPaper();
                    break;
                case "3":
                    game.playerOneSelectsScissors();
                    break;
                default:
                    System.out.println("Invalid choice. Defaulting to Rock.");
                    game.playerOneSelectsRock();
            }

            // Computer makes a random move
            int computerMove = (int)(Math.random() * 3) + 1;
            switch(computerMove) {
                case 1:
                    game.playerTwoSelectsRock();
                    System.out.println("Computer chose Rock");
                    break;
                case 2:
                    game.playerTwoSelectsPaper();
                    System.out.println("Computer chose Paper");
                    break;
                case 3:
                    game.playerTwoSelectsScissors();
                    System.out.println("Computer chose Scissors");
                    break;
            }
        } else {
            // Two player game
            System.out.println(player1.getName() + ", choose your move:");
            System.out.println("1. Rock");
            System.out.println("2. Paper");
            System.out.println("3. Scissors");

            String moveChoice1 = scanner.nextLine();

            switch(moveChoice1) {
                case "1":
                    game.playerOneSelectsRock();
                    break;
                case "2":
                    game.playerOneSelectsPaper();
                    break;
                case "3":
                    game.playerOneSelectsScissors();
                    break;
                default:
                    System.out.println("Invalid choice. Defaulting to Rock.");
                    game.playerOneSelectsRock();
            }

            System.out.println(player2.getName() + ", choose your move:");
            System.out.println("1. Rock");
            System.out.println("2. Paper");
            System.out.println("3. Scissors");

            String moveChoice2 = scanner.nextLine();

            switch(moveChoice2) {
                case "1":
                    game.playerTwoSelectsRock();
                    break;
                case "2":
                    game.playerTwoSelectsPaper();
                    break;
                case "3":
                    game.playerTwoSelectsScissors();
                    break;
                default:
                    System.out.println("Invalid choice. Defaulting to Rock.");
                    game.playerTwoSelectsRock();
            }
        }

        System.out.println(game.determineWinner());

        // Close the scanner
        scanner.close();
    }
}
