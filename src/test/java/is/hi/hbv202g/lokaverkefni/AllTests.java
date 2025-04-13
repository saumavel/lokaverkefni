package is.hi.hbv202g.lokaverkefni;

import is.hi.hbv202g.lokaverkefni.command.CommandTest;
import is.hi.hbv202g.lokaverkefni.game.MoveSelectionGameTest;
import is.hi.hbv202g.lokaverkefni.model.MoveFactoryTest;
import is.hi.hbv202g.lokaverkefni.model.MoveImplementationsTest;
import is.hi.hbv202g.lokaverkefni.model.ResultTest;
import is.hi.hbv202g.lokaverkefni.observer.ObserverPatternTest;
import is.hi.hbv202g.lokaverkefni.options.EnumsTest;
import is.hi.hbv202g.lokaverkefni.options.OptionsManagerTest;
import is.hi.hbv202g.lokaverkefni.score.ScoreManagerTest;
import is.hi.hbv202g.lokaverkefni.strategy.ComputerPlayerStrategyTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        // Command pattern tests
        CommandTest.class,

        // Game tests
        MoveSelectionGameTest.class,

        // Model tests
        MoveFactoryTest.class,
        MoveImplementationsTest.class,
        ResultTest.class,

        // Observer pattern tests
        ObserverPatternTest.class,

        // Options tests
        EnumsTest.class,
        OptionsManagerTest.class,

        // Score tests
        ScoreManagerTest.class,

        // Strategy tests
        ComputerPlayerStrategyTest.class
})
public class AllTests {
}
