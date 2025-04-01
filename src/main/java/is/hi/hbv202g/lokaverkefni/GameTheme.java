package is.hi.hbv202g.lokaverkefni;

public enum GameTheme {
    STANDARD(new String[]{"Rock", "Paper", "Scissors"}),
    BATHROOM(new String[]{"Poop", "Toilet Paper", "Pee"});
    // You can easily add more themes with different numbers of moves
    // Example: EXTENDED(new String[]{"Rock", "Paper", "Scissors", "Lizard", "Spock"})

    private final String[] moveNames;

    GameTheme(String[] moveNames) {
        this.moveNames = moveNames;
    }

    public String[] getMoveNames() {
        return moveNames;
    }

    public int getNumberOfMoves() {
        return moveNames.length;
    }

    public String getMoveName(int index) {
        if (index >= 0 && index < moveNames.length) {
            return moveNames[index];
        }
        throw new IndexOutOfBoundsException("Invalid move index: " + index);
    }
}
