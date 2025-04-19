package is.hi.hbv202g.lokaverkefni.game;

import is.hi.hbv202g.lokaverkefni.options.enums.GameMode;
import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import is.hi.hbv202g.lokaverkefni.strategy.ComputerPlayerStrategy;

/**
 *  Class for holding game configuration.
 * @param player1 player
 * @param player2 player
 * @param theme theme of the game
 * @param mode mode of the game
 * @param difficulty difficulty level
 */
public record GameConfiguration(Player player1, Player player2, GameTheme theme, GameMode mode,
                                ComputerPlayerStrategy.DifficultyLevel difficulty) {
}
