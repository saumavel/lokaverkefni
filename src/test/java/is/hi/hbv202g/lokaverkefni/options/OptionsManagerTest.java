package is.hi.hbv202g.lokaverkefni.options;

import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.Assert.*;

public class OptionsManagerTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));

        // Reset to English for each test
        OptionsManager.setLanguage(Language.ENGLISH);
    }

    @Test
    public void testGetTranslation() {
        // Test getting translations in English
        OptionsManager.setLanguage(Language.ENGLISH);
        assertEquals("Rock", OptionsManager.get("rock"));
        assertEquals("Paper", OptionsManager.get("paper"));
        assertEquals("Scissors", OptionsManager.get("scissors"));

        // Test getting translations in Icelandic
        OptionsManager.setLanguage(Language.ICELANDIC);
        assertEquals("Steinn", OptionsManager.get("rock"));
        assertEquals("Blað", OptionsManager.get("paper"));
        assertEquals("Skæri", OptionsManager.get("scissors"));

        // Test bathroom theme translations
        assertEquals("Kúkur", OptionsManager.get("poop"));
        assertEquals("Klósettpappír", OptionsManager.get("toilet_paper"));
        assertEquals("Piss", OptionsManager.get("pee"));

        // Test missing translation key
        assertTrue(OptionsManager.get("nonexistent_key").contains("Missing translation"));
    }

    @Test
    public void testLanguageSelection() {
        // Simulate user selecting English
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Scanner scanner = new Scanner(System.in);

        OptionsManager.promptLanguageSelection(scanner);
        assertTrue(outputStream.toString().contains("English selected"));

        // Reset output stream
        outputStream.reset();

        // Simulate user selecting Icelandic
        input = "2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scanner = new Scanner(System.in);

        OptionsManager.promptLanguageSelection(scanner);
        assertTrue(outputStream.toString().contains("Íslenska valin"));

        // Reset output stream
        outputStream.reset();

        // Simulate user entering "english" instead of a number
        input = "english\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scanner = new Scanner(System.in);

        OptionsManager.promptLanguageSelection(scanner);
        assertTrue(outputStream.toString().contains("English selected"));

        // Reset System.in
        System.setIn(System.in);
    }

    @Test
    public void testGetMoveIndexFromInput() {
        // Test standard theme move recognition
        assertEquals(Integer.valueOf(0), OptionsManager.getMoveIndexFromInput("rock", GameTheme.STANDARD));
        assertEquals(Integer.valueOf(1), OptionsManager.getMoveIndexFromInput("paper", GameTheme.STANDARD));
        assertEquals(Integer.valueOf(2), OptionsManager.getMoveIndexFromInput("scissors", GameTheme.STANDARD));

        // Test case insensitivity
        assertEquals(Integer.valueOf(0), OptionsManager.getMoveIndexFromInput("ROCK", GameTheme.STANDARD));
        assertEquals(Integer.valueOf(1), OptionsManager.getMoveIndexFromInput("Paper", GameTheme.STANDARD));

        // Test Icelandic move names
        assertEquals(Integer.valueOf(0), OptionsManager.getMoveIndexFromInput("steinn", GameTheme.STANDARD));
        assertEquals(Integer.valueOf(1), OptionsManager.getMoveIndexFromInput("blað", GameTheme.STANDARD));
        assertEquals(Integer.valueOf(2), OptionsManager.getMoveIndexFromInput("skæri", GameTheme.STANDARD));

        // Test bathroom theme move recognition
        assertEquals(Integer.valueOf(0), OptionsManager.getMoveIndexFromInput("poop", GameTheme.BATHROOM));
        assertEquals(Integer.valueOf(1), OptionsManager.getMoveIndexFromInput("toilet paper", GameTheme.BATHROOM));
        assertEquals(Integer.valueOf(2), OptionsManager.getMoveIndexFromInput("pee", GameTheme.BATHROOM));

        // Test Icelandic bathroom theme move names
        assertEquals(Integer.valueOf(0), OptionsManager.getMoveIndexFromInput("kúkur", GameTheme.BATHROOM));
        assertEquals(Integer.valueOf(1), OptionsManager.getMoveIndexFromInput("klósettpappír", GameTheme.BATHROOM));
        assertEquals(Integer.valueOf(2), OptionsManager.getMoveIndexFromInput("piss", GameTheme.BATHROOM));

        // Test partial match
        assertEquals(Integer.valueOf(0), OptionsManager.getMoveIndexFromInput("I choose rock", GameTheme.STANDARD));

        // Test invalid input
        assertNull(OptionsManager.getMoveIndexFromInput("invalid", GameTheme.STANDARD));
    }

    @Test
    public void testIsAffirmativeResponse() {
        // Test English affirmative responses
        assertTrue(OptionsManager.isAffirmativeResponse("y"));
        assertTrue(OptionsManager.isAffirmativeResponse("yes"));
        assertTrue(OptionsManager.isAffirmativeResponse("yeah"));
        assertTrue(OptionsManager.isAffirmativeResponse("yep"));

        // Test Icelandic affirmative responses
        assertTrue(OptionsManager.isAffirmativeResponse("j"));
        assertTrue(OptionsManager.isAffirmativeResponse("já"));
        assertTrue(OptionsManager.isAffirmativeResponse("jú"));

        // Test negative responses
        assertFalse(OptionsManager.isAffirmativeResponse("n"));
        assertFalse(OptionsManager.isAffirmativeResponse("no"));
        assertFalse(OptionsManager.isAffirmativeResponse("nope"));
        assertFalse(OptionsManager.isAffirmativeResponse("nei"));

        // Test case insensitivity
        assertTrue(OptionsManager.isAffirmativeResponse("YES"));
        assertFalse(OptionsManager.isAffirmativeResponse("NO"));

        // Test invalid input
        assertFalse(OptionsManager.isAffirmativeResponse("invalid"));
    }

    @Test
    public void testGetDifficultyFromInput() {
        // Test numeric inputs
        assertEquals("1", OptionsManager.getDifficultyFromInput("1"));
        assertEquals("2", OptionsManager.getDifficultyFromInput("2"));
        assertEquals("3", OptionsManager.getDifficultyFromInput("3"));
        assertEquals("4", OptionsManager.getDifficultyFromInput("4"));

        // Test English difficulty names
        assertEquals("1", OptionsManager.getDifficultyFromInput("man"));
        assertEquals("2", OptionsManager.getDifficultyFromInput("easy"));
        assertEquals("3", OptionsManager.getDifficultyFromInput("medium"));
        assertEquals("4", OptionsManager.getDifficultyFromInput("hard"));

        // Test Icelandic difficulty names
        assertEquals("1", OptionsManager.getDifficultyFromInput("maður"));
        assertEquals("2", OptionsManager.getDifficultyFromInput("auðvelt"));
        assertEquals("3", OptionsManager.getDifficultyFromInput("miðlungs"));
        assertEquals("4", OptionsManager.getDifficultyFromInput("erfitt"));

        // Test partial match
        assertEquals("2", OptionsManager.getDifficultyFromInput("I want easy mode"));

        // Test invalid input
        assertEquals("wrong", OptionsManager.getDifficultyFromInput("invalid"));

        // Test empty input (should default to "wrong")
        assertEquals("wrong", OptionsManager.getDifficultyFromInput(""));
    }

    @Test
    public void testIsBathroomThemeSelected() {
        // Test numeric input
        assertTrue(OptionsManager.isBathroomThemeSelected("2"));
        assertFalse(OptionsManager.isBathroomThemeSelected("1"));

        // Test English bathroom theme keywords
        assertTrue(OptionsManager.isBathroomThemeSelected("pee"));
        assertTrue(OptionsManager.isBathroomThemeSelected("poop"));
        assertTrue(OptionsManager.isBathroomThemeSelected("toilet"));

        // Test Icelandic bathroom theme keywords
        assertTrue(OptionsManager.isBathroomThemeSelected("piss"));
        assertTrue(OptionsManager.isBathroomThemeSelected("kúk"));
        assertTrue(OptionsManager.isBathroomThemeSelected("klósett"));

        // Test partial match
        assertTrue(OptionsManager.isBathroomThemeSelected("I want to play with poop"));

        // Test invalid input
        assertFalse(OptionsManager.isBathroomThemeSelected("invalid"));
    }

    @Test
    public void testIsNormalThemeSelected() {
        // Test numeric input
        assertTrue(OptionsManager.isNormalThemeSelected("1"));
        assertFalse(OptionsManager.isNormalThemeSelected("2"));

        // Test English standard theme keywords
        assertTrue(OptionsManager.isNormalThemeSelected("scissors"));
        assertTrue(OptionsManager.isNormalThemeSelected("paper"));
        assertTrue(OptionsManager.isNormalThemeSelected("stone"));
        assertTrue(OptionsManager.isNormalThemeSelected("normal"));

        // Test Icelandic standard theme keywords
        assertTrue(OptionsManager.isNormalThemeSelected("skæri"));
        assertTrue(OptionsManager.isNormalThemeSelected("blað"));
        assertTrue(OptionsManager.isNormalThemeSelected("steinn"));
        assertTrue(OptionsManager.isNormalThemeSelected("venjuleg"));

        // Test partial match
        assertTrue(OptionsManager.isNormalThemeSelected("I want to play with scissors"));

        // Test invalid input
        assertFalse(OptionsManager.isNormalThemeSelected("invalid"));
    }

    @Test
    public void testGetMoveNamesForTheme() {
        // Test standard theme in English
        OptionsManager.setLanguage(Language.ENGLISH);
        String[] standardMoves = OptionsManager.getMoveNamesForTheme(GameTheme.STANDARD);
        assertEquals(3, standardMoves.length);
        assertEquals("Rock", standardMoves[0]);
        assertEquals("Paper", standardMoves[1]);
        assertEquals("Scissors", standardMoves[2]);

        // Test bathroom theme in English
        String[] bathroomMoves = OptionsManager.getMoveNamesForTheme(GameTheme.BATHROOM);
        assertEquals(3, bathroomMoves.length);
        assertEquals("Poop", bathroomMoves[0]);
        assertEquals("Toilet Paper", bathroomMoves[1]);
        assertEquals("Pee", bathroomMoves[2]);

        // Test standard theme in Icelandic
        OptionsManager.setLanguage(Language.ICELANDIC);
        standardMoves = OptionsManager.getMoveNamesForTheme(GameTheme.STANDARD);
        assertEquals(3, standardMoves.length);
        assertEquals("Steinn", standardMoves[0]);
        assertEquals("Blað", standardMoves[1]);
        assertEquals("Skæri", standardMoves[2]);

        // Test bathroom theme in Icelandic
        bathroomMoves = OptionsManager.getMoveNamesForTheme(GameTheme.BATHROOM);
        assertEquals(3, bathroomMoves.length);
        assertEquals("Kúkur", bathroomMoves[0]);
        assertEquals("Klósettpappír", bathroomMoves[1]);
        assertEquals("Piss", bathroomMoves[2]);
    }
}
