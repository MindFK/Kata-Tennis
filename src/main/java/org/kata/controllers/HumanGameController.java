package org.kata.controllers;

import org.kata.entities.Game;
import org.kata.services.game.HumanGame;

/**
 * Created by Walid GHARIANI on 10/27/2018-7:36 PM.
 * <p>
 * Mainly, this class is used to define specific services tests.
 */
public class HumanGameController extends GameController {
    public HumanGameController(Game game) {
        this.game = new HumanGame(game);
    }

    public HumanGameController() {//players will join the game later
        this.game = new HumanGame(new Game());
    }

    public void winAPoint(int playerIndex) {
        game.checkSetIsStarted();
        ((HumanGame) game).winAPoint(playerIndex);
    }
}
