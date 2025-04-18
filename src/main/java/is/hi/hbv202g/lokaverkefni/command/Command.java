package is.hi.hbv202g.lokaverkefni.command;

/**
 * Handles game command operations.
 */
public interface Command {
    /**
     * Executes the command.
     */
    void execute();

    /**
     * Undoes the effects of the command.
     */
    void undo();

}
