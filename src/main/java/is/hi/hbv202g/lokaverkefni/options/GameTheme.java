package is.hi.hbv202g.lokaverkefni.options;

/**
 * Enum representing different game themes.
 */
public enum GameTheme {
    STANDARD,
    BATHROOM;

    /**
     * Gets the name of a move based on its index and the current theme.
     *
     * @param index The index of the move
     * @return The name of the move
     */
    public String getMoveName(int index) {
        return switch (this) {
            case STANDARD -> switch (index) {
                case 0 -> OptionsManager.get("rock");
                case 1 -> OptionsManager.get("paper");
                case 2 -> OptionsManager.get("scissors");
                default -> "Unknown";
            };
            case BATHROOM -> switch (index) {
                case 0 -> OptionsManager.get("poop");
                case 1 -> OptionsManager.get("toilet_paper");
                case 2 -> OptionsManager.get("pee");
                default -> "Unknown";
            };
            default -> "Unknown";
        };
    }

    /**
     * Gets all move names for the current theme.
     *
     * @return Array of move names
     */
    public String[] getMoveNames() {
        return switch (this) {
            case STANDARD -> new String[]{
                    OptionsManager.get("rock"),
                    OptionsManager.get("paper"),
                    OptionsManager.get("scissors")
            };
            case BATHROOM -> new String[]{
                    OptionsManager.get("poop"),
                    OptionsManager.get("toilet_paper"),
                    OptionsManager.get("pee")
            };
            default -> new String[]{"Unknown"};
        };
    }
    /**
     * Gets the number of moves available in this theme.
     *
     * @return The number of moves
     */
    public int getNumberOfMoves() {
        // Both themes currently have 3 moves
        return 3;
    }
}
