package is.hi.hbv202g.lokaverkefni.options;

import is.hi.hbv202g.lokaverkefni.options.enums.GameMode;
import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import is.hi.hbv202g.lokaverkefni.options.enums.Language;
import org.junit.Test;
import static org.junit.Assert.*;

public class EnumsTest {

    @Test
    public void testGameModeValues() {
        // Test that GameMode has the expected values
        GameMode[] modes = GameMode.values();
        assertEquals("GameMode should have 2 values", 2, modes.length);
        assertEquals("First mode should be SINGLE_PLAYER", GameMode.SINGLE_PLAYER, modes[0]);
        assertEquals("Second mode should be MULTIPLAYER", GameMode.MULTIPLAYER, modes[1]);
    }

    @Test
    public void testLanguageValues() {
        // Test that Language has the expected values
        Language[] languages = Language.values();
        assertEquals("Language should have 2 values", 2, languages.length);
        assertEquals("First language should be ENGLISH", Language.ENGLISH, languages[0]);
        assertEquals("Second language should be ICELANDIC", Language.ICELANDIC, languages[1]);
    }

    @Test
    public void testGameThemeValues() {
        // Test that GameTheme has the expected values
        GameTheme[] themes = GameTheme.values();
        assertEquals("GameTheme should have 2 values", 2, themes.length);
        assertEquals("First theme should be STANDARD", GameTheme.STANDARD, themes[0]);
        assertEquals("Second theme should be BATHROOM", GameTheme.BATHROOM, themes[1]);
    }

    @Test
    public void testGameThemeMoveNames() {
        // Test that GameTheme.getMoveName returns the expected values
        // Note: This depends on OptionsManager.get() which we'll mock in a more comprehensive test

        // For now, we'll just check that the method doesn't throw an exception
        GameTheme.STANDARD.getMoveName(0);
        GameTheme.STANDARD.getMoveName(1);
        GameTheme.STANDARD.getMoveName(2);

        GameTheme.BATHROOM.getMoveName(0);
        GameTheme.BATHROOM.getMoveName(1);
        GameTheme.BATHROOM.getMoveName(2);

        // Test invalid index returns "Unknown"
        assertEquals("Invalid index should return 'Unknown'", "Unknown", GameTheme.STANDARD.getMoveName(5));
    }

    @Test
    public void testGameThemeGetMoveNames() {
        // Test that GameTheme.getMoveNames returns arrays of the expected length
        assertEquals("Standard theme should have 3 moves", 3, GameTheme.STANDARD.getMoveNames().length);
        assertEquals("Bathroom theme should have 3 moves", 3, GameTheme.BATHROOM.getMoveNames().length);
    }

    @Test
    public void testGameThemeNumberOfMoves() {
        // Test that GameTheme.getNumberOfMoves returns the expected value
        assertEquals("Standard theme should have 3 moves", 3, GameTheme.STANDARD.getNumberOfMoves());
        assertEquals("Bathroom theme should have 3 moves", 3, GameTheme.BATHROOM.getNumberOfMoves());
    }
}

