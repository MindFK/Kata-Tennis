package org.kata;

import org.kata.controllers.AutoGameController;
import org.kata.entities.Player;

/**
 * Created by Walid GHARIANI on 10/27/2018-12:35 AM.
 */
public class MainGame {
    public static void main(String[] args) {
        AutoGameController gameController = new AutoGameController();
        gameController.joinTheGame(new Player("Player1"));
        gameController.joinTheGame(new Player("Player2"));
        gameController.startTheSet();
        gameController.playASet();
    }
}
