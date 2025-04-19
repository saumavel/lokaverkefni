package is.hi.hbv202g.lokaverkefni.options;

import java.util.Map;

/**
 *  Parses user input into a difficulty level for the computer player.
 */
public class DifficultyUtils {
    /**
     *  Parses the input string and returns the corresponding difficulty level as a string number.
     * @param input  The user input, e.g. "easy", "erfitt", or "2".
     * @param difficultyMap The map of difficulty levels and their corresponding string numbers.
     * @return A string number between "1" and "4" if matched, or "wrong" if unrecognized.
     */
    public static String parseDifficulty(String input, Map<String, String> difficultyMap) {
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
}