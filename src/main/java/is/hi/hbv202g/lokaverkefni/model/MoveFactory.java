package is.hi.hbv202g.lokaverkefni.model;

import is.hi.hbv202g.lokaverkefni.GameTheme;

public class MoveFactory {
    private final GameTheme theme;

    public MoveFactory(GameTheme theme) {
        this.theme = theme;
    }

    /**
     * Creates a move based on the index.
     *
     * @param index The index of the move to create
     * @return A new Move instance
     * @throws IllegalArgumentException if the index is invalid
     */
    public Move createMove(int index) {
        if (index < 0 || index >= theme.getNumberOfMoves()) {
            throw new IllegalArgumentException("Invalid move index: " + index);
        }

        // For the standard Rock-Paper-Scissors game
        if (theme.getNumberOfMoves() == 3) {
            return switch (index) {
                case 0 -> new Rock(theme);
                case 1 -> new Paper(theme);
                case 2 -> new Scissors(theme);
                default -> throw new IllegalArgumentException("Unexpected index: " + index);
            };
        }

        // For games with different numbers of moves, you would need to implement
        // appropriate logic here. This is just a placeholder for future expansion.
        throw new UnsupportedOperationException("Games with " + theme.getNumberOfMoves() +
                " moves are not yet implemented");
    }
}

