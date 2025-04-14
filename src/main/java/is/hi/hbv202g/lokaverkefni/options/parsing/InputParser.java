package is.hi.hbv202g.lokaverkefni.options.parsing;

public class InputParser {
    public static boolean isYes(String input) {
        input = input.toLowerCase().trim();
        return input.matches("y|yes|yeah|yep|j|já|jú");
    }

    public static boolean isNo(String input) {
        input = input.toLowerCase().trim();
        return input.matches("n|no|nope|nei");
    }

    public static boolean isBathroomTheme(String input) {
        input = input.toLowerCase().trim();
        return input.equals("2") || input.contains("poop") || input.contains("pee") || input.contains("piss") ||
                input.contains("kúk") || input.contains("toilet") || input.contains("klósett");
    }

    public static boolean isNormalTheme(String input) {
        input = input.toLowerCase().trim();
        return input.equals("1") || input.contains("rock") || input.contains("paper") || input.contains("scissors") ||
                input.contains("steinn") || input.contains("blað") || input.contains("skæri") || input.contains("normal");
    }
}