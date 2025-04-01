package is.hi.hbv202g.lokaverkefni;

interface Command {
    /**
     * Executes the command.
     */
    void execute();

    /**
     * Undoes the effects of the command.
     */
    void undo();
}
