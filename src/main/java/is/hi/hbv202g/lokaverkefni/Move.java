package is.hi.hbv202g.lokaverkefni;

public interface Move {
    Result compareWith(Move other);
    String getName();
}