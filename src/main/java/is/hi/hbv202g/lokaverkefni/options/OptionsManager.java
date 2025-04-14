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

    public static void setLanguage(Language language) {
        currentLanguage = language;
    }

    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

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

    public static boolean isAffirmativeResponse(String input) {
        input = input.toLowerCase().trim();
        Boolean response = yesNoResponses.get(input);
        return response != null && response;
    }

    public static String getDifficultyFromInput(String input) {
        input = input.toLowerCase().trim();

        if (input.matches("[1-4]")) {
            return input;
        }

        for (Map.Entry<String, String> entry : difficultyMap.entrySet()) {
            if (input.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        return "wrong";
    }

    public static boolean isBathroomThemeSelected(String input) {
        input = input.toLowerCase().trim();

        if (input.equals("2")) return true;

        return input.contains("pee") || input.contains("piss") ||
                input.contains("poop") || input.contains("kúk") ||
                input.contains("toilet") || input.contains("klósett");
    }

    public static boolean isNormalThemeSelected(String input) {
        input = input.toLowerCase().trim();

        if (input.equals("1")) return true;

        return input.contains("skæri") || input.contains("scissors") ||
                input.contains("blað") || input.contains("paper") ||
                input.contains("steinn") || input.contains("stone") ||
                input.contains("venjuleg") || input.contains("normal");
    }

    public static String[] getMoveNamesForTheme(GameTheme theme) {
        if (theme == GameTheme.STANDARD) {
            return new String[] {
                    TranslationManager.get("rock"),
                    TranslationManager.get("paper"),
                    TranslationManager.get("scissors")
            };
        } else {
            return new String[] {
                    TranslationManager.get("poop"),
                    TranslationManager.get("toilet_paper"),
                    TranslationManager.get("pee")
            };
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

