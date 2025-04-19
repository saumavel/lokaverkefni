package is.hi.hbv202g.lokaverkefni.options.parsing;

import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;

import java.util.HashMap;
import java.util.Map;

/**
 *  Maps user input to move index.
 */
public class MoveNameMapper {
    private static final Map<String, Integer> standard = new HashMap<>();
    private static final Map<String, Integer> bathroom = new HashMap<>();

    static {
        // Standard
        standard.put("rock", 0); standard.put("paper", 1); standard.put("scissors", 2);
        standard.put("steinn", 0); standard.put("blað", 1); standard.put("skæri", 2);

        // Bathroom
        bathroom.put("poop", 0); bathroom.put("toilet paper", 1); bathroom.put("toiletpaper", 1); bathroom.put("pee", 2);
        bathroom.put("kúkur", 0); bathroom.put("klósettpappír", 1); bathroom.put("piss", 2);
    }

    /**
     *  Maps user input to move index.
     * @param input the user input
     * @param theme the game theme
     * @return the move index
     */
    public static Integer getMoveIndex(String input, GameTheme theme) {
        input = input.toLowerCase().trim();
        Map<String, Integer> map = (theme == GameTheme.STANDARD) ? standard : bathroom;

        if (map.containsKey(input)) return map.get(input);

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (input.contains(entry.getKey())) return entry.getValue();
        }
        return null;
    }
}