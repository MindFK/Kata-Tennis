package org.kata.services.score;

import org.kata.entities.Game;
import org.kata.entities.Player;

/**
 * Created by Walid GHARIANI on 10/26/2018-11:04 PM.
 */
public class PlayerByIndex {
    private Game game;

    public PlayerByIndex(Game game) {
        this.game = game;
    }

    public Player getPlayer(int index) {
        if (index == 0)
            return game.getPlayer1();
        else if (index == 1)
            return game.getPlayer2();
        else
            throw new RuntimeException("Invalid index");
    }
}
