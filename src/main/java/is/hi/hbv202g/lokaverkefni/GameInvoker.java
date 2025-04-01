package is.hi.hbv202g.lokaverkefni;

import java.util.ArrayList;
import java.util.List;

public class GameInvoker {
    private final List<Command> history = new ArrayList<>();

    /**
     * Executes the given command and adds it to the history list.
     *
     * @param command The command to be executed.
     */
    public void executeCommand(Command command) {
        // Execute the command
        command.execute();
        // Add command to the history for potential undo
        history.add(command);
    }

    /**
     * Reverts the last command in the history and removes it from the list.
     * Does nothing if the history is empty.
     */
    public void undoLastCommand() {
        if (!history.isEmpty()) {
            // Get the last command from the history
            Command lastCommand = history.remove(history.size() - 1);
            // Undo the command
            lastCommand.undo();
        }
    }
}
