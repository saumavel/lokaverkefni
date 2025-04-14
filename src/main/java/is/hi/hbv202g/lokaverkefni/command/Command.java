package is.hi.hbv202g.lokaverkefni.command;

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
