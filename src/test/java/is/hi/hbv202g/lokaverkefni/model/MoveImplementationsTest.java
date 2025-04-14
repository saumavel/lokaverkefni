package is.hi.hbv202g.lokaverkefni.model;

import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MoveImplementationsTest {

    private Move rock;
    private Move paper;
    private Move scissors;
    private GameTheme standardTheme;
    private GameTheme bathroomTheme;

    @Before
    public void setUp() {
        // Initialize with standard theme
        standardTheme = GameTheme.STANDARD;
        rock = new Rock(standardTheme);
        paper = new Paper(standardTheme);
        scissors = new Scissors(standardTheme);

        // Initialize bathroom theme for name testing
        bathroomTheme = GameTheme.BATHROOM;
    }

    @Test
    public void testRockComparisons() {
        // Rock vs Rock = DRAW
        assertEquals(Result.DRAW, rock.compareWith(rock));

        // Rock vs Paper = LOSE
        assertEquals(Result.LOSE, rock.compareWith(paper));

        // Rock vs Scissors = WIN
        assertEquals(Result.WIN, rock.compareWith(scissors));
    }

    @Test
    public void testPaperComparisons() {
        // Paper vs Rock = WIN
        assertEquals(Result.WIN, paper.compareWith(rock));

        // Paper vs Paper = DRAW
        assertEquals(Result.DRAW, paper.compareWith(paper));

        // Paper vs Scissors = LOSE
        assertEquals(Result.LOSE, paper.compareWith(scissors));
    }

    @Test
    public void testScissorsComparisons() {
        // Scissors vs Rock = LOSE
        assertEquals(Result.LOSE, scissors.compareWith(rock));

        // Scissors vs Paper = WIN
        assertEquals(Result.WIN, scissors.compareWith(paper));

        // Scissors vs Scissors = DRAW
        assertEquals(Result.DRAW, scissors.compareWith(scissors));
    }

    @Test
    public void testMoveNamesStandardTheme() {
        // Test names in standard theme
        assertEquals(standardTheme.getMoveName(0), rock.getName());
        assertEquals(standardTheme.getMoveName(1), paper.getName());
        assertEquals(standardTheme.getMoveName(2), scissors.getName());
    }

    @Test
    public void testMoveNamesBathroomTheme() {
        // Test names in bathroom theme
        Move bathroomRock = new Rock(bathroomTheme);
        Move bathroomPaper = new Paper(bathroomTheme);
        Move bathroomScissors = new Scissors(bathroomTheme);

        assertEquals(bathroomTheme.getMoveName(0), bathroomRock.getName());
        assertEquals(bathroomTheme.getMoveName(1), bathroomPaper.getName());
        assertEquals(bathroomTheme.getMoveName(2), bathroomScissors.getName());
    }

    @Test
    public void testSymmetricComparisons() {
        // If A beats B, then B should lose to A
        // Rock beats Scissors
        assertEquals(Result.WIN, rock.compareWith(scissors));
        assertEquals(Result.LOSE, scissors.compareWith(rock));

        // Paper beats Rock
        assertEquals(Result.WIN, paper.compareWith(rock));
        assertEquals(Result.LOSE, rock.compareWith(paper));

        // Scissors beats Paper
        assertEquals(Result.WIN, scissors.compareWith(paper));
        assertEquals(Result.LOSE, paper.compareWith(scissors));
    }
}
