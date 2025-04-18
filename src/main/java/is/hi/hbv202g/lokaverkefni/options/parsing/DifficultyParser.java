package is.hi.hbv202g.lokaverkefni.options.parsing;

import is.hi.hbv202g.lokaverkefni.options.DifficultyUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Parses user input into a difficulty level for the computer player.
 * <p>
 * Accepts English and Icelandic names as well as numbers 1-4:
 * <ul>
 *     <li>1 - Man / Maður</li>
 *     <li>2 - Easy / Auðvelt</li>
 *     <li>3 - Medium / Miðlungs</li>
 *     <li>4 - Hard / Erfitt</li>
 * </ul>
 */
public class DifficultyParser {
    /** Maps keywords to corresponding difficulty levels. */
    private static final Map<String, String> difficultyMap = new HashMap<>();

    static {
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
     * Parses the input string and returns the corresponding difficulty level as a string number.
     *
     * @param input The user input, e.g. "easy", "erfitt", or "2".
     * @return A string number between "1" and "4" if matched, or "wrong" if unrecognized.
     */
    public static String parse(String input) {
        String difficulty = DifficultyUtils.parseDifficulty(input, difficultyMap);
        return difficulty.equals("wrong") ? "wrong" : difficulty;
    }
}