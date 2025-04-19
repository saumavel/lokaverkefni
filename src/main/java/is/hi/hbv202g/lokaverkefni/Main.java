package is.hi.hbv202g.lokaverkefni;

import is.hi.hbv202g.lokaverkefni.game.GameManager;
import is.hi.hbv202g.lokaverkefni.options.translation.TranslationsInitializer;

/**
 *  Main class for the game.
 */
public class Main {
    /**
     *  Main method for the game.
     */
    public static void main(String[] args) {
        TranslationsInitializer.load();
        GameManager gameManager = new GameManager();
        gameManager.start();
    }
}