package is.hi.hbv202g.lokaverkefni;

import org.junit.Test;

/**
 * Unit test for simple Main.
 */
public class MainTest
{
    /**
     * A test that checks if paper beats rock.
     */
    @Test
    public void paperShouldBeatRock() {
        Move paper = new Paper();
        Move rock = new Rock();
        Result result = paper.compareWith(rock);
        assert(result == Result.WIN);
    }
    /**
     * A test that checks if rock beats scissors.
     */
    @Test
    public void rockShouldBeatScissors() {
        Move rock = new Rock();
        Move scissors = new Scissors();
        Result result = rock.compareWith(scissors);
        assert(result == Result.WIN);
    }
    /**
     * A test that checks if scissors beats paper.
     */
    @Test
    public void scissorsShouldBeatPaper() {
        Move scissors = new Scissors();
        Move paper = new Paper();
        Result result = scissors.compareWith(paper);
        assert(result == Result.WIN);
    }
    @Test
    public void paperShouldDrawWithPaper() {
        Move paper = new Paper();
        Move paper2 = new Paper();
        Result result = paper.compareWith(paper2);
        assert(result == Result.DRAW);
    }
}
