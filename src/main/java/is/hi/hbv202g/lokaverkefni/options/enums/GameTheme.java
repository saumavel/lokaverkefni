package is.hi.hbv202g.lokaverkefni.options.enums;
import is.hi.hbv202g.lokaverkefni.options.translation.TranslationManager;

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
                case 0 -> TranslationManager.get("rock");
                case 1 -> TranslationManager.get("paper");
                case 2 -> TranslationManager.get("scissors");
                default -> "Unknown";
            };
            case BATHROOM -> switch (index) {
                case 0 -> TranslationManager.get("poop");
                case 1 -> TranslationManager.get("toilet_paper");
                case 2 -> TranslationManager.get("pee");
                default -> "Unknown";
            };
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
                    TranslationManager.get("rock"),
                    TranslationManager.get("paper"),
                    TranslationManager.get("scissors")
            };
            case BATHROOM -> new String[]{
                    TranslationManager.get("poop"),
                    TranslationManager.get("toilet_paper"),
                    TranslationManager.get("pee")
            };
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
