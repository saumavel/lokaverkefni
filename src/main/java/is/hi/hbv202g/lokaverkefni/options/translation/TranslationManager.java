package is.hi.hbv202g.lokaverkefni.options.translation;

import is.hi.hbv202g.lokaverkefni.options.enums.Language;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *  Manages translations
 */
public class TranslationManager {
    private static Language currentLanguage = Language.ENGLISH;
    private static final Map<String, Map<Language, String>> translations = new HashMap<>();

    /**
     * Set the language.
     * @param lang language
     */
    public static void setLanguage(Language lang) {
        currentLanguage = lang;
    }

    /**
     * Get the translation.
     * @param key lykill
     * @return string in the output.
     */
    public static String get(String key) {
        Map<Language, String> map = translations.get(key);
        if (map == null) {
            return "Missing translation: " + key;
        }
        return map.getOrDefault(currentLanguage, map.get(Language.ENGLISH));
    }

    /**
     * Add a translation.
     * @param key lykill
     * @param en english
     * @param is icelandic
     */
    public static void add(String key, String en, String is) {
        Map<Language, String> langMap = new HashMap<>();
        langMap.put(Language.ENGLISH, en);
        langMap.put(Language.ICELANDIC, is);
        translations.put(key, langMap);
    }

    /**
     * Prompt the user to select a language.
     * @param scanner scanner
     */
    public static void promptLanguageSelection(Scanner scanner) {
        setLanguage(Language.ENGLISH); // default

        while (true) {
            System.out.println(get("select_language"));
            System.out.println("1. " + get("english"));
            System.out.println("2. Íslenska");

            String input = scanner.nextLine().toLowerCase();
            if (input.equals("2") || input.contains("ísl") || input.contains("isl") || input.contains("ice")) {
                setLanguage(Language.ICELANDIC);
                System.out.println("Íslenska valin.");
                break;
            } else if (input.equals("1") || input.contains("eng") || input.contains("ens")) {
                setLanguage(Language.ENGLISH);
                System.out.println("English selected.");
                break;
            }
            else if (input.isEmpty()) {
                System.out.println(get("invalid_name_letters"));
            } else {
                System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }
    }
}