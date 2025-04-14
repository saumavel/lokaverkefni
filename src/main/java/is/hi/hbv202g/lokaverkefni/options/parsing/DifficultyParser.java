package is.hi.hbv202g.lokaverkefni.options.parsing;

import java.util.HashMap;
import java.util.Map;

public class DifficultyParser {
    private static final Map<String, String> difficultyMap = new HashMap<>();

    static {
        difficultyMap.put("man", "1");
        difficultyMap.put("easy", "2");
        difficultyMap.put("medium", "3");
        difficultyMap.put("hard", "4");

        difficultyMap.put("maður", "1");
        difficultyMap.put("auðvelt", "2");
        difficultyMap.put("miðlungs", "3");
        difficultyMap.put("erfitt", "4");
    }

    public static String parse(String input) {
        input = input.toLowerCase().trim();
        if (input.matches("[1-4]")) return input;
        for (Map.Entry<String, String> entry : difficultyMap.entrySet()) {
            if (input.contains(entry.getKey())) return entry.getValue();
        }
        return "wrong";
    }
}