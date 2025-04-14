package is.hi.hbv202g.lokaverkefni.strategy;

import is.hi.hbv202g.lokaverkefni.model.Move;
import is.hi.hbv202g.lokaverkefni.model.Paper;
import is.hi.hbv202g.lokaverkefni.model.Rock;
import is.hi.hbv202g.lokaverkefni.model.Scissors;
import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class ComputerPlayerStrategyTest {

    private ComputerPlayerStrategy strategy;
    private GameTheme standardTheme;
    private Move rockMove;
    private Move paperMove;
    private Move scissorsMove;

    @Before
    public void setUp() {
        standardTheme = GameTheme.STANDARD;
        strategy = new ComputerPlayerStrategy(standardTheme);

        // Create sample moves for testing
        rockMove = new Rock(standardTheme);
        paperMove = new Paper(standardTheme);
        scissorsMove = new Scissors(standardTheme);
    }

    @Test
    public void testManDifficulty() {
        // Set to MAN difficulty (always plays Rock)
        strategy.setDifficultyLevel(ComputerPlayerStrategy.DifficultyLevel.MAN);

        // Test multiple times to ensure consistency
        for (int i = 0; i < 10; i++) {
            Move selectedMove = strategy.selectMove(null);
            assertTrue("MAN difficulty should always select Rock", selectedMove instanceof Rock);
        }

        // Test with different opponent moves, should still always be Rock
        Move selectedMove1 = strategy.selectMove(rockMove);
        Move selectedMove2 = strategy.selectMove(paperMove);
        Move selectedMove3 = strategy.selectMove(scissorsMove);

        assertTrue("MAN difficulty should always select Rock regardless of opponent's move",
                selectedMove1 instanceof Rock);
        assertTrue("MAN difficulty should always select Rock regardless of opponent's move",
                selectedMove2 instanceof Rock);
        assertTrue("MAN difficulty should always select Rock regardless of opponent's move",
                selectedMove3 instanceof Rock);
    }

    @Test
    public void testEasyDifficulty() {
        // Set to EASY difficulty (completely random)
        strategy.setDifficultyLevel(ComputerPlayerStrategy.DifficultyLevel.EASY);

        // Run many trials to test randomness
        int rockCount = 0;
        int paperCount = 0;
        int scissorsCount = 0;
        int totalTrials = 1000;

        for (int i = 0; i < totalTrials; i++) {
            Move selectedMove = strategy.selectMove(null);

            if (selectedMove instanceof Rock) rockCount++;
            else if (selectedMove instanceof Paper) paperCount++;
            else if (selectedMove instanceof Scissors) scissorsCount++;
        }

        // In a truly random distribution, each move should be selected approximately 1/3 of the time
        // We'll allow for some statistical variation
        double expectedProportion = 1.0 / 3.0;
        double allowedDeviation = 0.1; // Allow 10% deviation from expected

        double rockProportion = (double) rockCount / totalTrials;
        double paperProportion = (double) paperCount / totalTrials;
        double scissorsProportion = (double) scissorsCount / totalTrials;

        assertTrue("Rock should be selected approximately 1/3 of the time in EASY mode",
                Math.abs(rockProportion - expectedProportion) < allowedDeviation);
        assertTrue("Paper should be selected approximately 1/3 of the time in EASY mode",
                Math.abs(paperProportion - expectedProportion) < allowedDeviation);
        assertTrue("Scissors should be selected approximately 1/3 of the time in EASY mode",
                Math.abs(scissorsProportion - expectedProportion) < allowedDeviation);

        // Also verify that the total adds up to the number of trials
        assertEquals("Total move count should equal number of trials",
                totalTrials, rockCount + paperCount + scissorsCount);
    }

    @Test
    public void testMediumDifficulty() {
        // Set to MEDIUM difficulty
        strategy.setDifficultyLevel(ComputerPlayerStrategy.DifficultyLevel.MEDIUM);

        // Test behavior with null opponent move (should be random)
        // We'll run multiple trials to account for randomness
        Map<Class<?>, Integer> nullOpponentMoveResults = new HashMap<>();
        int nullTrials = 100;

        for (int i = 0; i < nullTrials; i++) {
            Move selectedMove = strategy.selectMove(null);
            nullOpponentMoveResults.put(selectedMove.getClass(),
                    nullOpponentMoveResults.getOrDefault(selectedMove.getClass(), 0) + 1);
        }

        // Verify that all three move types are selected with null opponent move
        assertTrue("Rock should be selected sometimes with null opponent move",
                nullOpponentMoveResults.getOrDefault(Rock.class, 0) > 0);
        assertTrue("Paper should be selected sometimes with null opponent move",
                nullOpponentMoveResults.getOrDefault(Paper.class, 0) > 0);
        assertTrue("Scissors should be selected sometimes with null opponent move",
                nullOpponentMoveResults.getOrDefault(Scissors.class, 0) > 0);

        // Test counter-strategy behavior
        // In MEDIUM difficulty, the computer should counter the opponent's last move most of the time

        // Against Rock, should prefer Paper
        Map<Class<?>, Integer> vsRockResults = new HashMap<>();
        int vsRockTrials = 1000;

        for (int i = 0; i < vsRockTrials; i++) {
            Move selectedMove = strategy.selectMove(rockMove);
            vsRockResults.put(selectedMove.getClass(),
                    vsRockResults.getOrDefault(selectedMove.getClass(), 0) + 1);
        }

        // Paper should be selected more often than the other moves
        assertTrue("Paper should be selected more often against Rock in MEDIUM mode",
                vsRockResults.getOrDefault(Paper.class, 0) > vsRockResults.getOrDefault(Rock.class, 0));
        assertTrue("Paper should be selected more often against Rock in MEDIUM mode",
                vsRockResults.getOrDefault(Paper.class, 0) > vsRockResults.getOrDefault(Scissors.class, 0));

        // Against Paper, should prefer Scissors
        Map<Class<?>, Integer> vsPaperResults = new HashMap<>();
        int vsPaperTrials = 1000;

        for (int i = 0; i < vsPaperTrials; i++) {
            Move selectedMove = strategy.selectMove(paperMove);
            vsPaperResults.put(selectedMove.getClass(),
                    vsPaperResults.getOrDefault(selectedMove.getClass(), 0) + 1);
        }

        // Scissors should be selected more often than the other moves
        assertTrue("Scissors should be selected more often against Paper in MEDIUM mode",
                vsPaperResults.getOrDefault(Scissors.class, 0) > vsPaperResults.getOrDefault(Rock.class, 0));
        assertTrue("Scissors should be selected more often against Paper in MEDIUM mode",
                vsPaperResults.getOrDefault(Scissors.class, 0) > vsPaperResults.getOrDefault(Paper.class, 0));

        // Against Scissors, should prefer Rock
        Map<Class<?>, Integer> vsScissorsResults = new HashMap<>();
        int vsScissorsTrials = 1000;

        for (int i = 0; i < vsScissorsTrials; i++) {
            Move selectedMove = strategy.selectMove(scissorsMove);
            vsScissorsResults.put(selectedMove.getClass(),
                    vsScissorsResults.getOrDefault(selectedMove.getClass(), 0) + 1);
        }

        // Rock should be selected more often than the other moves
        assertTrue("Rock should be selected more often against Scissors in MEDIUM mode",
                vsScissorsResults.getOrDefault(Rock.class, 0) > vsScissorsResults.getOrDefault(Paper.class, 0));
        assertTrue("Rock should be selected more often against Scissors in MEDIUM mode",
                vsScissorsResults.getOrDefault(Rock.class, 0) > vsScissorsResults.getOrDefault(Scissors.class, 0));
    }

    @Test
    public void testHardDifficulty() {
        // Set to HARD difficulty
        strategy.setDifficultyLevel(ComputerPlayerStrategy.DifficultyLevel.HARD);

        // Test behavior with null opponent move (should be random)
        // We'll run multiple trials to account for randomness
        Map<Class<?>, Integer> nullOpponentMoveResults = new HashMap<>();
        int nullTrials = 100;

        for (int i = 0; i < nullTrials; i++) {
            Move selectedMove = strategy.selectMove(null);
            nullOpponentMoveResults.put(selectedMove.getClass(),
                    nullOpponentMoveResults.getOrDefault(selectedMove.getClass(), 0) + 1);
        }

        // Verify that all three move types are selected with null opponent move
        assertTrue("Rock should be selected sometimes with null opponent move in HARD mode",
                nullOpponentMoveResults.getOrDefault(Rock.class, 0) > 0);
        assertTrue("Paper should be selected sometimes with null opponent move in HARD mode",
                nullOpponentMoveResults.getOrDefault(Paper.class, 0) > 0);
        assertTrue("Scissors should be selected sometimes with null opponent move in HARD mode",
                nullOpponentMoveResults.getOrDefault(Scissors.class, 0) > 0);

        // Test anticipatory strategy behavior
        // In HARD difficulty, the computer anticipates that the player will switch to the move
        // that would have beaten their last move, and counters that

        // If opponent played Rock, they might switch to Paper, so computer should prefer Scissors
        Map<Class<?>, Integer> vsRockResults = new HashMap<>();
        int vsRockTrials = 100;

        for (int i = 0; i < vsRockTrials; i++) {
            Move selectedMove = strategy.selectMove(rockMove);
            vsRockResults.put(selectedMove.getClass(),
                    vsRockResults.getOrDefault(selectedMove.getClass(), 0) + 1);
        }

        // Scissors should be selected more often than the other moves
        assertTrue("Scissors should be selected more often against Rock in HARD mode",
                vsRockResults.getOrDefault(Scissors.class, 0) > vsRockResults.getOrDefault(Rock.class, 0));
        assertTrue("Scissors should be selected more often against Rock in HARD mode",
                vsRockResults.getOrDefault(Scissors.class, 0) > vsRockResults.getOrDefault(Paper.class, 0));

        // If opponent played Paper, they might switch to Scissors, so computer should prefer Rock
        Map<Class<?>, Integer> vsPaperResults = new HashMap<>();
        int vsPaperTrials = 100;

        for (int i = 0; i < vsPaperTrials; i++) {
            Move selectedMove = strategy.selectMove(paperMove);
            vsPaperResults.put(selectedMove.getClass(),
                    vsPaperResults.getOrDefault(selectedMove.getClass(), 0) + 1);
        }

        // Rock should be selected more often than the other moves
        assertTrue("Rock should be selected more often against Paper in HARD mode",
                vsPaperResults.getOrDefault(Rock.class, 0) > vsPaperResults.getOrDefault(Paper.class, 0));
        assertTrue("Rock should be selected more often against Paper in HARD mode",
                vsPaperResults.getOrDefault(Rock.class, 0) > vsPaperResults.getOrDefault(Scissors.class, 0));

        // If opponent played Scissors, they might switch to Rock, so computer should prefer Paper
        Map<Class<?>, Integer> vsScissorsResults = new HashMap<>();
        int vsScissorsTrials = 100;

        for (int i = 0; i < vsScissorsTrials; i++) {
            Move selectedMove = strategy.selectMove(scissorsMove);
            vsScissorsResults.put(selectedMove.getClass(),
                    vsScissorsResults.getOrDefault(selectedMove.getClass(), 0) + 1);
        }

        // Paper should be selected more often than the other moves
        assertTrue("Paper should be selected more often against Scissors in HARD mode",
                vsScissorsResults.getOrDefault(Paper.class, 0) > vsScissorsResults.getOrDefault(Rock.class, 0));
        assertTrue("Paper should be selected more often against Scissors in HARD mode",
                vsScissorsResults.getOrDefault(Paper.class, 0) > vsScissorsResults.getOrDefault(Scissors.class, 0));
    }

    @Test
    public void testDifficultyLevelEnum() {
        // Test that the DifficultyLevel enum has the expected values
        ComputerPlayerStrategy.DifficultyLevel[] levels = ComputerPlayerStrategy.DifficultyLevel.values();
        assertEquals("DifficultyLevel should have 4 values", 4, levels.length);
        assertEquals("First level should be MAN", ComputerPlayerStrategy.DifficultyLevel.MAN, levels[0]);
        assertEquals("Second level should be EASY", ComputerPlayerStrategy.DifficultyLevel.EASY, levels[1]);
        assertEquals("Third level should be MEDIUM", ComputerPlayerStrategy.DifficultyLevel.MEDIUM, levels[2]);
        assertEquals("Fourth level should be HARD", ComputerPlayerStrategy.DifficultyLevel.HARD, levels[3]);
    }

    @Test
    public void testDefaultDifficultyLevel() {
        // Create a new strategy without explicitly setting difficulty
        ComputerPlayerStrategy newStrategy = new ComputerPlayerStrategy(standardTheme);

        // Run many trials to test that it behaves like EASY difficulty (random)
        int rockCount = 0;
        int paperCount = 0;
        int scissorsCount = 0;
        int totalTrials = 1000;

        for (int i = 0; i < totalTrials; i++) {
            Move selectedMove = newStrategy.selectMove(null);

            if (selectedMove instanceof Rock) rockCount++;
            else if (selectedMove instanceof Paper) paperCount++;
            else if (selectedMove instanceof Scissors) scissorsCount++;
        }

        // In a truly random distribution, each move should be selected approximately 1/3 of the time
        // We'll allow for some statistical variation
        double expectedProportion = 1.0 / 3.0;
        double allowedDeviation = 0.1; // Allow 10% deviation from expected

        double rockProportion = (double) rockCount / totalTrials;
        double paperProportion = (double) paperCount / totalTrials;
        double scissorsProportion = (double) scissorsCount / totalTrials;

        assertTrue("Rock should be selected approximately 1/3 of the time with default difficulty",
                Math.abs(rockProportion - expectedProportion) < allowedDeviation);
        assertTrue("Paper should be selected approximately 1/3 of the time with default difficulty",
                Math.abs(paperProportion - expectedProportion) < allowedDeviation);
        assertTrue("Scissors should be selected approximately 1/3 of the time with default difficulty",
                Math.abs(scissorsProportion - expectedProportion) < allowedDeviation);
    }

    @Test
    public void testBathroomTheme() {
        // Test that the strategy works with the bathroom theme
        GameTheme bathroomTheme = GameTheme.BATHROOM;
        ComputerPlayerStrategy bathroomStrategy = new ComputerPlayerStrategy(bathroomTheme);

        // Set to MAN difficulty (always plays Rock/Poop)
        bathroomStrategy.setDifficultyLevel(ComputerPlayerStrategy.DifficultyLevel.MAN);

        Move selectedMove = bathroomStrategy.selectMove(null);
        assertTrue("MAN difficulty should always select Rock/Poop", selectedMove instanceof Rock);
        assertEquals("Move name should match bathroom theme", bathroomTheme.getMoveName(0), selectedMove.getName());

        // Test EASY difficulty with bathroom theme
        bathroomStrategy.setDifficultyLevel(ComputerPlayerStrategy.DifficultyLevel.EASY);

        // Run multiple trials to ensure all moves are selected
        boolean foundRock = false;
        boolean foundPaper = false;
        boolean foundScissors = false;

        for (int i = 0; i < 100; i++) {
            Move move = bathroomStrategy.selectMove(null);

            if (move instanceof Rock) {
                foundRock = true;
                assertEquals("Rock move should have bathroom theme name",
                        bathroomTheme.getMoveName(0), move.getName());
            } else if (move instanceof Paper) {
                foundPaper = true;
                assertEquals("Paper move should have bathroom theme name",
                        bathroomTheme.getMoveName(1), move.getName());
            } else if (move instanceof Scissors) {
                foundScissors = true;
                assertEquals("Scissors move should have bathroom theme name",
                        bathroomTheme.getMoveName(2), move.getName());
            }

            if (foundRock && foundPaper && foundScissors) {
                break; // Found all move types
            }
        }

        assertTrue("Rock/Poop should be selected sometimes in EASY mode with bathroom theme", foundRock);
        assertTrue("Paper/Toilet Paper should be selected sometimes in EASY mode with bathroom theme", foundPaper);
        assertTrue("Scissors/Pee should be selected sometimes in EASY mode with bathroom theme", foundScissors);
    }
}
