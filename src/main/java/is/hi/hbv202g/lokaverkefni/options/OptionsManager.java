package is.hi.hbv202g.lokaverkefni.options;

import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import is.hi.hbv202g.lokaverkefni.options.enums.Language;
import is.hi.hbv202g.lokaverkefni.options.translation.TranslationManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages language settings and non-translated mappings like move names and user responses.
 */
public class OptionsManager {
    private static Language currentLanguage = Language.ENGLISH; // Default language

    // Maps for theme-specific move names and their indices
    private static final Map<String, Integer> standardMoveMap = new HashMap<>();
    private static final Map<String, Integer> bathroomMoveMap = new HashMap<>();

    // Maps for common responses
    private static final Map<String, Boolean> yesNoResponses = new HashMap<>();

    // Maps for difficulty levels
    private static final Map<String, String> difficultyMap = new HashMap<>();

    static {
        initializeMoveNameMappings();
        initializeYesNoResponses();
        initializeDifficultyMappings();
    }

    /**
     * Sets the current language.
     * @param language The language to set.
     */
    public static void setLanguage(Language language) {
        currentLanguage = language;
    }

    /**
     * Gets the current language.
     * @return The current language.
     */
    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

    /**
     * Gets the move index from user input.
     * @param input The user input.
     * @param theme The game theme.
     * @return The move index, or null if not found.
     */
    public static Integer getMoveIndexFromInput(String input, GameTheme theme) {
        input = input.toLowerCase().trim();
        Map<String, Integer> moveMap = (theme == GameTheme.STANDARD) ? standardMoveMap : bathroomMoveMap;

        if (moveMap.containsKey(input)) {
            return moveMap.get(input);
        }

        for (Map.Entry<String, Integer> entry : moveMap.entrySet()) {
            if (input.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        return null;
    }

    /**
     * Checks if the input is an affirmative response.
     * @param input The user input.
     * @return True if the input is an affirmative response, false otherwise.
     */
    public static boolean isAffirmativeResponse(String input) {
        input = input.toLowerCase().trim();
        Boolean response = yesNoResponses.get(input);
        return response != null && response;
    }

    /**
     *  gets the difficulty level from user input
     * @param input the user input
     * @return the difficulty level
     */
    public static String getDifficultyFromInput(String input) {
        return DifficultyUtils.parseDifficulty(input, difficultyMap);
    }

    /**
     *  Checks if the input is a bathroom theme
     * @param input the user input
     * @return true if the input is a bathroom theme
     */
    public static boolean isBathroomThemeSelected(String input) {
        input = input.toLowerCase().trim();

        if (input.equals("2")) return true;

        return input.contains("pee") || input.contains("piss") ||
                input.contains("poop") || input.contains("kúk") ||
                input.contains("toilet") || input.contains("klósett");
    }

    /**
     *  Checks if the input is a normal theme
     * @param input the user input
     * @return true if the input is a normal theme
     */
    public static boolean isNormalThemeSelected(String input) {
        input = input.toLowerCase().trim();

        if (input.equals("1")) return true;

        return input.contains("skæri") || input.contains("scissors") ||
                input.contains("blað") || input.contains("paper") ||
                input.contains("steinn") || input.contains("stone") ||
                input.contains("venjuleg") || input.contains("normal");
    }

    /**
     *  gets the move names for a theme
     * @param theme the game theme
     * @return the move names
     *
     **/
    public static String[] getMoveNamesForTheme(GameTheme theme) {
        if (theme == GameTheme.STANDARD) {
            return new String[] {
                    TranslationManager.get("rock"),
                    TranslationManager.get("paper"),
                    TranslationManager.get("scissors"),
            };
        } else if (theme == GameTheme.BATHROOM) {
            return new String[] {
                    TranslationManager.get("poop"),
                    TranslationManager.get("toilet_paper"),
                    TranslationManager.get("pee"),
            };
        } else {
            throw new UnsupportedOperationException("Unsupported theme: " + theme);
        }
    }

    private static void initializeMoveNameMappings() {
        standardMoveMap.put("rock", 0);
        standardMoveMap.put("paper", 1);
        standardMoveMap.put("scissors", 2);
        standardMoveMap.put("steinn", 0);
        standardMoveMap.put("blað", 1);
        standardMoveMap.put("skæri", 2);

        bathroomMoveMap.put("poop", 0);
        bathroomMoveMap.put("toilet paper", 1);
        bathroomMoveMap.put("toiletpaper", 1);
        bathroomMoveMap.put("pee", 2);
        bathroomMoveMap.put("kúkur", 0);
        bathroomMoveMap.put("klósettpappír", 1);
        bathroomMoveMap.put("piss", 2);
    }

    private static void initializeYesNoResponses() {
        yesNoResponses.put("y", true);
        yesNoResponses.put("yes", true);
        yesNoResponses.put("yeah", true);
        yesNoResponses.put("yep", true);
        yesNoResponses.put("j", true);
        yesNoResponses.put("já", true);
        yesNoResponses.put("jú", true);
        yesNoResponses.put("n", false);
        yesNoResponses.put("no", false);
        yesNoResponses.put("nope", false);
        yesNoResponses.put("nei", false);
    }

    private static void initializeDifficultyMappings() {
        difficultyMap.put("man", "1");
        difficultyMap.put("easy", "2");
        difficultyMap.put("medium", "3");
        difficultyMap.put("hard", "4");
        difficultyMap.put("maður", "1");
        difficultyMap.put("auðvelt", "2");
        difficultyMap.put("miðlungs", "3");
        difficultyMap.put("erfitt", "4");
    }
}

