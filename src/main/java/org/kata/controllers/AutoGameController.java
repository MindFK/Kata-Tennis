package org.kata.controllers;

import org.kata.entities.Game;
import org.kata.services.game.AutoGame;
import org.kata.services.game.AutoPlayer;

/**
 * Created by Walid GHARIANI on 10/27/2018-3:19 AM.
 * <p>
 * This class launch the game automatically with the action lists specified by default
 * A game could be started automatically with a list of services predefined
 */
public class AutoGameController extends GameController {

    public AutoGameController(Game game) {
        this.game = new AutoGame(game);
    }


    // Waiting for player to join the game
    public AutoGameController() {
        this.game = new AutoGame(new Game());
    }

    public void playASet() {
        game.checkSetIsStarted();
        ((AutoGame) game).playASet();
    }

    public void playAMatch() {
        game.checkMatchIsStarted();
        ((AutoGame) game).playAMatch();
    }
}
