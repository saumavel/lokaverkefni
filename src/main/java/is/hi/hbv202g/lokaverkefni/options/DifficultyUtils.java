package is.hi.hbv202g.lokaverkefni.options;

import java.util.Map;

public class DifficultyUtils {
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