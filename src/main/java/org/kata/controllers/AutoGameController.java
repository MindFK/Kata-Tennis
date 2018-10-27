package org.kata.controllers;

import org.kata.entities.Game;
import org.kata.services.game.AutoPlayer;

/**
 * Created by Walid GHARIANI on 10/27/2018-3:19 AM.
 * <p>
 * This class launch the game automatically with the action lists specified by default
 * A game could be started automatically with a list of services predefined
 */
public class AutoGameController extends GameController {

    public AutoGameController(Game game) {
        this.game = new AutoPlayer(game);
    }


    // Waiting for player to join the game
    public AutoGameController() {
        this.game = new AutoPlayer(new Game());
    }

    public void playASet() {
        game.checkSetIsStarted();
        ((AutoPlayer) game).playASet();
    }
}
