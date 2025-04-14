package is.hi.hbv202g.lokaverkefni.options.translation;

import is.hi.hbv202g.lokaverkefni.options.enums.Language;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TranslationManager {
    private static Language currentLanguage = Language.ENGLISH;
    private static final Map<String, Map<Language, String>> translations = new HashMap<>();

    public static void setLanguage(Language lang) {
        currentLanguage = lang;
    }

    public static Language getLanguage() {
        return currentLanguage;
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

    public static void add(String key, String en, String is) {
        Map<Language, String> langMap = new HashMap<>();
        langMap.put(Language.ENGLISH, en);
        langMap.put(Language.ICELANDIC, is);
        translations.put(key, langMap);
    }

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
            } else {
                System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }
    }
}