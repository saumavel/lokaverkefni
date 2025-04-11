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
        switch (this) {
            case STANDARD:
                return switch (index) {
                    case 0 -> LanguageManager.get("rock");
                    case 1 -> LanguageManager.get("paper");
                    case 2 -> LanguageManager.get("scissors");
                    default -> "Unknown";
                };
            case BATHROOM:
                switch (index) {
                    case 0: return LanguageManager.get("poop");
                    case 1: return LanguageManager.get("toilet_paper");
                    case 2: return LanguageManager.get("pee");
                    default: return "Unknown";
                }
            default:
                return "Unknown";
        }
    }

    /**
     * Gets all move names for the current theme.
     *
     * @return Array of move names
     */
    public String[] getMoveNames() {
        return switch (this) {
            case STANDARD -> new String[]{
                    LanguageManager.get("rock"),
                    LanguageManager.get("paper"),
                    LanguageManager.get("scissors")
            };
            case BATHROOM -> new String[]{
                    LanguageManager.get("poop"),
                    LanguageManager.get("toilet_paper"),
                    LanguageManager.get("pee")
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
