package is.hi.hbv202g.lokaverkefni.options;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages language settings and translations for the game.
 */
public class LanguageManager {
    private static Language currentLanguage = Language.ENGLISH; // Default language
    private static final Map<String, Map<Language, String>> translations = new HashMap<>();

    // Initialize translations
    static {
        // Game setup translations
        addTranslation("welcome",
                "Welcome to the Game!",
                "Velkomin(n) í leikinn!");
        addTranslation("select_theme",
                "Select game theme:",
                "Veldu þema leiksins:");
        addTranslation("theme_standard",
                "Standard (Rock, Paper, Scissors)",
                "Hefðbundið (Steinn, Blað, Skæri)");
        addTranslation("theme_bathroom",
                "Bathroom (Poop, Toilet Paper, Pee)",
                "Baðherbergi (Kúkur, Klósettpappír, Piss)");
        addTranslation("theme_standard_selected",
                "Standard theme selected.",
                "Hefðbundið þema valið.");
        addTranslation("theme_bathroom_selected",
                "Bathroom theme selected!",
                "Baðherbergisþema valið!");
        addTranslation("select_players",
                "Write 1 for a one player game or 2 for a two-player game",
                "Skrifaðu 1 fyrir eins leikmanns leik eða 2 fyrir tveggja leikmanna leik");
        addTranslation("invalid_input",
                "Invalid input. Defaulting to one player game.",
                "Ógilt inntak. Sjálfgefið á eins leikmanns leik.");

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
                "Man difficulty selected. Your opponent will always play ",
                "Maður erfiðleikastig valið. Andstæðingur þinn mun alltaf spila ");
        addTranslation("difficulty_easy_selected",
                "Easy difficulty selected.",
                "Auðvelt erfiðleikastig valið.");
        addTranslation("difficulty_medium_selected",
                "Medium difficulty selected.",
                "Miðlungs erfiðleikastig valið.");
        addTranslation("difficulty_hard_selected",
                "Hard difficulty selected.",
                "Erfitt erfiðleikastig valið.");

        // Two player game setup
        addTranslation("two_player_selected",
                "You have selected two player game.",
                "Þú hefur valið tveggja leikmanna leik.");
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
                "🤫 Hate losing? Press 'u' and see what happens. It will be our little secret. 🤫",
                "🤫 Hatarðu að tapa? Ýttu á 'u' og sjáðu hvað gerist. Þetta verður okkar litla leyndarmál. 🤫");
        addTranslation("choose_move",
                ", choose your move:",
                ", veldu hreyfingu þína:");
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
                "Ógilt val. Vinsamlegast veldu gilt valmöguleika.");
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
                "Note: Scores for the current round may need to be recalculated.",
                "Athugið: Stig fyrir núverandi umferð gætu þurft að vera endurreiknuð.");
        addTranslation("undo_secret",
                "😉 A clean game is not always the best... It will be our little secret. 😉",
                "😉 Hreinn leikur er ekki alltaf bestur... Þetta verður okkar litla leyndarmál. 😉");
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
                "Erfiðleikastig aukið í Auðvelt.");
        addTranslation("difficulty_increased_medium",
                "Difficulty increased to Medium.",
                "Erfiðleikastig aukið í Miðlungs.");
        addTranslation("difficulty_increased_hard",
                "Difficulty increased to Hard.",
                "Erfiðleikastig aukið í Erfitt.");
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
        System.out.println(get("select_language"));
        System.out.println("1. " + get("english"));
        System.out.println("2. " + get("icelandic"));

        String choice = scanner.nextLine();

        if (choice.equals("2")) {
            setLanguage(Language.ICELANDIC);
            System.out.println("Íslenska valin.");
        } else {
            setLanguage(Language.ENGLISH);
            System.out.println("English selected.");
        }
    }
}
