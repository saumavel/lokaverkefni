package is.hi.hbv202g.lokaverkefni.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class ResultTest {

    @Test
    public void testResultValues() {
        // Test that Result has the expected values
        Result[] results = Result.values();
        assertEquals("Result should have 3 values", 3, results.length);
        assertEquals("First result should be WIN", Result.WIN, results[0]);
        assertEquals("Second result should be LOSE", Result.LOSE, results[1]);
        assertEquals("Third result should be DRAW", Result.DRAW, results[2]);
    }
}
