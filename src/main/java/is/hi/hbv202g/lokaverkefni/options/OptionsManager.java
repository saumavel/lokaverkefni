package is.hi.hbv202g.lokaverkefni.options;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages language settings and translations for the game.
 */
public class OptionsManager {
    private static Language currentLanguage = Language.ENGLISH; // Default language
    private static final Map<String, Map<Language, String>> translations = new HashMap<>();
    // Maps for theme-specific move names and their indices
    private static final Map<String, Integer> standardMoveMap = new HashMap<>();
    private static final Map<String, Integer> bathroomMoveMap = new HashMap<>();

    // Maps for common responses
    private static final Map<String, Boolean> yesNoResponses = new HashMap<>();

    // Maps for difficulty levels
    private static final Map<String, String> difficultyMap = new HashMap<>();
    // Initialize translations
    static {
        // Game setup translations
        addTranslation("welcome",
                "Welcome to the Game!",
                "Velkomin(n) í leikinn!");
        addTranslation("select_theme",
                "Rock Paper Scissors or Pee, Poop or Toiletpaper:",
                "Skæri, Blað, Steinn eða Piss, Kúkur, Klósettpappír:");
        addTranslation("theme_standard",
                "Rock, Paper, Scissors",
                "Skæri, Blað, Steinn");
        addTranslation("theme_bathroom",
                "Pee, Poop, Toilet Paper",
                "Piss, Kúkur, Klósettpappír");
        addTranslation("theme_standard_selected",
                "You chose Rock, Paper, Scissors",
                "Þú valdir Skæri, Blað, Steinn");
        addTranslation("theme_bathroom_selected",
                "You chose Pee, Poop, Toilet Paper",
                "Þú valdir Piss, Kúkur, Klósettpappír");
        addTranslation("select_players",
                "Write 1 for a one player game or 2 for a two-player game",
                "Skrifaðu 1 fyrir 1 leikmann eða 2 ef þú ert að spila við vin");
        addTranslation("invalid_input",
                "Invalid input. Please try again",
                "Ógilt inntak. Vinsamlegast reyndu aftur.");

        // One player game setup
        addTranslation("one_player_selected",
                "You have selected one player game.\nWhat is your name?",
                "Þú hefur valið eins leikmanns leik.\nHvað heitir þú?");
        addTranslation("select_difficulty",
                "Select difficulty level:",
                "Veldu erfiðleikastig:");
        addTranslation("difficulty_man",
                "Man (Always plays ",
                "Maður (Spilar alltaf ");
        addTranslation("difficulty_easy",
                "Easy",
                "Auðvelt");
        addTranslation("difficulty_medium",
                "Medium",
                "Miðlungs");
        addTranslation("difficulty_hard",
                "Hard",
                "Erfitt");
        addTranslation("difficulty_man_selected",
                "You have decided to play against a man. A man believes that a win is always possible with ",
                "Þú hefur valið að spila við mann. Menn halda að sigur fáist alltaf með ");
        addTranslation("difficulty_easy_selected",
                "You are now playing difficulty level : Easy",
                "Erfiðleikastig : Maður");
        addTranslation("difficulty_medium_selected",
                "You are now playing difficulty level : Medium",
                "Erfiðleikastig : Miðlungs");
        addTranslation("difficulty_hard_selected",
                "You are now playing difficulty level : Hard",
                "Erfiðleikastig : Erfitt");

        // Two player game setup
        addTranslation("two_player_selected",
                "You have selected two player game.",
                "Þú hefur valið að spila við vin.");
        addTranslation("enter_name_player1",
                "Enter name for Player 1:",
                "Sláðu inn nafn fyrir Leikmann 1:");
        addTranslation("enter_name_player2",
                "Enter name for Player 2:",
                "Sláðu inn nafn fyrir Leikmann 2:");

        // Game round
        addTranslation("round",
                "ROUND",
                "UMFERÐ");
        addTranslation("secret_hint",
                "🤫 psst... Hate losing? Press 'u' and see what happens.",
                "🤫 psst... Hatar þú að tapa? Ýttu á 'u' og sjáðu hvað gerist.");
        addTranslation("choose_move",
                ", choose your move:",
                ", veldu hendi:");
        addTranslation("you_chose",
                "You chose ",
                "Þú valdir ");
        addTranslation("player_chose",
                " chose ",
                " valdi ");
        addTranslation("computer_chose",
                "Computer chose ",
                "Tölvan valdi ");
        addTranslation("quit_game",
                "You chose to quit the game.",
                "Þú valdir að hætta í leiknum.");
        addTranslation("player_quit_game",
                " chose to quit the game.",
                " valdi að hætta í leiknum.");
        addTranslation("invalid_choice",
                "Invalid choice. Please select a valid option.",
                "Ógilt val. Vinsamlegast veldu gildan valmöguleika.");
        addTranslation("invalid_choice_default",
                "Invalid choice. Defaulting to first option.",
                "Ógilt val. Sjálfgefið á fyrsta valmöguleika.");
        addTranslation("invalid_choice_number",
                "Invalid choice. Please enter a number.",
                "Ógilt val. Vinsamlegast sláðu inn tölu.");

        // Undo and game results
        addTranslation("undo_move",
                "Last move undone.",
                "Síðasta færsla afturkölluð.");
        addTranslation("undo_note",
                "Meow",
                "Mjáw");
        addTranslation("undo_secret",
                "😉 A clean game is not always the best... This will be our little secret. 😉",
                "😉 Það er ekki alltaf best að segja sannleikann... Þetta verður littla leyndarmálið okkar. 😉");
        addTranslation("player_wins",
                " WINS THIS ROUND! 🎉",
                " VINNUR ÞESSA UMFERÐ! 🎉");
        addTranslation("computer_wins",
                "💻 COMPUTER WINS THIS ROUND! 💻",
                "💻 TÖLVAN VINNUR ÞESSA UMFERÐ! 💻");
        addTranslation("draw",
                "🤝 IT'S A DRAW! 🤝",
                "🤝 ÞAÐ ER JAFNTEFLI! 🤝");

        // Difficulty increase
        addTranslation("win_streak",
                "Impressive! You've won 5 games in a row!",
                "Glæsilegt! Þú hefur unnið 5 leiki í röð!");
        addTranslation("increase_difficulty",
                "Would you like to increase the difficulty? (y/n)",
                "Viltu auka erfiðleikastigið? (y/n)");
        addTranslation("difficulty_increased_easy",
                "Difficulty increased to Easy.",
                "Erfiðleikastig er núna Auðvelt.");
        addTranslation("difficulty_increased_medium",
                "Difficulty increased to Medium.",
                "Erfiðleikastig er núna Miðlungs.");
        addTranslation("difficulty_increased_hard",
                "Difficulty increased to Hard.",
                "Erfiðleikastig er núna Erfitt.");
        addTranslation("max_difficulty",
                "You're already at the highest difficulty!",
                "Þú ert nú þegar á hæsta erfiðleikastigi!");

        // Continue playing
        addTranslation("continue_playing",
                "\nYou've played ",
                "\nÞú hefur spilað ");
        addTranslation("rounds",
                " rounds. Do you want to continue playing? (y/n)",
                " umferðir. Viltu halda áfram að spila? (y/n)");
        addTranslation("thanks_for_playing",
                "Thanks for playing!",
                "Takk fyrir að spila!");

        // Standard theme moves
        addTranslation("rock", "Rock", "Steinn");
        addTranslation("paper", "Paper", "Blað");
        addTranslation("scissors", "Scissors", "Skæri");

        // Bathroom theme moves
        addTranslation("poop", "Poop", "Kúkur");
        addTranslation("toilet_paper", "Toilet Paper", "Klósettpappír");
        addTranslation("pee", "Pee", "Piss");

        // Language selection
        addTranslation("select_language",
                "Select language / Veldu tungumál:",
                "Select language / Veldu tungumál:");
        addTranslation("english", "English", "Enska");
        addTranslation("icelandic", "Icelandic", "Íslenska");

        addTranslation("invalid_choice_number",
                "Invalid choice. Please try again!",
                "Ógilt val. Vinsamlegast reyndu aftur!");

        addTranslation("invalid_name",
                "Name cannot be empty. Please enter at least one character.",
                "Nafn má ekki vera tómt. Vinsamlegast sláðu inn að minnsta kosti einn staf.");

        // Initialize move name mappings
        initializeMoveNameMappings();

        // Initialize yes/no responses
        initializeYesNoResponses();

        // Initialize difficulty mappings
        initializeDifficultyMappings();
    }

    /**
     * Initializes the mapping between move names and their indices.
     * This supports both English and Icelandic names for moves.
     */
    private static void initializeMoveNameMappings() {
        // Standard theme moves
        // English
        standardMoveMap.put("rock", 0);
        standardMoveMap.put("paper", 1);
        standardMoveMap.put("scissors", 2);
        // Icelandic
        standardMoveMap.put("steinn", 0);
        standardMoveMap.put("blað", 1);
        standardMoveMap.put("skæri", 2);

        // Bathroom theme moves
        // English
        bathroomMoveMap.put("poop", 0);
        bathroomMoveMap.put("toilet paper", 1);
        bathroomMoveMap.put("toiletpaper", 1);
        bathroomMoveMap.put("pee", 2);
        // Icelandic
        bathroomMoveMap.put("kúkur", 0);
        bathroomMoveMap.put("klósettpappír", 1);
        bathroomMoveMap.put("piss", 2);
    }

    /**
     * Initializes common yes/no responses for both languages.
     */
    private static void initializeYesNoResponses() {
        // English affirmative responses
        yesNoResponses.put("y", true);
        yesNoResponses.put("yes", true);
        yesNoResponses.put("yeah", true);
        yesNoResponses.put("yep", true);

        // Icelandic affirmative responses
        yesNoResponses.put("j", true);
        yesNoResponses.put("já", true);
        yesNoResponses.put("jú", true);

        // English negative responses
        yesNoResponses.put("n", false);
        yesNoResponses.put("no", false);
        yesNoResponses.put("nope", false);

        // Icelandic negative responses
        yesNoResponses.put("nei", false);
    }
    /**
     * Initializes difficulty level mappings.
     */
    private static void initializeDifficultyMappings() {
        // English
        difficultyMap.put("man", "1");
        difficultyMap.put("easy", "2");
        difficultyMap.put("medium", "3");
        difficultyMap.put("hard", "4");

        // Icelandic
        difficultyMap.put("maður", "1");
        difficultyMap.put("auðvelt", "2");
        difficultyMap.put("miðlungs", "3");
        difficultyMap.put("erfitt", "4");
    }


    /**
     * Adds a translation for a key in all supported languages.
     *
     * @param key The translation key
     * @param english The English translation
     * @param icelandic The Icelandic translation
     */
    private static void addTranslation(String key, String english, String icelandic) {
        Map<Language, String> languageMap = new HashMap<>();
        languageMap.put(Language.ENGLISH, english);
        languageMap.put(Language.ICELANDIC, icelandic);
        translations.put(key, languageMap);
    }

    /**
     * Sets the current language.
     *
     * @param language The language to set
     */
    public static void setLanguage(Language language) {
        currentLanguage = language;
    }

    /**
     * Gets the current language.
     *
     * @return The current language
     */
    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

    /**
     * Gets a translated string for the given key.
     *
     * @param key The translation key
     * @return The translated string in the current language
     */
    public static String get(String key) {
        Map<Language, String> languageMap = translations.get(key);
        if (languageMap == null) {
            return "Missing translation: " + key;
        }
        String translation = languageMap.get(currentLanguage);
        if (translation == null) {
            // Fallback to English if translation is missing
            translation = languageMap.get(Language.ENGLISH);
            if (translation == null) {
                return "Missing translation: " + key;
            }
        }
        return translation;
    }

    /**
     * Prompts the user to select a language.
     *
     * @param scanner The scanner to read input from
     */
    public static void promptLanguageSelection(java.util.Scanner scanner) {
        // Default to English for the initial prompt
        setLanguage(Language.ENGLISH);

        String choice;
        boolean isValidChoice = false;

        while (!isValidChoice) {
            System.out.println(get("select_language"));
            System.out.println("1. " + get("english"));
            System.out.println("2. Íslenska" );

            choice = scanner.nextLine().toLowerCase();

            // Accept both numbers and language names
            if (choice.equals("2") || choice.contains("ísl") || choice.contains("isl") || choice.contains("ice")) {
                setLanguage(Language.ICELANDIC);
                System.out.println("Íslenska valin.");
                isValidChoice = true;
            } else if (choice.equals("1") || choice.contains("eng") || choice.contains("ens")) {
                setLanguage(Language.ENGLISH);
                System.out.println("English selected.");
                isValidChoice = true;
            } else {
                // Show error message in English since we don't know the preferred language yet
                System.out.println("Invalid choice. Please select 1 for English or 2 for Icelandic.");
            }
        }
    }
    /**
     * Gets the move index from the input text for the specified theme.
     *
     * @param input The user's input text
     * @param theme The current game theme
     * @return The move index if found, null otherwise
     */
    public static Integer getMoveIndexFromInput(String input, GameTheme theme) {
        input = input.toLowerCase().trim();

        Map<String, Integer> moveMap = (theme == GameTheme.STANDARD) ? standardMoveMap : bathroomMoveMap;

        // Check for exact match first
        if (moveMap.containsKey(input)) {
            return moveMap.get(input);
        }

        // Check if the input contains any of the move names
        for (Map.Entry<String, Integer> entry : moveMap.entrySet()) {
            if (input.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        return null;
    }

    /**
     * Determines if the given input represents a "yes" response.
     *
     * @param input The user's input
     * @return true if the input is an affirmative response, false otherwise
     */
    public static boolean isAffirmativeResponse(String input) {
        input = input.toLowerCase().trim();

        Boolean response = yesNoResponses.get(input);
        return response != null && response;
    }

    /**
     * Determines if the given input represents a "no" response.
     *
     * @param input The user's input
     * @return true if the input is a negative response, false otherwise
     */
    public static boolean isNegativeResponse(String input) {
        input = input.toLowerCase().trim();

        Boolean response = yesNoResponses.get(input);
        return response != null && !response;
    }

    /**
     * Gets the difficulty level from text input.
     *
     * @param input The user's input
     * @return The difficulty level as a string ("1", "2", "3", or "4"), or null if not recognized
     */
    public static String getDifficultyFromInput(String input) {
        input = input.toLowerCase().trim();

        // Check if input is already a number
        if (input.matches("[1-4]")) {
            return input;
        }

        // Check if input contains a difficulty name
        for (Map.Entry<String, String> entry : difficultyMap.entrySet()) {
            if (input.contains(entry.getKey())) {
                return entry.getValue();
            }
        }

        // Default to easy if not recognized
        return "2";
    }

    /**
     * Determines if the input indicates a bathroom theme selection.
     *
     * @param input The user's input
     * @return true if the input indicates bathroom theme, false otherwise
     */
    public static boolean isBathroomThemeSelected(String input) {
        input = input.toLowerCase().trim();

        if (input.equals("2")) {
            return true;
        }

        // Check for bathroom theme keywords
        return input.contains("pee") || input.contains("piss") ||
                input.contains("poop") || input.contains("kúk") ||
                input.contains("toilet") || input.contains("klósett");
    }
    public static boolean isNormalThemeSelected(String input) {
        input = input.toLowerCase().trim();

        if (input.equals("1")) {
            return true;
        }

        // Check for bathroom theme keywords
        return input.contains("skæri") || input.contains("scissors") ||
                input.contains("blað") || input.contains("paper") ||
                input.contains("steinn") || input.contains("stone") ||
                input.contains("venjuleg") || input.contains("normal");
    }

    /**
     * Gets all available move names for a specific theme in the current language.
     *
     * @param theme The game theme
     * @return Array of move names
     */
    public static String[] getMoveNamesForTheme(GameTheme theme) {
        if (theme == GameTheme.STANDARD) {
            return new String[] {
                    get("rock"),
                    get("paper"),
                    get("scissors")
            };
        } else {
            return new String[] {
                    get("poop"),
                    get("toilet_paper"),
                    get("pee")
            };
        }
    }
}
