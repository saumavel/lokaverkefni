package is.hi.hbv202g.lokaverkefni.game;

import is.hi.hbv202g.lokaverkefni.options.enums.GameMode;
import is.hi.hbv202g.lokaverkefni.options.enums.GameTheme;
import is.hi.hbv202g.lokaverkefni.strategy.ComputerPlayerStrategy;

public record GameConfiguration(Player player1, Player player2, GameTheme theme, GameMode mode,
                                ComputerPlayerStrategy.DifficultyLevel difficulty) {
}
