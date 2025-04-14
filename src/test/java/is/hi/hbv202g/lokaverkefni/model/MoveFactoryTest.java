package is.hi.hbv202g.lokaverkefni.model;

import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MoveFactoryTest {

    private MoveFactory standardFactory;
    private MoveFactory bathroomFactory;

    @Before
    public void setUp() {
        standardFactory = new MoveFactory(GameTheme.STANDARD);
        bathroomFactory = new MoveFactory(GameTheme.BATHROOM);
    }

    @Test
    public void testCreateMoveStandardTheme() {
        // Test creating Rock (index 0)
        Move rock = standardFactory.createMove(0);
        assertTrue("Move should be a Rock instance", rock instanceof Rock);
        assertEquals("Move name should match theme's rock name",
                GameTheme.STANDARD.getMoveName(0), rock.getName());

        // Test creating Paper (index 1)
        Move paper = standardFactory.createMove(1);
        assertTrue("Move should be a Paper instance", paper instanceof Paper);
        assertEquals("Move name should match theme's paper name",
                GameTheme.STANDARD.getMoveName(1), paper.getName());

        // Test creating Scissors (index 2)
        Move scissors = standardFactory.createMove(2);
        assertTrue("Move should be a Scissors instance", scissors instanceof Scissors);
        assertEquals("Move name should match theme's scissors name",
                GameTheme.STANDARD.getMoveName(2), scissors.getName());
    }

    @Test
    public void testCreateMoveBathroomTheme() {
        // Test creating Poop (index 0)
        Move poop = bathroomFactory.createMove(0);
        assertTrue("Move should be a Rock instance (representing Poop)", poop instanceof Rock);
        assertEquals("Move name should match theme's poop name",
                GameTheme.BATHROOM.getMoveName(0), poop.getName());

        // Test creating Toilet Paper (index 1)
        Move toiletPaper = bathroomFactory.createMove(1);
        assertTrue("Move should be a Paper instance (representing Toilet Paper)",
                toiletPaper instanceof Paper);
        assertEquals("Move name should match theme's toilet paper name",
                GameTheme.BATHROOM.getMoveName(1), toiletPaper.getName());

        // Test creating Pee (index 2)
        Move pee = bathroomFactory.createMove(2);
        assertTrue("Move should be a Scissors instance (representing Pee)",
                pee instanceof Scissors);
        assertEquals("Move name should match theme's pee name",
                GameTheme.BATHROOM.getMoveName(2), pee.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMoveWithNegativeIndex() {
        standardFactory.createMove(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMoveWithTooLargeIndex() {
        standardFactory.createMove(3); // Only indices 0, 1, 2 are valid
    }

    @Test
    public void testCreateMoveAndCompare() {
        // Create moves using the factory
        Move rock = standardFactory.createMove(0);
        Move paper = standardFactory.createMove(1);
        Move scissors = standardFactory.createMove(2);

        // Test comparisons
        assertEquals(Result.LOSE, rock.compareWith(paper));
        assertEquals(Result.WIN, rock.compareWith(scissors));
        assertEquals(Result.DRAW, rock.compareWith(rock));

        assertEquals(Result.WIN, paper.compareWith(rock));
        assertEquals(Result.LOSE, paper.compareWith(scissors));
        assertEquals(Result.DRAW, paper.compareWith(paper));

        assertEquals(Result.LOSE, scissors.compareWith(rock));
        assertEquals(Result.WIN, scissors.compareWith(paper));
        assertEquals(Result.DRAW, scissors.compareWith(scissors));
    }

    @Test
    public void testBathroomThemeComparisons() {
        // Create moves using the bathroom factory
        Move poop = bathroomFactory.createMove(0);       // Equivalent to Rock
        Move toiletPaper = bathroomFactory.createMove(1); // Equivalent to Paper
        Move pee = bathroomFactory.createMove(2);        // Equivalent to Scissors

        // Test that the game logic works the same way
        // Toilet Paper beats Poop (Paper beats Rock)
        assertEquals(Result.WIN, toiletPaper.compareWith(poop));
        assertEquals(Result.LOSE, poop.compareWith(toiletPaper));

        // Poop beats Pee (Rock beats Scissors)
        assertEquals(Result.WIN, poop.compareWith(pee));
        assertEquals(Result.LOSE, pee.compareWith(poop));

        // Pee beats Toilet Paper (Scissors beats Paper)
        assertEquals(Result.WIN, pee.compareWith(toiletPaper));
        assertEquals(Result.LOSE, toiletPaper.compareWith(pee));

        // Draws
        assertEquals(Result.DRAW, poop.compareWith(poop));
        assertEquals(Result.DRAW, toiletPaper.compareWith(toiletPaper));
        assertEquals(Result.DRAW, pee.compareWith(pee));
    }

    @Test
    public void testCrossMoveComparisons() {
        // Create moves from both themes
        Move standardRock = standardFactory.createMove(0);
        Move bathroomPoop = bathroomFactory.createMove(0);

        // They should behave the same way in comparisons
        Move standardPaper = standardFactory.createMove(1);
        Move bathroomToiletPaper = bathroomFactory.createMove(1);

        // Standard Rock vs Standard Paper = LOSE
        assertEquals(Result.LOSE, standardRock.compareWith(standardPaper));

        // Bathroom Poop vs Bathroom Toilet Paper = LOSE
        assertEquals(Result.LOSE, bathroomPoop.compareWith(bathroomToiletPaper));

        // Standard Rock vs Bathroom Toilet Paper = LOSE
        assertEquals(Result.LOSE, standardRock.compareWith(bathroomToiletPaper));

        // Bathroom Poop vs Standard Paper = LOSE
        assertEquals(Result.LOSE, bathroomPoop.compareWith(standardPaper));
    }
}
