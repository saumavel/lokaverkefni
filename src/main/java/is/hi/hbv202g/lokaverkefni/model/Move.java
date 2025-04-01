package is.hi.hbv202g.lokaverkefni.model;

public interface Move {
    Result compareWith(Move other);
    String getName();
}