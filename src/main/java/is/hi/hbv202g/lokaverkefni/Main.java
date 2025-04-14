package is.hi.hbv202g.lokaverkefni;

import is.hi.hbv202g.lokaverkefni.game.GameManager;
import is.hi.hbv202g.lokaverkefni.options.translation.TranslationsInitializer;

public class Main {
    public static void main(String[] args) {
        TranslationsInitializer.load();
        GameManager gameManager = new GameManager();
        gameManager.start();
    }
}