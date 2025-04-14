package is.hi.hbv202g.lokaverkefni.command;

import is.hi.hbv202g.lokaverkefni.game.Player;
import is.hi.hbv202g.lokaverkefni.model.Move;
import is.hi.hbv202g.lokaverkefni.model.MoveFactory;
import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CommandTest {

    private GameInvoker invoker;
    private Player player;
    private Move rockMove;
    private Move paperMove;
    private MoveFactory moveFactory;

    @Before
    public void setUp() {
        // Use the STANDARD theme for testing
        GameTheme theme = GameTheme.STANDARD;
        moveFactory = new MoveFactory(theme);

        invoker = new GameInvoker();
        player = new Player("TestPlayer", theme);

        // Create moves using the factory
        rockMove = moveFactory.createMove(0); // Rock
        paperMove = moveFactory.createMove(1); // Paper
    }

    @Test
    public void testPlayMoveCommand() {
        // Test execute
        Command playRockCommand = new PlayMoveCommand(player, rockMove);
        invoker.executeCommand(playRockCommand);
        assertEquals("Player should have rock move after command execution",
                rockMove, player.getCurrentMove());

        // Test another command execution
        Command playPaperCommand = new PlayMoveCommand(player, paperMove);
        invoker.executeCommand(playPaperCommand);
        assertEquals("Player should have paper move after second command",
                paperMove, player.getCurrentMove());

        // Test undo
        invoker.undoLastCommand();
        assertEquals("Player should have rock move after undo",
                rockMove, player.getCurrentMove());
    }

    @Test
    public void testUndoEmptyHistory() {
        // Test undo with empty history
        invoker.undoLastCommand(); // Should not throw exception
        assertNull("Player move should remain null after undo with empty history",
                player.getCurrentMove());
    }

    @Test
    public void testMultipleCommandsAndUndos() {
        // Create a scissors move
        Move scissorsMove = moveFactory.createMove(2);

        // Execute multiple commands
        invoker.executeCommand(new PlayMoveCommand(player, rockMove));
        invoker.executeCommand(new PlayMoveCommand(player, paperMove));
        invoker.executeCommand(new PlayMoveCommand(player, scissorsMove));

        // Verify the last command was executed
        assertEquals("Player should have scissors move", scissorsMove, player.getCurrentMove());

        // Undo commands one by one
        invoker.undoLastCommand();
        assertEquals("Player should have paper move after first undo",
                paperMove, player.getCurrentMove());

        invoker.undoLastCommand();
        assertEquals("Player should have rock move after second undo",
                rockMove, player.getCurrentMove());

        invoker.undoLastCommand();
        assertNull("Player should have null move after third undo", player.getCurrentMove());
    }
}
