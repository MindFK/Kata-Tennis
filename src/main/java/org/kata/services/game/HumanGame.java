package org.kata.services.game;

import org.kata.entities.Game;

/**
 * Created by Walid GHARIANI on 10/27/2018-7:30 PM.
 */
public class HumanGame extends GameService {
    public HumanGame(Game game) {
        super(game);
    }

    public void winAPoint(int index) {
        setPointWinner(getPlayers().get(index));
        applyGameScoreRules(getPointWinner());
    }

}
